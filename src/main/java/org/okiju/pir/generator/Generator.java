package org.okiju.pir.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.okiju.pir.extractor.ExtractionBean;
import org.okiju.pir.model.Entry;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.DateFormatter;
import org.okiju.pir.util.DateHelper;
import org.okiju.pir.util.EmailBean;
import org.okiju.pir.util.FileHelper;
import org.okiju.pir.util.MailHelper;
import org.okiju.pir.util.MessageGenerator;

public class Generator {

    private Properties props;
    private List<ExtractionBean> extractions;
    private String subjectEmail;
    private TemplateInfo templateEmail;

    Generator(Properties props, List<ExtractionBean> extractions, String SubjectEmail, TemplateInfo templateEmail) {
        this.props = props;
        this.extractions = extractions;
        this.subjectEmail = SubjectEmail;
        this.templateEmail = templateEmail;
    }

    public void execute() {

        Map<String, List<String>> context = new HashMap<String, List<String>>();

        for (ExtractionBean extraction : extractions) {
            context.put(extraction.getContext(), asStringList(generateEntries(extraction)));
        }

        sendEmail(props, context, subjectEmail, templateEmail);

    }

    private Set<Entry> generateEntries(ExtractionBean extraction) {
        Set<Entry> entries = extraction.getGenerator().extract();
        System.out.println("entries:" + entries);
        entries = sort(entries);
        entries = filterEntriesByDate(entries);
        entries = filterEmptyEntries(entries);
        FileHelper.writeCollectionInDatedFile(extraction.getFilename(), entries);
        return entries;
    }

    public static Set<Entry> sort(Set<Entry> entries) {
        return new TreeSet<Entry>(entries);
    }

    public static Set<Entry> filterEntriesByDate(Set<Entry> entries) {
        Set<Entry> result = new HashSet<Entry>();
        for (Entry entry : entries) {
            if (entry.getDate() == null || !DateHelper.isDateAWeekBefore(entry.getDate())) {
                result.add(entry);
            }
        }
        return result;
    }

    public static List<String> asStringList(Set<Entry> entries) {
        List<String> result = new ArrayList<String>();
        for (Entry entry : entries) {
            System.out.println("entry:" + entry.toString());
            result.add(entry.toString());
        }
        return result;
    }

    public static Set<Entry> filterEmptyEntries(Set<Entry> entries) {
        Set<Entry> result = new TreeSet<Entry>();
        for (Entry entry : entries) {
            String text = entry.getText();
            if (text != null) {
                text = text.trim();
                if (text.length() != 0 ) {
                    result.add(entry);
                }
            }
        }
        return result;
    }
    
    private void sendEmail(Properties props, Map<String, List<String>> entries, String prefix, TemplateInfo templates) {
        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries, templates);
        String subject = prefix + DateFormatter.formatToday();

        MailHelper.sendMessage(message, subject, emailBean);
    }

}

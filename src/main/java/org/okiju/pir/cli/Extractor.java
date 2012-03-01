package org.okiju.pir.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.DateFormatter;
import org.okiju.pir.util.EmailBean;
import org.okiju.pir.util.FileHelper;
import org.okiju.pir.util.MailHelper;
import org.okiju.pir.util.MessageGenerator;

public class Extractor {

    private Properties props;
    private List<ExtractionBean> extractions;
    private String subjectEmail;
    private TemplateInfo templateEmail;

    Extractor(Properties props, List<ExtractionBean> extractions, String SubjectEmail, TemplateInfo templateEmail) {
        this.props = props;
        this.extractions = extractions;
        this.subjectEmail = SubjectEmail;
        this.templateEmail = templateEmail;
    }

    public void execute() {

        Map<String, Set<String>> context = new HashMap<String, Set<String>>();

        for (ExtractionBean extraction : extractions) {
            context.put(extraction.getContext(), asStringSet(generateEntries(extraction)));
        }

        sendEmail(props, context, subjectEmail, templateEmail);

    }

    private Set<Entry> generateEntries(ExtractionBean extraction) {
        Set<Entry> entries = extraction.getGenerator().generate();
        FileHelper.writeCollectionInDatedFile(extraction.getFilename(), entries);
        return entries;
    }

    private void sendEmail(Properties props, Map<String, Set<String>> entries, String prefix, TemplateInfo templates) {
        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries, templates);
        String subject = prefix + DateFormatter.formatToday();

        MailHelper.sendMessage(message, subject, emailBean);
    }

    private Set<String> asStringSet(Set<Entry> entries) {
        Set<String> result = new HashSet<String>();
        for (Entry entry : entries) {
            result.add(entry.getText());
        }
        return result;
    }
}


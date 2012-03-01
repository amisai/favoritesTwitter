package org.okiju.pir.cli;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.okiju.pir.generator.Generator;
import org.okiju.pir.model.Entry;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.DateFormatter;
import org.okiju.pir.util.EmailBean;
import org.okiju.pir.util.FileHelper;
import org.okiju.pir.util.MailHelper;
import org.okiju.pir.util.MessageGenerator;

public class BaseExtractor {

    protected static void checkArgs(String nameApp, String[] args) {
        if (args.length < 1) {
            System.out.println("Use: " + nameApp + " configPath");
            System.exit(1);
        }
    }

    protected static void doExtraction(Properties props, List<ExtractionBean> extractions, String SubjectEmail,
            TemplateInfo templateEmail) {
        Map<String, Set<String>> context = new HashMap<String, Set<String>>();

        for (ExtractionBean extraction : extractions) {
            context.put(extraction.getContext(),
                    asStringSet(generateEntries(extraction)));
        }

        sendEmail(props, context, SubjectEmail, templateEmail);
    }
    
    private static Set<Entry> generateEntries(ExtractionBean extraction) {
        Set<Entry> entries = extraction.getGenerator().generate();
        FileHelper.writeCollectionInDatedFile(extraction.getFilename(), entries);
        return entries;
    }

    private static void sendEmail(Properties props, Map<String, Set<String>> entries, String prefix,
            TemplateInfo templates) {
        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries, templates);
        String subject = prefix + DateFormatter.formatToday();

        MailHelper.sendMessage(message, subject, emailBean);
    }

    private static Set<String> asStringSet(Set<Entry> entries) {
        Set<String> result = new HashSet<String>();
        for (Entry entry : entries) {
            result.add(entry.getText());
        }
        return result;
    }
}

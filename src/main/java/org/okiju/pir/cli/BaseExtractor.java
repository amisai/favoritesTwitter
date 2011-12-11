package org.okiju.pir.cli;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.okiju.pir.generator.Generator;
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

    protected static List<String> generateEntries(Generator generator, String prefix) {
        List<String> entries = generator.generate();
        FileHelper.writeCollectionInDatedFile(prefix, entries);
        return entries;
    }

    protected static void sendEmail(Properties props, Map<String, List<String>> entries, String prefix, TemplateInfo templates) {
        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries, templates);
        String subject = prefix + DateFormatter.formatToday();

        MailHelper.sendMessage(message, subject, emailBean);
    }

}

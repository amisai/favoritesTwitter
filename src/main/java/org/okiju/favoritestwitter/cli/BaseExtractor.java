package org.okiju.favoritestwitter.cli;

import java.util.List;
import java.util.Properties;

import org.okiju.favoritestwitter.Generator;
import org.okiju.favoritestwitter.MessageGenerator;
import org.okiju.favoritestwitter.util.DateFormatter;
import org.okiju.favoritestwitter.util.EmailBean;
import org.okiju.favoritestwitter.util.FileHelper;
import org.okiju.favoritestwitter.util.MailHelper;

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
    protected static void sendEmail(Properties props, List<String> entries, String prefix, String emptyTemplate, String contentTemplate) {
        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries, emptyTemplate, contentTemplate);
        String subject = prefix + DateFormatter.formatToday();
    
        MailHelper.sendMessage(message, subject, emailBean);
    }

}

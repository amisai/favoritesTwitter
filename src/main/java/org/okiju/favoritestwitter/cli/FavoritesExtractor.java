package org.okiju.favoritestwitter.cli;

import java.util.List;
import java.util.Properties;

import org.okiju.favoritestwitter.Generator;
import org.okiju.favoritestwitter.MessageGenerator;
import org.okiju.favoritestwitter.TwitterGenerator;
import org.okiju.favoritestwitter.util.DateFormatter;
import org.okiju.favoritestwitter.util.EmailBean;
import org.okiju.favoritestwitter.util.FileHelper;
import org.okiju.favoritestwitter.util.MailHelper;

/**
 * CLI of favorites extractor
 * 
 */
public class FavoritesExtractor {

    public static void main(String[] args) {
        checkArgs(args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        List<String> entries = generateEntries(new TwitterGenerator(props), "ficheroTwits");

        sendEmail(props, entries, "Favoritos del día ");
    }

    private static void checkArgs(String[] args) {
        if (args.length < 1) {
            System.out.println("Use: FavoritesExtractor configPath");
            System.exit(1);
        }
    }

    private static List<String> generateEntries(Generator twitterGenerator, String prefix) {
        List<String> entries = twitterGenerator.generate();
        FileHelper.writeCollectionInDatedFile(prefix, entries);
        return entries;
    }

    private static void sendEmail(Properties props, List<String> entries, String prefix) {
        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries);
        String subject = prefix + DateFormatter.formatToday();

        MailHelper.sendMessage(message, subject, emailBean);
    }
}

package org.okiju.favoritestwitter.cli;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.okiju.favoritestwitter.MessageGenerator;
import org.okiju.favoritestwitter.TwitterHelper;
import org.okiju.favoritestwitter.util.DateFormatter;
import org.okiju.favoritestwitter.util.EmailBean;
import org.okiju.favoritestwitter.util.FileHelper;
import org.okiju.favoritestwitter.util.MailHelper;

/**
 * Hello world!
 * 
 */
public class FavoritesExtractor {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Use: FavoritesExtractor configPath");
            System.exit(1);
        }
        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        List<String> entries = TwitterHelper.generateListTwits(props);
        String prefix = "ficheroTwits";
        FileHelper.writeCollectionInDatedFile(prefix, entries);

        EmailBean emailBean = new EmailBean(props);
        String message = MessageGenerator.generateMessage(entries);
        String subject = "Favoritos del día " + DateFormatter.formatToday();

        MailHelper.sendMessage(message, subject, emailBean);
    }
}

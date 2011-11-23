package org.okiju.favoritestwitter;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
        Properties props = loadProperties(path, "email.props");

        List<String> twits = TwitterHelper.generateListTwits(path);

        String prefix = "ficheroTwits";
        FileHelper.writeCollectionInDatedFile(prefix, twits);

        String message = MessageGenerator.generateMessage(twits);
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        String from = props.getProperty("from");
        String recipient = props.getProperty("recipient");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        String subject = "Favoritos del día " + sdf.format(new Date());

        MailHelper.sendMessage(message, subject, recipient, from, username, password);
    }

    public static Properties loadProperties(String path, String file) {
        Properties props = new Properties();
        try {

            props.load(new FileInputStream(path + "/" + file));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return props;
    }
}

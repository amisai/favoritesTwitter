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
        Properties props = loadProperties();

        List<String> twits = TwitterHelper.generateListTwits();

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

    private static Properties loadProperties() {
        Properties props = new Properties();
        try {
            String path = "./";
            
            props.load(new FileInputStream(path + "email.props"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return props;
    }
}

package org.okiju.pir.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPBodyPart;

public class SentEmailGenerator implements Generator {

    private static transient Logger logger = LoggerFactory.getLogger(SentEmailGenerator.class);
    private String username;
    private String password;

    public SentEmailGenerator(Properties props) {

        username = props.getProperty("username");
        password = props.getProperty("password");
    }

    @Override
    public List<String> generate() {
        List<String> messages = new ArrayList<String>();
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);
        Store store;
        try {
            store = session.getStore("imaps");

            store.connect("imap.gmail.com", username, password);

            Folder sentMail = store.getFolder("[Gmail]/Sent Mail");
            sentMail.open(Folder.READ_ONLY);
            int deltaMsgs = 10;
            int end = sentMail.getMessageCount();
            int start = end - deltaMsgs;
            boolean found = false;
            List<Message> bulkSentMessages = new ArrayList<Message>();
            do {
                List<Message> tempMessages = Arrays.asList(sentMail.getMessages(start, end));
                found = isDateAWeekBefore(tempMessages.get(0).getSentDate());
                bulkSentMessages.addAll(tempMessages);
                end = start - 1;
                start = end - deltaMsgs;
            } while (!found);

            List<Message> sentMessages = new ArrayList<Message>();

            for (Message message : bulkSentMessages) {
                if (messageWanted(username, message)) {
                    sentMessages.add(message);
                }
            }
            for (Message msg : sentMessages) {
                StringBuilder abstractMsg = new StringBuilder();
                Object content = msg.getContent();
                String dataContent = content.toString();
                if (content instanceof MimeMultipart) {
                    MimeMultipart multipart = (MimeMultipart) content;
                    dataContent = ((IMAPBodyPart) multipart.getBodyPart(0)).getContent().toString();
                }
                dataContent = dataContent.replace("Un saludo,", "");
                dataContent = dataContent.replace("Enviado desde mi iPad", "");
                dataContent = dataContent.replace("Abel", "");

                abstractMsg.append(msg.getSubject()).append("\n").append(dataContent).append("\n");
                messages.add(abstractMsg.toString());
            }
            store.close();
        } catch (Exception e) {
            logger.error("Error while getting emails information", e);
        }
        return messages;
    }

    private static boolean messageWanted(String username, Message message) throws MessagingException {
        boolean result = false;
        String recipient = "";
        String subject = "";
        String favoritesSubject = "Favoritos del d";
        String instapaperSummarySubject = "Urls guardadas en Instapaper";
        String emailsSummarySubject = "Emails de la semana";
        try {
            recipient = message.getAllRecipients()[0].toString();
            if (recipient == null) {
                recipient = "";
            }
            subject = message.getSubject();
            if (subject == null) {
                subject = "";
            }
        } catch (Exception e) {
        }

        result = recipient.contains(username) && !isDateAWeekBefore(message.getSentDate())
                && !subject.contains(favoritesSubject) && !subject.contains(instapaperSummarySubject)
                && !subject.contains(emailsSummarySubject);
        return result;
    }

    private static boolean isDateAWeekBefore(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Calendar calDate = new GregorianCalendar();
        calDate.setTime(date);
        return calDate.before(cal);
    }
}
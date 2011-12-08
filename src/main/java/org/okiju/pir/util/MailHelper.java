package org.okiju.pir.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailHelper {

    private static transient Logger logger = LoggerFactory.getLogger(MailHelper.class);
    private static String port = "465";

    public static void setPort(String port) {
        MailHelper.port = port;
    }

    public static void sendMessage(String textMessage, String subject, final EmailBean emailBean) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailBean.getUsername(), emailBean.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailBean.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailBean.getRecipient()));
            message.setSubject(subject);
            message.setText(textMessage);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            logger.error("Error while sending email", e);
            throw new RuntimeException(e);
        }
    }
}
package org.okiju.pir.filter;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.okiju.pir.util.DateHelper;

public class SentEmailFilter implements Filter {

    private String username;
    public SentEmailFilter(String username) {
        this.username = username;
    }
    @Override
    public boolean accept(Object objectToFilter) {
        boolean result = false;

        if (objectToFilter instanceof Message) {

            Message message = (Message) objectToFilter;

            String recipient = "";
            String subject = "";
            List<String> subjectsToAvoid = new ArrayList<String>();
            subjectsToAvoid.add("Favoritos del d");
            subjectsToAvoid.add("Urls guardadas en Instapaper");
            subjectsToAvoid.add("Emails de la semana");
            subjectsToAvoid.add("Resumen diario");
            subjectsToAvoid.add("Resumen de la semana");
            subjectsToAvoid.add("subject");
            
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

            try {
                result = recipient.contains(username) && !DateHelper.isDateAWeekBefore(message.getSentDate());
                for (String subject2Avoid: subjectsToAvoid) {
                    result = result &  !subject.contains(subject2Avoid);
                }
            } catch (MessagingException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

}
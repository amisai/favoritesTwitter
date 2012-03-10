package org.okiju.pir.filter;

import javax.mail.Message;
import javax.mail.MessagingException;

public class SubjectEmailFilter implements Filter {

    private String subject;

    public SubjectEmailFilter(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean accept(Object objectToFilter) {
        boolean result = false;
        if (objectToFilter instanceof Message) {
            Message msg = (Message) objectToFilter;
            try {
                String sub = msg.getSubject();
                result = sub.contains(subject);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
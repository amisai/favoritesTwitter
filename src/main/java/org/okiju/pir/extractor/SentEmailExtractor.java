package org.okiju.pir.extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.okiju.pir.filter.Filter;
import org.okiju.pir.filter.MessageFilter;
import org.okiju.pir.model.Entry;
import org.okiju.pir.util.DateHelper;
import org.okiju.pir.util.StandardStringCleaner;
import org.okiju.pir.util.StringCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPBodyPart;

public class SentEmailExtractor implements Extractor {

    private static transient Logger logger = LoggerFactory.getLogger(SentEmailExtractor.class);
    private String username;
    private String password;

    public SentEmailExtractor(Properties props) {
        username = props.getProperty("email.username");
        password = props.getProperty("email.password");
    }

    @Override
    public Set<Entry> extract() {
        Set<Entry> messages = null;

        try {
            Store store = openStore();

            List<Message> bulkSentMessages = getEmailsFromServer(store);

            List<Message> sentMessages = filterMessages(bulkSentMessages, new MessageFilter(username));

            messages = generateEntries(sentMessages);
            store.close();
        } catch (Exception e) {
            logger.error("Error while getting emails information", e);
        }
        return messages;
    }

    private Store openStore() throws NoSuchProviderException, MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", username, password);
        return store;
    }

    private List<Message> filterMessages(List<Message> bulkSentMessages, Filter filter) throws MessagingException {
        List<Message> sentMessages = new ArrayList<Message>();

        for (Message message : bulkSentMessages) {
            if (filter.accept(message)) {
                sentMessages.add(message);
            }
        }
        return sentMessages;
    }

    private Set<Entry> generateEntries(List<Message> sentMessages) throws IOException, MessagingException {
        Set<Entry> messages = new HashSet<Entry>();
        for (Message msg : sentMessages) {
            String content = extractContentFromMessage(msg);
            messages.add(new Entry(content, "", null));
        }
        return messages;
    }

    private String extractContentFromMessage(Message msg) throws IOException, MessagingException {
   
        Object content = msg.getContent();
        String dataContent = content.toString();
        if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            dataContent = ((IMAPBodyPart) multipart.getBodyPart(0)).getContent().toString();
        }
        dataContent = removeGreetingExpresions(dataContent);
        
        return createMessageSummary(msg, dataContent);
    }

    private String createMessageSummary(Message msg, String dataContent) throws MessagingException {
        StringBuilder abstractMsg = new StringBuilder();
        abstractMsg.append(msg.getSubject()).append("\n").append(dataContent).append("\n");
        return abstractMsg.toString();
    }

    private String removeGreetingExpresions(String dataContent) {
        StringCleaner cleaner = new StandardStringCleaner();

        return cleaner.clean(dataContent);
    }

    private List<Message> getEmailsFromServer(Store store) throws NoSuchProviderException, MessagingException {

        Folder sentMail = store.getFolder("[Gmail]/Sent Mail");
        sentMail.open(Folder.READ_ONLY);
        int deltaMsgs = 10;
        int end = sentMail.getMessageCount();
        int start = end - deltaMsgs;
        boolean found = false;
        List<Message> bulkSentMessages = new ArrayList<Message>();
        do {
            List<Message> tempMessages = Arrays.asList(sentMail.getMessages(start, end));
            found = DateHelper.isDateAWeekBefore(tempMessages.get(0).getSentDate());
            bulkSentMessages.addAll(tempMessages);
            end = start - 1;
            start = end - deltaMsgs;
        } while (!found);
        return bulkSentMessages;
    }
}

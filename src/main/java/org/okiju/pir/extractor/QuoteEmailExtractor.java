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
import org.okiju.pir.filter.SubjectEmailFilter;
import org.okiju.pir.model.Entry;
import org.okiju.pir.util.QuoteMealCleaner;
import org.okiju.pir.util.StringCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPBodyPart;

public class QuoteEmailExtractor implements Extractor {

    private static transient Logger logger = LoggerFactory.getLogger(QuoteEmailExtractor.class);
    private String username;
    private String password;

    public QuoteEmailExtractor(Properties props) {
        username = props.getProperty("email.username");
        password = props.getProperty("email.password");
    }

    @Override
    public Set<Entry> extract() {
        Set<Entry> messages = null;

        try {
            Store store = openStore();

            List<Message> bulkSentMessages = getEmailsFromServer(store);

            List<Message> sentMessages = filterMessages(bulkSentMessages, new SubjectEmailFilter("Quotemeal"));
             messages = generateEntries(sentMessages);
            store.close();
        } catch (Exception e) {
            logger.error("Error while getting emails information", e);
        }
        return messages;
    }

    public Store openStore() throws NoSuchProviderException, MessagingException {
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
        dataContent = cleanMessageContent(dataContent);

        return createMessageSummary(msg, dataContent);
    }

    private String createMessageSummary(Message msg, String dataContent) throws MessagingException {
        StringBuilder abstractMsg = new StringBuilder();
        abstractMsg.append(dataContent).append("\n");
        return abstractMsg.toString();
    }

    private String cleanMessageContent(String dataContent) {
        StringCleaner cleaner = new QuoteMealCleaner();

        return cleaner.clean(dataContent);
    }
    
    public static List<Message> getEmailsFromServer(Store store) throws NoSuchProviderException, MessagingException {

        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        int end = inbox.getMessageCount();
        int start = 1;
        return Arrays.asList(inbox.getMessages(start, end));
    }
}

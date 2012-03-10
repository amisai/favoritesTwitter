package org.okiju.pir.extractor;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

import org.okiju.pir.model.Entry;
import org.okiju.pir.util.PropertyHelper;
import org.testng.annotations.Test;

public class QuoteEmailExtractorTest {

     @Test(groups = "functest")
    public void shouldGetMessages() {
        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);
        Extractor generator = new QuoteEmailExtractor(props);
        Set<Entry> messages = generator.extract();
        assertNotNull(messages, "no messages are retrieved");
        assertTrue(messages.size() > 0, "expected more messages");

        for (Entry entry : messages) {
            System.out.println("url:" + entry.getUrl() + ".text:" + entry.getText());
        }
    }

//    @Test(groups = "functest")
    public void shouldGetQuoteMessages() {
        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);

        QuoteEmailExtractor generator = new QuoteEmailExtractor(props);
        
        Store store = null;
        try {
            store = generator.openStore();
            List<Message> messages = QuoteEmailExtractor.getEmailsFromServer(store);
            assertNotNull(messages, "no messages are retrieved");
            assertTrue(messages.size() > 0, "expected more messages");

            for (Message entry : messages) {
                System.out.println("entry:" + entry.getSubject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (store != null) {
                try {
                    store.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

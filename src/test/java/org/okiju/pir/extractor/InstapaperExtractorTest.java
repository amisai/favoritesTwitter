package org.okiju.pir.extractor;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Properties;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.okiju.pir.util.PropertyHelper;
import org.testng.annotations.Test;

public class InstapaperExtractorTest {

    @Test(groups = "functest")
    public void shouldGet2DoMessages() {
        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);
        Extractor generator = new InstapaperExtractor(props, "Para hacer", false, "10");
        Set<Entry> messages = generator.extract();
        assertNotNull(messages, "no messages are retrieved");
        assertTrue(messages.size() > 0, "expected more messages");

        for (Entry entry : messages) {
            System.out.println("url:" + entry.getUrl() + ".text:" + entry.getText());
        }
    }

    @Test(groups = "functest")
    public void shouldGetQuotesMessages() {
        String path = "./target/test-classes/";
        
        Properties props = PropertyHelper.loadProperties(path);
        Extractor generator = new InstapaperExtractor(props, "Citas", false, "10");
        Set<Entry> messages = generator.extract();
        assertNotNull(messages, "no messages are retrieved");
        assertTrue(messages.size() > 0, "expected more messages");
        
        for (Entry entry : messages) {
            System.out.println("url:" + entry.getUrl() + ".text:" + entry.getText());
        }
    }
}

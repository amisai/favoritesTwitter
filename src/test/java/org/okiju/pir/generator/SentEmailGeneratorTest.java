package org.okiju.pir.generator;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Properties;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.okiju.pir.util.PropertyHelper;
import org.testng.annotations.Test;

public class SentEmailGeneratorTest {

    @Test(groups = "functest")
    public void shouldGetMessages() {
        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);
        Generator generator = new SentEmailGenerator(props);
        Set<Entry> messages = generator.generate();
        assertNotNull(messages, "no messages are retrieved");
        assertTrue(messages.size() > 0, "I expected more messages");
    }
}

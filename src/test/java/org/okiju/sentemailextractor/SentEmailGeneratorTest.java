package org.okiju.sentemailextractor;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.okiju.favoritestwitter.Generator;
import org.okiju.favoritestwitter.cli.PropertyHelper;
import org.testng.annotations.Test;

public class SentEmailGeneratorTest {

    @Test(groups = "functest")
    public void shouldGetMessages() {
        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);
        Generator generator = new SentEmailGenerator(props);
        List<String> messages = generator.generate();
        assertNotNull(messages, "no messages are retrieved");
        assertTrue(messages.size() > 0, "I expected more messages");
    }
}
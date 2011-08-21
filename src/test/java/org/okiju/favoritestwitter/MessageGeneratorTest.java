package org.okiju.favoritestwitter;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class MessageGeneratorTest {
    @Test
    public void shouldUseEmptyMessageWhenNoDataIsGiven() {
        List<String> data = new ArrayList<String>();
        String expected = "hoy";

        String message = MessageGenerator.generateMessage(data);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }
    @Test
    public void shouldUseFullMessageWhenDataIsGiven() {
        List<String> data = new ArrayList<String>();
        data.add("cita 1");
        data.add("cita 2");
        data.add("cita 3");
        data.add("cita 4");
        data.add("cita 5");
        String expected = "cita 4";

        String message = MessageGenerator.generateMessage(data);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
        
    }
}



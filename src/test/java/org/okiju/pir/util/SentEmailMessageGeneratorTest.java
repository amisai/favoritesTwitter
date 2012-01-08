package org.okiju.pir.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.okiju.pir.model.TemplateInfo;
import org.testng.annotations.Test;

public class SentEmailMessageGeneratorTest {
    @Test
    public void shouldUseEmptyMessageWhenNoMapIsGiven() {
        String expected = "";

        Map<String, Set<String>> data = null;
        String message = MessageGenerator.generateMessage(data, TemplateInfo.sentEmail);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }

    @Test
    public void shouldUseEmptyMessageWhenNoDataIsGiven() {
        Set<String> info = new HashSet<String>();
        String expected = "hoy";

        Map<String, Set<String>> data = new HashMap<String, Set<String>>();
        data.put("data", info);
        String message = MessageGenerator.generateMessage(data, TemplateInfo.sentEmail);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }

    @Test
    public void shouldUseFullMessageWhenDataIsGiven() {
        Set<String> info1 = new TreeSet<String>();
        Set<String> info2 = new TreeSet<String>();
        info1.add("cita 1");
        info1.add("cita 2");
        info1.add("cita 3");
        info2.add("cita 4");
        info2.add("cita 5");
        String expected = "Resumen de correos auto-enviados:\n\ncita 1\ncita 2\ncita 3\n\nPara hacer:\ncita 4\ncita 5\n";

        Map<String, Set<String>> data = new HashMap<String, Set<String>>();
        data.put("data", info1);
        data.put("data2Do", info2);
        String message = MessageGenerator.generateMessage(data, TemplateInfo.sentEmail);
        assertNotNull(message, "no message is generated");
        
        assertEquals(message, expected, "expected message is not generated");
    }
}

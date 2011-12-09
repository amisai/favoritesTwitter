package org.okiju.pir.util;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

public class MessageGeneratorTest {
    @Test
    public void shouldUseEmptyMessageWhenNoMapIsGiven() {
        String expected = "";

        Map<String, List<String>> data = null;
        String message = MessageGenerator.generateMessage(data, "emptyTemplateEmailRetriever.vtl",
                "contentTemplateEmailRetriever.vtl");
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }

    @Test
    public void shouldUseEmptyMessageWhenNoDataIsGiven() {
        List<String> info= new ArrayList<String>();
        String expected = "hoy";
        
        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("data", info);
        String message = MessageGenerator.generateMessage(data, "emptyTemplateEmailRetriever.vtl",
        "contentTemplateEmailRetriever.vtl");
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }
    
    @Test
    public void shouldUseFullMessageWhenDataIsGiven() {
        List<String> info = new ArrayList<String>();
        info.add("cita 1");
        info.add("cita 2");
        info.add("cita 3");
        info.add("cita 4");
        info.add("cita 5");
        String expected = "cita 4";

        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("data", info);
        String message = MessageGenerator.generateMessage(data, "emptyTemplateEmailRetriever.vtl",
                "contentTemplateEmailRetriever.vtl");
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");

    }
}

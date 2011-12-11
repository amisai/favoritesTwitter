package org.okiju.pir.util;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.okiju.pir.model.TemplateInfo;
import org.testng.annotations.Test;

public class InstapaperMessageGeneratorTest {
    @Test
    public void shouldUseEmptyMessageWhenNoMapIsGiven() {
        String expected = "";

        Map<String, List<String>> data = null;
        String message = MessageGenerator.generateMessage(data, TemplateInfo.instapaper);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }

    @Test
    public void shouldUseEmptyMessageWhenNoDataIsGiven() {
        List<String> info= new ArrayList<String>();
        String expected = "hoy";
        
        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("data", info);
        String message = MessageGenerator.generateMessage(data, TemplateInfo.instapaper);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }
    
    @Test
    public void shouldUseFullMessageWhenDataIsGiven() {
        List<String> info1 = new ArrayList<String>();
        List<String> info2 = new ArrayList<String>();
        info1.add("cita 1");
        info1.add("cita 2");
        info2.add("cita 3");
        info2.add("cita 4");
        info2.add("cita 5");
        String expected = "Mostrando las URLs de instapaper:\n\nCitas:\ncita 3\ncita 4\ncita 5\n\nPara hacer\ncita 1\ncita 2\n";

        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("data2Do", info1);
        data.put("dataQuote", info2);
        String message = MessageGenerator.generateMessage(data, TemplateInfo.instapaper);
        assertNotNull(message, "no message is generated");
        assertEquals(message, expected, "expected message is not generated:" + message + ":");

    }
}

package org.okiju.pir.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.okiju.pir.model.TemplateInfo;
import org.testng.annotations.Test;

public class DailyMessageGeneratorTest {
    @Test
    public void shouldUseEmptyMessageWhenNoMapIsGiven() {
        String expected = "";

        Map<String, List<String>> data = null;
        String message = MessageGenerator.generateMessage(data, TemplateInfo.dailyTemplate);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }
    
    @Test
    public void shouldUseEmptyMessageWhenNoDataIsGiven() {
        List<String> info = new ArrayList<String>();
        String expected = "hoy";

        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("data", info);
        String message = MessageGenerator.generateMessage(data, TemplateInfo.dailyTemplate);
        assertNotNull(message, "no message is generated");
        assertTrue(message.indexOf(expected) != -1, "expected message is not generated:" + message + ":");
    }

    @Test
    public void shouldUseFullMessageWhenDataIsGiven() {
        List<String> info1 = new ArrayList<String>();
        List<String> info2 = new ArrayList<String>();
        List<String> info3 = new ArrayList<String>();
        info1.add("cita 2");
        info1.add("cita 1");
 
        info2.add("cita 3");
        info3.add("cita 4");
        info3.add("cita 5");
        String expected = "Mostrando las entradas de hoy (2 entrada(s), 1 cita(s) de correos y 2 cita(s) en Instapaper):\n\ncita 2\ncita 1\n\nCitas:\ncita 3\ncita 4\ncita 5\n";

        Map<String, List<String>> data = new HashMap<String, List<String>>();
        data.put("data", info1);
        data.put("dataQuotes", info2);
        data.put("dataQuoteInstapaper", info3);
        
        String message = MessageGenerator.generateMessage(data, TemplateInfo.dailyTemplate);
        assertNotNull(message, "no message is generated");
        
//        for (int i = 0; i < Math.min(message.length(), expected.length()); i++) {
//            System.out.println(message.charAt(i) + " - " + expected.charAt(i));
//        }
        assertEquals(message, expected, "expected message is not generated:" + message.length() + "-" + expected.length());
    }
}

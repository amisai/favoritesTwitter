package org.okiju.pir.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class StringCleanerTest {
    @Test
    public void shouldRemoveStandardText() {
        String input = "\n Espero que todo vaya bien \n Enviado desde mi iPad\n \t \nUn saludo, \nAbel";

        String expected = "\n Espero que todo vaya bien \n";
        StringCleaner cleaner = new StringCleaner();

        cleaner.add("Enviado desde mi iPad");
        cleaner.add("Un saludo,");
        cleaner.add("Abel");
        String result = cleaner.clean(input);
        assertNotNull(result, "no valid result");
        assertEquals(result, expected, "no expected result." + result.length() + "-" + expected.length());
    }

    @Test
    public void shouldRemoveStandardTextMoreEasier() {
        String input = "\n Espero que todo vaya bien \n Enviado desde mi iPad\n \t \nUn saludo, \nAbel";

        String expected = "\n Espero que todo vaya bien \n";
        StringCleaner cleaner = new StandardStringCleaner();

        String result = cleaner.clean(input);
        assertNotNull(result, "no valid result");
        assertEquals(result, expected, "no expected result." + result.length() + "-" + expected.length());
    }

    @Test
    public void shouldRemoveRepeatedCR() {
        String input = "\n \n \n";
        String expected = "\n";
        StringCleaner cleaner = new StringCleaner();

        String result = cleaner.clean(input);
        assertNotNull(result, "no valid result");
        assertEquals(result, expected, "no expected result." + result.length() + "-" + expected.length());
    }

    @Test
    public void shouldRemoveWhitespaces() {
        String input = "\n     uno    \t dos \n tres \n";
        String expected = "\n uno dos \n tres \n";
        StringCleaner cleaner = new StringCleaner();

        String result = cleaner.clean(input);
        assertNotNull(result, "no valid result");
        assertEquals(result, expected, "no expected result." + result.length() + "-" + expected.length());
    }

    @Test
    public void shouldRemoveEmptyLines() {
        String input = "\n     uno    \t dos \n tres \n    \n     \t     \n";
        String expected = "\n uno dos \n tres \n";
        StringCleaner cleaner = new StringCleaner();

        String result = cleaner.clean(input);
        assertNotNull(result, "no valid result");
        assertEquals(result, expected, "no expected result." + result.length() + " - " + expected.length());
    }

}


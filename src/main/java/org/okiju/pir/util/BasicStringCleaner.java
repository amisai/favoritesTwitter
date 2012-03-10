package org.okiju.pir.util;

import java.util.ArrayList;
import java.util.List;

public class BasicStringCleaner implements StringCleaner{
    List<String> toRemove;

    public BasicStringCleaner() {
        toRemove = new ArrayList<String>();
    }

    public String clean(String input) {
        String result = input;

        for (String string : toRemove) {
            result = result.replace(string, "");
        }
        result = result.replaceAll("\t", " ");
        result = removeAdjacentStrings(result, " ");
        result = substituteRepeatedStrings(result, "\n \n", "\n");
        result = removeAdjacentStrings(result, "\n");

        return result;
    }

    public void add(String string) {
        toRemove.add(string);
    }

    private static String substituteRepeatedStrings(String result, String target, String substitution) {
        int length = 0;
        do {
            length = result.length();
            result = result.replaceAll(target, substitution);
        } while (length != result.length());
        return result;

    }

    private static String removeAdjacentStrings(String result, String target) {
        return substituteRepeatedStrings(result, target + target, target);
    }
}
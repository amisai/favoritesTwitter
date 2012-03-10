package org.okiju.pir.util;

public class QuoteMealCleaner implements StringCleaner {

    @Override
    public String clean(String input) {
        StringBuilder result = new StringBuilder();

        String[] lines = input.split("\n");
        int index = 0;
        
        while (lines[index++].trim().length() != 0);
        while (lines[index++].trim().length() == 0);
        int start = index - 1;

        while (lines[index++].indexOf("---") == -1);
        int end = index - 1;

        for (int i = start; i < end; i++) {
            result.append(lines[i].trim()).append(" ");
        }
        return result.toString();
    }
}
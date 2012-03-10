package org.okiju.pir.model;

import java.util.Date;

public class Entry implements Comparable<Entry> {
    private String text;
    private Date date;
    private String url;
    private static final int FIRST_OTHER = 1;
    private static final int FIRST_THIS = -1;

    public Entry(String text, String url, Date date) {
        super();
        this.text = text;
        this.url = url;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
    
    public Date getDate() {
        return date;
    }

    public String toString() {
        String result = text;
        if (url != null && !url.trim().isEmpty()) {
            result = text + ":" + url + " ";
        }
        return result;
    }

    @Override
    public int compareTo(Entry other) {

        int result = FIRST_OTHER;
        boolean thisQuote = text.indexOf("\"") != -1 || text.indexOf("'") != -1;
        boolean otherQuote = other.text.indexOf("\"") != -1 || other.text.indexOf("'") != -1;

        boolean thisStartsWithQuote = text.indexOf("\"") == 0 || text.indexOf("'") == 0;
        boolean otherStartsWithQuote = other.text.indexOf("\"") == 0 || other.text.indexOf("'") == 0;

        boolean thisHasUrl = text.indexOf("http://") != -1;
        boolean otherHasUrl = other.text.indexOf("http://") != -1;

        if (!thisHasUrl) {
            if (otherHasUrl) {
                result = FIRST_THIS;
            } else
                result = compareStrings(other, thisQuote, otherQuote, thisStartsWithQuote, otherStartsWithQuote);
        } else {
            if (!otherHasUrl) {
                result = FIRST_OTHER;
            } else
                result = compareStrings(other, thisQuote, otherQuote, thisStartsWithQuote, otherStartsWithQuote);
        }
        return result;
    }

    private int compareStrings(Entry other, boolean thisQuote, boolean otherQuote, boolean thisStartsWithQuote,
            boolean otherStartsWithQuote) {
        int result;
        if (!thisQuote) {
            if (!otherQuote) {
                result = text.compareTo(other.text);
            } else {
                result = FIRST_THIS;
            }
        } else if (!otherQuote) {
            result = FIRST_OTHER;
        } else if (thisStartsWithQuote) {
            if (!otherStartsWithQuote) {
                result = FIRST_OTHER;
            } else {
                result = text.compareTo(other.text);
            }
        } else {
            if (otherStartsWithQuote) {
                result = FIRST_THIS;
            } else {
                result = text.compareTo(other.text);
            }
        }
        return result;
    }
}

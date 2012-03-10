package org.okiju.pir.filter;

import java.util.Date;

import org.okiju.pir.model.Entry;
import org.okiju.pir.util.DateHelper;

public class OlderThanAWeekFilter implements Filter {
    public boolean accept(Object object) {
        boolean result = false;

        if (object instanceof Entry) {
            Entry entry = (Entry) object;
            if (entry != null) {
                Date date = entry.getDate();
                if (date != null) {
                    result = !DateHelper.isDateAWeekBefore(date);
                }
            }
        }
        return result;
    }

}
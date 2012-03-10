package org.okiju.pir.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    public static boolean isDateAWeekBefore(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Calendar calDate = new GregorianCalendar();
        calDate.setTime(date);
        return calDate.before(cal);
    }
}

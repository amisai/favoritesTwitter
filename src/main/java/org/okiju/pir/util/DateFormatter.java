package org.okiju.pir.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String DATE_FORMAT = "dd/MM/yyyy";

    public static String formatToday() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date());
    }
}

package org.okiju.pir.filter;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.okiju.pir.model.Entry;
import org.testng.annotations.Test;

public class OlderThanAWeekFilterTest {

    @Test
    public void ShouldRejectNullEntry() {
        Filter filter = new OlderThanAWeekFilter();
        assertFalse(filter.accept(null), "null entry is accepted");
        assertFalse(filter.accept(new Entry("text1", "", null)), "null date is accepted");

    }

    @Test
    public void ShouldAcceptAnCurrentEntry() {
        Filter filter = new OlderThanAWeekFilter();
        assertTrue(filter.accept(new Entry("text2", "", new Date())), "new entry is rejected");
    }
    @Test
    public void shouldRejectAnOlderEntry() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);

        Filter filter = new OlderThanAWeekFilter();
        assertFalse(filter.accept(new Entry("text3", "", calendar.getTime())), "new entry is accepted");
             
    }
}


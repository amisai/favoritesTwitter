package org.okiju.pir.generator;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.testng.annotations.Test;

public class GeneratorTest {
    @Test
    public void shouldFilterEntriesByDate() {
        Set<Entry> entries = new HashSet<Entry>();
        entries.add(new Entry("entry1", "", null));
        entries.add(new Entry("entry2", "", new Date()));

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        entries.add(new Entry("entry3", "", calendar.getTime()));

        Set<Entry> result = Generator.filterEntriesByDate(entries);
        assertNotNull(result, "no entries are returned");
        assertEquals(result.size(), 2, "incorrect number of entries");
    }

    @Test
    public void shouldFilterEmptyEntries() {
        Set<Entry> entries = new HashSet<Entry>();
        entries.add(new Entry("entry1", "", null));
        entries.add(new Entry("entry2", "", null));
        entries.add(new Entry(" ", "", null));
        entries.add(new Entry(" \t ", "", null));
        entries.add(new Entry("entry3", "", null));
        entries.add(new Entry("", "", null));
        
        Set<Entry> result = Generator.filterEmptyEntries(entries);
        assertNotNull(result, "no entries are returned");
        assertEquals(result.size(), 3, "incorrect number of entries");
    }
    
    @Test
    public void shouldSortEntries() {
        Set<Entry> entries = new HashSet<Entry>();

        entries.add(new Entry("entry1", "", null));
        entries.add(new Entry("en\"t\"ry2", "", null));
        entries.add(new Entry("entry3http://url", "", null));
        entries.add(new Entry("en\"tr\"y4http://url", "", null));
        entries.add(new Entry("\"entr\"y5http://url", "", null));
        entries.add(new Entry("\"ent\"ry6", "", null));
        entries.add(new Entry("entry7", "", null));

        Set<Entry> result = Generator.sort(entries);
        assertNotNull(result, "no entries are returned");
        assertEquals(result.size(), 7, "incorrect number of entries");

        System.out.println(result);
        Iterator<Entry> it = result.iterator();
        Entry entry = it.next();
        assertEquals(entry.getText(), "entry1", "not expected entry");
        entry = it.next();
        assertEquals(entry.getText(), "entry7", "not expected entry");
        entry = it.next();
        assertEquals(entry.getText(), "en\"t\"ry2", "not expected entry");
        entry = it.next();
        assertEquals(entry.getText(), "\"ent\"ry6", "not expected entry");
        entry = it.next();
        assertEquals(entry.getText(), "entry3http://url", "not expected entry");
        entry = it.next();
        assertEquals(entry.getText(), "en\"tr\"y4http://url", "not expected entry");
        entry = it.next();
        assertEquals(entry.getText(), "\"entr\"y5http://url", "not expected entry");
        
        List<String> result2 = Generator.asStringList(result);
        assertNotNull(result2, "no entries are returned");
        assertEquals(result2.size(), 7, "incorrect number of entries");
        
        System.out.println(result2);
        Iterator<String> it2 = result2.iterator();
        String entry2 = it2.next();
        assertEquals(entry2, "entry1", "not expected entry");
        entry2 = it2.next();
        assertEquals(entry2, "entry7", "not expected entry");
        entry2 = it2.next();
        assertEquals(entry2, "en\"t\"ry2", "not expected entry");
        entry2 = it2.next();
        assertEquals(entry2, "\"ent\"ry6", "not expected entry");
        entry2 = it2.next();
        assertEquals(entry2, "entry3http://url", "not expected entry");
        entry2 = it2.next();
        assertEquals(entry2, "en\"tr\"y4http://url", "not expected entry");
        entry2 = it2.next();
        assertEquals(entry2, "\"entr\"y5http://url", "not expected entry");
    }
}

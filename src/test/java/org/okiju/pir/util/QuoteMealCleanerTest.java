package org.okiju.pir.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

public class QuoteMealCleanerTest {

    @Test
    public void shouldExtract() {
        String input = "-/\\/\\-----------------------------------------------------------------\n"
                + " \\ / QUOTEMEAL from HEARTLIGHT -- http://www.heartlight.org/\n"
                + "--\\/------------------------------------------------------------------\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "A man can't be always defending the truth; there must be a time\n"
                + "to feed on it.\n" + "\n" + "-- C. S. Lewis\n" + "\n" + "\n"
                + "|-- Will You Help Us Keep the Light Shining? --------------|\n" + "\n"
                + "For 15 years Heartlight.org has provided daily devotionals\n"
                + "and articles to promote positive Christian living. Will \n"
                + "you help us continue our ministry? To learn how you can \n" + "help visit: \n" + "\n"
                + "|-- http://www.heartlight.org/support/ --------------------| \n" + "\n" + "\n" + "\n"
                + "=========================== FEATURED PRODUCT =========================\n" + "\n"
                + "FACING THE GIANTS, DVD, by Alex Kendrick\n"
                + "Created by a church in Georgia to evangelize non-believers and\n"
                + "encourage believers, this inspiring story about a losing high school\n"
                + "football coach with a new game plan will have your whole town talking\n"
                + "about trusting God for the impossible!\n" + "http://shopping.heartlight.org/cgi-shl/link?257\n"
                + "\n" + "Find more great books, CDs and videos at the Heartlight store! With\n"
                + "each purchase you make, you're helping to support Heartlight's \n"
                + "ministry. Thanks SO MUCH for your help!\n" + "\n";

        String expected = "A man can't be always defending the truth; there must be a time to feed on it.  -- C. S. Lewis   ";

        StringCleaner cleaner = new QuoteMealCleaner();
        String result = cleaner.clean(input);
        assertNotNull(result, "no valid result");
        assertEquals(result, expected, "no expected result." + result.length() + "-" + expected.length());
    }
}

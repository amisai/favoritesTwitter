package org.okiju.pir.filter;

import static org.testng.Assert.*;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

public class SubjectEmailFilterTest {
    @Test
    public void shouldCheckMessagesSubject() throws MessagingException {
        Filter filter = new SubjectEmailFilter("Quotemeal");
        assertFalse(filter.accept(null), "null is accepted");
        assertFalse(filter.accept("Quotemeal: \"For the power ...\""), "string is accepted");

        Message msg = mock(Message.class);
 
        when(msg.getSubject()).thenReturn("Quotemeal: \"For the power ...\"");
        assertTrue(filter.accept(msg), "Message with proper subject is rejected");

        when(msg.getSubject()).thenReturn("[GOOS] Re: Encapsulating JQuery");
        assertFalse(filter.accept(msg), "Message without proper subject is accepted");
        
    }
}



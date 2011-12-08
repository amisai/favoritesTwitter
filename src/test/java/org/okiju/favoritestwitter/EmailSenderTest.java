package org.okiju.favoritestwitter;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.Properties;

import org.okiju.pir.util.EmailBean;
import org.okiju.pir.util.MailHelper;
import org.okiju.pir.util.PropertyHelper;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import org.testng.annotations.Test;

public class EmailSenderTest {
    @Test(groups = "functest")
    public void testSendEmail() {
        Wiser wiser = new Wiser();
        wiser.setPort(2500); // Default is 25
        MailHelper.setPort("2500");
        wiser.start();

        String textMessage = "textMessage";
        String subject = "subject";
        String recipient = "abel.cuenca@gmail.com";
        String from = "abel.cuenca@gmail.com";

        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);

        EmailBean emailBean = new EmailBean(props);
        emailBean.setRecipient(recipient);
        emailBean.setFrom(from);

        MailHelper.sendMessage(textMessage, subject, emailBean);

        List<WiserMessage> listMessages = wiser.getMessages();
        assertEquals(listMessages.size(), 1, "message is not sent");
        WiserMessage message = listMessages.get(0);
        assertEquals(message.getEnvelopeReceiver(), recipient, "receiver is not correct");
        assertEquals(message.getEnvelopeSender(), from, "sender is not correct");
        wiser.stop();
    }
}

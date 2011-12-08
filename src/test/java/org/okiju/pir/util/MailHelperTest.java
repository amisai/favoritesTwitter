package org.okiju.pir.util;

import static org.testng.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

import org.testng.annotations.Test;

/**
 * Unit test for sending email.
 */
public class MailHelperTest {

    @Test(groups = "functest")
    public void shouldSendAnEmail() {
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

        assertTrue(checkEmailAccount(subject, textMessage, emailBean.getUsername(), emailBean.getPassword()),
                "message is not found");
    }

    private boolean checkEmailAccount(String subject, String textMessage, final String username, final String password) {
        boolean result = false;
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        try {

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            Store store;
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            System.out.println("Total Message:" + folder.getMessageCount());
            System.out.println("Unread Message:" + folder.getUnreadMessageCount());

            Message[] messages = null;

            for (int i = 0; i < 5; i++) {
                messages = folder.search(new SubjectTerm(subject), folder.getMessages());
                if (messages.length == 0) {
                    try {
                        System.out.println("waiting a bit...");
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Message mail : messages) {
                if (!mail.isSet(Flags.Flag.SEEN)) {
                    result = true;
                    break;
                }
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return result;
    }
}

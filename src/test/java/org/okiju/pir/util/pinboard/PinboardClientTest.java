package org.okiju.pir.util.pinboard;

import static org.testng.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.okiju.pir.model.Posts;
import org.okiju.pir.util.PropertyHelper;
import org.testng.annotations.Test;

public class PinboardClientTest {
    @Test(groups = "functest")
    public void shouldGetBookmarksInfo() {
        String path = "./target/test-classes/";

        Properties props = PropertyHelper.loadProperties(path);
        int count = 25;
        PinboardClient client = new PinboardClient(props);
        Posts bookmarks = client.getBookmarks(count);
        assertNotNull(bookmarks, "no bookmarks are retrieved");
        assertEquals(bookmarks.getPosts().size(), count, "no correct number of posts");
    }

    @Test
    public void shouldMapAnswerFromServer() {
        String answer = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<posts user=\"amisai\" dt=\"2012-02-26T19:21:45Z\">\n"
                + "<post href=\"http://momentjs.com/\" time=\"2012-02-26T19:21:45Z\" description=\"moment.js\" extended=\"\" tag=\"javascript\" hash=\"258a3f2b96f3cb6e6059d776dc1c8ff6\"  shared=\"no\"  />\n"
                + "<post href=\"https://github.com/liebke/incanter\" time=\"2012-02-26T18:31:14Z\" description=\"Incanter\" extended=\"Clojure-based, R-like statistical computing and graphics environment for the JVM\" tag=\"clojure statistics\" hash=\"a46078f02854334da1140833b8adb0fd\"  shared=\"no\"  />\n"
                + "<post href=\"http://spde.technically.us/Spde.html\" time=\"2012-02-26T17:33:03Z\" description=\"spde\" extended=\"\" tag=\"scala\" hash=\"3646d284a2874e6abd321a77b9f1f81b\"  shared=\"no\"  />\n"
                + "<post href=\"https://github.com/secondmarket/mongeez\" time=\"2012-02-26T12:53:22Z\" description=\"Mongeez\" extended=\"MongoDB easy change management\" tag=\"bases_de_datos\" hash=\"a13eb0f342e62e983fa0863f820dc7d0\"  shared=\"no\"  />\n"
                + "</posts>\n";
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Posts.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Posts response = (Posts) unmarshaller.unmarshal(new ByteArrayInputStream(answer
                    .getBytes()));

            assertNotNull(response, "posts is null");
            assertEquals(response.getPosts().size(), 4, "no correct number of posts");
            System.out.println(response);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
package org.okiju.pir.pinboard;

import java.net.URI;
import java.util.Properties;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.okiju.pir.model.Posts;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class PinboardClient {

    public PinboardClient (Properties props) {
        username = props.getProperty("pinboard.username");
        password= props.getProperty("pinboard.password");
    }
    public Posts getBookmarks(int count) {
        Posts answers = null;
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.addFilter(new HTTPBasicAuthFilter(username, password));
        WebResource service = client.resource(getBaseURI()).path("posts").path("recent")
                .queryParam("count", Integer.toString(count));
        answers = service.accept(MediaType.TEXT_XML).get(Posts.class);
        return answers;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("https://@api.pinboard.in/v1/").build();
    }

    private String password;
    private String username;
}
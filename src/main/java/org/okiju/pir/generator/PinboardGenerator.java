package org.okiju.pir.generator;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.okiju.pir.model.Post;
import org.okiju.pir.model.Posts;
import org.okiju.pir.pinboard.PinboardClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PinboardGenerator implements Generator {

    private static transient Logger logger = LoggerFactory.getLogger(PinboardGenerator.class);
    private Properties properties;

    public PinboardGenerator(Properties props) {
        this.properties = props;
    }

    public Set<Entry> generate() {
        Set<Entry> result = new HashSet<Entry>();
        int count = 50;
        PinboardClient client = new PinboardClient(properties);
        Posts posts = client.getBookmarks(count);

        for (Post post : posts.getPosts()) {
            result.add(new Entry(post.getHref()));
        }

        return result;
    }
}

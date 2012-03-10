package org.okiju.pir.extractor;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.okiju.pir.model.Post;
import org.okiju.pir.model.Posts;
import org.okiju.pir.util.pinboard.PinboardClient;

public class PinboardExtractor implements Extractor {

//    private static transient Logger logger = LoggerFactory.getLogger(PinboardExtractor.class);
    private Properties properties;

    public PinboardExtractor(Properties props) {
        this.properties = props;
    }

    public Set<Entry> extract() {
        Set<Entry> result = new HashSet<Entry>();
        int count = 50;
        PinboardClient client = new PinboardClient(properties);
        Posts posts = client.getBookmarks(count);

        for (Post post : posts.getPosts()) {
            result.add(new Entry(post.getDescription(), post.getHref(), post.getTime()));
        }

        return result;
    }
}

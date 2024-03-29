package org.okiju.pir.extractor;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.okiju.pir.model.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.PropertyConfiguration;

public class TwitterExtractor implements Extractor {

    private static transient Logger logger = LoggerFactory.getLogger(TwitterExtractor.class);
    private Properties properties;
    private boolean destroyFavorites;

    public TwitterExtractor(Properties props, boolean destroyFavorites) {
        this.properties = props;
        this.destroyFavorites = destroyFavorites;
    }

    public Set<Entry> extract() {
        Set<Entry> result = new HashSet<Entry>();
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            Configuration conf = new PropertyConfiguration(properties);
            Twitter twitter = new TwitterFactory(conf).getInstance();
            int page = 1;
            int contador = 0;
            List<Status> statuses;
            do {
                statuses = twitter.getFavorites(page);
                for (Status status : statuses) {
                    String text = status.getText() + " @ " + status.getUser().getScreenName() + " @ "
                    + sdf.format(status.getCreatedAt());
                    result.add(new Entry(text, "", null));
                    if (destroyFavorites) {
                        twitter.destroyFavorite(status.getId());
                    }
                }
                contador++;
            } while (statuses.size() > 0 && contador < 20);
            logger.info("done.");

        } catch (TwitterException te) {
            logger.error("Failed to get favorites.", te);
        }
        return result;
    }
}

package org.okiju.favoritestwitter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterHelper {

    public static List<String> generateListTwits() {
        List<String> result = new ArrayList<String>();
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {

            Twitter twitter = new TwitterFactory().getInstance();
            int page = 1;
            int contador = 0;
            List<Status> statuses;
            do {
                statuses = twitter.getFavorites(page);
                for (Status status : statuses) {
                    result.add(status.getText() + " @ " + status.getUser().getScreenName() + " @ "
                            + sdf.format(status.getCreatedAt()));
                    twitter.destroyFavorite(status.getId());
                }
                contador++;
            } while (statuses.size() > 0 && contador < 20);
            System.out.println("done.");

        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get favorites: " + te.getMessage());
        }
        return result;
    }
}

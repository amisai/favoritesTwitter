package org.okiju.favoritestwitter.cli;

import java.util.List;
import java.util.Properties;

import org.okiju.favoritestwitter.Generator;
import org.okiju.favoritestwitter.TwitterGenerator;
import org.okiju.favoritestwitter.util.FileHelper;

/**
 * CLI of favorites extractor
 * 
 */
public class FavoritesExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("FavoritesExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        List<String> entries = generateEntries(new TwitterGenerator(props), "ficheroTwits");

        sendEmail(props, entries, "Favoritos del día ", "emptyTemplateFavoritesTwitter.vtl",
                "contentTemplateFavoritesTwitter.vtl");
    }
}

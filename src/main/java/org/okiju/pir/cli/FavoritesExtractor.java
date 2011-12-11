package org.okiju.pir.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.okiju.pir.generator.InstapaperGenerator;
import org.okiju.pir.generator.TwitterGenerator;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of favorites extractor.
 * 
 */
public class FavoritesExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("FavoritesExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        Map<String, List<String>> context = new HashMap<String, List<String>>();

        List<String> entries = generateEntries(new TwitterGenerator(props), "ficheroTwits");
        context.put("data", entries);

        List<String> entriesQuotes = generateEntries(new InstapaperGenerator(props, "Citas", false),
                "ficheroInstapaperQuotes");
        context.put("dataQuote", entriesQuotes);
        sendEmail(props, context, "Favoritos del día ", TemplateInfo.favoritesTwitter);
    }
}

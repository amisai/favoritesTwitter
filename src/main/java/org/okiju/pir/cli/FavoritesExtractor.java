package org.okiju.pir.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.okiju.pir.generator.TemplateInfo;
import org.okiju.pir.generator.TwitterGenerator;
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

        List<String> entries = generateEntries(new TwitterGenerator(props), "ficheroTwits");
        Map<String, List<String>> context = new HashMap<String, List<String>>();
        context.put("data", entries);
        sendEmail(props, context, "Favoritos del día ", TemplateInfo.favoritesTwitter);
    }
}

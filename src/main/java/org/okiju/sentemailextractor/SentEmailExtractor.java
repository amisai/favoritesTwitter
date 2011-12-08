package org.okiju.sentemailextractor;

import java.util.List;
import java.util.Properties;

import org.okiju.favoritestwitter.cli.BaseExtractor;
import org.okiju.favoritestwitter.cli.PropertyHelper;

/**
 * Hello world!
 * 
 */
public class SentEmailExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("SentEmailExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        List<String> entries = generateEntries(new SentEmailGenerator(props), "ficheroEmails");

        sendEmail(props, entries, "Emails de la semana ", "emptyTemplateEmailRetriever.vtl",
        "contentTemplateEmailRetriever.vtl");
    }
}

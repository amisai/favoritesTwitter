package org.okiju.pir.cli;

import java.util.List;
import java.util.Properties;

import org.okiju.pir.generator.SentEmailGenerator;
import org.okiju.pir.util.PropertyHelper;

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

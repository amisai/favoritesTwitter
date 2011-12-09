package org.okiju.pir.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.okiju.pir.generator.SentEmailGenerator;
import org.okiju.pir.generator.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of sent email extractor.
 * 
 */
public class SentEmailExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("SentEmailExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        List<String> entries = generateEntries(new SentEmailGenerator(props), "ficheroEmails");
        Map<String, List<String>> context = new HashMap<String, List<String>>();
        context.put("data", entries);
        sendEmail(props, context, "Emails de la semana ",TemplateInfo.sentEmail);
    }
}

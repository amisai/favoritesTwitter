package org.okiju.pir.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.okiju.pir.generator.InstapaperGenerator;
import org.okiju.pir.generator.SentEmailGenerator;
import org.okiju.pir.model.TemplateInfo;
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

        Map<String, Set<String>> context = new HashMap<String, Set<String>>();

        Set<String> entries = generateEntries(new SentEmailGenerator(props), "ficheroEmails");
        context.put("data", entries);

        Set<String> entries2Do = generateEntries(new InstapaperGenerator(props, "Para hacer", true, "10"),
                "ficheroInstapaper2Do");
        context.put("data2Do", entries2Do);
        
        sendEmail(props, context, "Emails de la semana ", TemplateInfo.sentEmail);
    }
}

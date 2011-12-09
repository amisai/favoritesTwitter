package org.okiju.pir.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.okiju.pir.generator.InstapaperGenerator;
import org.okiju.pir.generator.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of Instapaper extractor.
 * 
 */
public class FoldersInstapaperExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("FolderInstapaperExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        List<String> entriesQuotes = generateEntries(new InstapaperGenerator(props, "Citas", false),
                "ficheroInstapaperQuotes");
        List<String> entries2Do = generateEntries(new InstapaperGenerator(props, "Para hacer", false),
                "ficheroInstapaper2Do");

        Map<String, List<String>> context = new HashMap<String, List<String>>();
        context.put("dataQuote", entriesQuotes);
        context.put("data2Do", entries2Do);
        sendEmail(props, context, "Urls guardadas en Instapaper ", TemplateInfo.instapaper);
    }
}

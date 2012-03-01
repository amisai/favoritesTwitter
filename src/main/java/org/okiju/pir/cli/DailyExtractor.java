package org.okiju.pir.cli;

import java.util.Properties;

import org.okiju.pir.generator.InstapaperGenerator;
import org.okiju.pir.generator.TwitterGenerator;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of daily extractor.
 * 
 */
public class DailyExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("DailyExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);
        
        Extractor extractor = BuildExtractor.withProperties(props)
        .thatExecutesThisExtraction(new ExtractionBean("data", new TwitterGenerator(props), "ficheroTwits"))
        .and()
        .thatExecutesThisExtraction(new ExtractionBean("dataQuote", new InstapaperGenerator(props, "Citas", true),
        "ficheroInstapaperQuotes"))
        .andSendAnEmailWithThisSubjectAndThisTemplate("Resumen diario ", TemplateInfo.favoritesTwitter)
        .build();
        
        extractor.execute();
        
//        List<ExtractionBean> extractions = new ArrayList<ExtractionBean>();
//
//        extractions.add(new ExtractionBean("data", new TwitterGenerator(props), "ficheroTwits"));
//
//        extractions.add(new ExtractionBean("dataQuote", new InstapaperGenerator(props, "Citas", true),
//        "ficheroInstapaperQuotes"));
//        
//        doExtraction(props, extractions, "Emails de la semana ", TemplateInfo.sentEmail);
    }
}

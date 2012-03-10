package org.okiju.pir.cli;

import java.util.Properties;

import org.okiju.pir.extractor.ExtractionBean;
import org.okiju.pir.extractor.InstapaperExtractor;
import org.okiju.pir.extractor.QuoteEmailExtractor;
import org.okiju.pir.extractor.TwitterExtractor;
import org.okiju.pir.generator.BuildGenerator;
import org.okiju.pir.generator.Generator;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of daily generator.
 * 
 */
public class DailyGenerator {

    public static void main(String[] args) {
        BaseCLI.checkArgs("DailyGenerator", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        boolean test = props.containsKey("testing");
        System.out.println("test?" + test);
        boolean testing = test;
        
        ExtractionBean twitterFavorites = new ExtractionBean("data", new TwitterExtractor(props, !testing),
                "ficheroTwits");
        ExtractionBean quotesEmail = new ExtractionBean("dataQuotes", new QuoteEmailExtractor(props),
        "ficheroQuotes");
        ExtractionBean quotesInstapaper = new ExtractionBean("dataQuoteInstapaper", new InstapaperExtractor(props, "Citas",
                !testing), "ficheroInstapaperQuotes");

        Generator generator = BuildGenerator.withProperties(props)
                .thatExecutesThisExtraction(twitterFavorites)
                .and().thatExecutesThisExtraction(quotesEmail)
                .and().thatExecutesThisExtraction(quotesInstapaper)
                .andSendAnEmailWithThisSubjectAndThisTemplate("Resumen diario del día ", TemplateInfo.dailyTemplate)
                .build();

        generator.execute();
    }
}

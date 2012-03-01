package org.okiju.pir.cli;

import java.util.Properties;

import org.okiju.pir.generator.InstapaperGenerator;
import org.okiju.pir.generator.PinboardGenerator;
import org.okiju.pir.generator.SentEmailGenerator;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of sent email extractor.
 * 
 */
public class WeeklyExtractor extends BaseExtractor {

    public static void main(String[] args) {
        checkArgs("WeeklyExtractor", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        Extractor extractor = BuildExtractor
                .withProperties(props)
                .thatExecutesThisExtraction(new ExtractionBean("data", new SentEmailGenerator(props), "ficheroEmails"))
                .and()
                .thatExecutesThisExtraction(
                        new ExtractionBean("dataPinboard", new PinboardGenerator(props), "ficheroPinboard"))
                .and()
                .thatExecutesThisExtraction(
                        new ExtractionBean("data2Do", new InstapaperGenerator(props, "Para hacer", true, "10"),
                                "ficheroInstapaper2Do"))
                .andSendAnEmailWithThisSubjectAndThisTemplate("Emails de la semana ", TemplateInfo.sentEmail).build();

        extractor.execute();
        //
        // List<ExtractionBean> extractions = new ArrayList<ExtractionBean>();
        //
        // extractions.add(new ExtractionBean("data", new
        // SentEmailGenerator(props), "ficheroEmails"));
        //
        // extractions.add(new ExtractionBean("dataPinboard", new
        // PinboardGenerator(props), "ficheroPinboard"));
        //
        // extractions.add(new ExtractionBean("data2Do", new
        // InstapaperGenerator(props, "Para hacer", true, "10"),
        // "ficheroInstapaper2Do"));
        //
        // doExtraction(props, extractions, "Emails de la semana ",
        // TemplateInfo.sentEmail);
    }
}

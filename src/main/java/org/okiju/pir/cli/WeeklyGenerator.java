package org.okiju.pir.cli;

import java.util.Properties;

import org.okiju.pir.extractor.ExtractionBean;
import org.okiju.pir.extractor.InstapaperExtractor;
import org.okiju.pir.extractor.PinboardExtractor;
import org.okiju.pir.extractor.SentEmailExtractor;
import org.okiju.pir.generator.BuildGenerator;
import org.okiju.pir.generator.Generator;
import org.okiju.pir.model.TemplateInfo;
import org.okiju.pir.util.PropertyHelper;

/**
 * CLI of weekly generator.
 * 
 */
public class WeeklyGenerator {

    public static void main(String[] args) {
        BaseCLI.checkArgs("WeeklyGenerator", args);

        String path = args[0];
        Properties props = PropertyHelper.loadProperties(path);

        boolean test = props.containsKey("testing");
        System.out.println("test?" + test);
        boolean testing = test;

        ExtractionBean sentEmail = new ExtractionBean("data", new SentEmailExtractor(props), "ficheroEmails");
        ExtractionBean urlsPinboard = new ExtractionBean("dataPinboard", new PinboardExtractor(props),
                "ficheroPinboard");
        ExtractionBean todoInstapaper = new ExtractionBean("data2Do", new InstapaperExtractor(props, "Para hacer",
                !testing, "10"), "ficheroInstapaper2Do");

        Generator generator = BuildGenerator.withProperties(props)
                .thatExecutesThisExtraction(sentEmail)
                .and().thatExecutesThisExtraction(urlsPinboard)
                .and().thatExecutesThisExtraction(todoInstapaper)
                .andSendAnEmailWithThisSubjectAndThisTemplate("Resumen de la semana ", TemplateInfo.weeklyTemplate)
                .build();

        generator.execute();
    }
}

package org.okiju.pir.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.okiju.pir.extractor.ExtractionBean;
import org.okiju.pir.model.TemplateInfo;

public class BuildGenerator {
    private static Properties props;
    private static TemplateInfo template;
    private static String subject;
    private static List<ExtractionBean> extractions;

    private BuildGenerator() {
        BuildGenerator.extractions = new ArrayList<ExtractionBean>();
    }

    public Generator build() {
        return new Generator(props, extractions, subject, template);
    }

    public BuildGenerator and() {
        return this;
    }

    public BuildGenerator thatExecutesThisExtraction(ExtractionBean extractionBean) {
        BuildGenerator.extractions.add(extractionBean);
        return this;
    }

    public static BuildGenerator withProperties(Properties props) {
        BuildGenerator.props = props;
        return new BuildGenerator();
    }

    public BuildGenerator andSendAnEmailWithThisSubjectAndThisTemplate(String string, TemplateInfo sentemail) {
        BuildGenerator.subject = string;
        BuildGenerator.template = sentemail;
        return this;
    }
}

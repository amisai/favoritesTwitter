package org.okiju.pir.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.okiju.pir.model.TemplateInfo;

public class BuildExtractor {
    private static Properties props;
    private static TemplateInfo template;
    private static String subject;
    private static List<ExtractionBean> extractions;

    private BuildExtractor() {
        BuildExtractor.extractions = new ArrayList<ExtractionBean>();
    }

    public Extractor build() {
        return new Extractor(props, extractions, subject, template);
    }

    public BuildExtractor and() {
        return this;
    }

    public BuildExtractor thatExecutesThisExtraction(ExtractionBean extractionBean) {
        BuildExtractor.extractions.add(extractionBean);
        return this;
    }

    public static BuildExtractor withProperties(Properties props) {
        BuildExtractor.props = props;
        return new BuildExtractor();
    }

    public BuildExtractor andSendAnEmailWithThisSubjectAndThisTemplate(String string, TemplateInfo sentemail) {
        BuildExtractor.subject = string;
        BuildExtractor.template = sentemail;
        return this;
    }
}

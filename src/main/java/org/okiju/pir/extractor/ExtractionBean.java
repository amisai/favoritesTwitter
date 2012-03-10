package org.okiju.pir.extractor;


public class ExtractionBean {
    private String context;
    private Extractor generator;
    private String filename;

    public ExtractionBean(String context, Extractor generator, String filename) {
        super();
        this.context = context;
        this.generator = generator;
        this.filename = filename;
    }

    public String getContext() {
        return context;
    }

    public Extractor getGenerator() {
        return generator;
    }

    public String getFilename() {
        return filename;
    }
}
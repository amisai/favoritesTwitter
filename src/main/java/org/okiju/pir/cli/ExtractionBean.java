package org.okiju.pir.cli;

import org.okiju.pir.generator.Generator;

public class ExtractionBean {
    private String context;
    private Generator generator;
    private String filename;

    public ExtractionBean(String context, Generator generator, String filename) {
        super();
        this.context = context;
        this.generator = generator;
        this.filename = filename;
    }

    public String getContext() {
        return context;
    }

    public Generator getGenerator() {
        return generator;
    }

    public String getFilename() {
        return filename;
    }
}
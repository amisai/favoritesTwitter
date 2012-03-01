package org.okiju.pir.model;

public enum TemplateInfo {
    favoritesTwitter("emptyTemplateFavoritesTwitter.vtl", "contentTemplateFavoritesTwitter.vtl"), 
    sentEmail("emptyTemplateEmailRetriever.vtl", "contentTemplateEmailRetriever.vtl"), 
    instapaper("emptyTemplateInstapaper.vtl", "contentTemplateInstapaper.vtl");

    private String normalTemplate;
    private String emptyTemplate;

    private TemplateInfo(String emptyTemplate, String normalTemplate) {
        this.emptyTemplate = emptyTemplate;
        this.normalTemplate = normalTemplate;
    }

    public String getNormalTemplate() {
        return normalTemplate;
    }

    public String getEmptyTemplate() {
        return emptyTemplate;
    }
}

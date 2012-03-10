package org.okiju.pir.model;

public enum TemplateInfo {
    dailyTemplate("emptyDailyTemplate.vtl", "contentDailyTemplate.vtl"), 
    weeklyTemplate("emptyWeeklyTemplate.vtl", "contentWeeklyTemplate.vtl");

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

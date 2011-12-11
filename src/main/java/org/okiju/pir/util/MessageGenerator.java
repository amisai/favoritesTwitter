package org.okiju.pir.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.okiju.pir.model.TemplateInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageGenerator {

    private static transient Logger logger = LoggerFactory.getLogger(MessageGenerator.class);

    public static String generateMessage(Map<String, List<String>> data, TemplateInfo template) {
        String result = "";
        if (data != null) {
            VelocityContext context = new VelocityContext();
            boolean empty = transferInfo2Context(data, context);
            String templateFile = chooseTemplate(template, empty);
            result = mergeDataWithTemplate(result, context, templateFile);
        }
        return result;
    }

    private static boolean transferInfo2Context(Map<String, List<String>> data, VelocityContext context) {
        boolean empty = true;
        
        for (String key : data.keySet()) {
            List<String> list = data.get(key);
            if (!list.isEmpty()) {
                context.put(key, list);
                empty = false;
            }
        }
        return empty;
    }

    private static String chooseTemplate(TemplateInfo templates, boolean empty) {
        String templateFile = "";
        if (empty) {
            templateFile = templates.getEmptyTemplate();
        } else {
            templateFile = templates.getNormalTemplate();
        }
        return templateFile;
    }

    private static String mergeDataWithTemplate(String result, VelocityContext context, String templateFile) {
        Template template = findTemplate(templateFile);
        if (template != null) {
            Writer writer = new StringWriter();

            template.merge(context, writer);
            try {
                writer.flush();
                writer.close();
                result = writer.toString();
            } catch (IOException e) {
                logger.error("Error while merging templates", e);
            }
        }
        return result;
    }

    public static String generateMessage(List<String> dataQuotes, List<String> data2Do) {
        String result = "";
        VelocityContext context = new VelocityContext();
        context.put("dataQuote", dataQuotes);
        context.put("data2Do", data2Do);

        String templateFile = "";
        if (dataQuotes.isEmpty() && data2Do.isEmpty()) {
            templateFile = "emptyTemplateInstaper.vtl";
        } else {
            templateFile = "contentTemplateInstapaper.vtl";
        }
        Template template = findTemplate(templateFile);
        if (template != null) {
            Writer writer = new StringWriter();

            template.merge(context, writer);
            try {
                writer.flush();
                writer.close();
                result = writer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Template findTemplate(String name) {
        VelocityEngine engine = new VelocityEngine();
        Properties properties = new Properties();

        properties.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
        properties.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class",
                ClasspathResourceLoader.class.getName());
        engine.init(properties);
        // get template
        return engine.getTemplate(name);
    }
}
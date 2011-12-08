package org.okiju.pir.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageGenerator {

    private static transient Logger logger = LoggerFactory.getLogger(MessageGenerator.class);

    public static String generateMessage(List<String> data, String emptyTemplate, String contentTemplate) {
        String result = "";
        VelocityContext context = new VelocityContext();
        context.put("data", data);
        String templateFile = "";
        if (data.isEmpty()) {
            templateFile = emptyTemplate;
        } else {
            templateFile = contentTemplate;
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
                logger.error("Error while merging templates", e);
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
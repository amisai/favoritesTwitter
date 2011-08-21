package org.okiju.favoritestwitter;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class MessageGenerator {

    public static String generateMessage(List<String> data) {
        String result = "";
        VelocityContext context = new VelocityContext();
         context.put("data", data);
        String templateFile = "";
        if (data.isEmpty()) {
            templateFile ="emptyTemplate.vtl";
        } else {
            templateFile = "contentTemplate.vtl";
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
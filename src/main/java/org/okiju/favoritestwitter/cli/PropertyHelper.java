package org.okiju.favoritestwitter.cli;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyHelper {

    private static transient Logger logger = LoggerFactory.getLogger(PropertyHelper.class);

    public static Properties loadProperties(String path) {
        return loadProperties(path, propertiesFile);
    }

    public static Properties loadProperties(String path, String file) {
        Properties props = new Properties();
        try {

            props.load(new FileInputStream(path + "/" + file));
        } catch (IOException e) {
            logger.error("error while reading properties", e);
            System.exit(-1);
        }
        return props;
    }

    public static final String propertiesFile = "twitter4j.properties";
}

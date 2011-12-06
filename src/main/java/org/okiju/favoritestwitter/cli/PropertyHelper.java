package org.okiju.favoritestwitter.cli;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHelper {

    public static Properties loadProperties(String path) {
        return loadProperties(path, propertiesFile);
    }

    public static Properties loadProperties(String path, String file) {
        Properties props = new Properties();
        try {

            props.load(new FileInputStream(path + "/" + file));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return props;
    }

    public static final String propertiesFile = "twitter4j.properties";
}

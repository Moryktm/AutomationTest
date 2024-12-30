package org.example.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    public static String loadProperty(String propertyName) throws IOException {

        InputStream inputStream = new FileInputStream("src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties.getProperty(propertyName);
    }

    public static String loadXrayProperty(String propertyName) throws IOException {

        InputStream inputStream = new FileInputStream("src/main/resources/xray.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties.getProperty(propertyName);
    }

    public static String loadJiraProperty(String propertyName) throws IOException {

        InputStream inputStream = new FileInputStream("src/main/resources/jira.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties.getProperty(propertyName);
    }


}

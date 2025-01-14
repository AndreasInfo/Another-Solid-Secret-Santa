package de.amrim.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    private static final AppProperties INSTANCE = new AppProperties();

    private final Properties properties;

    private AppProperties(){
        String path = "./app.properties";

        properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("app.properties not found at " + path + "!");
        };
    }

    public static AppProperties getInstance() {
        return INSTANCE;
    }
    public String get(String property) {
        return properties.getProperty(property);
    }
}

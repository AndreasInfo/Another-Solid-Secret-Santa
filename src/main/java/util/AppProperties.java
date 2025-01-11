package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
    public static final AppProperties INSTANCE = new AppProperties();

    private Properties properties;

    private AppProperties(){
        init();
    }

    public void init() {
        String path = "./app.properties";

        properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("app.properties not found at " + path + "!");
        }
    }

    public String get(String property) {
        return properties.getProperty(property);
    }
}

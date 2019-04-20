package ru.spoddubnyak.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ConfigProperties {

    private static Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        URL props = ClassLoader.getSystemResource("threads.properties");
        try {
            PROPERTIES.load(props.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getProperty(String key) {
        return Integer.parseInt(PROPERTIES.getProperty(key));
    }
}

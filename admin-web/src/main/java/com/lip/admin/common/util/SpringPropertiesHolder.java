package com.lip.admin.common.util;

import java.io.IOException;
import java.util.Properties;


public class SpringPropertiesHolder {
    
    private static Properties properties = null;

    protected SpringPropertiesHolder() {
        super();
    }

    /**
     * @param defaults
     * @throws IOException
     */
    protected SpringPropertiesHolder(
            SpringPropertyPlaceholderConfigurer propertyPlaceholderConfigurer)
            throws IOException {
        properties = new Properties (propertyPlaceholderConfigurer.getProperties());
    }
    

    public static String getProperty (String key) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(key);
    }
    
    public static String getProperty (String key, String defaultValue) {
        if (properties == null) {
            return defaultValue;
        }
        return properties.getProperty(key, defaultValue);
    }

 
    public static void setProperty (String key, String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
    }
}
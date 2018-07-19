package com.warrior.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 *
 * @author panyi on 18-7-17.
 */
public final class PropsUtil {
    public static Properties loadProps(String configFile) {
        Properties properties = new Properties();
        InputStream inputStream = PropsUtil.class.getClassLoader().getResourceAsStream(configFile);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getString(Properties props, String propKey) {
        return props.getProperty(propKey);
    }

    public static String getString(Properties props, String propKey, String defaultValue) {
        return props.getProperty(propKey, defaultValue);
    }
}

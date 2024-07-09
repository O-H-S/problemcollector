package com.ohs.problemcollector;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectorConfig {
    static Properties properties;
    public static String get(String key){
        if(properties == null){
            Properties prop = new Properties();
            // config.properties 파일로부터 설정을 로드
            try (InputStream input = CollectorConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new IOException("File not found: " + "config.properties");
                }
                prop.load(input);
                resolveEnvVariables(prop);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            properties = prop;
        }
        return properties.getProperty(key);
    }

    private static void resolveEnvVariables(Properties prop) {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        for (String key : prop.stringPropertyNames()) {
            String value = prop.getProperty(key);
            Matcher matcher = pattern.matcher(value);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String envVar = matcher.group(1);
                String envValue = System.getenv(envVar);
                if (envValue == null) {
                    //prop.setProperty(key, null);
                    prop.remove(key);
                    break;
                } else {
                    matcher.appendReplacement(sb, Matcher.quoteReplacement(envValue));
                }
            }
            matcher.appendTail(sb);
            if (prop.getProperty(key) != null) {
                // Only set the property if it wasn't already set to null
                prop.setProperty(key, sb.toString());
            }
        }
    }
}

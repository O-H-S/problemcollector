package com.ohs.problemcollector;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CollectorConfig {
    static Properties properties;
    public static String get(String key){
        if(properties == null){
            Properties prop = new Properties();
            // config.properties 파일로부터 설정을 로드
            try (InputStream input = new FileInputStream("config.properties")) {
                prop.load(input);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            properties = prop;
        }
        return properties.getProperty(key);
    }
}

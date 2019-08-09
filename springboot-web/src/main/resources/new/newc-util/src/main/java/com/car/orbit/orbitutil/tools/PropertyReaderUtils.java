package com.car.orbit.orbitutil.tools;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Title: PropertyReaderUtils
 * @Package: com.car.orbit.orbitutil.tools
 * @Description:
 * @Author: monkjavaer
 * @Date: 2019/03/15 11:52
 * @Version: V1.0
 */
public class PropertyReaderUtils {

    /**
     * 属性值映射
     */
    private static Map<String, String> propertiesMap;

    //初始化属性列表
    static {
        propertiesMap = new HashMap<>();
        try {
            //初始化属性读取文件
            Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");

            //读取属性列表
            for (Object key : properties.keySet()) {
                String keyStr = key.toString();
                propertiesMap.put(keyStr, new String(properties.getProperty(keyStr).getBytes("ISO-8859-1"), "utf-8"));
            }
            //获取对应环境的配置文件名
            String activeProperties = properties.getProperty("spring.profiles.active");

            //初始化属性读取文件
            Properties propertiesActive = PropertiesLoaderUtils.loadAllProperties("application-" + activeProperties + ".properties");

            //读取属性列表
            for (Object keyActive : propertiesActive.keySet()) {
                String keyActives = keyActive.toString();
                propertiesMap.put(keyActives, new String(propertiesActive.getProperty(keyActives).getBytes("ISO-8859-1"), "utf-8"));
            }
        } catch (Exception e) {
            throw new RuntimeException("initialize properties-reader failed", e);
        }
    }

    /**
     * 获取配置属性值
     *
     * @param proKey 配置属性的键
     * @return String 配置属性的文本值
     */
    public static String getProValue(String proKey) {
        return propertiesMap.get(proKey);
    }
}

package com.base.springboot.utils;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: spring配置文件读取工具
 * @Author: monkjavaer
 * @Date: 2019/6/22 20:11
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
            //初始化属性读取文件solr.properties
            Properties properties = PropertiesLoaderUtils.loadAllProperties("solr.properties");

            //读取属性列表
            for (Object key : properties.keySet()) {
                String keyStr = key.toString();
                propertiesMap.put(keyStr, new String(properties.getProperty(keyStr).getBytes("ISO-8859-1"), "utf-8"));
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

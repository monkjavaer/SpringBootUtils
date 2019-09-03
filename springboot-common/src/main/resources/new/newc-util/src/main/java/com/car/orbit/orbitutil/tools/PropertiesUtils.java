package com.car.orbit.orbitutil.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Title: PropertiesUtils
 * @Package: com.car.orbit.orbitutil.tools
 * @Description: properties 文件操作工具
 * @Author: monkjavaer
 * @Data: 2019/3/12 10:13
 * @Version: V1.0
 */
public class PropertiesUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

    private Properties props;

    public PropertiesUtils(String fileName){
        readProperties(fileName);
    }

    private void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStream fis = this.getClass().getResourceAsStream(fileName);
            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取某个属性
     */
    public String getPropertieValue(String key){
        return props.getProperty(key);
    }
    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
    public Map getAllProperty(){
        Map map=new HashMap();
        Enumeration enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }
    /**
     * 在控制台上打印出所有属性，调试时用。
     */
    public void printProperties(){
        props.list(System.out);
    }
}

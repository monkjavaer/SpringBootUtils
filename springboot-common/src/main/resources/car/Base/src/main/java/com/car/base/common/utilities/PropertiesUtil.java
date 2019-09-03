package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Description: properties 资源文件解析工具
 * Original Author: monkjavaer, 2017/12/20
 * modify by zks:修改为静态方法，避免出现大量的IO，每次都要重新读取文件，却只取其中一个属性 2019-05-07 TODO 先在cef分支上实现
 */
public class PropertiesUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Map<String,Properties> propertiesMap = new HashMap<>();

    private Properties props;
    //private URI uri;

    public PropertiesUtil(String fileName){
        readProperties(fileName);
    }
    private void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStream fis = this.getClass().getResourceAsStream(fileName);
            props.load(fis);
            //uri = this.getClass().getResource("/dbConfig.properties").toURI();
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
    /**
     * 写入properties信息
     */
    /*public void writeProperties(String key, String value) {
        try {
            OutputStream fos = new FileOutputStream(new File(uri));
            props.setProperty(key, value);
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "『comments』Update key：" + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}

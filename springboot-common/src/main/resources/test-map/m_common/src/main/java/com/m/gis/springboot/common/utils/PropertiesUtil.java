package com.m.gis.springboot.common.utils;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: PropertiesUtil
 * @Package: com.m.gis.springboot.common.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/29
 * @Version: V1.0
 */
public class PropertiesUtil {
    private final static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private Properties properties;

    private static String DEFAULT_CHARSET = "UTF-8";

    /**
     * key的提取正则表达式
     */
    private final String KEY_REGEX = "[\\w]+";

    /**
     * 数组序号的提取正则表达式
     */
    private final String INDEX_REGEX = "\\[[\\d]+\\]";

    public PropertiesUtil(String resourcePath) {
        Preconditions.checkArgument(StringUtils.isNotBlank(resourcePath),"PropertiesUtil constructor errors, param {resourcePath} is null or empty.");

        properties = new Properties();

        // 使用InPutStream流读取properties文件
        InputStream in = this.getClass().getResourceAsStream(resourcePath);
        try {
            properties.load(in);
        } catch (IOException | RuntimeException e) {
            GisIllegalParameterCommonException ex = new GisIllegalParameterCommonException(String.format("PropertiesUtil constructor errors, resource {%s} is not exist or valid.",resourcePath));
            logger.error(ex.toString());
            throw ex;
        }
    }

    /***
     * @Description: 获取属性值
     * @Author: monkjavaer
     * @Data: 17:04 2018/6/29
     * @Param: [key]
     * @Throws
     * @Return java.lang.String
     */
    public String getProperty(String key){
        return  getProperty(key,"");
    }

    /***
     * @Description: 获取属性值，设置默认属性值
     * @Author: monkjavaer
     * @Data: 17:44 2018/6/29
     * @Param: [key]
     * @Throws
     * @Return java.lang.String
     */
    public String getProperty(String key,String defaultValue){
        return  properties.getProperty(key,defaultValue);
    }

    /***
     * @Description: 获取属性值列表
     * @Author: monkjavaer
     * @Data: 17:04 2018/6/29
     * @Param: [key]
     * @Throws
     * @Return java.util.List<java.lang.String>
     */
    public List<String> getProperties(String key){
        Set<String> names = properties.stringPropertyNames();
        Iterator<String> iterator = names.iterator();
        //遍历资源文件所有key
        Integer maxIndex = -1;

        while(iterator.hasNext()){
            String item = iterator.next();
            //提取数组key的名字
            if(key.equals(findPattern(item,KEY_REGEX))){
                //提取数组key的下标
                String index = findPattern(item,INDEX_REGEX);
                //如果有形如[10]的下标字符串
                if(StringUtils.isNotBlank(index)){
                    index = index.substring(1,index.length()-1);
                    //下标字符串为数字
                    if(StringUtils.isNumeric(index)){
                        Integer indexValue = Integer.valueOf(index);
                        if(maxIndex < indexValue)
                            maxIndex = indexValue;
                    }
                }
            }
        }

        List<String> values = new ArrayList<>();
        for(Integer i=0;i<maxIndex+1;i++){
            StringBuilder builder = new StringBuilder();
            String keyItem = builder.append(key).append("[").append(i).append("]").toString();
            if(properties.containsKey(keyItem)){
                values.add(properties.getProperty(keyItem));
            }
            else{
                //检查数组下标完整性
                GisIllegalParameterCommonException ex = new GisIllegalParameterCommonException(String.format("PropertiesUtil getProperties errors, properties {%s} array index is unbound, please recheck resource file.",key));
                logger.error(ex.toString());
                throw ex;
            }
        }

        return values;
    }


    public String findPattern(String source,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        if(matcher.find()){
            return matcher.group();
        }
        return "";
    }

    public static String getStringFromInputStream(InputStream ins){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(ins,PropertiesUtil.DEFAULT_CHARSET));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();

    }


}

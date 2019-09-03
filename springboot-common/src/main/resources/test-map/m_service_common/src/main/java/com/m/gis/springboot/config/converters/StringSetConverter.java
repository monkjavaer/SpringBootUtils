package com.m.gis.springboot.config.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * @Title: StringSetConverter
 * @Package: com.m.gis.springboot.config.converters
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@Configuration
public class StringSetConverter implements Converter<String, Set<String>> {
    @Override
    public Set<String> convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        System.out.println("input string: " + source);
        String[] inputParams = source.split(",");
        Set<String> outParams = new HashSet<String>();
        for(String item:inputParams){
            outParams.add(item);
        }
        return outParams;
    }
}
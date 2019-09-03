package com.m.gis.springboot.config.converts;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.enums.GisBaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Title: GisBaseEnumConverterFactory
 * @Package: com.m.gis.springboot.config.converts
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/8/16
 * @Version: V1.0
 */
public class GisBaseEnumConverterFactory implements ConverterFactory<String, GisBaseEnum> {
    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends GisBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = converterMap.get(targetType);
        if(result == null) {
            result = new IntegerStrToEnum<T>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    class IntegerStrToEnum<T extends GisBaseEnum> implements Converter<String, T> {
        private final Class<T> enumType;
        private Map<String, T> enumMap = new HashMap<>();

        public IntegerStrToEnum(Class<T> enumType) {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();
            for(T e : enums) {
                enumMap.put(e.getValue() + "", e);
            }
        }

        @Override
        public T convert(String source) {
            T result = enumMap.get(source);
            if(result == null) {
                throw new GisIllegalParameterCommonException(enumType.getName() + " no element matches " + source);
            }
            return result;
        }
    }


}

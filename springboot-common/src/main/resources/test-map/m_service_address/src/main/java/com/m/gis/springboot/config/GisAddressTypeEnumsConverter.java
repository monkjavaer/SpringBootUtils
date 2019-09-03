package com.m.gis.springboot.config;

import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.enums.GisAddressTypeEnums;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/***
 * @Description: String参数与地名类型枚举的转换器
 * @Author: monkjavaer
 * @Data: 10:03 2018/8/16
 * @Param:
 * @Throws
 * @Return
 */
@Configuration
public class GisAddressTypeEnumsConverter implements Converter<String, GisAddressTypeEnums> {
    @Override
    public GisAddressTypeEnums convert(String source) {
       if(GisAddressTypeEnums.isValid(source)==false)
           throw new GisIllegalParameterCommonException("gis address type is illegal.");
       return GisAddressTypeEnums.getGisAddressType(source);
    }
}
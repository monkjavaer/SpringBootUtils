package com.m.gis.springboot.properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Title: GisDistrictDictProperties
 * @Package: com.m.gis.springboot.properties
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/25
 * @Version: V1.0
 */
@Component
@PropertySource({"classpath:dict.properties"})
@ConfigurationProperties(prefix="dict")
public class GisAddressDictProperties {
    private static final Logger logger = LoggerFactory.getLogger(GisAddressDictProperties.class);

    private String addressType;

    @PostConstruct
    public void init(){
        if(StringUtils.isBlank(addressType)){
            logger.error("No address type dict properties be found. Please check if {classpath:dict.properties} or {dict.addressType} key is existed.");
        }
        else{
            logger.info("address type properties is initialized successfully.");
            logger.info(String.format("dict.addressType is {%s}.",addressType));
        }
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}

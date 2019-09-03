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
public class GisPoiDictProperties {
    private static final Logger logger = LoggerFactory.getLogger(GisPoiDictProperties.class);

    private String queryPoiType;

    @PostConstruct
    public void init(){
        if(StringUtils.isBlank(queryPoiType)){
            logger.error("No query poi type dict properties be found. Please check if {classpath:dict.properties} or {dict.queryPoiType} key is existed.");
        }
        else{
            logger.info("query poi type properties is initialized successfully.");
            logger.info(String.format("dict.queryPoiType is {%s}.",queryPoiType));
        }
    }

    public String getQueryPoiType() {
        return queryPoiType;
    }

    public void setQueryPoiType(String queryPoiType) {
        this.queryPoiType = queryPoiType;
    }
}

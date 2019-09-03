package com.m.gis.springboot.enums;

/**
 * @Title: GisCommonExceptionEnums
 * @Package: springboot.common.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public enum GisPoiServiceExceptionEnums {
    GisPoiServiceException("5301", "poi service exception, some errors occur when this service is called.");

    private final String code;
    private final String message;

    GisPoiServiceExceptionEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}

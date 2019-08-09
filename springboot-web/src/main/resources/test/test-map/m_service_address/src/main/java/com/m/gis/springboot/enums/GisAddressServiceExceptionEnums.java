package com.m.gis.springboot.enums;

/**
 * @Title: GisAddressServiceExceptionEnums
 * @Package: com.m.gis.springboot.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public enum  GisAddressServiceExceptionEnums {
    GisAddressServiceException("5401", "address service exception, some errors occur when this service is called.");

    private final String code;
    private final String message;

    GisAddressServiceExceptionEnums(String code, String message) {
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

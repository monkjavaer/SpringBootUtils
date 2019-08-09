package com.m.gis.springboot.common.enums;

/**
 * @Title: GisCommonExceptionEnums
 * @Package: springboot.common.enums
 * @Description: define exception code and messages
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public enum GisCommonExceptionEnums {

    GisUndefinedRuntimeException("0001", "undefined runtime exceptions."),
    GisUndefinedThrowableException("0002", "undefined throwable exceptions."),
    GisIllegalParameterCommonException("0003", "illegal paramters."),
    GisNullParameterCommonException("0004", "null paramters."),
    GisIndexOutOfBoundsCommonException("0005", "index is out of bounds."),
    GisNoResultCommonException("0006", "no valid result returns exceptions."),
    GisCommonException("0007", "gis common occurs exceptions.");

    private final String code;
    private final String message;

    GisCommonExceptionEnums(String code, String message) {
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

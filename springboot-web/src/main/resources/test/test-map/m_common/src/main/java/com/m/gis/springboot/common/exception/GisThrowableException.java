package com.m.gis.springboot.common.exception;

import com.m.gis.springboot.common.enums.GisCommonExceptionEnums;
/**
 * @author monkjavaer
 * @version V1.0
 * @Title: GisException.java
 * @Package com.m.gis.springboot.exception
 * @Description: define throwable exception base class
 * @date 2017年11月16日 下午9:16:14
 */
public class GisThrowableException extends Exception {
    private String code;
    private String message;

    public GisThrowableException() {
        this.code = GisCommonExceptionEnums.GisUndefinedThrowableException.getCode();
        this.message = GisCommonExceptionEnums.GisUndefinedThrowableException.getMessage();
    }

    public GisThrowableException(String message) {
        this.code = GisCommonExceptionEnums.GisUndefinedThrowableException.getCode();
        this.message = message;
    }

    protected GisThrowableException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "code:" + code + ",desc:" + message;
    }
}


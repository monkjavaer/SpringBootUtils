package com.m.gis.springboot.common.exception;

import com.m.gis.springboot.common.enums.GisCommonExceptionEnums;
/**
 * @author monkjavaer
 * @version V1.0
 * @Title: GisIllegalParameterException.java
 * @Package com.m.gis.springboot.exception
 * @Description: define service exception
 * @date 2017年11月16日 下午9:43:40
 */
public class GisCommonException extends GisException {
    public GisCommonException() {
        super(GisCommonExceptionEnums.GisCommonException.getCode(), GisCommonExceptionEnums.GisCommonException.getMessage());
    }

    public GisCommonException(String message) {
        super(GisCommonExceptionEnums.GisCommonException.getCode(), message);
    }

}


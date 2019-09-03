package com.m.gis.springboot.common.exception;

import com.m.gis.springboot.common.enums.GisCommonExceptionEnums;

/**
 * @author monkjavaer
 * @version V1.0
 * @Title: GisIllegalParameterException.java
 * @Package com.m.gis.springboot.exception
 * @Description: define illegal paramter exception
 * @date 2017年11月16日 下午9:43:40
 */
public class GisNullParameterCommonException extends GisException {
    public GisNullParameterCommonException() {
        super(GisCommonExceptionEnums.GisNullParameterCommonException.getCode(), GisCommonExceptionEnums.GisNullParameterCommonException.getMessage());
    }

    public GisNullParameterCommonException(String message) {
        super(GisCommonExceptionEnums.GisNullParameterCommonException.getCode(), message);
    }
}


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
public class GisIndexOutOfBoundsCommonException extends GisException {
    public GisIndexOutOfBoundsCommonException() {
        super(GisCommonExceptionEnums.GisIndexOutOfBoundsCommonException.getCode(), GisCommonExceptionEnums.GisIndexOutOfBoundsCommonException.getMessage());
    }

    public GisIndexOutOfBoundsCommonException(String message) {
        super(GisCommonExceptionEnums.GisIndexOutOfBoundsCommonException.getCode(), message);
    }
}


package com.m.gis.springboot.common.exception;

import com.m.gis.springboot.common.enums.GisCommonExceptionEnums;
/**
 * @author monkjavaer
 * @version V1.0
 * @Title: GisIllegalParameterException.java
 * @Package com.m.gis.springboot.exception
 * @Description: define no result exception
 * @date 2017年11月16日 下午9:43:40
 */
public class GisNoResultCommonException extends GisException {
    public GisNoResultCommonException() {
        super(GisCommonExceptionEnums.GisNoResultCommonException.getCode(), GisCommonExceptionEnums.GisNoResultCommonException.getMessage());
    }

    public GisNoResultCommonException(String message) {
        super(GisCommonExceptionEnums.GisNoResultCommonException.getCode(), message);
    }

}


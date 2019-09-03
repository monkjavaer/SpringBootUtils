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
public class GisIllegalParameterCommonThrowableException extends GisThrowableException {
    public GisIllegalParameterCommonThrowableException() {
        super(GisCommonExceptionEnums.GisIllegalParameterCommonException.getCode(), GisCommonExceptionEnums.GisIllegalParameterCommonException.getMessage());
    }

    public GisIllegalParameterCommonThrowableException(String message) {
        super(GisCommonExceptionEnums.GisIllegalParameterCommonException.getCode(), message);
    }

}


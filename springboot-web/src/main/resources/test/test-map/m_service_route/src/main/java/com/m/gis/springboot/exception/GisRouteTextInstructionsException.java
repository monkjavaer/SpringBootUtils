package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisRouteServiceExceptionEnums;

/**
 * @Title: GisAddressServiceException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public class GisRouteTextInstructionsException extends GisException {
    public GisRouteTextInstructionsException() {
        super(GisRouteServiceExceptionEnums.GisRouteTextInstructionsException.getCode(), GisRouteServiceExceptionEnums.GisRouteTextInstructionsException.getMessage());
    }

    public GisRouteTextInstructionsException(String message) {
        super(GisRouteServiceExceptionEnums.GisRouteTextInstructionsException.getCode(), message);
    }
}

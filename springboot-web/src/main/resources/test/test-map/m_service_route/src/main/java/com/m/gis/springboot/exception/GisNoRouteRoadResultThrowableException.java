package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisThrowableException;
import com.m.gis.springboot.enums.GisRouteServiceExceptionEnums;

/**
 * @Title: GisAddressServiceException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public class GisNoRouteRoadResultThrowableException extends GisThrowableException {
    public GisNoRouteRoadResultThrowableException() {
        super(GisRouteServiceExceptionEnums.GisNoRouteRoadResultThrowableException.getCode(), GisRouteServiceExceptionEnums.GisNoRouteRoadResultThrowableException.getMessage());
    }

    public GisNoRouteRoadResultThrowableException(String message) {
        super(GisRouteServiceExceptionEnums.GisNoRouteRoadResultThrowableException.getCode(), message);
    }
}

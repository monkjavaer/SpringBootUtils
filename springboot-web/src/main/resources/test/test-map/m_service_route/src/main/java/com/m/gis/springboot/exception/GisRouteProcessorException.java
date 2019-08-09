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
public class GisRouteProcessorException extends GisException {
    public GisRouteProcessorException() {
        super(GisRouteServiceExceptionEnums.GisRouteProcessorException.getCode(), GisRouteServiceExceptionEnums.GisRouteProcessorException.getMessage());
    }

    public GisRouteProcessorException(String message) {
        super(GisRouteServiceExceptionEnums.GisRouteProcessorException.getCode(), message);
    }
}

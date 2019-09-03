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
public class GisPointsTooCloseServiceException extends GisException {
    public GisPointsTooCloseServiceException() {
        super(GisRouteServiceExceptionEnums.GisPointsTooCloseServiceException.getCode(), GisRouteServiceExceptionEnums.GisPointsTooCloseServiceException.getMessage());
    }

    public GisPointsTooCloseServiceException(String message) {
        super(GisRouteServiceExceptionEnums.GisPointsTooCloseServiceException.getCode(), message);
    }
}

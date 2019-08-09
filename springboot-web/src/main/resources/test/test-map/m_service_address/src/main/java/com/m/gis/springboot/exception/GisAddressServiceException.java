package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisAddressServiceExceptionEnums;

/**
 * @Title: GisAddressServiceException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public class GisAddressServiceException extends GisException {
    public GisAddressServiceException() {
        super(GisAddressServiceExceptionEnums.GisAddressServiceException.getCode(), GisAddressServiceExceptionEnums.GisAddressServiceException.getMessage());
    }

    public GisAddressServiceException(String message) {
        super(GisAddressServiceExceptionEnums.GisAddressServiceException.getCode(), message);
    }
}

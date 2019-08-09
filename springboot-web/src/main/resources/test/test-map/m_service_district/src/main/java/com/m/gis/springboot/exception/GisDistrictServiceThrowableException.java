package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisThrowableException;
import com.m.gis.springboot.enums.GisDistrictServiceExceptionEnums;

/**
 * @Title: GisDistrictServiceThrowableException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public class GisDistrictServiceThrowableException extends GisThrowableException {
    public GisDistrictServiceThrowableException() {
        super(GisDistrictServiceExceptionEnums.GisDistrictServiceThrowableException.getCode(), GisDistrictServiceExceptionEnums.GisDistrictServiceThrowableException.getMessage());
    }

    public GisDistrictServiceThrowableException(String message) {
        super(GisDistrictServiceExceptionEnums.GisDistrictServiceThrowableException.getCode(), message);
    }
}

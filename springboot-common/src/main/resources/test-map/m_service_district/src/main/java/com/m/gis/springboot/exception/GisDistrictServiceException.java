package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisDistrictServiceExceptionEnums;

/**
 * @Title: GisDistrictServiceException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public class GisDistrictServiceException extends GisException {
    public GisDistrictServiceException() {
        super(GisDistrictServiceExceptionEnums.GisDistrictServiceException.getCode(), GisDistrictServiceExceptionEnums.GisDistrictServiceException.getMessage());
    }

    public GisDistrictServiceException(String message) {
        super(GisDistrictServiceExceptionEnums.GisDistrictServiceException.getCode(), message);
    }

}

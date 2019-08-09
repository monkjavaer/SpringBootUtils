package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisDistrictServiceExceptionEnums;

/**
 * @Title: GisDistrictTypeServiceException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public class GisDistrictTypeServiceException extends GisException {
    public GisDistrictTypeServiceException() {
        super(GisDistrictServiceExceptionEnums.GisDistrictTypeServiceException.getCode(), GisDistrictServiceExceptionEnums.GisDistrictTypeServiceException.getMessage());
    }

    public GisDistrictTypeServiceException(String message) {
        super(GisDistrictServiceExceptionEnums.GisDistrictTypeServiceException.getCode(), message);
    }

}

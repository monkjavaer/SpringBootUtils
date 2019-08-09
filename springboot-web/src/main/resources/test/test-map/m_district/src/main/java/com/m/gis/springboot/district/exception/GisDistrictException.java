package com.m.gis.springboot.district.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.district.enums.GisDistrictExceptionEnums;

/**
 * @Title: GisDistrictException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public class GisDistrictException extends GisException {
    public GisDistrictException() {
        super(GisDistrictExceptionEnums.GisDistrictException.getCode(), GisDistrictExceptionEnums.GisDistrictException.getMessage());
    }

    public GisDistrictException(String message) {
        super(GisDistrictExceptionEnums.GisDistrictException.getCode(), message);
    }
}

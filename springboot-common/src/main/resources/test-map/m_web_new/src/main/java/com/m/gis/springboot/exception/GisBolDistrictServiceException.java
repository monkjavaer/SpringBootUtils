package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisBolWebExceptionEnums;

/**
 * @Title: GisDistrictServiceException
 * @Package: com.m.gis.springboot.exception
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
public class GisBolDistrictServiceException extends GisException {
    public GisBolDistrictServiceException() {
        super(GisBolWebExceptionEnums.GisBolDistrictServiceException.getCode(), GisBolWebExceptionEnums.GisBolDistrictServiceException.getMessage());
    }

    public GisBolDistrictServiceException(String message) {
        super(GisBolWebExceptionEnums.GisBolDistrictServiceException.getCode(), message);
    }

}

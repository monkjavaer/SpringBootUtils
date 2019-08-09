package com.m.gis.springboot.geo.base.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.geo.base.enums.GisGeoBaseExceptionEnums;

/**
 * @author monkjavaer
 * @version V1.0
 * @Title: DistrictException.java
 * @Package com.m.gis.geo.base.exception
 * @Description: TODO(添加描述)
 * @date 2017年9月27日 下午6:38:31
 */
public class GisUtilsBaseException extends GisException {

    public GisUtilsBaseException() {
        super(GisGeoBaseExceptionEnums.GisUtilsBaseException.getCode(), GisGeoBaseExceptionEnums.GisUtilsBaseException.getMessage());
    }

    public GisUtilsBaseException(String message) {
        super(GisGeoBaseExceptionEnums.GisUtilsBaseException.getCode(), message);
    }
}


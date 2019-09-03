package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisPoiServiceExceptionEnums;

public class GisPoiServiceException extends GisException {
    public GisPoiServiceException() {
        super(GisPoiServiceExceptionEnums.GisPoiServiceException.getCode(), GisPoiServiceExceptionEnums.GisPoiServiceException.getMessage());
    }

    public GisPoiServiceException(String message) {
        super(GisPoiServiceExceptionEnums.GisPoiServiceException.getCode(), message);
    }

}

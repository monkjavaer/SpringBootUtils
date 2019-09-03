package com.m.gis.springboot.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.enums.GisCommonServiceExceptionEnums;

public class GisGeometryServiceException extends GisException {
    public GisGeometryServiceException() {
        super(GisCommonServiceExceptionEnums.GisGeometryServiceException.getCode(), GisCommonServiceExceptionEnums.GisGeometryServiceException.getMessage());
    }

    public GisGeometryServiceException(String message) {
        super(GisCommonServiceExceptionEnums.GisGeometryServiceException.getCode(), message);
    }

}

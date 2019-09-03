package com.m.gis.springboot.geo.base.exception;

import com.m.gis.springboot.common.exception.GisThrowableException;
import com.m.gis.springboot.geo.base.enums.GisGeoBaseExceptionEnums;

public class GisParseGeometryBaseThrowableException extends GisThrowableException {
    public GisParseGeometryBaseThrowableException() {
        super(GisGeoBaseExceptionEnums.GisParseGeometryBaseThrowableException.getCode(), GisGeoBaseExceptionEnums.GisParseGeometryBaseThrowableException.getMessage());
    }

    public GisParseGeometryBaseThrowableException(String message) {
        super(GisGeoBaseExceptionEnums.GisParseGeometryBaseThrowableException.getCode(), message);
    }
}

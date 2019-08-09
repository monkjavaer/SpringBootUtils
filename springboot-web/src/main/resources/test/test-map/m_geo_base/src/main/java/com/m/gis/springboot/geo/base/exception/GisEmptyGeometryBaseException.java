package com.m.gis.springboot.geo.base.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.geo.base.enums.GisGeoBaseExceptionEnums;

public class GisEmptyGeometryBaseException extends GisException {
    public GisEmptyGeometryBaseException() {
        super(GisGeoBaseExceptionEnums.GisEmptyGeometryBaseException.getCode(), GisGeoBaseExceptionEnums.GisEmptyGeometryBaseException.getMessage());
    }

    public GisEmptyGeometryBaseException(String message) {
        super(GisGeoBaseExceptionEnums.GisEmptyGeometryBaseException.getCode(), message);
    }

}

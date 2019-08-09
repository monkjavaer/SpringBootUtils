package com.m.gis.springboot.geo.base.exception;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.geo.base.enums.GisGeoBaseExceptionEnums;

public class GisNullGeometryBaseException extends GisException {
    public GisNullGeometryBaseException() {
        super(GisGeoBaseExceptionEnums.GisNullGeometryBaseException.getCode(), GisGeoBaseExceptionEnums.GisNullGeometryBaseException.getMessage());
    }

    public GisNullGeometryBaseException(String message) {
        super(GisGeoBaseExceptionEnums.GisNullGeometryBaseException.getCode(), message);
    }
}

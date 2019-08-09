package com.m.gis.springboot.geo.base.enums;

/**
 * @Title: GisCommonExceptionEnums
 * @Package: springboot.common.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/7
 * @Version: V1.0
 */
public enum GisGeoBaseExceptionEnums {
    GisUtilsBaseException("0101", "utils operation exception, some errors occur when gis geo utils be called."),
    GisNullGeometryBaseException("0102", "geometry is null."),
    GisEmptyGeometryBaseException("0103", "geometry is empty."),
    GisParseGeometryBaseThrowableException("0104", "parse geometry occurs some exceptions.");

    private final String code;
    private final String message;

    GisGeoBaseExceptionEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}

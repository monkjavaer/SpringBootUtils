package com.m.gis.springboot.district.enums;

public enum GisDistrictExceptionEnums {
    GisDistrictException("0201", "district exception, some errors occur when some method in GisDistrict class is called.");

    private final String code;
    private final String message;

    GisDistrictExceptionEnums(String code, String message) {
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

package com.m.gis.springboot.enums;

public enum GisBolWebExceptionEnums {
    GisBolDistrictServiceException("10001", "bol district service exception, some errors occur when this service is called.");

    private final String code;
    private final String message;

    GisBolWebExceptionEnums(String code, String message) {
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

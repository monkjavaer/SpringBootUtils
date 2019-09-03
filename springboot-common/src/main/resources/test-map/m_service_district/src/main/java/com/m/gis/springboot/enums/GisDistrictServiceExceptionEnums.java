package com.m.gis.springboot.enums;

public enum GisDistrictServiceExceptionEnums {
    GisDistrictServiceException("5201", "district service exception, some errors occur when this service is called."),
    GisDistrictServiceThrowableException("5202","district service exception, some errors be throwed when this service is called."),
    GisDistrictTypeServiceException("5203","district type service exception, some errors occur when this service is called.");

    private final String code;
    private final String message;

    GisDistrictServiceExceptionEnums(String code, String message) {
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

package com.m.gis.springboot.enums;

/**
 * @Title: GisAddressServiceExceptionEnums
 * @Package: com.m.gis.springboot.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/28
 * @Version: V1.0
 */
public enum GisRouteServiceExceptionEnums {
    GisRoadServiceException("5501", "route service exception, some error occurs."),
    GisNoNearestRoadServiceException("5502", "no nearest road exception, location point is too far away from roads."),
    GisPointsTooCloseServiceException("5503","points too close exception, location points are too close."),
    GisNoServiceAreaServiceException("5504", "no service area exception."),
    GisRouteProcessorException("5505", "route processor exception, some error occurs."),
    GisRouteTextInstructionsException("5506","route text instructions exception, some error occurs."),
    GisRouteLegStepHandleException("5507","leg step handle exception, some error occurs."),
    GisNoRouteRoadResultThrowableException("5508","no route road result be found.");
    private final String code;
    private final String message;

    GisRouteServiceExceptionEnums(String code, String message) {
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

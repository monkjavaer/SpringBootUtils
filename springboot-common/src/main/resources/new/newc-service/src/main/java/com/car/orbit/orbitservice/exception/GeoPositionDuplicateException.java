package com.car.orbit.orbitservice.exception;

/**
 * @Title: GeoPositionDuplicateException
 * @Package: com.car.orbit.orbitservice.exception
 * @Description: 经纬度重复异常
 * @Author: monkjavaer
 * @Date: 2019/05/21 16:45
 * @Version: V1.0
 */
public class GeoPositionDuplicateException extends Exception {

    public GeoPositionDuplicateException() {
    }

    public GeoPositionDuplicateException(String message) {
        super(message);
    }

    public GeoPositionDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeoPositionDuplicateException(Throwable cause) {
        super(cause);
    }

    public GeoPositionDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

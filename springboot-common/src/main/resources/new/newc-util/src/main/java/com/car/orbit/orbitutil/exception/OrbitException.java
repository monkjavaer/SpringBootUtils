package com.car.orbit.orbitutil.exception;

import com.car.orbit.orbitutil.response.ResponseType;

/**
 * @Title: OrbitException
 * @Package: com.car.orbit.orbitutil.tools.exception
 * @Description: define runtime exception
 * @Author: monkjavaer
 * @Data: 2019/3/7 19:56
 * @Version: V1.0
 */
public class OrbitException extends RuntimeException {
    private Integer code;
    private String message;

    public OrbitException() {
        super();
        this.code = ResponseType.UNKNOWN.getCode();
        this.message = CommonExceptionEnums.UndefinedRuntimeException.getMessage();
    }

    public OrbitException(String message) {
        super();
        this.code = CommonExceptionEnums.UndefinedRuntimeException.getCode();
        this.message = message;
    }

    protected OrbitException(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "code:" + code + ",desc:" + message;
    }
}
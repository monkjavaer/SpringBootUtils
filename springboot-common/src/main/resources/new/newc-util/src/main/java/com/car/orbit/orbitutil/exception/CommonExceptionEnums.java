package com.car.orbit.orbitutil.exception;

/**
 * @Title: CommonExceptionEnums
 * @Package: com.car.orbit.orbitutil.tools.exception
 * @Description: define exception code and messages
 * @Author: monkjavaer
 * @Data: 2019/3/7 19:56
 * @Version: V1.0
 */
public enum CommonExceptionEnums {

    UndefinedRuntimeException(1000, "undefined runtime exceptions.");

    private final Integer code;
    private final String message;

    CommonExceptionEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

}

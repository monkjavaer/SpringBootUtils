package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/16 <br/>
 * Author：monkjavaer <br/>
 * Description: 异常参数异常
 **/
public class IllegalParamException extends Exception {

    public IllegalParamException() {
    }

    public IllegalParamException(String message) {
        super(message);
    }

    public IllegalParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParamException(Throwable cause) {
        super(cause);
    }

    public IllegalParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
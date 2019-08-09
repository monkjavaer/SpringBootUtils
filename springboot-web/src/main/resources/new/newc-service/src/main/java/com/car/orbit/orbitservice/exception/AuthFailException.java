package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/17 <br/>
 * Author：monkjavaer <br/>
 * Description: 认证失败异常
 **/
public class AuthFailException extends Exception{

    public AuthFailException() {
    }

    public AuthFailException(String message) {
        super(message);
    }

    public AuthFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthFailException(Throwable cause) {
        super(cause);
    }

    public AuthFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
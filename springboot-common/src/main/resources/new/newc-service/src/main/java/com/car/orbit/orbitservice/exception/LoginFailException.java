package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/13 <br/>
 * Author：monkjavaer <br/>
 * Description: 登陆失败exception
 **/
public class LoginFailException extends Exception{

    public LoginFailException() {
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(Throwable cause) {
        super(cause);
    }

    public LoginFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
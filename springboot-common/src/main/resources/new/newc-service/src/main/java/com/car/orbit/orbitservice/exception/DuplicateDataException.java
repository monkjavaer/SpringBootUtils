package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/16 <br/>
 * Author：monkjavaer <br/>
 * Description: 重复数据异常
 **/
public class DuplicateDataException extends Exception{

    public DuplicateDataException() {
    }

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDataException(Throwable cause) {
        super(cause);
    }

    public DuplicateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
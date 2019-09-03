package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/24 <br/>
 * Author：monkjavaer <br/>
 * Description: 结构化服务器异常
 **/
public class StructureException extends Exception {

    public StructureException() {
    }

    public StructureException(String message) {
        super(message);
    }

    public StructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public StructureException(Throwable cause) {
        super(cause);
    }

    public StructureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
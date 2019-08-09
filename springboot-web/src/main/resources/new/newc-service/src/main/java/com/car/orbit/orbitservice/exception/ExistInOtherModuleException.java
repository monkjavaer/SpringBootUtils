package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/16 <br/>
 * Author：monkjavaer <br/>
 * Description: 数据在其他模块已经存在异常
 **/
public class ExistInOtherModuleException extends Exception{

    public ExistInOtherModuleException() {
    }

    public ExistInOtherModuleException(String message) {
        super(message);
    }

    public ExistInOtherModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistInOtherModuleException(Throwable cause) {
        super(cause);
    }

    public ExistInOtherModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
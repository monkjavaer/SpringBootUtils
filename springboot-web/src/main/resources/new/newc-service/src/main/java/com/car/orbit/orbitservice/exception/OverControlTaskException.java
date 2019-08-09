package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/13 <br/>
 * Author：monkjavaer <br/>
 * Description: 停止布控任务异常
 **/
public class OverControlTaskException extends Exception {

    public OverControlTaskException() {
    }

    public OverControlTaskException(String message) {
        super(message);
    }

    public OverControlTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverControlTaskException(Throwable cause) {
        super(cause);
    }

    public OverControlTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
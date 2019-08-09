package com.car.orbit.orbitservice.exception;

/**
 * CreateDate：2019/5/16 <br/>
 * Author：monkjavaer <br/>
 * Description: 关联异常，用于删除时判断被删除的对象是否关联了其他对象
 **/
public class RelationshipException extends Exception {

    public RelationshipException() {
    }

    public RelationshipException(String message) {
        super(message);
    }

    public RelationshipException(String message, Throwable cause) {
        super(message, cause);
    }

    public RelationshipException(Throwable cause) {
        super(cause);
    }

    public RelationshipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
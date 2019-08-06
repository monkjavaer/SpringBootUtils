package com.base.springboot.exception;

import com.base.springboot.enums.CommonExceptionEnums;

/**
 * @Description: 公共异常类
 * @Author: monkjavaer
 * @Date: 2019/8/6 18:05
 * @Version: V1.0
 */
public class BaseException extends RuntimeException  {

    private String code;
    private String message;

    public BaseException() {
        super();
        this.code = CommonExceptionEnums.UndefinedRuntimeException.getCode();
        this.message = CommonExceptionEnums.UndefinedRuntimeException.getMessage();
    }

    public BaseException(String message) {
        super();
        this.code = CommonExceptionEnums.UndefinedRuntimeException.getCode();
        this.message = message;
    }

    protected BaseException(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "code:" + code + ",desc:" + message;
    }
}

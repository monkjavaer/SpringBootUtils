package com.base.springboot.enums;

/**
 * @Title: CommonExceptionEnums
 * @Package: com.base.springboot.enums
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Date: 2019/8/6 18:42
 * @Version: V1.0
 */
public enum CommonExceptionEnums {
    UndefinedRuntimeException("0001", "undefined runtime exceptions."),
    UndefinedThrowableException("0002", "undefined throwable exceptions."),
    IllegalParameterCommonException("0003", "illegal parameters."),
    NullParameterCommonException("0004", "null parameters."),
    IndexOutOfBoundsCommonException("0005", "index is out of bounds."),
    NoResultCommonException("0006", "no valid result returns exceptions."),
    CommonException("0007", "common occurs exceptions.");

    private final String code;
    private final String message;

    CommonExceptionEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}

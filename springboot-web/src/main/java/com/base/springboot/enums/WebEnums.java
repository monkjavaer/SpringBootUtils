package com.base.springboot.enums;

/**
 * @Title: WebEnums
 * @Package: com.base.springboot.enums
 * @Description: web枚举类
 * @Author: monkjavaer
 * @Date: 2019/8/8 13:30
 * @Version: V1.0
 */
public enum  WebEnums {
    AddressException("1001", "address service exception.");

    private final String code;
    private final String message;

    WebEnums(String code, String message) {
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

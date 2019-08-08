package com.base.springboot.exception;

import com.base.springboot.enums.WebEnums;

/**
 * @Title: AddressServiceException
 * @Package: com.base.springboot.exception
 * @Description: web异常类
 * @Author: monkjavaer
 * @Date: 2019/8/8 13:26
 * @Version: V1.0
 */
public class WebException extends BaseException {
    public WebException() {
        super(WebEnums.AddressException.getCode(), WebEnums.AddressException.getMessage());
    }

    public WebException(String message) {
        super(WebEnums.AddressException.getCode(), message);
    }
}

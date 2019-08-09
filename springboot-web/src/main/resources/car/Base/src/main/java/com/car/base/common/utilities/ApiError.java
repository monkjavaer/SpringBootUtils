/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/11/10
 */
package com.car.base.common.utilities;

import org.springframework.http.HttpStatus;

/**
 *
 */
public enum ApiError {
    //User
    LOGIN_ERROR(1001001, "Invalid username or password"),
    UNKNOWN_USER(1001002, "Header without authorization"),
    WRONG_PASSWORD(1001003, "Wrong password"),
    WRONG_USER(1001003, "Wrong authorization info"),
    UNKNOWN_EXCEPTION(1000000, "unknown exception"),
    RECORD_EXITS(5001001, "record already exist"),
    CANNOT_UPDATE(5001002, "involid operate"),
    NAME_EXIST(5001003,"inter name exist"),
    EXCEL_EXCEPTION(1001004, "excel exception"),
    NULLPARAM_EXCEPTION(2000000, "null parameter exception"),
    CANNOT_DELETE_EXCEPTION(3000000, "cannot delete exception"),
    CANNOT_DELETE_DEVICE_EXCEPTION(3000001, "cannot delete device exception"),
    CANNOT_DELETE_CONTROL_EXCEPTION(3000002, "cannot delete  control exception"),
    CANNOT_DELETE_DEVICE_CONTROL_EXCEPTION(3000003, "cannot delete  device control exception"),
    NOT_FOUND_DEVICE(40001, "not found device exception"),
    TOGETHER_EXCEPTION(40002, "together list is too long"),

    FILE_ERROR(1002001, "Cannot load file"),
    EMPTY_FILE(1002002, "File is empty"),

    REPEAT_NAME_EXCEPTION(1003001,"repeat name exception");

    private final int code;
    private final String message;
    private final HttpStatus status;
    private Object[] parameters = null;

    ApiError(final int code, final String message, final HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    ApiError(final int code, final String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }

    public ApiError withParams(final Object... parameters) {
        this.parameters = parameters;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getCodeAsString() {
        return String.valueOf(code);
    }

    public String getMessage() {
        return (null == parameters) ? message : String.format(message, parameters);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ApiError fromCode(final int code) {
        for (ApiError error : values()) {
            if (error.code == code) {
                return error;
            }
        }
        throw new IllegalArgumentException("Illegal " + ApiError.class + " code: " + code);
    }

    @Override
    public String toString() {
        return "[" + code + "]: " + getMessage();
    }
}

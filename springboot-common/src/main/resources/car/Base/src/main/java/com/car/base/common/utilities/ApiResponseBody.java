/**
 * Copyright(C) 2017 Car All rights reserved.
 * Original Author: monkjavaer, 2017/11/10
 */
package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class ApiResponseBody {
    private Result result = Result.success;
    private int code = 1000000;
    private Object info = new Object[0];
    private String[] errorMsg = new String[0];

    public ApiResponseBody(final Result result, final int code, final String info) {
        this.result = result;
        this.code = code;
        this.info = (null == info) ? new String[0] : new String[]{info};
    }

    private ApiResponseBody(final Result result, final int code, final String info, final String[] errorMsg) {
        this(result, code, info);
        this.errorMsg = (null == errorMsg) ? new String[0] : errorMsg;
    }

    public ApiResponseBody(final Object info) {
        this.info = (null == info) ? new Object[0] : info;
    }

    public ApiResponseBody(@NotNull final ApiError apiError) {
        this(Result.failure, apiError.getCode(), null, new String[]{apiError.getMessage()});
    }

    public ApiResponseBody(final ApiError apiError, final String errorMsg) {
        this(apiError);
        this.errorMsg = (null == errorMsg) ? new String[]{apiError.getMessage()} : new String[]{apiError.getMessage(), errorMsg};
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String[] getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String[] errorMsg) {
        this.errorMsg = errorMsg;
    }

    public enum Result {
        /**
         * 成功
         */
        success, failure;
    }
}

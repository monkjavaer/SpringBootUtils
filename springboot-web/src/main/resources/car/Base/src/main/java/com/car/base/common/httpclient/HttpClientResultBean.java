package com.car.base.common.httpclient;

import java.io.Serializable;

/**
 * Created by monkjavaer on 17-9-1.
 */
public class HttpClientResultBean implements Serializable {

    private static final long serialVersionUID = 5467856455882220908L;
    private boolean isSuccess = false;
    private Object result;
    private String errorMsg;
    private int errorStatus;

    public HttpClientResultBean() {
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorStatus() {
        return this.errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }
}

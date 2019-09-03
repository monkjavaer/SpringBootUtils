package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitutil.response.IResponseType;

/**
 * CreateDate：2019/5/7 <br/>
 * Author：monkjavaer <br/>
 * Description: service层操作结果类，用于返回给controller层，告知service层操作结果
 **/
public class ServiceResultBO<T> {

    /** 操作结果 */
    private IResponseType code;

    /** 操作结果类/失败理由 */
    private T data;

    public ServiceResultBO(IResponseType code, T data) {
        this.code = code;
        this.data = data;
    }

    public IResponseType getCode() {
        return code;
    }

    public void setCode(IResponseType code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiceResultBO{" +
                ", code='" + code + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
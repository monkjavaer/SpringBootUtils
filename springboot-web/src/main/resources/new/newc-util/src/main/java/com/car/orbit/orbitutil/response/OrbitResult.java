package com.car.orbit.orbitutil.response;

import com.car.orbit.orbitutil.exception.OrbitException;
import com.car.orbit.orbitutil.tools.JsonUtils;

/**
 * @author wujiong
 * @version V1.0
 * @Title: OrbitResult.java
 * @Package com.car.gis.springboot.common
 * @Description: 返回结构
 * @date 2017年11月16日 下午9:35:16
 */
public class OrbitResult {
    /**
     * 1、	code：消息码，正常返回为数字200，公共异常消息码定义见第四章节,其它异常消息码从数字1000开始自定义(详见各个接口的定义)。
     */
    private Integer code;
    /**
     * 2、	message：如果是正常状态的话，此字段为“ok”字符串；异常的话，返回异常信息。
     */
    private String message;
    /**
     * 3、	success：如果是正常状态的话，此字段为布尔值true；异常的话，返回布尔值false。
     */
    private Boolean success;
    /**
     * 4、	data：每个接口返回的具体参数（详见各个接口的定义）。
     */
    private Object data;

    public OrbitResult() {
        super();
    }

    public OrbitResult(ResponseType responseType) {
        this.code = responseType.getCode();
        this.message = responseType.getDesc();
        if (responseType.getCode().equals(IResponseType.SUCCESS)) {
            this.success = true;
        } else {
            this.success = false;
        }
    }

    public OrbitResult(Integer code, String message, Boolean success, Object data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public OrbitResult(OrbitException e) {
        super();
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String toString() {
        return JsonUtils.toJSONString(this);
    }
}


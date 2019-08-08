package com.base.springboot.response;

import com.base.springboot.exception.BaseException;
import com.base.springboot.utils.JsonUtils;

/**
 * @Description: 返回结果封装
 * @author monkjavaer
 * @date 2017年11月16日 下午9:35:16
 * @version V1.0
 */
public class BaseResult {
	private String code;
	private String message;
	private Object data;
	public BaseResult() {
		super();
	}
	public BaseResult(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public BaseResult(BaseException e){
		super();
		this.code = e.getCode();
		this.message = e.getMessage();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
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
	
	public String toString(){
		return JsonUtils.toJSONString(this);
	}
}


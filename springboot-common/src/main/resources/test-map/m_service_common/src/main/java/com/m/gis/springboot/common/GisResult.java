package com.m.gis.springboot.common;

import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.common.utils.JsonUtils;

/**
 * @Title: GisResult.java
 * @Package com.m.gis.springboot.common
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月16日 下午9:35:16
 * @version V1.0
 */
public class GisResult{
	private String code;
	private String message;
	private Object data;
	public GisResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GisResult(String code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public GisResult(GisException e){
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


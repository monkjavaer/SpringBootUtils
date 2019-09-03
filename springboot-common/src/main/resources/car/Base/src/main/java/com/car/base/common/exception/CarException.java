package com.base.springboot.car.Base.src.main.java.com.car.base.common.exception;

public class CarException extends Exception{
	private int code = -1;//
	public CarException() {
		
	}
	
	public CarException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CarException(String message) {
		super(message);
	}
	
	public CarException(int code, String message) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}

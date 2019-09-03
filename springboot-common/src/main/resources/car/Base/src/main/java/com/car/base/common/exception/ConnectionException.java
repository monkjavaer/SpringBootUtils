package com.base.springboot.car.Base.src.main.java.com.car.base.common.exception;

public class ConnectionException extends CarException {
	//Can be config filename or other source
	static final int MY_CODE = 200;
	
	public ConnectionException(String message) {
		super(MY_CODE, message);
	}
	
	public ConnectionException(int code, String message) {
		super(code, message);
	}
}

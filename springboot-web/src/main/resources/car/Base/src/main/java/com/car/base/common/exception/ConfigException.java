package com.car.base.common.exception;

public class ConfigException extends CarException {
	//Can be config filename or other source
	String configSrc;
	static final int MY_CODE = 100;
	
	public ConfigException(String message, String configSrc) {
		super(MY_CODE, message);
		this.configSrc = configSrc;
	}
	
	protected ConfigException(int code, String message, String configSrc) {
		super(code, message);
		this.configSrc = configSrc;
	}
}

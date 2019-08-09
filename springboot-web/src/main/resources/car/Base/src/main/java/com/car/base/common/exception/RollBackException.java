package com.car.base.common.exception;

/**
 * This exception is for abnormal database operation which should be rolled back
 * @author monkjavaer
 *
 */
public class RollBackException extends CarException{
	
	static final int MYCODE = 700;
	
	public RollBackException(String message) {
		super(MYCODE, message);
	}
	
	public RollBackException(int code, String message) {
		super(code, message);
	}
}

package com.car.base.common.exception;

/**
 * 
 * @author monkjavaer
 *
 */
public class ValidateException extends CarException {
	static final int MYCODE = 400;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8696031648875132275L;

	public ValidateException(String msg)  
    {  
        super(MYCODE, msg);
    }
	
}

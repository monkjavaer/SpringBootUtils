package com.car.base.common.exception;

/**
 * 
 * @author monkjavaer
 *
 */
public class TimeFormatException extends CarException {
	static final int MYCODE = 422;
	/**
	 *
	 */
	private static final long serialVersionUID = -8696031648875132275L;

	public TimeFormatException(String msg)
    {  
        super(MYCODE, msg);
    }
	
}

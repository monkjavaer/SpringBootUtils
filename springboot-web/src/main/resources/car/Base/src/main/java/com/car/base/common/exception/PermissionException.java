package com.car.base.common.exception;

/**
 *
 * @author monkjavaer
 *
 */
public class PermissionException extends CarException {
	static final int MYCODE = 300;
	/**
	 * 
	 */
	private static final long serialVersionUID = -6008004025445496953L;

	public PermissionException(String msg)  
    {  
        super(MYCODE, msg);
    }
	
}

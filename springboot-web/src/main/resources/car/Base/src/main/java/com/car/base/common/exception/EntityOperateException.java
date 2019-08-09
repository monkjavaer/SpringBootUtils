package com.car.base.common.exception;

/**
 * 
 * @author monkjavaer
 *
 */
public class EntityOperateException extends CarException {
	static final int MYCODE = 500;
	/**   
	 * 
	 */
	private static final long serialVersionUID = -1762283867533955865L;

	public EntityOperateException(String msg)  
    {  
        super(MYCODE, msg);
    }
	
}

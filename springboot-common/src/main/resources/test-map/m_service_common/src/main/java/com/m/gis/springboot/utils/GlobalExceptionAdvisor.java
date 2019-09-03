package com.m.gis.springboot.utils;

import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.common.exception.GisException;
import com.m.gis.springboot.common.exception.GisIllegalParameterCommonException;
import com.m.gis.springboot.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: GlobalExceptionHandler.java
 * @Package com.m.gis.springboot.utils
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月14日 下午11:51:32
 * @version V1.0
 */
@ControllerAdvice
public class GlobalExceptionAdvisor {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvisor.class);

	/**
	 * @Description: 提取Validator产生的异常错误
	 * @Author: monkjavaer
	 * @Data: 23:17 2018/6/11
	 * @Param: [bindingResult]
	 * @Throws
	 * @Return com.m.gis.springboot.common.exception.GisException
	 */
	private GisException parseBindingResult(BindingResult bindingResult){
		Map<String,String> errorMsgs = new HashMap<String,String>();
		for (FieldError error:bindingResult.getFieldErrors()){
			errorMsgs.put(error.getField(),error.getDefaultMessage());
		}
		if(errorMsgs.isEmpty())
			return new GisIllegalParameterCommonException();
		else
			return new GisIllegalParameterCommonException(JsonUtils.toJSONString(errorMsgs));
	}

	/**
	 * 
	 * @name validExceptionHandler 
	 * @description  捕获@Validate校验抛出的异常
	 * @param e
	 * @param request
	 * @param response
	 * @return 
	 * @author monkjavaer
	 * @date 2017年11月30日
	 */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public GisResult validExceptionHandler(BindException e, HttpServletRequest request, HttpServletResponse response) {
    	GisException ex = parseBindingResult(e.getBindingResult());
		logger.error(ex.getMessage());
        return ResultUtil.error(ex);
    }

	/**
	 *
	 * @name validExceptionHandler
	 * @description  捕获@Validate校验抛出的异常
	 * @param e
	 * @param request
	 * @param response
	 * @return
	 * @author monkjavaer
	 * @date 2017年11月30日
	 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public GisResult validException2Handler(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
		GisException ex = parseBindingResult(e.getBindingResult());
		logger.error(ex.getMessage());
		return ResultUtil.error(ex);
    }

	@ExceptionHandler(value = GisException.class)
	@ResponseBody
	public GisResult gisExceptionHandler(HttpServletRequest req, GisException e) throws Exception {
		logger.error(e.getMessage());
		return ResultUtil.error(e);
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public GisResult exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error(e.toString());
    	return ResultUtil.error(new GisException());
    }


}


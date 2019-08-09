package com.m.gis.springboot.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @Title: LocaleUtil.java
 * @Package com.m.gis.springboot.utils
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月27日 下午2:55:38
 * @version V1.0
 */

public class LocaleUtil {
	private static MessageSource messageSource = ApplicationContextUtil.getBean(MessageSource.class);

	/**
	 * 
	 * @name getMessage
	 * @description  获取国际化词条
	 * @param code 词条的key值
	 * @return 
	 * @author monkjavaer
	 * @date 2017年11月27日
	 */
	public static String getMessage(String code) {
		return getMessage(code, null);
	}

	/**
	 * 
	 * @name getMessage
	 * @description  获取国际化词条
	 * @param code 词条的key值
	 * @param args 数组类型参数
	 * @return 
	 * @author monkjavaer
	 * @date 2017年11月27日
	 */
	public static String getMessage(String code, Object[] args) {
		return getMessage(code, args, "");
	}

	/**
	 * 
	 * @name getMessage
	 * @description  获取国际化词条
	 * @param code 词条的key值
	 * @param args 数组类型参数
	 * @param defaultMessage 缺省时的默认返回值
	 * @return 
	 * @author monkjavaer
	 * @date 2017年11月27日
	 */
	public static String getMessage(String code, Object[] args, String defaultMessage) {
		// 这里使用比较方便的方法，不依赖request.
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}

	/***
	 * @Description: 获取locale对象
	 * @Author: monkjavaer
	 * @Data: 15:21 2018/7/19
	 * @Param: []
	 * @Throws
	 * @Return java.util.Locale
	 */
	public static Locale getLocale(){
		return LocaleContextHolder.getLocale();
	}

	/**
	* @Description: 通过前端传入的语言符号，获取Locale对象
	* @Author: monkjavaer
	* @Date: 2018/11/12 17:12
	* @Param: [localeString]
	* @Return: java.util.Locale
	* @Throws
	*/
	public static Locale getLocaleByString(String localeString){

		return new Locale(localeString.substring(0,localeString.indexOf("_")), localeString.substring(localeString.indexOf("_")+1,localeString.length()));
	}

}


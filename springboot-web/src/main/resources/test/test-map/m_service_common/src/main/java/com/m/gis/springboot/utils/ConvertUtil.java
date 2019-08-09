package com.m.gis.springboot.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @Title: ConvertUtil.java
 * @Package com.m.gis.springboot.utils
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年12月20日 下午5:14:32
 * @version V1.0
 */
public class ConvertUtil {

	public static <T> Set<T> string2Set(Class<T> clazz,String source) {
		// TODO Auto-generated method stub
		Set<T> types = new HashSet<T>();
	 	if(StringUtils.isBlank(source))
	 		return types;
	 	
	 	if(clazz.equals(Integer.class)){
	 		for(String item:source.split(",")){
		 		if(!StringUtils.isBlank(item))
		 			types.add(clazz.cast(Integer.valueOf(item)));
		 	}
	 	}
	 	else{
	 		for(String item:source.split(",")){
		 		if(!StringUtils.isEmpty(item))
		 			types.add(clazz.cast(item));
		 	}
	 	}
	     return types;
	}

/*	public static Set<Integer> convert2Integer(String source) {
		// TODO Auto-generated method stub
		Set<Integer> types = new HashSet<Integer>();
	 	if(StringUtils.isEmpty(source))
	 		return types;
	 	for(String item:source.split(",")){
	 		if(!StringUtils.isEmpty(item)){
	 			types.add(Integer.valueOf(item));
	 		}
	 	}
	     return types;
	}*/
	
}


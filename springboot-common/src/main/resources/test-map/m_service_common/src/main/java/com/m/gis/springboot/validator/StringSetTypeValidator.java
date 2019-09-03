package com.m.gis.springboot.validator;

import com.m.gis.springboot.annotation.StringSetType;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Title: StringSetTypeValidator.java
 * @Package com.m.gis.springboot.validator
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年12月20日 下午4:43:49
 * @version V1.0
 */
public class StringSetTypeValidator implements ConstraintValidator<StringSetType, String> {
	private StringSetType annotation;
	
	@Override
	public void initialize(StringSetType constraintAnnotation) {
		// TODO Auto-generated method stub
		annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(value))
			return true;
		
		String[] items = value.split(",");
		if(annotation.value().equals(String.class)){
			for(String item:items){
		 		if(false == item.matches("[0-9]+"))
		 			return false;
		 	}
		}
		else if(annotation.value().equals(Integer.class)){
			for(String item:items){
				if(false == item.matches("[0-9]+"))
		 			return false;
		 	}
		}
		return true;
	}
}


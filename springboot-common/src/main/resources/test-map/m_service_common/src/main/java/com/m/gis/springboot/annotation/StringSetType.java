package com.m.gis.springboot.annotation;

import com.m.gis.springboot.validator.StringSetTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: StringSetType.java
 * @Package com.m.gis.springboot.annotation
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年12月20日 下午4:31:09
 * @version V1.0
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME)  
@Constraint(validatedBy = {StringSetTypeValidator.class})
public @interface StringSetType {
	Class value();
	//默认错误消息
    String message() default "parameter be illegal, strings must be consist of  0-9 and splited by , .";
    //分组
    Class<?>[] groups() default {};
    //负载
    Class<? extends Payload>[] payload() default {};

}


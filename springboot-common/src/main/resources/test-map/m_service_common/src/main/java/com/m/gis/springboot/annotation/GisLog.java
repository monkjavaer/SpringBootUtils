package com.m.gis.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title: MultiTypeFormatter.java
 * @Package com.m.gis.springboot.annotation
 * @Description: TODO(添加描述)
 * @author monkjavaer
 * @date 2017年11月30日 下午2:38:16
 * @version V1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  
public @interface GisLog {
    String message() default "...";
}


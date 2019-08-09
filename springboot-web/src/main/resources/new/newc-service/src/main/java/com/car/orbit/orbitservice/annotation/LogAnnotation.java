package com.car.orbit.orbitservice.annotation;

/**
 * @Title: LogAnnotation
 * @Package: com.car.orbit.orbitservice.annotation
 * @Description: 日志注解
 * @Author: monkjavaer
 * @Data: 2019/3/12 17:22
 * @Version: V1.0
 */

import tk.mybatis.mapper.common.Mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)     // 用来规定注解的作用范围的,这里为方法级别.
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

    /** 操作类型 */
    String actionType() default "";

    /** 数据类型 */
    String dataType() default "";

    /** 数据操作mapper */
    Class mapperType() default Mapper.class;

}

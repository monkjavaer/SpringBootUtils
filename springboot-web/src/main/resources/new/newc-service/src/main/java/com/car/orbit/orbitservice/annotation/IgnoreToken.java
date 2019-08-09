package com.car.orbit.orbitservice.annotation;

import java.lang.annotation.*;

/**
 * CreateDate：2019/4/11 <br/>
 * Author：monkjavaer <br/>
 * Description: 标识不需要token验证拦截
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreToken {
}

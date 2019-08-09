package com.car.orbit.orbitservice.aop;

import com.car.orbit.orbitutil.response.OrbitResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CreateDate: 2018/6/28 <br/>
 * Author: monkjavaer <br/>
 * Description: aop of handle response type
 **/
@Aspect
@Component
@Order(3)
public class ResponseCodeAOP {

    /**
     * handle response code, add module code before origin response code
     *
     * @param joinPoint joinPoint
     * @return response content
     * @throws Throwable exception
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object handleResponseCode(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        if (proceed instanceof OrbitResult) {
            return proceed;
        }
        return proceed;
    }
}
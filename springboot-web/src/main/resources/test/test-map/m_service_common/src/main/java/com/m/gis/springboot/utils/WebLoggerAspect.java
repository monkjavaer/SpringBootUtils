package com.m.gis.springboot.utils;

import com.m.gis.springboot.annotation.GisLog;
import com.m.gis.springboot.common.GisResult;
import com.m.gis.springboot.common.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Title: WebLoggerAspect
 * @Package: com.m.gis.springboot.utils
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/11
 * @Version: V1.0
 */
@Aspect
@Component
public class WebLoggerAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<LoggerEntity> loggerLocal = new ThreadLocal<LoggerEntity>();

    @Pointcut("execution(public * com.m.gis.springboot.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        LoggerEntity loggerEntity = new LoggerEntity();
        loggerEntity.setRequestId(UUID.randomUUID().toString());
        loggerEntity.setSesseionId(request.getRequestedSessionId());
        loggerEntity.setUrl(request.getRequestURI());
        loggerEntity.setIp(request.getRemoteAddr());
        loggerEntity.setHttpMethod(request.getMethod());
        loggerEntity.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        loggerEntity.setParams( JsonUtils.toJSONArrayString(joinPoint.getArgs()));
        //loggerEntity.setParams(Arrays.toString(joinPoint.getArgs()));
        loggerEntity.setRequestTime(System.currentTimeMillis());
        loggerLocal.set(loggerEntity);
        logger.info(loggerEntity.getRequestFormat());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, GisResult ret) throws Throwable {
        // 处理完请求，返回内容
        LoggerEntity loggerEntity = loggerLocal.get();
        loggerEntity.setResponseTime(System.currentTimeMillis());

        //检查方法是否有GisLog注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation annotation = method.getAnnotation(GisLog.class);
        if(annotation != null){
            loggerEntity.setResult(((GisLog) annotation).message());
        }
        else
            loggerEntity.setResult(JsonUtils.toJSONString(ret));

        logger.info(loggerEntity.getResponseFormat());
    }

}

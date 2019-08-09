package com.car.orbit.orbitservice.aop;

import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.security.GeneralAuInfo;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * CreateDate：2019/3/9 <br/>
 * Author：monkjavaer <br/>
 * Description: 检查请求是否有zuul网关的认证
 **/
@Aspect
@Component
@Order(1)
public class RouteSecurityAOP {
    /** logger */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * verify from_zuul_label's existence
     *
     * @param joinPoint joint point
     * @return verify response
     * @throws Throwable
     */
    @Around(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public Object verifyHeader(ProceedingJoinPoint joinPoint) throws Throwable {

        //get request information
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        //charge if request have "from_zuul_label" header
        String accessKey = request.getHeader(GeneralAuInfo.ZUUL_PROCESS_LABEL_KEY);
        if (StringUtils.isBlank(accessKey)) {
            logger.warn("illegal access from request {} to {}, less the header '{}' ",
                    requestMethod, request.getRequestURL().toString(), GeneralAuInfo.ZUUL_PROCESS_LABEL_KEY);
            return new OrbitResult(ResponseType.ILLEGAL_PARAMETER);
        }

        //charge if "from_zuul_label" header is right
        if (!accessKey.equals(GeneralAuInfo.ZUUL_PROCESS_LABEL_VALUE)) {
            logger.warn("the header '{}' for request from request {} to {} is illegal",
                    requestMethod, request.getRequestURL().toString(), GeneralAuInfo.ZUUL_PROCESS_LABEL_KEY);
            return new OrbitResult(ResponseType.ILLEGAL_PARAMETER);

        }

        //from_zuul_label verify success,proceed
        return joinPoint.proceed();
    }
}
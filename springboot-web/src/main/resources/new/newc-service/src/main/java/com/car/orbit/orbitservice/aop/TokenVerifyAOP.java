package com.car.orbit.orbitservice.aop;

import com.car.orbit.orbitservice.util.LocalHolder;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.TokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;


/**
 * CreateDate：2019/3/8 <br/>
 * Author：monkjavaer <br/>
 * Description:
 **/
@Aspect
@Component
@Order(2)
public class TokenVerifyAOP {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisClient redisClient;

    @Value("${user.login-expire-time}")
    private int expireTime;

    /**
     * verify token's existence
     *
     * @param joinPoint joint point
     * @return verify response
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController) && !@annotation(com.car.orbit.orbitservice.annotation.IgnoreToken)")
    public Object verifyToken(ProceedingJoinPoint joinPoint) throws Throwable {

        //get request information
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        //charge if request have "token" header
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            // 如果header中没有token，去请求参数中获取
            token = request.getParameter("token");
            //token = token.replaceAll("\\+", "%2B");
            token = URLEncoder.encode(token, "utf-8");
            if (StringUtils.isBlank(token)) {
                logger.warn("illegal access from request {} to {}, please add token header", requestMethod, request.getRequestURL().toString());
                return new OrbitResult(ResponseType.ILLEGAL_TOKEN);
            }
        }

        //charge if request token is legal
        if (!TokenUtils.isLegalToken(token)) {
            logger.warn("request token from request {} to {} is illegal", requestMethod, request.getRequestURL().toString());
            return new OrbitResult(ResponseType.ILLEGAL_TOKEN);
        }

        // 检查redis中是否含有该token，若有，则重新设置超时时间，若没有，则认为用户token已经失效，请求失败
        if (!redisClient.existsKey(token)) {
            logger.warn("request token from request {} to {} is not in redis", requestMethod, request.getRequestURL().toString());
            return new OrbitResult(ResponseType.ILLEGAL_TOKEN);
        }
        redisClient.setExpireTime(token, expireTime);

        LocalHolder.put(LocalHolder.TOKEN_KEY, token);

        //token verify success,proceed
        return joinPoint.proceed();
    }
}
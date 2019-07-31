package com.base.springboot.interceptor;

import com.base.springboot.constants.CommonConstants;
import com.base.springboot.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author monkjavaer
 * @date 2019/7/31 13:47
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    /**
     * 定义拦截前的行为，记录请求日志
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoggerEntity loggerEntity = new LoggerEntity();

        String sessionId = request.getRequestedSessionId();
        String url = request.getRequestURI();
        String method = request.getMethod();
        String params = "";
        if("POST".equals(method)){
            InputStream io = null;
            try{
                io = request.getInputStream();
                params = IOUtils.toString(io, CommonConstants.ENCODE);
            }catch(IOException e){
                logger.error(e.toString());
            }
        }
        else{
            params = JsonUtils.toJSONString(request.getParameterMap());
        }
        loggerEntity.setSesseionId(sessionId);
        loggerEntity.setUrl(url);
        loggerEntity.setParams(params);
        loggerEntity.setHttpMethod(method);
        loggerEntity.setRequestTime(System.currentTimeMillis());
        request.setAttribute("LoggerEntity",loggerEntity);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoggerEntity loggerEntity = (LoggerEntity)request.getAttribute("LoggerEntity");
        int status = response.getStatus();
        Long currentTime = System.currentTimeMillis();

        loggerEntity.setStatus(status);
        loggerEntity.setResponseTime(currentTime);

        logger.info(loggerEntity.getRequestFormat());
        logger.info(loggerEntity.getResponseFormat());
    }
}

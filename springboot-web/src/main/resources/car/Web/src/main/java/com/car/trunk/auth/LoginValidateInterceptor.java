package com.car.trunk.auth;

import com.car.base.common.constant.AppConstant;
import com.car.base.redis.RedisHelper;
import com.car.trunk.center.system.bo.UserInfoBO;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 验证登录
 * Original Author: monkjavaer, 2017/12/20
 */
public class LoginValidateInterceptor  extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginValidateInterceptor.class);

    /**
     * 验证用户的登陆状态
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (validateUrl(requestURI)) {
            return super.preHandle(request, response, handler);
        } else {
            String token = request.getHeader("authorization");
            if (null == token) {
                response.sendRedirect("/");
                return false;
            }
            String jsonStr = RedisHelper.get(token);
            if (null == jsonStr) {
                response.sendRedirect("/");
                return false;
            }

            UserInfoBO bo = null;
            try {
                bo = (UserInfoBO) JSONObject.toBean(JSONObject.fromObject(jsonStr), UserInfoBO.class);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("/");
                return false;
            }

            if (null == bo) {
                response.sendRedirect("/");
                return false;
            } else {
                return super.preHandle(request, response, handler);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 返回类型
     * @param str
     * @return
     */
    public boolean validateUrl(String str){

        if(null != str){
            if(str.contains(".")){
                String type = str.substring(str.lastIndexOf("."), str.length());
                return AppConstant.FILE_TYPE.contains(type);
            } else {
                if(AppConstant.URL_LIST.contains(str) || str.startsWith("/api")
                        || str.startsWith("/alarm") || str.startsWith("/control") || str.startsWith("/static") ||
                        str.startsWith("/webSocketServer") || str.startsWith("/websocket")){
                    return true;
                }
            }
        }
        return false;
    }
}

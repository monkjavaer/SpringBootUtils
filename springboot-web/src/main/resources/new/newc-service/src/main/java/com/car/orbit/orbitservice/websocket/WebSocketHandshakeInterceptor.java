package com.car.orbit.orbitservice.websocket;

import com.car.orbit.orbitservice.constant.WebSocketConstant;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Title: WebSocketHandshakeInterceptor
 * @Package: com.car.orbit.orbitservice.websocket
 * @Description: WebSocketHandshakeInterceptor
 * @Author: monkjavaer
 * @Data: 2019/4/4 17:47
 * @Version: V1.0
 */
@Service
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(WebSocketHandshakeInterceptor.class);

    /**
     * This method will be called after the connection finish the http request/response period and begin the native tcp
     * period, i.e., before the websocket connection will start.
     * We store the username in the Websocket session to distinguish different users.
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            String token = httpRequest.getParameter("token");
            token = URLEncoder.encode(token, "utf-8");
            OrbitSysUser user = OrbitSysUserRedis.getLoginUser(token);
            if (user == null){
//                logger.error("===user is null ,token is {}",token);
                return false;
            }
            logger.info("=========username = {}",user.getName());
            //Use userName to distinguish WebSocket Session in order to send message to a specific user
            attributes.put(WebSocketConstant.WEBSOCKET_USERNAME, user.getUsername());
            attributes.put(WebSocketConstant.WEBSOCKET_TOKEN, token);
            RequestContext context = new RequestContext(httpRequest);
            attributes.put(WebSocketConstant.WEBSOCKET_CONTEXT, context);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {

    }
}
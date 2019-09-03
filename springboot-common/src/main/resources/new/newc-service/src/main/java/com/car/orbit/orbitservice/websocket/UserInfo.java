package com.car.orbit.orbitservice.websocket;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Title: UserInfo
 * @Package: com.car.trunk.websocket
 * @Description: 用户登录信息
 * @Author: monkjavaer
 * @Data: 2019/1/16 16:20
 * @Version: V1.0
 */
public class UserInfo {
    /**
     * 用户名
     */
    public String username;
    public WebSocketSession session;
    public RequestContext context;
    /**
     * token
     */
    public String token;

    public UserInfo(String username, WebSocketSession session, RequestContext context, String token) {
        this.username = username;
        this.session = session;
        this.context = context;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
    public RequestContext getContext() {
        return context;
    }

    public void setContext(RequestContext context) {
        this.context = context;
    }
}

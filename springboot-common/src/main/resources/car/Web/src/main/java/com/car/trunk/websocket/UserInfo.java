package com.base.springboot.car.Web.src.main.java.com.car.trunk.websocket;

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
    public String username;
    public WebSocketSession session;
    public Byte roleType;
    public RequestContext context;

    public UserInfo(WebSocketSession session, Byte roleType) {
        this.session = session;
        this.roleType = roleType;
    }

    public UserInfo(String username, WebSocketSession session, Byte roleType, RequestContext context) {
        this.username = username;
        this.session = session;
        this.roleType = roleType;
        this.context = context;
    }
}

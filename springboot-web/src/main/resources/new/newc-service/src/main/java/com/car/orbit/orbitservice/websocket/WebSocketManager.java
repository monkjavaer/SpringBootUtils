package com.car.orbit.orbitservice.websocket;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitservice.websocket.message.UserMessageBody;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: WebscoketManager
 * @Package: com.car.trunk.websocket
 * @Description: webscoket 管理类（用户信息维护，发送消息等）
 * @Author: monkjavaer
 * @Data: 2019/1/16 16:20
 * @Version: V1.0
 */
@Component
public class WebSocketManager {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketManager.class);
    /**
     * 保存用户信息
     */
    public static ConcurrentHashMap<String, UserInfo> USERS = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<WebSocketSession, UserInfo> SESSIONS = new ConcurrentHashMap<>();


    /**
     * 添加用户信息
     * @param username
     * @param userInfo
     */
    public void addUser(String username,UserInfo userInfo){
        this.USERS.put(username,userInfo);
    }

    /**
     * 添加session信息
     * @param session WebSocketSession
     * @param userInfo
     */
    public void addSession(WebSocketSession session, UserInfo userInfo){
        this.SESSIONS.put(session,userInfo);
    }

    /**
     * 给所有用户发消息
     *
     * @param message 消息
     */
    public void sendMessageToUsers(WebSocketMsg message) {
        Collection<UserInfo> userInfos = USERS.values();
        for (UserInfo userInfo : userInfos) {
            sendMessageToUser(userInfo.username, message);
        }

    }

    /**
     * 发送消息给指定用户
     * @param message 消息
     * @param userNames 接收消息用户
     */
    public void sendMessageToUsers(WebSocketMsg message, List<String> userNames) {
        Collection<UserInfo> userInfos = USERS.values();
        for (String userName : userNames) {
            for (UserInfo userInfo : userInfos) {
                if (userName.equals(userInfo.username)) {
                    sendMessageToUser(userInfo.username, message);
                }
            }
        }
    }

    public void sendMessageToUser(String username, WebSocketMsg message) {

        if (USERS.containsKey(username)) {
            UserInfo userInfo = USERS.get(username);
            if (userInfo != null) {
                sendMessageTo(userInfo, message);
            }
        }
    }


    /**
     * 给用户发消息，用于前端登出用户
     * @param message 消息
     */
    public void sendLogoutMessage(WebSocketMsg message) {
        UserMessageBody userMsg = (UserMessageBody) message.getBody();
        String username = userMsg.getUserName();
        if (USERS.containsKey(username)) {
            UserInfo userInfo = USERS.get(username);
            if (userInfo != null) {
                sendMessageTo(userInfo, message);
            }
            //发送消息后，移除session
            this.removeWebSocketSession(username);
        }
    }


    /**
     * 发送消息
     * @param userInfo 用户信息
     * @param message 消息体
     */
    private void sendMessageTo(UserInfo userInfo, WebSocketMsg message) {
        WebSocketSession session = userInfo.session;
        try {
            if (session.isOpen()) {

                TextMessage textMessage;
                if (null != message) {
                    textMessage = messageToTextMessage(message);

                } else {
                    textMessage = messageToTextMessage(null);
                }
                session.sendMessage(textMessage);
            }
        } catch (Exception e) {
            logger.error("send message error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 消息处理
     * @param message
     * @return
     */
    private TextMessage messageToTextMessage(WebSocketMsg message) {
        JSONObject jsonObject = JsonUtils.toJSONObject(message);
        if (null != jsonObject) {
            return new TextMessage(jsonObject.toString());
        } else {
            return null;
        }
    }

    /**
     * 移除session
     * @param user
     */
    public void removeWebSocketSession(String user) {
        try {
            UserInfo info = USERS.get(user);
            if (info != null) {
                USERS.remove(info.username);
                SESSIONS.remove(info.session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    public void removeWebSocketSession(WebSocketSession session) throws Exception {
        try {
            UserInfo info = SESSIONS.get(session);
            if (info != null) {
                USERS.remove(info.username);
            }
            SESSIONS.remove(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}

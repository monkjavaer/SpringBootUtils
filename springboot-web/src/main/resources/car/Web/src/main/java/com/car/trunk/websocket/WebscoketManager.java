package com.car.trunk.websocket;

import com.car.base.common.utilities.JsonDateValueProcessor;
import com.car.base.message.MQMessage;
import com.car.base.message.VehicleStructureMsg;
import com.car.base.webservice.UserLoginMsg;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Date;
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
public class WebscoketManager {

    private static final Logger logger = LoggerFactory.getLogger(WebscoketManager.class);
    /**
     * 保存用户信息
     */
    private ConcurrentHashMap<String, com.car.trunk.websocket.UserInfo> USERS = new ConcurrentHashMap<>();
    private ConcurrentHashMap<WebSocketSession, com.car.trunk.websocket.UserInfo> SESSIONS = new ConcurrentHashMap<>();


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
    public void addSession(WebSocketSession session,UserInfo userInfo){
        this.SESSIONS.put(session,userInfo);
    }

    /**
     * Send a message to the web browser of all users
     *
     * @param message
     */
    public void sendMessageToUsers(MQMessage message) {
        Collection<UserInfo> colInfos = USERS.values();
        for (UserInfo userInfo : colInfos) {
            sendMessageToUser(userInfo.username, message);
        }

    }

    /**
     * 发送消息给指定用户
     * @param message
     * @param userNames
     */
    public void sendMessageToUsers(MQMessage message, List<String> userNames) {
        Collection<UserInfo> colInfos = USERS.values();
        for (String userName : userNames) {
            String lowerCaseuserName = userName.toLowerCase();
            for (UserInfo userInfo : colInfos) {
                if (lowerCaseuserName.equals(userInfo.username)) {
                    sendMessageToUser(userInfo.username, message);
                }
            }
        }
    }

    /**
     * Send a message to the web browser of a user. The user can receive the
     * message only when he is online. If he is offline, he will receive the
     * message when he logged in next time.
     *
     * @param username
     * @param message
     */
    public void sendMessageToUser(String username, MQMessage message) {

        if (USERS.containsKey(username)) {
            UserInfo userInfo = USERS.get(username);
            if (userInfo != null) {
                sendMessageTo(userInfo, message);
            }
        }
    }

    /**
     * Send a message to the web browser of a user. The user can receive the
     * message only when he is online. If he is offline, he will receive the
     * message when he logged in next time.
     *
     * @param message
     */
    public void sendMessageToWebUser(MQMessage message) {
        UserLoginMsg alarmMsg = (UserLoginMsg) message.getBody();
        String username = alarmMsg.getUserName().toLowerCase();
        if (USERS.containsKey(username)) {
            UserInfo userInfo = USERS.get(username);
            if (userInfo != null) {
                sendMessageTo(userInfo, message);
            }
            this.removeWebSocketSession(username);
        }
    }

    /**
     * Send a message to the web browser of users with a roleType.
     *
     * @param roleType
     * @param message
     */
    public void sendMessageToRole(Byte roleType, MQMessage message) {

        Collection<UserInfo> colInfos = USERS.values();
        for (UserInfo userInfo : colInfos) {
            if (userInfo.roleType.equals(roleType)) {
                sendMessageTo(userInfo, message);
            }
        }
    }

    private void sendMessageTo(UserInfo userInfo, MQMessage message) {
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
            logger.error("&&&&&&&&&&&&&&&&---------receive message END to mq-7-1--Error ,messeage=====" + e.getMessage());
            e.printStackTrace();
        }
    }

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

    /**
     * 消息处理
     * @param message
     * @return
     */
    public TextMessage messageToTextMessage(MQMessage message) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONObject jsonObject = null;

        switch (message.getMessageType().value) {
            case 1:
                VehicleStructureMsg model = (VehicleStructureMsg) message.getBody();
                jsonObject = JSONObject.fromObject(model, jsonConfig);
                break;
            case 2:
                //黑名单报警
                jsonObject = JSONObject.fromObject(message, jsonConfig);
                break;
            case 3:
                //首页展示
                jsonObject = JSONObject.fromObject(message, jsonConfig);
                break;
            case 4:
                //登录
                jsonObject = JSONObject.fromObject(message, jsonConfig);
                break;
            case 5:
                //删除用户
                jsonObject = JSONObject.fromObject(message, jsonConfig);
                break;
            case 6:
                //重置用户
                jsonObject = JSONObject.fromObject(message, jsonConfig);
                break;
            case 7:
                //修改权限
                jsonObject = JSONObject.fromObject(message, jsonConfig);
                break;
            default:

                break;
        }

        if (null != jsonObject) {
            return new TextMessage(jsonObject.toString());
        } else {
            return null;
        }
    }

    private MQMessage textMessageToMessage(WebSocketMessage message) {
        if (!(message instanceof TextMessage)) {
            return null;
        }
        TextMessage textMessage = (TextMessage) message;
        JSONObject jsonObject = JSONObject.fromObject(new String(textMessage.asBytes()));
        return (MQMessage) JSONObject.toBean(jsonObject, MQMessage.class);
    }

}

package com.car.orbit.orbitservice.websocket;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.constant.WebSocketConstant;
import com.car.orbit.orbitservice.websocket.message.MessageType;
import com.car.orbit.orbitservice.websocket.message.WebSocketMsg;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.socket.*;

/**
 * @Title: WebscoketManager
 * @Package: com.car.trunk.websocket
 * @Description: webscoket 处理类（建立连接，断开连接等）
 * @Author: monkjavaer
 * @Data: 2019/1/16 16:20
 * @Version: V1.0
 */
@Component
public class SystemWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    @Autowired
    private WebSocketManager webSocketManager;


    private SystemWebSocketHandler() {
    }


    /**
     * webscoket建立建立连接后
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            logger.info("====afterConnectionEstablished====webscoket建立建立连接后...");
            String username = (String) session.getAttributes().get(WebSocketConstant.WEBSOCKET_USERNAME);
            String token = (String) session.getAttributes().get(WebSocketConstant.WEBSOCKET_TOKEN);
            RequestContext context = (RequestContext) session.getAttributes().get(WebSocketConstant.WEBSOCKET_CONTEXT);
            UserInfo userInfo = new UserInfo(username, session, context,token);
            webSocketManager.addUser(username, userInfo);
            webSocketManager.addSession(session, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Receive a message from the web browser
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage message) throws Exception {
//        logger.info(DateUtils.format(new Date()) +"接收到客户端:" + session.getId() + "发送的消息:" + message.getPayload().toString());
        WebSocketMsg webSocketMsg = new WebSocketMsg();
        webSocketMsg.setType(MessageType.heart);
        webSocketMsg.setBody("");
        JSONObject jsonObject = JsonUtils.toJSONObject(webSocketMsg);
        session.sendMessage(new TextMessage(jsonObject.toString()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info("====handleTransportError===");
        try {
            if (session.isOpen()) {
                session.close();
            }
            logger.info("===WebSocket session removed for some error===");
            webSocketManager.removeWebSocketSession(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.info("===WebSocket session removed===");
        webSocketManager.removeWebSocketSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
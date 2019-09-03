package com.base.springboot.car.Web.src.main.java.com.car.trunk.websocket;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities.Constants;
import com.base.springboot.car.Base.src.main.java.com.car.base.message.MQMessage;
import com.base.springboot.car.Base.src.main.java.com.car.base.message.MessageType;
import net.sf.json.JSONObject;
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
    private WebscoketManager webscoketManager;


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
            String username = (String) session.getAttributes().get(Constants.WEBSOCKET_USERNAME);
            RequestContext context = (RequestContext) session.getAttributes().get(Constants.WEBSOCKET_CONTEXT);
            UserInfo userInfo = new UserInfo(username, session, null, null);
            webscoketManager.addUser(username, userInfo);
            webscoketManager.addSession(session, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Receive a message from the web browser
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        MQMessage  mqMessage = new MQMessage();
        mqMessage.setMessageType(MessageType.HEART_BEAT);
        mqMessage.setBody("");
        JSONObject jsonObject = JSONObject.fromObject(mqMessage);
        session.sendMessage(new TextMessage(jsonObject.toString()));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        try {
            if (session.isOpen()) {
                session.close();
            }
            System.out.println("WebSocket session removed for some error");
            webscoketManager.removeWebSocketSession(session);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        //System.out.println("WebSocket session removed");
        //removeWebSocketSession(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
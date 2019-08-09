package com.car.orbit.orbitservice.websocket.message;

import java.io.Serializable;

/**
 * @Title: WebSocketMsg
 * @Package: com.car.orbit.orbitservice.constant
 * @Description: WebSocket消息体
 * @Author: monkjavaer
 * @Data: 2019/4/4 18:18
 * @Version: V1.0
 */
public class WebSocketMsg implements Serializable {
    /**
     * 消息类型
     */
    MessageType type;
    /**
     * 消息体
     */
    Serializable body;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Serializable getBody() {
        return body;
    }
    public void setBody(Serializable body) {
        this.body = body;
    }

}


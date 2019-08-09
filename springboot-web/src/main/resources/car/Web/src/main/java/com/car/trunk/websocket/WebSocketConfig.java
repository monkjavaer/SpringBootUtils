package com.car.trunk.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Title: WebscoketManager
 * @Package: com.car.trunk.websocket
 * @Description: 启动时扫描此类
 * @Author: monkjavaer
 * @Data: 2019/1/16 16:20
 * @Version: V1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(systemWebSocketHandler, "/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor());

        registry.addHandler(systemWebSocketHandler, "/sockjs/webSocketServer").addInterceptors(new WebSocketHandshakeInterceptor())
                .withSockJS();
    }
}

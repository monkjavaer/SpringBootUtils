package com.base.springboot.car.Web.src.main.java.com.car.trunk.websocket;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.constant.AppConstant;
import com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.bo.UserInfoBO;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Service
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor  {

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
          if (httpRequest != null) {
              UserInfoBO user = (UserInfoBO) httpRequest.getSession().getAttribute("session_user_key");
              attributes.put(AppConstant.WEBSOCKET_USERNAME, user.getUserName());
              RequestContext context = new RequestContext(httpRequest);
              attributes.put(AppConstant.WEBSOCKET_CONTEXT, context);
          }

     }
     return true;
 }

 @Override
 public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, 
		 Exception exception) {

 }
}

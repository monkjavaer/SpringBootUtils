package com.base.springboot.car.Base.src.main.java.com.car.base.message;


import java.io.Serializable;

public class MQMessage  implements Serializable {
	MessageType messageType;
	Serializable body;
	
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	public Serializable getBody() {
		return body;
	}
	public void setBody(Serializable body) {
		this.body = body;
	}

}

package com.base.springboot.car.Base.src.main.java.com.car.base.common.sms;

public class SMSMessage {
	//source name of the message, always be 'OMS'
	private String sourceName;
	//phone number
	private String phoneNumber;
	//sms message
	private String message;
	
	public SMSMessage() {
		
	}
	
	public SMSMessage(String sourceName, String phoneNumber, String message) {
		this.sourceName = sourceName;
		this.phoneNumber = phoneNumber;
		this.message = message;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

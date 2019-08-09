package com.car.base.common.sms;

import com.car.base.common.mail.EmailSMSConfig;
import net.sf.json.JSONObject;


public class SMSSender {
	
	private EmailSMSConfig config = null;
	
	private String smsServiceUrl = null;
	
	public SMSSender() {
		config = EmailSMSConfig.getInstance();
		smsServiceUrl = config.getSmsServiceUrl();
	}
	
	public boolean send(SMSMessage message) {
		return send(smsServiceUrl, message);
	}
	
	protected boolean send(String url, SMSMessage message) {
		JSONObject json = JSONObject.fromObject(message);
		try {
			String response = new HttpRequestor().post(url, json.toString());
			System.out.println(response);
			
			JSONObject jsonOBJ = JSONObject.fromObject(response);
			String status = jsonOBJ.getString("status");
			return "0".equalsIgnoreCase(status);



			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}

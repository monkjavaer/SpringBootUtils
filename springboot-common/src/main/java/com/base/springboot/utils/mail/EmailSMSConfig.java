package com.base.springboot.utils.mail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EmailSMSConfig {
	private Properties prop = null;
	//email server domain name
	private String defaultMailSmtpHost = "";
	//email server user name
	private String defaultMailSmtpUsername = "";
	//email server password
	private String defaultMailSmtpPassword = "";
	//email server need authentication?
	private String defaultMailSmtpAuth = "";
	//email server use ssl?
	private String defaultMailSmtpSslEnable = "";
	//ant telephone number
	private static String servicePhoneNumber = "";
	
	
	protected static EmailSMSConfig instance = null;
	//registration notice
	private static String noticeMailSubject = "";
	
	private static String noticeMailBody = "";
	
	//registration cancel
	private String cancelMailSubject = "";
	
	private String cancelMailBody = "";
	
	//registration notice
	private String lowInventMailSubject = "";
	
	private String lowInventMailBody = "";
	
	//sms service
	private String smsServiceUrl = "";
	
	private String smsServiceSourceName = "";	
	
	private String noticeSMS = "";
	
	private String cancelSMS = "";
	
	
	private EmailSMSConfig() {
		prop = new Properties();
		try {
			readProperties();
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static synchronized EmailSMSConfig getInstance() {
		if(instance == null) {
			instance = new EmailSMSConfig();
		}
		return instance;
	}
	
	protected synchronized void readProperties() throws Exception{
		boolean hasException = false;
		String messageException = "";
		


		String temp = null;
		temp = "mail.smtp.host";
		if(temp == null) {
			prop.put("mail.smtp.host", defaultMailSmtpHost);
			hasException = true;
			messageException += "mail.smtp.host,";
		}else{
			prop.put("mail.smtp.host", temp);
		}

//		temp = ConfigData.getPropertyByKey("mail.smtp.port");
//		if(!StringUtil.isNullOrEmpty(temp)){
//			prop.put("mail.smtp.port", temp);
//		}else{
//			prop.put("mail.smtp.port","25");
//		}

		temp = "mail.smtp.username";
		if(temp == null) {
			prop.put("mail.smtp.username", defaultMailSmtpUsername);
			hasException = true;
			messageException += "mail.smtp.username,";
		}else{
			prop.put("mail.smtp.username", temp);
		}


		temp = "mail.smtp.password";
		if(temp == null) {
			prop.put("mail.smtp.password", defaultMailSmtpPassword);
			hasException = true;
			messageException += "mail.smtp.password,";

		}else{
			prop.put("mail.smtp.password", temp);
		}


		temp = "mail.smtp.auth";
		if(temp == null) {
			prop.put("mail.smtp.auth", defaultMailSmtpAuth);
			hasException = true;
			messageException += "mail.smtp.auth,";
		}else{
			prop.put("mail.smtp.auth", temp);
		}

		temp = "mail.smtp.ssl.enable";
		if(temp == null) {
			prop.put("mail.smtp.ssl.enable", defaultMailSmtpSslEnable);
			hasException = true;
			messageException += "mail.smtp.ssl.enable,";

		}else{
			prop.put("mail.smtp.ssl.enable", temp);
		}

		temp = "sms.service.url";//sms.service.url=
		if(temp == null) {
			prop.put("sms.service.url", "");
			hasException = true;
			messageException += "sms.service.url,";
		} else {
			prop.put("sms.service.url", temp);
			smsServiceUrl = temp;
		}


		temp = "sms.service.source.name";
		if(temp == null) {
			prop.put("sms.service.source.name", "OMS");
			hasException = true;
			messageException += "sms.service.source.name,";
		} else {
			prop.put("sms.service.source.name", temp);
			smsServiceSourceName = temp;
		}

		temp ="service.phone";
		if(temp == null) {
			prop.put("service.phone", "");
			hasException = true;
			messageException += "service.phone,";
		} else {
			prop.put("service.phone", temp);
			servicePhoneNumber = temp;
		}


		if(hasException) {
            throw new Exception("ERROR: configuration ");
        }
			

		
	}
	
	protected synchronized void readNoticeTemplate() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream("Constants.REGIST_NOTICE_MAIL_PATH"), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line;
		}
		in.close();
		int indStart = content.indexOf("<title>");
		int indEnd = content.indexOf("</title>");
		noticeMailSubject = content.substring(indStart + 7, indEnd);
		noticeMailBody = content;

	}
	
	protected synchronized void readLowInventoryNoticeTemplate() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream("Constants.LOW_INVENTORY_MAIL_PATH"), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line;
		}
		in.close();
		int indStart = content.indexOf("<title>");
		int indEnd = content.indexOf("</title>");
		lowInventMailSubject = content.substring(indStart + 7, indEnd);
		lowInventMailBody = content;
		
	}	

	
	protected synchronized void readCancelTemplate() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream("Constants.REGIST_CANCEL_MAIL_PATH"), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line;
		}
		int indStart = content.indexOf("<title>");
		int indEnd = content.indexOf("</title>");
		cancelMailSubject = content.substring(indStart + 7, indEnd);
		cancelMailBody = content;
		
	}
	
	protected synchronized void readNoticeSMS() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream("Constants.REGIST_NOTICE_SMS_PATH"), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line + "\n";
		}
		noticeSMS = content;
		
	}
	
	
	protected synchronized void readCancelSMS() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream("Constants.REGIST_CANCEL_SMS_PATH"), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line + "\n";
		}
 
		cancelSMS = content;
		
	}
	
	public synchronized Properties getProp() {
		return prop;
	}



//	public static void main(String[]args) {
//		EmailSMSConfig config = new EmailSMSConfig();
//		try {
//			config.readCancelTemplate();
//			config.readNoticeTemplate();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}

	public synchronized String getNoticeMailSubject() {
		return noticeMailSubject;
	}

	public synchronized String getNoticeMailBody() {
		return noticeMailBody;
	}

	public static Map<String, String> getNoticeMailContent(String locale) throws IOException {
		Map<String, String> map = new HashMap<>();
		InputStreamReader reader = new InputStreamReader(new FileInputStream("Constants.REGIST_NOTICE_MAIL_PATH"+"-"+locale), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line;
		}
		in.close();
		int indStart = content.indexOf("<title>");
		int indEnd = content.indexOf("</title>");
		noticeMailSubject = content.substring(indStart + 7, indEnd);
		noticeMailBody = content;
		map.put("subject", noticeMailSubject);
		map.put("body", noticeMailBody);
		return map;
	}
	
	public synchronized String getLowInventoryNoticeMailBody() {
		return lowInventMailBody;
	}
	
	public synchronized String getLowInventoryNoticeMailSubject() {
		return lowInventMailSubject;
	}

	public synchronized String getCancelMailSubject() {
		return cancelMailSubject;
	}

	public synchronized String getCancelMailBody() {
		return cancelMailBody;
	}
 

	public String getNoticeSMS() {
		return noticeSMS;
	}

	public String getCancelSMS() {
		return cancelSMS;
	}

	public String getSmsServiceUrl() {
		return smsServiceUrl;
	}

	public String getSmsServiceSourceName() {
		return smsServiceSourceName;
	}

	public static String getServicePhoneNumber() {
		return servicePhoneNumber;
	}
 
}

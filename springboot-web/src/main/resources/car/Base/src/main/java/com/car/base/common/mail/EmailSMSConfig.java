package com.car.base.common.mail;

import com.car.base.common.exception.ConfigException;
import com.car.base.common.utilities.Constants;

import java.io.*;
import java.util.Properties;

public class EmailSMSConfig {
	private Properties prop = null;
	//email server domain name
	private String defaultMailSmtpHost= "smtp.Car.com.cn";
	//email server user name
	private String defaultMailSmtpUsername = "monkjavaer";
	//email server password
	private String defaultMailSmtpPassword = "ce1128";
	//email server need authentication?
	private String defaultMailSmtpAuth = "true";
	//email server use ssl?
	private String defaultMailSmtpSslEnable = "true";
	
	protected static EmailSMSConfig instance = null;
	//registration notice
	private String noticeMailSubject = "";
	
	private String noticeMailBody = "";
	
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
			readNoticeTemplate();
			readCancelTemplate();
			readLowInventoryNoticeTemplate();
			readNoticeSMS();
			readCancelSMS();
		} catch(ConfigException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		}

	}
	
	public static synchronized EmailSMSConfig getInstance() {
		if(instance == null) {
			instance = new EmailSMSConfig();
		}
		return instance;
	}
	
	protected synchronized void readProperties() throws ConfigException{
		InputStream in = null;
		boolean hasException = false;
		String messageException = "";
		
		try{
			//in = new BufferedInputStream(new FileInputStream("file-server.properties"));
			in = getClass().getResourceAsStream("/email-sms.properties");
			prop.load(in);

			String temp = null;
			temp = prop.getProperty("mail.smtp.host");
			if(temp == null) {
				prop.put("mail.smtp.host", defaultMailSmtpHost);
				hasException = true;
				messageException += "mail.smtp.host,";
			}
			
			
			temp = prop.getProperty("mail.smtp.username");
			if(temp == null) {
				prop.put("mail.smtp.username", defaultMailSmtpUsername);
				hasException = true;
				messageException += "mail.smtp.username,";
			}
		
			
			temp = prop.getProperty("mail.smtp.password");
			if(temp == null) {
				prop.put("mail.smtp.username", defaultMailSmtpPassword);
				hasException = true;
				messageException += "mail.smtp.password,";
 
			}
			
			
			temp = prop.getProperty("mail.smtp.auth");
			if(temp == null) {
				prop.put("mail.smtp.auth", defaultMailSmtpAuth);
				hasException = true;
				messageException += "mail.smtp.auth,";				
			}
			
			 
			temp = prop.getProperty("mail.smtp.ssl.enable");
			if(temp == null) {
				prop.put("mail.smtp.ssl.enable", defaultMailSmtpSslEnable);
				hasException = true;
				messageException += "mail.smtp.ssl.enable,";	
				
			}
			
			temp = prop.getProperty("sms.service.url");//sms.service.url=
			if(temp == null) {
				prop.put("sms.service.url", "");//TODO no default
				hasException = true;
				messageException += "sms.service.url,";	
			} else {
				smsServiceUrl = temp;
			}
 
			
			temp = prop.getProperty("sms.service.source.name");
			if(temp == null) {
				prop.put("sms.service.source.name", "OMS");//TODO no default
				hasException = true;
				messageException += "sms.service.source.name,";	
			} else {
				smsServiceSourceName = temp;
			}
			
			in.close();
			
			if(hasException) {
                throw new ConfigException("ERROR: configuration " + messageException.substring(0, messageException.length() - 1)
                        + " in email-sms.properties", "email-sms.properties");
            }
			
		}catch(FileNotFoundException ex) {
			ex.printStackTrace();
		}catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
		
	}
	
	protected synchronized void readNoticeTemplate() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(Constants.REGIST_NOTICE_MAIL_PATH), "UTF-8");
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
		//System.out.println(content);
		
	}
	
	protected synchronized void readLowInventoryNoticeTemplate() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(Constants.LOW_INVENTORY_MAIL_PATH), "UTF-8");
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
		//System.out.println(content);
		
	}	

	
	protected synchronized void readCancelTemplate() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(Constants.REGIST_CANCEL_MAIL_PATH), "UTF-8");
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
		//System.out.println(content);
		
	}
	
	protected synchronized void readNoticeSMS() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(Constants.REGIST_NOTICE_SMS_PATH), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line + "\n";
		}
 
		noticeSMS = content;
		//System.out.println(content);
		
	}
	
	
	protected synchronized void readCancelSMS() throws IOException{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(Constants.REGIST_CANCEL_SMS_PATH), "UTF-8");
		BufferedReader in = new BufferedReader(reader);
		String line;
		String content = "";
		while((line = in.readLine()) != null) {
			content += line + "\n";
		}
 
		cancelSMS = content;
		//System.out.println(content);
		
	}
	
	public synchronized Properties getProp() {
		return prop;//Properties is subclass of HashTable, which is synchronized.
	}
	
 
 
	public static void main(String[]args) {
		EmailSMSConfig config = new EmailSMSConfig();
		try {
			config.readCancelTemplate();
			config.readNoticeTemplate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public synchronized String getNoticeMailSubject() {
		return noticeMailSubject;
	}

	public synchronized String getNoticeMailBody() {
		return noticeMailBody;
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
 

 
 
}

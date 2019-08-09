package com.car.base.common.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailSender extends Thread{
	private enum SendMode {
		/**
		 * SIMPLE_ONE
		 */
		SIMPLE_ONE,
		SIMPLE_MANY,
		COMPLEX_MESSAGE
	}
	
	private SendMode mode = SendMode.COMPLEX_MESSAGE;
	
	private EmailSMSConfig config = null;
	/**
	 * Properties file
	 */
	private final Properties props = new Properties();
	/**
	 * Email session
	 */
	private Session session;
	/**
	 * Mail server authentication
	 */
	private MailAuthenticator authenticator;
	/**
	 * encoding type
	 */
	private String encoding = "utf-8";
	/**
	 * mail to send
	 */
	private MailMessage mailMessage = null;
	
	List<String> recipients = new ArrayList<String>();
	
	List<String> ccs = new ArrayList<String>();
	
	String subject;
	
	String content;
	
	
	/**
	 * 
	 * @param smtpHost - domain name of the smtp server host
	 * @param username - a full email address, e.g. billgates@microsoft.com
	 * @param password 
	 */
	public MailSender() {
		init();
	}
	
	
	
	/**
	 * 
	 * @param smtpHost - domain name of the smtp server host
	 * @param username - a full email address, e.g. billgates@microsoft.com
	 * @param password 
	 */
	public MailSender(MailMessage mailMessage) {
		this.mode = SendMode.COMPLEX_MESSAGE;
		init();
		this.mailMessage = mailMessage;
		this.setName("mailsender");
		start();
	}
	
	public MailSender(List<String> recipients, List<String> ccs, String subject, String content) {
		this.mode = SendMode.SIMPLE_MANY;
		init();
		this.recipients = recipients;
		this.ccs = ccs;
		this.subject = subject;
		this.content = content;
		start();		
	}
	
	public MailSender(String recipient, String cc, String subject, String content)  {
		this.mode = SendMode.SIMPLE_ONE;
		init();
		this.recipients.add(recipient);
		this.ccs.add(cc);
		this.subject = subject;
		this.content = content;
		start();		
	}	
	
	private void init() {
		config = EmailSMSConfig.getInstance();
		
		Properties props = config.getProp(); 
 
		String username = props.getProperty("mail.smtp.username");
		String password = props.getProperty("mail.smtp.password");
		authenticator = new MailAuthenticator(username, password);
		
		session = Session.getInstance(props, authenticator);
		//session.setDebug(props.getProperty("mail.smtp.debug").equals("true"));//TODO  comment it when release
	}
	
	@Override
    public void run() {
		try {
			if(mode == SendMode.SIMPLE_ONE) {
				send(recipients.get(0), ccs.get(0), subject, content);
			} else if(mode == SendMode.SIMPLE_MANY) {
				send(recipients, ccs, subject, content);				
			} if(mode == SendMode.COMPLEX_MESSAGE) {
				send(mailMessage);
			}
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Send an email to only one email address 
	 * @param recipient - a valid email address
	 * @param subject - a valid email address, optional, can be null
	 * @param content 
	 * @throws AddressException
	 * @throws MessagingException
	 */
	protected void send(String recipient, String cc, String subject, String content) 
			throws MessagingException {
		//create mime email
		final MimeMessage message = new MimeMessage(session);
		//set sender email
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		//set receivers' email address
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		//set cc' email address
		if(cc!= null && !"".equals(cc)) {
			message.setRecipient(RecipientType.CC, new InternetAddress(cc));
		}
		
		//set the subject
		message.setSubject(subject);
		//set the content
		message.setContent(content.toString(), "text/html;charset=" + encoding);
		//send
		Transport.send(message);
		
		
	}
	
	/**
	 * Send email to multiple recipients and cc receivers
	 * @param recipient
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	protected void send(List<String> recipients, List<String> ccs, String subject, String content) throws MessagingException {
		//create mime email
		final MimeMessage message = new MimeMessage(session);
		//set sender email
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		//set receivers' email
		InternetAddress recipientArr[] = new InternetAddress[recipients.size()];
		int i = 0;
		for(String address: recipients) {
			recipientArr[i++] = new InternetAddress(address);
		}
		message.setRecipients(RecipientType.TO, recipientArr);
		
		if(ccs != null && ccs.size() != 0) {
			//set cc' email
			InternetAddress ccsArr[] = new InternetAddress[ccs.size()];
			int j = 0;
			for(String address: ccs) {
				ccsArr[j++] = new InternetAddress(address);
			}
			message.setRecipients(RecipientType.CC, ccsArr);			
		}
		
		//set the subject
		message.setSubject(subject);
		//set the content
		message.setContent(content.toString(), "text/html;charset=" + encoding);
		//send
		Transport.send(message);
		
		
	}	
	
	/**
	 * Create a MimeBodyPart object wrap the file specified by the parameter filename
	 * @param filename - file name of an attachement
	 * @return
	 * @throws MessagingException 
	 * @throws Exception
	 */
	protected MimeBodyPart createAttachment(String filename) throws MessagingException  {
		MimeBodyPart attachmentPart = new MimeBodyPart();
		
		//FileDataSource fds = new FileDataSource(filename);
		FileDataSource fds = null;
		try {
			fds = new FileDataSource(new File(new URI(filename)));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		attachmentPart.setDataHandler(new DataHandler(fds));
		System.out.println(fds.getName());
		 
		//To resolve the problem of chinese file name, MimeUtility.encodeWord should be used
		try {
			attachmentPart.setFileName(MimeUtility.encodeWord(fds.getName()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return attachmentPart;
	}
	
	protected MimeBodyPart createContent(String body, List<String> contentIDs, List<String> contentFilenames) throws MessagingException {
		//Save the final body 
		MimeBodyPart contentBody = new MimeBodyPart();
		//combine the string and the image files, this will be store in contentBody
		MimeMultipart contentMulti = new MimeMultipart("related");
		
		//Text part of body
		MimeBodyPart textBody = new MimeBodyPart();
		textBody.setContent(body, "text/html;charset=" + encoding);
		contentMulti.addBodyPart(textBody);
		
		//Image part of body
		for(int i = 0; i < contentFilenames.size(); i ++) {
			String filename = contentFilenames.get(i);
			String contentID = contentIDs.get(i);
			MimeBodyPart imageBody = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(filename);
			imageBody.setDataHandler(new DataHandler(fds));
			imageBody.setContentID(contentID);
			contentMulti.addBodyPart(imageBody);
		}
		
		contentBody.setContent(contentMulti);
		
		return contentBody;
	}
	
	
	
	
	public void send(MailMessage mailMessage) throws MessagingException {
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		InternetAddress[] recipients = new InternetAddress[mailMessage.getRecipients().size()];
		for(int i = 0; i < mailMessage.getRecipients().size(); i ++) {
			recipients[i] = new InternetAddress(mailMessage.getRecipients().get(i));
		}
		
		message.setRecipients(RecipientType.TO, recipients);
		
		if(mailMessage.getCcs() != null && mailMessage.getCcs().size() != 0) {
			InternetAddress[] ccs = new InternetAddress[mailMessage.getCcs().size()];
			for(int i = 0; i < mailMessage.getCcs().size(); i ++) {
				ccs[i] = new InternetAddress(mailMessage.getCcs().get(i));
			}
			
			message.setRecipients(RecipientType.CC, ccs);
		}
		
		message.setSubject(mailMessage.getSubject());
 
		MimeBodyPart content = createContent(mailMessage.getContent(), mailMessage.getContentIDs(), mailMessage.getContentFiles());
		MimeMultipart allPart = new MimeMultipart("mixed");
		allPart.addBodyPart(content);
		
		if(mailMessage.getAttachmentFiles() != null) {
			for(String filename: mailMessage.getAttachmentFiles()) {
				MimeBodyPart attachment = createAttachment(filename);
				allPart.addBodyPart(attachment);
			}
		}
			
		message.setContent(allPart);
		message.saveChanges();
		try {
			message.writeTo(new FileOutputStream("D:\\workspace\\doc\\withAttachmentMail.eml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Transport transport = session.getTransport("smtps");
		Transport.send(message);
		
	}
	
	@Override
    protected void finalize() {
		//System.out.println("********************Delete*******************");
	}
	
	//TODO
	/**
	 * Change this method, read the body from a html file
	 * @return
	 */
	protected String getBody() {
		return config.getNoticeMailBody();
	}
	/*
	protected String getBody() {
		String body = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">" +
"<head>" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />" +
"<title>无标题文档</title>" +
"<style type=\"text/css\">" +
"<!--" +
".STYLE4 {font-family: Geneva, Arial, Helvetica, sans-serif}" +
".STYLE5 {font-family: Geneva, Arial, Helvetica, sans-serif; font-size: 18px; }" +
"-->" +
".tdmiddle{" +
"BORDER-RIGHT: 0px solid; " +
"border-top:0px solid; " +
"BORDER-LEFT: 0px solid;" +
"BORDER-BOTTOM: 0px solid;" +
"}" +
".tdtop{" +
"border-top: 1px solid; " +
"BORDER-LEFT: 1px solid;" +
"BORDER-RIGHT: 1px solid;" +
"BORDER-BOTTOM: 0px solid;" +
"}" +
".tdrow{" +
"BORDER-LEFT: 1px solid;" +
"BORDER-RIGHT: 1px solid;" +
"border-top:0px solid;" + 
"BORDER-BOTTOM: 0px solid;" +
"}" +
".tdbottom{" +
"border-top:0px solid; " +
"border-BOTTOM: 1px solid; " +
"BORDER-LEFT: 1px solid;" +
"BORDER-RIGHT: 1px solid;" +
"}" +
".tdleft{" +
"border-top:0px solid; " +
"border-BOTTOM: 0px solid; " +
"BORDER-LEFT: 1px solid;" +
"BORDER-RIGHT: 0px solid;" +
"}" +
".tdright{" +
"border-top:0px solid; " +
"border-BOTTOM: 0px solid; " +
"BORDER-LEFT: 0px solid;" +
"BORDER-RIGHT: 1px solid;" +
"}" +
"</style>" +
"</head>" +

"<body>" +
"<table width=\"400\" border=\"1\" cellspacing=\"0\" cellpadding=\"1\">" +
"  <tr>" +
"    <td colspan=\"4\" scope=\"col\" class=\"tdtop\"><img src=\"cid:logo_jpg\" width=\"600\" height=\"75\" /></td>" +
"  </tr>" +
"    <tr>" +
"    <td colspan=\"4\" bordercolor=\"#FFFFFF\" class=\"tdrow\"><div align=\"center\" class=\"STYLE5\">预约通知</div></td>" +
"  </tr>" +
"  <tr>" +
"    <td colspan=\"2\" bordercolor=\"#FFFFFF\" class=\"tdrow\"><span class=\"STYLE4\">尊敬的先生：</span></td>" +
"    <td width=\"322\" bordercolor=\"#FFFFFF\" class=\"tdmiddle\">&nbsp;</td>" +
"    <td width=\"169\" bordercolor=\"#FFFFFF\" class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td width=\"51\" class=\"tdleft\">&nbsp;</td>" +
"    <td colspan=\"3\" class=\"tdright\"><span class=\"STYLE4\">已为您/贵公司的车辆{VEHICLE_ID}预约好{REGISTRATION_TYPE}服务。</span></td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td colspan=\"2\" bordercolor=\"#FFFFFF\" class=\"tdmiddle\"><span class=\"STYLE4\"></span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td colspan=\"2\" class=\"tdmiddle\"><span class=\"STYLE4\">服务编号：{REGISTRATION_NO}</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td colspan=\"2\" class=\"tdmiddle\"><span class=\"STYLE4\">到达时间：{START_TIME}-{END_TIME}</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td colspan=\"2\" class=\"tdmiddle\"><span class=\"STYLE4\">服务车场</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td width=\"46\" class=\"tdmiddle\">&nbsp;</td>" +
"    <td class=\"tdmiddle\"><span class=\"STYLE4\">名称：{STATION_NAME}</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td class=\"tdmiddle\">&nbsp;</td>" +
"    <td class=\"tdmiddle\"><span class=\"STYLE4\">电话：{PHONE}</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td class=\"tdmiddle\">&nbsp;</td>" +
"    <td class=\"tdmiddle\"><span class=\"STYLE4\">联系人：{DIRECTOR}</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td class=\"tdmiddle\">&nbsp;</td>" +
"    <td class=\"tdmiddle\"><span class=\"STYLE4\">地址：{STATION_ADDRESS}</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td class=\"tdmiddle\">&nbsp;</td>" +
"    <td class=\"tdmiddle\">&nbsp;</td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td class=\"tdleft\">&nbsp;</td>" +
"    <td colspan=\"2\" class=\"tdmiddle\"><span class=\"STYLE4\">ANT服务电话：12345</span></td>" +
"    <td class=\"tdright\">&nbsp;</td>" +
"  </tr>" +
"  <tr>" +
"    <td colspan=\"4\"><a href=\"https://www.google.com/maps/d/viewer?mid=z7ZZqiUhbpsw.kP91DgCLYqw4\"><img src=\"cid:map_jpg\" width=\"600\" height=\"443\" /></a></td>" +
"  </tr>" +
"</table>" +
"</body>" +
"</html>";
		return body;
	}*/

}

package com.base.springboot.utils.mail;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailSender {
	private static final Logger logger = LoggerFactory.getLogger(MailSender.class);
	
	private enum SendMode {
		/**
		 * 对单人邮件
		 */
		SIMPLE_ONE,
		/**
		 * 对多人邮件
		 */
		SIMPLE_MANY,
		/**
		 * 复杂邮件
		 */
		COMPLEX_MESSAGE
	}
	
	private SendMode mode = SendMode.COMPLEX_MESSAGE;
	
	private EmailSMSConfig config = null;
//	/**
//	 * Properties file
//	 */
//	private final Properties props = new Properties();
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
	 */
	public MailSender() {
		init();
	}
	
	
	
	/**
	 * 
	 */
	public MailSender(MailMessage mailMessage) throws MessagingException {
		this.mode = SendMode.COMPLEX_MESSAGE;
		init();
		this.mailMessage = mailMessage;

		logger.info("MailSender runs...");
		if(mode == SendMode.SIMPLE_ONE) {
			send(recipients.get(0), ccs.get(0), subject, content);
		} else if(mode == SendMode.SIMPLE_MANY) {
			send(recipients, ccs, subject, content);
		} if(mode == SendMode.COMPLEX_MESSAGE) {
			send(mailMessage);
		}
		logger.info("MailSender runs over...");

	}

	
	private void init() {
		logger.debug("email init start ...");
		config = EmailSMSConfig.getInstance();
		
		Properties props = config.getProp();
		String username = "mail.smtp.username";
		String password = "mail.smtp.password";
		authenticator = new MailAuthenticator(username, password);
		
		session = Session.getInstance(props, authenticator);
		
		logger.debug("email init end ...");
	}
	
	public void run() {
		try {
			logger.info("MailSender runs...");
			if(mode == SendMode.SIMPLE_ONE) {
				send(recipients.get(0), ccs.get(0), subject, content);
			} else if(mode == SendMode.SIMPLE_MANY) {
				send(recipients, ccs, subject, content);				
			} if(mode == SendMode.COMPLEX_MESSAGE) {
				send(mailMessage);
			}
			logger.info("MailSender runs over...");
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
			throws AddressException, MessagingException {
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
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	protected void send(List<String> recipients, List<String> ccs, String subject, String content) throws AddressException, MessagingException {
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
		FileDataSource fds = null;
		try {
			fds = new FileDataSource(new File(new URI(filename)));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		attachmentPart.setDataHandler(new DataHandler(fds));
		try {
			attachmentPart.setFileName(MimeUtility.encodeWord(fds.getName()));
		} catch (UnsupportedEncodingException e) {
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
			//imageBody.setContentID("<"+contentID+">");
			imageBody.setHeader("Content-ID", "<"+contentID+">");
			imageBody.setDisposition(MimeBodyPart.INLINE);
			imageBody.setHeader("Content-Type", "image/png");
			contentMulti.addBodyPart(imageBody);
		}
		
		contentBody.setContent(contentMulti);
		
		return contentBody;
	}
	
	
	
	
	public void send(MailMessage mailMessage) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(authenticator.getUsername()));

		List<String> reciString = new ArrayList<>();
		for(int i = 0; i < mailMessage.getRecipients().size(); i ++) {
			if(!StringUtils.isEmpty(mailMessage.getRecipients().get(i))) {
				reciString.add(mailMessage.getRecipients().get(i));
			}
		}

		InternetAddress[] recipients = new InternetAddress[reciString.size()];
		for(int i = 0; i < reciString.size(); i ++) {
			recipients[i] = new InternetAddress(reciString.get(i));
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

		
		logger.info("send transport runs...");
		Transport.send(message);


		logger.info("send transport runs over...");		
	}
	
	


}

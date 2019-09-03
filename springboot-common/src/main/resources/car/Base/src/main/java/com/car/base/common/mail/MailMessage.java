package com.base.springboot.car.Base.src.main.java.com.car.base.common.mail;

import java.util.List;

public class MailMessage {
	private List<String> recipients;//接收人邮件地址列表
	private List<String> ccs;//抄送人邮件地址列表
	
	private String subject;//主题
	private String content;   //邮件体
	private List<String> contentIDs;//邮件体中的content id
	private List<String> contentFiles;//邮件体各content id对应的图片附件
	private List<String> attachmentFiles;//附件文件名列表，每个元素为一个本地文件的绝对路径名，含文件名
	
	public List<String> getRecipients() {
		return recipients;
	}
	
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}
	
	public List<String> getCcs() {
		return ccs;
	}
	
	public void setCcs(List<String> ccs) {
		this.ccs = ccs;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getContentIDs() {
		return contentIDs;
	}
	
	public void setContentIDs(List<String> contentIDs) {
		this.contentIDs = contentIDs;
	}
	
	public List<String> getContentFiles() {
		return contentFiles;
	}
	
	public void setContentFiles(List<String> contentFiles) {
		this.contentFiles = contentFiles;
	}
	
	public List<String> getAttachmentFiles() {
		return attachmentFiles;
	}
	
	public void setAttachmentFiles(List<String> attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}
	
	
 
	
}

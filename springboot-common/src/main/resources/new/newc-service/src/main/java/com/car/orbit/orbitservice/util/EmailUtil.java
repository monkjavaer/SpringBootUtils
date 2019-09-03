package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: EmailUtil
 * @Package: com.car.orbit.orbitservice.util
 * @Description: 邮件发送工具类
 * @Author: monkjavaer
 * @Date: 2019/04/15 16:42
 * @Version: V1.0
 */
@Component
public class EmailUtil {

    public static final String MAIL_FROM_MAIL_SENDER = "mail.fromMail.sender";

    private static String sender;

    @Autowired
    private JavaMailSender javaMailSenderTemp;

    private static JavaMailSender javaMailSender;

    @PostConstruct
    public void beforeInit() {
        sender = PropertyReaderUtils.getProValue(MAIL_FROM_MAIL_SENDER);
        javaMailSender = javaMailSenderTemp;
    }

    /**
     * 发送文本邮件
     *
     * @param sendTo
     * @param subject
     * @param text
     * @throws MailException
     */
    public static void sendSimpleEmail(String sendTo, String subject, String text) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    /**
     * 发送附件邮件
     *
     * @param sendTo
     * @param subject
     * @param text
     * @param file
     * @throws MessagingException
     */
    public static void sendFilesMail(String sendTo, String subject, String text, File file) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(text, true);

        FileSystemResource fileSystemResource = new FileSystemResource(file);
        String fileName = file.getName();
        helper.addAttachment(fileName, fileSystemResource);

        javaMailSender.send(message);
    }

    /**
     * 发送图片邮件
     *
     * @param sendTo
     * @param subject
     * @param text
     * @param file
     * @throws MessagingException
     */
    public static void sendImageMail(String sendTo, String subject, String text, File file) throws MessagingException {
        String contentId = UUIDUtils.generate();

        MimeMessage message = javaMailSender.createMimeMessage();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        stringBuilder.append(text);
        stringBuilder.append("</br><img src=\'cid:");
        stringBuilder.append(contentId).append("\'></body></html>");
        String content = stringBuilder.toString();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource fileSystemResource = new FileSystemResource(file);
        helper.addInline(contentId, fileSystemResource);

        javaMailSender.send(message);
    }

    /**
     * 发送Html邮件
     *
     * @param sendTo
     * @param subject
     * @param htmlBody
     * @throws MessagingException
     */
    public static void sendHtmlMail(String sendTo, String subject, String htmlBody) throws Exception {
        String content = "<html>\n" +
                "<body>\n" +
                htmlBody +
                "</body>\n" +
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }

    /**
     * 群发邮件
     *
     * @param receivers
     * @param subject
     * @param htmlBody
     * @throws Exception
     */
    public static void sendHtmlMail(List<String> receivers, String subject, String htmlBody) throws Exception {
        List list = new ArrayList();
        for (int i = 0; i < receivers.size(); i++){
            list.add(new InternetAddress(receivers.get(i)));
        }
        InternetAddress[] address =(InternetAddress[])list.toArray(new InternetAddress[list.size()]);

        String content = "<html>\n" +
                "<body>\n" +
                htmlBody +
                "</body>\n" +
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(address);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
}

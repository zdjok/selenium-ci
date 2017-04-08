package com.yt.util.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @author zhengdejing
 * @description Email Util
 */
public class SmtpUtil {

	private final Properties props = System.getProperties();
	
	private MailAuthenticator mailAuthenticator;
	
	private Session session;

	public SmtpUtil(final String smtpHostName, final String username, final String password){
		init(smtpHostName, username, password);
	}
	
	public SmtpUtil(final String username, final String password){
		String smtpHostName = "smtp." + username.split("@")[1];
		init(smtpHostName, username, password);
	}
	
	public void init(String smtpHostName, String username, String password){
		props.put("mail.smtp.auth", true);
		props.put("mail.debug", true);
		props.put("mail.smtp.host", smtpHostName);
		mailAuthenticator = new MailAuthenticator(username, password);
		session = Session.getInstance(props, mailAuthenticator);
	}
	
	/**
	 * 发送邮件
	 * @param recipient
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmail(String recipients, String subject, Object content) throws AddressException, MessagingException{
		//创建MIME类型邮件
		MimeMessage message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
		//设置收件人
		String[] recipientArr = recipients.split(";");
		int len = recipientArr.length;
		InternetAddress[] addresses = new InternetAddress[len];
		for(int i=0; i<len; i++){
			addresses[i] = new InternetAddress(recipientArr[i]);
		}
		message.setRecipients(RecipientType.TO, addresses);
		//设置邮件主题
		message.setSubject(subject);
		//设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		//发送
		Transport.send(message);
	}
	
	/**
	 * 带附件发送邮件
	 * @param recipient
	 * @param subject
	 * @param content
	 * @param attachedFile
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendEmail(String recipients, String subject, Object content, File attachedFile) throws AddressException, MessagingException, IOException{
		//创建MIME类型邮件
		MimeMessage message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
		//设置收件人
		String[] recipientArr = recipients.split(";");
		int len = recipientArr.length;
		InternetAddress[] addresses = new InternetAddress[len];
		for(int i=0; i<len; i++){
			addresses[i] = new InternetAddress(recipientArr[i]);
		}
		message.setRecipients(RecipientType.TO, addresses);
		//设置邮件主题
		message.setSubject(subject);
		
		// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象  
		Multipart mp = new MimeMultipart();
		//创建一个包含html内容的MimeBodyPart对象
		MimeBodyPart html = new MimeBodyPart();
		//将HTML源内容加入MimeBodyPart对象
		html.setContent(content.toString(), "text/html;charset=utf-8");
		//将html对象添加到MimeMultipart对象
		mp.addBodyPart(html);
		
		//添加附件，同html内容
		MimeBodyPart attachment = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(attachedFile);
		attachment.setDataHandler(new DataHandler(fds));
		//添加附件名称
		try {  
            attachment.setFileName(MimeUtility.encodeWord(attachedFile.getName(), "UTF-8", null));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
		mp.addBodyPart(attachment);
		
		//将MiniMultipart对象设置为邮件内容  
		message.setContent(mp);

		/**
		 * 发送邮件
		 * 邮箱POP3/SMTP服务需要开启
		 */
		Transport.send(message);
	}
	
	/**
	 * 不带附件群发邮件
	 * @param recipient：收件人
	 * @param subject：邮件主题
	 * @param content：邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendEmail(List<String> recipient, String subject, Object content) throws AddressException, MessagingException{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
		int num = recipient.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for(int i=0; i<num; i++){
			addresses[i] = new InternetAddress(recipient.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		message.setSubject(subject);
		message.setContent(content.toString(), "text/html;charset=utf-8");
		Transport.send(message);
	}
	
	
	/**
	 * 带附件群发邮件
	 * @param recipient
	 * @param subject
	 * @param content
	 * @param attachedFile
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public void sendEmail(List<String> recipient, String subject, Object content, File attachedFile) throws AddressException, MessagingException, IOException{
		//创建MIME类型邮件
		MimeMessage message = new MimeMessage(session);
		//设置发件人
		message.setFrom(new InternetAddress(mailAuthenticator.getUsername()));
		//设置收件人
		int num = recipient.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for(int i=0; i<num; i++){
			addresses[i] = new InternetAddress(recipient.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		//设置邮件主题
		message.setSubject(subject);
		
		// MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象  
		Multipart mp = new MimeMultipart();
		//创建一个包含html内容的MimeBodyPart对象
		MimeBodyPart html = new MimeBodyPart();
		//将HTML源内容加入MimeBodyPart对象
		html.setContent(content.toString(), "text/html;charset=utf-8");
		//将html对象添加到MimeMultipart对象
		mp.addBodyPart(html);
		
		//添加附件，同html内容
		MimeBodyPart attachment = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(attachedFile);
		attachment.setDataHandler(new DataHandler(fds));
		//添加附件名称
		try {  
            attachment.setFileName(MimeUtility.encodeWord(attachedFile.getName(), "UTF-8", null));  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
		mp.addBodyPart(attachment);
		
		//将MiniMultipart对象设置为邮件内容  
		message.setContent(mp);

		//发送邮件
		Transport.send(message);
	}
}

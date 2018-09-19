package com.cssiot.cssutil.common.utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.cssiot.cssutil.common.data.IsEnabledModel;

import java.io.*;

/**
 * 
 * @description 发送邮件
 * @author athena
 * @time 2016-09-12
 *
 */

public class SendMail {

	public SendMail() {
	}

//	/**
//	 * 单人发送邮件
//	 * 
//	 * @param sendto
//	 *            收件人
//	 * @param title
//	 *            标题
//	 * @param content
//	 *            内容
//	 */
//	@SuppressWarnings("all")
//	public static void sendMail(String sendto, String title, String content) {
//		try {
//			Properties props = new Properties();
//			Session sendMailSession;
////			Store store;
//			Transport transport;
//			PopupAuthenticator popAuthenticator = new PopupAuthenticator();
//			popAuthenticator.performCheck(Global.getConfig("mailUserName", "wagon"),
//					Global.getConfig("mailPassword", "wagon"));
//			sendMailSession = Session.getInstance(props, popAuthenticator);
//			// 邮件服务器
//			props.put("mail.smtp.host", Global.getConfig("mailHost", "wagon"));
//			// 是否需要身份验证
//			props.put("mail.smtp.auth", Global.getConfig("mailAuth", "wagon"));
//
//			// 设置自定义发件人昵称
//			String nick = "";
//			try {
//				nick = javax.mail.internet.MimeUtility.encodeText(Global.getConfig("mailNick", "wagon"));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			Message newMessage = new MimeMessage(sendMailSession);
//			// 发件人
//			newMessage.setFrom(new InternetAddress(nick + " <" + Global.getConfig("mailFrom", "wagon") + ">"));
//			newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendto));
//			if (ChkUtil.isEmpty(title)) {
//				newMessage.setSubject(Global.getConfig("mailSubject", "wagon"));
//			} else {
//				newMessage.setSubject(Global.getConfig("mailSubject", "wagon") + ":" + title);
//			}
//			newMessage.setSentDate(new Date());
//			newMessage.setContent(content, "text/html;charset=UTF-8");
//			transport = sendMailSession.getTransport(Global.getConfig("mailTransport", "wagon"));
//			transport.send(newMessage);
//		} catch (MessagingException m) {
//			logger.error(m);
//		}
//	}

	/**
	 * 单人发送邮件
	 * 
	 * @param sendto 收件人
	 * @param sendcc 抄送人
	 * @param title 标题
	 * @param content 内容
	 * @param sysList 邮件设置（mailUserName，mailPassword，mailHost，mailAuth，mailNick，mailFrom，mailSubject，mailTransport）
	 */
	@SuppressWarnings("all")
	public static IsEnabledModel sendMail(String sendto, String title, String content,
			String[] sysList) {
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		try {
			Properties props = new Properties();
			Session sendMailSession;
//			Store store;
			Transport transport;
			PopupAuthenticator popAuthenticator = new PopupAuthenticator();
			popAuthenticator.performCheck(sysList[0], sysList[1]);
			sendMailSession = Session.getInstance(props, popAuthenticator);
			// 邮件服务器
			props.put("mail.smtp.host", sysList[2]);
			// 是否需要身份验证
			props.put("mail.smtp.auth", sysList[3]);

			// 设置自定义发件人昵称
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText(sysList[4]);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Message newMessage = new MimeMessage(sendMailSession);
			// 发件人
			newMessage.setFrom(new InternetAddress(nick + " <" + sysList[5] + ">"));
			newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendto));
			if (ChkUtil.isEmpty(title)) {
				newMessage.setSubject(sysList[6]);
			} else {
				newMessage.setSubject(sysList[6] + ":" + title);
			}
			newMessage.setSentDate(new Date());
			newMessage.setContent(content, "text/html;charset=UTF-8");
			transport = sendMailSession.getTransport(sysList[7]);
			transport.send(newMessage);
			isEnabledModel.setCode("1");
			isEnabledModel.setMessage("发送成功！");
		} catch (MessagingException m) {
			isEnabledModel.setCode("4008");
			isEnabledModel.setMessage("发送失败，请联系管理员确认邮件设置信息！");
		}
		return isEnabledModel;
	}

	/**
	 * 多人发送邮件
	 * 
	 * @param sendto 收件人（可多人）
	 * @param sendcc 抄送人（可多人）
	 * @param title 标题
	 * @param content 内容
	 * @param sysList 邮件设置（mailUserName，mailPassword，mailHost，mailAuth，mailNick，mailFrom，mailSubject，mailTransport）
	 */
	@SuppressWarnings("all")
	public static IsEnabledModel sendMails(String sendto, String sendcc, String title, String content,
			String[] sysList) {
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		try {
			Properties props = new Properties();
			Session sendMailSession;
//			Store store;
			Transport transport;
			PopupAuthenticator popAuthenticator = new PopupAuthenticator();
			popAuthenticator.performCheck(sysList[0], sysList[1]);
			sendMailSession = Session.getInstance(props, popAuthenticator);
			// 邮件服务器
			props.put("mail.smtp.host", sysList[2]);
			// 是否需要身份验证
			props.put("mail.smtp.auth", sysList[3]);

			// 设置自定义发件人昵称
			String nick = "";
			try {
				nick = javax.mail.internet.MimeUtility.encodeText(sysList[4]);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Message newMessage = new MimeMessage(sendMailSession);
			// 发件人
			newMessage.setFrom(new InternetAddress(nick + " <" + sysList[5] + ">"));
			newMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendto));
			newMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sendcc));
			if (ChkUtil.isEmpty(title)) {
				newMessage.setSubject(sysList[6]);
			} else {
				newMessage.setSubject(sysList[6] + ":" + title);
			}
			newMessage.setSentDate(new Date());
			newMessage.setContent(content, "text/html;charset=UTF-8");
			transport = sendMailSession.getTransport(sysList[7]);
			transport.send(newMessage);
			isEnabledModel.setCode("1");
			isEnabledModel.setMessage("发送成功！");
		} catch (MessagingException m) {
			isEnabledModel.setCode("4008");
			isEnabledModel.setMessage("发送失败，请联系管理员确认邮件设置信息！");
		}
		return isEnabledModel;
	}

//	@Test
//	public void test() {
//		String[] sysList = { Global.getConfig("mailUserName", "wagon"), Global.getConfig("mailPassword", "wagon"),
//				Global.getConfig("mailHost", "wagon"), Global.getConfig("mailAuth", "wagon"),
//				Global.getConfig("mailNick", "wagon"), Global.getConfig("mailFrom", "wagon"),
//				Global.getConfig("mailSubject", "wagon"),
//				Global.getConfig("mailTransport", "wagon") };
////		IsEnabledModel temp = this.sendMails("2981624@qq.com,wendy530@126.com", "2981624@qq.com,wendy530@126.com","测试请示", "请示再次测试一下", sysList);
//		IsEnabledModel dgtemp = this.sendMail("2981624@qq.com","测试请示", "请示再次测试一下", sysList);
//		System.out.println(dgtemp.getCode());
//	}
}

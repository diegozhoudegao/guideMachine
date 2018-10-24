package com.cssiot.cssbase.modules.wxgzh.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import com.cssiot.cssbase.modules.wxgzh.data.TextMessage;

/*
 * 消息处理工具类
 */
public class MessageUtil {
	public static final String MSGTYPE_EVENT = "event";//消息类型--事件消息
	public static final String MESSAGE_SUBSCIBE = "subscribe";//消息事件类型--订阅事件
	public static final String MESSAGE_UNSUBSCIBE = "unsubscribe";//消息事件类型--取消订阅事件
	public static final String MSGTYPE_TEXT = "text";//消息类型--文本
	public static final String MSGTYPE_IMAGE = "image";//消息类型--图片消息
	public static final String MSGTYPE_VOICE = "voice";//消息类型--语音消息
	public static final String MSGTYPE_VIDEO = "video";//消息类型--视频消息
	public static final String MSGTYPE_SHORTVIDEO = "shortvideo";//消息类型--小视频消息
	public static final String MSGTYPE_LOCATION = "location";//消息类型--地理位置消息
	public static final String MSGTYPE_LINK = "link";//消息类型--链接消息
	public static final String MESSAGE_SCAN = "SCAN";//消息事件类型--取消订阅事件
	public static final String MESSAGE_TEXT = "text";//消息类型--文本消息
	public static final String WELCOME_MSG = "欢迎关注，精彩内容不容错过！";
	public static final String REBINE_MSG = "未找到用户信息，请先取消关注并登录系统进行绑定！";
	/*
	 * 组装文本消息
	 */
	public static String textMsg(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return XmlUtil.textMsgToxml(text);
	}
	
	/*
	 * 响应订阅事件--回复文本消息
	 */
	public static String subscribeForText(String toUserName,String fromUserName,String eventKey){
		return textMsg(toUserName, fromUserName, "欢迎关注，精彩内容不容错过！！！");
	}
	
	/*
	 * 响应取消订阅事件
	 */
	public static String unsubscribe(String toUserName,String fromUserName){
		//TODO 可以进行取关后的其他后续业务处理
		System.out.println("用户："+ fromUserName +"取消关注~");
		return "";
	}
	
	public static void httpsRequestPicture(String requestUrl, String requestMethod, String data, String savePath, String fileName, String fileType) {
		 InputStream inputStream = null; 
		 try { 
			 URL url = new URL(requestUrl); 
			 HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(); 
			 conn.setDoOutput(true); 
			 conn.setDoInput(true); 
			 conn.setUseCaches(false); 
			 //设置请求方式（GET/POST） 
			 conn.setRequestMethod(requestMethod); 
			 conn.connect(); 
			 //当data不为null时向输出流写数据 
			 if (null != data) { 
				 
				//getOutputStream方法隐藏了connect()方法 
				 OutputStream outputStream = conn.getOutputStream(); 
				 //注意编码格式 
				 outputStream.write(data.getBytes("UTF-8")); 
				 outputStream.close(); 
			 } 
			 // 从输入流读取返回内容 
			 inputStream = conn.getInputStream();
//			 logger.info("开始生成微信二维码...");
			 inputStreamToMedia(inputStream, savePath, fileName, fileType);
//			 logger.info("微信二维码生成成功!!!"); 
			 conn.disconnect(); 
		} catch (Exception e) { 
//			logger.error("发送https请求失败，失败", e); 
		}finally { 
			//释放资源 
			try { 
				if(null != inputStream) { 
					inputStream.close(); 
				} 
			} catch (IOException e) { 
//				logger.error("释放资源失败，失败", e); 
//				} 
			} 
		 }
	 }
	 
	 /**
	     * 将输入流转换为图片
	     * @param input 输入流
	     * @param savePath 图片需要保存的路径
	     * @param fileType 图片类型
	     */ 
	 public static void inputStreamToMedia(InputStream input, String savePath, String fileName, String fileType) throws Exception { 
		 String filePath = savePath + "/" + fileName + "." + fileType; File file = new File(filePath); 
		 FileOutputStream outputStream = new FileOutputStream(file); 
		 int length; 
		 byte[] data = new byte[1024]; 
		 while ((length = input.read(data)) != -1) { 
			 outputStream.write(data, 0, length); 
		 } 
		 outputStream.flush(); 
		 outputStream.close();
	 }
}

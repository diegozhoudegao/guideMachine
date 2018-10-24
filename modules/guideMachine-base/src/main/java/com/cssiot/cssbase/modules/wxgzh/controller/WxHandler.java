package com.cssiot.cssbase.modules.wxgzh.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssbase.modules.websocket.WebSocketServer;
import com.cssiot.cssbase.modules.wxgzh.data.TemplateMsgBean;
import com.cssiot.cssbase.modules.wxgzh.service.WechatSendTemplateService;
import com.cssiot.cssbase.modules.wxgzh.service.WechatTokenService;
import com.cssiot.cssbase.modules.wxgzh.utils.MessageUtil;
import com.cssiot.cssbase.modules.wxgzh.utils.XmlUtil;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.OkHttpUtil;
import com.cssiot.cssutil.common.utils.ResponseUtils;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/wxHandler")
public class WxHandler {
	
//	private static String accessToken_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
//	private static String showTicket_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
//	private static final String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	private static final String APPID = "wxef2f68e44bfba607";
	private static final String APPSECRET = "4133a38f80e79735eb3b9bc788403b6f";
	private static final String QR_SCENE = "QR_SCENE";//临时二维码
	private static final String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";//永久二维码
	private static final int EXPIRE_SECONDS = 604800;//该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
	private static final String MESSAGETEMPLATEID = "UCRU2lPikaAZnOR9z2sWtEaC5V-d9xea9BmFkRqHCRc";
	@Autowired
	private UserService userService;
	
	@Autowired
	private WechatTokenService wechatTokenService;
	
	@Autowired
	private WechatSendTemplateService wechatSendTemplateService;
	
	/**
	 * 微信公众号配置的响应地址
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author 
	 * 	2018-10-20 Diego.zhou 新建
	 */
	@RequestMapping("/verify")
	public void verify(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		log.info("测试回调方法");
//		log.info("signature内容为：{}",request.getParameter("signature"));
//		log.info("timestamp内容为：{}",request.getParameter("timestamp"));
//		log.info("nonce内容为：{}",request.getParameter("nonce"));
//		log.info("echostr内容为：{}",request.getParameter("echostr"));
//		Enumeration enu=request.getParameterNames();  
//		while(enu.hasMoreElements()){  
//			String paraName=(String)enu.nextElement();  
//			log.info("所有参数内容为：{}",paraName+": "+request.getParameter(paraName));
////			System.out.println(paraName+": "+request.getParameter(paraName));  
//		}  
//		response.getWriter().write(request.getParameter("echostr"));
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String message = "success";
		try {
			//把微信返回的xml信息转义成map
			Map<String, String> map = XmlUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");//消息来源用户标识，openId
			String toUserName = map.get("ToUserName");//消息目的用户标识
			String msgType = map.get("MsgType");//消息类型
			String content = map.get("Content");//消息内容
			String eventType = map.get("Event");//事件类型
			String eventKey = map.get("EventKey");//事件KEY值，qrscene_为前缀，后面为二维码的参数值
			String ticket = map.get("Ticket");//二维码的ticket，可用来换取二维码图片
			log.info("事件类型为{}",eventType);
			log.info("消息来源用户标识:fromUserName:{},消息目的用户标识:toUserName:{}"
					+ "消息类型msgType:{},消息内容content:{},事件类型eventType:{}"
					+ "事件KEY值eventKey:{},二维码的ticket:{}",fromUserName,toUserName,msgType,content,eventType,eventKey,ticket);
			
			if(MessageUtil.MSGTYPE_EVENT.equals(msgType)){//如果为事件类型
				if(MessageUtil.MESSAGE_SUBSCIBE.equals(eventType)){//处理订阅事件
					message = subscribeForText(toUserName, fromUserName,eventKey);
				}else if(MessageUtil.MESSAGE_UNSUBSCIBE.equals(eventType)){//处理取消订阅事件
					message = unsubscribe(toUserName, fromUserName);
				}else if(MessageUtil.MESSAGE_SCAN.equals(eventType)){//处理扫码事件
					message = scan(toUserName, fromUserName);
				}else {
					message = "";
				}
			}else {
				message = MessageUtil.textMsg(toUserName, fromUserName, MessageUtil.WELCOME_MSG);
			}
			log.info("message内容为：{}",message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			out.println(message);
			if(out!=null){
				out.close();
			}
		}
	}
	
	/*
	 * 响应订阅事件--回复文本消息
	 */
	private String subscribeForText(String toUserName,String fromUserName,String eventKey){
		String message = MessageUtil.textMsg(toUserName, fromUserName, MessageUtil.WELCOME_MSG);
		//获取场景值，这里的场景值为对应的用户登录成功后的userId
		try {
			String userId = eventKey.split("_")[1];
			if(!ChkUtil.isEmptyAllObject(userId)) {
				//获取用户
				User user = userService.get(userId);
				if(null != user) {
					user.setOpenId(fromUserName);
					user.setLastUpdateTime(new Date());
					userService.save(user);
					//长链接服务器主动推送关注成功消息
					WebSocketServer.sendMessageTo("绑定成功", user.getId());
				}
			}
		} catch (Exception e) {
			log.info("获取用户信息失败！");
		}
		return message;
	}
	
	
	/*
	 * 响应取消订阅事件
	 */
	private String unsubscribe(String toUserName,String fromUserName){
		//通过openId获取对应的用户
		try {
			User user = userService.getByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and openId='"+fromUserName+"'");
			if(null != user) {
				user.setOpenId(null);
				user.setLastUpdateTime(new Date());
				userService.save(user);
				//长链接服务器主动推送关注成功消息
				WebSocketServer.sendMessageTo("解绑成功", user.getId());
			}
		} catch (Exception e) {
			log.info("获取用户信息失败！");
		}
		log.info("用户："+ fromUserName +"取消关注~");
		return "";
	}
	
	/*
	 * 响应用户扫码事件
	 */
	private String scan(String toUserName,String fromUserName){
		//通过openId获取对应的用户
		try {
			User user = userService.getByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and openId='"+fromUserName+"'");
			if(null == user) {
				String message = MessageUtil.textMsg(toUserName, fromUserName, MessageUtil.REBINE_MSG);
				return message;
			}
		} catch (Exception e) {
			log.info("获取用户信息失败！");
		}
		return "";
	}
	
	/**
	 * 创建永久二维码
	 * @param sceneStr 场景值
	 * @return
	 * 	ticket: 二维码ticket
	 * @author 
	 * 	2018-10-20 Diego.zhou 新建
	 */
	@SuppressWarnings("all")
	public String createQrcode(String sceneStr) {
		//获取公众号accessToken
		String accessToken = wechatTokenService.getAccessToken();
		log.info("成功获取accessToken,{}",accessToken);
//		临时二维码
//		http请求方式: POST
//		URL: https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
//		POST数据格式：json
//		POST数据例子：{"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
//		{"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm
//			3sUw==","expire_seconds":60,"url":"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI"}
		
//		Map sceneIdMap = new HashMap<>();
//		sceneIdMap.put("scene_id", sceneId);
//		Map sceneMap = new HashMap<>();
//		sceneMap.put("scene", sceneIdMap);
//		Map requestMap = new HashMap<>();
//		requestMap.put("action_info", sceneMap);
//		requestMap.put("expire_seconds", EXPIRE_SECONDS);
//		requestMap.put("action_name", QR_SCENE);
//		---------------------------------------------------
//		永久二维码请求说明
//		http请求方式: POST
//		URL: https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
//		POST数据格式：json
//		POST数据例子：{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_str": 123}}}
//		或者也可以使用以下POST数据创建字符串形式的二维码参数：
//		{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}

		Map sceneStrMap = new HashMap<>();
		sceneStrMap.put("scene_str", sceneStr);
		Map sceneMap = new HashMap<>();
		sceneMap.put("scene", sceneStrMap);
		Map requestMap = new HashMap<>();
		requestMap.put("action_info", sceneMap);
//		requestMap.put("expire_seconds", EXPIRE_SECONDS);
		requestMap.put("action_name", QR_LIMIT_STR_SCENE);
		
		String jsonParams = ResponseUtils.buildResultJson(requestMap);
		String ticketJson = OkHttpUtil.postJsonParams(TICKET_URL.replace("TOKEN", accessToken), jsonParams, null, null, null);
		JSONObject json = JSON.parseObject(Encodes.unescapeHtml(ticketJson));
		String ticket = json.getString("ticket");
		String ticketUrl = json.getString("url");
		log.info("成功获取ticket,{}",ticket);
//		MessageUtil.httpsRequestPicture(showTicket_url + URLEncoder.encode(ticket), "GET", null, "/upload", sceneId+"", "png");
		return ticket;
	}
	
	/**
	 * 用户微信绑定接口
	 * @param response
	 * @param userId 用户Id
	 * @throws IOException
	 * @author 
	 * 	2018-10-20 Diego.zhou 新建
	 */
	@GetMapping("/doGetQrCode")
	public void doGetQrCode(HttpServletResponse response,String userId) throws IOException {
		String ticket = createQrcode(userId);
		response.getWriter().write(ticket);
	}
	
	/**
	 * 测试发送微信模板消息
	 * @param openId
	 */
	@GetMapping("/doSendTemplateMessage")
	@SuppressWarnings("all")
	public void doSendTemplateMessage() {
		TemplateMsgBean templateMsgBean = new TemplateMsgBean();
		templateMsgBean.setOpenId("o9v_s0SDEBapt03thpaqbShkAnGw");
		templateMsgBean.setTemplateId(MESSAGETEMPLATEID);
		Map dataMap = new HashMap<>();
		Map first = new HashMap<>();
		first.put("value", "Diego.zhou");
		first.put("color", "#173177");
		dataMap.put("first", first);
		Map keyword1 = new HashMap<>();
		keyword1.put("value", "ZZY20181020078");
		keyword1.put("color", "#173177");
		dataMap.put("keyword1", keyword1);
		Map remark = new HashMap<>();
		remark.put("value", "感谢你的处理！");
		remark.put("color", "#173177");
		dataMap.put("remark", remark);
		templateMsgBean.setData(dataMap);
		wechatSendTemplateService.doSendTemplateInfo(templateMsgBean);
	}
	

}

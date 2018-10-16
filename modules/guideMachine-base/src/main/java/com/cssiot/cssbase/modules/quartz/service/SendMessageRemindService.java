package com.cssiot.cssbase.modules.quartz.service;

import com.cssiot.cssbase.modules.quartz.data.MessageRemindModel;

public interface SendMessageRemindService {


	/**
	 * 发送提醒,表单和一般的都可以
	 * @param jsonModel 消息设定模型
	 * @author
	 * 2018年9月12日  tck 创建
	 */
	public Object doSendMeassageRemind(MessageRemindModel jsonModel);
	
}

package com.cssiot.cssbase.modules.wxgzh.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cssiot.cssbase.modules.wxgzh.config.WxgzhConfiguration;
import com.cssiot.cssbase.modules.wxgzh.data.TemplateMsgBean;
import com.cssiot.cssbase.modules.wxgzh.service.WechatSendTemplateService;
import com.cssiot.cssbase.modules.wxgzh.service.WechatTokenService;
import com.cssiot.cssutil.common.utils.OkHttpUtil;
import com.cssiot.cssutil.common.utils.ResponseUtils;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送微信的模版消息
 * @ClassName: SendWechatTemplateService
 * @Description: 
 * @author 
 * 	2018-10-20 Diego.zhou 新建
 */
@Slf4j
@Service
public class WechatSendTemplateServiceImpl implements WechatSendTemplateService {
	
	private static final String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
	
	@Autowired
	private WechatTokenService wechatTokenService;
	
	@Autowired
	private WxgzhConfiguration wxgzhConfiguration;
	
	/**
	 * 发送模版消息
	 * @param TemplateMsgBean msgData
	 * @return
	 * @author
	 * 	2018-10-20 Diego.zhou 新建
	 */
	@Override
	public void doSendTemplateInfo(TemplateMsgBean msgData) {
		//获取公众号accessToken
		String accessToken = wechatTokenService.getAccessToken();
		String url = String.format(SEND_TEMPLATE_MSG, accessToken);
        try {
            Map<String, String> miniProgram = Maps.newHashMap();
            miniProgram.put("appid", wxgzhConfiguration.getAppId());
//          miniProgram.put("pagepath", "所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）");
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("touser", msgData.getOpenId());
            paramMap.put("template_id", msgData.getTemplateId());
            paramMap.put("topcolor", "#FF0000");
            //url有值就跳转,没有Ios空白页,Android不跳转
            paramMap.put("url", msgData.getActionUrl());
            //是否跳转小程序,这个参数暂时保留,设置的话就直接跳转小程序了
            //paramMap.put("miniprogram", miniProgram);
            paramMap.put("data", msgData.getData());
            String param = ResponseUtils.buildResultJson(paramMap);
            log.info("发送模板消息,请求url:{},参数:{}", url, param);
            String result = OkHttpUtil.postJsonParams(url, param,null,null,null);
            log.info("发送模板消息,返回结果:{}", result);
        } catch (Exception ex) {
        	log.error("发送微信模板消息异常:", ex);
        }
		return;
	}
}

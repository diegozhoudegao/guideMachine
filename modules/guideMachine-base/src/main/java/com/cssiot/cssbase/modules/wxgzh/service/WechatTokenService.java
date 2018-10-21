package com.cssiot.cssbase.modules.wxgzh.service;


/**
 * 获取公共号的token
 * @ClassName: WechatTokenService
 * @Description: 
 * @author 
 * 2018-10-20 Diego.zhou 新建
 */
public interface WechatTokenService {
	
	/**
	 * 获取公共号的token(调用公共号的各接口需带上该token)
	 * @return token
	 * @author
	 * 	2018-10-20 Diego.zhou 新建
	 */
	public String getAccessToken();
	
}

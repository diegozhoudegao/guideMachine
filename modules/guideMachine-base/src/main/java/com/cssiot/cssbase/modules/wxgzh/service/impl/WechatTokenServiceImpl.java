package com.cssiot.cssbase.modules.wxgzh.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.wxgzh.config.WxgzhConfiguration;
import com.cssiot.cssbase.modules.wxgzh.service.WechatTokenService;
import com.cssiot.cssutil.common.constant.RedisConstant;
import com.cssiot.cssutil.common.enums.CommonConfigEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.OkHttpUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取公众号accessToken
 * @ClassName: WechatTokenServiceImpl
 * @Description: 
 * @author 
 * 	2018-10-20 Dieog.zhou 新建
 */
@Slf4j
@Service
public class WechatTokenServiceImpl implements WechatTokenService {
	
	private static String ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private WxgzhConfiguration wxgzhConfiguration;
	
	/**
	 * 获取公共号的token(调用公共号的各接口需带上该token)
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @return
	 * @author
	 * 2018年10月20日 Diego.zhou 创建
	 */
	public String getAccessToken(){
		// 加锁
		String accessToken = redisTemplate.opsForValue().get("wxPublicToken");
		try {
			// 为空或者小于规定的存活时间就重新获取token
			if (ChkUtil.isEmpty(accessToken)) {
				log.info("缓存中accessToken为空，获取token并放入缓存");
				//获取accessToken
				String url = ACCESSTOKEN_URL
						   .replace("APPID", wxgzhConfiguration.getAppId())
						   .replace("APPSECRET", wxgzhConfiguration.getAppSecret());
				String conn = OkHttpUtil.get(url, null, null, null, null);
				// 解析json格式数据
				JSONObject json = JSON.parseObject(Encodes.unescapeHtml(conn));
				// 校验获取是否正确
				if (!ChkUtil.isEmpty(json.get("errcode"))) {
					throw new ResultException(-2, "获取微信公共号token失败:"+json.get("errmsg"), null, null, null);
				}
				accessToken = json.getString("access_token");
				log.info("请求微信服务器，获取token并放入缓存，token:{},有效期为:{}",accessToken,json.getLong("expires_in"));
				redisTemplate.opsForValue().set("wxPublicToken", accessToken, json.getLong("expires_in"), TimeUnit.SECONDS);
			}else {
				log.info("成功从缓存中获取accessToken:{}",accessToken);
			}
		} catch (Exception e) {
			log.error("获取公众号accessToken异常，{}",e);
		}
		return accessToken;
	}
	
}

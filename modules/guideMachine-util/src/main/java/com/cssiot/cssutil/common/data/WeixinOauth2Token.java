package com.cssiot.cssutil.common.data;

import lombok.Data;

/**
 * 网页授权信息
 * 
 * @author 2017-12-26 tck 新建
  */
@Data
public class WeixinOauth2Token {
	// 网页授权接口调用凭证
	private String accessToken;
	// 凭证有效时长
	private int expiresIn;
	// 用于刷新凭证
	private String refreshToken;
	// 用户标识
	private String openId;
	// 用户授权作用域
	private String scope;
	// 所有应用下的用户统一标识
	private String unionId;
}

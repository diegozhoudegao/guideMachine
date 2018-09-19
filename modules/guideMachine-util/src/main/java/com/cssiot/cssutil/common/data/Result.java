package com.cssiot.cssutil.common.data;

import lombok.Data;

/**
 * 请求返回的最外层对象
 * @author 
 * 	2017-09-20 athena 创建
 */
@Data
public class Result<T> {
	
	//系统对应码
	private String code;

	//信息对应码
	private Integer msgCode;
	
	//提示信息
	private String message;
	
	//安全令牌
	private String token;
	
	//用户id
	private String loginName;
	
	//客户端类型(android/ios/web等)
	private String clientType;
	
	//具体的内容
	private T data;

}
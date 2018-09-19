package com.cssiot.cssutil.common.exception;

import com.cssiot.cssutil.common.enums.ResultEnum;

/**
 * 异常信息
 * @author
 * 	2017-09-21 athena 创建
 */
public class ResultException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	//信息对应码
	private Integer msgCode;
	
	//安全令牌
	private String token;
	
	//用户id
	private String loginName;
	
	//客户端类型(app/ios/web等)
	private String clientType;
	
	private ResultEnum resultEnum;
	
	//具体的内容
	private Object data;

	public ResultException(ResultEnum resultEnum,String token,String loginName,String clientType) {
		super(resultEnum.getMessage());
		this.resultEnum = resultEnum;
		this.msgCode = resultEnum.getMsgCode();
		this.token=token;
		this.loginName=loginName;
		this.clientType=clientType;
	}
	
	public ResultException(Object data,ResultEnum resultEnum,String token,String loginName,String clientType) {
		super(resultEnum.getMessage());
		this.data = data.toString();
		this.resultEnum = resultEnum;
		this.msgCode = resultEnum.getMsgCode();
		this.token=token;
		this.loginName=loginName;
		this.clientType=clientType;
	}
	
	public ResultException(Integer msgCode,String message,String token,String loginName,String clientType) {
	    super(message);
        this.msgCode = msgCode;
        this.token=token;
        this.loginName=loginName;
        this.clientType=clientType;
    }

	public ResultException(Object data,Integer msgCode,String message,String token,String loginName,String clientType) {
	    super(message);
	    this.data = data.toString();
        this.msgCode = msgCode;
        this.token=token;
        this.loginName=loginName;
        this.clientType=clientType;
    }
	
	public Integer getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public ResultEnum getResultEnum() {
		return resultEnum;
	}

	public void setResultEnum(ResultEnum resultEnum) {
		this.resultEnum = resultEnum;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
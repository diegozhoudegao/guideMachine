package com.cssiot.cssutil.common.utils;

import com.cssiot.cssutil.common.data.Result;
import com.cssiot.cssutil.common.enums.ResultEnum;

/**
 * 结果说明
 * @author
 *	2017-09-20 athena 创建
 */
public class ResultUtil {

	/**
	 * 成功结果
	 * @param object 传参
	 * @param resultEnum 错误代码
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	public static Result success(Object object,ResultEnum resultEnum,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("1");
		result.setMsgCode(resultEnum.getMsgCode());
		result.setMessage(resultEnum.getMessage());
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		result.setData(object);
		return result;
	}
	
	/**
	 * 错误结果-不正常，需清空token
	 * @param resultEnum 错误代码
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	public static Result error0(ResultEnum resultEnum,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("0");
		result.setMsgCode(resultEnum.getMsgCode());
		result.setMessage(resultEnum.getMessage());
		result.setLoginName(loginName);
		result.setClientType(clientType);
		return result;
	}
	
	/**
	 * 错误结果-业务不能继续操作，并且有提示信息
	 * @param resultEnum 错误代码
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	public static Result error4007(ResultEnum resultEnum,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4007");
		result.setMsgCode(resultEnum.getMsgCode());
		result.setMessage(resultEnum.getMessage());
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		return result;
	}
	
	/**
	 * 错误结果-可以继续进行操作，并且有提示信息，传入错误代码
	 * @param resultEnum 错误代码
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	public static Result error4008(ResultEnum resultEnum,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4008");
		result.setMsgCode(resultEnum.getMsgCode());
		result.setMessage(resultEnum.getMessage());
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		return result;
	}
	/**
	 * 错误结果-可以继续进行操作，并且有提示信息，传入错误代码,多传一个结果
	 * @param resultEnum 错误代码
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-11-06 shinry 创建
	 */
	@SuppressWarnings("all")
	public static Result error4008(Object object,ResultEnum resultEnum,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4008");
		result.setMsgCode(resultEnum.getMsgCode());
		result.setMessage(resultEnum.getMessage());
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		result.setData(object.toString());
		return result;
	}
	
	/**
	 * 错误结果-可以继续进行操作，并且有提示信息，传入提示信息
	 * @param msgCode 信息对应码
	 * @param msg 提示信息
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	public static Result error4008(Integer msgCode,String msg,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4008");
		result.setMsgCode(msgCode);
		result.setMessage(msg);
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		return result;
	}
	
	/**
	 * 错误结果-可以继续进行操作，并且有提示信息，传入提示信息
	 * @param msgCode 信息对应码
	 * @param msg 提示信息
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @param data 结果
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-11-08 Diego.zhou 创建
	 */
	@SuppressWarnings("all")
	public static Result error4008(Object data,Integer msgCode,String msg,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4008");
		result.setMsgCode(msgCode);
		result.setMessage(msg);
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		result.setData(data);
		return result;
	}
	
	/**
	 * 错误结果-可以继续进行操作，并且有提示信息，传入提示信息，第三方登录错误信息
	 * @param msgCode 信息对应码
	 * @param msg 提示信息
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @param data 结果
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-12-25 tck 创建
	 */
	@SuppressWarnings("all")
	public static Result error4008(Object errcode,Object errmsg,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4008");
		result.setMsgCode(Integer.parseInt(errcode.toString()));
		result.setMessage(errmsg.toString());
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		return result;
	}
	
	/**
	 * 错误结果-系统异常，不需要清空token
	 * @param resultEnum 错误代码
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	public static Result error4009(ResultEnum resultEnum,String token,String loginName,String clientType) {
		Result result=new Result();
		result.setCode("4009");
		result.setMsgCode(resultEnum.getMsgCode());
		result.setMessage(resultEnum.getMessage());
		result.setToken(token);
		result.setLoginName(loginName);
		result.setClientType(clientType);
		return result;
	}
}

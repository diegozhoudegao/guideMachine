package com.cssiot.cssutil.common.constant;

/**
 * redis配置内容
 * @author
 * 	2017-10-06 athena 创建
 *  2017-11-07 athena 增加SyncId前缀
 *  2017-11-09 shinry 增加心跳定时任务上传
 *  2017-12-27 shinry 修改token_prefix
 *  2018-09-03 Diego.zhou 增加app token
 */
public interface RedisConstant {

	//web前缀
	String WEB_TOKEN_PREFIX="%s_cssmfwwebtoken";
	
	//web前缀
	String APP_TOKEN_PREFIX="%s_cssmfwapptoken";
	
	//点击的click前缀
	String CLICKSTA_PREFIX="click_%s";
	
	//加密前缀
	String ENSCRYFIX="%s_enscry";
	
	//点击统计的有效时间(秒)
	Integer CLICKSTA_EXPIRE=259200;
	
	//web有效期(秒)
	Integer WEB_EXPIRE=1800;
	//忘记密码
	String FORGETPASS_WORDEMAIL="%s_forgetemail";
	
	//忘记密码短息
	String FORGETPASSWORD_PHONE="%s_forgetPhone";
	//分享链接前缀
	String FORMSHARELINK="%s_shareLink";
	//忘记密码失效
	Integer FORGET_EXPIRE=1800;
	
	Integer DINGDINGTIMEOUT=7000;
	//验证码前缀
	String VERTIFYCODE="%s_vertifyCode";
	
	String FORGET_PASSWORD_PREFIX="%s_forget_passsWord_prefix";
	
	//微信授权tokne的后缀
	String OAUTH_TOKEN_PREFIX="%s_wxOAuthToken";
	
	//微信授权refresh_tokne的后缀
	String OAUTH_REFRESH_TOKEN_PREFIX="%s_wxOAuthRefreshToken";
	
	//QQ授权tokne的后缀
	String QQ_OAUTH_TOKEN_PREFIX="%s_qqOAuthToken";
	
	//QQ授权refresh_tokne的后缀
	String QQ_OAUTH_REFRESH_TOKEN_PREFIX="%s_qqOAuthRefreshToken";
	
	//配置类的后缀
	String COMMONCONFIG_PREFIX="%s_commonConfig";
	//钉钉发送消息获取token
	String DINGDING_ACCESSTOKEN="%s_dingdingToken";
	
	//钉钉三方登陆获取token
	String DINGDIGN_LOGINTOKEM="%s_dingdingloginToken";
}
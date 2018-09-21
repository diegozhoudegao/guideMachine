package com.cssiot.cssutil.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendBatchSmsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 阿里大于短信发送
 * 	备注：国际短信发送请勿使用该工具类
 * @author 
 * 	2018-09-21 Diego.zhou 新建
 *
 */
public class DysmsUtil {
	
	 //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    // 可将此处配置移至配置文件中，安全起见可在此处硬编码
    static final String accessKeyId = "yourAccessKeyId";
    static final String accessKeySecret = "yourAccessKeySecret";
	
    /**
     * 单条短信发送
     * @param phoneNumbers 必填:待发送手机号
     * @param signName 必填:短信签名-可在短信控制台中找到
     * @param templateCode 必填:短信模板-可在短信控制台中找到
     * @param templateParam 可选:模板中的变量替换JSON串
     * @return
     * @throws ClientException
     * @author 
     * 	2018-09-21 Diego.zhou 新建
     */
	public static SendSmsResponse sendSingleSm(String phoneNumbers,String signName,String templateCode,String templateParam) throws ClientException {
	        //可自助调整超时时间
	        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	        //初始化acsClient,暂不支持region化
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
	        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	        IAcsClient acsClient = new DefaultAcsClient(profile);
	        //组装请求对象-具体描述见控制台-文档部分内容
	        SendSmsRequest request = new SendSmsRequest();
	        //必填:待发送手机号 
	        request.setPhoneNumbers(phoneNumbers);
	        //必填:短信签名-可在短信控制台中找到
	        request.setSignName(signName);
	        //必填:短信模板-可在短信控制台中找到
	        request.setTemplateCode(templateCode);
	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为{\"name\":\"Tom\", \"code\":\"123\"}
	        request.setTemplateParam(templateParam);
	        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
	        //request.setSmsUpExtendCode("90997");
	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//	        request.setOutId(outId);
	        //hint 此处可能会抛出异常，注意catch
	        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
	        return sendSmsResponse;
	    }
	
	/**
	 * 短信批量发送
	 * @param phoneNumberJson 必填:待发送手机号。支持JSON格式的批量调用，
	 * 					批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
	 * @param signNameJson 必填:短信签名-支持不同的号码发送不同的短信签名
	 * @param templateCode 必填:短信模板-可在短信控制台中找到
	 * @param templateParamJson 必填:模板中的变量替换JSON串
	 * @return
	 * @throws ClientException
	 * @author 
	 * 	2018-09-21 Diego.zhou 新建
	 */
	public static SendBatchSmsResponse sendMultiSm(String phoneNumberJson,String signNameJson,String templateCode,String templateParamJson) throws ClientException {
	    //设置超时时间-可自行调整
	    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	    //初始化ascClient需要的几个参数
	    final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
	    final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
	    //替换成你的AK
//	    final String accessKeyId = accessKeyId;//你的accessKeyId,参考本文档步骤2
//	    final String accessKeySecret = "yourAccessKeySecret";//你的accessKeySecret，参考本文档步骤2
	    //初始化ascClient,暂时不支持多region（请勿修改）
	    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
	    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	    IAcsClient acsClient = new DefaultAcsClient(profile);
	     //组装请求对象
	     SendBatchSmsRequest request = new SendBatchSmsRequest();
	     //使用post提交
	     request.setMethod(MethodType.POST);
	     //必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟
	     //	验证码类型的短信推荐使用单条调用的方式,例如[\"1500000000\",\"1500000001\"]
	     request.setPhoneNumberJson(phoneNumberJson);
	     //必填:短信签名-支持不同的号码发送不同的短信签名，例如[\"云通信\",\"云通信\"]
	     request.setSignNameJson(signNameJson);
	     //必填:短信模板-可在短信控制台中找到,如SMS_1000000
	     request.setTemplateCode(templateCode);
	     //必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为"[{\"name\":\"Tom\", \"code\":\"123\"},{\"name\":\"Jack\", \"code\":\"456\"}]"
	     //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
	     request.setTemplateParamJson(templateParamJson);
	     //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
	     //request.setSmsUpExtendCodeJson("[\"90997\",\"90998\"]");
	    //请求失败这里会抛ClientException异常
	    SendBatchSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
	    if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
	    	//请求成功
	    }
	    return sendSmsResponse;
    }
}

package com.cssiot.cssbase.modules.sys.service;

import java.util.List;

import com.cssiot.cssbase.modules.sys.entity.Visitors;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 游客表Service
 * @author
 *	2018-10-07 athena 创建
 */
public interface VisitorsService extends BaseService<Visitors>{
	
	/**
     * 游客登录,并新建用户信息
     * @param jsonStr 登录信息
     * 	{
     * 		code 临时登录凭证
     * 		signature 签名
     *  	rawData 原始数据
     *  	encryptedData 加密数据
     *  	iv 加密算法的初始向量
     *  }
     * @return
     * @author 
     * 	2018-09-22 Diego.zhou 新建
     * 	2018-10-14 Diego.zhou 修改
     */
    @SuppressWarnings("all")
    public Object doWxUserLogin(String jsonStr);
    
    /**
     * 获取用户手机号信息
     * @param jsonStr
     * 	{
     * 		openid openid
     * 		signature 签名
     *  	rawData 原始数据
     *  	encryptedData 加密数据
     *  	iv 加密算法的初始向量
     *  }
     * @return
     * @author
     * 	2018-09-22 Diego.zhou 新建
     * 	2018-10-14 Diego.zhou 修改
     */
    @SuppressWarnings("all")
    public Object doGetWxUserPhoneInfo(String jsonStr);
    
    /**
     * 发送模板消息
     * @param openid openid
     * @param formId 
     * 		1、在小程序内使用了微信支付接口，此时formId可以取prepay_id
     *  		2、在小程序里用户点击了表单，而且该表单的report-submit属性值为true时，formId取e.detail.formId
     * @param templateId 消息模板Id
     * @param dataList 按照消息模板参数顺序生成的数据集合
     * @return
     * @author 
     * 	2018-10-14 Diego.zhou 新建
     */
    public void doSendTemplateMsg(String openid,String formId,String templateId,List dataList);
}
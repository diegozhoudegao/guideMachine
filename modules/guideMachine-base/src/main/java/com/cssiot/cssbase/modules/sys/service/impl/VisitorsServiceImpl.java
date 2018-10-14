package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.sys.dao.VisitorsDao;
import com.cssiot.cssbase.modules.sys.entity.Visitors;
import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssbase.modules.wechat.config.WxMaConfiguration;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.enums.VisitorsTypeEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.MD5Util;
import com.cssiot.cssutil.common.utils.OptionUtil;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaMsgServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage.Data;
import io.vavr.control.Try;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 游客表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class VisitorsServiceImpl extends BaseServiceImpl<Visitors> implements VisitorsService{
	
	@Override
	public BaseDao<Visitors> getBaseDao() {
		return visitorsDao;
	}
	
	@Autowired
	private VisitorsDao visitorsDao;
	
	@Value("${wx.pay.appid}")
	private String appid;
	
	/**
     * 游客登录,并新建用户信息
     * @param jsonStr 登录信息
     * 	{
     * 		code 临时登录凭证code
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
    public Object doWxUserLogin(String jsonStr) {
    	Map map = new HashMap<>();
    	// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, null, null, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, null, null, null));
		//临时登录凭证code
		OptionUtil.of(json.get("code")).getOrElseThrow(() -> new ResultException(-2,"code为空！", null, null, null));
		String code = json.getString("code");
		//用户敏感信息加密数据encryptedData
		OptionUtil.of(json.get("encryptedData")).getOrElseThrow(() -> new ResultException(-2,"encryptedData为空！", null, null, null));
		String encryptedData = json.getString("encryptedData");
		//加密算法的初始向量iv
		OptionUtil.of(json.get("iv")).getOrElseThrow(() -> new ResultException(-2,"iv为空！", null, null, null));
		String iv = json.getString("iv");
		//签名signature
		String signature = "";
		if(!ChkUtil.isEmptyAllObject(json.get("signature"))) {
			signature = json.getString("signature");
		}
		//用户非敏感信息 rawData
		String rawData = "";
		if(!ChkUtil.isEmptyAllObject("rawData")) {
			rawData = json.getString("rawData");
		}
    	final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
        	throw new ResultException(-2, String.format("未找到对应appid=[%d]的配置，请核实！", appid), null, null, null);
        }
        //微信授权获取sessionKey和openid
		try {
			WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
			String sessionKey = session.getSessionKey();//sessionKey
			String openid = session.getOpenid();	//openId
			String skey = MD5Util.getMD5ofStr(sessionKey, 1);//自定义登录态
			// 用户信息校验
			if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
				throw new ResultException(-2, "用户校验失败！", null, null, null);
			}
			// 解密用户信息
			WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
			if(null == userInfo ) {
				throw new ResultException(-2, "获取用户信息失败！", null, null, null);
			}
			//判断openId对应的游客信息是否存在，不存在新增存在修改
	        Visitors oldVisitor = visitorsDao.getByHql("from Visitors where status='"+StateEnum.NEWSTATE.getCode()+"' and openId='"+openid+"'");
	        if(null != oldVisitor) {
	        	oldVisitor.setLastUpdateTime(new Date());
	        	oldVisitor.setSKey(skey);
	        	oldVisitor.setSessionKey(sessionKey);
	        	visitorsDao.save(oldVisitor);
	        }else {
	        	//保存访客数据
	        	Visitors visitor = new Visitors();
	        	visitor.setOpenId(openid);
	        	visitor.setCreateTime(new Date());
	        	visitor.setLastUpdateTime(new Date());
	        	visitor.setStatus(StateEnum.NEWSTATE.getCode());
	        	visitor.setVisitorsType(VisitorsTypeEnum.NORMAL.getCode());//普通游客
	        	visitor.setRegisterTime(new Date());
	        	visitor.setWechatNo(userInfo.getNickName());
	        	visitor.setSKey(skey);
	        	visitor.setSessionKey(sessionKey);
	        	visitorsDao.save(visitor);
	        }
			map.put("userInfo", userInfo);
			map.put("loginFlag", MD5Util.getMD5ofStr(sessionKey, 1));//用户登录态标识
		} catch (WxErrorException e) {
			throw new ResultException(-2, e.getMessage(), null, null, null);
		}
        return map;
    }
	
    
    /**
     * 获取微信用户手机号信息
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
    public Object doGetWxUserPhoneInfo(String jsonStr) {
    	Map map = new HashMap<>();
    	// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, null, null, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, null, null, null));
		//openid
		OptionUtil.of(json.get("openid")).getOrElseThrow(() -> new ResultException(-2,"openid为空！", null, null, null));
		String openid = json.getString("openid");
		//用户敏感信息加密数据encryptedData
		OptionUtil.of(json.get("encryptedData")).getOrElseThrow(() -> new ResultException(-2,"encryptedData为空！", null, null, null));
		String encryptedData = json.getString("encryptedData");
		//加密算法的初始向量iv
		OptionUtil.of(json.get("iv")).getOrElseThrow(() -> new ResultException(-2,"iv为空！", null, null, null));
		String iv = json.getString("iv");
		//签名signature
		String signature = "";
		if(!ChkUtil.isEmptyAllObject(json.get("signature"))) {
			signature = json.getString("signature");
		}
		//用户非敏感信息 rawData
		String rawData = "";
		if(!ChkUtil.isEmptyAllObject("rawData")) {
			rawData = json.getString("rawData");
		}
		final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
        	throw new ResultException(-2, String.format("未找到对应appid=[%d]的配置，请核实！", appid), null, null, null);
        }
        Visitors visitor = visitorsDao.getByHql("from Visitors where status='"+StateEnum.NEWSTATE.getCode()+"' and openId='"+openid+"'");
        if(null == visitor) {
			throw new ResultException(-2, "用户信息异常！", null, null, null);
        }
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(visitor.getSessionKey(), rawData, signature)) {
        	throw new ResultException(-2, "用户校验失败！", null, null, null);
        }
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(visitor.getSessionKey(), encryptedData, iv);
    	if(null == phoneNoInfo || ChkUtil.isEmptyAllObject(phoneNoInfo.getPhoneNumber())) {
			throw new ResultException(-2, "获取用户手机号失败！", null, null, null);
    	}
        //更新用户信息
    	visitor.setPhone(phoneNoInfo.getPhoneNumber());
    	visitor.setLastUpdateTime(new Date());
    	visitorsDao.save(visitor);
        map.put("phoneNoInfo", phoneNoInfo);
        return map;
    }
    
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
    public void doSendTemplateMsg(String openid,String formId,String templateId,List dataList) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
        	throw new ResultException(-2, String.format("未找到对应appid=[%d]的配置，请核实！", appid), null, null, null);
        }
        try {
        	WxMaTemplateMessage templateMessage = new WxMaTemplateMessage();
        	templateMessage.setToUser(openid);//openId
        	templateMessage.setTemplateId(templateId);
        	templateMessage.setFormId(formId);
//        	List dataList = Arrays.asList(
//        			new Data("keyword1","2018-09-23 21:00",""),
//        			new Data("keyword2","灌无忧",""),
//        			new Data("keyword3","微信",""),
//        			new Data("keyword4","100.00",""),
//        			new Data("keyword5","e232wa4fhrhfa3e","")
//        			);
        	templateMessage.setData(dataList);
        	WxMaMsgServiceImpl msgServiceImpl = new WxMaMsgServiceImpl(wxService);
        	msgServiceImpl.sendTemplateMsg(templateMessage);
        }catch(Exception e) {
			throw new ResultException(-2, e.getMessage(), null, null, null);
        }
        return;
    }
}
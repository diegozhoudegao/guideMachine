package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.base.entity.ScenicSpot;
import com.cssiot.cssbase.modules.base.service.ScenicSpotService;
import com.cssiot.cssbase.modules.biz.data.RentOrderModel;
import com.cssiot.cssbase.modules.biz.entity.RentOrder;
import com.cssiot.cssbase.modules.biz.service.RentOrderService;
import com.cssiot.cssbase.modules.sys.dao.VisitorsDao;
import com.cssiot.cssbase.modules.sys.data.VisitorsModel;
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
import com.cssiot.cssutil.common.utils.EasyPoiFileUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.MD5Util;
import com.cssiot.cssutil.common.utils.OptionUtil;
import com.cssiot.cssutil.common.utils.Page;
import com.cssiot.cssutil.common.utils.Parameter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaMsgServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
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
	
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	@Autowired
	private RentOrderService rentOrderService;
	
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
    
    /**
     * 游客信息查询接口
     * @param jsonStr 查询条件
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    @SuppressWarnings("all")
    public Object doSelectVisitorsInfo(HttpServletRequest request, HttpServletResponse response,
    		String jsonStr,String pageNo,String pageSize,String userId,String token) {
    	Map map = new HashMap<>();
    	//JSON解析
    	String orderBy="";
    	Parameter parameter =new Parameter();
    	String property=" from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' ";
    	if(!ChkUtil.isEmptyAllObject(jsonStr)) {
    		// JSON解析
    		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
    				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, null, null, null));
    		//获取注册省份
    		if(!ChkUtil.isEmpty(json.get("province"))){
				String province = json.getString("province");
				property=property+" and province = :province ";
				parameter.put("province", province);
			}
    		//获取注册城市
    		if(!ChkUtil.isEmpty(json.get("city"))){
				String city = json.getString("city");
				property=property+" and city = :city ";
				parameter.put("city", city);
			}
    		//获取注册区县
    		if(!ChkUtil.isEmpty(json.get("county"))){
				String county = json.getString("county");
				property=property+" and county = :county ";
				parameter.put("county", county);
			}
    		//获取注册景点Id
    		if(!ChkUtil.isEmpty(json.get("scenicSpotId"))){
				String scenicSpotId = json.getString("scenicSpotId");
				property=property+" and scenicSpotId =:scenicSpotId ";
				parameter.put("scenicSpotId", scenicSpotId);
			}
    		//注册日期区间
    		if(!ChkUtil.isEmpty(json.get("registerTimeStart"))){
				String registerTimeStart = json.getString("registerTimeStart");
				property=property+" and registerTime >=:registerTimeStart ";
				parameter.put("registerTimeStart", registerTimeStart);
			}
    		if(!ChkUtil.isEmpty(json.get("registerTimeEnd"))){
				String registerTimeEnd = json.getString("registerTimeEnd");
				property=property+" and registerTime <=:registerTimeEnd ";
				parameter.put("registerTimeEnd", registerTimeEnd);
			}
    		//手机号
    		if(!ChkUtil.isEmpty(json.get("phone"))){
				String phone = json.getString("phone");
				property=property+" and phone =:phone ";
				parameter.put("phone", phone);
			}
    		//身份证号
    		if(!ChkUtil.isEmpty(json.get("identityCard"))){
				String identityCard = json.getString("identityCard");
				property=property+" and identityCard =:identityCard ";
				parameter.put("identityCard", identityCard);
			}
    		//等级等级(全部、普通、VIP)
    		if(!ChkUtil.isEmpty(json.get("visitorsType"))){
				String visitorsType = json.getString("visitorsType");
				property=property+" and visitorsType =:visitorsType ";
				parameter.put("visitorsType", visitorsType);
			}
    		//归还状态(全部、已归还、未归还)
    		if(!ChkUtil.isEmpty(json.get("returnStatus"))){
				String returnStatus = json.getString("returnStatus");
				property=property+" and returnStatus =:returnStatus ";
				parameter.put("returnStatus", returnStatus);
			}
    		//黑名单
			if(!ChkUtil.isEmpty(json.get("isBlacklist"))){
				String isBlacklist = json.getString("isBlacklist");
				property=property+" and isBlacklist = :isBlacklist ";
				parameter.put("isBlacklist", isBlacklist);
			}
			if(!ChkUtil.isEmpty(json.get("orderBy"))){
				orderBy=json.get("orderBy").toString();
			}
    	}
    	//下面两行设置翻页
		Page pages = new Page<Visitors>(request,response);
		if(ChkUtil.isInteger(pageNo) && !pageNo.equals("0")){
			pages.setPageNo(Integer.parseInt(pageNo));
		}
		if(ChkUtil.isInteger(pageSize) && !pageSize.equals("0")){
			pages.setPageSize(Integer.parseInt(pageSize));
		}
		if(!ChkUtil.isEmpty(orderBy)){
			pages.setOrderBy(orderBy);
		}
		Page<Visitors> page = visitorsDao.find(pages, property,parameter);
		List<Visitors> list=page.getList();
		List visitorsList=new ArrayList<>();
		if(!ChkUtil.isEmptyAllObject(list)){
			for(Visitors visitors : list) {
				VisitorsModel visitorModel = new VisitorsModel(visitors);
				//注册景点
				if(!ChkUtil.isEmptyAllObject(visitors.getScenicSpotId())) {
					try {
						ScenicSpot ss = scenicSpotService.get(visitors.getScenicSpotId());
						if(null != ss) {
							visitorModel.setScenicSpotName(ss.getScenicSpotName());
						}
					} catch (Exception e) {
						throw new ResultException(ResultEnum.DATA_ERROR, null, null, null);
					}
				}
				visitorsList.add(visitorModel);
			}
			page.setList(visitorsList);
		}
		map.put("page", page);
    	return map;
    }
    
    /**
     * VIP游客新建保存接口
     * @param jsonStr 保存JSON,jsonStr:{province:'注册省份',city:'注册城市',county:'注册区县',
     * 				phone:'手机号',identityCard:'身份证号',vipRemark:'说明'}
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    @Override
    public Object doSaveVipVisitorsInfo(String jsonStr,String userId,String token) {
    	// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null));
		//获取VIP游客信息
		Gson gson = new Gson();
		//获取首页数据
		if(ChkUtil.isEmptyAllObject(json.get("visitors"))){
			throw new ResultException(-2, "VIP游客数据为空！", token, userId, null);
		}
		VisitorsModel visitorsModel = gson.fromJson(json.getString("visitors"),new TypeToken<VisitorsModel>(){}.getType());
		if(null == visitorsModel) {
			throw new ResultException(-2, "VIP游客数据为空！", token, userId, null);
		}
		//手机号或者身份证号二选一，说明为必填项
		if(ChkUtil.isEmptyAllObject(visitorsModel.getPhone()) && ChkUtil.isEmptyAllObject(visitorsModel.getIdentityCard())) {
			throw new ResultException(-2, "手机号或者身份证号至少填写一个！", token, userId, null);
		}
		//手机号格式校验
		Map phoneMap = ChkUtil.fieldCheck(visitorsModel.getPhone(),false,11, "手机号","4");
		if(ChkUtil.isEmptyAllObject(phoneMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!phoneMap.get("code").equals("1")){
			throw new ResultException(-2, phoneMap.get("message").toString(), token, userId, null);
		}
		//身份证号格式校验
		Map identityCardMap = ChkUtil.fieldCheck(visitorsModel.getIdentityCard(),false,18, "身份证号","9");
		if(ChkUtil.isEmptyAllObject(identityCardMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!identityCardMap.get("code").equals("1")){
			throw new ResultException(-2, identityCardMap.get("message").toString(), token, userId, null);
		}
		//VIP说明必填校验
		Map vipRemarkMap = ChkUtil.fieldCheck(visitorsModel.getVipRemark(),true,20, "说明","");
		if(ChkUtil.isEmptyAllObject(vipRemarkMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!vipRemarkMap.get("code").equals("1")){
			throw new ResultException(-2, vipRemarkMap.get("message").toString(), token, userId, null);
		}
		//存在校验
		if(!ChkUtil.isEmptyAllObject(visitorsModel.getPhone())) {
			String property = "from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+visitorsModel.getPhone()+"'";
			Visitors exist = visitorsDao.getByHql(property);
			if(null != exist) {
				throw new ResultException(-2, "已存在手机号为："+visitorsModel.getPhone()+"的游客信息！", token, userId, null);
			}
		}else if(!ChkUtil.isEmptyAllObject(visitorsModel.getIdentityCard())) {
			String property = "from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and identityCard='"+visitorsModel.getIdentityCard()+"'";
			Visitors exist = visitorsDao.getByHql(property);
			if(null != exist) {
				throw new ResultException(-2, "已存在身份证号为："+visitorsModel.getIdentityCard()+"的游客信息！", token, userId, null);
			}
		}
		//保存VIP游客信息
		Visitors visitors = new Visitors();
		visitors.setRegisterTime(new Date());
		visitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
		visitors.setProvince(visitorsModel.getProvince());
		visitors.setCity(visitorsModel.getCity());
		visitors.setCounty(visitorsModel.getCounty());
		visitors.setScenicSpotId(visitorsModel.getScenicSpotId());
		visitors.setPhone(visitorsModel.getPhone());
		visitors.setIdentityCard(visitorsModel.getIdentityCard());
		visitors.setVipRemark(visitorsModel.getVipRemark());
		visitors.setCreateTime(new Date());
		visitors.setCreateUser(userId);
		visitors.setLastUpdateTime(new Date());
		visitors.setLastUpdateUser(userId);
		visitors.setStatus(StateEnum.NEWSTATE.getCode());
		visitorsDao.save(visitors);
    	return null;
    }

    /**
     * 游客信息修改初始化接口
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return VisitorsModel
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    @SuppressWarnings("all")
    public Object doUpdateVisitorsData(String visitorsId,String userId,String token) {
    	Map map = new HashMap<>();
    	//游客Id校验
    	OptionUtil.of(visitorsId).getOrElseThrow(() -> 
    		new ResultException(-2,"游客Id为空!", token, userId, null));
    	//获取对应游客信息
		try {
			Visitors visitors = visitorsDao.get(visitorsId);
			if(null == visitors || ChkUtil.isEmptyAllObject(visitors.getStatus()) 
	    			|| visitors.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该游客数据异常！", token, userId, null);
	    	}
			VisitorsModel visitorModel = new VisitorsModel(visitors);
			//注册景点
			if(!ChkUtil.isEmptyAllObject(visitors.getScenicSpotId())) {
				ScenicSpot ss = scenicSpotService.get(visitors.getScenicSpotId());
				if(null != ss) {
					visitorModel.setScenicSpotName(ss.getScenicSpotName());
					visitorModel.setProvince(ss.getProvince());
					visitorModel.setCity(ss.getCity());
					visitorModel.setCounty(ss.getCounty());
				}
			}
			map.put("visitors", visitorModel);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
    	return map;
    }

    /**
     * 游客信息修改保存接口
     * @param jsonStr
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doUpdateVisitorsInfo(String jsonStr,String userId,String token) {
    	// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null));
		//获取VIP游客信息
		Gson gson = new Gson();
		//获取首页数据
		if(ChkUtil.isEmptyAllObject(json.get("visitors"))){
			throw new ResultException(-2, "游客数据为空！", token, userId, null);
		}
		VisitorsModel visitorsModel = gson.fromJson(json.getString("visitors"),new TypeToken<VisitorsModel>(){}.getType());
		if(null == visitorsModel) {
			throw new ResultException(-2, "游客数据为空！", token, userId, null);
		}
		//获取对应游客
		if(ChkUtil.isEmptyAllObject(visitorsModel.getVisitorsId())) {
			throw new ResultException(-2, "游客Id为空！", token, userId, null);
		}
		try {
			Visitors visitors = visitorsDao.get(visitorsModel.getVisitorsId());
			if(null == visitors || ChkUtil.isEmptyAllObject(visitors.getStatus()) 
	    			|| visitors.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该游客数据异常！", token, userId, null);
	    	}
			//判断游客类别
			String openId = visitors.getOpenId();
			//小程序游客可以修改游客支付宝账号、姓名、身份证号、性别、籍贯、民族、出生年月、地址
			if(!ChkUtil.isEmptyAllObject(openId)) {
				visitors.setAlipayNo(visitorsModel.getAlipayNo());
				visitors.setVisitorsName(visitorsModel.getVisitorsName());
				visitors.setIdentityCard(visitorsModel.getIdentityCard());
				visitors.setSex(visitorsModel.getSex());
				visitors.setNation(visitorsModel.getNation());
				visitors.setBirthDate(visitorsModel.getBirthDate());
				visitors.setHomeAddress(visitorsModel.getHomeAddress());
			}else {
				//手机号、微信号、支付宝号
				visitors.setPhone(visitorsModel.getPhone());
				visitors.setWechatNo(visitorsModel.getWechatNo());
				visitors.setAlipayNo(visitorsModel.getAlipayNo());
			}
			visitors.setLastUpdateTime(new Date());
			visitors.setLastUpdateUser(userId);
			visitorsDao.save(visitors);
		} catch(ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
    	return null;
    }
    
    /**
     * VIP游客模板导出接口
     * @return null
     * @author 
     * 	2018-10-23 Diego.zhou 新建
     */
	@Override
	public void doExportVipVisitorsTemplateInfo(HttpServletResponse response) {
		// 导出操作
		EasyPoiFileUtil.exportExcel(new ArrayList<>(), "VIP游客", "sheet1", VisitorsModel.class, "VIP游客信息导入模板.xls", response);
	}

	/**
     * VIP游客信息导入接口
     * @return null
     * @author 
     * 	2018-10-23 Diego.zhou 新建
     */
	@Override
	@SuppressWarnings("all")
	public Object doImportVipVisitorsInfo(MultipartFile file,String token,String userId) {
		// 解析excel
		List<VisitorsModel> visitorsList = EasyPoiFileUtil.importExcel(file, 1, 1, VisitorsModel.class);
		if (!ChkUtil.isEmptyAllObject(visitorsList)) {
			String property = null;
			for (VisitorsModel visitorsModel : visitorsList) {
				//手机号或者身份证号二选一，说明为必填项
				if(ChkUtil.isEmptyAllObject(visitorsModel.getPhone()) && ChkUtil.isEmptyAllObject(visitorsModel.getIdentityCard())) {
					throw new ResultException(-2, "手机号或者身份证号至少填写一个！", token, userId, null);
				}
				//手机号格式校验
				Map phoneMap = ChkUtil.fieldCheck(visitorsModel.getPhone(),false,11, "手机号","4");
				if(ChkUtil.isEmptyAllObject(phoneMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!phoneMap.get("code").equals("1")){
					throw new ResultException(-2, phoneMap.get("message").toString(), token, userId, null);
				}
				//身份证号格式校验
				Map identityCardMap = ChkUtil.fieldCheck(visitorsModel.getIdentityCard(),false,18, "身份证号","9");
				if(ChkUtil.isEmptyAllObject(identityCardMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!identityCardMap.get("code").equals("1")){
					throw new ResultException(-2, identityCardMap.get("message").toString(), token, userId, null);
				}
				//VIP说明必填校验
				Map vipRemarkMap = ChkUtil.fieldCheck(visitorsModel.getVipRemark(),true,20, "说明","");
				if(ChkUtil.isEmptyAllObject(vipRemarkMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!vipRemarkMap.get("code").equals("1")){
					throw new ResultException(-2, vipRemarkMap.get("message").toString(), token, userId, null);
				}
				//存在校验
				if(!ChkUtil.isEmptyAllObject(visitorsModel.getPhone())) {
					property = "from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+visitorsModel.getPhone()+"'";
					Visitors exist = visitorsDao.getByHql(property);
					if(null != exist) {
						throw new ResultException(-2, "已存在手机号为："+visitorsModel.getPhone()+"的游客信息！", token, userId, null);
					}
				}else if(!ChkUtil.isEmptyAllObject(visitorsModel.getIdentityCard())) {
					property = "from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and identityCard='"+visitorsModel.getIdentityCard()+"'";
					Visitors exist = visitorsDao.getByHql(property);
					if(null != exist) {
						throw new ResultException(-2, "已存在身份证号为："+visitorsModel.getIdentityCard()+"的游客信息！", token, userId, null);
					}
				}
				//保存VIP游客信息
				Visitors visitors = new Visitors();
				visitors.setRegisterTime(new Date());
				visitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
				visitors.setPhone(visitorsModel.getPhone());
				visitors.setIdentityCard(visitorsModel.getIdentityCard());
				visitors.setVipRemark(visitorsModel.getVipRemark());
				visitors.setCreateTime(new Date());
				visitors.setCreateUser(userId);
				visitors.setLastUpdateTime(new Date());
				visitors.setLastUpdateUser(userId);
				visitors.setStatus(StateEnum.NEWSTATE.getCode());
				visitorsDao.save(visitors);
			}
		}else {
			throw new ResultException(-2, "游客信息为空", token, userId, null);
		}
		return null;
	}

	/**
     * 手动加入黑名单接口
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
	@Override
	public Object doJoinBlacklistInfo(String visitorsId, String userId, String token) {
		//游客Id校验
    	OptionUtil.of(visitorsId).getOrElseThrow(() -> 
    		new ResultException(-2,"游客Id为空!", token, userId, null));
    	//获取对应游客信息
    	try {
    		Visitors visitors = visitorsDao.get(visitorsId);
    		if(null == visitors || ChkUtil.isEmptyAllObject(visitors.getStatus()) 
        			|| visitors.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
    			throw new ResultException(-2, "该游客数据异常！", token, userId, null);
        	}
    		//VIP用户无法加入黑名单
    		if(!ChkUtil.isEmptyAllObject(visitors.getVisitorsType()) && visitors.getVisitorsType().equals(VisitorsTypeEnum.VIP.getCode())) {
    			throw new ResultException(-2, "VIP游客无法加入黑名单！", token, userId, null);
    		}
    		//已是黑名单用户，避免重复加入
    		if(!ChkUtil.isEmptyAllObject(visitors.getIsBlacklist()) && visitors.getIsBlacklist().equals("0")) {
    			throw new ResultException(-2, "该游客已加入黑名单，请勿重复添加！", token, userId, null);
    		}
    		visitors.setIsBlacklist("0");
    		visitors.setLastUpdateTime(new Date());
    		visitors.setLastUpdateUser(userId);
    		visitorsDao.save(visitors);
    	} catch(ResultException e) {
    		throw e;
    	} catch(Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
    	}
		return null;
	}

	/**
     * 手动取消黑名单接口
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
	@Override
	public Object doCancelBlacklistInfo(String visitorsId, String userId, String token) {
		//游客Id校验
    	OptionUtil.of(visitorsId).getOrElseThrow(() -> 
    		new ResultException(-2,"游客Id为空!", token, userId, null));
    	//获取对应游客信息
    	try {
    		Visitors visitors = visitorsDao.get(visitorsId);
    		if(null == visitors || ChkUtil.isEmptyAllObject(visitors.getStatus()) 
        			|| visitors.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
    			throw new ResultException(-2, "该游客数据异常！", token, userId, null);
        	}
    		//已是黑名单用户，避免重复加入
    		if(!ChkUtil.isEmptyAllObject(visitors.getIsBlacklist()) && visitors.getIsBlacklist().equals("1")) {
    			throw new ResultException(-2, "该游客已不在黑名单中，请勿重复取消！", token, userId, null);
    		}
    		visitors.setIsBlacklist("1");
    		visitors.setLastUpdateTime(new Date());
    		visitors.setLastUpdateUser(userId);
    		visitorsDao.save(visitors);
    	} catch(ResultException e) {
    		throw e;
    	} catch(Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
    	}
		return null;
	}

	/**
     * 普通游客转Vip
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
	@Override
	public Object doUpdateNormalVisitorsToVipInfo(String visitorsId, String userId, String token) {
		//游客Id校验
    	OptionUtil.of(visitorsId).getOrElseThrow(() -> 
    		new ResultException(-2,"游客Id为空!", token, userId, null));
    	//获取对应游客信息
    	try {
    		Visitors visitors = visitorsDao.get(visitorsId);
    		if(null == visitors || ChkUtil.isEmptyAllObject(visitors.getStatus()) 
        			|| visitors.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
    			throw new ResultException(-2, "该游客数据异常！", token, userId, null);
        	}
    		//已是黑名单用户，不能升级
    		if(!ChkUtil.isEmptyAllObject(visitors.getIsBlacklist()) && visitors.getIsBlacklist().equals("1")) {
    			throw new ResultException(-2, "该游客已加入黑名单，不能升级Vip！", token, userId, null);
    		}
    		//判断是否已是Vip用户，重复升级
    		if(!ChkUtil.isEmptyAllObject(visitors.getVisitorsType()) && visitors.getVisitorsType().equals(VisitorsTypeEnum.VIP.getCode())) {
    			throw new ResultException(-2, "该游客已是Vip用户，请勿重复操作！", token, userId, null);
    		}
    		visitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
    		visitors.setLastUpdateTime(new Date());
    		visitors.setLastUpdateUser(userId);
    		visitorsDao.save(visitors);
    	} catch(ResultException e) {
    		throw e;
    	} catch(Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
    	}
		return null;
	}

	/**
     * Vip游客转普通游客
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
	@Override
	public Object doUpdateVipVisitorsToNormalInfo(String visitorsId, String userId, String token) {
		//游客Id校验
    	OptionUtil.of(visitorsId).getOrElseThrow(() -> 
    		new ResultException(-2,"游客Id为空!", token, userId, null));
    	//获取对应游客信息
    	try {
    		Visitors visitors = visitorsDao.get(visitorsId);
    		if(null == visitors || ChkUtil.isEmptyAllObject(visitors.getStatus()) 
        			|| visitors.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
    			throw new ResultException(-2, "该游客数据异常！", token, userId, null);
        	}
    		//判断是否已是Vip用户，重复升级
    		if(!ChkUtil.isEmptyAllObject(visitors.getVisitorsType()) && visitors.getVisitorsType().equals(VisitorsTypeEnum.NORMAL.getCode())) {
    			throw new ResultException(-2, "该游客已是普通用户，请勿重复操作！", token, userId, null);
    		}
    		visitors.setVisitorsType(VisitorsTypeEnum.NORMAL.getCode());
    		visitors.setLastUpdateTime(new Date());
    		visitors.setLastUpdateUser(userId);
    		visitorsDao.save(visitors);
    	} catch(ResultException e) {
    		throw e;
    	} catch(Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
    	}
		return null;
	}

	 /**
     * 游客租借历史查询接口
     * @param request
     * @param response
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * 	2018-10-24 Diego.zhou 新建
     */
	@Override
	public Object doSelectVisitorsRentHistoryInfo(HttpServletRequest request, HttpServletResponse response,
			String visitorsId, String userId, String token) {
		Map map = new HashMap<>();
		//游客Id校验
    	OptionUtil.of(visitorsId).getOrElseThrow(() -> 
    		new ResultException(-2,"游客Id为空!", token, userId, null));
    	//租借订单查询接口
    	try {
			List<RentOrder> rentOrderList = rentOrderService.findByHql("from RentOrder where status<>'"+StateEnum.DELETESTATE.getCode()+"' and visitorsId='"+visitorsId+"'");
			if(!ChkUtil.isEmptyAllObject(rentOrderList)) {
				List<RentOrderModel> rentList = new ArrayList<>();
				RentOrderModel rentModel = null;
				for(RentOrder rentOrder : rentOrderList) {
					rentModel = new RentOrderModel(rentOrder);
					//获取租借景点信息
					if(!ChkUtil.isEmptyAllObject(rentOrder.getScenicSpotId())) {
						ScenicSpot ss = scenicSpotService.get(rentOrder.getScenicSpotId());
						if(null != ss) {
							rentModel.setScenicSpotName(ss.getScenicSpotName());
							rentModel.setCity(ss.getCity());
							rentModel.setProvince(ss.getProvince());
							rentModel.setCounty(ss.getCounty());
						}
					}
					rentList.add(rentModel);
				}
				map.put("rentHistoryList", rentList);
			}
    	} catch(ResultException e) {
    		throw e;
    	} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return map;
	}
	
}
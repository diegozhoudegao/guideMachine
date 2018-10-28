package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.sys.dao.CompanyDao;
import com.cssiot.cssbase.modules.sys.data.CompanyModel;
import com.cssiot.cssbase.modules.sys.data.UserModel;
import com.cssiot.cssbase.modules.sys.entity.Company;
import com.cssiot.cssbase.modules.sys.service.CompanyService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.OptionUtil;
import com.cssiot.cssutil.common.utils.Page;
import com.cssiot.cssutil.common.utils.Parameter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import io.vavr.control.Try;

/**
 * 公司表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 *	2018-10-28 Diego.zhou 新建
 */
@Service
@Transactional
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService{
	
	@Override
	public BaseDao<Company> getBaseDao() {
		return companyDao;
	}
	
	@Autowired
	private CompanyDao companyDao;

	/**
	 * 商户查询接口
	 */
	@Override
	public Object doSelectCompanyInfo(HttpServletRequest request, HttpServletResponse response, String jsonStr,
			String pageNo, String pageSize, String userId, String token) {
		Map map = new HashMap<>();
    	//JSON解析
    	String orderBy="";
    	Parameter parameter =new Parameter();
    	String property=" from Company where status<>'"+StateEnum.DELETESTATE.getCode()+"' ";
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
    		//手机号
    		if(!ChkUtil.isEmpty(json.get("phone"))){
				String phone = json.getString("phone");
				property=property+" and phone =:phone ";
				parameter.put("phone", phone);
			}
    		//姓名
    		if(!ChkUtil.isEmpty(json.get("companyName"))){
				String companyName = json.getString("companyName");
				property=property+" and companyName like: companyName ";
				parameter.put("companyName", "%"+companyName+"%");
			}
			if(!ChkUtil.isEmpty(json.get("orderBy"))){
				orderBy=json.get("orderBy").toString();
			}
    	}
    	//下面两行设置翻页
		Page pages = new Page<Company>(request,response);
		if(ChkUtil.isInteger(pageNo) && !pageNo.equals("0")){
			pages.setPageNo(Integer.parseInt(pageNo));
		}
		if(ChkUtil.isInteger(pageSize) && !pageSize.equals("0")){
			pages.setPageSize(Integer.parseInt(pageSize));
		}
		if(!ChkUtil.isEmpty(orderBy)){
			pages.setOrderBy(orderBy);
		}
		Page<Company> page = companyDao.find(pages, property,parameter);
		map.put("page", page);
    	return map;
	}

	/**
	 * 商户新建保存接口
	 */
	@Override
	public Object doSaveCompanyInfo(String jsonStr, String userId, String token) {
		// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null));
		//获取VIP游客信息
		Gson gson = new Gson();
		//获取员工数据
		if(ChkUtil.isEmptyAllObject(json.get("companyModel"))){
			throw new ResultException(-2, "商户数据为空！", token, userId, null);
		}
		CompanyModel companyModel = gson.fromJson(json.getString("companyModel"),new TypeToken<UserModel>(){}.getType());
		if(null == companyModel) {
			throw new ResultException(-2, "商户数据为空！", token, userId, null);
		}
		//名称校验
		Map companyNameMap = ChkUtil.fieldCheck(companyModel.getCompanyName(),true,20, "商户名称","");
		if(ChkUtil.isEmptyAllObject(companyNameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!companyNameMap.get("code").equals("1")){
			throw new ResultException(-2, companyNameMap.get("message").toString(), token, userId, null);
		}
		//名称存在校验
		Company companyName = companyDao.getByHql("from Company where status<>'1' and companyName='"+companyModel.getCompanyName()+"'");
		if(null == companyName) {
			throw new ResultException(-2, "已存在名称为："+companyModel.getCompanyName()+"的商户！", token, userId, null);
		}
		//商户号校验
		if(ChkUtil.isEmptyAllObject(companyModel.getWechatNo())) {
			throw new ResultException(-2, "商户号必填！", token, userId, null);
		}
		Company companyNo = companyDao.getByHql("from Company where status<>'1' and wechatNo='"+companyModel.getWechatNo()+"'");
		if(null == companyNo) {
			throw new ResultException(-2, "已存在商户号为："+companyModel.getWechatNo()+"的商户！", token, userId, null);
		}
		//商户号存在校验
		Company company = new Company(companyModel);
		company.setLastUpdateTime(new Date());
		company.setLastUpdateUser(userId);
		company.setCreateTime(new Date());
		company.setCreateUser(userId);
		company.setStatus(StateEnum.NEWSTATE.getCode());
		companyDao.save(company);
		return null;
	}

	/**
	 * 商户数据修改初始化接口
	 */
	@Override
	public Object doUpdateCompanyData(String companyId, String userId, String token) {
		Map map = new HashMap<>();
		//员工Id校验
    	OptionUtil.of(companyId).getOrElseThrow(() -> 
    		new ResultException(-2,"商户Id为空!", token, userId, null));
    	//获取对应游客信息
		try {
			Company company = companyDao.get(companyId);
			if(null == company || ChkUtil.isEmptyAllObject(company.getStatus()) 
	    			|| company.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "商户数据异常！", token, userId, null);
	    	}
			CompanyModel companyModel = new CompanyModel(company);
			map.put("companyModel", companyModel);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return map;
	}

	/**
	 * 员工信息修改保存接口
	 */
	@Override
	public Object doUpdateCompanyInfo(String jsonStr, String userId, String token) {
		// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null));
		//获取VIP游客信息
		Gson gson = new Gson();
		//获取员工数据
		if(ChkUtil.isEmptyAllObject(json.get("companyModel"))){
			throw new ResultException(-2, "商户数据为空！", token, userId, null);
		}
		CompanyModel companyModel = gson.fromJson(json.getString("companyModel"),new TypeToken<UserModel>(){}.getType());
		if(null == companyModel) {
			throw new ResultException(-2, "商户数据为空！", token, userId, null);
		}
		//获取商户号
		//名称校验
		Map companyNameMap = ChkUtil.fieldCheck(companyModel.getCompanyName(),true,20, "商户名称","");
		if(ChkUtil.isEmptyAllObject(companyNameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!companyNameMap.get("code").equals("1")){
			throw new ResultException(-2, companyNameMap.get("message").toString(), token, userId, null);
		}
		//获取商户Id
		if(ChkUtil.isEmptyAllObject(companyModel.getCompanyId())) {
			throw new ResultException(-2, "商户Id为空！", token, userId, null);
		}
		//获取该商户数据
		Company company;
		try {
			company = companyDao.get(companyModel.getCompanyId());
			if(null == company || ChkUtil.isEmptyAllObject(company.getStatus()) || company.getStatus().equals("1")) {
				throw new ResultException(-2, "商户数据异常！", token, userId, null);
			}
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		//名称存在校验
		Company companyName = companyDao.getByHql("from Company where status<>'1' and companyName='"+companyModel.getCompanyName()+"' and id<>'"+companyModel.getCompanyId()+"'");
		if(null == companyName) {
			throw new ResultException(-2, "已存在名称为："+companyModel.getCompanyName()+"的商户！", token, userId, null);
		}
		//商户号校验
		if(ChkUtil.isEmptyAllObject(companyModel.getWechatNo())) {
			throw new ResultException(-2, "商户号必填！", token, userId, null);
		}
		Company companyNo = companyDao.getByHql("from Company where status<>'1' and wechatNo='"+companyModel.getWechatNo()+"' and id<>'"+companyModel.getCompanyId()+"'");
		if(null == companyNo) {
			throw new ResultException(-2, "已存在商户号为："+companyModel.getWechatNo()+"的商户！", token, userId, null);
		}
		company.setAddress(companyModel.getAddress());
		company.setAlipayNo(companyModel.getAlipayNo());
		company.setCity(companyModel.getCity());
		company.setCompanyName(companyModel.getCompanyName());
		company.setCounty(companyModel.getCounty());
		company.setPhone(companyModel.getPhone());
		company.setProvince(companyModel.getProvince());
		company.setWechatNo(companyModel.getWechatNo());
		company.setLastUpdateTime(new Date());
		company.setLastUpdateUser(userId);
		companyDao.save(company);
		return null;
	}

	/**
	 * 商户删除接口
	 */
	@Override
	public Object doDeleteCompanyInfo(String companyId, String userId, String token) {
		//员工Id校验
    	OptionUtil.of(companyId).getOrElseThrow(() -> 
    		new ResultException(-2,"商户Id为空!", token, userId, null));
    	//获取对应游客信息
		try {
			Company company = companyDao.get(companyId);
			if(null == company || ChkUtil.isEmptyAllObject(company.getStatus()) 
	    			|| company.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "商户数据异常或该商户数据已被删除！", token, userId, null);
	    	}
			company.setStatus(StateEnum.DELETESTATE.getCode());
			company.setLastUpdateTime(new Date());
			company.setLastUpdateUser(userId);
			companyDao.save(company);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return null;
	}
	
	
}
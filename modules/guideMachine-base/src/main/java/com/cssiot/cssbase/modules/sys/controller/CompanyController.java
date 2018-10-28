package com.cssiot.cssbase.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.sys.service.CompanyService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 商户
 * @author
 * 	2018-10-28 Diego.zhou 新建
 */

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	
	@ApiOperation("商户信息查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=false,value="查询条件jsonStr:{province:'省份',city:'城市',"
				+ "county:'区县',scenicSpotId:'注册景点Id',"
				+ "phone:'手机',roleId:'角色Id',loginName:'登录名',userName:'账号',"
				+ "orderBy:'排序方式'}"),
		@ApiImplicitParam(paramType="query",name="pageNo",dataType="String",required=true,value="页码"),
		@ApiImplicitParam(paramType="query",name="pageSize",dataType="String",required=true,value="一页条数")
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSelectCompanyInfo/{token}/{userId}")
	public Object doSelectCompanyInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr,
			String pageSize,String pageNo,HttpServletRequest request,HttpServletResponse response){
		Object result = companyService.doSelectCompanyInfo(request, response, jsonStr, pageNo, pageSize, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("商户信息新建保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="保存jsonStr:{userModel:{phone:'手机号'}}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSaveCompanyInfo/{token}/{userId}")
	public Object doSaveCompanyInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = companyService.doSaveCompanyInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工信息修改初始化接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="companyId",dataType="String",required=true,value="商户Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateCompanyData/{token}/{userId}")
	public Object doUpdateCompanyData(@PathVariable("token") String token,@PathVariable("userId")String userId,String companyId){
		Object result = companyService.doUpdateCompanyData(companyId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("商户信息修改保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="保存jsonStr:{visitors:{visitorsId:'游客Id',alipayNo:'支付宝账号',"
				+ "visitorsName:'姓名',identityCard:'身份证',sex:'性别',nation:'民族',birthDate:'出生日期',homeAddress:'地址',"
				+ "phone:'手机号',wechatNo:'微信号'}}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateCompanyInfo/{token}/{userId}")
	public Object doUpdateCompanyInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = companyService.doUpdateCompanyInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("商户删除接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="companyId",dataType="String",required=true,value="商户Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doDeleteCompanyInfo/{token}/{userId}")
	public Object doDeleteCompanyInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String companyId){
		Object result = companyService.doDeleteCompanyInfo(companyId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
}
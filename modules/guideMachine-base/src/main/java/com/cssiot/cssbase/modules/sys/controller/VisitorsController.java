package com.cssiot.cssbase.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 游客相关Controller
 * @author 
 * 	2018-10-23 Diego.zhou 新建
 *
 */
@Api(value="游客管理相关接口")
@RestController
@RequestMapping("/visitors")
public class VisitorsController {
	
	@Autowired
	private VisitorsService visitorsService;
	
	@ApiOperation("VIP游客导入模板下载接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@GetMapping("/doExportVipVisitorsTemplateInfo/{token}/{userId}")
	public void doExportVipVisitorsTemplateInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,HttpServletResponse response) {
		visitorsService.doExportVipVisitorsTemplateInfo(response);
	}
	
	@ApiOperation("VIP游客数据导入接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@PostMapping(value = "/doImportVipVisitorsInfo/{token}/{userId}", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	public Object doImportVipVisitorsInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,@ApiParam(value = "上传文件", required = true) MultipartFile file) {
		Object result = visitorsService.doImportVipVisitorsInfo(file, token, userId);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("游客信息查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=false,value="查询条件jsonStr:{province:'省份',city:'城市',"
				+ "county:'区县',scenicSpotId:'注册景点Id',registerTimeStart:'注册时间起始',registerTimeEnd:'注册时间终止',"
				+ "phone:'手机',identityCard:'身份证号',visitorsType:'游客类型(0普通、1VIP、空为全部)',returnStatus:'归还状态(0已归还、1未归还、空为全部)',"
				+ "isBlacklist:'是否黑名单(0是、1否、空为全部)',orderBy:'排序方式'}"),
		@ApiImplicitParam(paramType="query",name="pageNo",dataType="String",required=true,value="页码"),
		@ApiImplicitParam(paramType="query",name="pageSize",dataType="String",required=true,value="一页条数")
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSelectVisitorsInfo/{token}/{userId}")
	public Object doSelectVisitorsInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr,
			String pageSize,String pageNo,HttpServletRequest request,HttpServletResponse response){
		Object result = visitorsService.doSelectVisitorsInfo(request, response, jsonStr, pageNo, pageSize, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("VIP游客信息新建保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="保存jsonStr:{visitors:{phone:'手机号',identityCard:'身份证',vipRemark:'VIP说明'}}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSaveVipVisitorsInfo/{token}/{userId}")
	public Object doSaveVipVisitorsInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = visitorsService.doSaveVipVisitorsInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("游客信息修改初始化接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="visitorsId",dataType="String",required=true,value="游客Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateVisitorsData/{token}/{userId}")
	public Object doUpdateVisitorsData(@PathVariable("token") String token,@PathVariable("userId")String userId,String visitorsId){
		Object result = visitorsService.doUpdateVisitorsData(visitorsId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("游客信息修改保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="保存jsonStr:{visitors:{visitorsId:'游客Id',alipayNo:'支付宝账号',"
				+ "visitorsName:'姓名',identityCard:'身份证',sex:'性别',nation:'民族',birthDate:'出生日期',homeAddress:'地址',"
				+ "phone:'手机号',wechatNo:'微信号'}}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateVisitorsInfo/{token}/{userId}")
	public Object doUpdateVisitorsInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = visitorsService.doUpdateVisitorsInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("手动加入黑名单接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="visitorsId",dataType="String",required=true,value="游客Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doJoinBlacklistInfo/{token}/{userId}")
	public Object doJoinBlacklistInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String visitorsId){
		Object result = visitorsService.doJoinBlacklistInfo(visitorsId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("手动取消黑名单接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="visitorsId",dataType="String",required=true,value="游客Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doCancelBlacklistInfo/{token}/{userId}")
	public Object doCancelBlacklistInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String visitorsId){
		Object result = visitorsService.doCancelBlacklistInfo(visitorsId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("普通游客转Vip")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="visitorsId",dataType="String",required=true,value="游客Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateNormalVisitorsToVipInfo/{token}/{userId}")
	public Object doUpdateNormalVisitorsToVipInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String visitorsId){
		Object result = visitorsService.doUpdateNormalVisitorsToVipInfo(visitorsId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("Vip游客转普通游客")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="visitorsId",dataType="String",required=true,value="游客Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateVipVisitorsToNormalInfo/{token}/{userId}")
	public Object doUpdateVipVisitorsToNormalInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String visitorsId){
		Object result = visitorsService.doUpdateVipVisitorsToNormalInfo(visitorsId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("游客租借历史查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="visitorsId",dataType="String",required=true,value="游客Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSelectVisitorsRentHistoryInfo/{token}/{userId}")
	public Object doSelectVisitorsRentHistoryInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,HttpServletRequest request, HttpServletResponse response,String visitorsId){
		Object result = visitorsService.doSelectVisitorsRentHistoryInfo(request, response, visitorsId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
}

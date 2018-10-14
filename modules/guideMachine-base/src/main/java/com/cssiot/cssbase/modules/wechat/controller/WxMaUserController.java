package com.cssiot.cssbase.modules.wechat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 微信小程序用户接口
 *
 *  @author 
 * 	2018-09-22 Diego.zhou 新建
 * 	参考<a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VisitorsService visitorsService;
    
    /**
     * 游客登录,并获取用户信息
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
    @ApiOperation("游客登录,并获取用户信息接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="jsonStr{code:临时登录凭证,"
				+ "signature:签名,rawData:原始数据,encryptedData:加密数据,iv:加密算法的初始向量}"),
	})
	@PostMapping(value="/doWxUserLogin")
	public Object doWxUserLogin(String jsonStr) throws Exception {
		Object result = visitorsService.doWxUserLogin(jsonStr);
		return ResultUtil.success(result, ResultEnum.SUCCESS, null, null, null);
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
    @ApiOperation("获取微信用户手机号信息接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="jsonStr{openid:openid,"
				+ "signature:签名,rawData:原始数据,encryptedData:加密数据,iv:加密算法的初始向量}"),
	})
	@PostMapping(value="/doGetWxUserPhoneInfo")
	public Object doGetWxUserPhoneInfo(String jsonStr) throws Exception {
		Object result = visitorsService.doGetWxUserPhoneInfo(jsonStr);
		return ResultUtil.success(result, ResultEnum.SUCCESS, null, null, null);
	}

}

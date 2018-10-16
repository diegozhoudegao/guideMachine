package com.cssiot.cssbase.modules.wechat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.base.entity.Cabinet;
import com.cssiot.cssbase.modules.base.entity.Channel;
import com.cssiot.cssbase.modules.base.entity.ScenicSpot;
import com.cssiot.cssbase.modules.base.service.CabinetService;
import com.cssiot.cssbase.modules.base.service.ChannelService;
import com.cssiot.cssbase.modules.base.service.ScenicSpotService;
import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
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
    
    @Autowired
    private CabinetService cabinetService;
    
    @Autowired
    private ScenicSpotService scenicSpotService;
    
    @Autowired
    private ChannelService channelService;
    
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

    /**
     * 扫描租借接口
     * 	1、游客扫描二维码，打开微信小程序，将机柜id发送给http，调用机柜信息查询接口
     * 	1.1http接到查询消息，返回当前机柜中导游机可租借数量、每台租金，每台押金给微信小程序
     * @param cabinetId
     * @return
     */
    @SuppressWarnings("unchecked")
	public Object doGetUsableGuideMachineInfo(String cabinetId) {
    	//通过机柜Id获取可用的导游机数据(当前机柜中导游机可租借数量、每台租金，每台押金)
    	Map map = new HashMap<>();
    	if(ChkUtil.isEmptyAllObject(cabinetId)) {
    		throw new ResultException(-2, "导游机Id为空！", null, null, null);
    	}
		try {
			Cabinet cabinet = cabinetService.get(cabinetId);
			if(null == cabinet || ChkUtil.isEmptyAllObject(cabinet.getScenicSpotId())) {
	    		throw new ResultException(-2, "导游机信息异常，暂时无法租借！", null, null, null);
			}
			//通过导游机id获取导游机所属景点，并返回每个导游机的每台租金和每台押金
			ScenicSpot scenicSpot = scenicSpotService.get(cabinet.getScenicSpotId());
			if(null == scenicSpot) {
	    		throw new ResultException(-2, "景点数据异常，暂时无法租借！", null, null, null);
			}
			//获取租金和押金
			map.put("rentAmount",ChkUtil.isEmptyAllObject(scenicSpot.getRentAmount())?0:scenicSpot.getRentAmount());//租金
			map.put("depositAmount",ChkUtil.isEmptyAllObject(scenicSpot.getDepositAmount())?0:scenicSpot.getDepositAmount());//押金
			//获取该机柜可租导游机数量
			//获取该机柜下的所有仓道
			List<Channel> channelList = channelService.findByHql("from Channel where status='"+StateEnum.NEWSTATE.getCode()+"' and cabinetId='"+cabinetId+"' and channelStatus='"+StateEnum.NEWSTATE.getCode()+"' and guideMachineStatus is not null");
			if(!ChkUtil.isEmptyAllObject(channelList)) {
				for(Channel channel : channelList) {
					//获取该仓道对应的导游机
					
				}
			}else {
				map.put("usableRentNumber",0);
			}
		} catch(ResultException e) { 
			throw e;
		} catch (Exception e) {
			throw new ResultException(ResultEnum.DATA_ERROR, null, null, null);
		}
		return ResultUtil.success(null, ResultEnum.SUCCESS, null, null, null);
    }
}

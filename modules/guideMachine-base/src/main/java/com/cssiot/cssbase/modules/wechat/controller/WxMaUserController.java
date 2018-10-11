package com.cssiot.cssbase.modules.wechat.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.sys.entity.Visitors;
import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssbase.modules.wechat.config.WxMaConfiguration;
import com.cssiot.cssbase.modules.wechat.utils.JsonUtils;
import com.cssiot.cssutil.common.enums.StateEnum;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaMsgServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage.Data;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 微信小程序用户接口
 *
 *  @author 
 * 	2018-09-22 Diego.zhou 新建
 * 	参考<a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private VisitorsService visitorsService;
    
   /**
    * 登陆接口,获取sessionKey和openid
    * @param appid	appId应与配置文件匹配
    * @param code wx.login()获取的code信息
    * @return
    * @author 
    * 		2018-09-22 Diego.zhou 新建
    */
    @GetMapping("/login")
    @SuppressWarnings("all")
    public String login(@PathVariable String appid, String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }
        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            //保存访客数据
            Visitors visitor = new Visitors();
            visitor.setOpenId(session.getOpenid());
            visitor.setCreateTime(new Date());
            visitor.setLastUpdateTime(new Date());
            visitor.setStatus(StateEnum.NEWSTATE.getCode());
            visitor.setVisitorsType("0");//	普通，TODO 后期改为枚举
            visitor.setRegisterTime(new Date());
            visitorsService.save(visitor);
            Map map = new HashMap<>();
            map.put("sessionKey",session.getSessionKey());
            map.put("openid",session.getOpenid());
            map.put("userId", visitor.getId());
            return JsonUtils.toJson(map);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }
        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        //通过openId获取该用户，更新该用户信息
        Visitors visitor = visitorsService.getByHql("from Visitors where status='"+StateEnum.NEWSTATE.getCode()+"' and openId='"+userInfo.getOpenId()+"'");
        if(null != visitor) {
        	//更新用户信息,头像，昵称
        	visitor.setWechatNo(userInfo.getNickName());
        	visitor.setLastUpdateTime(new Date());
        	visitorsService.save(visitor);
        }
        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }
        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }
        // 解密
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        //更新用户信息
        Visitors visitor = visitorsService.getByHql("from Visitors where status='"+StateEnum.NEWSTATE.getCode()+"' and openId='"+userInfo.getOpenId()+"'");
        if(null != visitor) {
        	//更新用户信息
        	visitor.setPhone(phoneNoInfo.getPhoneNumber());
        	visitor.setLastUpdateTime(new Date());
        	visitorsService.save(visitor);
        }
        return JsonUtils.toJson(phoneNoInfo);
    }

    /**
     * 测试发送模板消息
     * 发送模板消息的情况：
     * 		1、在小程序内使用了微信支付接口，此时formId可以取prepay_id
	*  		2、在小程序里用户点击了表单，而且该表单的report-submit属性值为true时，formId取e.detail.formId
     * @param appid	appId 必填
     * @param openid 消息接受人 必填
     * @param formId 表单id 必填
     * @return
     * @author 
     * 		2018-09-23 Diego.zhou 新建
     */
    @GetMapping("/sendMsg")
    public String sendMsg(@PathVariable String appid,String openid,String formId) {

        final WxMaService wxService = WxMaConfiguration.getMaServices().get(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }
        // TEST 测试发送消息是否成功
        try {
        	WxMaTemplateMessage templateMessage = new WxMaTemplateMessage();
        	templateMessage.setToUser(openid);//openId
        	templateMessage.setTemplateId("CzSHg5ThZXpkgHda_8MB52-THUz0MmXAxhgFxZqtZBw");
        	templateMessage.setFormId(formId);
        	List dataList = Arrays.asList(
        			new Data("keyword1","2018-09-23 21:00",""),
        			new Data("keyword2","灌无忧",""),
        			new Data("keyword3","微信",""),
        			new Data("keyword4","100.00",""),
        			new Data("keyword5","e232wa4fhrhfa3e","")
        			);
        	templateMessage.setData(dataList);
        	WxMaMsgServiceImpl msgServiceImpl = new WxMaMsgServiceImpl(wxService);
        	msgServiceImpl.sendTemplateMsg(templateMessage);
        }catch(Exception e) {
        	 this.logger.info("发送模板消息失败，{}",e);
        }
        return "发送消息";
    }
}

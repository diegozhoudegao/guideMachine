package com.cssiot.cssbase.modules.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

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
    
    
    /**
     * 游客信息查询接口
     * @param jsonStr 查询条件
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doSelectVisitorsInfo(HttpServletRequest request, HttpServletResponse response,String jsonStr,String pageNo,String pageSize,String userId,String token);
    
    /**
     * VIP游客新建保存接口
     * @param jsonStr 保存JSOn
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doSaveVipVisitorsInfo(String jsonStr,String userId,String token);
    
    /**
     * VIP模板导出接口
     * @return
     */
    public void doExportVipVisitorsTemplateInfo(HttpServletResponse response);
    
    /**
     * VIP游客信息导入接口
     * @return
     */
    public Object doImportVipVisitorsInfo(MultipartFile file,String token,String userId);
    
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
    public Object doUpdateVisitorsData(String visitorsId,String userId,String token);
    
    /**
     * 游客信息修改保存接口
     * @param jsonStr
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doUpdateVisitorsInfo(String jsonStr,String userId,String token);
    
    /**
     * 手动加入黑名单接口
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
    public Object doJoinBlacklistInfo(String visitorsId,String userId,String token);
    
    /**
     * 手动取消黑名单接口
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
    public Object doCancelBlacklistInfo(String visitorsId,String userId,String token);
    
    /**
     * 普通游客转Vip
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
    public Object doUpdateNormalVisitorsToVipInfo(String visitorsId,String userId,String token);
    
    /**
     * Vip游客转普通游客
     * @param visitorsId 游客Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author
     * 	2018-10-24 Diego.zhou 新建
     */
    public Object doUpdateVipVisitorsToNormalInfo(String visitorsId,String userId,String token);
    
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
    public Object doSelectVisitorsRentHistoryInfo(HttpServletRequest request, HttpServletResponse response,String visitorsId,String userId,String token);
}
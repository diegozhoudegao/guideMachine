package com.cssiot.cssbase.modules.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cssiot.cssbase.modules.sys.entity.Company;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 公司表Service
 * @author
 *	2018-10-07 athena 创建
 */
public interface CompanyService extends BaseService<Company>{
	
	/**
     * 商户信息查询接口
     * @param jsonStr 查询条件
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author 
     * 	2018-10-28 Diego.zhou 新建
     */
    public Object doSelectCompanyInfo(HttpServletRequest request, HttpServletResponse response,String jsonStr,String pageNo,String pageSize,String userId,String token);

    /**
     * 商户新建保存接口
     * @param jsonStr 保存JSOn
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doSaveCompanyInfo(String jsonStr,String userId,String token);
    
    
    /**
     * 商户信息修改初始化接口
     * @param usersId 商户Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return 
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    @SuppressWarnings("all")
    public Object doUpdateCompanyData(String companyId,String userId,String token);
    
    /**
     * 商户信息修改保存接口
     * @param jsonStr
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doUpdateCompanyInfo(String jsonStr,String userId,String token);
    
    /**
     * 商户删除接口
     * @param usersId 商户Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return 
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doDeleteCompanyInfo(String companyId,String userId,String token);
}
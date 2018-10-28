package com.cssiot.cssbase.modules.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssutil.common.data.IsEnabledModel;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 员工表Service
 * @author
 *	2018-10-07 athena 创建
 */
public interface UserService extends BaseService<User>{
	
	/**
	 * 判断当前用户是否具有操作此功能的权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel isAccess(String userId,String content,String operate) throws Exception;
	
	/**
	 * 查询当前用户具有哪些操作权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel hasAccess(String userId,String content) throws Exception;
	
	/**
     * 员工信息查询接口
     * @param jsonStr 查询条件
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return
     * @author 
     * 	2018-10-28 Diego.zhou 新建
     */
    public Object doSelectUsersInfo(HttpServletRequest request, HttpServletResponse response,String jsonStr,String pageNo,String pageSize,String userId,String token);
    
    /**
     * 员工新建保存接口
     * @param jsonStr 保存JSOn
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doSaveUsersInfo(String jsonStr,String userId,String token);
    
    /**
     * 员工模板导出接口
     * @return
     */
    public Object doExportUsersTemplateInfo(HttpServletResponse response);
    
    /**
     * 员工信息导入接口
     * @return
     */
    public Object doImportUsersInfo(MultipartFile file,String token,String userId);
    
    /**
     * 员工信息修改初始化接口
     * @param usersId 员工Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return 
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    @SuppressWarnings("all")
    public Object doUpdateUsersData(String usersId,String userId,String token);
    
    /**
     * 员工信息修改保存接口
     * @param jsonStr
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return null
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doUpdateUsersInfo(String jsonStr,String userId,String token);
    
    /**
     * 员工离职接口
     * @param usersId 员工Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return 
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doDisableUsersInfo(String usersId,String userId,String token);
    
    /**
     * 员工离职恢复接口
     * @param usersId 员工Id
     * @param userId 当前登录者Id
     * @param token token令牌
     * @return 
     * @author 
     * 	2018-10-22 Diego.zhou 新建
     */
    public Object doEnableUsersInfo(String usersId,String userId,String token);
}
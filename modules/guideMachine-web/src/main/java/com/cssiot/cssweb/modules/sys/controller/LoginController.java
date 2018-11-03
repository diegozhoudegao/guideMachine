package com.cssiot.cssweb.modules.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cssiot.cssbase.modules.sys.data.LoginUserModel;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssbase.modules.sys.service.UserTreeService;
import com.cssiot.cssutil.common.constant.RedisConstant;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.MD5Util;
import com.cssiot.cssutil.common.utils.Parameter;
import com.cssiot.cssutil.common.utils.ResultUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录
 * @author
 * 	2017-10-27 athena 创建
 * 	2017-12-27 athena 修改状态数字为枚举型获取
 */

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTreeService userTreeService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 登录认证接口
	 * @param loginName 账号
	 * @param password 密码
	 * @param request
	 * @author 
	 * 	2017-11-25 athena 新建
	 * 	2018-01-02 athena 增加用户头像查询
	 * 	2018-11-04 Diego.zhou 修改传参以及校验方法
	 */
	@ApiOperation("登录认证接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="loginName",dataType="String",required=true,value="账号"),
		@ApiImplicitParam(paramType="query",name="password",dataType="String",required=true,value="密码"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/login")
	public Object login(@RequestParam("loginName") String loginName,@RequestParam("password") String password,HttpServletRequest request) {
		if(ChkUtil.isEmpty(loginName)) {
			log.warn("登录校验={}",ResultEnum.PHONE_NULL.getMessage());
			throw new ResultException(ResultEnum.PHONE_NULL,null,null,null);
		}
		Parameter parameter =new Parameter();
		parameter.put("loginName", loginName);
		User user=(User)userService.getByHql(" from User where state='"+StateEnum.NEWSTATE.getCode()+"' and loginName=:loginName",parameter);
		if(ChkUtil.isEmptyAllObject(user)){
			log.warn("登录校验={}",ResultEnum.CUSTOMER_USERNOTEXIST.getMessage());
			throw new ResultException(-2, "账号不存在", null, null, null);
		}
		if(!user.getPassword().equals(password)){
			log.warn("登录校验={}",ResultEnum.PASSWORD_ERROR.getMessage());
			throw new ResultException(ResultEnum.PASSWORD_ERROR,null,null,null);
		}
		LoginUserModel 	loginUserModel = getLoginUserModel("web", user, request);
		Map map = new HashMap<>();
		map.put("loginUserModel", loginUserModel);
		return ResultUtil.success(map, ResultEnum.SUCCESS, loginUserModel.getToken(), user.getId(), null);
	}
	
	/**
	 * 获取登陆者信息
	 * @param clientType 客户端类型(android/ios/web等)
	 * @param user 员工信息
	 * @param request
	 * @return
	 * @author 
	 * 	2018-10-09 athena 迁移
	 */
	@SuppressWarnings("all")
	private LoginUserModel getLoginUserModel(String clientType, User user, HttpServletRequest request) {
		LoginUserModel loginUserModel=new LoginUserModel(user);
		//登录生成token,并将token放入缓存
		String token=user.getId()+","+MD5Util.getMD5ofStr(user.getLoginName()+user.getPassword()+new Date().getTime(), 2);
		Integer expire=RedisConstant.WEB_EXPIRE;
		if(!ChkUtil.isEmpty(clientType) && clientType.toLowerCase().equals("web")) {
			redisTemplate.opsForValue().set(String.format(RedisConstant.WEB_TOKEN_PREFIX, user.getId()), token,expire,TimeUnit.SECONDS);
		}else {
			redisTemplate.opsForValue().set(String.format(RedisConstant.APP_TOKEN_PREFIX, user.getId()), token,expire,TimeUnit.SECONDS);
		}
		loginUserModel.setToken(token);
		return loginUserModel;
	}
	
	/**
	 * 登录退出接口
	 * @param userId 当前登录者Id
	 * @author 
	 * 	2018-10-09 athena 迁移
	 * 	2018-11-04 Diego.zhou 修改传参以及校验方法
	 */
	@ApiOperation("登录退出接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@PostMapping(value="/logout/{token}/{userId}")
	public Object logout(@PathVariable("userId") String userId,@PathVariable("token") String token) throws Exception {
		User user=userService.get(userId);
		if(user==null) {
			log.warn("退出登录={}",ResultEnum.USER_ERROR.getMessage());
			return ResultUtil.error4008(ResultEnum.USER_ERROR, token, userId, null);
		}
		if(redisTemplate.hasKey(String.format(RedisConstant.WEB_TOKEN_PREFIX, user.getId()))) {
			redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.WEB_TOKEN_PREFIX,user.getId()));
		}else {
			return ResultUtil.error4008(ResultEnum.REDIS_NULL, token, userId, null);
		}
		return ResultUtil.success(null, ResultEnum.SUCCESS, null, userId, null);
	}
	
	/**
	 * 获取用户对应树菜单接口
	 * @param token 安全令牌
	 * @param userId 当前登录者Id
	 * @author 
	 * 	2017-11-15 athena 迁移
	 * 	2018-11-04 Diego.zhou 修改传参以及校验方法
	 */
	@ApiOperation("获取用户对应树菜单接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@PostMapping(value="/getTreeText/{token}/{userId}")
	public Object getTreeText(@PathVariable("userId") String userId,@PathVariable("token") String token) throws Exception {
		Object result = userTreeService.getTreeText(userId, token, null);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
}

package com.cssiot.cssweb.common.aspect;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssutil.common.constant.RedisConstant;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.ResultUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验权限、token，写入日志
 * @author
 * 	2018-10-09 athena 迁移调整
 */
//@Aspect
//@Component
@Slf4j
public class HttpAspect {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Pointcut("execution(public * com.cssiot.*.modules.*.controller.*Controller.*(..))" +
	"&& !execution(public * com.cssiot.cssweb.modules.sys.controller.LoginController.*(..))"
	)
	public void verify() {
	}
	
	@Around("verify() && args(clientType,token,loginName,operation,..)")
	public Object doBefore(ProceedingJoinPoint joinPoint,String clientType,String token,String loginName,String operation) throws Throwable {
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		//url
		log.info("message");

		log.info("url={}",request.getRequestURL());
		//method
		log.info("method={}",request.getMethod());
		//ip
		log.info("ip={}",request.getRemoteAddr());
		//类方法
		log.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
		//参数
		log.info("args={}",joinPoint.getArgs());
		if(StringUtils.isEmpty(loginName)){
			log.warn("登录校验={}",ResultEnum.USERID_NULL.getMessage());
			return ResultUtil.error0(ResultEnum.USERID_NULL, loginName, clientType);
		}
//		String module="";//模块
//		String oper="";//操作
//		if(!ChkUtil.isEmpty(operation) && operation.contains(".")){
//			String[] opers=operation.split("\\.");
//			if(opers.length==2){
//				module=opers[0];
//				oper=opers[1];
//			}
//		}
		//保存操作日志
//		if(!ChkUtil.isEmptyAllObject(module) && !ChkUtil.isEmptyAllObject(oper) && module.length()<50 && oper.length()<20){
//			operationLogService.saveLogInfo(loginName,request,oper,request.getMethod()+joinPoint.getSignature().getDeclaringTypeName(),module,clientType);
//		}
		String appToken = "";//从缓存中取出的token
		String[] tokenData  = {};//传入的token解析后数据
		User user = null;//用户信息
		//当token不存在、为空、或者不为正确格式或者没有来源提示错误
		if(!"1".equals(loginName)){
			if(StringUtils.isEmpty(token)||token.indexOf(",")==-1){
				log.warn("登录校验={}",ResultEnum.TOKEN_NULL.getMessage());
				return ResultUtil.error0(ResultEnum.TOKEN_NULL, loginName, clientType);
			}
		}
		log.info("token={}",token);
		log.info("loginName={}",loginName);
		tokenData = token.split(",");
		if(!tokenData[0].equals(loginName)) {
			log.warn("登录校验={}",ResultEnum.TOKEN_ERROR.getMessage());
			return ResultUtil.error0(ResultEnum.TOKEN_ERROR, loginName, clientType);
		}
		user=userService.getByHql("from User where id='"+tokenData[0]+"' and (state='0' or state='99')");
		if(user==null) {
			log.warn("登录校验={}",ResultEnum.USER_ERROR.getMessage());
			return ResultUtil.error0(ResultEnum.USER_ERROR, loginName, clientType);
		}
		if(!ChkUtil.isEmptyAllObject(clientType) && clientType.toLowerCase().equals("web")) {
			appToken = redisTemplate.opsForValue().get(String.format(RedisConstant.WEB_TOKEN_PREFIX, user.getId()));
		}else {
			appToken = redisTemplate.opsForValue().get(String.format(RedisConstant.APP_TOKEN_PREFIX, user.getId()));
		}
        if (StringUtils.isEmpty(appToken)) {
			log.warn("登录校验={}",ResultEnum.REDIS_NULL.getMessage());
			return ResultUtil.error0(ResultEnum.REDIS_NULL, loginName, clientType);
		}else {
			if(!appToken.equals(token)){
				log.warn("登录校验={}",ResultEnum.TOKEN_ERROR.getMessage());
				return ResultUtil.error0(ResultEnum.TOKEN_ERROR, loginName, clientType);
    		}
			//判断功能操作权限
//			boolean flag = user.getStatus().equals("99")&&user.getLoginName().equals("admin");//admin免判断
//			if(!ChkUtil.isEmpty(operation) && operation.contains(".") && !flag && !ChkUtil.isEmpty(module) && !ChkUtil.isEmpty(oper)){
//				IsEnabledModel pertemp=accessService.isAccess(user.getId(),module,oper);
//				if(pertemp.getCode().equals("4008")){
//					log.warn("登录校验={}",pertemp.getMessage());
//					return ResultUtil.error4008(0, pertemp.getMessage(), token, loginName, clientType);
//				}
//			}
			//生成新token，重新放入
//			newToken=user.getId()+","+MD5Util.getMD5ofStr(user.getLoginName()+user.getPassword()+new Date().getTime(), 2);
			Integer expire=RedisConstant.WEB_EXPIRE;
			if(!ChkUtil.isEmptyAllObject(clientType) && clientType.toLowerCase().equals("web")) {
				redisTemplate.opsForValue().set(String.format(RedisConstant.WEB_TOKEN_PREFIX, user.getId()), appToken,expire,TimeUnit.SECONDS);
			}else {
				redisTemplate.opsForValue().set(String.format(RedisConstant.APP_TOKEN_PREFIX, user.getId()), appToken,expire,TimeUnit.SECONDS);
			}
			token=appToken;
		}
        Object[] args=joinPoint.getArgs();
        args[1]=token;
        request.setAttribute("token", token);
        Object retVal = null;  //连接点方法返回值
        retVal = joinPoint.proceed(args);
        return retVal;
	}
	
	@After("verify()")
	public void doAfter() {
		log.info("已完成该操作");
	}
	
	@AfterReturning(returning="object",pointcut="verify()")
	public void doAfterReturning(Object object) {
		log.info("response={}",object.toString());
	}
	
}

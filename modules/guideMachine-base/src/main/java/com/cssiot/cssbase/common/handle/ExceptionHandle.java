package com.cssiot.cssbase.common.handle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cssiot.cssutil.common.data.Result;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.ResultUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 捕获异常
 * @author
 * 	2017-09-21 athena 创建
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {
	
//	@Autowired
//	private BugLogService bugLogService;
	
	/**
	 * 捕获全局异常
	 * @param e 异常信息
	 * @param token 安全令牌
	 * @param loginName 用户id
	 * @param clientType 客户端类型(app/ios/web等)
	 * @return result 请求返回的最外层对象
	 * @author
	 * 	2017-10-07 athena 创建
	 */
	@SuppressWarnings("all")
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Result handle(Exception e) {
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		if(e instanceof ResultException) {
			ResultException resultException=(ResultException) e;
			if(!ChkUtil.isEmpty(resultException.getMsgCode()) && resultException.getMsgCode()==-3){
//				bugLogService.doSaveBugLogInfo(resultException.getToken(),resultException.getLoginName(),request,e,request.getMethod(),resultException.getClientType(),null);
				log.error("系统异常={}",e);
				return ResultUtil.error4009(ResultEnum.UNKONW_ERROR, resultException.getToken(),resultException.getLoginName(),resultException.getClientType());
			}
			log.error("正常异常={}",resultException);
			if(ChkUtil.isEmptyAllObject(resultException.getData())){
				return ResultUtil.error4008(resultException.getMsgCode(), resultException.getMessage(), resultException.getToken(), resultException.getLoginName(), resultException.getClientType());
			}else{
				return ResultUtil.error4008(resultException.getData(),resultException.getMsgCode(), resultException.getMessage(), resultException.getToken(), resultException.getLoginName(), resultException.getClientType());
			}
		}else {
//			bugLogService.doSaveBugLogInfo(null,"系统异常",request,e,request.getMethod(),null,null);
			log.error("系统异常={}",e);
			return ResultUtil.error4009(ResultEnum.UNKONW_ERROR, null, null, null);
		}
	}
}

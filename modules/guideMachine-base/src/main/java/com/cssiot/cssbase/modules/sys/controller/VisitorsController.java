package com.cssiot.cssbase.modules.sys.controller;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 游客相关Controller
 * @author 
 * 	2018-10-23 Diego.zhou 新建
 *
 */
@RestController
@RequestMapping("/visitors")
public class VisitorsController {
	
	@Autowired
	private VisitorsService visitorsService;
	
	/**
	 * VIP游客导入模板下载接口
	 * @param response
	 * @author 
	 * 	2018-10-23 Diego.zhou 新建
	 */
	@ApiOperation("VIP游客导入模板下载接口")
	@GetMapping("/doExportVIPVisitorsTemplateInfo")
	public void doExportVIPVisitorsTemplateInfo(HttpServletResponse response) {
		visitorsService.doExportVIPVisitorsTemplateInfo(response);
	}
	
	/**
	 * VIP游客数据导入接口
	 * @param file 附件
	 * @param token token令牌
	 * @param userId 当前登录者Id
	 * @return
	 * @author 
	 * 	2018-1023 DIego.zhou 新建
	 */
	@ApiOperation("VIP游客数据导入接口")
	@PostMapping(value = "/doImportVIPVisitorsInfo", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	public Object doImportVIPVisitorsInfo(@ApiParam(value = "上传文件", required = true) MultipartFile file,@PathVariable("token") String token,@PathVariable("userId")String userId) {
		Object result = visitorsService.doImportVIPVisitorsInfo(file, token, userId);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
}

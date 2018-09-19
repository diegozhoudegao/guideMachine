package com.cssiot.cssutil.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

/****
 * 代码生成
 * @author 
 * 	2017-12-28 athena 新建
 * 
 */
@Slf4j
public class GenerateUtil {

	/**
	 * 生成表对应sql文件
	 * @param moduleName 方案id
	 * @param subModuleName 小模块名称
	 * @param className 表单id
	 * @param entityData 组件英文名称集合
	 * @param lengthData 长度改变的组件集合
	 * @param ftlName 调用模板文件名称
	 * @return filePath 生成sql文件存放路径
	 * @throws IOException
	 * @throws Exception
	 * @author
	 * 	2017-12-28 athena 迁移调整
	 * 	2018-02-26 Diego.zhou 解决在war包启动时生成表报错
	 */
	public static String generateSql(String moduleName,String subModuleName,String className,List entityData,List lengthData,String ftlName) throws IOException,Exception{
		// 获取文件分隔符
		String separator = File.separator;
		//获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		//暂时注释该段代码，在war包启动时报错
//		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
//			projectPath = projectPath.getParentFile();
//		}
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/template", "/", separator);
		//sql存放路径
		String tar = new String(Global.getConfig("filePath","wagon"));
		String[] tars = null;
		if(tar.indexOf(separator)>=0){
			tars=tar.split(separator);
		}else if(tar.indexOf("\\")>=0){
			tars=tar.split("\\");
		}else if(tar.indexOf("/")>=0){
			tars=tar.split("/");
		}else{
			tars = new String[1];
			tars[0]=tar;
		}
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		String temppath = request.getSession().getServletContext().getRealPath(tar)
				+ separator;
		int temp1=temppath.lastIndexOf(separator,temppath.lastIndexOf(separator)-1);
		int temp2=0;
		for(int j=0;j<tars.length;j++){
			temp2=temppath.lastIndexOf(separator, temp1-1);
			temp1=temp2;
		}
		String temptar="";
		for(int j=0;j<tars.length;j++){
			if(ChkUtil.isEmpty(temptar)){
				temptar=tars[j];
			}else{
				temptar=temptar+separator+tars[j];
			}
		}
		String tempPath=temppath.substring(0,temp2)+separator+temptar;
		String sqlPath = StringUtils.replace(tempPath+"/sql", "/", separator);
		//处理传值
		//全小写
		moduleName=!ChkUtil.isEmpty(moduleName)?StringUtils.lowerCase(moduleName):"";
		subModuleName=!ChkUtil.isEmpty(subModuleName)?StringUtils.lowerCase(subModuleName):"";
		//首位小写类名
		className=StringUtils.uncapitalize(className);
		//判断添加还是修改
		String temp="t_";
		String tableName=temp+moduleName+(StringUtils.isNotBlank(subModuleName)?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+className;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(tplPath));
		// 定义模板变量
		Map<String, Object> model = Maps.newHashMap();
		model.put("tableName",tableName);
		model.put("entityData",entityData);
		model.put("lengthData",lengthData);
		// 生成 sql脚本文件
		Template template = cfg.getTemplate(ftlName);
		String content = renderTemplate(template, model);
		//sql脚本文件路径
		String filePath="";
		if(!ChkUtil.isEmpty(ftlName) && ftlName.equals("altersql.ftl")){
			temp="a_";
			filePath = sqlPath+separator+moduleName+separator+"sql"+separator
					+StringUtils.lowerCase(subModuleName)+separator+temp+tableName+".sql";
		}else{
			filePath = sqlPath+separator+moduleName+separator+"sql"+separator
					+StringUtils.lowerCase(subModuleName)+separator+tableName+".sql";
		}
		FileUtil.contentToFile(content, filePath);
		return filePath;
	}
	
	/**
	 * 获取表单对应的sql路径
	 * @param moduleName
	 * @param subModuleName
	 * @param className
	 * @param ftlName
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static Map generateFormSql(String moduleName,String subModuleName,String className,String ftlName) throws IOException,Exception{
		Map map = new HashMap<>();
		// 获取文件分隔符
		String separator = File.separator;
		//sql存放路径
		String tar = new String(Global.getConfig("filePath","wagon"));
		String[] tars = null;
		if(tar.indexOf(separator)>=0){
			tars=tar.split(separator);
		}else if(tar.indexOf("\\")>=0){
			tars=tar.split("\\");
		}else if(tar.indexOf("/")>=0){
			tars=tar.split("/");
		}else{
			tars = new String[1];
			tars[0]=tar;
		}
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		String temppath = request.getSession().getServletContext().getRealPath(tar)
				+ separator;
		int temp1=temppath.lastIndexOf(separator,temppath.lastIndexOf(separator)-1);
		int temp2=0;
		for(int j=0;j<tars.length;j++){
			temp2=temppath.lastIndexOf(separator, temp1-1);
			temp1=temp2;
		}
		String temptar="";
		for(int j=0;j<tars.length;j++){
			if(ChkUtil.isEmpty(temptar)){
				temptar=tars[j];
			}else{
				temptar=temptar+separator+tars[j];
			}
		}
		String tempPath=temppath.substring(0,temp2)+separator+temptar;
		String sqlPath = StringUtils.replace(tempPath+"/sql", "/", separator);
		//处理传值
		//全小写
		moduleName=!ChkUtil.isEmpty(moduleName)?StringUtils.lowerCase(moduleName):"";
		subModuleName=!ChkUtil.isEmpty(subModuleName)?StringUtils.lowerCase(subModuleName):"";
		//首位小写类名
		className=StringUtils.uncapitalize(className);
		//判断添加还是修改
		String temp="t_";
		String tableName=temp+moduleName+(StringUtils.isNotBlank(subModuleName)?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+className;
		//sql脚本文件路径
		String filePath="";
		if(!ChkUtil.isEmpty(ftlName) && ftlName.equals("altersql.ftl")){
			temp="a_";
			filePath = sqlPath+separator+moduleName+separator+"sql"+separator
					+StringUtils.lowerCase(subModuleName)+separator+temp+tableName+".sql";
		}else{
			filePath = sqlPath+separator+moduleName+separator+"sql"+separator
					+StringUtils.lowerCase(subModuleName)+separator+tableName+".sql";
		}
		map.put("filePath", filePath);
		map.put("tableName", tableName);
		return map;
	}
	
	/**
	 * 生成视图对应sql文件
	 * @param moduleName 方案id
	 * @param subModuleName 小模块名称
	 * @param className 数据源id
	 * @param viewSql 查询语句
	 * @param ftlName 调用模板文件名称，如viewsql.ftl
	 * @return filePath 生成sql文件存放路径
	 * @throws IOException
	 * @throws Exception
	 * @author
	 * 	2018-06-27 athena 新增
	 */
	public static String generateViewSql(String moduleName,String subModuleName,String className,String viewSql,String ftlName) throws IOException,Exception{
		// 获取文件分隔符
		String separator = File.separator;
		//获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/template", "/", separator);
		//sql存放路径
		String tar = new String(Global.getConfig("filePath","wagon"));
		String[] tars = null;
		if(tar.indexOf(separator)>=0){
			tars=tar.split(separator);
		}else if(tar.indexOf("\\")>=0){
			tars=tar.split("\\");
		}else if(tar.indexOf("/")>=0){
			tars=tar.split("/");
		}else{
			tars = new String[1];
			tars[0]=tar;
		}
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		String temppath = request.getSession().getServletContext().getRealPath(tar)
				+ separator;
		int temp1=temppath.lastIndexOf(separator,temppath.lastIndexOf(separator)-1);
		int temp2=0;
		for(int j=0;j<tars.length;j++){
			temp2=temppath.lastIndexOf(separator, temp1-1);
			temp1=temp2;
		}
		String temptar="";
		for(int j=0;j<tars.length;j++){
			if(ChkUtil.isEmpty(temptar)){
				temptar=tars[j];
			}else{
				temptar=temptar+separator+tars[j];
			}
		}
		String tempPath=temppath.substring(0,temp2)+separator+temptar;
		String sqlPath = StringUtils.replace(tempPath+"/viewsql", "/", separator);
		//处理传值
		//全小写
		moduleName=!ChkUtil.isEmpty(moduleName)?StringUtils.lowerCase(moduleName):"";
		subModuleName=!ChkUtil.isEmpty(subModuleName)?StringUtils.lowerCase(subModuleName):"";
		//首位小写类名
		className=StringUtils.uncapitalize(className);
		//判断添加还是修改
		String temp="v_";
		String viewName=temp+moduleName+(StringUtils.isNotBlank(subModuleName)?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+className;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(tplPath));
		// 定义模板变量
		Map<String, Object> model = Maps.newHashMap();
		model.put("viewName",viewName);
		model.put("viewSql",viewSql);
		// 生成 sql脚本文件
		Template template = cfg.getTemplate(ftlName);
		String content = renderTemplate(template, model);
		//sql脚本文件路径
		String filePath="";
		filePath = sqlPath+separator+moduleName+separator+"viewsql"+separator+StringUtils.lowerCase(subModuleName)+separator+viewName+".sql";
		FileUtil.contentToFile(content, filePath);
		return filePath;
	}
	
	/**
	 * 生成sql文件内容
	 * @param moduleName 方案英文名称
	 * @param subModuleName 小模块名称
	 * @param className 表单英文名称
	 * @param entityData 组件英文名称
	 * @param ftlName 调用模板文件名称
	 * @return filePath 生成sql文件存放路径
	 * @throws IOException
	 * @throws Exception
	 * @author
	 * 	2017-12-28 athena 迁移调整
	 */
	public static String getSql(String moduleName,String subModuleName,String className,List entityData,String ftlName) throws IOException,Exception{
		// 获取文件分隔符
		String separator = File.separator;
		//获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
			projectPath = projectPath.getParentFile();
		}
		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/src/main/resources/template", "/", separator);
		//处理传值
		//全小写
		moduleName=!ChkUtil.isEmpty(moduleName)?StringUtils.lowerCase(moduleName):"";
		subModuleName=!ChkUtil.isEmpty(subModuleName)?StringUtils.lowerCase(subModuleName):"";
		//首位小写类名
		className=StringUtils.uncapitalize(className);
		//判断添加还是修改
		String temp="t_";
		if(!ChkUtil.isEmpty(ftlName) && ftlName.equals("altersql.ftl")){
			temp="a_";
		}
		String tableName=temp+moduleName+moduleName+(StringUtils.isNotBlank(subModuleName)?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+className;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(tplPath));
		// 定义模板变量
		Map<String, Object> model = Maps.newHashMap();
		model.put("tableName",tableName);
		model.put("entityData",entityData);
		// 生成 sql脚本
		Template template = cfg.getTemplate(ftlName);
		String content = renderTemplate(template, model);
		return content;
	}

	public static String renderTemplate(Template template, Object model) {
		try {
			StringWriter result = new StringWriter();
			template.process(model, result);
			return result.toString();
		} catch (Exception e) {
		}
		return null;
	}
}

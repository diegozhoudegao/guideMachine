package com.cssiot.cssutil.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
* @ClassName: FileHandleUtil 
* @Description: 文件的相关处理，如上传或下，输入输入出等
* @author 作者 jacking
* @date 2016-5-22 上午1:22:46
 */
public class FileHandleUtil {
	//标识文件后缀名.xls,.xlsx
	public static String suffixName=null;
	
	/**
	* @Description: 文件上传的处理
	* @param  设定
	* @author 作者 jacking 
	* @date 2016-5-22 上午1:23:59 
	* @return
	 */
	@SuppressWarnings("all")
	public static Map fileUpload(HttpServletRequest request,String savePath) {
		Map m=new HashMap();
		  m.put("code", "0");
		   m.put("info", "上传出现异常");
		   m.put("url", "");
		try {
			Date date=new Date();
			String newName =Times.getDateToStringHHmmssSSS(date);// 自定义文件名字
			 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			 CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile("file");// 表单中对应的文件名；
			   if (orginalFile != null && !orginalFile.isEmpty()) {
				   String fileName = orginalFile.getOriginalFilename();
				   String suffix = fileName.substring(fileName.indexOf("."));// 获得后缀名
				   fileName=newName+suffix;
				    File file = new File(savePath);
					if (!file.exists()) {// 判断文件夹是否存在，不存在就根据上面的路径创建文件夹
						file.mkdirs();
					}
					String realpath = request.getSession().getServletContext()
							.getRealPath(savePath );// 给予存储路径
					File f = new File(realpath + File.separator + fileName);
					String url =savePath+ fileName;// 获得相对路径
					FileUtils.copyInputStreamToFile(orginalFile.getInputStream(), f);// 获取文件，并按照路径保存
					   m.put("code", "1");
					   m.put("info", "上传成功");
					   m.put("url", url);
					   m.put("date", date);
					   return m;
			   }
		} catch (Exception e) {
			e.printStackTrace();
			return m;
		}
		return m;
	}
	/**
	* @Description: 文件上传的处理
	* @param  paramName  // 表单中对应的文件name名；
	* @author 作者 jacking 
	* @date 2016-5-22 上午1:23:59 
	* @return
	 * @throws IOException 
	 */
	@SuppressWarnings("all")
	public static InputStream getfileUploadInputStream(HttpServletRequest request,String paramName) throws IOException {
	         
			 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			 CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile(paramName);// 表单中对应的文件名；
			 String fileName = orginalFile.getOriginalFilename();
			   String suffix = fileName.substring(fileName.indexOf("."));// 获得后缀名
				if (!".xlsx".equalsIgnoreCase(suffix) && !".xls".equalsIgnoreCase(suffix)) {
					 suffixName="";
				}else{
					suffixName=suffix;
				}
		return orginalFile.getInputStream();
	}
	public static Map<String,Object> getfileUploadInputStreamMap(HttpServletRequest request,String paramName)  {
		
		Map map=new HashMap();
		boolean result=false;
		InputStream in=null;
		String  message="文件扩展名或格式不正确,不是excel文件";
		String  url="dataError";//一级错误
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile(paramName);// 表单中对应的文件名；
		String fileName = orginalFile.getOriginalFilename();
		String suffix = fileName.substring(fileName.lastIndexOf(".")==-1?0:fileName.lastIndexOf("."));// 获得后缀名
		if (!".xlsx".equalsIgnoreCase(suffix) && !".xls".equalsIgnoreCase(suffix)) {
			map.put("result", result);
			map.put("inputStream", in);
			map.put("message", message);
			map.put("url", url);
		}else{
			 try {
				 result=true;
				in=orginalFile.getInputStream();
				message="文件格式正确";
				url=orginalFile.getName();
				map.put("result", result);
				map.put("inputStream", in);
				map.put("message", message);
				map.put("url", url);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	
		return map;
	}
	
 
	/**
	* @Description: 文件上传的处理
	* @param  paramName  // 表单中对应的文件name名；
	* @author 作者 jacking 
	* @date 2016-5-22 上午1:23:59 
	* @return
	 * @throws IOException 
	 */
	@SuppressWarnings("all")
	public static InputStream getfileUploadInputStream(HttpServletRequest request) throws IOException {
		return request.getInputStream();
	}
	
	
}

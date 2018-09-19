package com.cssiot.cssutil.common.utils.httpclient;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

/**
 * 使用httpclient工具类实现请求处理
 * commons-httpclient的延展
 * @description TODO
 * @author song
 * @time 2015-12-29 下午11:27:31
 */
@SuppressWarnings("deprecation")
public class HttpClientUtils {
	
	/**
	 * 发出请求,DELETE请求params为空
	 * @param sendRequest 请求地址
	 * @param requestMethod 请求方式  GET/POST/put/delete
	 * @param params 查询参数
	 * @param timeout 超时 如果为-1则为默认值
	 * @return 通过map进行数据返回，其中flag标示：2-请求方式不对；0-标示成功(value)；1-无返回信息；3-请求异常，并附有HTTP请求状态statusCode;4-代码异常，并有异常信息
	 * @author song
	 * @time 2015-12-29 下午11:23:54
	 */
	public static Map<String,Object> sendRequest(String requestUrl, HttpMethod requestMethod,List<NameValuePair> params,int timeout) {
		Map<String,Object>  retMap=new HashMap<String, Object>();
		try {
			URL url=new URL(requestUrl);
			return HttpClientUtils.sendRequest(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(), requestMethod, params,timeout);
		} catch (MalformedURLException e) {
			retMap.put("flag", "5");
			retMap.put("mes", "请求地址异常");
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 发出请求,DELETE请求params为空
	 * @param scheme 请求模式
	 * @param host 主机
	 * @param port 端口:-1标识没有端口
	 * @param path 资源地址
	 * @param requestMethod 请求方式  GET/POST/put/delete
	 * @param params 查询参数
	 * @param timeout 超时 如果为-1则为默认值
	 * @return 通过map进行数据返回，其中flag标示：2-请求方式不对；0-标示成功(value)；1-无返回信息；3-请求异常，并附有HTTP请求状态statusCode;4-代码异常，并有异常信息
	 * @author song
	 * @time 2015-12-29 下午11:23:54
	 */
	public static Map<String,Object> sendRequest(String scheme, String host, int port, String path, HttpMethod requestMethod,List<NameValuePair> params,int timeout) {
		Map<String,Object>  retMap=new HashMap<String, Object>();
		// 创建一个客户端
		HttpClient client = new DefaultHttpClient();
		HttpRequestBase requestBase=null;
		//如果传入参数为空，则新建参数集合对象
		if(params==null)
			params=new ArrayList<NameValuePair>();
		//POST请求
		if(HttpMethod.POST.equals(requestMethod)){
			requestBase=getPostRequest(scheme,host,port,path,params);
		}else if(HttpMethod.GET.equals(requestMethod)){//get请求
			requestBase=getGetRequest(scheme,host,port,path,params);
		}else if(HttpMethod.PUT.equals(requestMethod)){//PUT请求
			requestBase=getPutRequest(scheme,host,port,path,params);
		}else if(HttpMethod.DELETE.equals(requestMethod)){//DELETE请求
			requestBase=getDeleteRequest(scheme,host,port,path);
		}else{
			retMap.put("flag", 2);
			retMap.put("mes", "传入的请求方式不对");
			return retMap;
		}
		//设置请求超时
		if(timeout!=-1){
			RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
			requestBase.setConfig(requestConfig);
		}
		try {
			// 执行请求
			HttpResponse response = client.execute(requestBase);
			//请求成功
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				// 获取返回内容
				if (response.getEntity() != null) {
					retMap.put("flag", 0);
					retMap.put("value", EntityUtils.toString(response.getEntity()));
				}else{
					retMap.put("flag", 1);
					retMap.put("mes", "没有返回信息");
				}
			}else{
				retMap.put("flag", 3);  
				retMap.put("statusCode", response.getStatusLine().getStatusCode());
				retMap.put("mes", "请求异常，响应状态为"+response.getStatusLine().getStatusCode());
			}
			// 关闭流
			EntityUtils.consume(response.getEntity());
		} catch (IOException e) {
			retMap.put("flag", 4);  
			retMap.put("mes", e.getMessage());
			e.printStackTrace();
		} finally {
			// 关闭连接
			client.getConnectionManager().shutdown();
		}
		return retMap;
	}
	
	/**
	 * 构建POST请求
	 * @param scheme 请求模式
	 * @param host 主机
	 * @param port 端口:-1标识没有端口
	 * @param path 资源地址
	 * @param params 查询参数
	 * @return
	 * @author song
	 * @time 2015-12-29 下午7:39:19
	 */
	private static HttpRequestBase getPostRequest(String scheme, String host, int port, String path, List<NameValuePair> params){
		String requestUrl = "";
		if (port == -1)
			requestUrl = scheme + "://" + host + path;
		else
			requestUrl = scheme + "://" + host + ":" + port + path;
		HttpPost post = new HttpPost(requestUrl); 
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(entity);  
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
		return post;
	}
	
	/**
	 * 构建GET请求
	 * @param scheme 请求模式，如 http
	 * @param host 主机
	 * @param port 端口:-1标识没有端口
	 * @param path 资源地址
	 * @param params 查询参数
	 * @return
	 * @author song
	 * @time 2015-12-29 下午7:39:14
	 */
	private static HttpRequestBase getGetRequest(String scheme, String host, int port, String path, List<NameValuePair> params){
		HttpGet get = new HttpGet();
		try {
			URI uri = URIUtils.createURI(scheme, host, port, path,  
			        URLEncodedUtils.format(params, "UTF-8"), null);
			get.setURI(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
		return get;
	}
	
	/**
	 * 构建PUT请求
	 * @param scheme 请求模式
	 * @param host 主机
	 * @param port 端口:-1标识没有端口
	 * @param path 资源地址
	 * @param params 查询参数
	 * @return
	 * @author song
	 * @time 2015-12-29 下午7:39:19
	 */
	private static HttpRequestBase getPutRequest(String scheme, String host, int port, String path, List<NameValuePair> params){
		String requestUrl = "";
		if (port == -1)
			requestUrl = scheme + "://" + host + path;
		else
			requestUrl = scheme + "://" + host + ":" + port + path;
		HttpPut put = new HttpPut(requestUrl); 
		put.setHeader("Accept-Encoding", "gzip, deflate");
		put.setHeader("Accept-Language", "zh-CN");
		put.setHeader("Accept", "application/json, application/xml, text/html, text/*, image/*, */*");
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(params, "UTF-8");
			put.setEntity(entity);  
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
		return put;
	}
	
	/**
	 * 构建DELETE请求
	 * @param scheme 请求模式
	 * @param host 主机
	 * @param port 端口:-1标识没有端口
	 * @param path 资源地址
	 * @return
	 * @author song
	 * @time 2015-12-29 下午7:39:19
	 */
	private static HttpRequestBase getDeleteRequest(String scheme, String host, int port, String path){
		String requestUrl = "";
		if (port == -1)
			requestUrl = scheme + "://" + host + path;
		else
			requestUrl = scheme + "://" + host + ":" + port + path;
		HttpDelete delete = new HttpDelete(requestUrl); 
		return delete;
	}
	
	/**
	 * 发送文件请求
	 * @param requestUrl 请求地址
	 * @param files 待上传文件数组
	 * @param params 上传信息的map对象
	 * @param timeout 超时 如果为-1则为默认值
	 * @return 通过map进行数据返回，其中flag标示：2-请求方式不对；0-标示成功(value)；1-无返回信息；3-请求异常，并附有HTTP请求状态statusCode;4-代码异常，并有异常信息
	 * @author song
	 * @time 2015-12-29 下午11:23:54
	 */
	public static Map<String,Object> sendMultipartForm(String requestUrl,File[] files,Map<String,String> params,int timeout){
		Map<String,Object>  retMap=new HashMap<String, Object>();
		try {
			URL url=new URL(requestUrl);
			return HttpClientUtils.sendMultipartForm(url.getProtocol(), url.getHost(), url.getPort(), url.getPath(),files, params,timeout);
		} catch (MalformedURLException e) {
			retMap.put("flag", "5");
			retMap.put("mes", "请求地址异常");
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 发送文件请求
	 * @param scheme 请求模式
	 * @param host 主机
	 * @param port 端口:-1标识没有端口
	 * @param path 资源地址
	 * @param files 待上传文件数组
	 * @param params 上传信息的map对象
	 * @param timeout 超时 如果为-1则为默认值
	 * @return 通过map进行数据返回，其中flag标示：2-请求方式不对；0-标示成功(value)；1-无返回信息；3-请求异常，并附有HTTP请求状态statusCode;4-代码异常，并有异常信息
	 * @author song
	 * @time 2015-12-29 下午11:23:54
	 */
	public static Map<String,Object> sendMultipartForm(String scheme, String host, int port, String path,File[] files,Map<String,String> params,int timeout){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String request = "";
			if (port == -1)
				request = scheme + "://" + host + path;
			else
				request = scheme + "://" + host + ":" + port + path;
			HttpPost httppost = new HttpPost(request);
			//设置请求超时
			if(timeout!=-1){
				RequestConfig requestConfig=RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
				httppost.setConfig(requestConfig);
			}
			
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
			// 设置编码
			multipartEntityBuilder.setCharset(CharsetUtils.get("UTF-8"));
			// 设置浏览器兼容模式,否则中文文件名乱码
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			// 设置带上传待上传的文件集合
			for (int i = 0; i < files.length; i++) {
				multipartEntityBuilder = multipartEntityBuilder.addPart("file" + i,
						new FileBody(files[i], ContentType.create("multipart/form-data", "UTF-8")));
			}
			// 遍历传入的参数集合
			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					multipartEntityBuilder = multipartEntityBuilder.addPart(entry.getKey(),
							new StringBody(entry.getValue(), ContentType.create("text/plain", "UTF-8")));
				}
			}

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			// System.out.println("executing request " +
			// httppost.getRequestLine());
			// 执行请求
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 请求成功
				// System.out.println(response.getStatusLine());
				if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
					// 获取返回内容
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						retMap.put("flag", 0);
						// String content= EntityUtils.toString(resEntity);
						// System.out.println(content);
						retMap.put("value", EntityUtils.toString(resEntity));
					} else {
						retMap.put("flag", 1);
						retMap.put("mes", "没有返回信息");
					}
				} else {
					retMap.put("flag", 3);
					retMap.put("statusCode", response.getStatusLine().getStatusCode());
					retMap.put("mes", "请求异常，响应状态为" + response.getStatusLine().getStatusCode());
				}
				// 关闭流
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				retMap.put("flag", 4);
				retMap.put("mes", e.getMessage());
				e.printStackTrace();
			} finally {
				response.close();
			}
		} catch (Exception e) {
			retMap.put("flag", 4);
			retMap.put("mes", e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				retMap.put("flag", 4);
				retMap.put("mes", e.getMessage());
				e.printStackTrace();
			}
		}
		return retMap;
	}
	
	
	/**
	 * 测试代码
	 * @param args
	 * @author song
	 * @time 2015-12-29 上午12:41:39
	 */
	public static void main(String[] args) {
		//post-test-不带参数
//		Map<String,Object> ret=HttpClientUtils2.sendRequest("http","localhost",8080,"CloudSea/synch/device/synchEquipmentType", "POST",new ArrayList<NameValuePair>());
//		System.out.println(ret.get("flag"));
		//post-test-带参数
//		List<NameValuePair> params = new ArrayList<NameValuePair>();  
//		params.add(new BasicNameValuePair("lastUpdateDateTime", "2015-9-29 12:12:12"));  
//		Map<String,Object> ret=HttpClientUtils2.sendRequest("http","localhost",8080,"CloudSea/synch/device/synchEquipmentType", "POST", params);
//		System.out.println(ret.get("flag"));
		//get-test-不带参数
//		String str=HttpClientUtils2.sendRequest("http","localhost",8080,"CloudSea/synch/device/synchEquipmentType", "GET",new ArrayList<NameValuePair>());
//		System.out.println(str);
		//get-test-带参数
//		String str;
//		List<NameValuePair> params = new ArrayList<NameValuePair>();  
//		params.add(new BasicNameValuePair("lastUpdateDateTime", "2015-9-29 12:12:12"));  
//		params.add(new BasicNameValuePair("REMARK", "呜呜+呜我=+")); 
//		str = HttpClientUtils2.sendRequest("http","localhost",8080,"CloudSea/synch/device/synchEquipmentType", "GET", params);
//		System.out.println(str);
//		Map<String,String> map=new HashMap<String,String>();	
//		map.put("remark", "呜呜+呜我=+");
//		Map<String,Object> ret = HttpClientUtils.sendMultipartForm("http","localhost",8080,"CloudSea/synch/upload/test/testUpload3", 
//					new File[]{new File("C:\\Users\\song\\Desktop\\problem.txt"),new File("C:\\Users\\song\\Desktop\\催款系统APP接口规范说明V0.1.doc")},map);
//		System.out.println(ret.get("flag"));
	}
}

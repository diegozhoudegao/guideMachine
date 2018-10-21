package com.cssiot.cssutil.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.cssiot.cssutil.common.exception.ResultException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public class OkHttpUtil{

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String get(String url, Map<String, String> queries, String loginName, String token, String clientType) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Entry<String, String>> iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("okhttp3 put error >> ex = {}", ExceptionUtils.getStackTrace(e));
            throw new ResultException(-2, "网络异常,请稍后重试!", token, loginName, clientType); 
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }
    /**
     * gethearderMap参数
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @param hearderMap 在请求头需要些的信息
     * @return
     * @author shinry.liu创建
     */
    public static String getHearder(String url, Map<String, String> queries,Map<String,String> hearderMap) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Entry<String, String>> iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .url(sb.toString())
                .build();
        Response response = null;
        OkHttpClient okHttpClient=null;
        try {
        	 okHttpClient=new OkHttpClient.Builder().addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						// TODO Auto-generated method stub
					    Builder builder=chain.request().newBuilder();
					    hearderMap.forEach((k,v)->{
					    	builder.addHeader(k,v);
					    });
					   Request request=builder.build();
						
						return chain.proceed(request);
					}
				}).build();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("okhttp3 put error >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        String responseBody = "";
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
        	log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }
    
    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String postIM(String url, Map<String, String> params) {
    	String responseBody = "";
    	FormBody.Builder builder = new FormBody.Builder();
    	//添加参数
    	if (params != null && params.keySet().size() > 0) {
    		for (String key : params.keySet()) {
    			builder.add(key, params.get(key));
    		}
    	}
    	String imAppKey =new String(Global.getConfig("imAppKey","wagon"));
		String imAppSecret =new String(Global.getConfig("imAppSecret","wagon"));
		String nonce = String.valueOf(Math.random() * 1000000);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		StringBuilder toSign = new StringBuilder(imAppSecret).append(nonce).append(timestamp);
		String sign = CodeUtil.hexSHA1(toSign.toString());
    	Request request = new Request.Builder()
    			.url(url)
    			.addHeader("AppKey", imAppKey)
    			.addHeader("Nonce", nonce)
    			.addHeader("CurTime", timestamp)
    			.addHeader("CheckSum", sign)
    			.post(builder.build())
    			.build();
    	Response response = null;
    	try {
    		OkHttpClient okHttpClient = new OkHttpClient();
    		response = okHttpClient.newCall(request).execute();
    		if (response.isSuccessful()) {
    			return response.body().string();
    		}
    	} catch (Exception e) {
    		log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
    		throw new ResultException(-2, "IM网络异常！", null, null, null); 
    	} finally {
    		if (response != null) {
    			response.close();
    		}
    	}
    	return responseBody;
    }

    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String getForHeader(String url, Map<String, String> queries) {
        String responseBody = "";
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Entry<String, String>> iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        Request request = new Request.Builder()
                .addHeader("key", "value")
                .url(sb.toString())
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
        	log.error("okhttp3 put error >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJsonParams(String url, String jsonParams, String loginName, String token, String clientType) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
        	log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
        	throw new ResultException(-2, "网络异常,请稍后重试!", token, loginName, clientType); 
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     * 参数三：请求回调
     */
    public static String postXmlParams(String url, String xml) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
        	log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }
    
    /**
     * Post请求发送xml数据....
     *@param ：请求Url
     *@param  头部map
     *@param  查询信息
     */
    public static String postSendHeardQueryParams(String url,Map <String,String>hearderMap,String jsonParams){
    	Map map=new HashMap<>();
    	String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = null;
        OkHttpClient okHttpClient=null;
        try {
       	 okHttpClient=new OkHttpClient.Builder().addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					// TODO Auto-generated method stub
				    Builder builder=chain.request().newBuilder();
				    hearderMap.forEach((k,v)->{
				    	builder.addHeader(k,v);
				    });
				   Request request=builder.build();
					
					return chain.proceed(request);
				}
			}).build();
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
        	log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
   	
   }
    
    /**
	 * http请求 put方法价文件路径上传
	 * @param url路径
	 * @param heardMap hearder头发送map
	 * @param jsonParams 头部参数
	 * @param file 文件上传
	 * @author 
	 *    2018-06-20 shinry创建
	 */
   
   public static String PutSendHeardQueryParams(String url,Map <String,String>hearderMap,String jsonParams,File file){
   	Map map=new HashMap<>();
   	String responseBody = "";
     RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown; charset=utf-8"),file);
      
   	Builder builer = new Request.Builder()
               .url(url)
               .put(requestBody);
       Request request=builer.build();
       Response response = null;
       OkHttpClient okHttpClient=null;
       try {
      	 okHttpClient=new OkHttpClient.Builder().addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					// TODO Auto-generated method stub
				    Builder builder=chain.request().newBuilder();
				    hearderMap.forEach((k,v)->{
				    	builder.addHeader(k,v);
				    });
				   Request request=builder.build();
					return chain.proceed(request);
				}
			}).build();
           response = okHttpClient.newCall(request).execute();
           if (response.isSuccessful()) {
               return response.body().string();
           }
       } catch (Exception e) {
       	log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
       } finally {
           if (response != null) {
               response.close();
           }
       }
       return responseBody;
  	
  }
   /**
	 * http请求
	 * @param url路径
	 * @param heardMap hearder头发送map
	 * @param jsonParams body中的单数
	 * @param mothed post,put ,delete
	 * @param urlHearderMap url参数中带的map
	 * @author 
	 *    2018-06-20 shinry创建
	 */
   public static String PutSendHeardQueryParams(String url,Map <String,String>hearderMap,String jsonParams,String mothed,Map urlHearderMap){
	   	Map map=new HashMap<>();
	   	String responseBody = "";
	   	StringBuffer sb = new StringBuffer(url);
        if (urlHearderMap != null && urlHearderMap.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator<Entry<String, String>> iterator = urlHearderMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
	       RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
	       Builder builer = new Request.Builder()
	               .url(sb.toString());
	       switch(mothed){
	       case "post":builer.post(requestBody);
	        break;
	       case "put": builer.put(requestBody);
	        break;
	       case "delete":builer.delete(requestBody);
	       break;
	            
	       }
	       Request request=builer.build();
	       Response response = null;
	       OkHttpClient okHttpClient=null;
	       try {
	      	 okHttpClient=new OkHttpClient.Builder().addInterceptor(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						// TODO Auto-generated method stub
					    Builder builder=chain.request().newBuilder();
					    hearderMap.forEach((k,v)->{
					    	builder.addHeader(k,v);
					    });
					   Request request=builder.build();
						
						return chain.proceed(request);
					}
				}).build();
	           response = okHttpClient.newCall(request).execute();
	           if (response.isSuccessful()) {
	               return response.body().string();
	           }
	       } catch (Exception e) {
	       	log.error("okhttp3 post error >> ex = {}", ExceptionUtils.getStackTrace(e));
	       } finally {
	           if (response != null) {
	               response.close();
	           }
	       }
	       return responseBody;
	  	
	  }
   
}
package com.cssiot.cssutil.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * http 公共服务
 *
 * @author RongCloud
 */
public class IMHttpUtil {

	private static SSLContext sslCtx = null;
	
	static {

		try {
			sslCtx = SSLContext.getInstance("SSL","SunJSSE");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			sslCtx.init(null, new TrustManager[] { tm }, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				// TODO Auto-generated method stub
				return true;
			}

		});

		HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx.getSocketFactory());

	}

	// 设置body体
	public static void setBodyParameter(StringBuilder sb, HttpURLConnection conn) throws IOException {
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.writeBytes(sb.toString());
		out.flush();
		out.close();
	}

	public static HttpURLConnection CreateGetHttpConnection(String uri) throws MalformedURLException, IOException {
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(30000);
		conn.setRequestMethod("GET");
		return conn;
	}

	public static void setBodyParameter(String str, HttpURLConnection conn) throws IOException {
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(str.getBytes("utf-8"));
		out.flush();
		out.close();
	}

	public static HttpURLConnection CreatePostHttpConnection(String hostType, String uri, String contentType) 
			throws MalformedURLException, IOException, ProtocolException {
		uri = hostType + uri;
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		
		conn.setRequestProperty("Content-Type", contentType);
		return conn;
	}
	
	public static HttpURLConnection CreateIMPostHttpConnection(String hostType, String uri, String contentType) 
			throws MalformedURLException, IOException, ProtocolException {
		uri = hostType + uri;
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
		
		String imAppKey =new String(Global.getConfig("imAppKey","wagon"));
		String imAppSecret =new String(Global.getConfig("imAppSecret","wagon"));
		String nonce = String.valueOf(Math.random() * 1000000);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		StringBuilder toSign = new StringBuilder(imAppSecret).append(nonce).append(timestamp);
		String sign = CodeUtil.hexSHA1(toSign.toString());
		conn.setRequestProperty("AppKey", imAppKey);
		conn.setRequestProperty("Nonce", nonce);
		conn.setRequestProperty("CurTime", timestamp);
		conn.setRequestProperty("CheckSum", sign);
		conn.setRequestProperty("Content-Type", contentType);
		return conn;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	public static String returnResult(HttpURLConnection conn) throws Exception, IOException {
		InputStream input = null;
		if (conn.getResponseCode() == 200) {
			input = conn.getInputStream();
		} else {
			input = conn.getErrorStream();
		}
		String result = new String(readInputStream(input), "UTF-8");
		return result;
	}

}

package com.cssiot.cssutil.common.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class SendSms {
	public static void sendsms() throws Exception {
		String info = null;
		try{
			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod("http://js.ums86.com:8899/sms/Api/Send.do");//
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
			post.addParameter("SpCode", "201971");
			post.addParameter("LoginName", "js_sg");
			post.addParameter("Password", "sg1234");
			post.addParameter("MessageContent", "您的验证码为999999");
			post.addParameter("UserNumber", "13626191302");
			post.addParameter("SerialNumber", System.currentTimeMillis()+"");
			post.addParameter("ScheduleTime", "");
			post.addParameter("ExtendAccessNum", "");
			post.addParameter("f", "1");
			httpclient.executeMethod(post);
			info = new String(post.getResponseBody(),"gbk");
			System.out.println(info);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getByteString( byte[] buff_out )
	{
		StringBuffer strBuf = new StringBuffer(buff_out.length * 3);
		strBuf.append("Length[");
		strBuf.append(buff_out.length);
		strBuf.append("];Content[");
		for ( int i = 0 ; i < buff_out.length ; ++i ) {
			int l = buff_out[i] & 0x0F;
			int h = (buff_out[i] & 0xF0) >> 4;

			char ll = (char) (l > 9 ? 'a' + l - 10 : '0' + l);
			char hh = (char) (h > 9 ? 'a' + h - 10 : '0' + h);

			strBuf.append(hh);
			strBuf.append(ll);
			strBuf.append(" ");
		}
		strBuf.append("]");
		return strBuf.toString().toUpperCase();
	}

}

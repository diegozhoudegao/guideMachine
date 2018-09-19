package com.cssiot.cssutil.common.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
* @ClassName: Nextmonth 
* @Description: 下月份处理
* @author 作者 jacking
* @date 2016-5-20 下午3:48:40
 */
public class Nextmonth {
	public static String getnextmonthtime(String date) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = sdf.parse(date);
		sDate.setMonth(sDate.getMonth()+1); 
	    String time=sdf.format(sDate);
	    
		return time;
	}
  public static String getnextmonthtime(String date,int i) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date sDate = sdf.parse(date);
		sDate.setMonth(sDate.getMonth()+i); 
	    String time=sdf.format(sDate);
	    
		return time;
	}
}

package com.cssiot.cssutil.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateAirth {
 public static final String yyyyMMDD_8="yyyyMMdd";
 public static final String yyyyMMDD_10="yyyy-MM-dd";
 public static final String yyyyMMDDHHMM_16="yyyy-MM-dd HH:mm";
 public static final String yyyyMMDDHHMMSS_19="yyyy-MM-dd HH;mm:ss";
 public static final String DEFAULT_PATERN=yyyyMMDD_10;
 /**
  * 获取指定日期（年月日时分秒）的加减日期
  * @param date
  * @param years
  * @param months
  * @param days
  * @param hours
  * @param minutes
  * @param seconds
  * @return
  */
 
 
 public static Date getAirthMyDate(Date date,int years,int months,int days,int hours,int minutes,int seconds){
	 Date willDate=new Date();
	 if(null!=date&&!"".equals(date)){
		 try {
			willDate=date;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 Calendar cal=Calendar.getInstance();
	 cal.setTime(willDate);
	 cal.add(cal.YEAR,years);
	 cal.add(cal.MONTH,months);
	 cal.add(cal.DAY_OF_MONTH,days);
	 cal.add(cal.HOUR_OF_DAY,hours);
	 cal.add(cal.MINUTE,minutes);
	 cal.add(cal.SECOND,seconds);
	 return cal.getTime();
 }
}

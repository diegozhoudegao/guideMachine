package com.cssiot.cssutil.common.utils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeCount {
	// private static Date date1;
	// private static Date date2;
	private static int days;

	// 根据两个日期计算之间的差值
	public static int getDValue(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		long day1 = calendar.getTimeInMillis();
		calendar.setTime(date2);
		long day2 = calendar.getTimeInMillis();
		long between = 0;
		if (day2 >= day1) {
			between = (day2 - day1) / (1000 * 3600 * 24);
		} else {
			between = (day1 - day2) / (1000 * 3600 * 24);
		}
		days = Integer.parseInt(String.valueOf(between));
		return days;
	}
	
	// 根据两个日期计算之间的分钟差值
	public static int getMValue(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		long day1 = calendar.getTimeInMillis();
		calendar.setTime(date2);
		long day2 = calendar.getTimeInMillis();
		long between = 0;
		if (day2 >= day1) {
			between = (day2 - day1) / (1000 * 60);
		} else {
			between = (day1 - day2) / (1000 * 60);
		}
		days = Integer.parseInt(String.valueOf(between));
		return days;
	}

	// 假如用户输入的是差值，根据当前输入的差值和上一个阶段的时间计算当前阶段的时间
	public static Date getDate(Date date1, int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(date1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}

	// 假如用户输入的是差值，根据当前输入的差值和上一个阶段的时间计算当前阶段的时间
	public static Date getDays(Date date1, int years) {
		Calendar now = Calendar.getInstance();
		now.setTime(date1);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + years);
		return now.getTime();
	}

	// 根据两个日期计算之间的差值
	public static int getOneDValue(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		long day1 = calendar.getTimeInMillis();
		calendar.setTime(date2);
		long day2 = calendar.getTimeInMillis();
		long between = 0;
		between = (day2 - day1) / (1000 * 3600 * 24);
		days = Integer.parseInt(String.valueOf(between));
		return days;
	}
	
	//根据传过来的时间单位和时间差值，与上一个阶段的时间计算当前阶段的时间
	public static Date getTime(Date date1,String value,String unit){
		Calendar now = Calendar.getInstance();
		now.setTime(date1);
		String[] str=value.split("\\.");
		if(null!=str && str.length>0){
			double d=Double.valueOf(value);
			double d2=Double.valueOf(str[0]);
			double d3=(d-d2)*365;
			int temp=new Double(d3).intValue();
			if(unit.equals("天")){
				now.set(Calendar.DATE, now.get(Calendar.DATE) + (int)d2);
			}else if(unit.equals("年")){
				now.set(Calendar.YEAR, now.get(Calendar.YEAR) + (int)d2);
				now.set(Calendar.DATE, now.get(Calendar.DATE) + temp);
			}
		}
		return now.getTime();
	}
	
	//若时间差为年的时候将年数换算为天数
	public static String turnDvalue(String value,String unit){
		String tempDvalue="";
		if(unit.equals("天")){
			tempDvalue=value;
		}else if(unit.equals("年")){
			double d=Double.valueOf(value);
			int temp=(int)(d)*365;
			tempDvalue=temp+"";
		}
		return tempDvalue;
	}
	
	//计算付款时间差，将天换算为年，value:解冻与冻结之间的时间差，dvalue:原来的时间差
	public static String turnDayToYear(String value,String dvalue,String unit){
		String temp="";
		if(unit.equals("年")){
			double v=Double.valueOf(value);
			double d=Double.valueOf(dvalue);
			DecimalFormat df=new DecimalFormat("0.00#");
			temp=df.format(v/365+d).toString();
		}else{
			
			temp=String.valueOf(Integer.parseInt(value)+Integer.parseInt(dvalue));
		}
		return temp;
	}

	// 假如用户输入的是差值，根据当前输入的差值和上一个阶段的时间计算当前阶段的时间
	public static Date getBeforeDate(Date date1, int days) {
		Calendar now = Calendar.getInstance();
		if(null==date1){
			return null;
		}
		now.setTime(date1);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - days);
		return now.getTime();
	}
	
	// 假如用户输入的是差值，根据当前输入的差值和上一个阶段的时间计算当前阶段的时间
	public static Date getBeforeDays(Date date1, int years) {
		Calendar now = Calendar.getInstance();
		if(null==date1){
			return null;
		}
		now.setTime(date1);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) - years);
		return now.getTime();
	}
	
//	@Test
//	public void test(){
//		Calendar now = Calendar.getInstance();
//		now.setTime(new Date());
//		String unit="年";
//		String value="1";
//		String[] str=value.split("\\.");
//		if(null!=str && str.length>0){
//			double d=Double.valueOf(value);
//			double d2=Double.valueOf(str[0]);
//			double d3=(d-d2)*365;
//			//String tempD3=String.valueOf(d3);
//			int temp=new Double(d3).intValue();
//			if(unit.equals("天")){
//				now.set(Calendar.DATE, now.get(Calendar.DATE) + (int)d2);
//			}else if(unit.equals("年")){
//				now.set(Calendar.YEAR, now.get(Calendar.YEAR) + (int)d2);
//				now.set(Calendar.DATE, now.get(Calendar.DATE) + temp);
//			}
//		}
//		System.out.print(now.getTime());
//	}
}

package com.cssiot.cssutil.common.utils;


import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cssiot.cssutil.common.enums.DateFormatEnum;

/**
* @ClassName: Times 
* @Description: 处理时间相关格式问题
* @author 作者 jacking
* @date 2016-5-20 下午3:47:29
 */
public class Times {
	/**
	 * yyyy
	 */
	public static final String YYYY_4="yyyy";
	
	/**
	 * yyyyMMdd
	 */
	public static final String YYYYMMDD_8 = "yyyyMMdd";
	/**
	 * 只显示年月，用于期间，6位显示
     * yyyyMM
     */
    public static final String YYYYMM_6 = "yyyyMM";
    /**
     * yyyy-MM-dd
     */
    public static final String YYYYMMDD_10 = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String YYYYMMDDHHMM_16 = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYYMMDDHHMMSS_19 = "yyyy-MM-dd HH:mm:ss";
	
	/**获取当前日期字符串
	 * @return yyyy-MM-dd HH:mm:SS
	 * */
	public static String getTimes(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//用来获取当前时间
		String str = sdf.format(new Date());
		return str;
	}
	
	/**获取当前日期字符串
	 * @return yyyy-MM-dd
	 * */
	public static String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//用来获取当前时间
		String str = sdf.format(new Date());
		return str; 
	}
	
	/**比较字符串日期大小先后顺序
	 * @param DATE1  yyyy-MM-dd HH:mm:ss
	 * @param DATE2  yyyy-MM-dd HH:mm:ss
	 * @return -1:第一个大，1：相等，0：第二大
	 */
	public static int compare_date(String DATE1, String DATE2) {
		int i=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
            	i=-1;
            } else if (dt1.getTime() < dt2.getTime()) {
                i=0;
            }else if (dt1.getTime()==dt2.getTime()) {
            	i=1;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
		return i;
      }
	/**比较字符串日期大小先后顺序
	 * @param DATE1  yyyy-MM-dd HH:mm:ss
	 * @param DATE2  yyyy-MM-dd HH:mm:ss
	 * @return -1:第一个大，1：相等，0：第二大
	 */
	public static int compare_date(Date DATE1, Date DATE2) {
		int i=0;
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (DATE1.getTime() > DATE2.getTime()) {
				i=-1;
			} else if (DATE1.getTime() < DATE2.getTime()) {
				i=0;
			}else if (DATE1.getTime()==DATE2.getTime()) {
				i=1;
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return i;
	}
	
	
	/**比较字符串日期大小先后顺序
	 * @param DATE1  yyyy-MM-dd
	 * @param DATE2 yyyy-MM-dd
	 * @return -1:第一个大，1：相等，0：第二大
	 */
	public static int compare_dateymd(String DATE1, String DATE2) {
		int i=0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
            	i=-1;
            } else if (dt1.getTime() < dt2.getTime()) {
                i=0;
            }else if (dt1.getTime()==dt2.getTime()) {
            	i=1;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
		return i;
      }
	
	
	public static String getLastDayOfMonth(int year, int month) {
		  Calendar cal = Calendar.getInstance();
		  cal.set(Calendar.YEAR, year);
		  cal.set(Calendar.MONTH, month-1);
		  cal.set(Calendar.DAY_OF_MONTH, 1);
		  int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		  cal.set(Calendar.DAY_OF_MONTH, value);
		  return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime())+" 17:30:00";
		 }
		
		public static String nextfirst(String s,int n){
			String r=((Integer.parseInt(s.substring(5,6)))==0)?s.substring(6,7):s.substring(5,7);
			int year=Integer.parseInt(s.substring(0,4));
			int mon=Integer.parseInt(r);
			if((mon+n)>12){
				year=year+((mon+n)/12);
				if((mon+n)%12!=0){
					mon=(mon+n)%12;
				}
			}else{
				mon=mon+n;
			}
			return year+"-"+((mon<10)?"0"+mon:mon)+"-"+"01 09:00:00";
		}
		
	// 获取当前月最后一天
		public static Date lastDayOfMonth(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.roll(Calendar.DAY_OF_MONTH, -1);
			return cal.getTime();
		}
		//获取当前月第一天0点0分1秒
		public static Date firstDayOfMonth(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY,0);
			return cal.getTime();
		}
		
	public static String getnumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String str = sdf.format(new Date());
		return str;
	}
	
	public static String getserialno(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String str = sdf.format(new Date());
		return str;
	}
	
	// 根据两个日期A、B计算AB之间的天数
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Math.abs(quot);
	}
	// 根据两个日期A、B计算AB之间小时
	public static int getABhour(Date time1, Date time2) {
		long quot = 0;
		try {
			quot = time1.getTime() - time2.getTime();
			quot = quot / 1000 / 60 / 60 ;
		} catch (Exception e) {
			e.printStackTrace();
			return new Long(quot).intValue();
		}
		return  new Long(Math.abs(quot)).intValue();
	}
	
	/**获取i天后的日期
	 * @param date
	 * @param i
	 * @return
	 * @throws ParseException
	 */
	public static String getday(String date, int i) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sDate = sdf.parse(date);
		String time = "";
		sDate.setDate(sDate.getDate() + i);
		time = sdf.format(sDate);
		return time;
	}
	
	/**
	 * 统计连个日期见的天数
	 * @param smdate 日期1
	 * @param bdate 日期2
	 * @return int  相隔的天数
	 * */
	 public static int daysBetween(String smdate,String bdate) throws ParseException{  
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(sdf.parse(smdate));    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(sdf.parse(bdate));    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));     
	    }
	 
	
	
	/**
	 * 获得当前年月
	 * */
	public static String getyyyymmddhh(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(new Date());
		return str;
	}
	
	
	public static String getorderID(String cid){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");//生成订单编号，cid是客户ID
		String str = sdf.format(new Date());
		return str+cid;
	}
	
	public static int[] convertStr(String str) {
		str=str.substring(0,10);
		String[] strArr = str.split("-");
		int[] result = new int[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			result[i] = Integer.parseInt(strArr[i]);
		}
		return result;
	}
	public static int diffMonth(String startDate, String endDate) {
		int[] date1 = convertStr(startDate);
		int[] date2 = convertStr(endDate);
		int result = 0; // 年份相减*12
		result += (date2[0] - date1[0]) * 12; // 月份相减
		result += (date2[1] - date1[1]); // 天数相差不足1个月，扣除1个月
		result += (date2[2] > date1[2] ? 0 : -1);
		return Math.abs(result);
	}
	public static double getQuot1(String time1, String time2) {
		double quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime() - date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ArithSolve.getdouble(quot);
	}
	
	public static double getDiffMin(String time1, String time2) {
		double quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date2.getTime() - date1.getTime();
			quot = quot / 1000 / 60;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ArithSolve.getdouble(quot);
	}

	public static boolean isWeekOfSaturday(String bDate) throws ParseException{
		
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
		Date bdate = format1.parse(bDate); 
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(bdate);
	    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY)
	    	return true;
	    return false;
	}
	
	
	public static boolean isWeekOfSunday(String bDate) throws ParseException{
		
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); 
		Date bdate = format1.parse(bDate); 
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(bdate);
	    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
	    	return true;
	    return false;
	}
	
	
	
	
	public static boolean deleteFile(String sPath) {   
	    boolean   flag = false;   
		File    file = new File(sPath);   
		  // 路径为文件且不为空则进行删除   
		 if (file.isFile() && file.exists()) {   
		       file.delete();   
		       flag = true;   
		   }   
		return flag;   
	}
	
	/**
	 * 获得day天前的时间
	 * @param date
	 * @param day
	 * @return
	 */
	public static String[] getBefore(Date date,int day){
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
		 Calendar now = Calendar.getInstance();  
	      now.setTime(date);  
	      now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
	      sdf.format(date);
	      String[] dates=new String[2];
	      dates[0]=sdf.format(now.getTime());
	      dates[1]=sdf.format(date);
	     return dates;
	}
	
	/**
	 * 计算n个月之后的日期
	 * */
		public static String getEtime(String dtime, int n) throws Exception {// 计算n个月之后的今天
			String ctime = "";
			SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (Integer.parseInt(dtime.substring(8, 10)) == 31
					|| Integer.parseInt(dtime.substring(8, 10)) == 30
					|| Integer.parseInt(dtime.substring(8, 10)) == 29) {// 取款日期是31或30号
				String time1 = dtime.subSequence(5, 7).toString();//
				int montha = Integer.parseInt(time1) + n;
				if (montha == 2) {// 如果是2月(2月最后一天还)
					String time2 = dtime.substring(0, 5) + "02-01";
					ctime = sdfFormat
							.format(lastDayOfMonth(sdfFormat.parse(time2)));
				} else if (Integer.parseInt(dtime.substring(8, 10)) == 31) {// 最后一天
					String time2 = dtime.substring(0, 5)
							+ (montha > 10 ? montha + "" : "0" + montha) + "-01";
					ctime = sdfFormat
							.format(lastDayOfMonth(sdfFormat.parse(time2)));
				} else {// 剩下的是30
					ctime = Nextmonth.getnextmonthtime(dtime, n);// n个月后未提前的应还日
				}
			} else {// 取款日期是其他日期
				ctime = Nextmonth.getnextmonthtime(dtime, n);// n个月后未提前的应还日
			}
			return ctime;
		}
		/**
		 * 计算n个月之后的今天
		 * @param dtime
		 * @param n
		 * @return
		 */
		public static Date getEtime(Date dtime, int n){// 计算n个月之后的今天
			Calendar now = Calendar.getInstance();  
			now.setTime(dtime);  
			now.add(Calendar.MONTH, n);
			return now.getTime();
		}

	
	/** 获取当前月末日期*/
	public static String getLastDay(){ 
	Calendar calendar = Calendar.getInstance(); 
	calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)); 
	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)); 
	int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
	return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+endday;
	}
	
	
	/**获得本月的最后一天*/
	public static String getlastday() {
		Calendar cal = Calendar.getInstance();
//		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-01");
		//cal.add(Calendar.DAY_OF_MONTH, -1);
		int year = cal.get(Calendar.YEAR);// 当前年
		int month = cal.get(Calendar.MONTH) + 1;// 当前月
		cal.set(Calendar.YEAR, year); // 2010年
		String m="";
		if(month>9){
			m=month+"";
		}
		else{
			m="0"+month;
		}
		int day=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return year+"-"+m+"-"+day;
	}
	
	
		/** 获得前n个月的第一天
		 * @param n
		 * @return
		 */
		public static String gettwomonthago(int n) {
			// 获得本月1号
			String dtime = "";
			Calendar cal = Calendar.getInstance();
//			SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-01");
			int year = cal.get(Calendar.YEAR);// 当前年
			int month = cal.get(Calendar.MONTH) + 1;// 当前月
			String m = "";
			if (month - n > 0) {
				if (month - n > 9) {
					m = month - n + "";
				} else {
					m = "0" + (month - n);
				}
				dtime = year + "-" + m + "-01";
			} else {
				if (month - n + 12 > 9) {
					m = (month - n + 12) + "";
				} else {
					m = "0" + (month - n + 12);
				}
				dtime = year - 1 + "-" + m + "-01";
			}
			return dtime;
		}
		
		
		/**计算前指定日期的前n天时间
		 * @param daytime 时间，例：2013-01-03
		 * @param temp 提前的天数
		 * @return string 日期:2013-01-02
		 * */
		public static String getBeforeday(String daytime,int temp){
			   Calendar c = Calendar.getInstance();
		        Date date = new Date();
		        try {
		            date = new SimpleDateFormat("yy-MM-dd").parse(daytime);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        c.setTime(date);
		        int day = c.get(Calendar.DATE);
		        c.set(Calendar.DATE, day -temp);

		        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
		                .getTime());
		        return dayBefore;
		}
		
		
		/**获得当前时间带时分秒，格式：yyyy年MM月dd日 HH时mm分ss秒
		 * @return
		 */
		public static String getdayZh(){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
			return sdf.format(new Date());
		} 

		
		
		/**获取i天后的日期 ymd
		 * @param date
		 * @param i
		 * @return
		 * @throws ParseException
		 */
		public static String getdayymd(String date, int i) throws ParseException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date sDate = sdf.parse(date);
			String time = "";
			sDate.setDate(sDate.getDate() + i);
			time = sdf.format(sDate);
			return time;
		}
		
		
		/**
		 * 返回 传入 精确到天的日期字符串 的当天的0点的精确到秒的字符串
		 */
		public static String start(String date){
			return date+" 00:00:00";
		}
		/**
		 * 返回 传入 精确到天的日期字符串 的当天的最后一秒的精确到秒的字符串
		 */
		public static String end(String date){
			return date+" 23:59:59";
		}
		
		/**字符串日期转date类型日期
		 * return 传入字符串("yyyy-MM-dd HH:mm:ss")的date日期
		 */
		public static Date getStringToDate(String date){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date2 = null;
			try {
				date2 = df.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date2;
		}
		/**字符串日期转date类型日期
		 * return 传入字符串自定义SimpleDateFormat的date日期
		 * @param date String日期
		 * @param df  SimpleDateFormat
		 * @return
		 */
		public static Date getStringToDate(String date,SimpleDateFormat df){
			Date date2 = null;
			try {
				if(!ChkUtil.isEmpty(date)){
					
					date2 = df.parse(date);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date2;
		}
		/**
		 * date 转string 日期
		 * @param date
		 * @param df
		 * @return
		 */
		public static String getDateToString(Date date,SimpleDateFormat df){
			String date2 = "";
			try {
				date2 = df.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date2;
		}
		/**字符串日期转date类型日期
		 * return 传入字符串("yyyy-MM-dd HH:mm:ss.SSS")的date日期
		 */
		public static Date getStringToDateSSS(String date){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date2 = null;
			try {
				date2 = df.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date2;
		}
		/**字符串日期转date类型日期
		 * return 传入字符串("yyyyMMddHHmmssSSS")的date日期
		 */
		public static Date getStringToDateSSS2(String date){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			Date date2 = null;
			try {
				date2 = df.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date2;
		}

		/**date类型日期转字符串日期
		 * return 传入date日期返回字符串("yyyy-MM-dd HH:mm:ss")
		 */
		public static String getDateToString(Date date){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String  strDate =null;
			try {
				strDate = df.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return strDate;
		}

		/**
		 * yyyy-MM-dd HH:mm:ss:SSS
		 * @param date
		 * @return
		 */
		public static String getDateToStringSSS(Date date){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			String  strDate =null;
			try {
				strDate = df.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return strDate;
		}
		/**
		 * yyyyMMddHHmmssSSS
		 * @param date
		 * @return
		 */
		public static String getDateToStringHHmmssSSS(Date date){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String  strDate =null;
			try {
				strDate = df.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return strDate;
		}
		
		public static void main(String[] args) throws ParseException{
			Date date2 = null;
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			try {
				date2 = df.parse("");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*String date="2016-01-05 05:20:22";
			Date willDate=Times.getStringToDate(date);
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(willDate);
			  cal.add(cal.YEAR, -1);
			  cal.set(cal.MONTH, 0);
			  cal.set(cal.DAY_OF_MONTH, 0);
			String d=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cal.getTime());*/
//			System.out.println(d);
		}
		public static int getdaysOfYear(int year){
			int days = 365; 
			if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) { 
				days = 366; 
				} 
			return days;
		}
		public static java.sql.Timestamp getSqlDate(String date) throws ParseException{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			return new java.sql.Timestamp(sdf.parse(date).getTime()) ;
		}
		public static String getyyyyMMdd(Date date){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
		public static String getyyyyMMddhhmmss(Date date){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return sdf.format(date);
		}
		
		/**
		 * 获得n天之后的日期
		 * */
		public static String getDatedays(String date,int day){			
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			  Calendar c = Calendar.getInstance(); 
			  Date dt;
			try {
				dt = sdf.parse(date);
				  c.setTimeInMillis(dt.getTime());
			        c.add(Calendar.DAY_OF_MONTH, day); 
			        Date dttemp= new Date(c.getTimeInMillis());
			        return sdf.format(dttemp);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		
		}
		public static int comparetwotime(String DATE1, String DATE2) {
			int i=0;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        try {
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	            	i=-1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                i=0;
	            }else if (dt1.getTime()==dt2.getTime()) {
	            	i=1;
	            }

	        } catch (Exception exception) {
	            exception.printStackTrace();
	        }
			return i;
	      }
		public static int number(String now,String time) throws Exception{
			int i=-1;
			while(comparetwotime(now,time)!=-1){
				i++;
				now=getEtime(now,1);
			};
			return i;
		}
		
		/**
		 * 输入日期，根据格式补全string
		 * @param timeString
		 * @param formatType
		 *  FORMAT0("0","yyyy-MM-dd"),
			FORMAT1("1","yyyy-MM-dd HH:mm:ss"),
			FORMAT2("2","yyyy"),
			FORMAT3("3","yyyy-MM"),
			FORMAT4("4","yyyy-MM-dd HH:mm"),
		 *	@return
		 * @throws ParseException 
		 * @author 
		 * 	2018-08-08 Diego.zhou 新建
		 */
		public static String formatString(String time,String formatType) throws ParseException {
	    	if(formatType.equals("0")) {//yyyy-MM-dd
	    		String[] result = time.split("-");
	    		if(null == result || result.length<=0) {
	    			System.out.println("日期格式错误！");
	    			return null;
	    		}
	    		if(result.length == 1) {//2018
	    			time+="-01-01";
	    		}else if(result.length == 2) {//2018-09
	    			time+="-01";
	    		}else if(result.length == 3) {
	    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    			Date parseDate = sdf.parse(time);
	    			time = sdf.format(parseDate);
	    		}
	    		System.out.println(time);
	    	}
	    	if(formatType.equals("1")) {//yyyy-MM-dd HH:mm:ss
	    		String[] result = time.split("-");
	    		if(null == result || result.length<=0) {
	    			System.out.println("日期格式错误！");
	    			return null;
	    		}
	    		if(result.length == 1) {//2018
	    			time+="-01-01 00:00:00";
	    		}else if(result.length == 2) {//2018-09
	    			time+="-01 00:00:00";
	    		}else if(result.length == 3) {//2018-03-04 12:00:00
	    			//判断最后一位
	    			String last = result[2];
	    			if(last.replaceAll(" ", "").length()==2) {
	    				time+=" 00:00:00";
	    			}else if(last.replaceAll(" ", "").length()==4) {
	    				time+=":00:00";
	    			}else if(last.replaceAll(" ", "").length()==7) {
	    				time+=":00";
	    			}
	    		}
	    		System.out.println("格式化后的日期为:"+time);
	    	}
	    	if(formatType.equals("2")) {//yyyy
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date parseDate = sdf.parse(time);
				time = sdf.format(parseDate);
				System.out.println(time);
	    	}
	    	if(formatType.equals("3")) {//yyyy-MM
	    		String[] result = time.split("-");
	    		if(null == result || result.length<=0) {
	    			System.out.println("日期格式错误！");
	    			return null;
	    		}
	    		if(result.length == 1) {//2018
	    			time+="-01";
	    		}else {
	    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    			Date parseDate = sdf.parse(time);
	    			time = sdf.format(parseDate);
	    		}
	    		System.out.println(time);
	    	}
	    	if(formatType.equals("4")) {//yyyy-MM-dd HH:mm
	    		String[] result = time.split("-");
	    		if(null == result || result.length<=0) {
	    			System.out.println("日期格式错误！");
	    			return null;
	    		}
	    		if(result.length == 1) {//2018
	    			time+="-01-01 00:00";
	    		}else if(result.length == 2) {//2018-09
	    			time+="-01 00:00";
	    		}else if(result.length == 3) {//2018-03-04 12:00:00
	    			//判断最后一位
	    			String last = result[2];
	    			if(last.replaceAll(" ", "").length()==2) {
	    				time+=" 00:00";
	    			}else if(last.replaceAll(" ", "").length()==4) {
	    				time+=":00";
	    			}else {
	    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        			Date parseDate = sdf.parse(time);
	        			time = sdf.format(parseDate);
	    			}
	    		}
	    		System.out.println("格式化后的日期为:"+time);
	    	}
			return time;
		}
		
		/**
		 * 根据时间字符串返回该时间的格式
		 * @param time
		 * @return
		 * @author
		 * 	2018-08-20 Diego.zhou 新建
		 */
		public static String doGetTimeFormat(String time) {
			String format = "";
			if(ChkUtil.isEmptyAllObject(time)) {
				return format;
			}
			int length = time.length();
			if(length == 4) {//yyyy
				return DateFormatEnum.FORMAT2.getMessage();
			}
			if(length == 7) {//yyyy-MM
				return DateFormatEnum.FORMAT3.getMessage();
			}
			if(length == 10) {//yyyy-MM-dd
				return DateFormatEnum.FORMAT0.getMessage();
			}
			if(length == 16) {//yyyy-MM-dd HH:mm
				return DateFormatEnum.FORMAT4.getMessage();
			}
			if(length == 19) {//yyyy-MM-dd HH:mm:ss
				return DateFormatEnum.FORMAT1.getMessage();
			}
			return format;
		}
		
		/**
		 * 根据当前时间，获取指定期限后的日期
		 * @param time
		 * @param type
		 * @param dValue
		 * @return
		 * @author 
		 * 	2018-08-09 Diego.zhou 新建
		 */
		public static long doGetAfterYear(long time,String type,int dValue) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			if(type.equals("y")) {//'y','M','d','H','m','s','q','w'(分别对应年、月、日、时、分、秒、季度(3个月)、周数）
				cal.add(Calendar.YEAR, dValue);
			}else if(type.equals("M")) {//月
				cal.add(Calendar.MONTH, dValue);
			}else if(type.equals("d")) {//天
				cal.add(Calendar.DATE, dValue);
			}else if(type.equals("H")) {//时
				cal.add(Calendar.HOUR, dValue);
			}else if(type.equals("m")) {//分
				cal.add(Calendar.MINUTE, dValue);
			}else if(type.equals("s")) {//秒
				cal.add(Calendar.SECOND, dValue);
			}else if(type.equals("q")) {//季度
				cal.add(Calendar.MONTH, dValue*3);
			}else if(type.equals("w")) {//周数
				cal.add(Calendar.DATE, dValue*7);
			}
			return cal.getTimeInMillis();
		}
		
		/**
		 * 计算两个时间的时间差
		 * @param time1
		 * @param time2
		 * @param type
		 * 	TIMEDIFF(结束日期/时间,开始日期/时间,精度)，精度可填'y','M','d','H','m','s'"
		 * @return
		 * @author 
		 * 	2018-08-09 Diego.zhou 新建
		 */
		public static BigDecimal doGetTimesDValue(long time1,long time2,String type) {
			BigDecimal decimal1 = new BigDecimal(time1);
			BigDecimal decimal2 = new BigDecimal(time2);
			BigDecimal decimal3 = new BigDecimal(1);
			if(type.equals("y")) {//年
				decimal3 = new BigDecimal(24*60*60*1000*365L);
			}else if(type.equals("M")) {//月
				decimal3 = new BigDecimal(24*60*60*1000*30L);
			}else if(type.equals("d")) {//天
				decimal3 = new BigDecimal(24*60*60*1000L);
			}else if(type.equals("H")) {//小时
				decimal3 = new BigDecimal(60*60*1000L);
			}else if(type.equals("m")) {//分
				decimal3 = new BigDecimal(60*1000L);
			}else if(type.equals("s")) {//秒
				decimal3 = new BigDecimal(1000L);
			}
			return decimal1.subtract(decimal2).divide(decimal3, 4, BigDecimal.ROUND_HALF_UP);
		}
		
		/**
		 * DAY("03002","DAY"),某日期是当月的第几日
		 * HOUR("03005","HOUR"),HOUR函数可以返回某日期的小时数
		 * MINUTE("03007","MINUTE"),MINUTE函数可以返回某日期的分钟数
		 * MONTH("03008","MONTH"),MONTH返回某日期的月份
		 * SECOND("030010","SECOND"),SECOND函数可以返回某日期的秒数
		 * WEEKNUM("030015","WEEKNUM"),WEEKNUM函数可以返回指定日期在当年是第几周
		 * YEAR("030016","YEAR"), YEAR函数可以返回某日期的年份
		 * @param time
		 * @param type
		 * @return
		 * @author 
		 * 	2018-08-10 Diego.zhou 新建
		 */
		public static long doGetTimeDetail(long time,String type) {
			long result = 0;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(time);
			if(type.equals("DAY")) {//某日期是当月的第几日
				return cal.get(Calendar.DAY_OF_MONTH);
			}else if(type.equals("HOUR")) {//某日期的小时数
				return cal.get(Calendar.HOUR_OF_DAY);
			}else if(type.equals("MINUTE")) {//某日期的分钟数
				return cal.get(Calendar.MINUTE);
			}else if(type.equals("MONTH")) {//某日期的月份
				return cal.get(Calendar.MONTH)+1;
			}else if(type.equals("SECOND")) {//某日期的秒数
				return cal.get(Calendar.SECOND);
			}else if(type.equals("WEEKNUM")) {//指定日期在当年是第几周
				return cal.get(Calendar.WEEK_OF_YEAR);
			}else if(type.equals("YEAR")) {//某日期的年份
				return cal.get(Calendar.YEAR);
			}
			return result;
		}
		
		/**
		 * 将数字型Decimal日期转字符串String
		 * @param time
		 * @param formatType
		 * @return
		 * @author 
		 * 	2018-08-09 Diego.zhou 新建
		 */
		public static String doGetDecimalTimeToString(String time,String formatType) {
			String timeValueStr = "";
			if(!ChkUtil.isEmptyAllObject(time)) {
				long timeValue = new BigDecimal(time).longValue();
				//long转String
				String formatStyle = "yyyy-MM-dd";
				SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
				if(!ChkUtil.isEmpty(formatType)){
					//遍历时间格式枚举，获取对应的类型
					for(DateFormatEnum dateEnum : DateFormatEnum.values()){
						if(formatType.equals(dateEnum.getKey())){
							formatStyle = dateEnum.getMessage();
							break;
						}
					}
					sdf = new SimpleDateFormat(formatStyle);
				}
				Date date = new Date(timeValue);
				timeValueStr = sdf.format(date);
			}
			return timeValueStr;
		}
}

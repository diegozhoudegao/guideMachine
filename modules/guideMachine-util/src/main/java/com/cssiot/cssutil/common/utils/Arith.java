package com.cssiot.cssutil.common.utils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.Test;
/*** 
* @ClassName: Arith 
* @Description: 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精* 确的浮点数运算，包括加减乘除和四舍五入
* @author 作者 jacking
* @date 2016-5-20 下午3:50:14
 */
public class Arith{
	//默认除法运算精度
	private static final int DEF_DIV_SCALE =2;
	/**
	* @Description: 这个类不能实例化
	 */
	private Arith(){}
	
	
	/*** 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	*/
	public static double add(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	
	/*** 提供精确的减法运算。
	 * * @param v1 被减数
	 * * @param v2 减数
	 * * @return 两个参数的差
	*/
	public static double sub(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	
	/*** 提供精确的乘法运算。
	 * * @param v1 被乘数
	 * * @param v2 乘数
	 * * @return 两个参数的积
	*/
	public static double mul(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	
	/*** 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * * 小数点以后10位，以后的数字四舍五入。
	 * * @param v1 被除数* @param v2 除数
	 * * @return 两个参数的商
	 * */
	public static double div(double v1,double v2){
		return div(v1,v2,DEF_DIV_SCALE);
	}
	
	
	/*** 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
	 * * 定精度，以后的数字四舍五入。
	 * * @param v1 被除数
	 * * @param v2 除数
	 * * @param scale 表示表示需要精确到小数点以后几位。
	 * * @return 两个参数的商
	 * */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
			}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	/*** 提供精确的小数位四舍五入处理。
	 * * @param v 需要四舍五入的数字
	 * * @param scale 小数点后保留几位
	 * * @return 四舍五入后的结果
	 * */
	public static double round(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
			}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/*** 提供精确的小数位四舍五入处理。
	 * * @param v 需要四舍五入的数字
	 * * @param scale 小数点后保留几位
	 * * @return 四舍五入后的结果
	 * */
	public static double getNaNRound(String v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public static String getNaNRoundString(String v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
	}
	public static String getNaNRoundString(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
	}
	
	
	/**
	 * 截取整数
	 * */
	public static int getInteg(double num){
		String s=getdouble(num)+"";
		int index=s.indexOf('.');
		String l=s.substring(0,index);
		return Integer.parseInt(l);
	}
	
	/*
	 * 四舍五入
	 * @author Administrator date : 2013-8-28 上午10:29:36
	 */
	public static double getdouble(double d){
		BigDecimal b = new BigDecimal(d); 
		return  b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
	}
	
	public static double psnumber(Double d){
		double result=0.0;
		String s=getdouble(d)+"";
		int index=s.indexOf('.');
		String l=s.substring(index);
		if(l.equals(".0")){
			result=d;
		}else{
			result=Double.parseDouble(s.substring(0,index)+"")+1.00;
		}
		return result;
	}
	

	// 处理钱元转化为分
	public static String toPenny(double money) {
		
		String amt = mul(money, 100) + "";
		BigDecimal decimal = new BigDecimal(amt);
		amt = decimal.toPlainString();
		int index = amt.lastIndexOf(".");
		if (index > 0) {
			amt = amt.substring(0, index);
		}
		return amt;
	}
	/**
	 * 五舍六入，两位计算，自定义保留小数位数
	 * 格式化double小数new DecimalFormat("0.0000");
	 * @param data
	 * @return String 
	 */
	public static  String formatDoubleString(double data){
		DecimalFormat df=new DecimalFormat("0.0000");
		return df.format(data);
	}
		/**
		 * 五舍六入，两位计算，自定义保留小数位数
		 * 格式化double小数new DecimalFormat("0.0000");
		 * @param data
		 * @return double 
		 */
		public static  double formatDouble(double data){
//			BigDecimal b = new BigDecimal(Double.toString(data));
			DecimalFormat df=new DecimalFormat("0.0000");
			return Double.parseDouble(df.format(data));
	}
		/**
		 * 
		 * 格式化double小数 四舍五入
		 * @param data
		 * @return double 
		 */
		public static  double formatDoubleScaleFive(double data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("The scale must be a positive integer or zero");
			}
			BigDecimal   b   =   new   BigDecimal(Double.toString(data));  
//			double   f   =   b.setScale(scale, RoundingMode.HALF_UP).doubleValue(); 
			BigDecimal one = new BigDecimal("1");
			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
//			DecimalFormat df=new DecimalFormat("0.00");
//			return Double.parseDouble(df.format(data));
//			return f;
		}
		/**
		 * 五舍六入，两位计算，自定义保留小数位数
		 * @param data
		 * @param scale
		 * @return
		 */
		public static  double formatDoubleScaleSix(String data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("The scale must be a positive integer or zero");
			}
			BigDecimal   b   =   new   BigDecimal(data);  
			return  b.setScale(scale, RoundingMode.HALF_UP).doubleValue(); 
//			BigDecimal one = new BigDecimal("1");
//			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
//			DecimalFormat df=new DecimalFormat("0.00");
//			return Double.parseDouble(df.format(data));
//			return f;
		}
		/**
		 * 五舍六入，两位计算，自定义保留小数位数
		 * @param data
		 * @param scale
		 * @return
		 */
		public static  double formatDoubleScaleSix(double data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("The scale must be a positive integer or zero");
			}
			BigDecimal   b   =   new   BigDecimal(data);  
			return  b.setScale(scale, RoundingMode.HALF_UP).doubleValue(); 
//			BigDecimal one = new BigDecimal("1");
//			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
//			DecimalFormat df=new DecimalFormat("0.00");
//			return Double.parseDouble(df.format(data));
//			return f;
		}
		/**
		 * 五舍六入，两位计算保留2位小数
		 * @param data "#,##0.0000"
		 * @return double 
		 */
		public static  double formatDoubleMyDouble(double data){
			
			
			return Double.parseDouble(new DecimalFormat("#,##0.0000").format(data))/1.00;
			
		/*	double d= Math.round(data*100);
			double   f   =   111231.5585;  
			BigDecimal   b   =   new   BigDecimal(f);  
			double   f1   =   b.setScale(2, RoundingMode.HALF_UP).doubleValue(); 
			DecimalFormat df=new DecimalFormat("0.00");
			return Double.parseDouble(df.format(data));*/
		}
		/**
		 * 五舍六入，两位计算保留2位小数
		 * @param data
		 * @return String 
		 */
		public static  String formatDoubleMyString(double data){
			
			return new DecimalFormat("#,##0.0000").format(data);
			
			/*	double d= Math.round(data*100);
			double   f   =   111231.5585;  
			BigDecimal   b   =   new   BigDecimal(f);  
			double   f1   =   b.setScale(2, RoundingMode.HALF_UP).doubleValue(); 
			DecimalFormat df=new DecimalFormat("0.00");
			return Double.parseDouble(df.format(data));*/
		}
		
		
		
		public static void main(String[] str){
//			double d=formatDouble2(0.5446);
//			double d= Math.round(10.1);
			double d= Math.round(10.1*100);
			double dd=d/100.00;
			DecimalFormat df=new DecimalFormat("0.0000");
//			System.out.println(d);
			dd=Double.parseDouble(df.format(0.112));
//			System.out.println(dd);
//			 System.out.println("Double :" + new DecimalFormat("#,##0.00").format(new Double("96")));
			 String ddd=new DecimalFormat("#,##0.0000").format(100.0);
			 BigDecimal bg = new BigDecimal(ddd);  
	            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
			double d2=Double.parseDouble(ddd);
//			 System.out.println(f1);
		/*	double   f   =   111231.5551;  
			BigDecimal   b   =   new   BigDecimal(f);  
			double   f1   =   b.setScale(2, RoundingMode.HALF_UP).doubleValue();  
			System.out.println(f1);*/
		}
		
		@Test
		public void format(){
//			String str="00123.99";
//			System.out.println("data="+Float.valueOf(str));
			
			double tt=1.20f;
//			System.out.println(tt);
		}
		@Test
		public void format22(){
//			String str="00123.99";
//			System.out.println("data="+Float.valueOf(str));
			
//			System.out.println(getNaNRoundString("9991", 2));
//			System.out.println(getNaNRoundString(0.1, 2));
		}
}

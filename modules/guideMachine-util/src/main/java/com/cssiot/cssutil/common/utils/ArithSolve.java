package com.cssiot.cssutil.common.utils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
/*** 
* @ClassName: Arith 
* @Description: 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精* 确的浮点数运算，包括加减乘除和四舍五入
* @author 作者 jacking
* @date 2016-5-20 下午3:50:14
 */
public class ArithSolve{
	//默认除法运算精度
	private static final int DEF_DIV_SCALE =2;
	/**
	* @Description: 这个类不能实例化
    * @author 作者 jacking 
	* @date 2016-5-27 下午2:18:15 
	 */
	private ArithSolve(){}
	
	
	/*** 提供精确的加法运算。
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 * @author 作者 jacking 
	 * @date 2016-5-27 下午2:18:15 
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
	 * * @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
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
	 * * @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
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
	 * * @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
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
	 * * @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
	 * */
	public static double div(double v1,double v2,int scale){
		if(scale<0){
			throw new IllegalArgumentException("精度不能小于0");
			}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	/*** 提供精确的小数位四舍五入处理。
	 * * @param v 需要四舍五入的数字
	 * * @param scale 小数点后保留几位
	 * * @return 四舍五入后的结果
	 * * @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
	 * */
	public static double round(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("精度不能小于0");
			}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/*** 提供精确的小数位四舍五入处理。
	 * * @param v 需要四舍五入的数字
	 * * @param scale 小数点后保留几位
	 * * @return 四舍五入后的结果
	 * * @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
	 * */
	public static double getNaNRound(String v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("精度不能小于0");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * 四舍五入，自定义精度
	* @Description: TODO(描述) 
	* @param  v 数据
	* @param  scale 精度
	* @author 作者 jacking 
	* @date 2016-5-27 下午2:28:52 
	* @return
	 */
	public static String getNaNRoundString(String v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("精度不能小于0");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
	}
	/**
	 * 四舍五入，自定义精度
	* @Description: TODO(描述) 
	* @param  v 数据
	* @param  scale 精度
	* @author 作者 jacking 
	* @date 2016-5-27 下午2:28:52 
	* @return
	 */
	public static String getNaNRoundString(double v,int scale){
		if(scale<0){
			throw new IllegalArgumentException("精度不能小于0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
	}
	
	
	/**
	* @Description: 截取数据为整数
	* @param  num 数据
	* @author 作者 jacking 
	* @date 2016-5-27 下午2:28:52 
	* @return
	 */
	public static int getInteg(double num){
		String s=getdouble(num)+"";
		int index=s.indexOf('.');
		String l=s.substring(0,index);
		return Integer.parseInt(l);
	}
	
	/**
	 * 四舍五入保留两位小数
	* @Description: 四舍五入
	* @param  d double数据
	* @author 作者 jacking 
	* @date 2016-5-27 下午2:31:38 
	* @return
	 */
	public static double getdouble(double d){
		BigDecimal b = new BigDecimal(d); 
		return  b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
	}
 
	
	/**
	 * 处理钱元转化为分
	* @Description: TODO(描述) 
	* @param  设定
	* @author 作者 jacking 
	* @date 2016-5-27 下午2:40:56 
	* @return
	 */
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
	 * 五舍六入，两位计算，保留小数位数
	 * 格式化double小数new DecimalFormat("0.00");
	 * @param data
	 * @return String 
	 * @author 作者 jacking 
	* @date 2016-5-27 下午2:40:56 
	 */
	public static  String formatDoubleString(double data){
		DecimalFormat df=new DecimalFormat("0.00");
		return df.format(data);
	}
		/**
		 * 五舍六入，两位计算，保留小数位数
		 * 格式化double小数new DecimalFormat("0.00");
		 * @param data
		 * @return double 
		 * @author 作者 jacking 
		 * @date 2016-5-27 下午2:40:56 
		 */
		public static  double formatDouble(double data){
			DecimalFormat df=new DecimalFormat("0.00");
			return Double.parseDouble(df.format(data));
	}
		/**
		 * 
		 * 格式化double小数 四舍五入
		 * @param data
		 * @return double 
		 * @author 作者 jacking 
	     * @date 2016-5-27 下午2:40:56 
		 */
		public static  double formatDoubleScaleFive(double data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("精度不能小于0");
			}
			BigDecimal   b   =   new   BigDecimal(Double.toString(data));  
			BigDecimal one = new BigDecimal("1");
			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		/**
		 * 
		 * 格式化String小数 四舍五入
		 * @param data
		 * @return double 
		 * @author 作者 jacking 
	     * @date 2016-5-27 下午2:40:56 
		 */
		public static  String formatDoubleScaleFiveString(double data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("精度不能小于0");
			}
			BigDecimal b = new BigDecimal(Double.toString(data));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
		}
		
		
		
		/**
		 * 格式化String 数据小数 四舍五入
		* @Description: TODO(描述) 
		* @param  data 数据
		* @param  scale 保留多少位小数
		* @author 作者 jacking 
		* @date 2016-5-27 下午2:18:15 
		* @return
		 */
		public static  String formatStringScaleFive(String data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("保留多少位小数，不能小于0");
			}
			BigDecimal   b   =   new   BigDecimal(data);  
			//除数比例
			BigDecimal one = new BigDecimal("1");
			return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).toString();
		}
		/**
		 * * 五舍六入，两位计算，自定义保留小数位数
		 * @param data
		 * @param scale
		* @author 作者 jacking 
		* @date 2016-5-27 下午2:19:28 
		* @return
		 */
		public static  double formatDoubleScaleSix(String data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("精度不能小于0");
			}
			BigDecimal   b   =   new   BigDecimal(data);  
			return  b.setScale(scale, RoundingMode.HALF_UP).doubleValue(); 
		}
		/**
		 * 五舍六入，两位计算，自定义保留小数位数
		 * @param data
		 * @param scale
		 * @author 作者 jacking 
		* @date 2016-5-27 下午2:40:56 
		 * @return
		 */
		public static  double formatDoubleScaleSix(double data,int scale){
			if(scale<0){
				throw new IllegalArgumentException("The scale must be a positive integer or zero");
			}
			BigDecimal   b   =   new   BigDecimal(data);  
			return  b.setScale(scale, RoundingMode.HALF_UP).doubleValue(); 
		}
		/**
		 * 五舍六入，两位计算保留2位小数
		 * @param data "#,##0.00"
		 * @return double 
		 * @author 作者 jacking 
		 * @date 2016-5-27 下午2:40:56 
		 */
		public static  double formatDoubleMyDouble(double data){
			
			return Double.parseDouble(new DecimalFormat("#,##0.00").format(data))/1.00;
		}
		/**
		 * 五舍六入，两位计算保留2位小数
		 * @param data
		 * @return String 
		 * @author 作者 jacking 
		 * @date 2016-5-27 下午2:40:56 
		 */
		public static  String formatDoubleMyString(double data){
			return new DecimalFormat("#,##0.00").format(data);
		}
		/**
		 * 数字字符串转long类型
		* @Description: TODO(描述) 
		* @param  data 数据
		* @author 作者 jacking 
		* @date 2016-6-16 下午2:46:07 
		* @return
		 */
		public static  long formatStringNumber2Long(String data){
			    double d=new Double(data);
			    long l=Math.round(d);;
			return l;
		}
 
		/**
		 * 根据传入的整数，和补零位数，转换@param  data  整数， scale  位数标识如"00" 保留两位，不足补零
		* @Description: TODO(描述) 
		* @param  data  整数
		* @param  scale  位数标识如"00" 保留两位，不足补零
		* @author 作者 jacking 
		* @date 2016-6-22 上午10:53:34 
		* @return
		 */
		public static  String formatInteger2StringScale(Integer data,String scale){
			DecimalFormat df = new DecimalFormat(scale);  
			return df.format(data);
		}
		
		
}

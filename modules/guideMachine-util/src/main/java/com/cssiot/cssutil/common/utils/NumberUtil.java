package com.cssiot.cssutil.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字处理
 * @author 
 * 	2018-06-07 athena 迁移
 *
 */
public class NumberUtil {
	private static final String[] CN_UPPER_NUMBER = { "零", "壹", "贰", "叁", "肆","伍", "陆", "柒", "捌", "玖" };//汉语中数字大写
	private static final String[] CN_UPPER_MONETRAY_UNIT = { "分", "角", "元","拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆", "拾","佰", "仟" };//汉语中货币单位大写，这样的设计类似于占位符
	private static final String CN_FULL = "整";//特殊字符：整
	private static final String CN_NEGATIVE = "负";//特殊字符：负
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;//特殊字符：零元整
	
	/**
	 * 把输入的金额转换为汉语中人民币的大写
	 * @param numberOfMoney 输入的金额
	 * @return 对应的汉语大写
	 * @author 
	 * 	2018-06-07 athena 迁移
	 */
	public static String number2CNMontrayUnit(BigDecimal numberOfMoney,int percisionOfMoney) {
	     StringBuffer sb = new StringBuffer();
	     // -1, 0, or 1 as the value of this BigDecimal is negative, zero, or positive.
	     if(!ChkUtil.isBigDecimal(numberOfMoney)){
	    	 return CN_ZEOR_FULL;
	     }
	     int signum = numberOfMoney.signum();
	     if(!ChkUtil.isInteger(percisionOfMoney)){
	    	 percisionOfMoney=2;
	     }
	     // 零元整的情况
	     if (signum == 0) {
	         return CN_ZEOR_FULL;
	     }
	     // 这里会进行金额的四舍五入
	     long number = numberOfMoney.movePointRight(percisionOfMoney).setScale(0, 4).abs().longValue();
	     // 得到小数点后两位值
	     long scale = number % 100;
	     int numUnit = 0;
	     int numIndex = 0;
	     boolean getZero = false;
         // 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
         if (!(scale > 0)) {
             numIndex = 2;
             number = number / 100;
             getZero = true;
         }
         if ((scale > 0) && (!(scale % 10 > 0))) {
             numIndex = 1;
             number = number / 10;
             getZero = true;
         }
         int zeroSize = 0;
         while (true) {
             if (number <= 0) {
                 break;
             }
             // 每次获取到最后一个数
             numUnit = (int) (number % 10);
             if (numUnit > 0) {
                 if ((numIndex == 9) && (zeroSize >= 3)) {
                     sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
                 }
                 if ((numIndex == 13) && (zeroSize >= 3)) {
                     sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
                 }
                 sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                 sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                 getZero = false;
                 zeroSize = 0;
             } else {
                 ++zeroSize;
                 if (!(getZero)) {
                     sb.insert(0, CN_UPPER_NUMBER[numUnit]);
                 }
                 if (numIndex == 2) {
                     if (number > 0) {
                         sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                     }
                 } else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
                     sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
                 }
                 getZero = true;
             }
             // 让number每次都去掉最后一个数
             number = number / 10;
             ++numIndex;
         }
         // 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
         if (signum == -1) {
             sb.insert(0, CN_NEGATIVE);
         }
         // 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
         if (!(scale > 0)) {
             sb.append(CN_FULL);
         }
         return sb.toString();
     }
	
	 /**
	  * 格式化数字为千分位
	  * @param text 数字
	  * @return 格式化后的数字
	  * @author 
	  *	  2018-06-07 athena 迁移
	  */
	 public static String fmtMicrometer(String text) {  
        DecimalFormat df = null;  
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {  
                df = new DecimalFormat("###,##0.");  
            } else if (text.length() - text.indexOf(".") - 1 >= 1) {
            	int len=text.length() - text.indexOf(".") - 1;
            	String temp="###,##0.";
            	for(int i=1;i<len+1;i++){
            		temp=temp+"#";
            	}
            	df = new DecimalFormat(temp);
//                df = new DecimalFormat("###,##0.0");
//            } else if (text.length() - text.indexOf(".") - 1 == 2) {  
//                df = new DecimalFormat("###,##0.00");
//            } else if (text.length() - text.indexOf(".") - 1 == 3) {  
//                df = new DecimalFormat("###,##0.000");
//            } else {  
//                df = new DecimalFormat("###,##0.00");  
            }  
        } else {  
            df = new DecimalFormat("###,##0");  
        }  
        double number = 0.0;  
        try {  
            number = Double.parseDouble(text);  
        } catch (Exception e) {  
            number = 0.0;  
        }  
        return df.format(number);  
    }
 
     public static void main(String[] args) {
         double money = 2020004.01;
         BigDecimal numberOfMoney = new BigDecimal(money);
         String s = NumberUtil.number2CNMontrayUnit(numberOfMoney,2);
         System.out.println("你输入的金额为：【"+ money +"】   #--# [" +s.toString()+"]");
     }
}

package com.cssiot.cssutil.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

/**
 * 随机生成动态吗
 * 
 * @author shinry.liu
 *
 */
public class RandomStringUtil {
	/**
	 * 生成随机的字符串
	 * 
	 * @param length 长度
	 * @param type 类型
	 * @return 
	 */
	public static  String getRandomNewString(String length,String type){
		String [] starts=new String[Integer.parseInt(length)];
		List exampleList=getExampleList(type);
		String [] exacmple=new String[exampleList.size()];
		exacmple=(String[]) exampleList.toArray(exacmple);
		StringBuffer buString=new StringBuffer();
		for(int i=0;i<starts.length;i++){
			buString.append(exacmple[RandomUtils.nextInt(exacmple.length)]);
		}
		//System.out.println(buString.toString());
		return buString.toString();
	}
	/**
	 * 生成随机的字符串
	 * 
	 * @param length 长度
	 * @param type 类型 0是0-9为数字
	 *                1是A-Z的字母
	 *                2是0-9，a-z,A-z
	 *                3是-09，A-Z
	 *                4是A-Z,a-z
	 *                5是0-9,A-z
	 *                
	 * @return 
	 */
	@SuppressWarnings("all")
	public static List getExampleList(String type){
		List exampleList=new ArrayList<>();
		List<String> numberList=new ArrayList<>();
		for(int i=0;i<10;i++){
			numberList.add(String.valueOf(i));
		}
		List<String>caseNumberList=new ArrayList<>();
		for(int i=65;i<91;i++){
			//先转化为char
			char c=(char)i;
			caseNumberList.add(String.valueOf(c));
		}
		List<String> lowerNumberList=new ArrayList<>();
		for(int i=97;i<123;i++){
			char c=(char)i;
			lowerNumberList.add(String.valueOf(c));
		}
		if("0".equals(type)){
			exampleList.addAll(numberList);
		}else if("1".equals(type)){
			exampleList.addAll(caseNumberList);
		}else if("2".equals(type)){
			exampleList.addAll(caseNumberList);
			exampleList.addAll(numberList);
			exampleList.addAll(lowerNumberList);
		}else if("3".equals(type)){
			exampleList.addAll(caseNumberList);
			exampleList.addAll(numberList);
		}else if("4".equals(type)){
			exampleList.addAll(caseNumberList);
			exampleList.addAll(lowerNumberList);
		}else if("5".equals(type)){
			exampleList.addAll(numberList);
			exampleList.addAll(lowerNumberList);
		}
		return exampleList;
	}
	
	public static void main(String[] args) {
		System.out.println(RandomStringUtil.getRandomNewString("6","2"));
	}
}

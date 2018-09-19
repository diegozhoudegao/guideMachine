package com.cssiot.cssutil.common.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cssiot.cssutil.common.data.CommModel;

/**
 * 
 * @description 生成查询in里内容
 * @author zdg
 * @time 2016-03-10
 * 
 */

public class SelectUtils {

	/**
	 * 用于生成查询语句所用，将list转成string，用，隔开
	 * @param idlist
	 * @param type
	 * @param tmpType
	 * @return
	 * @author zdg athena
	 * @time 2016-03-10
	 */
	public static String getSelectString(List<String> idlist, String type,String tmpType){
		String temp = "";
		DecimalFormat df = new DecimalFormat("###.0000");
		String tmp=df.format(idlist.size()/10000.0);
		double tmpSize = Double.parseDouble(tmp);
		double tmpjSize=idlist.size();
		
		for(int j=0;j<tmpSize;j++){
			if(j==0){
				temp+="("+type+" "+tmpType+" in (";
			}else{
				temp+=" or "+type+" "+tmpType+" in (";
			}
			double tmpj=j+1;
			if(tmpj>tmpSize){
				tmpj=tmpSize;
			}
			if(idlist.size()>1000){
				tmpjSize=tmpj*1000;
			}
			for(int i=j*1000;i<tmpjSize;i++){
				temp+="'"+idlist.get(i)+"',";
			}
			temp=temp.substring(0, temp.length()-1)+") ";
		}
		if(!temp.equals("")){
			temp=temp+")";
		}
		return temp;
	}
	
	/**
	 * 按长度对string进行截取
	 * @param s 需要截取内容
	 * @param length 截取长度
	 * @return
	 * @throws Exception
	 * @author athena
	 * @time 2017-02-07
	 */
	public static String getSubstring(String s, int length) throws Exception{
        byte[] bytes = s.getBytes("Unicode");
        int n = 0; // 表示当前的字节数
        int i = 2; // 要截取的字节数，从第3个字节开始
        if(bytes.length<length){
        	length=bytes.length;
        }
        for (; i < bytes.length && n < length; i++){
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
            if (i % 2 == 1){
                n++; // 在UCS2第二个字节时n加1
            }else{
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
                if (bytes[i] != 0){
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数
        if (i % 2 == 1){
            // 该UCS2字符是汉字时，去掉这个截一半的汉字
            if (bytes[i - 1] != 0){
                i = i - 1;
            // 该UCS2字符是字母或数字，则保留该字符
            }else{
                i = i + 1;
            }
        }
        return new String(bytes, 0, i, "Unicode");
    }
	
	/**
	 * 按长度对string进行拆分
	 * @param s 需要截取内容
	 * @param length 截取长度
	 * @return
	 * @throws Exception
	 * @author athena
	 * @time 2017-02-07
	 * 	2018-05-30 athena 增加对循环体多余信息的去掉
	 */
	public static List getSubstringToList(String s, int length) throws Exception{
		List list=new ArrayList<>();
		String temp="";
		s=s.replace("<tr class=\"firstRow loop_", "<tr class=\"loop_");
		while(temp.equals("")){
			temp=getSubstring(s, length);
			list.add(temp);
			if(temp.length()<s.length()){
	    		s=s.substring(temp.length(), s.length());
	    		temp="";
			}
    	}
        return list;
    }
	
	/**
	 * 从string中获取变量
	 * @param s 需要截取内容
	 * @param loopBody 循环体
	 * @return
	 * @throws Exception
	 * @author athena
	 * @time 2017-02-07
	 */
	public static List getVarToList(String s,String loopBody) throws Exception{
		List<CommModel> list=new ArrayList<>();
		Boolean flag=true;
		while(flag){
			int startIndex = s.indexOf("$${");
			if(startIndex==-1){
				return list;
			}
			s=s.substring(startIndex+3,s.length());
			int endIndex = s.indexOf("}");
			if(endIndex==-1){
				return list;
			}
			String temp=s.substring(0, endIndex);
			while(temp.contains("$${")){
				int tempIndex = temp.indexOf("$${");
				temp=temp.substring(tempIndex+3,temp.length());
			}
			if(!list.contains(temp)){
				CommModel cm=new CommModel();
				cm.setId(temp);
				if(!ChkUtil.isEmpty(loopBody)){
					cm.setName(loopBody);
				}else{
					cm.setName("");
				}
				list.add(cm);
			}
			if(endIndex>=s.length()-1){
				flag=false;
			}else{
				s=s.substring(endIndex+1, s.length());
			}
		}
        return list;
    }
	
	/**
	 * 从string中获取变量
	 * @param s 需要截取内容
	 * @return
	 * @throws Exception
	 * @author athena
	 * @time 2017-02-21
	 */
	public static List getContentToList(String s) throws Exception{
		List loopList=new ArrayList<>();
		List<CommModel> cmList=new ArrayList<>();
		Boolean flag=true;
		s=s.replace("<tr class=\"firstRow loop_", "<tr class=\"loop_");
		while(flag){
			int startIndex = s.indexOf("<tr class=\"loop_");
			if(startIndex==-1){
				List list=getVarToList(s,"");
				if(!ChkUtil.isEmptyAllObject(list)){
					cmList.addAll(list);
				}
				if(!ChkUtil.isEmptyAllObject(cmList)){
					//set去重
			        Set<CommModel> set = new HashSet<CommModel>(cmList);  
			        cmList.clear();
			        cmList.addAll(set);
				}
				return cmList;
			}
			List nolist=getVarToList(s.substring(0,startIndex),"");
			if(!ChkUtil.isEmptyAllObject(nolist)){
				cmList.addAll(nolist);
			}
			s=s.substring(startIndex,s.length());
			int endIndex = s.indexOf("</tr>");
			if(endIndex==-1){
				if(!ChkUtil.isEmptyAllObject(cmList)){
					//set去重
			        Set<CommModel> set = new HashSet<CommModel>(cmList);  
			        cmList.clear();
			        cmList.addAll(set);
				}
				return cmList;
			}
			String temp=s.substring(0, endIndex+5);
			String wtemp=s.substring(16,endIndex);
			while(wtemp.contains("<tr class=\"loop_")){
				int tempIndex = wtemp.indexOf("<tr class=\"loop_");
				temp=temp.substring(tempIndex,temp.length());
				wtemp=wtemp.substring(tempIndex+16,wtemp.length());
			}
			if(!loopList.contains(temp)){
				loopList.add(temp);
				List list=getVarToList(temp,temp);
				if(!ChkUtil.isEmptyAllObject(list)){
					cmList.addAll(list);
				}
			}
			if(endIndex>=s.length()-5){
				flag=false;
			}else{
				s=s.substring(endIndex+5, s.length());
			}
		}
		if(!ChkUtil.isEmptyAllObject(cmList)){
			//set去重
	        Set<CommModel> set = new HashSet<CommModel>(cmList);  
	        cmList.clear();
	        cmList.addAll(set);
		}
        return cmList;
    }
	
}

package com.cssiot.cssutil.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import com.cssiot.cssutil.common.data.FieldValueModel;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author athena
 * @time 2016-02-04
 * 
 */
public class StringUtil extends StringUtils {
	
	public static String lowerFirst(String str){
		if(StringUtil.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toLowerCase() + str.substring(1);
		}
	}
	
	public static String upperFirst(String str){
		if(StringUtil.isBlank(str)) {
			return "";
		} else {
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 缩略字符串（替换html）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
	}
		
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}
	
	/**
	 * 将元换算为万元,保留length位小数
	 * @param amount元为单位的金额，传入null则赋0
	 * @param length小数位长度
	 * @return amount万元为单位，length位小数
	 * @author cheng.zhang
	 * @since 2016-01-14
	 */
	public static BigDecimal changeAmount(BigDecimal amount,int length){
		if(length<=0){length=0;}
		if(null==amount){amount=new BigDecimal(0);};//如果传入了null值则默认赋0
//		amount = amount.divide(new BigDecimal(10000));
		return amount.setScale(length, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 将元换算为万元,保留length位小数
	 * @param String amount元为单位的金额，传入null则赋0
	 * @param int length小数位长度
	 * @return String amount万元为单位，length位小数
	 * @author jacking.jiao
	 * @since 2016-01-15
	 * 	2018-04-11 athena 修改toString为toPlainString解决科学技术法问题
	 */
	public static String changeAmountToString(String amount,int length){
		if(length<=0){length=0;}
		if(null==amount||"".equals(amount)){
			amount="0";
		}
		BigDecimal b=new BigDecimal(amount);
		return b.setScale(length, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
	
	/**
	 * 将集合拼接成字符串，值为null时也拼接，若拼接时排除null，则
	 * 采用org.apache.commons.lang.StringUtils.join(list.toArray(),separator);
	 * @param list 集合
	 * @param separator 拼接符
	 * @return String 拼接好的字符串，且末位已去掉拼接符
	 * @author 
	 * 	2018-01-29 Diego.zhou 新建
	 */
	public static String listToString(List list, char separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		return list.isEmpty()?"":sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	/**
	 * 将map转成相应sql语句，
	 * 	新增示例insert into tableName(key1,key2,key3) values('value1','value2','value3')
	 * 	修改示例update tableName set key1='value1',key2='value2' where key3='value3'
	 *  查询示例select * from tableName where key3='value3'
	 *  累加修改示例update tableName set key1=key1+value1,key2=key2+value2 where key3='value3'
	 *  累减修改示例update tableName set key1=key1-value1,key2=key2-value2 where key3='value3'
	 * @param valueMap 值map(K:字段名，V:字段值) 
	 * @param tableName 表名
	 * @param operate 操作:形成新增sql还是修改sql(0:新增，1:修改，2:查询，3：累加修改，4：累减修改，5：负的绝对值累加修改，6:新增带类型，7：反写查询)
	 * @param whereMap 条件map(K:字段名，V:字段值),目前只支持条件为等于的情况
	 * @return String 完整的新增sql语句
	 * @author 
	 * 	2018-02-07 Diego.zhou 修改
	 * 	2018-06-05 athena 增加生成查询、累加修改和累减修改语句功能
	 * 	2018-06-06 athena 负的绝对值累加修改
	 * 	2018-06-19 athena 调整3、4、5，增加对反写值类型的校验，增加6新增带类型
	 * 	2018-06-21 athena 增加7反写查询，调试修改
	 * 	2018-08-09 athena 增加比较符替换条件中的等于
	 * 	2018-08-29 athena 去掉如果为数字等于时的单引号
	 * 	2018-08-30 athena 解决负数累加或累减出错问题
	 */
	@SuppressWarnings("all")
	public static String mapToSql(Map<String,Object> valueMap,String tableName,String operate,Map<String,Object> whereMap){
    	//操作判断
		if(ChkUtil.isEmpty(operate)){
			return "";
		}
		//新增
		if(operate.equals("0")){
			List fields = new ArrayList<>();
	    	List values = new ArrayList<>();
	    	if(!ChkUtil.isEmptyAllObject(valueMap)){
	    		valueMap.forEach((K,V) -> {
	    			fields.add(K);
	    			if(null != V){
	    				values.add("'"+V+"'");
	    			}else{
	    				values.add(null);
	    			}
	    		});
	    	}
	    	String fieldStr = "( "+listToString(fields,',')+" )";
	    	String valueStr = "( "+listToString(values,',')+" )";
	    	//新增sql
	    	String addSql = "INSERT INTO " + tableName +fieldStr+" values"+valueStr;
	    	return addSql;
		}
		//修改
		if(operate.equals("1")){
			String updateMsg = "";
	    	if(!ChkUtil.isEmptyAllObject(valueMap)){
	    		for(String key : valueMap.keySet()){
	    			updateMsg+=key;
	    			if(null != valueMap.get(key)){
	    				updateMsg+=" = "+"'"+valueMap.get(key)+"', ";
	    			}else{
	    				updateMsg+=" = NULL, ";
	    			}
	    		}
	    		updateMsg = updateMsg.substring(0, updateMsg.lastIndexOf(","));
	    	}
	    	String where = "";
	    	if(!ChkUtil.isEmptyAllObject(whereMap)){
	    		for(String key : whereMap.keySet()){
	    			where+=key;
	    			if(null != whereMap.get(key)){
	    				where+=" = "+"'"+whereMap.get(key)+"' AND ";
	    			}else{
	    				where+=" = NULL AND ";
	    			}
	    		}
	    		//截掉最后一个AND
	    		where = where.substring(0, where.lastIndexOf("AND"));
	    	}
	    	//修改sql
	    	if(!ChkUtil.isEmpty(where)){
	    		String updateSql = "UPDATE "+ tableName +" SET "+updateMsg+" WHERE "+where;
	    		return updateSql;
	    	}
		}
		//查询
		if(operate.equals("2")){
	    	String where = "";
	    	if(!ChkUtil.isEmptyAllObject(whereMap)){
	    		for(String key : whereMap.keySet()){
	    			where+=key;
	    			if(null != whereMap.get(key)){
	    				where+=" = "+"'"+whereMap.get(key)+"' AND ";
	    			}else{
	    				where+=" = NULL AND ";
	    			}
	    		}
	    		//截掉最后一个AND
	    		where = where.substring(0, where.lastIndexOf("AND"));
	    	}
	    	//修改sql
	    	if(!ChkUtil.isEmpty(where)){
	    		String selectSql = "SELECT * FROM "+ tableName +" WHERE "+where;
	    		return selectSql;
	    	}
		}
		//反写查询
		if(operate.equals("7")){
	    	String where = "";
	    	FieldValueModel fvm=new FieldValueModel();
	    	if(!ChkUtil.isEmptyAllObject(whereMap)){
	    		for(String key : whereMap.keySet()){
//	    			where+=key;
	    			if(null != whereMap.get(key)){
	    				fvm=(FieldValueModel) whereMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							if(new BigDecimal(fvm.getElementValue()).compareTo(new BigDecimal(0))==0){
	    								where+=" ("+key+" "+fvm.getJudgeSymbol()+" "+fvm.getElementValue()+" or "+key+" is null) AND ";
	    							}else{
	    								where+=" "+key+" "+fvm.getJudgeSymbol()+" "+fvm.getElementValue()+" AND ";
	    							}
		    					}else{
		    						where+=" "+key+" "+fvm.getJudgeSymbol()+" '"+fvm.getElementValue()+"' AND ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
	    							where+=" "+key+" "+fvm.getJudgeSymbol()+" NULL AND ";
		    					}else{
		    						where+=" "+key+" "+fvm.getJudgeSymbol()+" 0 AND ";
		    					}
	    					}
	    				}else{
	    					where+=" "+key+" "+fvm.getJudgeSymbol()+" NULL AND ";
	    				}
	    			}else{
	    				where+=" "+key+" "+fvm.getJudgeSymbol()+" NULL AND ";
	    			}
	    		}
	    		//截掉最后一个AND
	    		where = where.substring(0, where.lastIndexOf("AND"));
	    	}
	    	//修改sql
	    	if(!ChkUtil.isEmpty(where)){
	    		String selectSql = "SELECT * FROM "+ tableName +" WHERE "+where;
	    		return selectSql;
	    	}
		}
		//累加修改
		if(operate.equals("3")){
			String updateMsg = "";
			FieldValueModel fvm=new FieldValueModel();
	    	if(!ChkUtil.isEmptyAllObject(valueMap)){
	    		for(String key : valueMap.keySet()){
	    			updateMsg+=key;
	    			if(null != valueMap.get(key)){
	    				fvm=(FieldValueModel) valueMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							if(!ChkUtil.isEmpty(fvm.getRewriteType()) && "1".equals(fvm.getRewriteType())){
	    								updateMsg+=" = "+key+fvm.getElementValue()+", ";
	    							}else if(!ChkUtil.isEmpty(fvm.getRewriteType()) && "0".equals(fvm.getRewriteType())){
	    								if(fvm.getElementValue().startsWith("-")){
	    									//负
	    									updateMsg+=" = "+key+fvm.getElementValue()+", ";
	    								}else{
	    									//正
	    									updateMsg+=" = "+key+"+"+fvm.getElementValue()+", ";
	    								}
	    							}else{
	    								updateMsg+=" = "+fvm.getElementValue()+", ";
	    							}
		    					}else{
		    						updateMsg+=" = '"+fvm.getElementValue()+"', ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
		    						updateMsg+=" = '"+fvm.getElementValue()+"', ";
		    					}else{
		    	    				updateMsg+=" = NULL, ";
		    	    			}
	    					}
	    				}else{
		    				updateMsg+=" = NULL, ";
		    			}
	    			}else{
	    				updateMsg+=" = NULL, ";
	    			}
	    		}
	    		updateMsg = updateMsg.substring(0, updateMsg.lastIndexOf(","));
	    	}
	    	String where = "";
	    	if(!ChkUtil.isEmptyAllObject(whereMap)){
	    		for(String key : whereMap.keySet()){
	    			where+=key;
	    			if(null != whereMap.get(key)){
	    				fvm=(FieldValueModel) whereMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							where+=" "+fvm.getJudgeSymbol()+" "+fvm.getElementValue()+" AND ";
		    					}else{
		    						where+=" "+fvm.getJudgeSymbol()+" '"+fvm.getElementValue()+"' AND ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
	    							where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
		    					}else{
		    						where+=" "+fvm.getJudgeSymbol()+" 0 AND ";
		    					}
	    					}
	    				}else{
	    					where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
	    				}
	    			}else{
	    				where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
	    			}
	    		}
	    		//截掉最后一个AND
	    		where = where.substring(0, where.lastIndexOf("AND"));
	    	}
	    	//修改sql
	    	if(!ChkUtil.isEmpty(where)){
	    		String updateSql = "UPDATE "+ tableName +" SET "+updateMsg+" WHERE "+where;
	    		return updateSql;
	    	}
		}
		//累减修改
		if(operate.equals("4")){
			String updateMsg = "";
			String temp="";
			FieldValueModel fvm=new FieldValueModel();
	    	if(!ChkUtil.isEmptyAllObject(valueMap)){
	    		for(String key : valueMap.keySet()){
	    			updateMsg+=key;
	    			if(null != valueMap.get(key)){
	    				fvm=(FieldValueModel) valueMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						temp=fvm.getElementValue();
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							if(!ChkUtil.isEmpty(fvm.getRewriteType()) && "1".equals(fvm.getRewriteType())){
	    								//累减
	    								updateMsg+=" = "+key+"+"+temp.substring(1, temp.length())+", ";
	    							}else if(!ChkUtil.isEmpty(fvm.getRewriteType()) && "0".equals(fvm.getRewriteType())){
	    								if(temp.startsWith("-")){
	    									//负
	    									updateMsg+=" = "+key+"+"+temp.substring(1, temp.length())+", ";
	    								}else{
	    									//正
	    									updateMsg+=" = "+key+"-"+temp+", ";
	    								}
	    							}else{
	    								updateMsg+=" = "+temp+", ";
	    							}
		    					}else{
		    						updateMsg+=" = '"+temp+"', ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
		    						updateMsg+=" = '"+fvm.getElementValue()+"', ";
		    					}else{
		    	    				updateMsg+=" = NULL, ";
		    	    			}
	    					}
	    				}else{
		    				updateMsg+=" = NULL, ";
		    			}
	    			}else{
	    				updateMsg+=" = NULL, ";
	    			}
	    		}
	    		updateMsg = updateMsg.substring(0, updateMsg.lastIndexOf(","));
	    	}
	    	String where = "";
	    	if(!ChkUtil.isEmptyAllObject(whereMap)){
	    		for(String key : whereMap.keySet()){
	    			where+=key;
	    			if(null != whereMap.get(key)){
	    				fvm=(FieldValueModel) whereMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							where+=" "+fvm.getJudgeSymbol()+" "+fvm.getElementValue()+" AND ";
		    					}else{
		    						where+=" "+fvm.getJudgeSymbol()+" '"+fvm.getElementValue()+"' AND ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
	    							where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
		    					}else{
		    						where+=" "+fvm.getJudgeSymbol()+" 0 AND ";
		    					}
	    					}
	    				}else{
	    					where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
	    				}
	    			}else{
	    				where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
	    			}
	    		}
	    		//截掉最后一个AND
	    		where = where.substring(0, where.lastIndexOf("AND"));
	    	}
	    	//修改sql
	    	if(!ChkUtil.isEmpty(where)){
	    		String updateSql = "UPDATE "+ tableName +" SET "+updateMsg+" WHERE "+where;
	    		return updateSql;
	    	}
		}
		//负的绝对值累加修改
		if(operate.equals("5")){
			String updateMsg = "";
			String temp="";
			FieldValueModel fvm=new FieldValueModel();
	    	if(!ChkUtil.isEmptyAllObject(valueMap)){
	    		for(String key : valueMap.keySet()){
	    			updateMsg+=key;
	    			if(null != valueMap.get(key)){
	    				fvm=(FieldValueModel) valueMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						temp=fvm.getElementValue();
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							if(!ChkUtil.isEmpty(fvm.getRewriteType()) && "1".equals(fvm.getRewriteType())){
	    								//累减
	    								updateMsg+=" = "+key+"+"+temp.substring(1, temp.length())+", ";
	    							}else if(!ChkUtil.isEmpty(fvm.getRewriteType()) && "0".equals(fvm.getRewriteType())){
	    								updateMsg+=" = "+key+"-"+temp+", ";
	    							}else{
	    								updateMsg+=" = "+temp+", ";
	    							}
	    							
		    					}else{
		    						updateMsg+=" = '"+temp+"', ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
		    						updateMsg+=" = '"+temp+"', ";
		    					}else{
		    	    				updateMsg+=" = NULL, ";
		    	    			}
	    					}
	    				}else{
		    				updateMsg+=" = NULL, ";
		    			}
	    			}else{
	    				updateMsg+=" = NULL, ";
	    			}
	    		}
	    		updateMsg = updateMsg.substring(0, updateMsg.lastIndexOf(","));
	    	}
	    	String where = "";
	    	if(!ChkUtil.isEmptyAllObject(whereMap)){
	    		for(String key : whereMap.keySet()){
	    			where+=key;
	    			if(null != whereMap.get(key)){
	    				fvm=(FieldValueModel) whereMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							where+=" "+fvm.getJudgeSymbol()+" "+fvm.getElementValue()+" AND ";
		    					}else{
		    						where+=" "+fvm.getJudgeSymbol()+" '"+fvm.getElementValue()+"' AND ";
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
	    							where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
		    					}else{
		    						where+=" "+fvm.getJudgeSymbol()+" 0 AND ";
		    					}
	    					}
	    				}else{
	    					where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
	    				}
	    			}else{
	    				where+=" "+fvm.getJudgeSymbol()+" NULL AND ";
	    			}
	    		}
	    		//截掉最后一个AND
	    		where = where.substring(0, where.lastIndexOf("AND"));
	    	}
	    	//修改sql
	    	if(!ChkUtil.isEmpty(where)){
	    		String updateSql = "UPDATE "+ tableName +" SET "+updateMsg+" WHERE "+where;
	    		return updateSql;
	    	}
		}
		//新增带类型
		if(operate.equals("6")){
			List fields = new ArrayList<>();
	    	List values = new ArrayList<>();
	    	FieldValueModel fvm=new FieldValueModel();
	    	if(!ChkUtil.isEmptyAllObject(valueMap)){
	    		for(String key : valueMap.keySet()){
	    			fields.add(key);
	    			if(null != valueMap.get(key)){
	    				fvm=(FieldValueModel) valueMap.get(key);
	    				if(!ChkUtil.isEmptyAllObject(fvm)){
	    					if(!ChkUtil.isEmpty(fvm.getElementValue())){
	    						if(!ChkUtil.isEmpty(fvm.getFieldType()) && "1".equals(fvm.getFieldType())){
	    							values.add(""+fvm.getElementValue()+"");
		    					}else{
		    						values.add("'"+fvm.getElementValue()+"'");
		    					}
	    					}else{
	    						if(ChkUtil.isEmpty(fvm.getFieldType()) || "0".equals(fvm.getFieldType())){
	    							values.add(null);
		    					}else{
		    						values.add(0);
		    					}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	String fieldStr = "( "+listToString(fields,',')+" )";
	    	String valueStr = "( "+listToString(values,',')+" )";
	    	//新增sql
	    	String addSql = "INSERT INTO " + tableName +fieldStr+" values"+valueStr;
	    	return addSql;
		}
		return "";
	}
	
	/**
	 *  首字母大写
	 * @param field 字段
	 * @param opType get或set
	 * @return
	 * @author
	 * 2018年6月20日 tck 创建
	 */
	public static String getMethod(String userField, String opType) {
		 char[] cs = userField.toCharArray();
	     cs[0]-=32;
	     return opType + String.valueOf(cs);
	}

	/**
	 * 通过实体类的字段属性过滤
	 * @param keyExtractor
	 * @return
	 * @author
	 * 2018年7月27日 tck 创建
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
	    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
	    return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
	}
}

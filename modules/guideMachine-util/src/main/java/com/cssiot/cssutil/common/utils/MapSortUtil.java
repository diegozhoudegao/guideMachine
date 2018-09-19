package com.cssiot.cssutil.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.junit.Test;
@SuppressWarnings("all")
/**
* @ClassName: MapSortUtil 
* @Description: 本类主要处理Map 的按key，按value的排序问题
* @author 作者 jacking
* @date 2016-5-20 下午3:53:51
 */
public class MapSortUtil {
	
 /**
  * Map 按key 排序
  * @param map
  * @param sort  true 正序 false 倒序
  * @return
  */
	
	public  static Map  getSortMapByKey(Map<String,Object> map,final boolean sort){
		 Map<String, Object> treeMap = new TreeMap<String, Object>(
	                new Comparator<String>() {
	                    public int compare(String obj1, String obj2) {
	                        // 降序排序
	                    	if(sort){
	                    		 return obj1.compareTo(obj2);
	                    	}else{
	                    		return obj2.compareTo(obj1);
	                    	}
	                    }
	                });
		 
		 for(Entry<String, Object> entry:map.entrySet()){
			 String key=entry.getKey();
			 Object value=entry.getValue();
			 treeMap.put(key, value);
			 
		 }
		
	
		return treeMap;
		
	}
	/**
	 * Map 按key 排序
	 * @param map
	 * @param sort  true 正序 false 倒序
	 * @return
	 */
	
	public  static Map  getSortMapByKeyInteger(Map<Integer,Object> map,final boolean sort){
		Map<Integer, Object> treeMap = new TreeMap<Integer, Object>(
				new Comparator<Integer>() {
					public int compare(Integer obj1, Integer obj2) {
						// 降序排序
						if(sort){
							return obj1.compareTo(obj2);
						}else{
							return obj2.compareTo(obj1);
						}
					}
				});
		
		for(Entry<Integer, Object> entry:map.entrySet()){
			Integer key=entry.getKey();
			Object value=entry.getValue();
			treeMap.put(key, value);
			
		}
		
		
		return treeMap;
		
	}
	/**
	 * Map 按key(为String) 排序,返回List中数组（Object[]  1：key :value）
	 * @param map
	 * @param sort  true 正序 false 倒序
	 * @return List<Object[]>  Object[1] key,Object[1] value
	 */
	
	public  static List<Object[]>  getSortMapByKey2List(Map<String,Object> map,final boolean sort){
		List<Object[]>  lst=new ArrayList<>();
		Map<String, Object> treeMap = new TreeMap<String, Object>(
				new Comparator<String>() {
					public int compare(String obj1, String obj2) {
						// 降序排序
						if(sort){
							return obj1.compareTo(obj2);
						}else{
							return obj2.compareTo(obj1);
						}
					}
				});
		
		for(Entry<String, Object> entry:map.entrySet()){
			String key=entry.getKey();
			Object value=entry.getValue();
			treeMap.put(key, value);
		}
		for(Entry<String, Object> entry:treeMap.entrySet()){
			String key=entry.getKey();
			Object value=entry.getValue();
			Object[] data=new Object[2];
			data[0]=key;
			data[1]=value;
			lst.add(data);
		}
		
		return lst;
		
	}
	
	/**
	 * Map 按key(为Integer) 排序,返回List中数组（Object[]  1：key :value）
	 * @param map
	 * @param sort  true 正序 false 倒序
	 * @return List<Object[]>  Object[0] key,Object[1] value
	 */
	public  static List<Object[]>  getSortMapByKeyByInteger2List(Map<Integer,Object> map,final boolean sort){
		List<Object[]>  lst=new ArrayList<>();
		Map<Integer, Object> treeMap = new TreeMap<Integer, Object>(
				new Comparator<Integer>() {
					public int compare(Integer obj1, Integer obj2) {
						// 降序排序
						if(sort){
							return obj1.compareTo(obj2);
						}else{
							return obj2.compareTo(obj1);
						}
					}
				});
		
		for(Entry<Integer, Object> entry:map.entrySet()){
			Integer key=entry.getKey();
			Object value=entry.getValue();
			treeMap.put(key, value);
		}
		for(Entry<Integer, Object> entry:treeMap.entrySet()){
			Integer key=entry.getKey();
			Object value=entry.getValue();
			Object[] data=new Object[2];
			data[0]=key;
			data[1]=value;
			lst.add(data);
		}
		
		return lst;
		
	}
	
	/**
	 * Map 按value 排序(key value 均为String)
	 * @param map
	 * @param sort  true 正序 false 倒序
	 * @return
	 */
	public static Map  getSortMapByStringValue(Map<String,String> map,final boolean sort){
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
            	if(sort){
            		return o1.getValue().compareTo(o2.getValue());
            	}else{
            		return o2.getValue().compareTo(o1.getValue());
            	}
            }
        });
        
        map.clear();
        for(Map.Entry<String,String> mapping:list){ 
        	map.put(mapping.getKey(), mapping.getValue());
          } 
		
		return map;
		
	}
	
 
	   /**
	   * @Description:  按Map 指定key 或bean指定属性的值对此list集合进行排序 
       * @param list   要排序的集合 
		* @param propertyName  集合元素的属性名 
		* @param isAsc 排序方向,true--降序排序,false--升序排序 
	   * @author 作者 jacking 
	   * @date 2016-5-21 下午4:18:33 
	   * @return 排序后的集合 
	    */
		public  static List sortList(List list, String propertyName, boolean isAsc) { 
		      Comparator mycmp = ComparableComparator.getInstance();       
		        mycmp = ComparatorUtils.nullLowComparator(mycmp);  //允许null 
		        if(isAsc){ 
		        mycmp = ComparatorUtils.reversedComparator(mycmp); //降序       
		        } 
		        Comparator cmp = new BeanComparator(propertyName, mycmp);    
		        Collections.sort(list, cmp);   
		   return list; 
		} 
		
		  public static void main(String args[])
			{
				int i,min,max;
				int A[]={74,48,30,17,62};  // 声明整数数组A,并赋初值
				 
				min=max=A[0];
//				System.out.print("数组A的元素包括：");
				for(i=0;i<A.length;i++)
				{
//				System.out.print(A[i]+" ");
				if(A[i]>max)   // 判断最大值
				max=A[i];
				if(A[i]<min)   // 判断最小值
				min=A[i];
				}
//				System.out.println("\n数组的最大值是："+max); // 输出最大值
//				System.out.println("数组的最小值是："+min); // 输出最小值
				
				
				List<List> list = new ArrayList<List>();
				
				  Collections.sort(list, new Comparator<List>(){
				   public int compare(List o1, List o2) {
				    return 0;
				   }
				     });
			}
}

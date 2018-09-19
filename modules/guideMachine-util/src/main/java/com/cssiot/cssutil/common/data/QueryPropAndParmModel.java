package com.cssiot.cssutil.common.data;


import com.cssiot.cssutil.common.utils.Parameter;

import lombok.Data;

/**
 * 提取查询条件：语句，参数，和orderby
 * @ClassName: QueryPropAndParmModel
 * @Description: 
 * @author 
 * 2017年11月28日  tck 创建
 */
@Data
public class QueryPropAndParmModel {
	private String property;//语句
	
	private Parameter parameter;//参数
	
	private String orderBy;//排序
}

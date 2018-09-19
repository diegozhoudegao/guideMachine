package com.cssiot.cssutil.common.data;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 反写设定中目标表单组件情况记录Model
 * @author 
 *	2018-06-19 athena 新建
 *	2018-06-22 athena 增加反写方式暂存(用于反写方式交互暂存之用)
 *	2018-08-09 athena 增加反写条件的判断比较符和是否真实值
 */
@Data
@NoArgsConstructor
public class FieldValueModel implements Serializable{
	private static final long serialVersionUID = 2631590509760908280L;
	@ApiModelProperty(value="组件英文名")
	private String elementEnName;
	
	@ApiModelProperty(value="字段类型0字符串1数字")
	private String fieldType;
	
	@ApiModelProperty(value="组件值")
	private String elementValue;
	
	@ApiModelProperty(value="是否真实值0是1否")
	private String isReadyValue;
	
	@ApiModelProperty(value="反写条件的判断比较符")
	private String judgeSymbol;
	
	@ApiModelProperty(value="针对值组件集合的反写方式（0：累加，1：累减，2：值）")
	private String rewriteType;
	
	@ApiModelProperty(value="反写方式暂存(用于反写方式交互暂存之用)")
	private String rewriteValue;
	
	@ApiModelProperty(value="反写对应真实组件id")
	private String linkFormElementId;

	public FieldValueModel(String elementEnName, String fieldType, String elementValue,String rewriteType,String linkFormElementId,String judgeSymbol,String isReadyValue) {
		super();
		this.elementEnName = elementEnName;
		this.fieldType = fieldType;
		this.elementValue = elementValue;
		this.rewriteType=rewriteType;
		this.linkFormElementId=linkFormElementId;
		this.judgeSymbol=judgeSymbol;
		this.isReadyValue=isReadyValue;
	}
	
}
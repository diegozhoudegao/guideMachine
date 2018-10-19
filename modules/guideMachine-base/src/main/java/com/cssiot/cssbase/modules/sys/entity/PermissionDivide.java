package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限对应操作表
 * @author 
 * 	2018-10-19 athena 创建
 */

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_permissionDivide")
public class PermissionDivide extends CommonFields {
	@ApiModelProperty(value="标签")
	@Column(name="name")
	private String name;				//标签
	
	@ApiModelProperty(value="键值")
	@Column(name="keyName")
	private String keyName;				//键值
	
	@ApiModelProperty(value="所属模块")
	@Column(name="module")
	private String module;				//所属模块
	
	@ApiModelProperty(value="模块内容")
	@Column(name="content")
	private String content;            	//模块内容
	
	@ApiModelProperty(value="权重")
	@Column(name="weight")
	private int weight;					//权重
	
}
package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.sys.data.PermissionModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_permission")
public class Permission extends CommonFields{

	@ApiModelProperty(value="URL")
	@Column(name="url")
	private String url;
	
	@ApiModelProperty(value="权限名称")
	@Column(name="name")
	private String name;
	
	@ApiModelProperty(value="权限编码")
	@Column(name="number")
	private String number;

	@ApiModelProperty(value="父权限编码")
	@Column(name="parentNumber")
	private String parentNumber;
	
	@ApiModelProperty(value="级数")
	@Column(name="level")
	private Integer level;
	
	@ApiModelProperty(value="是否叶子节点")
	@Column(name="isLeaf")
	private String isLeaf;
	
	@ApiModelProperty(value="图标")
	@Column(name="icon")
	private String icon;
	
	@ApiModelProperty(value="权重")
	@Column(name="weight")
	private Integer weight;

	@ApiModelProperty(value="模块内容")
	@Column(name="content")
	private String content;
	
	public Permission(PermissionModel permission) {
		super();
		this.url = permission.getUrl();
		this.name = permission.getName();
		this.number = permission.getNumber();
		this.parentNumber = permission.getParentNumber();
		this.level = permission.getLevel();
		this.isLeaf = permission.getIsLeaf();
		this.icon = permission.getIcon();
		this.weight = permission.getWeight();
		this.content = permission.getContent();
	}
}
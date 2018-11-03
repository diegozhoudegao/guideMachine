package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.Permission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
@NoArgsConstructor
public class PermissionModel {
	
	@ApiModelProperty(value="权限id")
	private String permissionId;
	
	@ApiModelProperty(value="URL")
	private String url;
	
	@ApiModelProperty(value="权限名称")
	private String name;
	
	@ApiModelProperty(value="权限编码")
	private String number;

	@ApiModelProperty(value="父权限编码")
	private String parentNumber;
	
	@ApiModelProperty(value="级数")
	private Integer level;
	
	@ApiModelProperty(value="是否叶子节点")
	private String isLeaf;
	
	@ApiModelProperty(value="图标")
	private String icon;
	
	@ApiModelProperty(value="权重")
	private Integer weight;

	@ApiModelProperty(value="模块内容")
	private String content;

	public PermissionModel(Permission permission) {
		super();
		this.permissionId = permission.getId();
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
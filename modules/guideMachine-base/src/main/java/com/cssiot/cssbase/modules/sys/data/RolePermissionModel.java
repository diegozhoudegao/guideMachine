package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.RolePermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
@NoArgsConstructor
public class RolePermissionModel {
	
	@ApiModelProperty(value="角色权限id")
	private String rolePermissionId;
	
	@ApiModelProperty(value="权限id")
	private String permissionId;
	
	@ApiModelProperty(value="角色id")
	private String roleId;

	public RolePermissionModel(RolePermission rolePermission) {
		super();
		this.rolePermissionId = rolePermission.getId();
		this.permissionId = rolePermission.getPermissionId();
		this.roleId = rolePermission.getRoleId();
	}

}
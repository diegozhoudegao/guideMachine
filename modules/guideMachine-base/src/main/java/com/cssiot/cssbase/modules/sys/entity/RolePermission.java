package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssbase.modules.sys.data.RolePermissionModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色权限表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_rolePermission")
public class RolePermission extends CommonFields{

	@ApiModelProperty(value="权限id")
	@Column(name="permissionId")
	private String permissionId;
	
	@ApiModelProperty(value="角色id")
	@Column(name="roleId")
	private String roleId;

	public RolePermission(RolePermissionModel rolePermission) {
		super();
		this.permissionId = rolePermission.getPermissionId();
		this.roleId = rolePermission.getRoleId();
	}

}
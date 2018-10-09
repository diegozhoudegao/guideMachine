package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class RoleModel {
	
	@ApiModelProperty(value="角色id")
	private String roleId;
	
	@ApiModelProperty(value="角色名称")
	private String roleName;

	public RoleModel(Role role) {
		super();
		this.roleId = role.getId();
		this.roleName = role.getRoleName();
	}

}
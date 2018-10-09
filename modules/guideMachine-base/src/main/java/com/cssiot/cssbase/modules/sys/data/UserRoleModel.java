package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工角色表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class UserRoleModel {
	
	@ApiModelProperty(value="员工角色id")
	private String userRoleId;
	
	@ApiModelProperty(value="员工id")
	private String userId;
	
	@ApiModelProperty(value="角色id")
	private String roleId;

	public UserRoleModel(UserRole userRole) {
		super();
		this.userRoleId = userRole.getId();
		this.userId = userRole.getUserId();
		this.roleId = userRole.getRoleId();
	}
	
}
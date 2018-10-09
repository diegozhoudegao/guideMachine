package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.sys.data.UserRoleModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 员工角色表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_userRole")
public class UserRole extends CommonFields{

	@ApiModelProperty(value="员工id")
	@Column(name="userId")
	private String userId;
	
	@ApiModelProperty(value="角色id")
	@Column(name="roleId")
	private String roleId;

	public UserRole(UserRoleModel userRole) {
		super();
		this.userId = userRole.getUserId();
		this.roleId = userRole.getRoleId();
	}

}
package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工权限视图
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@Table(name = "v_sys_userPermission")
public class UserPermission {

	@Id
	@ApiModelProperty(value="员工权限id")
	@Column(name="id")
	private String id;
	
	@ApiModelProperty(value="员工id")
	@Column(name="userId")
	private String userId;
	
	@ApiModelProperty(value="权限id")
	@Column(name="permissionId")
	private String permissionId;
	
	@ApiModelProperty(value="标识")
	@Column(name="flag")
	private String flag;
	
}
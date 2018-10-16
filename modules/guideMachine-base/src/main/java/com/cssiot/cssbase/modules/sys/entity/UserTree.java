package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工树视图
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@Table(name = "v_sys_userTree")
public class UserTree {

	@Id
	@ApiModelProperty(value="员工树id")
	@Column(name="id")
	private String id;
	
	@ApiModelProperty(value="员工id")
	@Column(name="userId")
	private String userId;
	
	@ApiModelProperty(value="权限id")
	@ManyToOne
	@JoinColumn(name = "permissionId_",referencedColumnName="id_")
	private Permission permission;
	
	@ApiModelProperty(value="标识")
	@Column(name="flag")
	private String flag;
	
}
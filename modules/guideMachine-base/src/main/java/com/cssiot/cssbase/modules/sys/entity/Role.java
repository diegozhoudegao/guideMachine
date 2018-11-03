package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssbase.modules.sys.data.RoleModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 角色表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_role")
public class Role extends CommonFields{

	@ApiModelProperty(value="角色名称")
	@Column(name="roleName")
	private String roleName;
	
	public Role(RoleModel role) {
		super();
		this.roleName = role.getRoleName();
	}

}
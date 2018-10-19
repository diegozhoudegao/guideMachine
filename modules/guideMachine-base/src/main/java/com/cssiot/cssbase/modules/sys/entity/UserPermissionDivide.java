package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户权限操作视图
 * @author
 *	2018-10-19 athena 创建
 */
@Entity
@Data
@Table(name = "v_sys_userPermissionDivide")
public class UserPermissionDivide {

	@Id
	@ApiModelProperty(value="员工权限id")
	@Column(name="id")
	private String id;
	
	@ApiModelProperty(value="员工id")
	@Column(name="userId")
	private String userId;
	
	@ApiModelProperty(value="角色id")
	@Column(name="roleId")
	private String roleId;
	
	@ApiModelProperty(value="权限id")
	@Column(name="permissionId")
	private String permissionId;
	
	@ApiModelProperty(value="模块内容(dataDictionary的content)")
	@Column(name="content")
	private String content;
	
	@ApiModelProperty(value="键值Id(dataDictionary的ID)")
	@Column(name="keyNameId")
	private String keyNameId;
	
	@ApiModelProperty(value="键值(dataDictionary的keyName)")
	@Column(name="keyName")
	private String keyName;
}
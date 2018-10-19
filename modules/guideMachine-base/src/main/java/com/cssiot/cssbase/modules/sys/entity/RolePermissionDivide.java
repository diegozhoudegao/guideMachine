package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色权限操作表
 * @author 
 * 	2018-10-19 athena 创建
 */

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_rolePermissionDivide")
public class RolePermissionDivide extends CommonFields {
	
	@ApiModelProperty(value="角色权限Id")
	@Column(name="rolePermissionId")
	private String rolePermissionId;	//角色权限Id
	
	@ApiModelProperty(value="键值Id")
	@Column(name="keyNameId")
	private String keyNameId;			//键值Id(dataDictionary数据字典的ID)
	
	@ApiModelProperty(value="标签")
	@Column(name="name")
	private String name;				//标签
	
}

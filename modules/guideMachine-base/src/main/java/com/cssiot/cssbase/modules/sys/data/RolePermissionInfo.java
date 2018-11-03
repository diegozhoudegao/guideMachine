package com.cssiot.cssbase.modules.sys.data;

import java.util.List;

import lombok.Data;

@Data
public class RolePermissionInfo {

	private String roleId;
	private String roleName;
	private List<PermissionCheckedModel> permissionList;
}

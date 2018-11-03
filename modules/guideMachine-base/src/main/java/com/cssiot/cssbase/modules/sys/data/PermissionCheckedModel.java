package com.cssiot.cssbase.modules.sys.data;

import lombok.Data;

@Data
public class PermissionCheckedModel {
	private String id;
	private String pId;
	private String name;
	private String permissionId;
	private boolean checked;
}

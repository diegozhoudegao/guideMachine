package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.RolePermissionDao;
import com.cssiot.cssbase.modules.sys.entity.RolePermission;
import com.cssiot.cssbase.modules.sys.service.RolePermissionService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 角色权限表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService{
	
	@Override
	public BaseDao<RolePermission> getBaseDao() {
		return rolePermissionDao;
	}
	
	@Autowired
	private RolePermissionDao rolePermissionDao;
	
	
}
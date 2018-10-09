package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.PermissionDao;
import com.cssiot.cssbase.modules.sys.entity.Permission;
import com.cssiot.cssbase.modules.sys.service.PermissionService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 权限表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService{
	
	@Override
	public BaseDao<Permission> getBaseDao() {
		return permissionDao;
	}
	
	@Autowired
	private PermissionDao permissionDao;
	
	
}
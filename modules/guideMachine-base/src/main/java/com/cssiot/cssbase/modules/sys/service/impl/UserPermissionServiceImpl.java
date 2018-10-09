package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserPermissionDao;
import com.cssiot.cssbase.modules.sys.entity.UserPermission;
import com.cssiot.cssbase.modules.sys.service.UserPermissionService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 员工权限视图ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserPermissionServiceImpl extends BaseServiceImpl<UserPermission> implements UserPermissionService{
	
	@Override
	public BaseDao<UserPermission> getBaseDao() {
		return userPermissionDao;
	}
	
	@Autowired
	private UserPermissionDao userPermissionDao;
	
	
}
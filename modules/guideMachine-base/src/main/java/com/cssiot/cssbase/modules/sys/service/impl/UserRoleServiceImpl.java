package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserRoleDao;
import com.cssiot.cssbase.modules.sys.entity.UserRole;
import com.cssiot.cssbase.modules.sys.service.UserRoleService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 员工角色表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService{
	
	@Override
	public BaseDao<UserRole> getBaseDao() {
		return userRoleDao;
	}
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	
}
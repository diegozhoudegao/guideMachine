package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.RoleDao;
import com.cssiot.cssbase.modules.sys.entity.Role;
import com.cssiot.cssbase.modules.sys.service.RoleService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 角色表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Override
	public BaseDao<Role> getBaseDao() {
		return roleDao;
	}
	
	@Autowired
	private RoleDao roleDao;
	
	
}
package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserPermissionDivideDao;
import com.cssiot.cssbase.modules.sys.entity.UserPermissionDivide;
import com.cssiot.cssbase.modules.sys.service.UserPermissionDivideService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 权限操作表ServiceImpl
 * @author
 *	2018-10-24 athena 创建
 */
@Service
@Transactional
public class UserPermissionDivideServiceImpl extends BaseServiceImpl<UserPermissionDivide> implements UserPermissionDivideService{
	
	@Override
	public BaseDao<UserPermissionDivide> getBaseDao() {
		return userPermissionDivideDao;
	}
	
	@Autowired
	private UserPermissionDivideDao userPermissionDivideDao;
	
	
}
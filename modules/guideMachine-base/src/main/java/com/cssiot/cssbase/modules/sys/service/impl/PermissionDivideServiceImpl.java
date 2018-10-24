package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.PermissionDivideDao;
import com.cssiot.cssbase.modules.sys.entity.PermissionDivide;
import com.cssiot.cssbase.modules.sys.service.PermissionDivideService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 权限操作表ServiceImpl
 * @author
 *	2018-10-24 athena 创建
 */
@Service
@Transactional
public class PermissionDivideServiceImpl extends BaseServiceImpl<PermissionDivide> implements PermissionDivideService{
	
	@Override
	public BaseDao<PermissionDivide> getBaseDao() {
		return permissionDivideDao;
	}
	
	@Autowired
	private PermissionDivideDao permissionDivideDao;
	
	
}
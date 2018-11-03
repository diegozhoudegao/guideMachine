package com.cssiot.cssbase.modules.sys.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssbase.modules.sys.dao.RolePermissionDivideDao;
import com.cssiot.cssbase.modules.sys.entity.RolePermissionDivide;
import com.cssiot.cssbase.modules.sys.service.RolePermissionDivideService;

/**
 *  
 * @description Service实现类
 * @author 
 * 	2018-11-01 Diego.zhou 新建
 *
 */

@Service("rolePermissionDivideService")
@Transactional
public class RolePermissionDivideServiceImpl extends BaseServiceImpl<RolePermissionDivide>  implements RolePermissionDivideService {
	@Resource
	private RolePermissionDivideDao rolePermissionDivideDao;

	@Override
	public BaseDao<RolePermissionDivide> getBaseDao() {
		return rolePermissionDivideDao;
	}
	

}

package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserCityManageDao;
import com.cssiot.cssbase.modules.sys.entity.UserCityManage;
import com.cssiot.cssbase.modules.sys.service.UserCityManageService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 员工管理城市表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserCityManageServiceImpl extends BaseServiceImpl<UserCityManage> implements UserCityManageService{
	
	@Override
	public BaseDao<UserCityManage> getBaseDao() {
		return userCityManageDao;
	}
	
	@Autowired
	private UserCityManageDao userCityManageDao;
	
	
}
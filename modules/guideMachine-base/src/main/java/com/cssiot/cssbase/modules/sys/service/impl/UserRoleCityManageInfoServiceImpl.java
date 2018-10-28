package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cssiot.cssbase.modules.sys.dao.UserRoleCityManageInfoDao;
import com.cssiot.cssbase.modules.sys.entity.UserRoleCityManageInfo;
import com.cssiot.cssbase.modules.sys.service.UserRoleCityManageInfoService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 员工角色管理城市信息
 * @author
 *	2018-10-28 Diego.zhou 创建
 */
@Service
@Transactional
public class UserRoleCityManageInfoServiceImpl extends BaseServiceImpl<UserRoleCityManageInfo> implements UserRoleCityManageInfoService{
	
	@Override
	public BaseDao<UserRoleCityManageInfo> getBaseDao() {
		return userRoleCityManageInfoDao;
	}
	
	@Autowired
	private UserRoleCityManageInfoDao userRoleCityManageInfoDao;
	
	
}
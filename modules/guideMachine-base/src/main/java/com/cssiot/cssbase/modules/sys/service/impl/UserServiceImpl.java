package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserDao;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 员工表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Override
	public BaseDao<User> getBaseDao() {
		return userDao;
	}
	
	@Autowired
	private UserDao userDao;
	
	
}
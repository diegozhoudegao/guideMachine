package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cssiot.cssbase.modules.sys.dao.UserDao;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户ServiceImpl
 * @author
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public BaseDao<User> getBaseDao() {
		return userDao;
	}
	
	
	public Object doSaveUser() {
		User user = new User();
		user.setName("王菲王菲");
		user.setState("0");
		userDao.save(user);
		return null;
	}
	
	public Object doGetUserByName(String userName) {
		User user = userDao.getByHql("from User where name='测试K'");
		return user;
	}
	
	
}

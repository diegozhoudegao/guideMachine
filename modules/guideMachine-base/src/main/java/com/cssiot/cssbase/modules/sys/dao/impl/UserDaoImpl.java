package com.cssiot.cssbase.modules.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.cssiot.cssutil.common.dao.impl.BaseDaoImpl;
import com.cssiot.cssbase.modules.sys.dao.UserDao;
import com.cssiot.cssbase.modules.sys.entity.User;

/**
 *  
 * @description Dao实现类
 * @author athena
 * @time 2016-02-04
 *
 */

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {


}

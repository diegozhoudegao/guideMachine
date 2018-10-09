package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.VisitorsDao;
import com.cssiot.cssbase.modules.sys.entity.Visitors;
import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 游客表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class VisitorsServiceImpl extends BaseServiceImpl<Visitors> implements VisitorsService{
	
	@Override
	public BaseDao<Visitors> getBaseDao() {
		return visitorsDao;
	}
	
	@Autowired
	private VisitorsDao visitorsDao;
	
	
}
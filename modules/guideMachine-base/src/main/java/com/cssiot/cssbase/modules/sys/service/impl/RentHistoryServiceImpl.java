package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.RentHistoryDao;
import com.cssiot.cssbase.modules.sys.entity.RentHistory;
import com.cssiot.cssbase.modules.sys.service.RentHistoryService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 租借历史表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class RentHistoryServiceImpl extends BaseServiceImpl<RentHistory> implements RentHistoryService{
	
	@Override
	public BaseDao<RentHistory> getBaseDao() {
		return rentHistoryDao;
	}
	
	@Autowired
	private RentHistoryDao rentHistoryDao;
	
	
}
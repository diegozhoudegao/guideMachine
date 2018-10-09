package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.GuideMachineDao;
import com.cssiot.cssbase.modules.base.entity.GuideMachine;
import com.cssiot.cssbase.modules.base.service.GuideMachineService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 导游机表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class GuideMachineServiceImpl extends BaseServiceImpl<GuideMachine> implements GuideMachineService{
	
	@Override
	public BaseDao<GuideMachine> getBaseDao() {
		return guideMachineDao;
	}
	
	@Autowired
	private GuideMachineDao guideMachineDao;
	
	
}
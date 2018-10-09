package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.CabinetDao;
import com.cssiot.cssbase.modules.base.entity.Cabinet;
import com.cssiot.cssbase.modules.base.service.CabinetService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 机柜表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class CabinetServiceImpl extends BaseServiceImpl<Cabinet> implements CabinetService{
	
	@Override
	public BaseDao<Cabinet> getBaseDao() {
		return cabinetDao;
	}
	
	@Autowired
	private CabinetDao cabinetDao;
	
	
}
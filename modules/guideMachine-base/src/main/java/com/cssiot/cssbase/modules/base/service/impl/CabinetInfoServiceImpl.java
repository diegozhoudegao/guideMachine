package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.CabinetInfoDao;
import com.cssiot.cssbase.modules.base.entity.CabinetInfo;
import com.cssiot.cssbase.modules.base.service.CabinetInfoService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 机柜视图ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class CabinetInfoServiceImpl extends BaseServiceImpl<CabinetInfo> implements CabinetInfoService{
	
	@Override
	public BaseDao<CabinetInfo> getBaseDao() {
		return cabinetInfoDao;
	}
	
	@Autowired
	private CabinetInfoDao cabinetInfoDao;
	
	
}
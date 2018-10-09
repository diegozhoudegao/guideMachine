package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.ScenicSpotEntranceDao;
import com.cssiot.cssbase.modules.base.entity.ScenicSpotEntrance;
import com.cssiot.cssbase.modules.base.service.ScenicSpotEntranceService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 景点出入口表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class ScenicSpotEntranceServiceImpl extends BaseServiceImpl<ScenicSpotEntrance> implements ScenicSpotEntranceService{
	
	@Override
	public BaseDao<ScenicSpotEntrance> getBaseDao() {
		return scenicSpotEntranceDao;
	}
	
	@Autowired
	private ScenicSpotEntranceDao scenicSpotEntranceDao;
	
	
}
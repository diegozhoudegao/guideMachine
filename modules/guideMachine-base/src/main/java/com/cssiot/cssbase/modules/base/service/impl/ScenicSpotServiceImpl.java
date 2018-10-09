package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.ScenicSpotDao;
import com.cssiot.cssbase.modules.base.entity.ScenicSpot;
import com.cssiot.cssbase.modules.base.service.ScenicSpotService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 景点主表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class ScenicSpotServiceImpl extends BaseServiceImpl<ScenicSpot> implements ScenicSpotService{
	
	@Override
	public BaseDao<ScenicSpot> getBaseDao() {
		return scenicSpotDao;
	}
	
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	
	
}
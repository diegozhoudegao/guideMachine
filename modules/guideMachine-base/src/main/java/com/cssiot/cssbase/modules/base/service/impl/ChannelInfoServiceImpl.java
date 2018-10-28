package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.ChannelInfoDao;
import com.cssiot.cssbase.modules.base.entity.ChannelInfo;
import com.cssiot.cssbase.modules.base.service.ChannelInfoService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 仓道导游机信息ServiceImpl
 * @author
 *	2018-10-16 athena 创建
 */
@Service
@Transactional
public class ChannelInfoServiceImpl extends BaseServiceImpl<ChannelInfo> implements ChannelInfoService{
	
	@Override
	public BaseDao<ChannelInfo> getBaseDao() {
		return channelInfoDao;
	}
	
	@Autowired
	private ChannelInfoDao channelInfoDao;
	
	
}
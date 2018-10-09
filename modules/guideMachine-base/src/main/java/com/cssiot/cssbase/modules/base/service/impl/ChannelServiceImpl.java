package com.cssiot.cssbase.modules.base.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.base.dao.ChannelDao;
import com.cssiot.cssbase.modules.base.entity.Channel;
import com.cssiot.cssbase.modules.base.service.ChannelService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 仓道表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class ChannelServiceImpl extends BaseServiceImpl<Channel> implements ChannelService{
	
	@Override
	public BaseDao<Channel> getBaseDao() {
		return channelDao;
	}
	
	@Autowired
	private ChannelDao channelDao;
	
	
}
package com.cssiot.cssbase.modules.biz.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cssiot.cssbase.modules.biz.dao.RentOrderDao;
import com.cssiot.cssbase.modules.biz.entity.RentOrder;
import com.cssiot.cssbase.modules.biz.service.RentOrderService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 租借订单表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class RentOrderServiceImpl extends BaseServiceImpl<RentOrder> implements RentOrderService{
	
	@Override
	public BaseDao<RentOrder> getBaseDao() {
		return rentOrderDao;
	}
	
	@Autowired
	private RentOrderDao rentOrderDao;
	
	
}
package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.TransactionDetailDao;
import com.cssiot.cssbase.modules.sys.entity.TransactionDetail;
import com.cssiot.cssbase.modules.sys.service.TransactionDetailService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 交易明细表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class TransactionDetailServiceImpl extends BaseServiceImpl<TransactionDetail> implements TransactionDetailService{
	
	@Override
	public BaseDao<TransactionDetail> getBaseDao() {
		return transactionDetailDao;
	}
	
	@Autowired
	private TransactionDetailDao transactionDetailDao;
	
	
}
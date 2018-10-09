package com.cssiot.cssbase.modules.sys.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.CompanyDao;
import com.cssiot.cssbase.modules.sys.entity.Company;
import com.cssiot.cssbase.modules.sys.service.CompanyService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;

/**
 * 公司表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService{
	
	@Override
	public BaseDao<Company> getBaseDao() {
		return companyDao;
	}
	
	@Autowired
	private CompanyDao companyDao;
	
	
}
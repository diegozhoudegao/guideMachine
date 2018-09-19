package com.cssiot.cssutil.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.service.BaseService;
import com.cssiot.cssutil.common.utils.Page;
import com.cssiot.cssutil.common.utils.Parameter;

/**
 *  
 * @description Service基类实现类
 * @author athena
 * @time 2016-02-04
 *
 */

public abstract class BaseServiceImpl<E> implements BaseService<E> {
	
	public abstract BaseDao<E> getBaseDao();
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(E e){
		getBaseDao().save(e);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(List<E> entityList){
		getBaseDao().save(entityList);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(E e) throws Exception{
		getBaseDao().delete(e);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteByIds(String[] ids)throws Exception{
		getBaseDao().deleteByIds(ids);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public boolean isExist(String filed, String value) throws Exception{
		return getBaseDao().isExist(filed, value);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public boolean isExist(String filter) throws Exception{
		return getBaseDao().isExist(filter);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public E get(Serializable id) throws Exception{
		return getBaseDao().get(id);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<E> findAll() throws Exception{
		return getBaseDao().findAll();
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<E> findByHql(String hqlString) throws Exception{
		return getBaseDao().findByHql(hqlString);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> findByHqlMap(String hqlString) throws Exception{
		return getBaseDao().findByHqlMap(hqlString);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> executeProcedure(String names, List params) throws Exception{
		return getBaseDao().executeProcedure(names, params);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> Page<E> find(Page<E> page, String hqlString){
		return getBaseDao().find(page, hqlString);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> Page<E> find(Page<E> page, String hqlString, Parameter parameter){
		return getBaseDao().find(page, hqlString, parameter);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> List<E> find(String hqlString){
		return getBaseDao().find(hqlString);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> List<E> find(String hqlString, Parameter parameter){
		return getBaseDao().find(hqlString, parameter);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public E getByHql(String hqlString){
		return getBaseDao().getByHql(hqlString);
	}
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public E getByHql(String hqlString, Parameter parameter){
		return getBaseDao().getByHql(hqlString, parameter);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> Page<E> findBySql(Page<E> page, String sqlString){
		return getBaseDao().findBySql(page, sqlString);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter){
		return getBaseDao().findBySql(page, sqlString, parameter);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Class<?> resultClass){
		return getBaseDao().findBySql(page, sqlString, resultClass);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter, Class<?> resultClass){
		return getBaseDao().findBySql(page, sqlString, parameter, resultClass);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> List<E> findBySql(String sqlString){
		return getBaseDao().findBySql(sqlString);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> List<E> findBySql(String sqlString, Parameter parameter){
		return getBaseDao().findBySql(sqlString, parameter);
	}
	
	@SuppressWarnings("hiding")
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	public <E> List<E> findBySql(String sqlString, Parameter parameter, Class<?> resultClass){
		return getBaseDao().findBySql(sqlString, parameter, resultClass);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int updateBySql(String sqlString, Parameter parameter){
		return getBaseDao().updateBySql(sqlString, parameter);
	}
	
}

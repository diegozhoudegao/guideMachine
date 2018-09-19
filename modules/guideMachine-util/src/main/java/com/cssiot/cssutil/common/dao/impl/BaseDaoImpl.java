package com.cssiot.cssutil.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.utils.Global;
import com.cssiot.cssutil.common.utils.Page;
import com.cssiot.cssutil.common.utils.Parameter;
import com.cssiot.cssutil.common.utils.Reflections;
import com.cssiot.cssutil.common.utils.StringUtil;

/**
 *  
 * @description Dao基类实现类
 * @author athena
 * @time 2016-02-04
 *
 */

public abstract class BaseDaoImpl<E> extends HibernateDaoSupport implements BaseDao<E>{
	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	private Class<E> entityClass;
	public Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public BaseDaoImpl() {
		this.entityClass = Reflections.getClassGenricType(getClass());
	}

	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 获取 Session
	 */
	public Session getSession(){  
	  return getSessionFactory().getCurrentSession();
	}

	/**
	 * 强制与数据库同步
	 */
	public void flush(){
		getSession().flush();
	}

	/**
	 * 清除缓存数据
	 */
	public void clear(){ 
		getSession().clear();
	}
	
	// -------------HibernateTemplate---------------
	
	/**
	 * 保存实体
	 */
	public void save(E e) {
		try {
			// 获取实体ID
			Object id = null;
			for (Method method : e.getClass().getMethods()){
				Id idAnn = method.getAnnotation(Id.class);
				if (idAnn != null){
					id = method.invoke(e);
					break;
				}
			}

			// 插入前执行方法
			if (StringUtil.isBlank((String)id)){
				for (Method method : e.getClass().getMethods()){
					PrePersist pp = method.getAnnotation(PrePersist.class);
					if (pp != null){
						method.invoke(e);
						break;
					}
				}
			}
			// 更新前执行方法
			else{
				for (Method method : e.getClass().getMethods()){
					PreUpdate pu = method.getAnnotation(PreUpdate.class);
					if (pu != null){
						method.invoke(e);
						break;
					}
				}
			}
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		this.getHibernateTemplate().saveOrUpdate(e);
	}
	
	/**
	 * 批量保存实体
	 */
	public void save(List<E> entityList) {
		for (E entity : entityList){
			save(entity);
		}
	}

	/**
	* 删除实体
	*/
	public void delete(E e) throws ConstraintViolationException,Exception {
		this.getHibernateTemplate().delete(e);
	}
	
	/**
	* 批量删除实体
	*/
	public void deleteByIds(String[] ids)throws ConstraintViolationException,Exception {
		Collection<E> collection=new HashSet<E>();
		for (String id : ids) {
			collection.add(get(id));
		}
		this.getHibernateTemplate().deleteAll(collection);
	}
	
	/**
	 * 指定某属性以及对应值校验其唯一性
	 * @param filed 属性
	 * @param value 属性值
	 * @return true表示已经存在；false表示不存在
	 * @throws Exception
	 */
	public boolean isExist(String filed, String value) throws Exception {
		String hql = "from " + entityClass.getSimpleName()
				+ " where "+filed+"='"+value+"'";
		return findByHql(hql).size()==0?false:true;
	}
	
	/**
	 * 根据传入的过滤条件判断是否存在已存在
	 * @param filter
	 * @return true表示已经存在；false表示不存在
	 * @throws Exception
	 */
	public boolean isExist(String filter) throws Exception {
		String hql="from "+entityClass.getSimpleName()+" where "+filter;
		return findByHql(hql).size()==0?false:true;
	} 
	
	/**
	 * 获取泛型对象实体
	 * @param id 主键
	 * @param isLock 是否锁定
	 * @return 泛型对象
	 * @throws Exception
	 */
	public E get(Serializable id) throws Exception {
		E e = this.getHibernateTemplate().get(entityClass,id);
		return e;
	}
	
	/**
	 *  获取泛型对象所有数据集合
	 * @return
	 * @throws Exception
	 */
	public List<E> findAll() throws Exception{
		String hql = "from " + entityClass.getSimpleName()+" "+Global.getConfig("defaultOrder","wagon");
		return findByHql(hql);
	}

	/**
	 * 根据指定的hql获取泛型对象集合
	 * @param hqlString 查询hql
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<E> findByHql(String hqlString) throws Exception{
		return (List<E>) this.getHibernateTemplate().find(hqlString);
		
	}
	
	/**
	 * 根据hql获取对应的数据集合
	 * @param hql  hql中使用new map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findByHqlMap(String hqlString) throws Exception{
		return (List<Map<String, Object>>) getHibernateTemplate().find(hqlString);
	}
	
	/**
	 * 执行存储过程
	 * @param names
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "all" })
	public List<Map> executeProcedure(final String names, final List params) throws Exception {
		return   (List<Map>) getHibernateTemplate().execute(new org.springframework.orm.hibernate5.HibernateCallback() {
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				SQLQuery query=null;
				String parstring="";
				if (params != null && params.size()> 0) {
					for (int i = 0; i < params.size(); i++) {
						if(null==parstring || "".equals(parstring)){
							parstring="(?";
						}else{
							parstring=parstring+",?";
						}
					}
				}
				if(null!=parstring && !"".equals(parstring)){
					parstring=parstring+")";
				}
				query = session.createSQLQuery("{call "+names+parstring+"}"); 
				if (params != null && params.size()> 0) {
					for (int i = 0, j = params.size(); i < j; i++) {
						query.setParameter(i, params.get(i));
					}
				}
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				return  query.list();
			}
		});
	}

	// -------------- HQL Query --------------
	
	/**
	 * HQL 分页查询
	 * @param page
	 * @param hqlString
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> Page<E> find(Page<E> page, String hqlString){
    	return find(page, hqlString, null);
    }
    
	/**
	 * HQL 分页查询
	 * @param page
	 * @param hqlString
	 * @param parameter
	 * @return
	 */
    @SuppressWarnings({ "unchecked", "hiding", "static-access" })
	public <E> Page<E> find(Page<E> page, String hqlString, Parameter parameter){
		// get count
    	if (!page.isDisabled() && !page.isNotCount()){
	        String countQlString = "select count(*) " + removeSelect(removeOrders(hqlString));  
	        Query query = createQuery(countQlString, parameter);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	page.setCount(Long.valueOf(list.get(0).toString()));
	        }else{
	        	page.setCount(list.size());
	        }
			if (page.getCount() < 1) {
				return page;
			}
			page.setAllPage(page.countTotalPage(page.getPageSize(), (int)page.getCount()));
    	}
    	if(page.getPageSize()*(page.getPageNo()-1)>=page.getCount()){
    		page.setList(new ArrayList<E>());
    		return page;
    	}
    	// order by
    	String hql = hqlString;
		if (StringUtil.isNotBlank(page.getOrderBy())){
			hql += " order by " + page.getOrderBy();
		}
        Query query = createQuery(hql, parameter); 
    	// set page
        if (!page.isDisabled()){
	        query.setFirstResult(page.getFirstResult());
	        query.setMaxResults(page.getMaxResults()); 
        }
        page.setList(query.list());
		return page;
    }
    

    /**
	 * HQL 查询
	 * @param hqlString
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> List<E> find(String hqlString){
		return find(hqlString, null);
	}
    
    /**
	 * HQL 查询
	 * @param hqlString
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <E> List<E> find(String hqlString, Parameter parameter){
		Query query = createQuery(hqlString, parameter);
		return query.list();
	}
	
	/**
	 * 获取实体
	 * @param hqlString
	 * @return
	 */
	public E getByHql(String hqlString){
		return getByHql(hqlString, null);
	}
	
	/**
	 * 获取实体
	 * @param hqlString
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getByHql(String hqlString, Parameter parameter){
		Query query = createQuery(hqlString, parameter);
		return (E)query.uniqueResult();
	}
	
	/**
	 * 创建 QL 查询对象
	 * @param hqlString
	 * @param parameter
	 * @return
	 */
	public Query createQuery(String hqlString, Parameter parameter){
		Query query = getSession().createQuery(hqlString);
		setParameter(query, parameter);
		return query;
	}
	
	// -------------- SQL Query --------------

    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> Page<E> findBySql(Page<E> page, String sqlString){
    	return findBySql(page, sqlString, null, null);
    }

    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter){
    	return findBySql(page, sqlString, parameter, null);
    }

    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @param resultClass
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Class<?> resultClass){
    	return findBySql(page, sqlString, null, resultClass);
    }
    
    /**
	 * SQL 分页查询
	 * @param page
	 * @param sqlString
	 * @param resultClass
	 * @param parameter
	 * @return
	 */
    @SuppressWarnings({ "unchecked", "hiding", "static-access" })
	public <E> Page<E> findBySql(Page<E> page, String sqlString, Parameter parameter, Class<?> resultClass){
		// get count
    	if (!page.isDisabled() && !page.isNotCount()){
	        String countSqlString = "select count(*) " + removeSelect(removeOrders(sqlString));  
	        Query query = createSqlQuery(countSqlString, parameter);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	page.setCount(Long.valueOf(list.get(0).toString()));
	        }else{
	        	page.setCount(list.size());
	        }
			if (page.getCount() < 1) {
				return page;
			}
			page.setAllPage(page.countTotalPage(page.getPageSize(), (int)page.getCount()));
    	}
    	// order by
    	String sql = sqlString;
		if (StringUtil.isNotBlank(page.getOrderBy())){
			sql += " order by " + page.getOrderBy();
		}
        SQLQuery query = createSqlQuery(sql, parameter); 
		// set page
        if (!page.isDisabled()){
	        query.setFirstResult(page.getFirstResult());
	        query.setMaxResults(page.getMaxResults()); 
        }
        setResultTransformer(query, resultClass);
        page.setList(query.list());
		return page;
    }

	/**
	 * SQL 查询
	 * @param sqlString
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> List<E> findBySql(String sqlString){
		return findBySql(sqlString, null, null);
	}
	
	/**
	 * SQL 查询
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> List<E> findBySql(String sqlString, Parameter parameter){
		return findBySql(sqlString, parameter, null);
	}
	
	/**
	 * SQL 查询
	 * @param sqlString
	 * @param resultClass
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <E> List<E> findBySql(String sqlString, Parameter parameter, Class<?> resultClass){
		SQLQuery query = createSqlQuery(sqlString, parameter);
		setResultTransformer(query, resultClass);
		return query.list();
	}
	
	/**
	 * SQL 更新
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public int updateBySql(String sqlString, Parameter parameter){
		return createSqlQuery(sqlString, parameter).executeUpdate();
	}
	
	/**
	 * 创建 SQL 查询对象
	 * @param sqlString
	 * @param parameter
	 * @return
	 */
	public SQLQuery createSqlQuery(String sqlString, Parameter parameter){
		SQLQuery query = getSession().createSQLQuery(sqlString);
		setParameter(query, parameter);
		return query;
	}
	
	// -------------- Query Tools --------------

	/**
	 * 设置查询结果类型
	 * @param query
	 * @param resultClass
	 */
	private void setResultTransformer(SQLQuery query, Class<?> resultClass){
		if (resultClass != null){
			if (resultClass == Map.class){
				query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			}else if (resultClass == List.class){
				query.setResultTransformer(Transformers.TO_LIST);
			}else{
				query.addEntity(resultClass);
			}
		}
	}
	
	/**
	 * 设置查询参数
	 * @param query
	 * @param parameter
	 */
	private void setParameter(Query query, Parameter parameter){
		if (parameter != null) {
            Set<String> keySet = parameter.keySet();
            for (String string : keySet) {
                Object value = parameter.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(value instanceof Collection<?>){
                    query.setParameterList(string, (Collection<?>)value);
                }else if(value instanceof Object[]){
                    query.setParameterList(string, (Object[])value);
                }else{
                    query.setParameter(string, value);
                }
            }
        }
	}
	
    /** 
     * 去除hqlString的select子句。 
     * @param hqlString
     * @return 
     */  
    private String removeSelect(String hqlString){  
        int beginPos = hqlString.toLowerCase().indexOf("from");  
        return hqlString.substring(beginPos);  
    }  
      
    /** 
     * 去除hql的orderBy子句。 
     * @param hqlString
     * @return 
     */  
    private String removeOrders(String hqlString) {  
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(hqlString);  
        StringBuffer sb = new StringBuffer();  
        while (m.find()) {  
            m.appendReplacement(sb, "");  
        }
        m.appendTail(sb);
        return sb.toString();  
    }
    
}

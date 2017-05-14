/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014年4月21日
 * Id: HibernateService.java,v 1.0 2014年4月21日 下午2:48:07 yehao
 */
package com.zhiyun.bigdata.framework.ssh;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.zhiyun.bigdata.framework.utils.Page;

/**
 * @ClassName HibernateService
 * @author yehao
 * @date Nov 17, 2011 10:38:56 AM
 * @Description hibernate查询方法的一些简单的封装类
 */
@Component("hibernateService")
public class HibernateService {
	
protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @return
	 * @Description 获得Hibernate session对象
	 */
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 * @Description 根据hql和参数按顺序绑定并创建Query对象
	 */
	public Query getQuery(String hql , Object... values){
		Assert.hasText(hql, "hql不能为空");
		Query query = getSession().createQuery(hql);
		if(values != null){
			for (int i = 0; i < values.length; i++) {
				if(values[i] instanceof Date){
					query.setTimestamp(i, (Date)values[i]);			
				} 
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 * @Description 根据sql和参数按顺序绑定并创建Query对象
	 */
	public Query getSQLQuery(String hql , Object... values){
		Assert.hasText(hql, "sql不能为空");
		Query query = getSession().createSQLQuery(hql);
		if(values != null){
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @Description 执行HQL语句
	 */
	public void excuteHQL(String hql , Object... values){
		Assert.hasText(hql, "hql不能为空");
		getQuery(hql,values).executeUpdate();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param sql
	 * @Description 执行SQL语句
	 */
	public void excuteSQL(String sql){
		Assert.hasText(sql, "sql不能为空");
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 * @Description 查询分页（通过hql）
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql,Object... values){
		Assert.hasText(hql, "hql不能为空");
		return getQuery(hql,values).list();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 * @Description 查询分页（通过sql）
	 */
	@SuppressWarnings("unchecked")
	public List findSQL(String sql,Object... values){
		Assert.hasText(sql, "hql不能为空");
		return getSQLQuery(sql,values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param page
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 * @Description 获得关于list的分页对象
	 */
	public Page findListPage(Page page , String hql ,Object... values){
		Assert.hasText(hql, "hql不能为空");
		long totalCount = countHQLResult(hql,values);//获得总数
		page.setTotalCount(totalCount);
		page.setTotalPages((int)page.getTotalPages());//设置总页数大小
		Query query = getQuery(hql,values);
		query = setPageParameterToQuery(query, page);
		page.setResult(query.list());
		return page;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param page
	 * @param hql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return 键值对的分页对象
	 * @Description 获得关于键值对的分页对象
	 */
	@SuppressWarnings("unchecked")
	public Page findMapPage(Page page , String hql ,Object... values){
		Assert.hasText(hql, "hql不能为空");
		long totalCount = countHQLResult(hql,values);//获得总数
		page.setTotalCount(totalCount);
		page.setTotalPages((int)page.getTotalPages());//设置总页数大小
		Query query = getQuery(hql,values);
		query = setPageParameterToQuery(query, page);
		page.setResult(query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return page;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param page
	 * @param sql
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return
	 * @Description 获得关于键值对的分页对象（SQL方法）
	 */
	public Page findSQLMapPage(Page page , String sql ,Object... values){
		Assert.hasText(sql, "hql不能为空");
		long totalCount = countSQLResult(sql,values);//获得总数
		page.setTotalCount(totalCount);
		page.setTotalPages((int)page.getTotalPages());//设置总页数大小
		Query query = getSQLQuery(sql,values);
		query = setPageParameterToQuery(query, page);
		page.setResult(query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return page;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param query
	 * @param page
	 * @return
	 * @Description 设置查询的范围和参数
	 */
	private Query setPageParameterToQuery(Query query , Page page){
		if(page.getPageSize() <= 0){
			throw new IllegalArgumentException();
		}
		query.setFirstResult(page.getFirst()-1);
		query.setMaxResults(page.getPageSize());
		return query;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values
	 * @return
	 * @Description 获得查询结果(HQL)
	 */
	protected long countHQLResult(String hql ,Object... values){
		Long count = 0L;
		String fromHql = hql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from ");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		fromHql = StringUtils.substringBefore(fromHql, "group by");
		String countHql = "select count(*) " + fromHql;

		try {
			count = findUnique(countHql,values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
		return count;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values
	 * @return
	 * @Description 获得查询结果(SQL)
	 */
	protected long countSQLResult(String sql ,Object... values){
		Long count = 0L;
		String fromHql = sql;
		//select子句与order by子句会影响count查询,进行简单的排除.
//		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
//		fromHql = StringUtils.substringBefore(fromHql, "order by");
//		fromHql = StringUtils.substringBefore(fromHql, "group by");
		fromHql = "from (" + sql +") mycount";
		String countHql = "select count(*) " + fromHql;

		try {
			count = Long.parseLong(findSQLUnique(countHql,values).toString());
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
		return count;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values
	 * @return
	 * @Description 以键值对的方式返回结果HQL的查询方式
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findHQLMap(String hql ,Object... values){
		return getQuery(hql,values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	/**
	 * @author Administrator
	 * @date Nov 17, 2011
	 * @param sql
	 * @return
	 * @Description 以键值对的方式返回结果SQL的查询方式
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findSQLList(String sql){
		return getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	/**
	 * @author admin
	 * @date 2015年11月25日
	 * @param sql
	 * @param values
	 * @return
	 * @Description 以键值对的方式返回结果SQL的查询方式,增加查询参数
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findSQLListByParameter(String sql , Object... values){
		return getSQLQuery(sql, values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values
	 * @return
	 * @Description 查询唯一对象（hql）
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T findUnique(String hql ,Object... values ){
		return (T) getQuery(hql, values).uniqueResult();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param hql
	 * @param values
	 * @return
	 * @Description 查询唯一对象（hql）
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T findForOne(String hql ,Object... values ){
		List list = getQuery(hql, values).setMaxResults(1).list();
		if(!CollectionUtils.isEmpty(list)){
			return (T) list.get(0);
		}
		return null;
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param sql
	 * @param values
	 * @return
	 * @Description 查询唯一对象（sql）
	 */
	@SuppressWarnings("unchecked")
	public Object findSQLUnique(String sql ,Object... values ){
		return getSQLQuery(sql, values).uniqueResult();
	}
	
	/**
	 * @author yehao
	 * @date 2014年4月21日
	 * @param sql
	 * @param values
	 * @return
	 * @Description 查询唯一对象（sql）
	 */
	public Object findSQLForOne(String sql ,Object... values ){
		List list = getSQLQuery(sql, values).setMaxResults(1).list();
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
}


/**
 * Copyright (c) 2014-2015 BrdInfo Technology Company LTD.
 * All rights reserved.
 * 
 * Created on 2014��4��21��
 * Id: HibernateService.java,v 1.0 2014��4��21�� ����2:48:07 yehao
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
 * @Description hibernate��ѯ������һЩ�򵥵ķ�װ��
 */
@Component("hibernateService")
public class HibernateService {
	
protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @return
	 * @Description ���Hibernate session����
	 */
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 * @Description ����hql�Ͳ�����˳��󶨲�����Query����
	 */
	public Query getQuery(String hql , Object... values){
		Assert.hasText(hql, "hql����Ϊ��");
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
	 * @date 2014��4��21��
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 * @Description ����sql�Ͳ�����˳��󶨲�����Query����
	 */
	public Query getSQLQuery(String hql , Object... values){
		Assert.hasText(hql, "sql����Ϊ��");
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
	 * @date 2014��4��21��
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @Description ִ��HQL���
	 */
	public void excuteHQL(String hql , Object... values){
		Assert.hasText(hql, "hql����Ϊ��");
		getQuery(hql,values).executeUpdate();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param sql
	 * @Description ִ��SQL���
	 */
	public void excuteSQL(String sql){
		Assert.hasText(sql, "sql����Ϊ��");
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 * @Description ��ѯ��ҳ��ͨ��hql��
	 */
	@SuppressWarnings("unchecked")
	public List find(String hql,Object... values){
		Assert.hasText(hql, "hql����Ϊ��");
		return getQuery(hql,values).list();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 * @Description ��ѯ��ҳ��ͨ��sql��
	 */
	@SuppressWarnings("unchecked")
	public List findSQL(String sql,Object... values){
		Assert.hasText(sql, "hql����Ϊ��");
		return getSQLQuery(sql,values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param page
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 * @Description ��ù���list�ķ�ҳ����
	 */
	public Page findListPage(Page page , String hql ,Object... values){
		Assert.hasText(hql, "hql����Ϊ��");
		long totalCount = countHQLResult(hql,values);//�������
		page.setTotalCount(totalCount);
		page.setTotalPages((int)page.getTotalPages());//������ҳ����С
		Query query = getQuery(hql,values);
		query = setPageParameterToQuery(query, page);
		page.setResult(query.list());
		return page;
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param page
	 * @param hql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return ��ֵ�Եķ�ҳ����
	 * @Description ��ù��ڼ�ֵ�Եķ�ҳ����
	 */
	@SuppressWarnings("unchecked")
	public Page findMapPage(Page page , String hql ,Object... values){
		Assert.hasText(hql, "hql����Ϊ��");
		long totalCount = countHQLResult(hql,values);//�������
		page.setTotalCount(totalCount);
		page.setTotalPages((int)page.getTotalPages());//������ҳ����С
		Query query = getQuery(hql,values);
		query = setPageParameterToQuery(query, page);
		page.setResult(query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return page;
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param page
	 * @param sql
	 * @param values �����ɱ�Ĳ���,��˳���.
	 * @return
	 * @Description ��ù��ڼ�ֵ�Եķ�ҳ����SQL������
	 */
	public Page findSQLMapPage(Page page , String sql ,Object... values){
		Assert.hasText(sql, "hql����Ϊ��");
		long totalCount = countSQLResult(sql,values);//�������
		page.setTotalCount(totalCount);
		page.setTotalPages((int)page.getTotalPages());//������ҳ����С
		Query query = getSQLQuery(sql,values);
		query = setPageParameterToQuery(query, page);
		page.setResult(query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return page;
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param query
	 * @param page
	 * @return
	 * @Description ���ò�ѯ�ķ�Χ�Ͳ���
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
	 * @date 2014��4��21��
	 * @param hql
	 * @param values
	 * @return
	 * @Description ��ò�ѯ���(HQL)
	 */
	protected long countHQLResult(String hql ,Object... values){
		Long count = 0L;
		String fromHql = hql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
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
	 * @date 2014��4��21��
	 * @param hql
	 * @param values
	 * @return
	 * @Description ��ò�ѯ���(SQL)
	 */
	protected long countSQLResult(String sql ,Object... values){
		Long count = 0L;
		String fromHql = sql;
		//select�Ӿ���order by�Ӿ��Ӱ��count��ѯ,���м򵥵��ų�.
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
	 * @date 2014��4��21��
	 * @param hql
	 * @param values
	 * @return
	 * @Description �Լ�ֵ�Եķ�ʽ���ؽ��HQL�Ĳ�ѯ��ʽ
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
	 * @Description �Լ�ֵ�Եķ�ʽ���ؽ��SQL�Ĳ�ѯ��ʽ
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findSQLList(String sql){
		return getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	/**
	 * @author admin
	 * @date 2015��11��25��
	 * @param sql
	 * @param values
	 * @return
	 * @Description �Լ�ֵ�Եķ�ʽ���ؽ��SQL�Ĳ�ѯ��ʽ,���Ӳ�ѯ����
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findSQLListByParameter(String sql , Object... values){
		return getSQLQuery(sql, values).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param hql
	 * @param values
	 * @return
	 * @Description ��ѯΨһ����hql��
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T findUnique(String hql ,Object... values ){
		return (T) getQuery(hql, values).uniqueResult();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param hql
	 * @param values
	 * @return
	 * @Description ��ѯΨһ����hql��
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
	 * @date 2014��4��21��
	 * @param sql
	 * @param values
	 * @return
	 * @Description ��ѯΨһ����sql��
	 */
	@SuppressWarnings("unchecked")
	public Object findSQLUnique(String sql ,Object... values ){
		return getSQLQuery(sql, values).uniqueResult();
	}
	
	/**
	 * @author yehao
	 * @date 2014��4��21��
	 * @param sql
	 * @param values
	 * @return
	 * @Description ��ѯΨһ����sql��
	 */
	public Object findSQLForOne(String sql ,Object... values ){
		List list = getSQLQuery(sql, values).setMaxResults(1).list();
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
}


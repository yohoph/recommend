package com.zhiyun.bigdata.recommend.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.zhiyun.bigdata.framework.ssh.HibernateService;
import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.dao.RecommendDao;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;

@Repository
@Component
public class RecommendDaoImpl extends HibernateService implements RecommendDao {

	@Override
	public Page getTeachers(Page page, String userId) {
		String hql = " from Teacher t";
		return findListPage(page, hql);
	}

	@Override
	public List<Order> getOrder(String userId) {
		String hql = " from Order o where o.studentId = ? ";
		return find(hql, userId);
	}

	@Override
	public Course getCourse(String courseId) {
		String hql = " from Course c where c.courseId = ?";
		return findForOne(hql, courseId);
	}

}

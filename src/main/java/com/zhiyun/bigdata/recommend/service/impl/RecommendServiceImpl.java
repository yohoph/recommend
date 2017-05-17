package com.zhiyun.bigdata.recommend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.dao.RecommendDao;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;
import com.zhiyun.bigdata.recommend.service.RecommendService;

@Transactional
@Service
@Component("recommendServiceImpl")
public class RecommendServiceImpl implements RecommendService {
	
	@Autowired
	private RecommendDao recommendDao;
	

	@Override
	public Page getTeachers(Page page, String userId) {
		return recommendDao.getTeachers(page, userId);
	}

	@Override
	public List<Order> getOrder(String userId) {
		return recommendDao.getOrder(userId);
	}

	@Override
	public Course getCourse(String courseId) {
		return recommendDao.getCourse(courseId);
	}

}

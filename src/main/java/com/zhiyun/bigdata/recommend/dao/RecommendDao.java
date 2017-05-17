package com.zhiyun.bigdata.recommend.dao;

import java.util.List;

import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;

public interface RecommendDao {
	
	public Page getTeachers(Page page , String userId);
	
	public List<Order> getOrder(String userId);
	
	public Course getCourse(String courseId);

}

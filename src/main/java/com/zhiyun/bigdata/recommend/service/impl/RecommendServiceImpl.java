package com.zhiyun.bigdata.recommend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.dao.RecommendDao;
import com.zhiyun.bigdata.recommend.pojo.Attention;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;
import com.zhiyun.bigdata.recommend.pojo.Student;
import com.zhiyun.bigdata.recommend.pojo.Teacher;
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

	@Override
	public List<Attention> getAttention(String userId) {
		return recommendDao.getAttention(userId);
	}

	@Override
	public Teacher getTeacher(String teacherId) {
		return recommendDao.getTeacher(teacherId);
	}

	@Override
	public long countGradeScan(String gradeId, String userId) {
		return recommendDao.countGradeScan(gradeId, userId);
	}

	@Override
	public long countSubjectScan(String subjectId, String userId) {
		return recommendDao.countSubjectScan(subjectId, userId);
	}

	@Override
	public long sumGradeStayLog(String gradeId, String userId) {
		return recommendDao.sumGradeStayLog(gradeId, userId);
	}

	@Override
	public long sumSubjectStayLog(String subjectId, String userId) {
		return recommendDao.sumSubjectStayLog(subjectId, userId);
	}

	@Override
	public Student getStudent(String studentId) {
		return recommendDao.getStudent(studentId);
	}


}

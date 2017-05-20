package com.zhiyun.bigdata.recommend.dao;

import java.util.List;

import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.pojo.Attention;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;
import com.zhiyun.bigdata.recommend.pojo.Student;
import com.zhiyun.bigdata.recommend.pojo.Teacher;

public interface RecommendDao {
	
	public Page getTeachers(Page page , String userId);
	
	public List<Order> getOrder(String userId);
	
	public Course getCourse(String courseId);
	
	public List<Attention> getAttention(String userId);
	
	public Teacher getTeacher(String teacherId);
	
	public long countGradeScan(String gradeId, String userId);
	
	public long countSubjectScan(String subjectId ,String userId);
	
	public long sumGradeStayLog(String gradeId , String userId);
	
	public long sumSubjectStayLog(String subjectId , String userId);
	
	public Student getStudent(String studentId);

}

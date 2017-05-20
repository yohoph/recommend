package com.zhiyun.bigdata.recommend.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.zhiyun.bigdata.framework.ssh.HibernateService;
import com.zhiyun.bigdata.framework.utils.Page;
import com.zhiyun.bigdata.recommend.dao.RecommendDao;
import com.zhiyun.bigdata.recommend.pojo.Attention;
import com.zhiyun.bigdata.recommend.pojo.Course;
import com.zhiyun.bigdata.recommend.pojo.Order;
import com.zhiyun.bigdata.recommend.pojo.Student;
import com.zhiyun.bigdata.recommend.pojo.Teacher;

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
	
	@Override
	public List<Attention> getAttention(String userId) {
		String hql = " from Attention a where a.studentId = ?";
		return find(hql, userId);
	}

	@Override
	public Teacher getTeacher(String teacherId) {
		return (Teacher) getSession().get(Teacher.class, teacherId);
	}

	@Override
	public long countGradeScan(String gradeId, String userId) {
		String sql = "SELECT count(*) from scan_dimension s inner join course_dimension c on s.course_id = c.course_id where c.grade_id = ? and s.user_id = ? ";
		return countSQLResult(sql , gradeId ,userId);
	}

	@Override
	public long countSubjectScan(String subjectId, String userId) {
		String sql = "SELECT count(*) from scan_dimension s inner join course_dimension c on s.course_id = c.course_id where c.subject_id = ? and s.user_id = ?";
		return countSQLResult(sql, subjectId ,userId);
	}

	@Override
	public long sumGradeStayLog(String gradeId, String userId) {
		String sql = "select sum(stay_count) from stay_dimension s INNER JOIN teacher_dimension t on s.teacher_id = t.teacher_id "
				+ "WHERE t.grade_id = ? and s.student_id = ?";
		Object sum = (Object) findSQLForOne(sql, gradeId , userId);
		return sum == null ? 0L : Long.parseLong(sum.toString());
	}

	@Override
	public long sumSubjectStayLog(String subjectId, String userId) {
		String sql = "select sum(stay_count) from stay_dimension s INNER JOIN teacher_dimension t on s.teacher_id = t.teacher_id "
				+ "WHERE t.subject_id = ? and s.student_id = ?";
		Object sum = (Object) findSQLForOne(sql, subjectId , userId);
		return sum == null ? 0L : Long.parseLong(sum.toString());
	}

	@Override
	public Student getStudent(String studentId) {
		return (Student) getSession().get(Student.class, studentId);
	}

}

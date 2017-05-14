package com.zhiyun.bigdata.recommend.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 课程维度表
 * @author yehao
 *
 */
@Entity
@Table(name="course_dimension")
public class Course implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4222716505135044141L;

	/**
	 * 记录ID
	 */
	private String cid;
	
	/**
	 * 课程ID
	 */
	private String courseId;
	
	/**
	 * 年级ID
	 */
	private String gradeId;
	
	/**
	 * 科目ID
	 */
	private String subjectId;
	
	/**
	 * 老师ID
	 */
	private String teacherId;
	
	/**
	 * 课程价格
	 */
	private Long payCost;
	
	/**
	 * 基础权重
	 */
	private Integer weight;

	public Course() {
		super();
	}

	public Course(String cid, String courseId, String gradeId, String subjectId, String teacherId, Long payCost,
			Integer weight) {
		super();
		this.cid = cid;
		this.courseId = courseId;
		this.gradeId = gradeId;
		this.subjectId = subjectId;
		this.teacherId = teacherId;
		this.payCost = payCost;
		this.weight = weight;
	}

	@Id
	@Column(name = "cid", unique = true, nullable = false)
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "course_id")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Column(name = "grade_id")
	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name = "subject_id")
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "teacher_id")
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "pay_cost")
	public Long getPayCost() {
		return payCost;
	}

	public void setPayCost(Long payCost) {
		this.payCost = payCost;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}

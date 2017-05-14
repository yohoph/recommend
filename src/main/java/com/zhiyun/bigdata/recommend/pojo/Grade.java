package com.zhiyun.bigdata.recommend.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 年级/班级维度表
 * @author yehao
 *
 */
@Entity
@Table(name="grade_dimension")
public class Grade implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 529939865135099643L;

	/**
	 * 记录ID
	 */
	private String cid;
	
	/**
	 * 年级ID
	 */
	private String gradeId;
	
	/**
	 * 学生ID
	 */
	private String studentId;

	public Grade() {
		super();
	}

	public Grade(String cid, String gradeId, String studentId) {
		super();
		this.cid = cid;
		this.gradeId = gradeId;
		this.studentId = studentId;
	}

	@Id
	@Column(name = "cid", unique = true, nullable = false)
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "grade_id")
	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	
	@Column(name = "student_id")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
}

package com.zhiyun.bigdata.recommend.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 关注维度表
 * @author yehao
 *
 */
@Entity
@Table(name="attention_dimension")
public class Attention implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6924436170576315378L;

	/**
	 * 记录ID
	 */
	private String cid;
	
	/**
	 * 学生ID
	 */
	private String studentId;
	
	/**
	 * 老师ID
	 */
	private String teacherId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	public Attention() {
		super();
	}

	public Attention(String cid, String studentId, String teacherId, Date createTime) {
		super();
		this.cid = cid;
		this.studentId = studentId;
		this.teacherId = teacherId;
		this.createTime = createTime;
	}

	@Id
	@Column(name = "cid", unique = true, nullable = false)
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Column(name = "student_id")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Column(name = "teacher_id")
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}

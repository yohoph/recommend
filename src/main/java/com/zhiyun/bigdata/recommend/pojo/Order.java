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
 * ������Ϣά�ȱ�
 * @author yehao
 *
 */
@Entity
@Table(name="order_dimension")
public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3747219131752563096L;

	/**
	 * ��¼ID
	 */
	private String cid;
	
	/**
	 * ѧ��ID
	 */
	private String studentId;
	
	/**
	 * �γ�ID
	 */
	private String courseId;
	
	/**
	 * ֧���۸�
	 */
	private Long payCost;
	
	/**
	 * ����ʱ��
	 */
	private Date createTime;
	
	/**
	 * ���ʱ��
	 */
	private Date completeTime;
	
	/**
	 * ����Ȩ��
	 */
	private Integer weight;

	public Order() {
		super();
	}

	public Order(String cid, String studentId, String courseId, Long payCost, Date createTime, Date completeTime,
			Integer weight) {
		super();
		this.cid = cid;
		this.studentId = studentId;
		this.courseId = courseId;
		this.payCost = payCost;
		this.createTime = createTime;
		this.completeTime = completeTime;
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

	@Column(name = "student_id")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Column(name = "course_id")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Column(name = "pay_cost")
	public Long getPayCost() {
		return payCost;
	}

	public void setPayCost(Long payCost) {
		this.payCost = payCost;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "complete_time")
	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}

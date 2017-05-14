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
 * �����¼ά�ȱ�
 * @author yehao
 *
 */
@Entity
@Table(name="scan_dimension")
public class ScanLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2090109728723553542L;

	/**
	 * ��¼ID
	 */
	private String cid;
	
	/**
	 * �û�ID
	 */
	private String userId;
	
	/**
	 * �γ�ID
	 */
	private String courseId;
	
	/**
	 * ���ʱ��
	 */
	private Date createTime;

	public ScanLog() {
		super();
	}

	public ScanLog(String cid, String userId, String courseId, Date createTime) {
		super();
		this.cid = cid;
		this.userId = userId;
		this.courseId = courseId;
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

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "course_id")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
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

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
 * 用户停留维度表
 * @author yehao
 *
 */
@Entity
@Table(name="stay_dimension")
public class StayLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4024463377121607656L;

	/**
	 * 记录ID
	 */
	private String cid;
	
	/**
	 * 学生ID
	 */
	private String studentId;
	
	/**
	 * 房间ID
	 */
	private String roomId;
	
	/**
	 * 老师ID
	 */
	private String teacherId;
	
	/**
	 * 停留时长
	 */
	private Long stayCount;
	
	/**
	 * 停留时间
	 */
	private Date createTime;
	
	/**
	 * 基础权重
	 */
	private Integer weight;

	public StayLog() {
		super();
	}

	public StayLog(String cid, String studentId, String roomId, String teacherId, Long stayCount, Date createTime,
			Integer weight) {
		super();
		this.cid = cid;
		this.studentId = studentId;
		this.roomId = roomId;
		this.teacherId = teacherId;
		this.stayCount = stayCount;
		this.createTime = createTime;
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

	@Column(name = "room_id")
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "teacher_id")
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "stay_count")
	public Long getStayCount() {
		return stayCount;
	}

	public void setStayCount(Long stayCount) {
		this.stayCount = stayCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
}

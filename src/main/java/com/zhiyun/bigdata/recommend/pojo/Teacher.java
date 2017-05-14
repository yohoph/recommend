package com.zhiyun.bigdata.recommend.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ��ʦά����Ϣ��
 * @author yehao
 *
 */
@Entity
@Table(name="teacher_dimension")
public class Teacher implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 718922528258300747L;

	/**
	 * ��ʦID
	 */
	private String teacherId;
	
	/**
	 * ��ɫID��'�û���ɫ��ѧ����1001���ҳ���1002����ʦ��1003�����̣�1004'
	 */
	private Integer roleId;
	
	/**
	 * ����ID
	 */
	private String roomId;
	
	/**
	 * ��ĿID
	 */
	private String subjectId;
	
	/**
	 * �꼶/�༶ID
	 */
	private String gradeId;
	
	/**
	 * �γ�ID
	 */
	private String courseId;
	
	/**
	 * �γ̼۸�
	 */
	private Long coursePrice;
	
	/**
	 * ��ͥסַ
	 */
	private String address;
	
	/**
	 * ��ʦ�ȼ�
	 */
	private Integer teacherLevel;
	
	/**
	 * ��������
	 */
	private Long partNum;
	
	/**
	 * ��������
	 */
	private Long onlineNum;
	
	/**
	 * ����Ȩ��
	 */
	private Integer weight;
	
	/**
	 * �û�GPS����
	 */
	private String userGps;

	public Teacher() {
		super();
	}

	public Teacher(String teacherId, String roomId, String subjectId, String gradeId, String courseId, Long coursePrice,
			String address, Integer teacherLevel, Long partNum, Long onlineNum, Integer weight, String userGps) {
		super();
		this.teacherId = teacherId;
		this.roomId = roomId;
		this.subjectId = subjectId;
		this.gradeId = gradeId;
		this.courseId = courseId;
		this.coursePrice = coursePrice;
		this.address = address;
		this.teacherLevel = teacherLevel;
		this.partNum = partNum;
		this.onlineNum = onlineNum;
		this.weight = weight;
		this.userGps = userGps;
	}

	@Id
	@Column(name = "teacher_id", unique = true, nullable = false)
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "room_id")
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Column(name = "subject_id")
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "grade_id")
	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name = "course_id")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@Column(name = "course_price")
	public Long getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(Long coursePrice) {
		this.coursePrice = coursePrice;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "teacher_level")
	public Integer getTeacherLevel() {
		return teacherLevel;
	}

	public void setTeacherLevel(Integer teacherLevel) {
		this.teacherLevel = teacherLevel;
	}

	@Column(name = "part_num")
	public Long getPartNum() {
		return partNum;
	}

	public void setPartNum(Long partNum) {
		this.partNum = partNum;
	}

	@Column(name = "online_num")
	public Long getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(Long onlineNum) {
		this.onlineNum = onlineNum;
	}

	@Column(name = "weight")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = "user_gps")
	public String getUserGps() {
		return userGps;
	}

	public void setUserGps(String userGps) {
		this.userGps = userGps;
	}
	
}

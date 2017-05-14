package com.zhiyun.bigdata.recommend.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ѧ��ά����Ϣ��
 * @author yehao
 *
 */
@Entity
@Table(name="student_dimension")
public class Student implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -96655533950962293L;

	/**
	 * ѧ��ID
	 */
	private String studentId;
	
	/**
	 * ��ɫID��'�û���ɫ��ѧ����1001���ҳ���1002����ʦ��1003�����̣�1004'
	 */
	private Integer roleId;
	
	/**
	 * ��ַ��ʡ/��/��/�ֵ�
	 */
	private String address;
	
	/**
	 * ����ָ��
	 */
	private Integer spendPower;
	
	/**
	 * ����Ȩ��
	 */
	private Integer weight;
	
	/**
	 * �û�GPS����
	 */
	private String userGps;
	
	/**
	 * �����û�ID��ѧ�������ҳ�ID���ҳ�����ѧ��ID
	 */
	private String relateId;

	public Student() {
		super();
	}

	public Student(String studentId, String address, Integer spendPower, Integer weight, String userGps) {
		super();
		this.studentId = studentId;
		this.address = address;
		this.spendPower = spendPower;
		this.weight = weight;
		this.userGps = userGps;
	}

	@Id
	@Column(name = "student_id", unique = true, nullable = false)
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "spend_power")
	public Integer getSpendPower() {
		return spendPower;
	}

	public void setSpendPower(Integer spendPower) {
		this.spendPower = spendPower;
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

	@Column(name = "relate_id")
	public String getRelateId() {
		return relateId;
	}

	public void setRelateId(String relateId) {
		this.relateId = relateId;
	}
	
	
}

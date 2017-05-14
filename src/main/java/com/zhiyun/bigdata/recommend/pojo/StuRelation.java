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
 * ѧ����ϵά�ȱ�
 * @author yehao
 *
 */
@Entity
@Table(name="stu_relation_dimension")
public class StuRelation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5765032607681233429L;

	/**
	 * ��¼ID
	 */
	private String cid;
	
	/**
	 * ��ע��
	 */
	private String fromId;
	
	/**
	 * ����ע��
	 */
	private String toId;
	
	/**
	 * ����ʱ��
	 */
	private Date createTime;

	public StuRelation() {
		super();
	}

	public StuRelation(String cid, String fromId, String toId, Date createTime) {
		super();
		this.cid = cid;
		this.fromId = fromId;
		this.toId = toId;
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

	@Column(name = "from_id")
	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	@Column(name = "to_id")
	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
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

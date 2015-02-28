package cn.com.daybreak.blog.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LifeStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2934141856255436446L;
	private int lifeStatusID;
	private User user;
	private String status;
	private Date createTime;
	
	public int getLifeStatusID() {
		return lifeStatusID;
	}
	public void setLifeStatusID(int lifeStatusID) {
		this.lifeStatusID = lifeStatusID;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}

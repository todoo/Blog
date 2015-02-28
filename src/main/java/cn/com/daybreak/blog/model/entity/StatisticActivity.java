package cn.com.daybreak.blog.model.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StatisticActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2721351830085827317L;
	
	private int statActivityID;
	private User user;
	private Date statDate;
	private int articleCount;
	
	public int getStatActivityID() {
		return statActivityID;
	}
	public void setStatActivityID(int statActivityID) {
		this.statActivityID = statActivityID;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getStatDate() {
		return statDate;
	}
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
}

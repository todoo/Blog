package cn.com.daybreak.blog.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StatisticClassify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7273132883223394704L;
	
	private int statClassifyID;
	private User user;
	private ArticleCategory category;
	private float percent;
	
	public int getStatClassifyID() {
		return statClassifyID;
	}
	
	public void setStatClassifyID(int statClassifyID) {
		this.statClassifyID = statClassifyID;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public ArticleCategory getCategory() {
		return category;
	}
	
	public void setCategory(ArticleCategory category) {
		this.category = category;
	}
	
	public float getPercent() {
		return percent;
	}
	
	public void setPercent(float percent) {
		this.percent = percent;
	}
	
}

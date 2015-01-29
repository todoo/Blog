package cn.com.daybreak.model.hibernate.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3458136502167463076L;
	
	private int articleID;
	private User user;
	private ArticleCategory category;
	private String articleTitle;
	private String articleBrief;
	private String articleContent;
	private Date createTime;
	private Date updateTime;
	
	private int categoryID;
	
	public int getArticleID() {
		return articleID;
	}
	
	public void setArticleID(int articleID) {
		this.articleID = articleID;
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
	
	public String getArticleTitle() {
		return articleTitle;
	}
	
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	
	public String getArticleBrief() {
		return articleBrief;
	}
	
	public void setArticleBrief(String articleBrief) {
		this.articleBrief = articleBrief;
	}
	
	public String getArticleContent() {
		return articleContent;
	}
	
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
}

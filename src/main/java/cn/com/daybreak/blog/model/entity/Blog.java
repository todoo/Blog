package cn.com.daybreak.blog.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Blog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1073597477997027278L;
	private int blogID;
	private User user;
	private String title;
	private String message;
	
	public int getBlogID() {
		return blogID;
	}
	public void setBlogID(int blogID) {
		this.blogID = blogID;
	}
	@JsonIgnore
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

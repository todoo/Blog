package cn.com.daybreak.model.hibernate.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Media implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2222330807461539472L;
	
	private int mediaID;
	private User user;
	private DicMediaType mediaType;
	private String mediaPath;
	private BackgroundMusic backgroundMusic;
	
	public int getMediaID() {
		return mediaID;
	}
	
	public void setMediaID(int mediaID) {
		this.mediaID = mediaID;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public DicMediaType getMediaType() {
		return mediaType;
	}
	
	public void setMediaType(DicMediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	public String getMediaPath() {
		return mediaPath;
	}
	
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	@JsonIgnore
	public BackgroundMusic getBackgroundMusic() {
		return backgroundMusic;
	}

	public void setBackgroundMusic(BackgroundMusic backgroundMusic) {
		this.backgroundMusic = backgroundMusic;
	}
	
}

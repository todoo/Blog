package cn.com.daybreak.model.hibernate.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BackgroundMusic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1674819734289370710L;
	
	private int bgMusicID;
	private User user;
	private Media media;
	private String bgMusicName;
	
	public int getBgMusicID() {
		return bgMusicID;
	}
	
	public void setBgMusicID(int bgMusicID) {
		this.bgMusicID = bgMusicID;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public Media getMedia() {
		return media;
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}
	
	public String getBgMusicName() {
		return bgMusicName;
	}
	
	public void setBgMusicName(String bgMusicName) {
		this.bgMusicName = bgMusicName;
	}
	
}

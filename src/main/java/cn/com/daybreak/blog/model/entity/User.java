package cn.com.daybreak.blog.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1684253633097908904L;
	private int userID;
	private String urlID;
	private String userName;
	private String password;
	private String nickName;
	private String logo;
	private String indexAudio;
	private Set<LifeStatus> statuses = new HashSet<LifeStatus>();
	private Set<StatisticActivity> statActivities = new HashSet<StatisticActivity>();
	private Blog blog;
	private Set<ArticleCategory> categories = new HashSet<ArticleCategory>();
	private Set<Article> articles = new HashSet<Article>();
	private Set<StatisticClassify> statClassifies = new HashSet<StatisticClassify>();
	private Set<Media> medias = new HashSet<Media>();
	private Set<BackgroundMusic> backgroundMusics = new HashSet<BackgroundMusic>();
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUrlID() {
		return urlID;
	}
	
	public void setUrlID(String urlID) {
		this.urlID = urlID;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@JsonIgnore
	public Blog getBlog() {
		return blog;
	}
	
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	
	public String getIndexAudio() {
		return indexAudio;
	}
	
	public void setIndexAudio(String indexAudio) {
		this.indexAudio = indexAudio;
	}
	
	@JsonIgnore
	public Set<LifeStatus> getStatuses() {
		return statuses;
	}
	
	public void setStatuses(Set<LifeStatus> statuses) {
		this.statuses = statuses;
	}
	
	@JsonIgnore
	public Set<StatisticActivity> getStatActivities() {
		return statActivities;
	}
	
	public void setStatActivities(Set<StatisticActivity> statActivities) {
		this.statActivities = statActivities;
	}
	
	@JsonIgnore
	public Set<ArticleCategory> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<ArticleCategory> categories) {
		this.categories = categories;
	}
	
	@JsonIgnore
	public Set<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	@JsonIgnore
	public Set<StatisticClassify> getStatClassifies() {
		return statClassifies;
	}

	public void setStatClassifies(Set<StatisticClassify> statClassifies) {
		this.statClassifies = statClassifies;
	}

	@JsonIgnore
	public Set<Media> getMedias() {
		return medias;
	}

	public void setMedias(Set<Media> medias) {
		this.medias = medias;
	}
	
	@JsonIgnore
	public Set<BackgroundMusic> getBackgroundMusics() {
		return backgroundMusics;
	}

	public void setBackgroundMusics(Set<BackgroundMusic> backgroundMusics) {
		this.backgroundMusics = backgroundMusics;
	}
	
}

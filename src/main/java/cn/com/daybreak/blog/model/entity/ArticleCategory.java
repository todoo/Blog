package cn.com.daybreak.blog.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArticleCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5060966125623316035L;

	private int categoryID;
	private User user;
	private ArticleCategory parentCategory;
	private String categoryName;
	private Set<ArticleCategory> subCategories = new HashSet<ArticleCategory>();
	private Set<Article> articles = new HashSet<Article>();
	private StatisticClassify statClassfiy;
	
	private int articleCount;//类目及其子类目共包含多少博文
	
	public int getCategoryID() {
		return categoryID;
	}
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@JsonIgnore
	public ArticleCategory getParentCategory() {
		return parentCategory;
	}
	
	public void setParentCategory(ArticleCategory parentCategory) {
		this.parentCategory = parentCategory;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@JsonIgnore
	public Set<ArticleCategory> getSubCategories() {
		return subCategories;
	}
	
	public void setSubCategories(Set<ArticleCategory> subCategories) {
		this.subCategories = subCategories;
	}
	
	@JsonIgnore
	public Set<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	@JsonIgnore
	public StatisticClassify getStatClassfiy() {
		return statClassfiy;
	}

	public void setStatClassfiy(StatisticClassify statClassfiy) {
		this.statClassfiy = statClassfiy;
	}

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	
}

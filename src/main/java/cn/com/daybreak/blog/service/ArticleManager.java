package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;
import cn.com.daybreak.blog.model.entity.Article;

public interface ArticleManager {
	/**
	 * 获取类目的所有直接博文
	 * @param urlID
	 * @param categoryID
	 * @return
	 */
	public ResultInfo getArticles(String urlID, int categoryID);
	
	/**
	 * 获取article
	 * @param articleID
	 * @return
	 */
	public Article getArticleByArticleID(int articleID);
	
	/**
	 * 新增博文
	 * @param userName
	 * @param categoryID
	 * @param article
	 * @return
	 */
	public ResultInfo addArticleByUserName(String userName, int categoryID, Article article);
	
	/**
	 * 删除博文
	 * @param userName
	 * @param articleID
	 * @return
	 */
	public ResultInfo deleteArticleByUserNameAndArticleID(String userName, int articleID);
	
	/**
	 * 更新博文
	 * @param userName
	 * @param article
	 * @return
	 */
	public ResultInfo updateArticleByUserName(String userName, Article article);
}

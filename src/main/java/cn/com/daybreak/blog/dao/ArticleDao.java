package cn.com.daybreak.blog.dao;

import java.util.List;

import cn.com.daybreak.blog.model.entity.Article;

public interface ArticleDao extends BaseDao<Article> {
	/**
	 * 获取urlID用户的所有博文
	 * @param urlID
	 * @return
	 */
	public List<Article> getArticlesByUrlID(String urlID);
	
	/**
	 * 获取指定类目的所有博文
	 * @param categoryID
	 * @return
	 */
	public List<Article> getArticlesByCategoryID(int categoryID);
	
	/**
	 * 检测用户是否存在指定博文
	 * @param userName
	 * @param articleID
	 * @return
	 */
	public boolean isExistArticleByUserNameAndArticleID(String userName, int articleID);
}

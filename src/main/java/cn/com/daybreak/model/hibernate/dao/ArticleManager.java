package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.entity.Article;

public interface ArticleManager {
	/**
	 * 获取类目的所有直接博文
	 * @param urlID
	 * @param categoryID
	 * @return
	 */
	public ResultInfo getArticles(String urlID, int categoryID);
	
	/**
	 * 新增博文
	 * @param userName
	 * @param categoryID
	 * @param article
	 * @return
	 */
	public ResultInfo addArticleByUserName(String userName, int categoryID, Article article);
}

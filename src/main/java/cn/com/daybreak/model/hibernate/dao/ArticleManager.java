package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

public interface ArticleManager {
	/**
	 * 获取类目的所有直接博文
	 * @param urlID
	 * @param categoryID
	 * @return
	 */
	public ResultInfo getArticles(String urlID, int categoryID);
}

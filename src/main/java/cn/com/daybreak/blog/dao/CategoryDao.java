package cn.com.daybreak.blog.dao;

import java.util.List;

import cn.com.daybreak.blog.model.entity.ArticleCategory;

public interface CategoryDao extends BaseDao<ArticleCategory> {
	/**
	 * 检测对应urlID的用户是否存在categoryID的类目
	 * @param urlID
	 * @param categoryID
	 * @return
	 */
	public boolean isExistCategoryByUrlIDAndCategoryID(String urlID, int categoryID);
	
	/**
	 * 检测对应userName的用户是否存在categoryID的类目
	 * @param userName
	 * @param categoryID
	 * @return
	 */
	public boolean isExistCategoryByUserNameAndCategoryID(String userName, int categoryID);
	
	/**
	 * 获取一个类目的所有子类目
	 * @param urlID
	 * @param parentID
	 * @return
	 */
	public List<ArticleCategory> queryListByUrlIDAndParentID(String urlID, int parentID);
	
	/**
	 * 获取某一类目及其子类目的所有博文数目
	 * @param categoryID
	 * @return
	 */
	public int queryArticleCountByCategoryID(int categoryID);
	
	/**
	 * 获取所有用户的所有根类目列表
	 * @return
	 */
	public List<ArticleCategory> queryRoot();
}

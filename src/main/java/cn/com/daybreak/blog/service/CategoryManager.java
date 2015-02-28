package cn.com.daybreak.blog.service;

import cn.com.daybreak.blog.common.bean.ResultInfo;

public interface CategoryManager {
	/**
	 * 获取所有直接子类目
	 * @param urlID
	 * @param parentID
	 * @return
	 */
	public ResultInfo getCategorys(String urlID, int parentID);
	
}

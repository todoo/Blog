package cn.com.daybreak.model.hibernate.dao;

import cn.com.daybreak.common.bean.ResultInfo;

public interface CategoryManager {
	/**
	 * 获取所有直接子类目
	 * @param urlID
	 * @param parentID
	 * @return
	 */
	public ResultInfo getCategorys(String urlID, int parentID);
	
}

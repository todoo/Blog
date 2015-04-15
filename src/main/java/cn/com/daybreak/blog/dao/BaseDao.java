package cn.com.daybreak.blog.dao;

import java.io.Serializable;

public interface BaseDao<T> {
	/**
	 * 增
	 * @param entity 新增实体对象
	 * @return 成功:返回主键对象; 失败:返回null
	 */
	public Serializable add(T entity);
	
	/**
	 * 删
	 * @param id 
	 * @return 删除的数目
	 */
	public int deleteByID(Serializable id);
	
	/**
	 * 改
	 * @param entity 新增实体对象
	 * @return 成功:true; 失败:false
	 */
	public void update(T entity);
	
	/**
	 * 查
	 * @param id
	 * @return 成功:实体对象; 失败:null
	 */
	public T queryByID(Serializable id);

}

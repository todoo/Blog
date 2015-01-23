package cn.com.daybreak.model.hibernate.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.daybreak.common.bean.ResultInfo;
import cn.com.daybreak.model.hibernate.dao.DicMediaTypeManager;
import cn.com.daybreak.model.hibernate.entity.DicMediaType;

@Service
public class DicMediaTypeManagerImpl implements DicMediaTypeManager {
	@Autowired
	private SessionFactory sf;

	@SuppressWarnings("unchecked")
	@Override
	public ResultInfo getMediaType(String mediaTypeName) {
		ResultInfo result = new ResultInfo(true);
		try {
			Session session = sf.getCurrentSession();
			session.beginTransaction();
			
			String hql = "from DicMediaType where mediaTypeName=:mediaTypeName";
			Query query = session.createQuery(hql);
			query.setString("mediaTypeName", mediaTypeName);
			List<DicMediaType> mediaTypes = query.list();
			
			if (mediaTypes.size()<=0) {
				throw new Exception();
			}
			result.addData("mediaType", mediaTypes.get(0));
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("获取媒体类型异常");
		}
		return result;
	}
}

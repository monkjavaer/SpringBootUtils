package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.system.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities.PropertiesUtil;
import com.base.springboot.car.Base.src.main.java.com.car.base.dao.daos.BaseDao;
import com.base.springboot.car.Base.src.main.java.com.car.base.model.models.ResultTuple;
import com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.system.bo.UserBO;
import com.car.trunk.dal.system.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>Description:用户DAO实现类 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-30 9:42
 */
@Repository("UserDAO")
public class UserDAOImpl extends BaseDao<UserEntity> implements UserDAO {
	@Override
	public ResultTuple<UserBO> queryList(UserVO userVO) {
		PropertiesUtil propertiesUtil = new PropertiesUtil("/auth.properties");
		//配置超级管理员不查询出来
		String superAdmin = propertiesUtil.getPropertieValue("Auth.CA.userName");
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT u.ID as id, u.USERNAME as username, u.NAME as name, "
			+ "u.ROLE_ID as roleId, u.GENDER as gender, u.PHONE as phone, u.EMAIL AS email, "
			+ "u.MONITOR_CENTER_ID as monitorCenterId,m.NAME as monitorCenterName FROM `USER` u " +
				"left join MONITOR_CENTER m on u.MONITOR_CENTER_ID = m.ID WHERE 1=1  AND u.DELETED = " + HasDelete.NO.value);
		
		Criteria criteria = getCriteria();
		if (userVO.getMonitorCenterId() != null) {
			criteria.add(Restrictions.eq("monitorCenterId", userVO.getMonitorCenterId()));
			
			sqlStr.append(" AND u.MONITOR_CENTER_ID = " + userVO.getMonitorCenterId());
		}
		if (userVO.getRoleId() != null) {
			criteria.add(Restrictions.eq("roleId", userVO.getRoleId()));
			
			sqlStr.append(" AND u.ROLE_ID = " + userVO.getRoleId());
		}
		if (userVO.getGender() != null) {
			criteria.add(Restrictions.eq("gender", userVO.getGender()));
			
			sqlStr.append(" AND u.GENDER = " + userVO.getGender());
		}
		if (!"".equals(userVO.getUser())) {
			criteria.add(Restrictions.or(
				Restrictions.ilike("username", userVO.getUser(), MatchMode.ANYWHERE),
				Restrictions.ilike("name", userVO.getUser(), MatchMode.ANYWHERE)
			));
			
			sqlStr.append(" AND (u.USERNAME like '%" + userVO.getUser() + "%' OR u.NAME like '%" + userVO.getUser() + "%')");
		}
		criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
		criteria.add(Restrictions.ne("username", superAdmin));
		sqlStr.append(" ORDER BY u.USERNAME ASC limit " + (userVO.getPageNo() - 1) * userVO.getPageSize() + ", " + userVO.getPageSize());
		SQLQuery query = getSession().createSQLQuery(sqlStr.toString());
		query.setResultTransformer(Transformers.aliasToBean(UserBO.class));
		
		
//		criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        Integer count = criteria.list().size();
//        criteria.setResultTransformer(Transformers.aliasToBean(UserBO.class));
//        List<UserBO> items = criteria
//    		.setFirstResult((userVO.getPageNo() - 1) * userVO.getPageSize())
//    		.setMaxResults(userVO.getPageSize())
//    		.list();
        
        List<UserBO> items = query.list();
        
        return new ResultTuple<UserBO>(items, count);
	}
	
	@Override
	public List<UserEntity> getByUsername(String username) {
		Criteria criteria = getCriteria();
        if (StringUtils.isNotEmpty(username)) {
            criteria.add(Restrictions.eq("username", username));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        List<UserEntity> items = criteria.list();
		return items;
	}
	
	@Override
	public String getPasswordById(BigInteger id) {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT password FROM `USER` WHERE id = " + id);
		SQLQuery query = getSession().createSQLQuery(sqlStr.toString());
		List<String> list = query.list();
		String password = list.get(0);
		return password;
	}

	@Override
	public UserBO getUserForLogin(String userName, String password) {
		String sql = "SELECT u.ID as id, u.USERNAME as username, u.NAME as name, "
				+ "u.ROLE_ID as roleId, u.GENDER as gender, u.PHONE as phone, u.EMAIL AS email, "
				+ "u.MONITOR_CENTER_ID as monitorCenterId FROM USER AS u WHERE DELETED = 0 "
				+ "AND USERNAME = :userName AND PASSWORD = :password ";
		SQLQuery sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setParameter("userName", userName);
		sqlQuery.setParameter("password", password);
		sqlQuery.setResultTransformer(Transformers.aliasToBean(UserBO.class));
		List<UserBO> boList = sqlQuery.list();
		if (null != boList && !boList.isEmpty()) {
            return boList.get(0);
        }
		return null;
	}
}

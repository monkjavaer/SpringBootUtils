package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.system.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.interfaces.IBaseDao;
import com.base.springboot.car.Base.src.main.java.com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.system.bo.UserBO;
import com.car.trunk.dal.system.vo.UserVO;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>Description:用户DAO接口 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-30 9:41
 */
public interface UserDAO extends IBaseDao<UserEntity> {
	/**
	 * 用户分页列表
	 * @param userVO
	 * @return
	 */
	ResultTuple<UserBO> queryList(UserVO userVO);

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	List<UserEntity> getByUsername(String username);

	/**
	 * 查询密码
	 * @param id
	 * @return
	 */
	String getPasswordById(BigInteger id);

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @return
	 */
	UserBO getUserForLogin(String userName, String password);
}

package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import com.car.base.common.utilities.PageList;
import com.car.base.service.interfaces.IBaseService;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.system.bo.UserBO;
import com.car.trunk.dal.system.dao.UserDAO;
import com.car.trunk.dal.system.vo.UserVO;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>Description:用户Service接口 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-29 10:14
 */
public interface UserService extends IBaseService<UserEntity, UserDAO>{

    /**
     * 添加用户
     * @param user
     * @throws Exception
     */
    void addUser(UserEntity user) throws Exception;

    /**
     * 分页
     * @return
     */
    PageList<UserEntity> queryUserList();

    /**
     * 用户分页列表
     * @param userVO
     * @return
     */
    PageList<UserBO> queryList(UserVO userVO);

    /**
     * 查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    UserEntity queryUserInfo(BigInteger id) throws Exception;

    /**
     * 修改用户
     * @param user
     * @throws Exception
     */
    void modifyUser(UserEntity user) throws Exception;

    /**
     * 删除用户
     * @param user
     * @throws Exception
     */
    void deleteUser(UserEntity user) throws Exception;

    /**
     * 用户名查重
     * @param username
     * @return
     * @throws Exception
     */
    boolean checkUsername(String username) throws Exception;

    /**
     * 根据用户名获得用户
     * @param username
     * @return
     * @throws Exception
     */
    List<UserEntity> getUserByName(String username) throws Exception;

    /**
     * 根据id查密码
     * @param id
     * @return
     * @throws Exception
     */
    String getPasswordById(BigInteger id) throws Exception;

    /**
     * 登录
     * @param userName
     * @param password
     * @param superAdminOriginalpwd
     * @return
     */
    UserInfoBO login(String userName, String password,boolean superAdminOriginalpwd);

    /**
     * 登录测试
     * @param userName
     * @param password
     * @return
     */
    UserInfoBO loginmyself(String userName, String password);
}

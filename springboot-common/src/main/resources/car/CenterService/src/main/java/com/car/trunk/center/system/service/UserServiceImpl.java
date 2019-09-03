package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.PageList;
import com.car.base.common.utilities.PageListUtil;
import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import com.car.base.model.models.ResultTuple;
import com.car.base.service.services.BaseService;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.model.RoleEntity;
import com.car.trunk.dal.model.UserEntity;
import com.car.trunk.dal.system.bo.UserBO;
import com.car.trunk.dal.system.dao.RoleAuthorityDao;
import com.car.trunk.dal.system.dao.RoleDAO;
import com.car.trunk.dal.system.dao.UserDAO;
import com.car.trunk.dal.system.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>Description:用户Service实现类 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-29 10:14
 */
@Service("UserService")
public class UserServiceImpl extends BaseService<UserEntity, UserDAO> implements UserService {
	@Autowired
    public UserServiceImpl(@Qualifier("UserDAO")UserDAO dao) {
        super(dao);
    }

    @Autowired
    private UserDAO userDao;
	@Autowired
    private RoleDAO roleDAO;
	@Autowired
    private RoleAuthorityDao roleAuthorityDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addUser(UserEntity user) throws Exception{
        user.setId(SnowflakeIdWorkerUtil.generateId());
        user.setDeleted(HasDelete.NO.value);
        userDao.save(user);
        logger.info("create user(id:{})", user.getId());
    }

    @Override
    public PageList<UserEntity> queryUserList() {
        return PageListUtil.getPageList(4,1, userDao.listAll(), 10);
    }
    
    @Override
    public PageList<UserBO> queryList(UserVO userVO) {
    	ResultTuple<UserBO> result = userDao.queryList(userVO);
    	return PageListUtil.getPageList(result.count, userVO.getPageNo(), result.items, userVO.getPageSize());
    }

    @Override
    public UserEntity queryUserInfo(BigInteger id) throws Exception {
        return userDao.get(id);
    }

    @Override
    public void modifyUser(UserEntity user) throws Exception {
        userDao.update(user);
        logger.info("update user(id:{})", user.getId());
    }

    @Override
    public void deleteUser(UserEntity user) throws Exception {
    	user.setDeleted(HasDelete.YES.value);
    	userDao.update(user);
        logger.info("delete user(id:{})", user.getId());
    }
    
    @Override
    public boolean checkUsername(String username) {
    	boolean result = true;
        List<UserEntity> list = entityDao.getByUsername(username);
        if(list.size() > 0){
            result = false;
        }
    	return result;
    }

    @Override
    public List<UserEntity> getUserByName(String username) throws Exception {
        return entityDao.getByUsername(username);
    }

    @Override
    public String getPasswordById(BigInteger id) {
    	return userDao.getPasswordById(id);
    }

    @Override
    public UserInfoBO login(String userName, String password ,boolean superAdminOriginalpwd) {
        // 统一认证登录

        UserBO userBO = userDao.getUserForLogin(userName, password);
        if (null == userBO) {
            return null;
        }
        RoleEntity roleEntity = null;
        try {
            roleEntity = roleDAO.get(userBO.getRoleId());
        } catch (ValidateException e) {
            e.printStackTrace();
        }
        if (null == roleEntity) {
            return null;
        }

        List<String> authorityList = roleAuthorityDao.getAuthoritiesByRoleId(roleEntity.getId());
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(userBO.getId());
        userInfoBO.setUserName(userName);
        userInfoBO.setRoleName(roleEntity.getName());
        userInfoBO.setCenterId(userBO.getMonitorCenterId());
        userInfoBO.setAuthority(authorityList);
        return userInfoBO;
    }

    @Override
    public UserInfoBO loginmyself(String userName, String password) {
        UserBO userBO = userDao.getUserForLogin(userName, password);
        if (null == userBO) {
            return null;
        }
        RoleEntity roleEntity = null;
        try {
            roleEntity = roleDAO.get(userBO.getRoleId());
        } catch (ValidateException e) {
            e.printStackTrace();
        }
        if (null == roleEntity) {
            return null;
        }

        List<String> authorityList = roleAuthorityDao.getAuthoritiesByRoleId(roleEntity.getId());
        UserInfoBO userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(userBO.getId());
        userInfoBO.setUserName(userName);
        userInfoBO.setRoleName(roleEntity.getName());
        userInfoBO.setCenterId(userBO.getMonitorCenterId());
        userInfoBO.setAuthority(authorityList);
        return userInfoBO;
    }
}

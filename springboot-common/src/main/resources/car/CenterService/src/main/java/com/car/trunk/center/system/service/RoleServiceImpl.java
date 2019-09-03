package com.base.springboot.car.CenterService.src.main.java.com.car.trunk.center.system.service;

import com.car.base.common.exception.EntityOperateException;
import com.car.base.common.exception.ValidateException;
import com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.model.AuthorityEntity;
import com.car.trunk.dal.model.RoleEntity;
import com.car.trunk.dal.system.bo.RoleListBO;
import com.car.trunk.dal.system.dao.AuthorityDao;
import com.car.trunk.dal.system.dao.RoleAuthorityDao;
import com.car.trunk.dal.system.dao.RoleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Description:角色Service实现类 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-11-29 16:12
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private RoleAuthorityDao roleAuthorityDao;
    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public void addRole(RoleEntity roleEntity) {
        try {
            roleDAO.save(roleEntity);
        } catch (EntityOperateException e) {
            logger.info(e.getMessage());
            //e.printStackTrace();
        } catch (ValidateException e) {
            logger.info(e.getMessage());
            //e.printStackTrace();
        }
    }

    @Override
    public void modifyRole(RoleEntity roleEntity) {
        try {
            roleDAO.update(roleEntity);
        } catch (EntityOperateException e) {
            logger.info(e.getMessage());
            //e.printStackTrace();
        } catch (ValidateException e) {
            logger.info(e.getMessage());
            //e.printStackTrace();
        }
    }

    @Override
    public void deleteRole(RoleEntity roleEntity) {
        try {
            roleDAO.delete(roleEntity);
        } catch (EntityOperateException e) {
            logger.info(e.getMessage());
            //e.printStackTrace();
        } catch (ValidateException e) {
            logger.info(e.getMessage());
            //e.printStackTrace();
        }
    }

    @Override
    public ResultTuple<RoleListBO> queryRoleList() {
        List<RoleListBO> roleList = new ArrayList<>();
        List<RoleEntity> roleEntities = roleDAO.listAll();
        for (RoleEntity roleEntity : roleEntities) {
            List<String> authorityList = roleAuthorityDao.getAuthoritiesByRoleId(roleEntity.getId());

            RoleListBO bo = new RoleListBO();
            bo.setRoleId(roleEntity.getId());
            bo.setRoleName(roleEntity.getName());
            bo.setAuthority(authorityList);

            roleList.add(bo);
        }
        return new ResultTuple<>(roleList, roleList.size());
    }

    @Override
    public List<AuthorityEntity> queryAuthorityList() {
        return authorityDao.listAll();
    }

    @Override
    public void updateRoleAuthority(BigInteger roleId, List<String> authorityName) {

        //List<RoleAuthorityEntity> entityList = new ArrayList<>();

        //List<BigInteger> roleList = new ArrayList<>();
        //List<BigInteger> authorityList = new ArrayList<>();
        //for (BigInteger authorityId : authorityIds) {
            /*RoleAuthorityEntity entity = new RoleAuthorityEntity();
            entity.setRoleId(roleId);
            entity.setAuthorityId(authorityId);
            entityList.add(entity);*/
        //    roleList.add(roleId);
        //    authorityList.add(authorityId);
        //}

        try {
            roleAuthorityDao.deleteAuthoritiesByRoleId(roleId);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }

        try {
            roleAuthorityDao.insertRoleAuthority(roleId, authorityName);
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }
    }
}

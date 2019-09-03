package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.system.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.daos.BaseDao;
import com.car.trunk.dal.model.AuthorityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Description: 权限表DAO实现类
 * Original Author: monkjavaer, 2017/12/19
 */
@Repository
public class AuthorityDaoImpl extends BaseDao<AuthorityEntity> implements AuthorityDao {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityDaoImpl.class);
}

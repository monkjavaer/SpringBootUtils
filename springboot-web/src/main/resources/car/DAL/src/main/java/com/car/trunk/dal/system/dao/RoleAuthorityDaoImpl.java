package com.car.trunk.dal.system.dao;

import com.car.base.dao.daos.BaseDao;
import com.car.trunk.dal.model.RoleAuthorityEntity;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Description: 角色权限表DAO实现类
 * Original Author: monkjavaer, 2017/12/11
 */
@Repository
public class RoleAuthorityDaoImpl extends BaseDao<RoleAuthorityEntity> implements RoleAuthorityDao {
    private static final Logger logger = LoggerFactory.getLogger(RoleAuthorityDaoImpl.class);

    @Override
    public List<String> getAuthoritiesByRoleId(BigInteger roleId) {
        String sql = "SELECT a.NAME FROM AUTHORITY AS a INNER JOIN ROLE_AUTHORITY AS r ON a.ID = r.AUTHORITY_ID WHERE r.ROLE_ID = :roleId ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("roleId", roleId);
        //sqlQuery.addEntity(BigInteger.class);
        return sqlQuery.list();
    }

    @Override
    public void deleteAuthoritiesByRoleId(BigInteger roleId) {
        String sql = "DELETE FROM ROLE_AUTHORITY WHERE ROLE_ID = :roleId ";
        SQLQuery sqlQuery = getSession().createSQLQuery(sql);
        sqlQuery.setParameter("roleId", roleId);
        sqlQuery.executeUpdate();
    }

    @Override
    public void insertRoleAuthority(BigInteger roleId, List<String> authorityList) {
        for (String authorityName : authorityList) {
            String sql1 = "SELECT ID FROM AUTHORITY WHERE NAME = :authorityName";
            SQLQuery sqlQuery1 = getSession().createSQLQuery(sql1);
            sqlQuery1.setParameter("authorityName", authorityName);
            List<BigInteger> ids = sqlQuery1.list();
            if (null == ids || ids.isEmpty()) {
                continue;
            }

            String sql = "INSERT INTO ROLE_AUTHORITY(ROLE_ID, AUTHORITY_ID) VALUES(:roleId, :authorityId) ";
            SQLQuery sqlQuery = getSession().createSQLQuery(sql);
            sqlQuery.setParameter("roleId", roleId);
            sqlQuery.setParameter("authorityId", ids.get(0));
            sqlQuery.executeUpdate();
        }
    }
}

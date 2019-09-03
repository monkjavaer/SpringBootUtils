package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.constant.EnvConstant;
import com.base.springboot.car.Base.src.main.java.com.car.base.dao.daos.BaseDao;
import com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.model.AreaEntity;
import com.car.trunk.dal.util.AuthorityUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Repository("AreaDao")
public class AreaDaoImpl extends BaseDao<AreaEntity> implements AreaDao {

    /**
     * 用户城市区域DAO接口
     */
    @Autowired
    private UserCityAreaDAO userCityAreaDAO;

    @Override
    public List<AreaEntity> queryList(BigInteger cityId) {
        Criteria criteria = getCriteria();
        if (cityId != null) {
            criteria.add(Restrictions.eq("cityId", cityId));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        criteria.addOrder(Order.desc("name"));
        return criteria.list();
    }

    @Override
    public List<AreaEntity> getByName(String name) {
        Criteria criteria = getCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.add(Restrictions.eq("name", name));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        List<AreaEntity> items = criteria.list();
        return items;
    }

    @Override
    public List<AreaEntity> checkAreaCode(String code) {
        Criteria criteria = getCriteria();
        if (StringUtils.isNotEmpty(code)) {
            criteria.add(Restrictions.eq("adminRegionCode", code));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        List<AreaEntity> items = criteria.list();
        return items;
    }

    @Override
    public List<AreaEntity> queryListByUserId(BigInteger cityId,BigInteger userId, String userName) {
        String citySql = null;
        String areaSql = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.ID id,a.NAME name,a.ADMIN_REGION_CODE adminRegionCode,a.CITY_ID cityId,a.DELETED deleted FROM AREA a ");
        sb.append("WHERE a.DELETED = '" + HasDelete.NO.value + "' ");
        sb.append("AND a.CITY_ID = "+cityId+ " ");
        if (!EnvConstant.SUPERADMIN.equals(userName)) {
            Set<BigInteger> cityChecked = userCityAreaDAO.queryCheckedCityByUserId(userId);
            citySql = AuthorityUtil.getIdSqlByIdS(cityChecked);
            Set<BigInteger> areaChecked = userCityAreaDAO.queryCheckedAreaByUserIdCityIds(userId, citySql);
            areaSql = AuthorityUtil.getIdSqlByIdS(areaChecked);
            if (StringUtils.isNotBlank(areaSql)) {
                sb.append("AND a.ID in (" + areaSql + ") ");
            }
        }
        sb.append("ORDER BY a.`NAME` ");
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(AreaEntity.class));
        return query.list();
    }
}

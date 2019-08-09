package com.car.trunk.dal.device.dao;

import com.car.base.common.constant.EnvConstant;
import com.car.base.dao.daos.BaseDao;
import com.car.trunk.dal.device.bo.CityBO;
import com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.model.CityEntity;
import com.car.trunk.dal.util.AuthorityUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
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
@Repository("CityDao")
public class CityDaoImpl extends BaseDao<CityEntity> implements CityDao {

    /**
     * 用户城市区域DAO接口
     */
    @Autowired
    private UserCityAreaDAO userCityAreaDAO;

    @Override
    public List<CityBO> queryList(BigInteger monitorCenterId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT c.ID AS id,c.`NAME` AS name,c.ADMIN_REGION_CODE AS adminRegionCode,c.MONITOR_CENTER_ID AS monitorCenterId,m.`NAME` AS monitorCenterName  FROM CITY c ");
        sb.append("LEFT JOIN MONITOR_CENTER m ON c.MONITOR_CENTER_ID = m.ID ");
        sb.append("WHERE c.DELETED = '" + HasDelete.NO.value + "' ");
        if (monitorCenterId != null) {
            sb.append("AND c.MONITOR_CENTER_ID = '" + monitorCenterId + "' ");
        }
        sb.append("ORDER BY c.`NAME` ");
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(CityBO.class));
        return query.list();
    }

    @Override
    public List<CityEntity> getByName(String name) {
        Criteria criteria = getCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.add(Restrictions.eq("name", name));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        List<CityEntity> items = criteria.list();
        return items;
    }

    @Override
    public List<CityEntity> checkCityCode(String code) {
        Criteria criteria = getCriteria();
        if (StringUtils.isNotEmpty(code)) {
            criteria.add(Restrictions.eq("adminRegionCode", code));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        List<CityEntity> items = criteria.list();
        return items;
    }

    @Override
    public List<CityBO> queryListByUserId(BigInteger userId, String userName) {
        String citySql = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT c.ID AS id,c.`NAME` AS name,c.ADMIN_REGION_CODE AS adminRegionCode," +
                "c.MONITOR_CENTER_ID AS monitorCenterId,m.`NAME` AS monitorCenterName  FROM CITY c ");
        sb.append("LEFT JOIN MONITOR_CENTER m ON c.MONITOR_CENTER_ID = m.ID ");
        sb.append("LEFT JOIN AREA a ON a.CITY_ID =c.ID ");
        sb.append("WHERE c.DELETED = '" + HasDelete.NO.value + "' ");
        if (!EnvConstant.SUPERADMIN.equals(userName)) {
            Set<BigInteger> cityChecked = userCityAreaDAO.queryCheckedCityByUserId(userId);
            citySql = AuthorityUtil.getIdSqlByIdS(cityChecked);
            if (StringUtils.isNotBlank(citySql)) {
                sb.append("AND c.ID in (" + citySql + ") ");
            }
        }
        sb.append("ORDER BY c.`NAME` ");
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(CityBO.class));
        return query.list();
    }
}

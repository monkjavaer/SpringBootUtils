package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.dao.daos.BaseDao;
import com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.model.RoadCrossPointEntity;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Repository("RoadCrossPointDao")
public class RoadCrossPointDaoImpl extends BaseDao<RoadCrossPointEntity> implements RoadCrossPointDao {
    @Override
    public List<RoadCrossPointEntity> queryList(BigInteger areaId) {
        Criteria criteria = getCriteria();
        if (areaId != null) {
            criteria.add(Restrictions.eq("areaId", areaId));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        criteria.addOrder(Order.desc("name"));
        return criteria.list();
    }

    @Override
    public BigInteger queryMonitorCenterId(BigInteger roadCrossPointId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT c.MONITOR_CENTER_ID FROM ROAD_CROSS_POINT r ");
        sb.append("LEFT JOIN AREA a ON r.AREA_ID = a.ID ");
        sb.append("LEFT JOIN CITY c ON a.CITY_ID = c.ID ");
        sb.append("WHERE r.ID = '"+roadCrossPointId+"'; ");
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        List list = query.list();
        if (list.size()>0) {
            return (BigInteger) list.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<RoadCrossPointEntity> queryListByCityId(String cityId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT r.ID AS id,r.NAME AS name,r.LATITUDE AS latitude,r.LONGITUDE AS longitude,r.AREA_ID AS areaId from ROAD_CROSS_POINT r ");
        sb.append("LEFT JOIN AREA a ON r.AREA_ID = a.ID ");
        sb.append("LEFT JOIN CITY c ON a.CITY_ID = c.ID ");
        sb.append("WHERE c.ID = '"+cityId+"' ");
        sb.append("AND r.DELETED = '"+HasDelete.NO.value+"' ");

        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(RoadCrossPointEntity.class));
        return query.list();
    }

    @Override
    public List<RoadCrossPointEntity> getByName(String name) {
        Criteria criteria = getCriteria();
        if (StringUtils.isNotEmpty(name)) {
            criteria.add(Restrictions.eq("name", name));
        }
        criteria.add(Restrictions.eq("deleted", HasDelete.NO.value));
        List<RoadCrossPointEntity> items = criteria.list();
        return items;
    }

    @Override
    public List<RoadCrossPointEntity> queryRoadDevice(BigInteger areaId){
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT rcp.ID AS id,rcp.NAME as name,rcp.LATITUDE as latitude,rcp.LONGITUDE as longitude," +
                "rcp.ROADWAY_NUM as roadwayNum,rcp.AREA_ID as areaId,rcp.DELETED as deleted,rcp.DIRECTION_CODE as directionCode " +
                "FROM ROAD_CROSS_POINT rcp LEFT JOIN DEVICE d ON rcp.ID=d.ROAD_CROSS_POINT_ID " +
                "WHERE rcp.AREA_ID = "+areaId+" AND rcp.DELETED = "+HasDelete.NO.value+
                " AND d.ROAD_CROSS_POINT_ID IS NOT NULL");

        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        return query.list();

    }
}

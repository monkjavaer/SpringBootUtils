package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.device.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.constant.EnvConstant;
import com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities.DateTimeUtils;
import com.base.springboot.car.Base.src.main.java.com.car.base.dao.daos.BaseDao;
import com.base.springboot.car.Base.src.main.java.com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.device.bo.DeviceBO;
import com.car.trunk.dal.device.bo.DeviceQueryVO;
import com.car.trunk.dal.device.bo.DeviceSimpleBO;
import com.car.trunk.dal.device.vo.DeviceRedisVO;
import com.car.trunk.dal.device.vo.DeviceSimpleVO;
import com.car.trunk.dal.device.vo.DeviceVO;
import com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary.DeviceType;
import com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.dictionary.HasDelete;
import com.car.trunk.dal.model.DeviceEntity;
import com.car.trunk.dal.util.AuthorityUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Repository("DeviceDao")
public class DeviceDaoImpl extends BaseDao<DeviceEntity> implements DeviceDao {

    /**
     * 用户城市区域DAO接口
     */
    @Autowired
    private UserCityAreaDAO userCityAreaDAO;

    @Override
    public ResultTuple<DeviceBO> queryList(DeviceVO deviceVO,BigInteger userId,String userName) {
        List<DeviceBO> resultList = new ArrayList<DeviceBO>();
        Integer count = 0;
        Set<BigInteger> areaChecked = null;
        String citySql = null;
        String areaSql = null;
        if (!EnvConstant.SUPERADMIN.equals(userName)) {
            if (deviceVO.getCityId() == null) {
                Set<BigInteger> cityChecked = userCityAreaDAO.queryCheckedCityByUserId(userId);
                citySql = AuthorityUtil.getIdSqlByIdS(cityChecked);
            }else {
                citySql = deviceVO.getCityId().toString();
            }
            if (deviceVO.getAreaId() == null) {
                areaChecked = userCityAreaDAO.queryCheckedAreaByUserIdCityIds(userId, citySql);
                areaSql = AuthorityUtil.getIdSqlByIdS(areaChecked);
            }
        }
        if (DeviceType.ELECTRONIC_POLICE.value.equals(deviceVO.getType()) || DeviceType.BAYONET.value.equals(deviceVO.getType()) ||deviceVO.getDevided() != null) {
            resultList = deviceQuery(deviceVO,citySql,areaSql);
            count = deviceQueryCount(deviceVO,citySql,areaSql);

        }else if (DeviceType.HOST.value.equals(deviceVO.getType())){
            resultList = terminalQuery(deviceVO,citySql,areaSql);
            count = terminalQueryCount(deviceVO,citySql,areaSql);
        }else {
            count = deviceQueryCount(deviceVO,citySql,areaSql)+terminalQueryCount(deviceVO,citySql,areaSql);
            List<DeviceBO> deviceList = deviceQuery(deviceVO,citySql,areaSql);
            List<DeviceBO> terminalList = terminalQuery(deviceVO,citySql,areaSql);
            resultList.addAll(deviceList);
            resultList.addAll(terminalList);
        }
        int toIndex = ((deviceVO.getPageNo() * deviceVO.getPageSize()) > resultList.size()) ? (resultList.size()) : (deviceVO.getPageNo() * deviceVO.getPageSize());
        List<DeviceBO> subList = resultList.subList((deviceVO.getPageNo() - 1) * deviceVO.getPageSize(), toIndex);
        // 指定页的数据
        return new ResultTuple<>(subList, count);
    }



    public List<DeviceBO> deviceQuery(DeviceVO deviceVO,String citySql,String areaSql){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT d.ID id,d.NAME name,d.TYPE type,d.ONLINE online,c.NAME cityName,a.NAME areaName,r.NAME roadName,m.ID monitorCenterId," +
                "d.TERMINAL_ID terminalId,d.INSTALL_ADDRESS installAddress,d.LATITUDE latitude,d.LONGITUDE longitude," +
                "c.ID cityId,a.ID areaId,r.ID roadCrossPointId FROM DEVICE d ");
        sb.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
        sb.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        sb.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        sb.append("LEFT JOIN MONITOR_CENTER m ON m.ID = c.MONITOR_CENTER_ID ");
        sb.append("WHERE d.DELETED = '" + HasDelete.NO.value + "' ");
        if (deviceVO.getCityId() != null) {
            sb.append("AND c.ID = '" + deviceVO.getCityId() + "' ");
        }else {
            if (StringUtils.isNotBlank(citySql)) {
                sb.append("AND c.ID in (" + citySql + ") ");
            }
        }
        if (deviceVO.getAreaId() != null) {
            sb.append("AND a.ID = '" + deviceVO.getAreaId() + "' ");
        }else {
            if (StringUtils.isNotBlank(areaSql)) {
                sb.append("AND a.ID in (" + areaSql + ") ");
            }
        }
        if (deviceVO.getRoadCrossPointId() != null) {
            sb.append("AND r.ID = '" + deviceVO.getRoadCrossPointId() + "' ");
        }
        if (deviceVO.getType() != null) {
            sb.append("AND d.TYPE = '" + deviceVO.getType() + "' ");
        }
        if (deviceVO.getDevided() != null) {
            if (deviceVO.getDevided() == 1){//1已划分
                sb.append("AND d.ROAD_CROSS_POINT_ID IS NOT NULL ");
            }
            if(deviceVO.getDevided() == 0){//0，未划分
                sb.append("AND d.ROAD_CROSS_POINT_ID IS NULL ");
            }

        }
        int start = (deviceVO.getPageNo() - 1) * deviceVO.getPageSize();
        sb.append("ORDER BY d.CREATE_TIME DESC ");
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceBO.class));
        return query.list();
    }

    public Integer deviceQueryCount(DeviceVO deviceVO,String citySql,String areaSql){
        StringBuffer countSb = new StringBuffer();
        countSb.append("SELECT count(1) FROM DEVICE d ");
        countSb.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
        countSb.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        countSb.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        countSb.append("WHERE d.DELETED = '" + HasDelete.NO.value + "' ");
        if (deviceVO.getCityId() != null) {
            countSb.append("AND c.ID = '" + deviceVO.getCityId() + "' ");
        }else {
            if (StringUtils.isNotBlank(citySql)) {
                countSb.append("AND c.ID in (" + citySql + ") ");
            }
        }
        if (deviceVO.getAreaId() != null) {
            countSb.append("AND a.ID = '" + deviceVO.getAreaId() + "' ");
        }else {
            if (StringUtils.isNotBlank(areaSql)) {
                countSb.append("AND a.ID in (" + areaSql + ") ");
            }
        }
        if (deviceVO.getRoadCrossPointId() != null) {
            countSb.append("AND r.ID = '" + deviceVO.getRoadCrossPointId() + "' ");
        }
        if (deviceVO.getType() != null) {
            countSb.append("AND d.TYPE = '" + deviceVO.getType() + "' ");
        }
        if (deviceVO.getDevided() != null) {
            if (deviceVO.getDevided() == 1){//1已划分
                countSb.append("AND d.ROAD_CROSS_POINT_ID IS NOT NULL ");
            }
            if(deviceVO.getDevided() == 0){//0未划分
                countSb.append("AND d.ROAD_CROSS_POINT_ID IS NULL ");
            }

        }
        SQLQuery queryCount = getSession().createSQLQuery(countSb.toString());
        Integer count = Integer.valueOf(((BigInteger) queryCount.uniqueResult()).toString());
        return count;
    }
    public List<DeviceBO> terminalQuery(DeviceVO deviceVO,String citySql,String areaSql){
        StringBuffer terminalSQL = new StringBuffer();
        terminalSQL.append("SELECT t.ID terminalId,t. NAME name,t.TYPE type,t. ONLINE online,c. NAME cityName,a. NAME areaName," +
                "r. NAME roadName,m.ID monitorCenterId,t.INSTALL_ADDRESS installAddress,t.LATITUDE latitude,t.LONGITUDE longitude,c.ID cityId," +
                "a.ID areaId,r.ID roadCrossPointId,t.IP ip,t.PORT port,t.DEVICE_NUM deviceNum FROM TERMINAL t ");
        terminalSQL.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = t.ROAD_CROSS_POINT_ID ");
        terminalSQL.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        terminalSQL.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        terminalSQL.append("LEFT JOIN MONITOR_CENTER m ON m.ID = c.MONITOR_CENTER_ID ");
        terminalSQL.append("WHERE t.DELETED = '" + HasDelete.NO.value + "' ");
        if (deviceVO.getCityId() != null) {
            terminalSQL.append("AND c.ID = '" + deviceVO.getCityId() + "' ");
        }else {
            if (StringUtils.isNotBlank(citySql)) {
                terminalSQL.append("AND c.ID in (" + citySql + ") ");
            }
        }
        if (deviceVO.getAreaId() != null) {
            terminalSQL.append("AND a.ID = '" + deviceVO.getAreaId() + "' ");
        }else {
            if (StringUtils.isNotBlank(areaSql)) {
                terminalSQL.append("AND a.ID in (" + areaSql + ") ");
            }
        }
        if (deviceVO.getRoadCrossPointId() != null) {
            terminalSQL.append("AND r.ID = '" + deviceVO.getRoadCrossPointId() + "' ");
        }
        if (deviceVO.getOnline() != null) {
            terminalSQL.append("AND t.ONLINE = '" + deviceVO.getOnline() + "' ");
        }
        int start = (deviceVO.getPageNo() - 1) * deviceVO.getPageSize();
        terminalSQL.append("ORDER BY t.CREATE_TIME DESC ");
        SQLQuery query = getSession().createSQLQuery(terminalSQL.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceBO.class));
        return query.list();
    }

    public Integer terminalQueryCount(DeviceVO deviceVO,String citySql,String areaSql){
        StringBuffer terminalcountSQL = new StringBuffer();
        terminalcountSQL.append("SELECT count(1) FROM TERMINAL t ");
        terminalcountSQL.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = t.ROAD_CROSS_POINT_ID ");
        terminalcountSQL.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        terminalcountSQL.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        terminalcountSQL.append("WHERE t.DELETED = '" + HasDelete.NO.value + "' ");
        if (deviceVO.getCityId() != null) {
            terminalcountSQL.append("AND c.ID = '" + deviceVO.getCityId() + "' ");
        }else {
            if (StringUtils.isNotBlank(citySql)) {
                terminalcountSQL.append("AND c.ID in (" + citySql + ") ");
            }
        }
        if (deviceVO.getAreaId() != null) {
            terminalcountSQL.append("AND a.ID = '" + deviceVO.getAreaId() + "' ");
        }else {
            if (StringUtils.isNotBlank(areaSql)) {
                terminalcountSQL.append("AND a.ID in (" + areaSql + ") ");
            }
        }
        if (deviceVO.getRoadCrossPointId() != null) {
            terminalcountSQL.append("AND r.ID = '" + deviceVO.getRoadCrossPointId() + "' ");
        }
        if (deviceVO.getOnline() != null) {
            terminalcountSQL.append("AND t.ONLINE = '" + deviceVO.getOnline() + "' ");
        }
        SQLQuery queryCount = getSession().createSQLQuery(terminalcountSQL.toString());
        Integer count = Integer.valueOf(((BigInteger) queryCount.uniqueResult()).toString());
        return count;
    }


    @Override
    public List<DeviceSimpleBO> queryDeviceList(DeviceSimpleVO deviceSimpleVO) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT d.ID AS id,d.`NAME` AS name,d.ONLINE AS online,d.LATITUDE AS latitude,d.LONGITUDE AS longitude FROM DEVICE d  ");
        if (deviceSimpleVO.getCityId() != null) {
            sb.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
            sb.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
            sb.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        }
        sb.append("WHERE d.DELETED = '"+HasDelete.NO.value+"'");
        sb.append("AND (d.TYPE = '"+ DeviceType.BAYONET.value+"' OR d.TYPE = '"+ DeviceType.ELECTRONIC_POLICE.value+"') ");
        if (StringUtils.isNotBlank(deviceSimpleVO.getName())) {
            sb.append("AND d.NAME LIKE '%"+deviceSimpleVO.getName()+"%' ");
        }
        if (deviceSimpleVO.getRoadCrossPointId() != null){
            sb.append("AND d.ROAD_CROSS_POINT_ID='"+deviceSimpleVO.getRoadCrossPointId()+"'");
        }
        if (deviceSimpleVO.getCityId() != null){
            sb.append("AND c.ID='"+deviceSimpleVO.getCityId()+"'");
        }
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceSimpleBO.class));
        List list = query.list();
        return list;
    }


    @Override
    public List<DeviceSimpleBO> queryDeviceListByUser(DeviceSimpleVO deviceSimpleVO,BigInteger userId) {
        Set<BigInteger> cityChecked  = new HashSet<BigInteger>();
        Set<BigInteger> areaChecked  = new HashSet<BigInteger>();
        StringBuffer cityIds = new StringBuffer();
        StringBuffer areaIds = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT d.ID AS id,d.`NAME` AS name,d.ONLINE AS online,d.LATITUDE AS latitude,d.LONGITUDE AS longitude FROM DEVICE d  ");
//        if (deviceSimpleVO.getCityId() != null) {
        sb.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
        sb.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        sb.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
//        }
        sb.append("WHERE d.DELETED = '"+HasDelete.NO.value+"'");
        sb.append("AND (d.TYPE = '"+ DeviceType.BAYONET.value+"' OR d.TYPE = '"+ DeviceType.ELECTRONIC_POLICE.value+"') ");
        if (StringUtils.isNotBlank(deviceSimpleVO.getName())) {
            sb.append("AND d.NAME LIKE '%"+deviceSimpleVO.getName()+"%' ");
        }
        if (deviceSimpleVO.getRoadCrossPointId() != null){
            sb.append("AND d.ROAD_CROSS_POINT_ID='"+deviceSimpleVO.getRoadCrossPointId()+"'");
        }
        if (deviceSimpleVO.getCityId() != null){
            sb.append("AND c.ID='"+deviceSimpleVO.getCityId()+"'");
        }
        cityChecked  = userCityAreaDAO.queryCheckedCityByUserId(userId);
        if(null != cityChecked && cityChecked.size()>0){
            int tempInt = 0;
            for (BigInteger cityid: cityChecked) {
                if(tempInt == 0){
                    cityIds.append(cityid);
                }else{
                    cityIds.append(",").append(cityid);
                }
                tempInt++;
            }
            sb.append("AND c.ID in (" + cityIds.toString() + ") ");
        }else{
            sb.append("AND c.ID ='" + cityIds.toString() + "'");
        }
        areaChecked  = userCityAreaDAO.queryCheckedAreaByUserIdCityIds(userId,cityIds.toString());
        if(null != areaChecked && areaChecked.size()>0){
            int tempInt = 0;
            for (BigInteger cityid: areaChecked) {
                if(tempInt == 0){
                    areaIds.append(cityid);
                }else{
                    areaIds.append(",").append(cityid);
                }
                tempInt++;
            }
            sb.append("AND a.ID in (" + areaIds.toString() + ") ");
        }else{
            sb.append("AND a.ID ='" + areaIds.toString() + "'");
        }
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceSimpleBO.class));
        List list = query.list();
        return list;
    }

    @Override
    public List<DeviceEntity> queryDeviceByDeviceId(String deviceId) {
        Criteria criteria = getCriteria();
        if (deviceId != null) {
            criteria.add(Restrictions.eq("deviceId", deviceId));
        }
        return criteria.list();
    }

    @Override
    public List<DeviceQueryVO> queryInfoByDeviceId(BigInteger deviceId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT r.`NAME` roadName,a.`NAME` areaName,c.`NAME` cityName,a.ID areaId,c.ID cityId,r.ID roadCrossPointId," +
                "d.NAME deviceName,d.TERMINAL_ID terminalId FROM DEVICE d ");
        sb.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
        sb.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        sb.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        if (deviceId != null){
            sb.append("WHERE d.ID = '"+deviceId+"'");
        }
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceQueryVO.class));
        return  query.list();
    }

    @Override
    public List<BigInteger> selectOnlineTerminal() throws ParseException {
        //暂定扫描两个小时内的过车信息
        String endTime = DateTimeUtils.getNowTime();
        String startTime = DateTimeUtils.addDateHour(endTime,-2);
        StringBuffer querySql =  new StringBuffer();
        querySql.append("SELECT DISTINCT d.TERMINAL_ID FROM DEVICE d WHERE d.ID in( ");
        querySql.append("SELECT DISTINCT p.DEVICE_ID FROM PASSEDBY_VEHICLE p WHERE ");
        querySql.append("p.CAPTURE_TIME>='"+startTime +"' AND p.CAPTURE_TIME<='"+endTime+"') ");
        SQLQuery query = getSession().createSQLQuery(querySql.toString());
        return  query.list();
    }

    /**
     * 查询没有删除的电子警察部分信息
     * @return
     */
    @Override
    public List<DeviceRedisVO> queryDevicetoRedis() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT d.ID deviceId ,d.`NAME` deviceName, d.BAYONET_NO bayonetNo," +
                "c.ID cityId,c.`NAME` cityName,a.ID areaId,a.`NAME` areaName," +
                "r.ID roadId,r.`NAME` roadName FROM DEVICE d " +
                "LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID " +
                "LEFT JOIN AREA a ON a.ID = r.AREA_ID " +
                "LEFT JOIN CITY c ON c.ID = a.CITY_ID " +
                "WHERE d.DELETED = '"+HasDelete.NO.value+"'" +
                " AND d.TYPE = '"+DeviceType.ELECTRONIC_POLICE.value+"' ");
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceRedisVO.class));
        return  query.list();
    }
}

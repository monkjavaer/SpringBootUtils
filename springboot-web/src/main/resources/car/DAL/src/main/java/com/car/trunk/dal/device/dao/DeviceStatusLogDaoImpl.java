package com.car.trunk.dal.device.dao;

import com.car.base.common.constant.EnvConstant;
import com.car.base.common.utilities.PageList;
import com.car.base.common.utilities.PageListUtil;
import com.car.base.dao.daos.BaseDao;
import com.car.base.jdbc.BatchSqlWithJdbcTemplate;
import com.car.trunk.dal.device.bo.DeviceLogBO;
import com.car.trunk.dal.device.bo.DeviceLogDTO;
import com.car.trunk.dal.device.vo.DeviceLogVO;
import com.car.trunk.dal.dictionary.DeviceType;
import com.car.trunk.dal.dictionary.HasOnline;
import com.car.trunk.dal.model.DeviceStatusLogEntity;
import com.car.trunk.dal.util.AuthorityUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Repository("DeviceStatusLogDao")
public class DeviceStatusLogDaoImpl extends BaseDao<DeviceStatusLogEntity> implements DeviceStatusLogDao {

    /**
     * 用户城市区域DAO接口
     */
    @Autowired
    private UserCityAreaDAO userCityAreaDAO;

    @Override
    public DeviceLogDTO queryList(DeviceLogVO deviceLogVO, BigInteger userId, String userName) throws ParseException {
        List<DeviceLogBO> resultList = new ArrayList<DeviceLogBO>();
        int count = 0;
        int onlineCount = 0;
        Set<BigInteger> cityChecked = null;
        Set<BigInteger> areaChecked = null;
        String cityIds = null;
        String areaIds = null;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (!EnvConstant.SUPERADMIN.equals(userName)) {
            if (deviceLogVO.getCityId() == null) {
                cityChecked = userCityAreaDAO.queryCheckedCityByUserId(userId);
                cityIds = AuthorityUtil.getIdSqlByIdS(cityChecked);
            }else {
                cityIds = deviceLogVO.getCityId().toString();
            }
            if (deviceLogVO.getAreaId() == null) {
                areaChecked = userCityAreaDAO.queryCheckedAreaByUserIdCityIds(userId, cityIds);
                areaIds = AuthorityUtil.getIdSqlByIdS(areaChecked);
            }
        }
        if (DeviceType.ELECTRONIC_POLICE.value.equals(deviceLogVO.getType())) {
            resultList = deviceLogQuery(deviceLogVO, cityIds, areaIds);
            count = deviceLogQueryCount(deviceLogVO, cityIds, areaIds);
            deviceLogVO.setOnline(HasOnline.YES.value);
            onlineCount = deviceLogQueryCount(deviceLogVO, cityIds, areaIds);
        } else if (DeviceType.HOST.value.equals(deviceLogVO.getType())) {
            resultList = terminalLogQuery(deviceLogVO, cityIds, areaIds);
            count = terminalLogQueryCount(deviceLogVO, cityIds, areaIds);
            deviceLogVO.setOnline(HasOnline.YES.value);
            onlineCount = terminalLogQueryCount(deviceLogVO, cityIds, areaIds);
        } else {
            List<DeviceLogBO> resultList1 = deviceLogQuery(deviceLogVO, cityIds, areaIds);
            List<DeviceLogBO> resultList2 = terminalLogQuery(deviceLogVO, cityIds, areaIds);
            resultList.addAll(resultList1);
            resultList.addAll(resultList2);
            count = deviceLogQueryCount(deviceLogVO, cityIds, areaIds) + terminalLogQueryCount(deviceLogVO, cityIds, areaIds);
            deviceLogVO.setOnline(HasOnline.YES.value);
            onlineCount = deviceLogQueryCount(deviceLogVO, cityIds, areaIds) + terminalLogQueryCount(deviceLogVO, cityIds, areaIds);
        }

        for (DeviceLogBO deviceLogBO : resultList) {
            Integer hour = hourBetween(deviceLogBO.getCreateTime(), deviceLogBO.getRecentTime());
            double rate = 0.0;
            if (hour > 0) {
                rate = Double.valueOf(decimalFormat.format((double) deviceLogBO.getContinuedTime() / (double) hour)) * 100;
            }
            if (rate <= 0){
                rate = 0.0;
            }
            if (rate >= 100){
                rate = 100.0;
            }
            deviceLogBO.setOnlineRate((float) rate);
            deviceLogBO.setInstallAddress("");
            if (deviceLogBO.getContinuedTime() <= 0) {
                deviceLogBO.setContinuedTime(0);
            }
        }


        Collections.sort(resultList, new Comparator<DeviceLogBO>() {
            @Override
            public int compare(DeviceLogBO o1, DeviceLogBO o2) {
                if (deviceLogVO.getSort() == 0 || deviceLogVO.getSort() == 1) {
                    if (o1.getOnline() > o2.getOnline()) {
                        return 1;
                    }
                    if (o1.getOnline() < o2.getOnline()) {
                        return -1;
                    }
                } else {
                    if (o1.getOnline() > o2.getOnline()) {
                        return -1;
                    }
                    if (o1.getOnline() < o2.getOnline()) {
                        return 1;
                    }
                }
                return 0;
            }
        });


        int toIndex = ((deviceLogVO.getPageNo() * deviceLogVO.getPageSize()) > resultList.size()) ? (resultList.size()) : (deviceLogVO.getPageNo() * deviceLogVO.getPageSize());
        List<DeviceLogBO> subList = resultList.subList((deviceLogVO.getPageNo() - 1) * deviceLogVO.getPageSize(), toIndex);
        // 指定页的数据
        PageList<DeviceLogBO> pageList = PageListUtil.getPageList(resultList.size(), deviceLogVO.getPageNo(), subList, deviceLogVO.getPageSize());

        DeviceLogDTO deviceLogDTO = new DeviceLogDTO();
        deviceLogDTO.setPageList(pageList);
        if (count > 0) {
            double rate = (double) onlineCount / (double) count;
            Double similarity = Double.valueOf(new DecimalFormat("#.00").format(rate)) * 100;
            deviceLogDTO.setTotalRate(similarity);
        }
        return deviceLogDTO;
    }


    /**
     * 获取两个时间小时差
     */
    public Integer hourBetween(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(startDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(endDate));
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 60 * 60);

        return Integer.parseInt(String.valueOf(betweenDays));
    }


    @Override
    public DeviceStatusLogEntity querybyDeviceId(BigInteger id) {
        Criteria criteria = getCriteria();
        if (id != null) {
            criteria.add(Restrictions.eq("deviceId", id));
        }
        List<DeviceStatusLogEntity> items = criteria.list();
        return items.get(0);
    }

    @Override
    public DeviceStatusLogEntity querybyterminalId(BigInteger id) {
        Criteria criteria = getCriteria();
        if (id != null) {
            criteria.add(Restrictions.eq("terminalId", id));
        }
        List<DeviceStatusLogEntity> items = criteria.list();
        return items.get(0);
    }


    public List<DeviceLogBO> deviceLogQuery(DeviceLogVO deviceLogVO, String cityIds, String areaIds) {

        StringBuffer deviceSql = new StringBuffer();
        deviceSql.append("SELECT d.`NAME` name,c.`NAME` cityName,a.`NAME` areaName,r.`NAME` roadName,dl.TYPE type,dl.TOTAL_TIME continuedTime," +
                "dl.`ONLINE` online,DATE_FORMAT(d.CREATE_TIME,'%d-%m-%Y %H:%i:%s') createTime,DATE_FORMAT(dl.LASTUPDATE_TIME,'%d-%m-%Y %H:%i:%s') recentTime  FROM DEVICE_STATUS_LOG dl ");
        deviceSql.append("LEFT JOIN DEVICE d ON d.ID =  dl.DEVICE_ID ");
        deviceSql.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
        deviceSql.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        deviceSql.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        deviceSql.append("WHERE 1=1 AND dl.DEVICE_ID IS NOT NULL ");
        if (deviceLogVO.getCityId() != null) {
            deviceSql.append("AND c.ID=" + deviceLogVO.getCityId() + " ");
        } else {
            if (StringUtils.isNotBlank(cityIds)) {
                deviceSql.append("AND c.ID in (" + cityIds + ") ");
            }
        }
        if (deviceLogVO.getAreaId() != null) {
            deviceSql.append("AND a.ID=" + deviceLogVO.getAreaId() + " ");
        } else {
            if (StringUtils.isNotBlank(areaIds)) {
                deviceSql.append("AND a.ID in (" + areaIds + ") ");
            }
        }
        if (deviceLogVO.getRoadCrossPointId() != null) {
            deviceSql.append("AND r.ID=" + deviceLogVO.getRoadCrossPointId() + " ");
        }
        if (deviceLogVO.getType() != null) {
            deviceSql.append("AND dl.TYPE='" + deviceLogVO.getType() + "' ");
        }
        if (deviceLogVO.getOnline() != null) {
            deviceSql.append("AND dl.`ONLINE`='" + deviceLogVO.getOnline() + "' ");
        }


        SQLQuery query = getSession().createSQLQuery(deviceSql.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceLogBO.class));
        return query.list();
    }

    public Integer deviceLogQueryCount(DeviceLogVO deviceLogVO, String cityIds, String areaIds) {
        StringBuffer deviceSql = new StringBuffer();
        deviceSql.append("SELECT count(1)  FROM DEVICE_STATUS_LOG dl ");
        deviceSql.append("LEFT JOIN DEVICE d ON d.ID =  dl.DEVICE_ID ");
        deviceSql.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = d.ROAD_CROSS_POINT_ID ");
        deviceSql.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        deviceSql.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        deviceSql.append("WHERE 1=1 AND dl.DEVICE_ID IS NOT NULL ");
        if (deviceLogVO.getCityId() != null) {
            deviceSql.append("AND c.ID=" + deviceLogVO.getCityId() + " ");
        } else {
            if (StringUtils.isNotBlank(cityIds)) {
                deviceSql.append("AND c.ID in (" + cityIds + ") ");
            }
        }
        if (deviceLogVO.getAreaId() != null) {
            deviceSql.append("AND a.ID=" + deviceLogVO.getAreaId() + " ");
        } else {
            if (StringUtils.isNotBlank(areaIds)) {
                deviceSql.append("AND a.ID in (" + areaIds + ") ");
            }
        }
        if (deviceLogVO.getRoadCrossPointId() != null) {
            deviceSql.append("AND r.ID=" + deviceLogVO.getRoadCrossPointId() + " ");
        }
        if (deviceLogVO.getType() != null) {
            deviceSql.append("AND dl.TYPE='" + deviceLogVO.getType() + "' ");
        }
        if (deviceLogVO.getOnline() != null) {
            deviceSql.append("AND dl.`ONLINE`='" + deviceLogVO.getOnline() + "' ");
        }

        SQLQuery queryCount = getSession().createSQLQuery(deviceSql.toString());
        Integer count = Integer.valueOf(((BigInteger) queryCount.uniqueResult()).toString());
        return count;
    }

    public List<DeviceLogBO> terminalLogQuery(DeviceLogVO deviceLogVO, String cityIds, String areaIds) {
        StringBuffer deviceSql = new StringBuffer();
        deviceSql.append("SELECT t.`NAME` name,c.`NAME` cityName,a.`NAME` areaName,r.`NAME` roadName,dl.TYPE type,dl.TOTAL_TIME continuedTime," +
                "dl.`ONLINE` online,DATE_FORMAT(t.CREATE_TIME,'%d-%m-%Y %H:%i:%s') createTime,DATE_FORMAT(dl.LASTUPDATE_TIME,'%d-%m-%Y %H:%i:%s') recentTime  FROM DEVICE_STATUS_LOG dl ");
        deviceSql.append("LEFT JOIN TERMINAL t ON t.ID =  dl.TERMINAL_ID ");
        deviceSql.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = t.ROAD_CROSS_POINT_ID ");
        deviceSql.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        deviceSql.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        deviceSql.append("WHERE 1=1 AND dl.TERMINAL_ID IS NOT NULL ");
        if (deviceLogVO.getCityId() != null) {
            deviceSql.append("AND c.ID=" + deviceLogVO.getCityId() + " ");
        } else {
            if (StringUtils.isNotBlank(cityIds)) {
                deviceSql.append("AND c.ID in (" + cityIds + ") ");
            }
        }
        if (deviceLogVO.getAreaId() != null) {
            deviceSql.append("AND a.ID=" + deviceLogVO.getAreaId() + " ");
        } else {
            if (StringUtils.isNotBlank(areaIds)) {
                deviceSql.append("AND a.ID in (" + areaIds + ") ");
            }
        }
        if (deviceLogVO.getRoadCrossPointId() != null) {
            deviceSql.append("AND r.ID=" + deviceLogVO.getRoadCrossPointId() + " ");
        }
        if (deviceLogVO.getType() != null) {
            deviceSql.append("AND dl.TYPE='" + deviceLogVO.getType() + "' ");
        }
        if (deviceLogVO.getOnline() != null) {
            deviceSql.append("AND dl.`ONLINE`='" + deviceLogVO.getOnline() + "' ");
        }

        SQLQuery query = getSession().createSQLQuery(deviceSql.toString());
        query.setResultTransformer(Transformers.aliasToBean(DeviceLogBO.class));
        return query.list();
    }

    public Integer terminalLogQueryCount(DeviceLogVO deviceLogVO, String cityIds, String areaIds) {
        StringBuffer deviceSql = new StringBuffer();
        deviceSql.append("SELECT count(1) FROM DEVICE_STATUS_LOG dl ");
        deviceSql.append("LEFT JOIN TERMINAL t ON t.ID =  dl.TERMINAL_ID ");
        deviceSql.append("LEFT JOIN ROAD_CROSS_POINT r ON r.ID = t.ROAD_CROSS_POINT_ID ");
        deviceSql.append("LEFT JOIN AREA a ON a.ID = r.AREA_ID ");
        deviceSql.append("LEFT JOIN CITY c ON c.ID = a.CITY_ID ");
        deviceSql.append("WHERE 1=1 AND dl.TERMINAL_ID IS NOT NULL ");
        if (deviceLogVO.getCityId() != null) {
            deviceSql.append("AND c.ID=" + deviceLogVO.getCityId() + " ");
        } else {
            if (StringUtils.isNotBlank(cityIds)) {
                deviceSql.append("AND c.ID in (" + cityIds + ") ");
            }
        }
        if (deviceLogVO.getAreaId() != null) {
            deviceSql.append("AND a.ID=" + deviceLogVO.getAreaId() + " ");
        } else {
            if (StringUtils.isNotBlank(areaIds)) {
                deviceSql.append("AND a.ID in (" + areaIds + ") ");
            }
        }
        if (deviceLogVO.getRoadCrossPointId() != null) {
            deviceSql.append("AND r.ID=" + deviceLogVO.getRoadCrossPointId() + " ");
        }
        if (deviceLogVO.getType() != null) {
            deviceSql.append("AND dl.TYPE='" + deviceLogVO.getType() + "' ");
        }
        if (deviceLogVO.getOnline() != null) {
            deviceSql.append("AND dl.`ONLINE`='" + deviceLogVO.getOnline() + "' ");
        }
        SQLQuery queryCount = getSession().createSQLQuery(deviceSql.toString());
        Integer count = Integer.valueOf(((BigInteger) queryCount.uniqueResult()).toString());
        return count;
    }

    @Override
    public void batchUpdateDeviceStatusLog(List<DeviceStatusLogEntity> deviceStatusLogEntityList) throws DataAccessException {
        String updateSql = "update DEVICE_STATUS_LOG set ONLINE = ? , LASTUPDATE_TIME = ? where TERMINAL_ID = ?";
        List<Object[]> dataSet = new ArrayList<Object[]>();
        for (int i = 0; i <deviceStatusLogEntityList.size() ; i++) {
            Object[] objectsTemp = new Object[3];
            objectsTemp[0] = deviceStatusLogEntityList.get(i).getOnline();
            objectsTemp[1] = deviceStatusLogEntityList.get(i).getLastupdateTime();
            objectsTemp[2] = deviceStatusLogEntityList.get(i).getTerminalId();
            dataSet.add(objectsTemp);
        }
        // define SQL types of the arguments
        int[] types = new int[] { Types.BIT, Types.DATE , Types.BIGINT};
        BatchSqlWithJdbcTemplate.updateBatch(updateSql,dataSet,types);
        System.out.println(updateSql);
    }

}

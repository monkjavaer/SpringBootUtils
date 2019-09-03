package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.system.dao;

import com.base.springboot.car.Base.src.main.java.com.car.base.common.constant.EnvConstant;
import com.base.springboot.car.Base.src.main.java.com.car.base.dao.daos.BaseDao;
import com.base.springboot.car.Base.src.main.java.com.car.base.model.models.ResultTuple;
import com.car.trunk.dal.model.UserLogEntity;
import com.car.trunk.dal.system.bo.UserLogBO;
import com.car.trunk.dal.system.vo.UserLogVO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by monkjavaer on 2017/12/15 0015.
 */
@Repository("UserLogDao")
public class UserLogDaoImpl extends BaseDao<UserLogEntity> implements UserLogDao{

    /**
     * 用户操作日志分页列表,只展示本机构下用户日志
     * @param userLogVO
     * @param userName 用户名
     * @param monitorCenterId 机构id
     * @return
     */
    @Override
    public ResultTuple<UserLogBO> queryList(UserLogVO userLogVO,String userName,BigInteger monitorCenterId) {
        StringBuffer sb = new StringBuffer();
        StringBuffer countSb = new StringBuffer();
        sb.append("SELECT u.`NAME` AS name,u.`USERNAME` AS userName,ul.DATA_TYPE AS dataType,ul.ACTION_TYPE AS actionType,DATE_FORMAT(ul.CREATE_TIME,'%d-%m-%Y %H:%i:%s') AS createTime,ul.DESCRIPTION AS description FROM USER_LOG ul ");
        sb.append("LEFT JOIN `USER` u ON u.ID = ul.USER_ID ");
        sb.append("LEFT JOIN MONITOR_CENTER m ON m.ID = u.MONITOR_CENTER_ID ");

        countSb.append("SELECT count(1) FROM USER_LOG ul ");
        countSb.append("LEFT JOIN `USER` u ON u.ID = ul.USER_ID ");
        countSb.append("LEFT JOIN MONITOR_CENTER m ON m.ID = u.MONITOR_CENTER_ID ");

        sb.append("WHERE 1=1 ");

        countSb.append("WHERE 1=1 ");

        if (!EnvConstant.SUPERADMIN.equals(userName)) {
            sb.append("AND m.ID = ").append(monitorCenterId).append(" ");
            countSb.append("AND m.ID = ").append(monitorCenterId).append(" ");
            sb.append("AND u.USERNAME !='"+EnvConstant.SUPERADMIN+"' ");
            countSb.append("AND u.USERNAME !='"+EnvConstant.SUPERADMIN+"' ");
        }

        if (StringUtils.isNotBlank(userLogVO.getUser())){
            sb.append("AND (u.USERNAME like '%" + userLogVO.getUser() + "%' OR u.NAME like '%" + userLogVO.getUser() + "%')");
            countSb.append("AND (u.USERNAME like '%" + userLogVO.getUser() + "%' OR u.NAME like '%" + userLogVO.getUser() + "%')");
        }

        if (userLogVO.getActionType() != null){
            sb.append("AND ul.ACTION_TYPE = '"+userLogVO.getActionType()+"' ");
            countSb.append("AND ul.ACTION_TYPE = '"+userLogVO.getActionType()+"' ");
        }
        if (userLogVO.getDataType() != null){
            sb.append("AND ul.DATA_TYPE = '"+userLogVO.getDataType()+"' ");
            countSb.append("AND ul.DATA_TYPE = '"+userLogVO.getDataType()+"' ");
        }
        if (StringUtils.isNotEmpty(userLogVO.getStartTime())) {
            sb.append("AND ul.CREATE_TIME >= '" + userLogVO.getStartTime() + "' ");
            countSb.append("AND ul.CREATE_TIME >= '" + userLogVO.getStartTime() + "' ");
        }
        if (StringUtils.isNotEmpty(userLogVO.getEndTime())) {
            sb.append("AND ul.CREATE_TIME  <= '" + userLogVO.getEndTime() + "' ");
            countSb.append("AND ul.CREATE_TIME  <= '" + userLogVO.getEndTime() + "' ");

        }
        int start = (userLogVO.getPageNo() - 1) * userLogVO.getPageSize();
        sb.append("ORDER BY ul.CREATE_TIME DESC limit " + start + "," + userLogVO.getPageSize() + "");
        SQLQuery queryCount = getSession().createSQLQuery(countSb.toString());
        Integer count = Integer.valueOf(((BigInteger) queryCount.uniqueResult()).toString());
        SQLQuery query = getSession().createSQLQuery(sb.toString());
        query.setResultTransformer(Transformers.aliasToBean(UserLogBO.class));
        List list = query.list();
        return new ResultTuple<UserLogBO>(list, count);
    }
}

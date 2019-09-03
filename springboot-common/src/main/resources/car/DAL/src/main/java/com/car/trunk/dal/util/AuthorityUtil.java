package com.base.springboot.car.DAL.src.main.java.com.car.trunk.dal.util;

import java.math.BigInteger;
import java.util.Set;

/**
 * @author monkjavaer
 * @date 2018/8/8 15:11
 */
public class AuthorityUtil {

    /**
     * 根据id集合拼接sql，用于数据权限过滤
     * @param ids
     * @return
     */
    public static String getIdSqlByIdS(Set<BigInteger> ids){
        StringBuilder sql = new StringBuilder();
        if (null != ids && ids.size() > 0) {
            int tempInt = 0;
            for (BigInteger id : ids) {
                if (tempInt == 0) {
                    sql.append(id);
                } else {
                    sql.append(",").append(id);
                }
                tempInt++;
            }
        } else {
            sql.append("null");
        }
        return sql.toString();
    }

}

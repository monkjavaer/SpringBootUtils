package com.base.springboot.car.Base.src.main.java.com.car.base.common.utilities;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.*;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * <p>Description:查询辅助工具类 </p>
 *
 * @author monkjavaer
 * @version 1.0 创建时间:2017-12-08 9:29
 */
public class DataQueryUtil {

    public static  <T> PageList<T> getPageList(Session session, Map<String,String> map, String hql, Class<T> objectClass, int pageIndex, int pageSize){
        // 拼接SQL
        StringBuilder listSql = new StringBuilder("SELECT ");
        for (Map.Entry<String,String> entry : map.entrySet()) {
            listSql.append(entry.getValue()).append(" ").append(entry.getKey()).append(",");
        }
        String listSqlPrefix = listSql.substring(0, listSql.length() - 1) + " ";
        String countSqlPrefix = "SELECT COUNT(*) ";
        String listSqlSuffix = " LIMIT " + (pageIndex - 1) * pageSize + "," + pageSize;
        SQLQuery listSqlQuery = session.createSQLQuery(listSqlPrefix + hql + listSqlSuffix);
        SQLQuery countSqlQuery = session.createSQLQuery(countSqlPrefix + hql);
        // 循环目标对象
        for (Field field : objectClass.getDeclaredFields()) {
            // 字段名
            String fieldName = field.getName();
            // 类型名
            String typeName = field.getType().getName();
            // 如果字段在Map里获取不到，则认为不会被查询
            if (map.get(fieldName) == null) {
                continue;
            }
            if ("java.math.BigInteger".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, BigIntegerType.INSTANCE);
                continue;
            }
            if ("java.math.BigDecimal".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, BigDecimalType.INSTANCE);
                continue;
            }
            if ("java.lang.Boolean".equals(typeName) || "boolean".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, BooleanType.INSTANCE);
                continue;
            }
            if ("java.lang.Byte".equals(typeName) || "byte".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, ByteType.INSTANCE);
                continue;
            }
            if ("java.lang.Character".equals(typeName) || "char".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, CharacterType.INSTANCE);
                continue;
            }
            if ("java.util.Calendar".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, CalendarType.INSTANCE);
                continue;
            }
            if ("java.lang.Class".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, ClassType.INSTANCE);
                continue;
            }
            if ("java.sql.Date".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, DateType.INSTANCE);
                continue;
            }
            if ("java.util.Date".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, TimestampType.INSTANCE);
                continue;
            }
            if ("".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, DateType.INSTANCE);
                continue;
            }
            if ("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, DoubleType.INSTANCE);
                continue;
            }
            if ("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, FloatType.INSTANCE);
                continue;
            }
            if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, LongType.INSTANCE);
                continue;
            }
            if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, IntegerType.INSTANCE);
                continue;
            }
            if ("java.lang.Object".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, ObjectType.INSTANCE);
                continue;
            }
            if ("java.lang.Short".equals(typeName) || "short".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, ShortType.INSTANCE);
                continue;
            }
            if ("java.lang.String".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, StringType.INSTANCE);
                continue;
            }
            if ("java.sql.Timestamp".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, TimestampType.INSTANCE);
                continue;
            }
            if ("java.sql.Time".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, TimeType.INSTANCE);
                continue;
            }
            if ("java.net.Url".equals(typeName)) {
                listSqlQuery.addScalar(fieldName, UrlType.INSTANCE);
                continue;
            }
            throw new IllegalArgumentException("Field Type Is Not Perfect");
        }
        listSqlQuery.setResultTransformer(Transformers.aliasToBean(objectClass));
        List<T> list = listSqlQuery.list();
        BigInteger total = (BigInteger) countSqlQuery.uniqueResult();
        return (PageList<T>) PageListUtil.getPageList(total.intValue(), pageIndex, list, pageSize);
    }
}

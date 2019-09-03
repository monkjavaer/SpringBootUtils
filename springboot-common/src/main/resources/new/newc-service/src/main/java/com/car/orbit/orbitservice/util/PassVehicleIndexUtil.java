package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitutil.es.ESUtils;
import com.car.orbit.orbitutil.tools.DateUtils;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @Title: PassVehicleIndexUtil
 * @Package: com.car.orbit.orbitservice.util
 * @Description: 根据起始时间点，计算ES数据库索引集合
 * @Author: monkjavaer
 * @Date: 2019/03/16 16:51
 * @Version: V1.0
 */
public class PassVehicleIndexUtil {

    public static final String ORBIT_PASS_VEHICLE_RECORD = "orbit_pass_vehicle_record_";
    public static final int NEARBY_WEEK_COUNT = 10;

    /**
     * 根据时间范围计算ES索引集合，一般有多个 orbit_pass_vehicle_record_[year]_[week]
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String[] calculateIndexCollection(String startTime, String endTime) {
        /** 取现在时间和endTime较早的 **/
        Date now = new Date();
        if (now.compareTo(DateUtils.getDate(endTime)) < 0) {
            endTime = DateUtils.format(now);
        }

        /** 判断是否有必要刷新ES索引缓存 **/
        List<String> cacheIndexList = new ArrayList<>(ESUtils.indexSet);
        if (cacheIndexList.size() > 0) {
            String lastIndex = cacheIndexList.get(cacheIndexList.size() - 1);
            int year = DateUtils.getYearOfDate(DateUtils.getDate(endTime));
            int week = DateUtils.getWeekInYear(DateUtils.getDate(endTime));
            String index = new StringBuilder().append(ORBIT_PASS_VEHICLE_RECORD)
                    .append(year).append("_")
                    .append(new DecimalFormat("00").format(week))
                    .toString();
            if (index.compareTo(lastIndex) > 0) {
                ESUtils.refreshIndexCache();
            }
        } else {
            ESUtils.refreshIndexCache();
        }

        //ESUtils.refreshIndexCache();

        Date startDt = DateUtils.getDate(startTime);
        Date endDt = DateUtils.getDate(endTime);

        Set<String> set = new HashSet<>();
        List<String> indexList = new ArrayList<>();

        Date dt = startDt;
        while (dt.compareTo(endDt) <= 0) {
            getIndexFromDate(dt, set, indexList);
            dt = DateUtils.addOneWeek(dt);
        }

        /** 处理跨年的时间点, 例如[2018-10-01,2019-02-01],需要提取2018-12-31和2019-01-01处理一次 **/
        int startYear = DateUtils.getYearOfDate(startDt);
        int endYear = DateUtils.getYearOfDate(endDt);
        if (startYear < endYear) {
            /** 单独处理startYear这一年的最后一天 **/
            getIndexFromDate(DateUtils.getYearLast(startYear), set, indexList);
            /** 单独处理endYear这一年的第一天 **/
            getIndexFromDate(DateUtils.getYearFirst(endYear), set, indexList);
        }

        /** 当endTime是当年最后一天，提取最后一天算一下 例如[2018-01-02,2018-12-31] **/
        getIndexFromDate(endDt, set, indexList);

        return indexList.toArray(new String[indexList.size()]);
    }

    /**
     * 计算从captureTime往前推N周的有效索引集合
     *
     * @param captureTime
     * @return
     */
    public static String[] calculateEarlierIndexCollection(String captureTime) {
        ESUtils.refreshIndexCache();

        Set<String> set = new HashSet<>();
        List<String> indexList = new ArrayList<>();

        int count = NEARBY_WEEK_COUNT;
        Date dt = DateUtils.getDate(captureTime);
        do  {
            getIndexFromDate(dt, set, indexList);
            dt = DateUtils.addDays(dt, -7);
            count--;
        } while (count > 0);

        return indexList.toArray(new String[indexList.size()]);
    }

    /**
     * 计算从captureTime往后推N周的有效索引集合
     *
     * @param captureTime
     * @return
     */
    public static String[] calculateLaterIndexCollection(String captureTime) {
        ESUtils.refreshIndexCache();

        Set<String> set = new HashSet<>();
        List<String> indexList = new ArrayList<>();

        int count = NEARBY_WEEK_COUNT;
        Date dt = DateUtils.getDate(captureTime);
        do  {
            getIndexFromDate(dt, set, indexList);
            dt = DateUtils.addDays(dt, 7);
            count--;
        } while (count > 0);

        return indexList.toArray(new String[indexList.size()]);
    }

    /**
     * 通过给定的Date计算过车记录表索引,若满足一定条件加入到IndexList
     *
     * @param dt
     * @param set
     * @param indexList
     */
    private static void getIndexFromDate(Date dt, Set<String> set, List<String> indexList) {
        int year = DateUtils.getYearOfDate(dt);
        int week = DateUtils.getWeekInYear(dt);

        String index = new StringBuilder().append(ORBIT_PASS_VEHICLE_RECORD)
                .append(year).append("_")
                .append(new DecimalFormat("00").format(week))
                .toString();
        if (!set.contains(index) && ESUtils.isIndexExist(index)) {
            indexList.add(index);
            set.add(index);
        }
    }
}

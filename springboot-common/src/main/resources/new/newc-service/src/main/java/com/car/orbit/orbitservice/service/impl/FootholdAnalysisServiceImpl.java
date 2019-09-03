package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.*;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.qo.FootholdQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.IFootholdAnalysisService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitutil.es.ESUtils;
import com.car.orbit.orbitutil.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: FootholdAnalysisServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 落脚点分析服务实现类
 * @Author: monkjavaer
 * @Date: 2019/03/28 11:32
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class FootholdAnalysisServiceImpl implements IFootholdAnalysisService {

    public static final int MAX_FOOTHOLD_INTERVAL = 12;
    public static final int MAX_RANK_NUMBER = 10;
    @Autowired
    private TransportClient transportClient;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    /**
     * 落脚点分析
     *
     * @param footholdQO
     * @return
     */
    @Override
    public VehicleSearchBO<FootholdBO> queryPageList(FootholdQO footholdQO) {
        int start = (footholdQO.getPageNo() - 1) * footholdQO.getPageSize();
        String key = footholdQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            // 构建查询条件
            BoolQueryBuilder queryBuilder = createQueryBuilder(footholdQO);

            // 根据时间范围计算索引
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(
                    footholdQO.getStartTime(),
                    footholdQO.getEndTime());

            SearchHits hits = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                    .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .execute()
                    .actionGet()
                    .getHits();

            Map<String, FootholdBO> map = new HashMap<>();
            SearchHit[] hitArray = hits.getHits();
            for (int i = 0; i < hitArray.length - 1; i++) {
                OrbitPassVehicleRecord record1 = JsonUtils.toBean(hitArray[i].getSourceAsString(), OrbitPassVehicleRecord.class);
                OrbitPassVehicleRecord record2 = JsonUtils.toBean(hitArray[i + 1].getSourceAsString(), OrbitPassVehicleRecord.class);
                String startTime = record1.getCaptureTime();
                String endTime = record2.getCaptureTime();
                String deviceId = record1.getDeviceId();

                // 计算两次相邻拍摄时间的时间间隔
                double hour = DateUtils.getHoursBetweenTwoDate(startTime, endTime);
                if (hour >= footholdQO.getMinHour()) {
                    // 落脚时长最长默认为12个小时
                    if (hour >= MAX_FOOTHOLD_INTERVAL) {
                        hour = MAX_FOOTHOLD_INTERVAL;
                        endTime = add12Hours(startTime);
                    }

                    FootholdBO footholdBO;
                    if (map.containsKey(deviceId)) {
                        footholdBO = map.get(deviceId);
                    } else {
                        footholdBO = new FootholdBO();
                        footholdBO.setDeviceId(deviceId);
                        // 设备名
                        OrbitResDevice device = DevicePointRedis.getDevicePointByCode(deviceId);
                        if (device != null) {
                            footholdBO.setDeviceName(device.getName());
                            footholdBO.setLatitude(device.getLatitude());
                            footholdBO.setLongitude(device.getLongitude());
                        }
                        // 设备具体位置信息
                        footholdBO.setDevicePosition(DevicePointRedis.getFullPositionName(deviceId));
                    }

                    footholdBO.setLandingNumber(footholdBO.getLandingNumber() + 1);
                    footholdBO.setTotalHour(fixDouble(footholdBO.getTotalHour() + hour));

                    List<String> daytimeList = new ArrayList<>();
                    calculateDaytime(startTime, endTime, footholdQO.getDaytimeStartHour(),
                            footholdQO.getDaytimeEndHour(), daytimeList);

                    for (int k = 0; k < daytimeList.size() - 1; k += 2) {
                        double dayHour = DateUtils.getHoursBetweenTwoDate(daytimeList.get(k), daytimeList.get(k + 1));
                        footholdBO.setDayHour(footholdBO.getDayHour() + dayHour);
                    }
                    footholdBO.setNightHour(footholdBO.getTotalHour() - footholdBO.getDayHour());

                    map.put(footholdBO.getDeviceId(), footholdBO);
                }
            }

            List<FootholdBO> resultList;
            List<String> filterDeviceIdList = footholdQO.getDeviceIdList();
            if (filterDeviceIdList == null || filterDeviceIdList.size() == 0) {
                filterDeviceIdList = iDeviceService.queryAllDeviceIdList();
            }
            resultList = new ArrayList<>();
            for (Map.Entry<String, FootholdBO> entry : map.entrySet()) {
                if (filterDeviceIdList.contains(entry.getKey())) {
                    resultList.add(entry.getValue());
                }
            }

            /** 计算白天落脚时长占比 **/
            resultList.stream().forEach(footholdBO -> {
                double percentage = (double) footholdBO.getDayHour() / footholdBO.getTotalHour();
                if (percentage > 1) {
                    percentage = 1.0;
                }
                footholdBO.setDaytimePercentage(new DecimalFormat("#.##%").format(percentage));
            });


            /** 按总时长排序 **/
            Collections.sort(resultList, (o1, o2) -> {
                if (o1.getTotalHour() < o2.getTotalHour()) return 1;
                else if (o1.getTotalHour() > o2.getTotalHour()) return -1;
                else return 0;
            });


            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            List<FootholdBO> pageList = redisClient.getListWithPage(searchKey, start, footholdQO.getPageSize(), FootholdBO.class);
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<FootholdBO> pageList = redisClient.getListWithPage(footholdQO.getSearchKey(), start, footholdQO.getPageSize(), FootholdBO.class);
            Integer size = redisClient.getTotalPageSize(footholdQO.getSearchKey());
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(footholdQO.getSearchKey(), size, pageList);
        }
    }

    /**
     * 查询落脚点详细
     *
     * @param footholdQO
     * @return
     */
    @Override
    public VehicleSearchBO<FootholdDetail> queryFootholdDetail(FootholdQO footholdQO) {
        int start = (footholdQO.getPageNo() - 1) * footholdQO.getPageSize();
        String key = footholdQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            // 构建查询条件
            BoolQueryBuilder queryBuilder = createQueryBuilder(footholdQO);

            // 根据时间范围计算索引
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(
                    footholdQO.getStartTime(),
                    footholdQO.getEndTime());

            // Do search
            SearchHits hits = transportClient
                    .prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                    .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .execute()
                    .actionGet()
                    .getHits();

            String filterDeviceId = footholdQO.getDeviceIdList().get(0);

            // 结果解析
            List<FootholdDetail> resultList = new ArrayList<>();
            SearchHit[] searchHit = hits.getHits();
            for (int i = 0; i < searchHit.length - 1; i++) {
                String deviceId = ESUtils.getField(searchHit[i], EsConstant.DEVICE_ID, String.class);
                String startTime = ESUtils.getField(searchHit[i], EsConstant.CAPTURE_TIME, String.class);
                String endTime = ESUtils.getField(searchHit[i + 1], EsConstant.CAPTURE_TIME, String.class);

                if (!deviceId.equals(filterDeviceId)) {
                    continue;
                }

                // 间隔时间
                double hour = DateUtils.getHoursBetweenTwoDate(startTime, endTime);
                if (hour >= footholdQO.getMinHour()) {
                    FootholdDetail footholdDetail = new FootholdDetail();

                    if (hour > MAX_FOOTHOLD_INTERVAL) {
                        hour = MAX_FOOTHOLD_INTERVAL;
                        endTime = add12Hours(startTime);
                    }
                    footholdDetail.setRecordId(ESUtils.getField(searchHit[i], EsConstant.ID, String.class));
                    footholdDetail.setHours(fixDouble(hour));
                    //footholdDetail.setHours(hour);
                    footholdDetail.setCaptureTime(startTime);
                    footholdDetail.setPhotoUrl(ESUtils.getField(searchHit[i], EsConstant.PHOTO_ORI_FASTDFS_URL, String.class));

                    List<String> daytimeList = new ArrayList<>();
                    calculateDaytime(startTime, endTime, footholdQO.getDaytimeStartHour(), footholdQO.getDaytimeEndHour(), daytimeList);
                    footholdDetail.setDaytimePercentage(calculateDaytimePercentage(startTime, endTime, daytimeList));

                    resultList.add(footholdDetail);
                }
            }

            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            List<FootholdDetail> pageList = redisClient.getListWithPage(searchKey, start, footholdQO.getPageSize(), FootholdDetail.class);
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<FootholdDetail> pageList = redisClient.getListWithPage(footholdQO.getSearchKey(), start, footholdQO.getPageSize(), FootholdDetail.class);
            Integer size = redisClient.getTotalPageSize(footholdQO.getSearchKey());
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(footholdQO.getSearchKey(), size, pageList);
        }
    }

    /**
     * 对给定车辆最近7天、最近一个月、最近三个月的落脚点情况进行统计，并根据落脚总时长取Top10
     *
     * @param footholdQO
     * @return
     */
    @Override
    public FootholdStatisticsBO footholdStatistics(FootholdQO footholdQO) {
        Date now = new Date();

        footholdQO.setStartTime(DateUtils.format(DateUtils.addDays(now, -7)));
        footholdQO.setEndTime(DateUtils.format(now));
        List<FootholdBO> list1 = getTop10FootholdByTotalHour(footholdQO);

        footholdQO.setStartTime(DateUtils.format(DateUtils.addMonths(now, -1)));
        footholdQO.setEndTime(DateUtils.format(now));
        List<FootholdBO> list2 = getTop10FootholdByTotalHour(footholdQO);

        footholdQO.setStartTime(DateUtils.format(DateUtils.addMonths(now, -3)));
        footholdQO.setEndTime(DateUtils.format(now));
        List<FootholdBO> list3 = getTop10FootholdByTotalHour(footholdQO);

        return new FootholdStatisticsBO(list1, list2, list3);
    }

    /**
     * 获取指定车辆在一定时间范围内的落脚时长排行，包括总落脚时长排行，日间落脚时长排行，夜间落脚时长排行，并分别取Top10
     *
     * @param footholdQO
     * @return
     */
    @Override
    public FootholdMapBO footholdRank(FootholdQO footholdQO) {
        // 构建查询条件
        BoolQueryBuilder queryBuilder = createQueryBuilder(footholdQO);

        // 根据时间范围计算索引
        String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(
                footholdQO.getStartTime(),
                footholdQO.getEndTime());

        SearchHits hits = transportClient.prepareSearch(indexArray)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                .execute()
                .actionGet()
                .getHits();

        Map<String, FootholdRankBO> map = new HashMap<>();
        SearchHit[] hitArray = hits.getHits();
        for (int i = 0; i < hitArray.length - 1; i++) {
            OrbitPassVehicleRecord record1 = JsonUtils.toBean(hitArray[i].getSourceAsString(), OrbitPassVehicleRecord.class);
            OrbitPassVehicleRecord record2 = JsonUtils.toBean(hitArray[i + 1].getSourceAsString(), OrbitPassVehicleRecord.class);
            String startTime = record1.getCaptureTime();
            String endTime = record2.getCaptureTime();
            String deviceId = record1.getDeviceId();

            // 计算两次相邻拍摄时间的时间间隔
            double hour = DateUtils.getHoursBetweenTwoDate(startTime, endTime);
            if (hour >= footholdQO.getMinHour()) {
                // 落脚时长最长默认为12个小时
                if (hour >= MAX_FOOTHOLD_INTERVAL) {
                    hour = MAX_FOOTHOLD_INTERVAL;
                    endTime = add12Hours(startTime);
                }

                FootholdRankBO footholdBO;
                if (map.containsKey(deviceId)) {
                    footholdBO = map.get(deviceId);
                } else {
                    footholdBO = new FootholdRankBO();
                    footholdBO.setId(deviceId);
                }
                footholdBO.setTotalHour(footholdBO.getTotalHour() + hour);

                List<String> daytimeList = new ArrayList<>();
                calculateDaytime(startTime, endTime, footholdQO.getDaytimeStartHour(),
                        footholdQO.getDaytimeEndHour(), daytimeList);

                for (int k = 0; k < daytimeList.size() - 1; k += 2) {
                    double dayHour = DateUtils.getHoursBetweenTwoDate(daytimeList.get(k), daytimeList.get(k + 1));
                    footholdBO.setDaytimeHour(footholdBO.getDaytimeHour() + dayHour);
                }
                footholdBO.setNightHour(footholdBO.getTotalHour() - footholdBO.getDaytimeHour());

                OrbitResDevice device = DevicePointRedis.getDevicePointByCode(deviceId);
                if (device != null) {
                    footholdBO.setLatitude(device.getLatitude());
                    footholdBO.setLongitude(device.getLongitude());
                }

                map.put(footholdBO.getId(), footholdBO);
            }
        }

        List<FootholdRankBO> resultList;
        List<String> filterDeviceIdList = footholdQO.getDeviceIdList();
        if (filterDeviceIdList == null || filterDeviceIdList.size() == 0) {
            filterDeviceIdList = iDeviceService.queryAllDeviceIdList();
        }
        resultList = new ArrayList<>();
        for (Map.Entry<String, FootholdRankBO> entry : map.entrySet()) {
            if (filterDeviceIdList.contains(entry.getKey())) {
                resultList.add(entry.getValue());
            }
        }

        // 总落脚时长排行
        List<FootholdRankBO> list1 = new ArrayList<>();
        list1.addAll(resultList);
        Collections.sort(list1, (o1, o2) -> {
            if (o1.getTotalHour() < o2.getTotalHour()) return 1;
            else if (o1.getTotalHour() > o2.getTotalHour()) return -1;
            else return 0;
        });

        // 日间落脚时长排行
        List<FootholdRankBO> list2 = new ArrayList<>();
        list2.addAll(resultList);
        Collections.sort(list2, (o1, o2) -> {
            if (o1.getDaytimeHour() < o2.getDaytimeHour()) return 1;
            else if (o1.getDaytimeHour() > o2.getDaytimeHour()) return -1;
            else return 0;
        });

        // 夜间落脚时长排行
        List<FootholdRankBO> list3 = new ArrayList<>();
        list3.addAll(resultList);
        Collections.sort(list3, (o1, o2) -> {
            if (o1.getNightHour() < o2.getNightHour()) return 1;
            else if (o1.getNightHour() > o2.getNightHour()) return -1;
            else return 0;
        });

        FootholdMapBO footholdMapBO = new FootholdMapBO();
        footholdMapBO.setTotalHourRank(getTop10(list1));
        footholdMapBO.setDaytimeRank(getTop10(list2));
        footholdMapBO.setNightRank(getTop10(list3));

        return footholdMapBO;
    }

    /**
     * Top10
     *
     * @param list
     * @return
     */
    private List<FootholdRankBO> getTop10(List<FootholdRankBO> list) {
        int size = (list.size() < MAX_RANK_NUMBER) ? list.size() : MAX_RANK_NUMBER;
        List<FootholdRankBO> resultList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.get(i).setContent("Top" + (i + 1));
            resultList.add(list.get(i));
        }
        return resultList;
    }

    /**
     * 构建查询条件
     *
     * @param footholdQO
     * @return
     */
    private BoolQueryBuilder createQueryBuilder(FootholdQO footholdQO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(footholdQO.getStartTime())
                .to(footholdQO.getEndTime()));

        // 车牌号
        if (!StringUtils.isEmpty(footholdQO.getPlateNumber())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.PLATE_NUMBER, footholdQO.getPlateNumber()));
        }
        // 车牌颜色
        if (!StringUtils.isEmpty(footholdQO.getPlateColor())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.PLATE_COLOR, footholdQO.getPlateColor()));
        }
        // 设备列表
//        if (footholdQO.getDeviceIdList() != null && footholdQO.getDeviceIdList().size() > 0) {
//            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, footholdQO.getDeviceIdList()));
//        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }


    /**
     * 计算[startDt, endDt]落在白天的区间段,startDt和endDt最多跨一天
     *
     * @param startDt     yyyy-MM-dd HH:mm:ss
     * @param endDt       yyyy-MM-dd HH:mm:ss
     * @param d1
     * @param d2
     * @param daytimeList
     */
    private void calculateDaytime(String startDt, String endDt, int d1, int d2, List<String> daytimeList) {
        String h1 = new DecimalFormat("00").format(d1);
        String h2 = new DecimalFormat("00").format(d2);

        // 拼接出第一天的白天时间区间
        String firstDaytimeStart = new StringBuilder().append(startDt, 0, startDt.indexOf(" ") + 1)
                .append(h1).append(":00:00").toString();
        String firstDaytimeEnd = new StringBuilder().append(startDt, 0, startDt.indexOf(" ") + 1)
                .append(h2).append(":00:00").toString();
        getRegionInDaytime(startDt, endDt, firstDaytimeStart, firstDaytimeEnd, daytimeList);

        // 如果跨了一天，拼接出第二天的白天时间区间
        if (!org.apache.commons.lang3.time.DateUtils.isSameDay(DateUtils.getDate(startDt), DateUtils.getDate(endDt))) {
            Date date = DateUtils.getDate(firstDaytimeStart);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            String secondDaytimeStart = DateUtils.format(cal.getTime());
            String secondDaytimeEnd = new StringBuilder().append(secondDaytimeStart, 0, secondDaytimeStart.indexOf(" ") + 1)
                    .append(h2).append(":00:00").toString();
            getRegionInDaytime(startDt, endDt, secondDaytimeStart, secondDaytimeEnd, daytimeList);
        }
    }

    /**
     * 计算落在白天的时间区间
     *
     * @param startTime
     * @param endTime
     * @param daytimeStart
     * @param daytimeEnd
     * @param daytimeList
     */
    public static void getRegionInDaytime(String startTime, String endTime,
                                          String daytimeStart, String daytimeEnd,
                                          List<String> daytimeList) {
        Date startDate1 = DateUtils.getDate(daytimeStart);
        Date endDate1 = DateUtils.getDate(daytimeEnd);
        Date startDate2 = DateUtils.getDate(startTime);
        Date endDate2 = DateUtils.getDate(endTime);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long btlong = Math.min(startDate1.getTime(), endDate1.getTime());
        long otlong = Math.max(startDate1.getTime(), endDate1.getTime());
        long stlong = Math.min(startDate2.getTime(), endDate2.getTime());
        long edlong = Math.max(startDate2.getTime(), endDate2.getTime());

        // 是否有重叠区域
        if ((stlong >= btlong && stlong <= otlong) || (edlong >= btlong && edlong <= otlong)) {
            long sblong = stlong >= btlong ? stlong : btlong;
            long eblong = otlong >= edlong ? edlong : otlong;
            daytimeList.add(sdf.format(sblong));
            daytimeList.add(sdf.format(eblong));
        }
    }

    /**
     * 计算白天时长占比
     *
     * @param startTime
     * @param endTime
     * @param daytimeList
     * @return
     */
    private String calculateDaytimePercentage(String startTime, String endTime, List<String> daytimeList) {
        List<String> totalDate = new ArrayList<>();
        totalDate.add(startTime);
        totalDate.add(endTime);
        return calculateDaytimePercentage(totalDate, daytimeList);
    }

    /**
     * 计算白天时长占比
     *
     * @param totalDateList
     * @param daytimeList
     * @return
     */
    private String calculateDaytimePercentage(List<String> totalDateList, List<String> daytimeList) {
        long total = 0, daytime = 0;
        for (int i = 0; i < totalDateList.size() - 1; i += 2) {
            total += (DateUtils.getDate(totalDateList.get(i + 1)).getTime() - DateUtils.getDate(totalDateList.get(i)).getTime());
        }
        for (int i = 0; i < daytimeList.size() - 1; i += 2) {
            daytime += (DateUtils.getDate(daytimeList.get(i + 1)).getTime() - DateUtils.getDate(daytimeList.get(i)).getTime());
        }

        // 防止测试数据有问题,相邻两个抓拍时间完全相同
        if (total == 0) {
            return "0%";
        }

        double percentage = (double) daytime / total;
        return new DecimalFormat("#.##%").format(percentage);
    }

    /**
     * 加12个小时
     *
     * @param dateStr
     * @return
     */
    private String add12Hours(String dateStr) {
        Date date = DateUtils.getDate(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 12);
        return DateUtils.format(cal.getTime());
    }

    /**
     * 小数点后保留1位
     *
     * @param d
     * @return
     */
    private double fixDouble(double d) {
        BigDecimal b = new BigDecimal(d);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取特定车辆在给定时间范围内的落脚总时长Top10
     *
     * @param footholdQO
     * @return
     */
    private List<FootholdBO> getTop10FootholdByTotalHour(FootholdQO footholdQO) {
        // 构建查询条件
        BoolQueryBuilder queryBuilder = createQueryBuilder(footholdQO);

        // 根据时间范围计算索引
        String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(
                footholdQO.getStartTime(),
                footholdQO.getEndTime());

        SearchHits hits = transportClient.prepareSearch(indexArray)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                .execute()
                .actionGet()
                .getHits();

        Map<String, FootholdBO> map = new HashMap<>();
        SearchHit[] hitArray = hits.getHits();
        for (int i = 0; i < hitArray.length - 1; i++) {
            OrbitPassVehicleRecord record1 = JsonUtils.toBean(hitArray[i].getSourceAsString(), OrbitPassVehicleRecord.class);
            OrbitPassVehicleRecord record2 = JsonUtils.toBean(hitArray[i + 1].getSourceAsString(), OrbitPassVehicleRecord.class);
            String startTime = record1.getCaptureTime();
            String endTime = record2.getCaptureTime();
            String deviceId = record1.getDeviceId();

            // 计算两次相邻拍摄时间的时间间隔
            double hour = DateUtils.getHoursBetweenTwoDate(startTime, endTime);

            // 落脚时长最长默认为12个小时
            if (hour >= MAX_FOOTHOLD_INTERVAL) {
                hour = MAX_FOOTHOLD_INTERVAL;
            }

            FootholdBO footholdBO;
            if (map.containsKey(deviceId)) {
                footholdBO = map.get(deviceId);
            } else {
                footholdBO = new FootholdBO();
                footholdBO.setDeviceId(deviceId);
                // 设备名
                OrbitResDevice device = DevicePointRedis.getDevicePointByCode(deviceId);
                if (device != null) {
                    footholdBO.setDeviceName(device.getName());
                    footholdBO.setLatitude(device.getLatitude());
                    footholdBO.setLongitude(device.getLongitude());
                }
                // 设备具体位置信息
                footholdBO.setDevicePosition(DevicePointRedis.getFullPositionName(deviceId));
            }
            footholdBO.setTotalHour(fixDouble(footholdBO.getTotalHour() + hour));
            map.put(footholdBO.getDeviceId(), footholdBO);
        }

        List<FootholdBO> originalList = new ArrayList<>(map.values());
        /** 如果落脚总时长为0，则不返给前端 **/
        List<FootholdBO> resultList = originalList.stream().filter(footholdBO -> (footholdBO.getTotalHour() == 0 ? false : true)).collect(Collectors.toList());
        Collections.sort(resultList, (o1, o2) -> {
            if (o1.getTotalHour() < o2.getTotalHour()) return 1;
            else if (o2.getTotalHour() > o2.getTotalHour()) return -1;
            else return 0;
        });

        int size = (resultList.size() < MAX_RANK_NUMBER) ? resultList.size() : MAX_RANK_NUMBER;
        List<FootholdBO> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(resultList.get(i));
        }
		//以时间顺序排序
        list = list.stream().sorted(Comparator.comparing(FootholdBO::getTotalHour).reversed()).limit(10).collect(Collectors.toList());
        return list;
    }
}

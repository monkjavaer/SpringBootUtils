package com.car.orbit.orbitservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.car.orbit.orbitservice.bo.FootholdStatisticsBO;
import com.car.orbit.orbitservice.bo.VehicleOneCarOneGearBO;
import com.car.orbit.orbitservice.bo.onecaronegear.*;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitResArea;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.enums.EsDateAggEnum;
import com.car.orbit.orbitservice.enums.OneCarTagsEnum;
import com.car.orbit.orbitservice.mapper.OrbitResDeviceMapper;
import com.car.orbit.orbitservice.qo.DeviceQO;
import com.car.orbit.orbitservice.qo.FootholdQO;
import com.car.orbit.orbitservice.qo.TacticsOneCarOneGearQO;
import com.car.orbit.orbitservice.service.IFootholdAnalysisService;
import com.car.orbit.orbitservice.service.ITacticsOneCarOneGearService;
import com.car.orbit.orbitservice.util.ControlTaskMatcher;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.ExtendedBounds;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CreateDate：2019/3/29 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-一车一档service实现类
 **/
@Service
////@Transactional(rollbackFor = Exception.class)
public class TacticsOneCarOneGearServiceImpl implements ITacticsOneCarOneGearService {
    /**
     * redis client
     */
    @Autowired
    private RedisClient redisClient;

    /**
     * ES client
     */
    @Autowired
    private TransportClient transportClient;

    /**
     * ES client
     */
    @Autowired
    private IFootholdAnalysisService iFootholdAnalysisService;

    /**
     * 设备mapper
     */
    @Autowired
    private OrbitResDeviceMapper deviceMapper;


    /**
     * 过车记录表查询索引
     */
    private static final String ORBIT_PASS_VEHICLE_RECORD = "orbit_pass_vehicle_record";

    /**
     * 时间格式
     */
    private static final String DATE_FORMAT_YY_MM_DD = "yyyy-MM-dd";

    /**
     * 时间格式
     */
    private static final String DATE_FORMAT_YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";


    /***一车一档查询接口服务实现***/
    @Override
    public VehicleOneCarOneGearBO findVehiclesOneCarOneGearInfos(TacticsOneCarOneGearQO tacticsOneCarOneGearQO) {
        // key是redis的缓存key，如果存在，说明之前已经查询过一次，在redis中有缓存
        String key = tacticsOneCarOneGearQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            String[] includes = new String[]{EsConstant.PHOTO_FASTDFS_URL};
            //设置查询条件
            BoolQueryBuilder boolQueryBuilder = getVehicleBoolSearchBuilder(getBeforThreeMonth(), getNowDate(), tacticsOneCarOneGearQO.getPlateNumber(), tacticsOneCarOneGearQO.getPlateColor());
            //创建以天为粒度的1周日期聚合
            DateHistogramAggregationBuilder dayDateHistogramAggregationBuilder = getVehicleDateOfAgg(EsDateAggEnum.DAY.getAggType(), getBeforThreeMonth().split(" ")[0], getNowDate().split(" ")[0], EsDateAggEnum.DAYAGG.getAggType());
            //创建以周为粒度的1月的日期聚合
            DateHistogramAggregationBuilder monthDateHistogramAggregationBuilder = getVehicleDateOfAgg(EsDateAggEnum.WEEK.getAggType(), getBeforThreeMonth().split(" ")[0], getNowDate().split(" ")[0], EsDateAggEnum.MONTHAGG.getAggType());
            //创建以月为粒度的3个月的日期聚合
            DateHistogramAggregationBuilder threeOfMonthDateHistogramAggregationBuilder = getVehicleDateOfAgg(EsDateAggEnum.MONTH.getAggType(), getBeforThreeMonth().split(" ")[0], getNowDate().split(" ")[0], EsDateAggEnum.THREEOFMONTAGG.getAggType());
            //对索引查询条件
            SearchResponse searchResponse = getVehicleSearchResponse(transportClient, ORBIT_PASS_VEHICLE_RECORD, EsConstant.TYPE_INFO, boolQueryBuilder, dayDateHistogramAggregationBuilder, monthDateHistogramAggregationBuilder, threeOfMonthDateHistogramAggregationBuilder);
            //查询最近几张照片记录
            SearchResponse photoSearchResponse = transportClient.prepareSearch(ORBIT_PASS_VEHICLE_RECORD).setQuery(boolQueryBuilder).setTypes(EsConstant.TYPE_INFO).setFetchSource(includes, null).addSort(EsConstant.CAPTURE_TIME, SortOrder.DESC).setSize(10).get();
            //当查询结果返回为空时不进行处理
            if (searchResponse == null || searchResponse.getHits().totalHits == 0) {
                return new VehicleOneCarOneGearBO();
            }
            if (searchResponse != null && searchResponse.getHits() != null && searchResponse.getHits().totalHits > 0) {
                Aggregations histogramAgg = searchResponse.getAggregations();
                //以天为粒度的聚合
                Histogram dayAggHistogram = histogramAgg.get(EsDateAggEnum.DAYAGG.getAggType());
                //以周为粒度的聚合
                Histogram monthAggHistogram = histogramAgg.get(EsDateAggEnum.MONTHAGG.getAggType());
                //以月为粒度的聚合
                Histogram threeOfMonthAggHistogram = histogramAgg.get(EsDateAggEnum.THREEOFMONTAGG.getAggType());

                //将查询到的结果返回基础信息表，得到车的基础信息
                SearchHit searchHit = searchResponse.getHits().getAt(0);
                TacticsOneCarOneGearBaseInfo tacticsOneCarOneGearBaseInfo = new TacticsOneCarOneGearBaseInfo();
                Map<String, Object> vehicleSource = searchHit.getSource();
                //将品牌转换
                if (ObjectUtils.allNotNull(vehicleSource.get(EsConstant.VEHICLE_BRAND))) {
                    String orbitVehicleBrand = BrandRedis.getBrandNameByCode((String) vehicleSource.get(EsConstant.VEHICLE_BRAND));
                    tacticsOneCarOneGearBaseInfo.setBrandName(orbitVehicleBrand);
                }
                //将子品牌转换
                if (ObjectUtils.allNotNull(vehicleSource.get(EsConstant.VEHICLE_BRAND_CHILD))) {
                    String orbitVehicleChildBrand = BrandRedis.getChildBrandNameByCode((String) vehicleSource.get(EsConstant.VEHICLE_BRAND_CHILD));
                    tacticsOneCarOneGearBaseInfo.setChildBrandName(orbitVehicleChildBrand);
                }
                //全量品牌
                String fullBrand = BrandRedis.getFullBrandNameByCode((String) vehicleSource.get(EsConstant.VEHICLE_BRAND), (String) vehicleSource.get(EsConstant.VEHICLE_BRAND_CHILD));
                tacticsOneCarOneGearBaseInfo.setFullBrand(fullBrand);
                //将车辆类型转换
                if (ObjectUtils.allNotNull(vehicleSource.get(EsConstant.VEHICLE_TYPE))) {
                    String orbitVehicleType = (String) vehicleSource.get(EsConstant.VEHICLE_TYPE);
                    tacticsOneCarOneGearBaseInfo.setVehicleType(orbitVehicleType);
                }
                tacticsOneCarOneGearBaseInfo.setVid((String) vehicleSource.get(EsConstant.VID));
                tacticsOneCarOneGearBaseInfo.setPlateNumber((String) vehicleSource.get(EsConstant.PLATE_NUMBER));
                tacticsOneCarOneGearBaseInfo.setPlateColor((String) vehicleSource.get(EsConstant.PLATE_COLOR));
                tacticsOneCarOneGearBaseInfo.setVehicleColor((String) vehicleSource.get(EsConstant.VEHICLE_COLOR));
                //更新图片信息
                if (photoSearchResponse != null) {
                    List<String> photoUrlList = new ArrayList<>();
                    photoSearchResponse.getHits().forEach(photoSearch -> photoUrlList.add((String) photoSearch.getSource().get(EsConstant.PHOTO_FASTDFS_URL)));
                    photoUrlList.stream().distinct().collect(Collectors.toList());
                    tacticsOneCarOneGearBaseInfo.setPhotoFastDfsUrl(photoUrlList);
                }

                //得到标签的信息
                if (ObjectUtils.allNotNull(vehicleSource.get(EsConstant.VID))) {
                    String VID = (String) vehicleSource.get(EsConstant.VID);
                    List<String> tagList = new ArrayList<>();
                    Boolean isInBlackList = BlacklistRedis.isInBlacklist(VID);
                    //判断是否布控是1
                    try {
                        List<ControlTaskMatcher> controlTaskMatchers = OnControlRedis.getAllMatcher();
                        for (ControlTaskMatcher controlTaskMatcher : controlTaskMatchers) {
                            Boolean isOnControl = controlTaskMatcher.match((String) vehicleSource.get(EsConstant.PLATE_NUMBER), (String) vehicleSource.get(EsConstant.PLATE_COLOR), (String) vehicleSource.get(EsConstant.DEVICE_ID), (String) vehicleSource.get(EsConstant.VEHICLE_BRAND), (String) vehicleSource.get(EsConstant.VEHICLE_BRAND_CHILD), (String) vehicleSource.get(EsConstant.PLATE_COLOR), (String) vehicleSource.get(EsConstant.VEHICLE_TYPE));
                            if (isOnControl) {
                                tagList.add(String.valueOf(OneCarTagsEnum.MONITOR_VEHICLE.getValue()));
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //判断是否黑名单是2
                    if (isInBlackList) {
                        tagList.add(String.valueOf(OneCarTagsEnum.BLACKLISTED_VEHICLE.getValue()));
                    }
                    //判断是否白名单是3
                    Boolean isInWhiteList = WhitelistRedis.isInWhitelist(VID);
                    if (isInWhiteList) {
                        tagList.add(String.valueOf(OneCarTagsEnum.WHITE_LIST_VEHICLE.getValue()));
                    }
                    //判断是否套牌车
                    BoolQueryBuilder deskBoolQueryBuilder = QueryBuilders.boolQuery();
                    deskBoolQueryBuilder.must(QueryBuilders.termQuery(EsConstant.VID, VID));
                    deskBoolQueryBuilder.must(QueryBuilders.termQuery(EsConstant.EXCEPTION, "2"));
                    deskBoolQueryBuilder.mustNot(QueryBuilders.termQuery(EsConstant.HANDLE_STATUS, "2"));
                    SearchResponse searchDeskResponse = transportClient.prepareSearch(EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD).setTypes(EsConstant.TYPE_INFO).setQuery(deskBoolQueryBuilder).setFetchSource(new String[]{}, null).get();
                    //是套牌车
                    if (searchDeskResponse.getHits().totalHits > 0) {
                        tagList.add(String.valueOf(OneCarTagsEnum.DECK_VEHICLE.getValue()));
                    }
                    //对标签去重
                    tacticsOneCarOneGearBaseInfo.setTag(tagList);
                }


                /****将周统计数据映射成对应的格式****/
                List<OneCarStatisticsNameAndTime> featrue = new ArrayList<>();
                Map<String, Long> featureMapStat = getFeatureStatInfoMap(dayAggHistogram, 7, EsDateAggEnum.FEATUTRAGG_ALIAS_CHILD.getAggType());
                featrue = getFeatureStatInfos(featrue, featureMapStat);
                tacticsOneCarOneGearBaseInfo.setRiskStatistics(featrue);

                /***处理道路地址信息数据***/
                TacticsOneCarOneGearActivePointStat tacticsOneCarOneGearActivePointStat = new TacticsOneCarOneGearActivePointStat();
                //道路一周地址信息转换
                List<OneCarActiveStat> oneCarActiveStatWeekList = new ArrayList<>();
                Map<String, Long> dayAggHistogramMap = getStatInfoMap(dayAggHistogram, 7, EsDateAggEnum.ROADAGG_ALIAS.getAggType());
                oneCarActiveStatWeekList = getActiveStatInfos(oneCarActiveStatWeekList, BaseBusinessRedis.DIC_DEVICE_KEY, dayAggHistogramMap);
                tacticsOneCarOneGearActivePointStat.setWeek(oneCarActiveStatWeekList);

                //道路一月地址信息转换
                List<OneCarActiveStat> oneCarActiveStatMonthList = new ArrayList<>();
                Map<String, Long> monthAggHistogramMap = getStatInfoMap(dayAggHistogram, 30, EsDateAggEnum.ROADAGG_ALIAS.getAggType());
                oneCarActiveStatMonthList = getActiveStatInfos(oneCarActiveStatMonthList, BaseBusinessRedis.DIC_DEVICE_KEY, monthAggHistogramMap);
                tacticsOneCarOneGearActivePointStat.setMonth(oneCarActiveStatMonthList);

                //道路三个月地址信息转换
                List<OneCarActiveStat> oneCarActiveStatThreeOfMonthList = new ArrayList<>();
                Map<String, Long> threeOfMonthAggHistogramMap = getStatInfoMap(dayAggHistogram, 90, EsDateAggEnum.ROADAGG_ALIAS.getAggType());
                oneCarActiveStatThreeOfMonthList = getActiveStatInfos(oneCarActiveStatThreeOfMonthList, BaseBusinessRedis.DIC_DEVICE_KEY, threeOfMonthAggHistogramMap);
                tacticsOneCarOneGearActivePointStat.setThreeMonth(oneCarActiveStatThreeOfMonthList);

                /***处理区域地址信息数据***/
                TacticsOneCarOneGearActiveAreaStat tacticsOneCarOneGearActiveAreaStat = new TacticsOneCarOneGearActiveAreaStat();
                //道路一周区域地址信息转换
                List<OneCarActiveStat> oneCarActiveAreaWeekList = new ArrayList<>();
                Map<String, Long> dayAreaAggHistogramMap = getStatInfoMap(dayAggHistogram, 7, EsDateAggEnum.AREAAGG_ALIAS.getAggType());
                oneCarActiveAreaWeekList = getActiveStatInfos(oneCarActiveAreaWeekList, BaseBusinessRedis.DIC_AREA_KEY, dayAreaAggHistogramMap);
                tacticsOneCarOneGearActiveAreaStat.setWeek(oneCarActiveAreaWeekList);

                //道路一月区域地址信息转换
                List<OneCarActiveStat> oneCarActiveAreaMonthList = new ArrayList<>();
                Map<String, Long> monthAreaAggHistogramMap = getStatInfoMap(dayAggHistogram, 30, EsDateAggEnum.AREAAGG_ALIAS.getAggType());
                oneCarActiveAreaMonthList = getActiveStatInfos(oneCarActiveAreaMonthList, BaseBusinessRedis.DIC_AREA_KEY, monthAreaAggHistogramMap);
                tacticsOneCarOneGearActiveAreaStat.setMonth(oneCarActiveAreaMonthList);

                //道路3月区域地址信息转换
                List<OneCarActiveStat> oneCarActiveAreaThreeOfMonthList = new ArrayList<>();
                Map<String, Long> threeOfMonthAreaAggHistogramMap = getStatInfoMap(dayAggHistogram, 90, EsDateAggEnum.AREAAGG_ALIAS.getAggType());
                oneCarActiveAreaThreeOfMonthList = getActiveStatInfos(oneCarActiveAreaThreeOfMonthList, BaseBusinessRedis.DIC_AREA_KEY, threeOfMonthAreaAggHistogramMap);
                tacticsOneCarOneGearActiveAreaStat.setThreeMonth(oneCarActiveAreaThreeOfMonthList);

                /*****获取可疑点****/
                FootholdQO footholdQO = new FootholdQO();
                footholdQO.setPlateNumber(tacticsOneCarOneGearQO.getPlateNumber());
                footholdQO.setPlateColor(tacticsOneCarOneGearQO.getPlateColor());
                FootholdStatisticsBO footholdStatisticsBO = iFootholdAnalysisService.footholdStatistics(footholdQO);


                /****将统计数据映射成对应的时间格式****/
                TacticsOneCarOneGearTimeStat tacticsOneCarOneGearTimeStat = new TacticsOneCarOneGearTimeStat();
                //拿一个星期的统计数据
                List<OneCarStatistics> dayTime = mappingTimeADDAggs(dayAggHistogram, 7, EsDateAggEnum.TIMEAGG_ALIAS.getAggType());
                dayTime = transTimeAggsForDayOfWeek(dayTime);
                dayTime = sortTimeAggsForDayOfWeek(dayTime);
                tacticsOneCarOneGearTimeStat.setWeek(dayTime);
                //拿一个月的统计数据
                List<OneCarStatistics> monthTime = mappingTimeAggsForOneToSev(30, ORBIT_PASS_VEHICLE_RECORD, EsConstant.TYPE_INFO, dayTime, tacticsOneCarOneGearQO);
                tacticsOneCarOneGearTimeStat.setMonth(monthTime);
                //拿三个月的统计数据
                List<OneCarStatistics> threeOfmonthTime = mappingTimeAggsForOneToSev(90, ORBIT_PASS_VEHICLE_RECORD, EsConstant.TYPE_INFO, dayTime, tacticsOneCarOneGearQO);
                tacticsOneCarOneGearTimeStat.setThreeMonth(threeOfmonthTime);

                /****将统计数据映射成对应的时间格式****/
                TacticsOneCarOneGearTimeStat tacticsOneCarOneGearTimeADDStat = new TacticsOneCarOneGearTimeStat();
                //拿一个星期的统计数据
                List<OneCarStatistics> dayADDTime = mappingTimeADDAggsForWeek(dayAggHistogram, 90, "timeAggsADD");
                Collections.reverse(dayADDTime);

                tacticsOneCarOneGearTimeADDStat.setWeek(dayADDTime);

                //拿一个月的统计数据
                List<OneCarStatistics> monthADDTime = mappingTimeADDAggsForMonth(monthAggHistogram, 12, "timeAggsADD");
                Collections.reverse(monthADDTime);
                tacticsOneCarOneGearTimeADDStat.setMonth(monthADDTime);

                //拿三个月的统计数据
                List<OneCarStatistics> threeOfmonthADDTime = mappingTimeADDAggs(monthADDTime);
                Collections.reverse(threeOfmonthADDTime);
                tacticsOneCarOneGearTimeADDStat.setThreeMonth(threeOfmonthADDTime);


                /****将计算过后的消息返回***/
                //创建返回类型
                TacticsOneCarOneGearInfo tacticsOneCarOneGearInfo = new TacticsOneCarOneGearInfo();
                //处理基础信息的信息
                tacticsOneCarOneGearInfo.setBaseInfo(tacticsOneCarOneGearBaseInfo);
                //处理时间统计信息
                tacticsOneCarOneGearInfo.setTimeStat(tacticsOneCarOneGearTimeStat);
                //处理活跃点信息
                tacticsOneCarOneGearInfo.setActivePointStat(tacticsOneCarOneGearActivePointStat);
                //处理活跃区域信息
                tacticsOneCarOneGearInfo.setActiveAreaStat(tacticsOneCarOneGearActiveAreaStat);
                //处理可疑点信息
                tacticsOneCarOneGearInfo.setSuspectedStat(footholdStatisticsBO);
                //处理3个月的时间的信息
                tacticsOneCarOneGearInfo.setThreeOfMonthTimeStat(tacticsOneCarOneGearTimeADDStat);
                // Redis缓存记录
                String searchKey = UUIDUtils.generate();
                VehicleOneCarOneGearBO vehicleOneCarOneGearBO = new VehicleOneCarOneGearBO(searchKey, 1, tacticsOneCarOneGearInfo);
                redisClient.saveSingleByExpire(searchKey, EsDateAggEnum.REDIS_FIELD.getAggType(), vehicleOneCarOneGearBO, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
                return vehicleOneCarOneGearBO;
            }
        } else {
            String vehicleOneCarOneGearBOJsonString = redisClient.get(key, EsDateAggEnum.REDIS_FIELD.getAggType());
            VehicleOneCarOneGearBO vehicleOneCarOneGearBO = JSON.parseObject(vehicleOneCarOneGearBOJsonString, VehicleOneCarOneGearBO.class);
            return vehicleOneCarOneGearBO;
        }
        return null;
    }

    @Override
    public OneCarActiveStat findVehiclesOneCarOneGearInfos(DeviceQO deviceQO) {
        List<DeviceDetailVO> list = deviceMapper.queryListByPage(deviceQO);
        if (!CollectionUtils.isEmpty(list) && list.size() > 0) {
            Map<String, List<Double>> areaLatLonMap = list.stream()
                    .collect(Collectors.groupingBy(DeviceDetailVO::getAreaId,
                            Collectors.collectingAndThen(Collectors.toList(), lists -> {
                                double latAverage = lists.stream().collect(Collectors.averagingDouble(DeviceDetailVO::getLatitude));
                                double lonAverage = lists.stream().collect(Collectors.averagingDouble(DeviceDetailVO::getLongitude));
                                return Arrays.asList(latAverage, lonAverage);
                            })));
            List<Double> latLonList = areaLatLonMap.get(deviceQO.getAreaId());
            OneCarActiveStat oneCarActiveStat = new OneCarActiveStat();
            oneCarActiveStat.setLat(latLonList.get(0));
            oneCarActiveStat.setLon(latLonList.get(1));
            return oneCarActiveStat;
        }
        return new OneCarActiveStat();
    }


    private BoolQueryBuilder getVehicleBoolSearchBuilder(String startTime, String endTime, String plateNumber, String plateColor) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME).from(startTime).to(endTime);
        TermQueryBuilder pnTermQueryBuilder = QueryBuilders.termQuery(EsConstant.PLATE_NUMBER, plateNumber);
        if (plateColor != null && !plateColor.trim().equals("")) {
            TermQueryBuilder pcTermQueryBuilder = QueryBuilders.termQuery(EsConstant.PLATE_COLOR, plateColor);
            boolQueryBuilder.must(pcTermQueryBuilder);
        }
        boolQueryBuilder.must(rangeQueryBuilder);
        boolQueryBuilder.must(pnTermQueryBuilder);
        return boolQueryBuilder;
    }



    private DateHistogramAggregationBuilder getVehicleDateOfAgg(String dataAggType, String extendedStartTime, String extendedEndTime, String aggName) {
        DateHistogramAggregationBuilder dateHistogramAggregationBuilder = AggregationBuilders.dateHistogram(aggName).field(EsConstant.CAPTURE_TIME);
        if (dataAggType.equals(EsDateAggEnum.DAY.getAggType())) {
            dateHistogramAggregationBuilder = dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.DAY).format(DATE_FORMAT_YY_MM_DD).minDocCount(0).order(Histogram.Order.KEY_DESC).extendedBounds(
                    new ExtendedBounds(extendedStartTime, extendedEndTime)).minDocCount(0);
            TermsAggregationBuilder timeADDTermsAggregationBuilder = AggregationBuilders.terms("timeAggsADD").field("CAPTURE_HOUR_EVEN").minDocCount(0);
            dateHistogramAggregationBuilder.subAggregation(timeADDTermsAggregationBuilder);
        } else if (dataAggType.equals(EsDateAggEnum.WEEK.getAggType())) {
            dateHistogramAggregationBuilder = dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.WEEK).format(DATE_FORMAT_YY_MM_DD).minDocCount(0).order(Histogram.Order.KEY_DESC).extendedBounds(
                    new ExtendedBounds(extendedStartTime, extendedEndTime)
            ).minDocCount(0);
            TermsAggregationBuilder timeADDTermsAggregationBuilder = AggregationBuilders.terms("timeAggsADD").field("CAPTURE_DATE").minDocCount(0);
            dateHistogramAggregationBuilder.subAggregation(timeADDTermsAggregationBuilder);
        } else if (dataAggType.equals(EsDateAggEnum.MONTH.getAggType())) {
            dateHistogramAggregationBuilder = dateHistogramAggregationBuilder.dateHistogramInterval(DateHistogramInterval.MONTH).format(DATE_FORMAT_YY_MM_DD).minDocCount(0).order(Histogram.Order.KEY_DESC).extendedBounds(
                    new ExtendedBounds(extendedStartTime, extendedEndTime)
            ).minDocCount(0);

        }
        TermsAggregationBuilder timeTermsAggregationBuilder = AggregationBuilders.terms(EsDateAggEnum.TIMEAGG_ALIAS.getAggType()).field(EsConstant.CAPTURE_HOUR).size(30);
        TermsAggregationBuilder areaTermsAggregationBuilder = AggregationBuilders.terms(EsDateAggEnum.AREAAGG_ALIAS.getAggType()).field(EsConstant.AREA_ID).size(30);
        TermsAggregationBuilder roadTermsAggregationBuilder = AggregationBuilders.terms(EsDateAggEnum.ROADAGG_ALIAS.getAggType()).field(EsConstant.DEVICE_ID).size(30);
        TermsAggregationBuilder featureTermsAggregationBuilder = AggregationBuilders.terms(EsDateAggEnum.FEATUTRAGG_ALIAS_CHILD.getAggType()).field(EsDateAggEnum.ES_VEHICLE_FIELD_VEHICLE_PROPERTIES_CHILDNAME.getAggType()).size(30);
        NestedAggregationBuilder featureNestedAggregationBuilder = AggregationBuilders.nested(EsDateAggEnum.FEATUTRAGG_ALIAS.getAggType(), EsDateAggEnum.ES_VEHICLE_FIELD.getAggType()).subAggregation(featureTermsAggregationBuilder);
        dateHistogramAggregationBuilder.subAggregation(areaTermsAggregationBuilder);
        dateHistogramAggregationBuilder.subAggregation(timeTermsAggregationBuilder);
        dateHistogramAggregationBuilder.subAggregation(roadTermsAggregationBuilder);
        dateHistogramAggregationBuilder.subAggregation(featureNestedAggregationBuilder);
        return dateHistogramAggregationBuilder;
    }

    private SearchResponse getVehicleSearchResponse(TransportClient transportClient, String index, String type, BoolQueryBuilder boolQueryBuilder, DateHistogramAggregationBuilder... dateHistogramAggregationBuilder) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index).setTypes(type).setSize(1);
        searchRequestBuilder.setQuery(boolQueryBuilder);
        for (DateHistogramAggregationBuilder dateHistogramAggregationBuilder1 : dateHistogramAggregationBuilder) {
            searchRequestBuilder.addAggregation(dateHistogramAggregationBuilder1);
        }
        SearchResponse searchResponse = searchRequestBuilder.get();
        return searchResponse;
    }

    /**
     * 将time的类型映射
     ***/
    private List<OneCarStatistics> transTimeAggsForDayOfWeek(List<OneCarStatistics> oneCarStatisticsList) {
        for (OneCarStatistics oneCarStatistics : oneCarStatisticsList) {
            String date = oneCarStatistics.getWhatDay();
            oneCarStatistics.setWhatDay(String.valueOf(getAfterDayDateOfWeek(date)));
        }
        return oneCarStatisticsList;
    }

    /**
     * 将time的类型映射
     ***/
    private List<OneCarStatistics> sortTimeAggsForDayOfWeek(List<OneCarStatistics> oneCarStatisticsList) {
        List<OneCarStatistics> datTimeNew = oneCarStatisticsList.stream().sorted( (OneCarStatistics o1 ,OneCarStatistics o2) ->{ return o1.getWhatDay().compareTo(o2.getWhatDay());} ).collect(Collectors.toList());
        OneCarStatistics oneCarStatistics = datTimeNew.get(0);
        datTimeNew.remove(0);
        datTimeNew.add(oneCarStatistics);
        return datTimeNew;
    }


    /**
     * 将time的类型映射
     ***/
    private List<OneCarStatistics> mappingTimeAggsForOneToSev(int dayTimes, String index, String type, List<OneCarStatistics> oneCarStatisticsList, TacticsOneCarOneGearQO tacticsOneCarOneGearQO) {
        List<OneCarStatistics> newOneCarStatisticsList = generateOneCarStatisticsForTime();
        BoolQueryBuilder boolQueryBuilder = getVehicleBoolSearchBuilder(getAfterDayDateStr(getNowDate(), -dayTimes, DATE_FORMAT_YY_MM_DD_HH_MM_SS), getNowDate(), tacticsOneCarOneGearQO.getPlateNumber(), tacticsOneCarOneGearQO.getPlateColor());
        TermsAggregationBuilder timeTermsAggregationBuilder = AggregationBuilders.terms("weekAgg").field("DAY_OF_WEEK").size(30);
        TermsAggregationBuilder dayOfWeekTermsAggregationBuilder = AggregationBuilders.terms("hourAgg").field("CAPTURE_HOUR").size(30);
        timeTermsAggregationBuilder.subAggregation(dayOfWeekTermsAggregationBuilder);
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index).setTypes(type).setSize(0);
        searchRequestBuilder.setQuery(boolQueryBuilder);
        searchRequestBuilder.addAggregation(timeTermsAggregationBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        Aggregations aggregations = searchResponse.getAggregations();
        Terms terms = aggregations.get("weekAgg");
        List<Terms.Bucket> termsBuckets = (List<Terms.Bucket>) terms.getBuckets();
        for (Terms.Bucket bucket : termsBuckets) {
            for (OneCarStatistics oneCarStatistics : newOneCarStatisticsList) {
                if (oneCarStatistics.getWhatDay().equals(bucket.getKey().toString())) {
                    Aggregations bucketAggregations = bucket.getAggregations();
                    Terms bucketTerms = bucketAggregations.get("hourAgg");
                    List<OneCarStatisticsNameAndTime> isoneCarStatisticsNameAndTimeList = new ArrayList<>();
                    for (Terms.Bucket bucketBucket : bucketTerms.getBuckets()) {
                        OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime = new OneCarStatisticsNameAndTime();
                        oneCarStatisticsNameAndTime.setStatKey(bucketBucket.getKey().toString());
                        oneCarStatisticsNameAndTime.setTimes(String.valueOf(bucketBucket.getDocCount()));
                        isoneCarStatisticsNameAndTimeList.add(oneCarStatisticsNameAndTime);
                    }
                    oneCarStatistics.setStats(isoneCarStatisticsNameAndTimeList);
                }
            }
        }
        return newOneCarStatisticsList;
    }


    /**
     * 将time的类型映射
     ***/
    private List<OneCarStatistics> mappingTimeADDAggs(Histogram histogram, int type, String aggType) {
        List<OneCarStatistics> dayTimeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(histogram.getBuckets())) {
            histogram.getBuckets().stream().limit(type).forEach(timeBucket -> {
                //得到时间的信息
                OneCarStatistics oneCarStatistics = new OneCarStatistics();
                oneCarStatistics.setWhatDay(timeBucket.getKeyAsString());
                List<OneCarStatisticsNameAndTime> oneDayTimeList = new ArrayList<>();
                Terms timeAggsTerm = timeBucket.getAggregations().get(aggType);
                oneDayTimeList = getOneCarStatisticsNameAndTimeList(oneDayTimeList, timeAggsTerm);
                oneCarStatistics.setStats(oneDayTimeList);
                dayTimeList.add(oneCarStatistics);
            });
        }
        return dayTimeList;
    }


    /**
     * 将timeADD月的类型映射
     ***/
    private List<OneCarStatistics> mappingTimeADDAggs(List<OneCarStatistics> oneCarStatisticsList) {
        if (!CollectionUtils.isEmpty(oneCarStatisticsList)) {
            List<OneCarStatistics> list = new ArrayList<>();
            List<OneCarStatisticsNameAndTime> oneCarStatisticsNameAndTimeListObj = new ArrayList<>();
            OneCarStatistics oneCarStatistics = new OneCarStatistics();

            for (OneCarStatistics oneCarStatistic : oneCarStatisticsList) {
                List<OneCarStatisticsNameAndTime> oneCarStatisticsNameAndTimeList = oneCarStatistic.getStats();
                OneCarStatisticsNameAndTime oneCarStatisticsNameAndTimeObj = new OneCarStatisticsNameAndTime();
                oneCarStatisticsNameAndTimeObj.setStatKey(oneCarStatistic.getWhatDay());
                Integer allInt = 0;
                for (OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime : oneCarStatisticsNameAndTimeList) {
                    allInt += Integer.valueOf(oneCarStatisticsNameAndTime.getTimes());
                }
                oneCarStatisticsNameAndTimeObj.setTimes(String.valueOf(allInt));
                oneCarStatisticsNameAndTimeObj.setStatRange(oneCarStatistic.getWhatDay() + "-" + getAfterDayDateStr(oneCarStatistic.getWhatDay()));
                oneCarStatisticsNameAndTimeListObj.add(oneCarStatisticsNameAndTimeObj);
            }

            oneCarStatistics.setWhatDay("threeMonthOfData");
            oneCarStatistics.setStats(oneCarStatisticsNameAndTimeListObj);

            list.add(oneCarStatistics);
            return list;
        }
        return oneCarStatisticsList;
    }

    /**
     * 将time的类型映射
     ***/
    private List<OneCarStatistics> mappingTimeADDAggsForWeek(Histogram histogram, int type, String aggType) {
        List<OneCarStatistics> dayTimeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(histogram.getBuckets())) {
            histogram.getBuckets().stream().limit(type).forEach(timeBucket -> {
                //得到时间的信息
                OneCarStatistics oneCarStatistics = new OneCarStatistics();
                oneCarStatistics.setWhatDay(timeBucket.getKeyAsString());
                Terms timeAggsTerm = timeBucket.getAggregations().get(aggType);
                List<OneCarStatisticsNameAndTime> oneDayTimeList = getOneCarStatisticsNameAndTimeListForWeek(timeAggsTerm);
                oneCarStatistics.setStats(oneDayTimeList);
                dayTimeList.add(oneCarStatistics);
            });
        }
        return dayTimeList;
    }

    private List<OneCarStatisticsNameAndTime> getOneCarStatisticsNameAndTimeListForWeek(Terms timeAggsTerm) {
        List<OneCarStatisticsNameAndTime> oneDayTimeList = generateOneCarStatisticsNameAndTimeList();
        if (!CollectionUtils.isEmpty(timeAggsTerm.getBuckets())) {
            timeAggsTerm.getBuckets().stream().forEach(tbket -> {
                for (OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime : oneDayTimeList) {
                    if (oneCarStatisticsNameAndTime.getStatKey().equals(tbket.getKeyAsString())) {
                        oneCarStatisticsNameAndTime.setTimes(String.valueOf(tbket.getDocCount()));
                    }
                }
            });
        }
        return oneDayTimeList;
    }

    private List<OneCarStatistics> generateOneCarStatisticsForTime() {

        List<OneCarStatistics> oneCarStatisticsList = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            OneCarStatistics oneCarStatistics = new OneCarStatistics();
            if( i < 7){
                oneCarStatistics.setWhatDay(String.valueOf(i));
            }else {
                oneCarStatistics.setWhatDay("0");
            }
            List<OneCarStatisticsNameAndTime> oneCarStatisticsNameAndTimeList = new ArrayList<>();
            oneCarStatistics.setStats(oneCarStatisticsNameAndTimeList);
            oneCarStatisticsList.add(oneCarStatistics);
        }

        return oneCarStatisticsList;
    }

    private List<OneCarStatisticsNameAndTime> generateOneCarStatisticsNameAndTimeList() {

        List<OneCarStatisticsNameAndTime> oneCarStatisticsNameAndTimeList = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            if (i % 2 == 0) {
                OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime = new OneCarStatisticsNameAndTime();
                oneCarStatisticsNameAndTime.setStatKey(String.valueOf(i));
                oneCarStatisticsNameAndTime.setTimes("0");
                if (i < 9) {
                    oneCarStatisticsNameAndTime.setStatRange("0" + String.valueOf(i) + ":00" + "-" + String.valueOf(i + 2) + ":00");
                } else {
                    oneCarStatisticsNameAndTime.setStatRange(String.valueOf(i) + ":00" + "-" + String.valueOf(i + 2) + ":00");
                }
                oneCarStatisticsNameAndTimeList.add(oneCarStatisticsNameAndTime);
            }
        }

        return oneCarStatisticsNameAndTimeList;
    }

    /**
     * 将time的类型映射
     ***/
    private List<OneCarStatistics> mappingTimeADDAggsForMonth(Histogram histogram, int type, String aggType) {
        List<OneCarStatistics> dayTimeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(histogram.getBuckets())) {
            histogram.getBuckets().stream().limit(type).forEach(timeBucket -> {
                //得到时间的信息
                OneCarStatistics oneCarStatistics = new OneCarStatistics();
                oneCarStatistics.setWhatDay(timeBucket.getKeyAsString());
                List<OneCarStatisticsNameAndTime> oneDayTimeList = generateOneCarStatisticsNameAndTimeForMonthList(timeBucket.getKeyAsString());
                Terms timeAggsTerm = timeBucket.getAggregations().get(aggType);
                oneDayTimeList = getOneCarStatisticsNameAndTimeListForMonth(oneDayTimeList, timeAggsTerm);
                oneCarStatistics.setStats(oneDayTimeList);
                dayTimeList.add(oneCarStatistics);
            });
        }
        return dayTimeList;
    }

    private List<OneCarStatisticsNameAndTime> getOneCarStatisticsNameAndTimeListForMonth(List<OneCarStatisticsNameAndTime> oneDayTimeList, Terms timeAggsTerm) {
        if (!CollectionUtils.isEmpty(timeAggsTerm.getBuckets())) {
            timeAggsTerm.getBuckets().stream().forEach(tbket -> {
                for (OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime : oneDayTimeList) {
                    if (oneCarStatisticsNameAndTime.getStatKey().equals(tbket.getKeyAsString())) {
                        oneCarStatisticsNameAndTime.setTimes(String.valueOf(tbket.getDocCount()));
                    }
                }
            });
            return oneDayTimeList;
        }
        return oneDayTimeList;
    }

    private List<OneCarStatisticsNameAndTime> generateOneCarStatisticsNameAndTimeForMonthList(String startDay) {

        List<OneCarStatisticsNameAndTime> oneCarStatisticsNameAndTimeList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime = new OneCarStatisticsNameAndTime();
            String dataStr = getAfterDayDateStr(startDay, i, DATE_FORMAT_YY_MM_DD);
            oneCarStatisticsNameAndTime.setStatKey(dataStr);
            oneCarStatisticsNameAndTime.setTimes("0");
            oneCarStatisticsNameAndTime.setStatRange(String.valueOf(getAfterDayDateOfWeek(dataStr)));
            oneCarStatisticsNameAndTimeList.add(oneCarStatisticsNameAndTime);
        }

        return oneCarStatisticsNameAndTimeList;
    }

    private List<OneCarStatisticsNameAndTime> getOneCarStatisticsNameAndTimeList(List<OneCarStatisticsNameAndTime> oneDayTimeList, Terms timeAggsTerm) {
        if (!CollectionUtils.isEmpty(timeAggsTerm.getBuckets())) {
            timeAggsTerm.getBuckets().stream().forEach(tbket -> {
                OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime = new OneCarStatisticsNameAndTime();
                oneCarStatisticsNameAndTime.setStatKey(tbket.getKeyAsString());
                oneCarStatisticsNameAndTime.setTimes(String.valueOf(tbket.getDocCount()));
                oneDayTimeList.add(oneCarStatisticsNameAndTime);
            });
        }
        return oneDayTimeList;
    }

    //*处理特征值的map方法*//
    private Map<String, Long> getFeatureStatInfoMap(Histogram histogram, int day, String aggTypyName) {
        Map<String, Long> oneCarActiveStatMap = new HashMap();
        day = histogram.getBuckets().size() > day ? day : histogram.getBuckets().size();
        histogram.getBuckets().stream().limit(day)
                .forEach(daysBucket -> {
                    Nested ntd = daysBucket.getAggregations().get(EsDateAggEnum.FEATUTRAGG_ALIAS.getAggType());
                    Terms roadBuckets = ntd.getAggregations().get(aggTypyName);
                    roadBuckets.getBuckets().stream().forEach(roadBucket -> {
                        Long orginValue = 0L;
                        if (oneCarActiveStatMap.get(roadBucket.getKeyAsString()) != null) {
                            orginValue = oneCarActiveStatMap.get(roadBucket.getKeyAsString());
                        }
                        Long nowValue = orginValue + roadBucket.getDocCount();
                        oneCarActiveStatMap.put(roadBucket.getKeyAsString(), nowValue);
                    });
                });

        return oneCarActiveStatMap;
    }


    /**
     * 统计最近几天的
     ***/
    private List<OneCarStatisticsNameAndTime> getFeatureStatInfos(List<OneCarStatisticsNameAndTime> oneCarActiveStatList, Map<String, Long> featureStatInfoMap) {
        if (featureStatInfoMap != null && featureStatInfoMap.size() > 0) {
            featureStatInfoMap.forEach((featureId, times) -> {
                if (featureId.equals("11") || featureId.equals("8")) {
                    OneCarStatisticsNameAndTime oneCarStatisticsNameAndTime = new OneCarStatisticsNameAndTime();
//                    //TODO:需要将featureId转化成对应名称
                    oneCarStatisticsNameAndTime.setStatKey(featureId);
                    oneCarStatisticsNameAndTime.setTimes(String.valueOf(times));
                    oneCarActiveStatList.add(oneCarStatisticsNameAndTime);
                }
            });

        }
        return oneCarActiveStatList;
    }

    //*处理活跃点的map方法*//
    private Map<String, Long> getStatInfoMap(Histogram histogram, int day, String aggTypyName) {
        Map<String, Long> oneCarActiveStatMap = new HashMap();

        for (int i = 0; i < day; i++) {
            if (i == histogram.getBuckets().size()) {
                return oneCarActiveStatMap;
            }
            Histogram.Bucket daysBucket = histogram.getBuckets().get(i);
            Terms roadBuckets = daysBucket.getAggregations().get(aggTypyName);
            roadBuckets.getBuckets().stream().forEach(roadBucket -> {
                Long orginValue = 0L;
                if (oneCarActiveStatMap.get(roadBucket.getKeyAsString()) != null) {
                    orginValue = oneCarActiveStatMap.get(roadBucket.getKeyAsString());
                }
                Long nowValue = orginValue + roadBucket.getDocCount();
                oneCarActiveStatMap.put(roadBucket.getKeyAsString(), nowValue);
            });
        }
        return oneCarActiveStatMap;
    }

    /**
     * 统计最近几天的包含经纬度的数据
     ***/
    private List<OneCarActiveStat> getActiveStatInfos(List<OneCarActiveStat> oneCarActiveStatList, String redisKey, Map<String, Long> activeStatInfoMap) {
        if (!CollectionUtils.isEmpty(activeStatInfoMap)) {
            List<OneCarActiveStat> finalOneCarActiveStatList = oneCarActiveStatList;
            activeStatInfoMap.forEach((activePoint, times) -> {
                OneCarActiveStat oneCarActiveStat = new OneCarActiveStat();
                oneCarActiveStat.setActive(activePoint);
                //活跃点的经纬度转换
                String activePointStr = redisClient.get(redisKey, activePoint);
                if (!StringUtils.isEmpty(activePointStr)) {
                    //如果是道路的信息
                    if (redisKey.equals(BaseBusinessRedis.DIC_DEVICE_KEY)) {
                        OrbitResDevice orbitResDevice = JSON.parseObject(activePointStr, OrbitResDevice.class);
                        oneCarActiveStat.setActiveName(orbitResDevice.getName());
                        oneCarActiveStat.setLat(orbitResDevice.getLatitude());
                        oneCarActiveStat.setLon(orbitResDevice.getLongitude());
                    }
                    //如果是区域的ID
                    if (redisKey.equals(BaseBusinessRedis.DIC_AREA_KEY)) {
                        //区域信息的经纬度由其他接口提供
                        OrbitResArea orbitResArea = JSON.parseObject(activePointStr, OrbitResArea.class);
                        oneCarActiveStat.setActiveName(orbitResArea.getName());
                    }
                }
                oneCarActiveStat.setTimes( activeStatInfoMap.get(activePoint));
                finalOneCarActiveStatList.add(oneCarActiveStat);
            });
            oneCarActiveStatList = finalOneCarActiveStatList.stream().sorted(Comparator.comparing(OneCarActiveStat::getTimes).reversed()).limit(10).collect(Collectors.toList());
        }
        return oneCarActiveStatList;
    }


    private String getNowDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YY_MM_DD_HH_MM_SS);
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    private String getAfterDayDateStr(String data) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YY_MM_DD);
        Date date = null;
        try {

            date = format.parse(data);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, +7);
        Date dateBefor7Days = c.getTime();
        return format.format(dateBefor7Days);
    }

    private int getAfterDayDateOfWeek(String data) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YY_MM_DD);
        Date date = null;
        try {

            date = format.parse(data);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int w = c.get(Calendar.DAY_OF_WEEK) - 1;
        return w;
    }

    private String getAfterDayDateStr(String data, int day, String dateForm) {
        SimpleDateFormat format = new SimpleDateFormat(dateForm);
        Date date = null;
        try {

            date = format.parse(data);
            //  System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        Date dateBeforDays = c.getTime();
        return format.format(dateBeforDays);
    }


    private String getBeforOneMonth() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YY_MM_DD_HH_MM_SS);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date dateBefor7Days = c.getTime();
        return format.format(dateBefor7Days);
    }

    private String getBeforThreeMonth() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YY_MM_DD_HH_MM_SS);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -3);
        Date dateBefor7Days = c.getTime();
        return format.format(dateBefor7Days);
    }

}

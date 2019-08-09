package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.TogetherVehicleBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.qo.TogetherVehicleQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.ITogetherVehicleService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.OribtTypeConverter;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.util.VehicleTrajectoryUtil;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.TogetherVehicleInfoVO;
import com.car.orbit.orbitservice.vo.TogetherVehicleVO;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;
import com.car.orbit.orbitutil.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: TogetherVehicleServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 同行车分析
 * @Author: monkjavaer
 * @Data: 2019/3/27 16:11
 * @Version: V1.0
 */
@Service
public class TogetherVehicleServiceImpl implements ITogetherVehicleService {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(TogetherVehicleServiceImpl.class);

    /**
     * es客服端
     */
    @Autowired
    private TransportClient transportClient;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    /**
     * 同行车分析
     *
     * @param togetherVehicleQO TogetherVehicleQO
     * @return List<TogetherVehicleVO>
     */
    @Override
    public VehicleSearchBO<TogetherVehicleVO> analysisTogetherVehicle(TogetherVehicleQO togetherVehicleQO) {
        int start = (togetherVehicleQO.getPageNo() - 1) * togetherVehicleQO.getPageSize();
        String key = togetherVehicleQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            //获得被查询车辆过车记录
            List<OrbitPassVehicleRecord> vehicleTrajectoryList = getOriginalTrajectory(togetherVehicleQO.getPlateNumber(),
                    togetherVehicleQO.getStartTime(), togetherVehicleQO.getEndTime(), togetherVehicleQO.getDeviceIdList());
            if (vehicleTrajectoryList.size() <= 1) {
                logger.info("The number of cameras passing the vehicle is less than or equal to 1");
                return new VehicleSearchBO<>(key, 0, new ArrayList<>());
            }

            OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
            //原始车辆过车轨迹按用户加车牌缓存到redis
            redisClient.setObjectEx(user.getId() + togetherVehicleQO.getPlateNumber(), vehicleTrajectoryList, 1200);
            //同行车结果集
            List<TogetherVehicleVO> togetherVehicles = getTogetherVehicle(vehicleTrajectoryList, togetherVehicleQO);
            //** 分页缓存查询结果 */
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, togetherVehicles, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            List<TogetherVehicleVO> pageList = redisClient.getListWithPage(searchKey, start, togetherVehicleQO.getPageSize(), TogetherVehicleVO.class);
            List<TogetherVehicleVO> pageListNew = (List<TogetherVehicleVO>) OribtTypeConverter.completeList(transportClient, pageList);
            return new VehicleSearchBO<>(searchKey, togetherVehicles.size(), pageListNew);
        } else {
            List<TogetherVehicleVO> pageList = redisClient.getListWithPage(key, start, togetherVehicleQO.getPageSize(), TogetherVehicleVO.class);
            Integer size = redisClient.getTotalPageSize(key);
            List<TogetherVehicleVO> pageListNew = (List<TogetherVehicleVO>) OribtTypeConverter.completeList(transportClient, pageList);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(key, size, pageListNew);
        }
    }

    /**
     * 同行车详细信息
     *
     * @param togetherVehicleQO TogetherVehicleQO
     * @return VehicleSearchBO<TogetherVehicleInfoVO>
     */
    @Override
    public TogetherVehicleBO togetherVehicleInfo(TogetherVehicleQO togetherVehicleQO) {
        int start = (togetherVehicleQO.getPageNo() - 1) * togetherVehicleQO.getPageSize();
        String key = togetherVehicleQO.getSearchKey();
        if (StringUtils.isBlank(key) || !redisClient.checkKeyExist(key)) {
            OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
            String togetherRedisKey = user.getId() + togetherVehicleQO.getPlateNumber();

            // 获取原始车辆的过车记录(在分析同行车时，将该结果会保存到redis，现在直接取出来使用)
            List<OrbitPassVehicleRecord> originalList;
            if (redisClient.checkKeyExist(togetherRedisKey)) {
                String str = redisClient.getJsonString(togetherRedisKey);
                originalList = JsonUtils.toBeanArray(str, OrbitPassVehicleRecord.class);
            } else {
                originalList = getOriginalTrajectory(togetherVehicleQO.getPlateNumber(),
                        togetherVehicleQO.getStartTime(), togetherVehicleQO.getEndTime(), togetherVehicleQO.getDeviceIdList());
            }

            List<OrbitPassVehicleRecord> togetherList = getTogetherPassVehicleRecord(originalList, togetherVehicleQO);

            //组装同行车轨迹
            List<VehicleTrajectoryVO> togetherTrajectoryList = VehicleTrajectoryUtil.getTrajectory(togetherList);

            //组装原始车轨迹
            List<VehicleTrajectoryVO> originalTrajectoryList = VehicleTrajectoryUtil.getTrajectory(originalList);

            //包装同行数据
            List<TogetherVehicleInfoVO> resultList = new ArrayList<>();
            for (OrbitPassVehicleRecord together : togetherList) {
                TogetherVehicleInfoVO togetherVehicleInfoVO = new TogetherVehicleInfoVO();
                for (OrbitPassVehicleRecord original : originalList) {
                    //分页显示经过相同点位的数据
                    if (together.getDeviceId().equals(original.getDeviceId())) {
                        long l1 = DateUtils.getDate(original.getCaptureTime()).getTime();
                        long l2 = DateUtils.getDate(together.getCaptureTime()).getTime();
                        long interval = l1 - l2;
                        if (interval < 0) {
                            interval = 0 - interval;
                        }
                        if (interval <= togetherVehicleQO.getJetLag() * 60000) {
                            //组装数据
                            getTogetherVehicleInfoVO(togetherVehicleInfoVO, together, original);
                            resultList.add(togetherVehicleInfoVO);
                            break;
                        }
                    }
                }
            }
            //** 分页缓存查询结果 */
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            Integer size = redisClient.getTotalPageSize(searchKey);
            List<TogetherVehicleInfoVO> pageList = redisClient.getListWithPage(searchKey, start, togetherVehicleQO.getPageSize(), TogetherVehicleInfoVO.class);

            return new TogetherVehicleBO(searchKey, size, originalTrajectoryList, togetherTrajectoryList, pageList);
        } else {
            List<TogetherVehicleInfoVO> pageList = redisClient.getListWithPage(key, start, togetherVehicleQO.getPageSize(), TogetherVehicleInfoVO.class);
            Integer size = redisClient.getTotalPageSize(key);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            return new TogetherVehicleBO(key, size, null, null, pageList);
        }
    }


    /**
     * 组装数据TogetherVehicleInfoVO
     *
     * @param togetherVehicleInfoVO
     * @param together
     * @param original
     * @return
     */
    private void getTogetherVehicleInfoVO(TogetherVehicleInfoVO togetherVehicleInfoVO,
                                          OrbitPassVehicleRecord together,
                                          OrbitPassVehicleRecord original) {
        togetherVehicleInfoVO.setOriginalPlateNumber(original.getPlateNumber());
        togetherVehicleInfoVO.setOriginalVid(original.getVid());
        togetherVehicleInfoVO.setOriginalCaptureTime(original.getCaptureTime());
        togetherVehicleInfoVO.setOriginalAdress(original.getCityName() + " " + original.getAreaName() + " " + original.getRoadName());
        togetherVehicleInfoVO.setOriginalVehicleType(original.getVehicleType());
        togetherVehicleInfoVO.setOriginalPhotourl(original.getPhotoOriFastdfsUrl());

        togetherVehicleInfoVO.setTogetherPlateNumber(together.getPlateNumber());
        togetherVehicleInfoVO.setTogetherVid(together.getVid());
        togetherVehicleInfoVO.setTogetherCaptureTime(together.getCaptureTime());
        togetherVehicleInfoVO.setTogetherAdress(together.getCityName() + " " + together.getAreaName() + " " + together.getRoadName());
        togetherVehicleInfoVO.setTogetherVehicleType(together.getVehicleType());
        togetherVehicleInfoVO.setTogetherPhotourl(together.getPhotoOriFastdfsUrl());
    }


    /**
     * 查询同行车
     *
     * @param vehicleTrajectoryList List<OrbitPassVehicleRecord>
     * @param togetherVehicleQO     TogetherVehicleQO
     * @return List<TogetherVehicleVO>
     */
    private List<TogetherVehicleVO> getTogetherVehicle(List<OrbitPassVehicleRecord> vehicleTrajectoryList,
                                                       TogetherVehicleQO togetherVehicleQO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        BoolQueryBuilder shouldQueryBuilder = QueryBuilders.boolQuery();
        //同行车最小和最大时间计算范围，用于计算索引
        //最先经过的点位减去时差就是最小时间
        String analysisStartTime = null;
        //最后经过的点位加上时差就是最大时间
        String analysisEndTime = null;

        //循环被查询车辆轨迹，拼装同行车分析语句
        for (int i = 0; i < vehicleTrajectoryList.size(); i++) {
            OrbitPassVehicleRecord orbitPassVehicleRecord = vehicleTrajectoryList.get(i);
            //根据时差计算单个点位开始时间和结束时间
            String startTime = DateUtils.getBeforeOrAfterMinute(orbitPassVehicleRecord.getCaptureTime(), -togetherVehicleQO.getJetLag());
            String endTime = DateUtils.getBeforeOrAfterMinute(orbitPassVehicleRecord.getCaptureTime(), togetherVehicleQO.getJetLag());
            //最先经过的点位
            if (i == 0) {
                analysisStartTime = startTime;
            }
            //最后经过的点位
            if (i == vehicleTrajectoryList.size() - 1) {
                analysisEndTime = endTime;
            }

            BoolQueryBuilder subQueryBuilder = QueryBuilders.boolQuery();
            RangeQueryBuilder rangequerybuilder = QueryBuilders
                    .rangeQuery(EsConstant.CAPTURE_TIME)
                    .from(startTime)
                    .to(endTime);
            subQueryBuilder.must(rangequerybuilder);

            //增加设备限制，同行车辆必须与原始车辆在相同点位出现
            if (StringUtils.isNotBlank(orbitPassVehicleRecord.getDeviceId())) {
                subQueryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, orbitPassVehicleRecord.getDeviceId()));
            }else{
                continue;
            }

            shouldQueryBuilder.should(subQueryBuilder);
        }

        queryBuilder.must(shouldQueryBuilder);

        //过滤原始车辆
        queryBuilder.mustNot(QueryBuilders.termsQuery(EsConstant.PLATE_NUMBER, togetherVehicleQO.getPlateNumber()));

        //过滤车辆类型
        if (togetherVehicleQO.getVehicleTypeList() != null && togetherVehicleQO.getVehicleTypeList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, togetherVehicleQO.getVehicleTypeList()));
        }

        //过滤车身颜色
        if (!StringUtils.isEmpty(togetherVehicleQO.getVehicleColor())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_COLOR, togetherVehicleQO.getVehicleColor()));
        }

        //过滤车辆品牌
        if (!StringUtils.isEmpty(togetherVehicleQO.getBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND, togetherVehicleQO.getBrand()));
        }

        //过滤车辆子品牌
        if (!StringUtils.isEmpty(togetherVehicleQO.getChildBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND_CHILD, togetherVehicleQO.getChildBrand()));
        }

        // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
        String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(analysisStartTime, analysisEndTime);

        //根据VID做聚合
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("together_term")
                .field(EsConstant.VID)
                .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)));

        SearchResponse response = transportClient.prepareSearch(indexArray)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .setSize(0)
                .get();
        //获取聚合结果
        Terms aggregation = response.getAggregations().get("together_term");
        List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
        List<TogetherVehicleVO> list = new ArrayList<>();
        for (Terms.Bucket bucket : buckets) {
            if (bucket.getDocCount() < togetherVehicleQO.getTogetherCount()) {
                continue;
            }
            TogetherVehicleVO togetherVehicleVO = new TogetherVehicleVO();
            togetherVehicleVO.setVid(bucket.getKeyAsString());
            togetherVehicleVO.setTogetherCount(bucket.getDocCount());
            list.add(togetherVehicleVO);
        }
        return list;
    }

    /**
     * 查询同行车的过车记录
     *
     * @param vehicleTrajectoryList
     * @param togetherVehicleQO
     * @return
     */
    private List<OrbitPassVehicleRecord> getTogetherPassVehicleRecord(List<OrbitPassVehicleRecord> vehicleTrajectoryList,
                                                                      TogetherVehicleQO togetherVehicleQO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        BoolQueryBuilder shouldQueryBuilder = QueryBuilders.boolQuery();
        //同行车最小和最大时间计算范围，用于计算索引
        //最先经过的点位减去时差就是最小时间
        String analysisStartTime = null;
        //最后经过的点位加上时差就是最大时间
        String analysisEndTime = null;

        //循环被查询车辆轨迹，拼装同行车分析语句
        for (int i = 0; i < vehicleTrajectoryList.size(); i++) {
            OrbitPassVehicleRecord orbitPassVehicleRecord = vehicleTrajectoryList.get(i);
            //根据时差计算单个点位开始时间和结束时间
            String startTime = DateUtils.getBeforeOrAfterMinute(orbitPassVehicleRecord.getCaptureTime(), -togetherVehicleQO.getJetLag());
            String endTime = DateUtils.getBeforeOrAfterMinute(orbitPassVehicleRecord.getCaptureTime(), togetherVehicleQO.getJetLag());
            //最先经过的点位
            if (i == 0) {
                analysisStartTime = startTime;
            }
            //最后经过的点位
            if (i == vehicleTrajectoryList.size() - 1) {
                analysisEndTime = endTime;
            }

            BoolQueryBuilder subQueryBuilder = QueryBuilders.boolQuery();
            RangeQueryBuilder rangequerybuilder = QueryBuilders
                    .rangeQuery(EsConstant.CAPTURE_TIME)
                    .from(startTime)
                    .to(endTime);
            subQueryBuilder.must(rangequerybuilder);

            //增加设备限制，同行车辆必须与原始车辆在相同点位出现
            if (StringUtils.isNotBlank(orbitPassVehicleRecord.getDeviceId())) {
                subQueryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, orbitPassVehicleRecord.getDeviceId()));
            }else{
                continue;
            }

            shouldQueryBuilder.should(subQueryBuilder);
        }

        queryBuilder.must(shouldQueryBuilder);
        //过滤车辆VID
        if (!StringUtils.isEmpty(togetherVehicleQO.getTogetherVid())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, togetherVehicleQO.getTogetherVid()));
        }

        //过滤车辆类型
        if (togetherVehicleQO.getVehicleTypeList() != null && togetherVehicleQO.getVehicleTypeList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, togetherVehicleQO.getVehicleTypeList()));
        }

        //过滤车身颜色
        if (!StringUtils.isEmpty(togetherVehicleQO.getVehicleColor())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_COLOR, togetherVehicleQO.getVehicleColor()));
        }

        //过滤车辆品牌
        if (!StringUtils.isEmpty(togetherVehicleQO.getBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND, togetherVehicleQO.getBrand()));
        }

        //过滤车辆子品牌
        if (!StringUtils.isEmpty(togetherVehicleQO.getChildBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND_CHILD, togetherVehicleQO.getChildBrand()));
        }

        // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
        String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(analysisStartTime, analysisEndTime);

        //开始查询
        SearchHits hits = transportClient
                .prepareSearch(indexArray)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                .execute()
                .actionGet()
                .getHits();

        //被查询车辆过车记录集合
        List<OrbitPassVehicleRecord> list = new ArrayList<>();
        //根据查询hits获得
        for (SearchHit searchHit : hits.getHits()) {
            String jsonStr = searchHit.getSourceAsString();
            list.add(JsonUtils.toBean(jsonStr, OrbitPassVehicleRecord.class));
        }

        return list;
    }

    /**
     * 获得被查询车辆过车记录
     *
     * @param plateNumber 被查询车辆车牌
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param deviceIdList 設備列表
     * @return List<OrbitPassVehicleRecord>
     */
    private List<OrbitPassVehicleRecord> getOriginalTrajectory(String plateNumber, String startTime, String endTime, List<String> deviceIdList) {
        //查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(startTime)
                .to(endTime);
        queryBuilder.must(rangequerybuilder);
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.PLATE_NUMBER, plateNumber));
        if (deviceIdList.size() > 0){
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, deviceIdList));
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
        String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(startTime, endTime);

        //开始查询
        SearchHits hits = transportClient
                .prepareSearch(indexArray)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ANALYSIS_TOGETHER_MAX)))
                .execute()
                .actionGet()
                .getHits();

        //被查询车辆过车记录集合
        List<OrbitPassVehicleRecord> vehicleTrajectoryList = new ArrayList<>();
        //根据查询hits获得
        for (SearchHit searchHit : hits.getHits()) {
            String jsonStr = searchHit.getSourceAsString();
            vehicleTrajectoryList.add(JsonUtils.toBean(jsonStr, OrbitPassVehicleRecord.class));
        }

        return vehicleTrajectoryList;
    }
}

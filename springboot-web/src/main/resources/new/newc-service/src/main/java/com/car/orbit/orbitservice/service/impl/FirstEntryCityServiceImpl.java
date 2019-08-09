package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.FirstEntryCityBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.qo.FirstEntryCityQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.IFirstEntryCityService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitutil.es.ESUtils;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.elasticsearch.search.aggregations.pipeline.bucketselector.BucketSelectorPipelineAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: FirstEntryCityServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 首次入城服务实现类
 * @Author: monkjavaer
 * @Date: 2019/03/25 16:03
 * @Version: V1.0
 */
@Service
public class FirstEntryCityServiceImpl implements IFirstEntryCityService {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    /**
     * 首次入城查询
     *
     * @param firstEntryCityQO
     * @return
     */
    @Override
    public VehicleSearchBO<FirstEntryCityBO> queryPageList(FirstEntryCityQO firstEntryCityQO) {
        int start = (firstEntryCityQO.getPageNo() - 1) * firstEntryCityQO.getPageSize();
        String key = firstEntryCityQO.getSearchKey();

        String excludeEndTime = firstEntryCityQO.getEntryStartTime();
        String excludeStartTime = DateUtils.format(DateUtils.addDays(excludeEndTime, 0 - firstEntryCityQO.getExcludeDays()));

        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            // 构建查询条件
            BoolQueryBuilder queryBuilder = createQueryBuilder(firstEntryCityQO);

            // 根据时间范围计算索引
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(
                    excludeStartTime,
                    firstEntryCityQO.getEntryEndTime());

            // 1 聚合车辆Id
            TermsAggregationBuilder aggregation = AggregationBuilders.terms("groupByVehicle")
                    .field(EsConstant.VID)
                    .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_TWICE_AGG_PAGESIZE)));

            // 2.1 聚合回溯时间范围过车次数
            FilterAggregationBuilder numsOfBefore = AggregationBuilders.filter("numsOfBefore", QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME)
                    .gte(excludeStartTime)
                    .lt(excludeEndTime));
            numsOfBefore.subAggregation(AggregationBuilders.count("size").field(EsConstant.ID));
            aggregation.subAggregation(numsOfBefore);

            // 2.2 聚合入城时间范围过车次数
            FilterAggregationBuilder numsOfAfter = AggregationBuilders.filter("numsOfAfter", QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME)
                    .gte(firstEntryCityQO.getEntryStartTime())
                    .lt(firstEntryCityQO.getEntryEndTime()));
            numsOfAfter.subAggregation(AggregationBuilders.count("size").field(EsConstant.ID));
            numsOfAfter.subAggregation(AggregationBuilders.terms("groupByCaptureTime")
                    .field(EsConstant.CAPTURE_TIME)
                    .order(Terms.Order.term(true))
                    .size(1));

            aggregation.subAggregation(numsOfAfter);

            Map<String, String> bucketsPathsMap = new HashMap<>();
            bucketsPathsMap.put("numsOfBefore", "numsOfBefore>size");
            bucketsPathsMap.put("numsOfAfter", "numsOfAfter>size");
            Script script = new Script("params.numsOfBefore <= 0 && params.numsOfAfter > 0");

            BucketSelectorPipelineAggregationBuilder bs =
                    PipelineAggregatorBuilders.bucketSelector("result", bucketsPathsMap, script);
            aggregation.subAggregation(bs);

            SearchResponse response = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(aggregation)
                    .setSize(0)
                    .get();

            // 结果解析
            List<FirstEntryCityBO> resultList = new ArrayList<>();

            Terms terms = response.getAggregations().get("groupByVehicle");
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
//            buckets.stream().forEach(bucket -> {
            for (int i = 0; i < buckets.size(); i++) {
                if (i == Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    break;

                Terms.Bucket bucket = buckets.get(i);

                FirstEntryCityBO firstEntryCityBO = new FirstEntryCityBO();

                String vid = bucket.getKeyAsString();
                firstEntryCityBO.setVid(vid);
                firstEntryCityBO.setBlackList(BlacklistRedis.isInBlacklist(vid));

                Filter filter = bucket.getAggregations().get("numsOfAfter");
                Terms termsTime = filter.getAggregations().get("groupByCaptureTime");
                for (Terms.Bucket bk : termsTime.getBuckets()) {
                    firstEntryCityBO.setEntryTime(bk.getKeyAsString());
                    break;
                }

                resultList.add(firstEntryCityBO);
            }
//            });

            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            List<FirstEntryCityBO> pageList = redisClient.getListWithPage(searchKey, start, firstEntryCityQO.getPageSize(), FirstEntryCityBO.class);
            queryMoreInformation(pageList, firstEntryCityQO.getDeviceIdList());
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<FirstEntryCityBO> pageList = redisClient.getListWithPage(firstEntryCityQO.getSearchKey(), start, firstEntryCityQO.getPageSize(), FirstEntryCityBO.class);
            queryMoreInformation(pageList, firstEntryCityQO.getDeviceIdList());
            Integer size = redisClient.getTotalPageSize(firstEntryCityQO.getSearchKey());
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(firstEntryCityQO.getSearchKey(), size, pageList);
        }
    }

    /**
     * 构建查询条件
     *
     * @param firstEnterCityQO
     * @return
     */
    private BoolQueryBuilder createQueryBuilder(FirstEntryCityQO firstEnterCityQO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        String excludeEndTime = firstEnterCityQO.getEntryStartTime();
        String excludeStartTime = DateUtils.format(DateUtils.addDays(excludeEndTime, 0 - firstEnterCityQO.getExcludeDays()));

        queryBuilder.must(QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(excludeStartTime)
                .to(firstEnterCityQO.getEntryEndTime()));

        if (firstEnterCityQO.getVehicleTypeList() != null && firstEnterCityQO.getVehicleTypeList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, firstEnterCityQO.getVehicleTypeList()));
        }
        if (!StringUtils.isEmpty(firstEnterCityQO.getVehicleColor())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_COLOR, firstEnterCityQO.getVehicleColor()));
        }
        if (!StringUtils.isEmpty(firstEnterCityQO.getBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND, firstEnterCityQO.getBrand()));
        }
        if (!StringUtils.isEmpty(firstEnterCityQO.getChildBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND_CHILD, firstEnterCityQO.getChildBrand()));
        }

        if (firstEnterCityQO.getDeviceIdList() != null && firstEnterCityQO.getDeviceIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, firstEnterCityQO.getDeviceIdList()));
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }

    /**
     * 根据车辆id和抓拍时间获取其他信息
     *
     * @param firstEntryCityBOList
     */
    private void queryMoreInformation(List<FirstEntryCityBO> firstEntryCityBOList, List<String> deviceIdList) {
        for (FirstEntryCityBO item : firstEntryCityBOList) {
            queryMoreInformation(item, deviceIdList);
        }
    }

    /**
     * 根据车辆id和抓拍时间获取其他信息
     *
     * @param firstEntryCityBO
     */
    private void queryMoreInformation(FirstEntryCityBO firstEntryCityBO, List<String> deviceIdList) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, firstEntryCityBO.getVid()));
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.CAPTURE_TIME, firstEntryCityBO.getEntryTime()));
        if (deviceIdList != null && deviceIdList.size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, deviceIdList));
        }

        int year = DateUtils.getYearOfDate(DateUtils.getDate(firstEntryCityBO.getEntryTime()));
        int week = DateUtils.getWeekInYear(DateUtils.getDate(firstEntryCityBO.getEntryTime()));
        String index = new StringBuilder().append(PassVehicleIndexUtil.ORBIT_PASS_VEHICLE_RECORD)
                .append(year)
                .append("_")
                .append(new DecimalFormat("00").format(week))
                .toString();

        SearchHits hits = transportClient
                .prepareSearch(index)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .setSize(1)
                .execute()
                .actionGet()
                .getHits();

        if (hits.getHits().length > 0) {
            SearchHit hit = hits.getHits()[0];
            // 车牌
            firstEntryCityBO.setPlateNumber(ESUtils.getField(hit, EsConstant.PLATE_NUMBER, String.class));
            // 车牌颜色
            firstEntryCityBO.setPlateColor(ESUtils.getField(hit, EsConstant.PLATE_COLOR, String.class));
            // 车辆类别
            firstEntryCityBO.setVehicleType(ESUtils.getField(hit, EsConstant.VEHICLE_TYPE, String.class));
            // 车辆颜色
            firstEntryCityBO.setVehicleColor(ESUtils.getField(hit, EsConstant.VEHICLE_COLOR, String.class));
            // 抓拍图片url
            firstEntryCityBO.setPhotoFastdfsUrl(ESUtils.getField(hit, EsConstant.PHOTO_ORI_FASTDFS_URL, String.class));
            // 城市名
            firstEntryCityBO.setCityName(ESUtils.getField(hit, EsConstant.CITY_NAME, String.class));
            // 区域名
            firstEntryCityBO.setAreaName(ESUtils.getField(hit, EsConstant.AREA_NAME, String.class));
            // 路口名
            firstEntryCityBO.setRoadName(ESUtils.getField(hit, EsConstant.ROAD_NAME, String.class));
            // 设备id
            firstEntryCityBO.setDeviceId(ESUtils.getField(hit, EsConstant.DEVICE_ID, String.class));
            // 设备名
            firstEntryCityBO.setDeviceName(ESUtils.getField(hit, EsConstant.DEVICE_NAME, String.class));
            // 品牌名
            String brandId = ESUtils.getField(hit, EsConstant.VEHICLE_BRAND, String.class);
            firstEntryCityBO.setBrandName(BrandRedis.getBrandNameByCode(brandId));
            // 子品牌名
            String brandChildId = ESUtils.getField(hit, EsConstant.VEHICLE_BRAND_CHILD, String.class);
            firstEntryCityBO.setChildBrandName(BrandRedis.getChildBrandNameByCode(brandChildId));

            firstEntryCityBO.setFullBrand(BrandRedis.getFullBrandNameByCode(brandId, brandChildId));

            // 经纬度
            String deviceId = ESUtils.getField(hit, EsConstant.DEVICE_ID, String.class);
            OrbitResDevice device = DevicePointRedis.getDevicePointByCode(deviceId);
            if (device != null) {
                firstEntryCityBO.setLatitude(device.getLatitude());
                firstEntryCityBO.setLongitude(device.getLongitude());
            }
        }
    }
}

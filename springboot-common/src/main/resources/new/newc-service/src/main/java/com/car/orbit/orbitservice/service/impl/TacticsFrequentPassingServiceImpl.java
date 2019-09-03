package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.TacticsFrequentPassingInfo;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.qo.TacticsFrequentPassingQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.ITacticsFrequentPassingService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.OribtTypeConverter;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateDate：2019/3/27 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-频繁过车service实现类
 **/
@Service
////@Transactional(rollbackFor = Exception.class)
public class TacticsFrequentPassingServiceImpl implements ITacticsFrequentPassingService {

    /** redis client */
    @Autowired
    private RedisClient redisClient;

    /** ES client */
    @Autowired
    private TransportClient transportClient;

    /** 第一层聚合名称，以VID为聚合条件 */
    private final String groupByVid = "group_by_vid";

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    /**
     * 查询频繁过车信息
     *
     * @param tacticsFrequentPassingQO 频繁过车查询条件
     * @return 频繁过车记录
     */
    @Override
    public VehicleSearchBO<TacticsFrequentPassingInfo> findFrequentPassingVehicles(TacticsFrequentPassingQO tacticsFrequentPassingQO) {
        // key是redis的缓存key，如果存在，说明之前已经查询过一次，在redis中有缓存
        String key = tacticsFrequentPassingQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(tacticsFrequentPassingQO.getStartTime(), tacticsFrequentPassingQO.getEndTime());
            // 进行查询
            Aggregations aggregations = transportClient
                    .prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(generateQueryBuilder(tacticsFrequentPassingQO))
                    .addAggregation(generateAggBuilder(tacticsFrequentPassingQO))
                    .setSize(0)
                    .execute()
                    .actionGet()
                    .getAggregations();

            List<TacticsFrequentPassingInfo> frequentPassingInfoList = new ArrayList<>();

            // 结果解析
            Terms terms = aggregations.get(groupByVid);
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            buckets.forEach(bucket -> {
                TacticsFrequentPassingInfo frequentPassingInfo = new TacticsFrequentPassingInfo();
                frequentPassingInfo.setVid(bucket.getKeyAsString());
                frequentPassingInfo.setSnapshotCount(bucket.getDocCount());
                frequentPassingInfoList.add(frequentPassingInfo);
            });

            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, frequentPassingInfoList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            int start = (tacticsFrequentPassingQO.getPageNo() - 1) * tacticsFrequentPassingQO.getPageSize();
            List<TacticsFrequentPassingInfo> pageList = redisClient.getListWithPage(searchKey, start, tacticsFrequentPassingQO.getPageSize(), TacticsFrequentPassingInfo.class);
            // 过车记录清洗为最新一条的记录
            List<TacticsFrequentPassingInfo> vehicleNewestMessage = (List<TacticsFrequentPassingInfo>) OribtTypeConverter.completeList(transportClient, pageList);
            return new VehicleSearchBO<>(searchKey, frequentPassingInfoList.size(), vehicleNewestMessage);
        } else {
            int start = (tacticsFrequentPassingQO.getPageNo() - 1) * tacticsFrequentPassingQO.getPageSize();
            List<TacticsFrequentPassingInfo> pageList = redisClient.getListWithPage(tacticsFrequentPassingQO.getSearchKey(), start, tacticsFrequentPassingQO.getPageSize(), TacticsFrequentPassingInfo.class);
            Integer size = redisClient.getTotalPageSize(tacticsFrequentPassingQO.getSearchKey());
            // 过车记录清洗为最新一条的记录
            List<TacticsFrequentPassingInfo> vehicleNewestMessage = (List<TacticsFrequentPassingInfo>) OribtTypeConverter.completeList(transportClient, pageList);
            return new VehicleSearchBO<>(tacticsFrequentPassingQO.getSearchKey(), size, vehicleNewestMessage);
        }
    }

    /**
     * 生成聚合查询条件
     *
     * @param tacticsFrequentPassingQO 查询条件
     * @return 聚合查询条件
     */
    private AggregationBuilder generateAggBuilder(TacticsFrequentPassingQO tacticsFrequentPassingQO) {
        TermsAggregationBuilder groupBuilder = AggregationBuilders.terms(groupByVid)
                .field(EsConstant.VID)
                .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                .minDocCount(tacticsFrequentPassingQO.getPassTimes());
        return groupBuilder;
    }

    /**
     * 生成只携带查询时间范围的query builder
     *
     * @param tacticsFrequentPassingQO 查询条件
     * @return query builder
     */
    private QueryBuilder generateQueryBuilder(TacticsFrequentPassingQO tacticsFrequentPassingQO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 查询时间
        String str1 = DateUtils.format(DateUtils.getDate(tacticsFrequentPassingQO.getStartTime()));
        String str2 = DateUtils.format(DateUtils.getDate(tacticsFrequentPassingQO.getEndTime()));
        RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME).from(str1).to(str2);
        queryBuilder.must(timeRangeQuery);

        // 车辆颜色
        if (StringUtils.isNotBlank(tacticsFrequentPassingQO.getVehicleColor())) {
            TermQueryBuilder vehicleColorTermQuery = QueryBuilders.termQuery(EsConstant.VEHICLE_COLOR, tacticsFrequentPassingQO.getVehicleColor());
            queryBuilder.must(vehicleColorTermQuery);
        }

        // 车辆品牌
        if (StringUtils.isNotBlank(tacticsFrequentPassingQO.getBrand())) {
            TermQueryBuilder vehicleBrandTermQuery = QueryBuilders.termQuery(EsConstant.VEHICLE_BRAND, tacticsFrequentPassingQO.getBrand());
            queryBuilder.must(vehicleBrandTermQuery);
            // 车辆子品牌
            if (StringUtils.isNotBlank(tacticsFrequentPassingQO.getChildBrand())) {
                TermQueryBuilder vehicleChildBrandTermQuery = QueryBuilders.termQuery(EsConstant.VEHICLE_BRAND_CHILD, tacticsFrequentPassingQO.getChildBrand());
                queryBuilder.must(vehicleChildBrandTermQuery);
            }
        }

        // 车辆类型
        if (!CollectionUtils.isEmpty(tacticsFrequentPassingQO.getVehicleType())) {
            TermsQueryBuilder vehicleTypeTermsQuery = QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, tacticsFrequentPassingQO.getVehicleType());
            queryBuilder.must(vehicleTypeTermsQuery);
        }

        // 设备编号
        if (!CollectionUtils.isEmpty(tacticsFrequentPassingQO.getDeviceIdList())) {
            TermsQueryBuilder roadTermsQuery = QueryBuilders.termsQuery(EsConstant.DEVICE_ID, tacticsFrequentPassingQO.getDeviceIdList());
            queryBuilder.must(roadTermsQuery);
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }
}
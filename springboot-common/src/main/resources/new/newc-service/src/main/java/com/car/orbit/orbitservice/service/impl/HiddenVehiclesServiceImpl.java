package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.qo.HiddenVehiclesQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.IHiddenVehiclesService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.OribtTypeConverter;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.vo.HiddenVehiclesVO;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregatorBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
@Service
public class HiddenVehiclesServiceImpl implements IHiddenVehiclesService {
    @Autowired
    RedisClient redisClient;

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    /**
     * @param hiddenVehiclesQO
     * @return
     * @description 构建查询条件
     * @date: 2019-3-27 14:45
     * @author: monkjavaer
     */
    private BoolQueryBuilder createQueryBuilder(HiddenVehiclesQO hiddenVehiclesQO, String startTime, String endTime) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //筛选案发前后时间
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(startTime)
                .to(endTime);
        queryBuilder.must(rangequerybuilder);
        //筛选隐匿车辆类型
        if (hiddenVehiclesQO.getVehicleTypeList() != null && hiddenVehiclesQO.getVehicleTypeList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, hiddenVehiclesQO.getVehicleTypeList()));
        }
        //筛选车辆品牌子品牌
        if (hiddenVehiclesQO.getBrand() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VEHICLE_BRAND, hiddenVehiclesQO.getBrand()));
        }
        if (hiddenVehiclesQO.getChildBrand() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VEHICLE_BRAND_CHILD, hiddenVehiclesQO.getChildBrand()));
        }
        //筛选卡口列表
        if (hiddenVehiclesQO.getDeviceIdList() != null && hiddenVehiclesQO.getDeviceIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, hiddenVehiclesQO.getDeviceIdList()));
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }
        //筛选车身颜色
        if (hiddenVehiclesQO.getVehicleColor() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VEHICLE_COLOR, hiddenVehiclesQO.getVehicleColor()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }

    /**
     * @param hiddenVehiclesQO
     * @return
     * @description 查询隐匿车辆，返回分页结果
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     */
    @Override
    public VehicleSearchBO<HiddenVehiclesVO> search(HiddenVehiclesQO hiddenVehiclesQO) {
        int start = (hiddenVehiclesQO.getPageNo() - 1) * hiddenVehiclesQO.getPageSize();
        String key = hiddenVehiclesQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            String aggName = "groupByVehicle";
            /** 构造过车记录检索条件*/
            String endTime = DateUtils.getBeforeOrAfterMinute(hiddenVehiclesQO.getTimeOfTheIncident(),
                    hiddenVehiclesQO.getTimePeriodBeforeAfterTheIncident() * 24 * 60);
            String startTime = DateUtils.getBeforeOrAfterMinute(hiddenVehiclesQO.getTimeOfTheIncident(),
                    hiddenVehiclesQO.getTimePeriodBeforeAfterTheIncident() * 24 * 60 * -1);

            BoolQueryBuilder queryBuilder = createQueryBuilder(hiddenVehiclesQO, startTime, endTime);

            /** 按照车辆VID进行聚合*/
            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName).field(EsConstant.VID)
                    .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_TWICE_AGG_PAGESIZE)))
                    .minDocCount(hiddenVehiclesQO.getPassesBeforeTheIncident());

            /** 针对车辆聚合结果进行二次聚合，计算案发前通行次数和案发后通行次数*/
            aggregationBuilder
                    .subAggregation(
                            AggregationBuilders.filter("numsOfBefore", QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME).lt(hiddenVehiclesQO.getTimeOfTheIncident()))
                                    .subAggregation(AggregationBuilders.count("size").field(EsConstant.ID))
                    )
                    .subAggregation(
                            AggregationBuilders.filter("numsOfAfter", QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME).gt(hiddenVehiclesQO.getTimeOfTheIncident()))
                                    .subAggregation(AggregationBuilders.count("size").field(EsConstant.ID))
                    );
            /** 过滤出符合隐匿车辆条件的结果*/
            Script script = new Script(
                    String.format("params.numsOfBefore >= %d && params.numsOfAfter <= %d",
                            hiddenVehiclesQO.getPassesBeforeTheIncident(), hiddenVehiclesQO.getPassesAfterTheIncident())
            );
            Map<String, String> bucketsPathsMap = new HashMap<>();
            bucketsPathsMap.put("numsOfBefore", "numsOfBefore>size");
            bucketsPathsMap.put("numsOfAfter", "numsOfAfter>size");

            aggregationBuilder
                    .subAggregation(
                            PipelineAggregatorBuilders.bucketSelector("result", bucketsPathsMap, script)
                    );

            /** 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week] */
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(startTime, endTime);

            /** 解析查询结果 */
            SearchResponse response = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(aggregationBuilder)
                    .setSize(0)
                    .execute()
                    .actionGet();

            //存储隐匿车辆列表
            List<HiddenVehiclesVO> hiddenVehiclesVOList = new ArrayList<>();

            Terms it = response.getAggregations().get(aggName);
            List<Terms.Bucket> buckets = (List<Terms.Bucket>) it.getBuckets();
            for (int i = 0; i < buckets.size(); i++) {
                if (i == Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    break;

                Terms.Bucket lb = buckets.get(i);
                //获取每辆车总过车次数
                HiddenVehiclesVO hiddenVehiclesVO = new HiddenVehiclesVO();
                hiddenVehiclesVO.setVid(lb.getKeyAsString());
                hiddenVehiclesVO.setSnapshotCount((int) lb.getDocCount());
                //获取每辆车案发前过车次数
                hiddenVehiclesVO.setSnapshotCountBeforeIncident((int) ((InternalValueCount) ((InternalFilter) lb.getAggregations().get("numsOfBefore")).getAggregations().get("size")).getValue());
                //获取每辆车案发后过车次数
                hiddenVehiclesVO.setGetSnapshotCountAfterIncident((int) ((InternalValueCount) ((InternalFilter) lb.getAggregations().get("numsOfAfter")).getAggregations().get("size")).getValue());
                //添加到隐匿车列表中
                hiddenVehiclesVOList.add(hiddenVehiclesVO);
            }
            /** 分页缓存查询结果 */
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, hiddenVehiclesVOList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            /** 返回结果 */
            List<HiddenVehiclesVO> pageList = redisClient.getListWithPage(searchKey, start, hiddenVehiclesQO.getPageSize(), HiddenVehiclesVO.class);
            List<HiddenVehiclesVO> pageListNew = (List<HiddenVehiclesVO>) OribtTypeConverter.completeList(transportClient, pageList);
            VehicleSearchBO<HiddenVehiclesVO> vehicleSearchBO = new VehicleSearchBO<>(searchKey, hiddenVehiclesVOList.size(), pageListNew);
            return vehicleSearchBO;
        } else {
            /** 从缓存中获取数据并更新过期时间,第二次开始不返回总数*/
            List<HiddenVehiclesVO> pageList = redisClient.getListWithPage(hiddenVehiclesQO.getSearchKey(), start, hiddenVehiclesQO.getPageSize(), HiddenVehiclesVO.class);
            List<HiddenVehiclesVO> pageListNew = (List<HiddenVehiclesVO>) OribtTypeConverter.completeList(transportClient, pageList);
            redisClient.expire(hiddenVehiclesQO.getSearchKey(), Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            int count = redisClient.getTotalPageSize(hiddenVehiclesQO.getSearchKey());
            return new VehicleSearchBO<>(hiddenVehiclesQO.getSearchKey(), count, pageListNew);
        }
    }
}

package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.qo.DayHideNightOutQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.ITacticsDayHideNightOutService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.OribtTypeConverter;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.util.redis.TacticsDayHideNightOutRedis;
import com.car.orbit.orbitservice.vo.DayHideNightOutVO;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.commons.lang.StringUtils;
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
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Title: TacticsDayHideNightOutServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 战法研判==昼伏夜出+夜间频出
 * @Author: zks
 * @Date: 2019/03/29 09:44
 * @Version: V1.0
 */
@Service
public class TacticsDayHideNightOutServiceImpl implements ITacticsDayHideNightOutService {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    @Override
    public VehicleSearchBO<DayHideNightOutVO> queryPageList(DayHideNightOutQO qo) {
        int start = (qo.getPageNo() - 1) * qo.getPageSize();
        String key = qo.getSearchKey();
        //查询昼伏夜出列表
        if (StringUtils.isEmpty(key) || !TacticsDayHideNightOutRedis.checkKeyExist(key, qo.isDayNightFlag())) {
            //查询列表
            String aggName = "groupByVehicle";
            /** 构造过车记录检索条件*/
            String startTime = qo.getStartTime();
            String endTime = qo.getEndTime();
            BoolQueryBuilder queryBuilder = createQueryBuilder(qo, startTime, endTime);

            /** 按照车辆VID进行聚合[增加最新聚合结果>晚上过车数]*/
            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName)
                    .field(EsConstant.VID)
                    .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_TWICE_AGG_PAGESIZE)))
                    .minDocCount(qo.getNightTimePass());
            /** 针对车辆聚合结果进行二次聚合，计算白天出现次数和晚上出现次数*/
            aggregationBuilder
                    .subAggregation(
                            AggregationBuilders.filter("dayPassNum", QueryBuilders.rangeQuery(EsConstant.CAPTURE_HOUR).gte(qo.getDayTimeStart()).lt(qo.getDayTimeEnd()))
                                    .subAggregation(AggregationBuilders.count("size").field(EsConstant.ID))
                    )
                    .subAggregation(
                            AggregationBuilders.filter("nightPassNum", QueryBuilders.boolQuery().should(QueryBuilders.rangeQuery(EsConstant.CAPTURE_HOUR).lt(qo.getDayTimeStart())).should(QueryBuilders.rangeQuery(EsConstant.CAPTURE_HOUR).gte(qo.getDayTimeEnd())))
                                    .subAggregation(AggregationBuilders.count("size").field(EsConstant.ID))
                    );

            /** 过滤出符合条件的结果*/
            Script script = null;
            Map<String, String> bucketsPathsMap = new HashMap<>();
            if (qo.isDayNightFlag()) {
                script = new Script(String.format("params.dayPassNum <= %d && params.nightPassNum >= %d", qo.getDayTimePass(), qo.getNightTimePass()));
                bucketsPathsMap.put("dayPassNum", "dayPassNum>size");
                bucketsPathsMap.put("nightPassNum", "nightPassNum>size");
            } else {
                script = new Script(String.format("params.nightPassNum >= %d", qo.getNightTimePass()));
                bucketsPathsMap.put("nightPassNum", "nightPassNum>size");
            }

            aggregationBuilder.subAggregation(PipelineAggregatorBuilders.bucketSelector("result", bucketsPathsMap, script));

            /** 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week] */
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(startTime, endTime);

            // 输出DSL查询语言
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(queryBuilder);

            /** 解析查询结果 */
            SearchResponse response = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(aggregationBuilder)
                    .setSize(0)
                    .execute()
                    .actionGet();

            //存储DayHideNightOutVO列表
            List<DayHideNightOutVO> dayHideNightOutVOList = new ArrayList<>();

            Terms it = response.getAggregations().get(aggName);
            List<Terms.Bucket> buckets = (List<Terms.Bucket>) it.getBuckets();
            for (int i = 0; i < buckets.size(); i++) {
                if (i == Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    break;

                Terms.Bucket lb = buckets.get(i);
                DayHideNightOutVO vo = new DayHideNightOutVO();
                vo.setVid(lb.getKeyAsString());
                vo.setAllCount(String.valueOf((int) lb.getDocCount()));
                int dayNum = 0;
                if (qo.isDayNightFlag()) {
                    dayNum = (int) ((InternalValueCount) ((InternalFilter) lb.getAggregations().get("dayPassNum")).getAggregations().get("size")).getValue();
                }
                int nightNum = (int) ((InternalValueCount) ((InternalFilter) lb.getAggregations().get("nightPassNum")).getAggregations().get("size")).getValue();
                vo.setDayCount(String.valueOf(dayNum));
                vo.setNightCount(String.valueOf(nightNum));
                dayHideNightOutVOList.add(vo);
            }

            Collections.sort(dayHideNightOutVOList, (o1, o2) -> {
                int c1 = Integer.parseInt(o1.getNightCount());
                int c2 = Integer.parseInt(o2.getNightCount());

                if (c1 < c2) return 1;
                else if (c1 > c2) return -1;
                else return 0;
            });

            /** 分页缓存查询结果 */
            String searchKey = UUIDUtils.generate();
            TacticsDayHideNightOutRedis.saveWithPage(searchKey, dayHideNightOutVOList, qo.isDayNightFlag());

            /** 返回结果 */
            List<DayHideNightOutVO> pageList = TacticsDayHideNightOutRedis.getWithPage(searchKey, start, qo.getPageSize(), qo.isDayNightFlag());
            List<DayHideNightOutVO> pageListNew = (List<DayHideNightOutVO>) OribtTypeConverter.completeList(transportClient, pageList);
            VehicleSearchBO<DayHideNightOutVO> vehicleSearchBO = new VehicleSearchBO<DayHideNightOutVO>(searchKey, dayHideNightOutVOList.size(), pageListNew);
            return vehicleSearchBO;
        } else {
            /** 从缓存中获取数据并更新过期时间,第二次开始不返回总数*/
            List<DayHideNightOutVO> pageList = TacticsDayHideNightOutRedis.getWithPage(qo.getSearchKey(), start, qo.getPageSize(), qo.isDayNightFlag());
            List<DayHideNightOutVO> pageListNew = (List<DayHideNightOutVO>) OribtTypeConverter.completeList(transportClient, pageList);
            TacticsDayHideNightOutRedis.expire(qo.getSearchKey(), qo.isDayNightFlag());
            Integer size = TacticsDayHideNightOutRedis.getTotalCount(qo.getSearchKey(), qo.isDayNightFlag());
            return new VehicleSearchBO<>(qo.getSearchKey(), size, pageListNew);
        }
    }

    /**
     * @param qo
     * @param startTime
     * @param endTime
     * @description 构建查询条件
     * @date: 2019-3-28 14:45
     * @author: zks
     */
    private BoolQueryBuilder createQueryBuilder(DayHideNightOutQO qo, String startTime, String endTime) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //筛选时间段内过车信息
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(startTime)
                .to(endTime);
        queryBuilder.must(rangequerybuilder);
        //筛选车辆类型
        if (qo.getVehicleTypeList() != null && qo.getVehicleTypeList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, qo.getVehicleTypeList()));
        }
        //筛选车辆品牌子品牌
        if (qo.getBrandCode() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VEHICLE_BRAND, qo.getBrandCode()));
        }
        if (qo.getChildBrandCode() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VEHICLE_BRAND_CHILD, qo.getChildBrandCode()));
        }
        //筛选卡口列表
        if (qo.getDeviceIds() != null && qo.getDeviceIds().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, qo.getDeviceIds()));
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }
        //筛选车身颜色
        if (qo.getBodyColorCode() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VEHICLE_COLOR, qo.getBodyColorCode()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());
        return queryBuilder;
    }
}

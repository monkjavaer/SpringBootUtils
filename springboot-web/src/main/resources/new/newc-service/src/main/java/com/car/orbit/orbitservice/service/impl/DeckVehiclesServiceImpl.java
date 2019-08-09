package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.PassDeckVehicleDetail;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitPassDeckVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.qo.DeckVehiclesQO;
import com.car.orbit.orbitservice.service.*;
import com.car.orbit.orbitservice.util.OribtTypeConverter;
import com.car.orbit.orbitservice.util.VehicleTrajectoryUtil;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitservice.vo.DeckVehiclesVO;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
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
import org.elasticsearch.search.aggregations.metrics.max.InternalMax;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
@Service
public class DeckVehiclesServiceImpl implements IDeckVehiclesService {
    @Autowired
    RedisClient redisClient;

    @Autowired
    private TransportClient transportClient;

    /** 黑名单管理service */
    @Autowired
    private IBlackListService blackListService;


    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    @Autowired
    private IPassVehicleRecordService passVehicleRecordService;

    /**
     * @param deckVehiclesQO
     * @param startTime
     * @param endTime
     * @return
     * @description 构建查询条件
     * @date: 2019-3-27 14:45
     * @author: monkjavaer
     */
    private BoolQueryBuilder createQueryBuilder(DeckVehiclesQO deckVehiclesQO, String startTime, String endTime) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //筛选前后时间
        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(startTime)
                .to(endTime);
        queryBuilder.must(rangequerybuilder);

        //筛选卡口列表
        if (deckVehiclesQO.getDeviceIdList() != null && deckVehiclesQO.getDeviceIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, deckVehiclesQO.getDeviceIdList()));
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }

        //筛选车牌
        if (deckVehiclesQO.getPlateNumber() != null && !"".equals(deckVehiclesQO.getPlateNumber().trim())) {
            queryBuilder.must(QueryBuilders.wildcardQuery(EsConstant.PLATE_NUMBER, "*" + deckVehiclesQO.getPlateNumber() + "*"));
        }

        //筛选车牌颜色
        if (deckVehiclesQO.getPlateColor() != null && !"".equals(deckVehiclesQO.getPlateColor().trim())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.PLATE_COLOR, deckVehiclesQO.getPlateColor()));
        }

        //筛选存在异常情况的过车信息
        //queryBuilder.must(QueryBuilders.existsQuery(EsConstant.EXCEPTION));
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.EXCEPTION, "2"));

        //筛选处理状态
        if (deckVehiclesQO.getStatus() != null) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.HANDLE_STATUS, deckVehiclesQO.getStatus()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }

    /**
     * @param deckVehiclesQO
     * @return
     * @description 构建排除套牌的查询条件
     * @date: 2019-3-27 14:45
     * @author: monkjavaer
     */
    private BoolQueryBuilder createExcludeQueryBuilder(DeckVehiclesQO deckVehiclesQO) {
        BoolQueryBuilder queryBuilder;

        String startTime = deckVehiclesQO.getStartTime();
        String endTime = deckVehiclesQO.getEndTime();

        queryBuilder = createQueryBuilder(deckVehiclesQO, startTime, endTime);

        //筛选车辆vid
        if (deckVehiclesQO.getVid() != null && !"".equals(deckVehiclesQO.getVid().trim())) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.VID, deckVehiclesQO.getVid()));
        }

        //筛选异常过车状态
        if (deckVehiclesQO.getException() != null && !"".equals(deckVehiclesQO.getException().trim())) {
            queryBuilder.must(QueryBuilders.termQuery(EsConstant.EXCEPTION, deckVehiclesQO.getException()));
        }

        return queryBuilder;
    }

    /**
     * @param deckVehiclesVOArrayList
     * @param black,是否在布控中,true表示布控
     * @return
     * @description 筛选出列表中符合黑名单条件的数据
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     */
    private List<DeckVehiclesVO> filterByBlackList(List<DeckVehiclesVO> deckVehiclesVOArrayList, boolean black) {
        List<DeckVehiclesVO> newDeckVehiclesVOList = new ArrayList<>();
        for (int i = 0; i < deckVehiclesVOArrayList.size(); i++) {
            if (black) {
                //筛选黑名单车辆
                if (blackListService.isInBlackList(deckVehiclesVOArrayList.get(i).getVid())) {
                    newDeckVehiclesVOList.add(deckVehiclesVOArrayList.get(i));
                }
            } else {
                //筛选非黑名单车辆
                if (!blackListService.isInBlackList(deckVehiclesVOArrayList.get(i).getVid())) {
                    newDeckVehiclesVOList.add(deckVehiclesVOArrayList.get(i));
                }
            }
        }
        return newDeckVehiclesVOList;
    }

    /**
     * @param deckVehiclesQO
     * @return
     * @description 查询套牌车辆，返回分页结果
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     */
    @Override
    public VehicleSearchBO<DeckVehiclesVO> search(DeckVehiclesQO deckVehiclesQO) {
        int start = (deckVehiclesQO.getPageNo() - 1) * deckVehiclesQO.getPageSize();
        String key = deckVehiclesQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            String aggName = "groupByVehicle";
            /** 构造过车记录检索条件*/
            String startTime = deckVehiclesQO.getStartTime();
            String endTime = deckVehiclesQO.getEndTime();

            BoolQueryBuilder queryBuilder = createQueryBuilder(deckVehiclesQO, startTime, endTime);

            /** 按照车辆VID进行聚合*/
            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms(aggName).field(EsConstant.VID)
                    .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .order(Terms.Order.term(true));

            /** 针对车辆聚合结果进行二次聚合，计算不同类型套牌的抓拍次数,同时计算最近的抓拍时间*/
            aggregationBuilder
                    .subAggregation(
                            AggregationBuilders.terms("exception").field(EsConstant.EXCEPTION).size(100)
                                    .subAggregation(AggregationBuilders.terms("handleStatus").field(EsConstant.HANDLE_STATUS).size(100)
                                            .subAggregation(AggregationBuilders.max("latestCaptureTime").field(EsConstant.CAPTURE_TIME)))
                    );

            /** 计算索引 */
            String[] indexArray = {EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD};

            /** 解析查询结果 */
            SearchResponse response = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(aggregationBuilder)
                    .setSize(0)
                    .execute()
                    .actionGet();

            //存储套牌车辆列表
            List<DeckVehiclesVO> deckVehiclesVOArrayList = new ArrayList<>();

            Terms it = response.getAggregations().get(aggName);
            List<Terms.Bucket> buckets = (List<Terms.Bucket>) it.getBuckets();
            for (int i = 0; i < buckets.size(); i++) {
                Terms.Bucket lb = buckets.get(i);
                //获取每辆车的VID
                String Vid = lb.getKeyAsString();
                //获取每辆车对应的套牌异常类型、对应的过车记录数量、最近的抓拍时间、处理状态
                List<Terms.Bucket> subBuckets = (List<Terms.Bucket>) ((Terms) lb.getAggregations().get("exception")).getBuckets();
                for (int iException = 0; iException < subBuckets.size(); iException++) {
                    Terms.Bucket lbException = subBuckets.get(iException);
                    List<Terms.Bucket> handleStatusBuckets = (List<Terms.Bucket>) ((Terms) lbException.getAggregations().get("handleStatus")).getBuckets();
                    for (int iHandle = 0; iHandle < handleStatusBuckets.size(); iHandle++) {
                        Terms.Bucket lbHandle = handleStatusBuckets.get(iHandle);
                        DeckVehiclesVO deckVehiclesVO = new DeckVehiclesVO();
                        deckVehiclesVO.setVid(Vid);
                        deckVehiclesVO.setException(lbException.getKeyAsString());
                        deckVehiclesVO.setDeckRecord((int) lbException.getDocCount());
                        deckVehiclesVO.setCaptureTime(((InternalMax) lbHandle.getAggregations().get("latestCaptureTime")).getValueAsString());
                        deckVehiclesVO.setStatus(lbHandle.getKeyAsString());
                        //添加到套牌车列表中
                        deckVehiclesVOArrayList.add(deckVehiclesVO);
                    }
                }
            }

            /** 根据查询条件是否在黑名单中筛选数据*/
            if (deckVehiclesQO.getBlack() != null) {
                deckVehiclesVOArrayList = filterByBlackList(deckVehiclesVOArrayList, deckVehiclesQO.getBlack());
            }

            /** 分页缓存查询结果 */
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, deckVehiclesVOArrayList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            /** 返回结果 */
            List<DeckVehiclesVO> pageList = redisClient.getListWithPage(searchKey, start, deckVehiclesQO.getPageSize(), DeckVehiclesVO.class);
            List<DeckVehiclesVO> pageListNew = (List<DeckVehiclesVO>) OribtTypeConverter.completeList(transportClient, pageList);
            VehicleSearchBO<DeckVehiclesVO> vehicleSearchBO = new VehicleSearchBO<>(searchKey, deckVehiclesVOArrayList.size(), pageListNew);
            return vehicleSearchBO;
        } else {
            /** 从缓存中获取数据并更新过期时间,第二次开始不返回总数*/
            List<DeckVehiclesVO> pageList = redisClient.getListWithPage(deckVehiclesQO.getSearchKey(), start, deckVehiclesQO.getPageSize(), DeckVehiclesVO.class);
            List<DeckVehiclesVO> pageListNew = (List<DeckVehiclesVO>) OribtTypeConverter.completeList(transportClient, pageList);
            redisClient.expire(deckVehiclesQO.getSearchKey(), Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            int count = redisClient.getTotalPageSize(deckVehiclesQO.getSearchKey());
            return new VehicleSearchBO<>(deckVehiclesQO.getSearchKey(), count, pageListNew);
        }
    }


    /**
     * 查询套牌车详细
     *
     * @param deckVehiclesQO
     * @return
     */
    public VehicleSearchBO<PassDeckVehicleDetail> queryDeckVehiclesDetail(DeckVehiclesQO deckVehiclesQO) {
        int start = (deckVehiclesQO.getPageNo() - 1) * deckVehiclesQO.getPageSize();
        String key = deckVehiclesQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            String startTime = deckVehiclesQO.getStartTime();
            String endTime = deckVehiclesQO.getEndTime();
            BoolQueryBuilder queryBuilder = createQueryBuilder(deckVehiclesQO, startTime, endTime);

            if (deckVehiclesQO.getVid() != null && !"".equals(deckVehiclesQO.getVid().trim())) {
                queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, deckVehiclesQO.getVid()));
            }

            String[] indexArray = {EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD};
            SearchHits hits = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addSort(EsConstant.CAPTURE_TIME, SortOrder.DESC)
                    .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .execute()
                    .actionGet()
                    .getHits();

            List<PassDeckVehicleDetail> resultList = new ArrayList<>();

            for (SearchHit searchHit : hits.getHits()) {
                /** recordA **/
                String jsonStr = searchHit.getSourceAsString();
                OrbitPassDeckVehicleRecord recordA = JsonUtils.toBean(jsonStr, OrbitPassDeckVehicleRecord.class);

                if (deckVehiclesQO.getBlack() != null) {
                    if (deckVehiclesQO.getBlack() && !BlacklistRedis.isInBlacklist(recordA.getVid())) {
                        continue;
                    }
                    if (!deckVehiclesQO.getBlack() && BlacklistRedis.isInBlacklist(recordA.getVid())) {
                        continue;
                    }
                }

                String brandName = BrandRedis.getBrandNameByCode(recordA.getVehicleBrand());
                recordA.setBrandName(brandName);
                String brandChildName = BrandRedis.getChildBrandNameByCode(recordA.getVehicleBrandChild());
                recordA.setChildBrandName(brandChildName);
                recordA.setFullBrand(BrandRedis.getFullBrandNameByCode(recordA.getVehicleBrandChild()));
                OrbitResDevice device = DevicePointRedis.getDevicePointByCode(recordA.getDeviceId());
                if (device != null) {
                    recordA.setLatitude(device.getLatitude());
                    recordA.setLongitude(device.getLongitude());
                }
                recordA.setPhotoFastdfsUrl(recordA.getPhotoOriFastdfsUrl());

                /** recordB **/
                OrbitPassVehicleRecord recordB = null;
                if (StringUtils.isNotEmpty(recordA.getIdLast()) && StringUtils.isNotEmpty(recordA.getCaptureTimeLast())) {
                    recordB = passVehicleRecordService.queryPassVehicleRecordById(recordA.getIdLast(),
                            DateUtils.getDate(recordA.getCaptureTimeLast()));
                }

                /** 整合数据 **/
                OrbitPassVehicleRecord[] vehicle = new OrbitPassVehicleRecord[2];
                vehicle[0] = recordA;
                // TODO
                if (recordB != null) {
                    vehicle[1] = recordB;
                } else {
                    vehicle[1] = recordA;
                }

                PassDeckVehicleDetail deckVehicleDetail = new PassDeckVehicleDetail();
                deckVehicleDetail.setVehicle(vehicle);
                deckVehicleDetail.setException(recordA.getException());
                if (recordA.getDistance() != null) {
                    deckVehicleDetail.setDistance(recordA.getDistance() / 1000);
                } else {
                    deckVehicleDetail.setDistance((float) 0);
                }
                deckVehicleDetail.setHandleStatus(recordA.getHandleStatus());
                deckVehicleDetail.setCaptureTmDiffer(DateUtils.getIntervalSeconds(vehicle[0].getCaptureTime(), vehicle[1].getCaptureTime()));
                if (deckVehicleDetail.getCaptureTmDiffer() == 0) {
                    deckVehicleDetail.setEstimatedSpeed((float) 0.0);
                } else {
                    if (deckVehicleDetail.getDistance() == null || deckVehicleDetail.getCaptureTmDiffer() == null) {
                        deckVehicleDetail.setEstimatedSpeed((float) 0.0);
                    } else {
                        deckVehicleDetail.setEstimatedSpeed(deckVehicleDetail.getDistance() / deckVehicleDetail.getCaptureTmDiffer() * 3600);
                    }
                }

                /** 轨迹 **/
                List<OrbitPassVehicleRecord> recordList = new ArrayList<>();
                recordList.add(recordA);

                if (recordB != null) {
                    recordList.add(recordB);
                } else {
                    recordList.add(recordA);
                }
                List<VehicleTrajectoryVO> trajectoryList = VehicleTrajectoryUtil.getTrajectory(recordList);
                deckVehicleDetail.setTrajectoryList(trajectoryList);

                resultList.add(deckVehicleDetail);
            }

            /** 分页缓存查询结果 */
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            /** 返回结果 */
            List<PassDeckVehicleDetail> pageList = redisClient.getListWithPage(searchKey, start, deckVehiclesQO.getPageSize(), PassDeckVehicleDetail.class);
            VehicleSearchBO<PassDeckVehicleDetail> vehicleSearchBO = new VehicleSearchBO<>(searchKey, resultList.size(), pageList);

            return vehicleSearchBO;
        } else {
            /** 从缓存中获取数据并更新过期时间,第二次开始不返回总数*/
            List<PassDeckVehicleDetail> pageList = redisClient.getListWithPage(deckVehiclesQO.getSearchKey(), start, deckVehiclesQO.getPageSize(), PassDeckVehicleDetail.class);
            redisClient.expire(deckVehiclesQO.getSearchKey(), Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            int count = redisClient.getTotalPageSize(deckVehiclesQO.getSearchKey());
            return new VehicleSearchBO<>(deckVehiclesQO.getSearchKey(), count, pageList);
        }
    }

    /**
     * @param deckVehiclesQO
     * @return
     * @description 排除套牌车辆
     * @date: 2019-3-27 12:06
     * @author: monkjavaer
     */
    @Override
    public OrbitResult exclude(DeckVehiclesQO deckVehiclesQO) {
//        /** 构建查询条件 */
//        if (StringUtils.isEmpty(deckVehiclesQO.getVid()) || StringUtils.isEmpty(deckVehiclesQO.getException())) {
//            return ResultUtil.error(ResponseStatusEnum.PARAMS_ERROR.getValue(), ResponseStatusEnum.PARAMS_ERROR.getDescription());
//        }
//        BoolQueryBuilder queryBuilder = createExcludeQueryBuilder(deckVehiclesQO);
//
//        /** 设置车辆相关异常过车信息状态为已排除 */
//        UpdateByQueryRequestBuilder updateBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(transportClient);
//
//        Script script = new Script(String.format("ctx._source.%s = '%d'", EsConstant.HANDLE_STATUS, HandleStatusEnum.EXCLUDE.getValue()));
//
//        updateBuilder.source(EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD).script(script)
//                .filter(queryBuilder).get();
//
//        /** 立刻刷新，保证更新异常过车信息后前台能立刻检索到*/
//        RefreshRequest refreshRequest = new RefreshRequest(EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD);
//        transportClient.admin().indices().refresh(refreshRequest).actionGet();

        Map<String, Object> map = new HashMap<>();
        map.put(EsConstant.HANDLE_STATUS, Integer.parseInt(deckVehiclesQO.getStatus()));
        transportClient.prepareUpdate(EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD, EsConstant.TYPE_INFO, deckVehiclesQO.getId())
                .setDoc(map).get();

        RefreshRequest refreshRequest = new RefreshRequest(EsConstant.ES_ORBIT_PASS_DECK_VEHICLE_RECORD);
        transportClient.admin().indices().refresh(refreshRequest).actionGet();

        return ResultUtil.success();
    }
}

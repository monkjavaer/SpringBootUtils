package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.TacticsTrajectoryInfoBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.qo.TacticsTrajectoryAnalyseQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.ITacticsTrajectoryAnalyseService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.OribtTypeConverter;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitservice.vo.DeviceDetailVO;
import com.car.orbit.orbitservice.vo.TrajectoryDeviceInfoVO;
import com.car.orbit.orbitutil.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 战法研判-轨迹分析service实现类
 **/
@Service
////@Transactional(rollbackFor = Exception.class)
public class TacticsTrajectoryAnalyseServiceImpl implements ITacticsTrajectoryAnalyseService {

    /** ES client */
    @Autowired
    private TransportClient transportClient;

    /** redis client */
    @Autowired
    private RedisClient redisClient;

    /** 查询索引 */
    private static final String ORBIT_PASS_VEHICLE_RECORD = "orbit_pass_vehicle_record";

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    /**
     * 根据查询条件，获得某辆车的运行轨迹
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 运行轨迹信息集合
     */
    @Override
    public VehicleSearchBO<TacticsTrajectoryInfoBO> findTrajectoryInfo(TacticsTrajectoryAnalyseQO trajectoryAnalyseQO) {

        String key = trajectoryAnalyseQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {

            // 进行查询
            List<TacticsTrajectoryInfoBO> trajectoryInfoList = doSearch(trajectoryAnalyseQO);

            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, trajectoryInfoList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            int start = (trajectoryAnalyseQO.getPageNo() - 1) * trajectoryAnalyseQO.getPageSize();
            List<TacticsTrajectoryInfoBO> pageList = redisClient.getListWithPage(searchKey, start, trajectoryAnalyseQO.getPageSize(), TacticsTrajectoryInfoBO.class);
            // 车辆信息清洗为最新一条的过车记录信息
            List<TacticsTrajectoryInfoBO> vehicleNewestMessage = (List<TacticsTrajectoryInfoBO>) OribtTypeConverter.completeList(transportClient, pageList);
            return new VehicleSearchBO<>(searchKey, trajectoryInfoList.size(), vehicleNewestMessage);
        } else {
            // 直接从Redis获取缓存
            int start = (trajectoryAnalyseQO.getPageNo() - 1) * trajectoryAnalyseQO.getPageSize();
            List<TacticsTrajectoryInfoBO> pageList = redisClient.getListWithPage(trajectoryAnalyseQO.getSearchKey(), start, trajectoryAnalyseQO.getPageSize(), TacticsTrajectoryInfoBO.class);
            // 车辆信息清洗为最新一条的过车记录信息
            List<TacticsTrajectoryInfoBO> vehicleNewestMessage = (List<TacticsTrajectoryInfoBO>) OribtTypeConverter.completeList(transportClient, pageList);
            Integer size = redisClient.getTotalPageSize(trajectoryAnalyseQO.getSearchKey());
            return new VehicleSearchBO<>(trajectoryAnalyseQO.getSearchKey(), size, vehicleNewestMessage);
        }
    }

    /**
     * 根据查询条件，获得某个车辆运行轨迹经过的设备的信息
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 设备信息
     */
    @Override
    public List<DeviceDetailVO> findTrajectoryDeviceInfo(TacticsTrajectoryAnalyseQO trajectoryAnalyseQO) {
        // 进行查询
        List<TacticsTrajectoryInfoBO> trajectoryInfoList = doSearch(trajectoryAnalyseQO);

        // 根据查询结果获得设备信息
        List<DeviceDetailVO> deviceDetailVOList = new LinkedList<>();
        Map<String, TrajectoryDeviceInfoVO> deviceDetailMap = redisClient.get(DevicePointRedis.DIC_DEVICE_KEY, TrajectoryDeviceInfoVO.class);
        for (TacticsTrajectoryInfoBO tacticsTrajectoryInfoBO : trajectoryInfoList) {
            TrajectoryDeviceInfoVO deviceDetail = deviceDetailMap.get(tacticsTrajectoryInfoBO.getDeviceId());
            deviceDetail.setAreaName(tacticsTrajectoryInfoBO.getAreaName());
            deviceDetail.setCityName(tacticsTrajectoryInfoBO.getCityName());
            deviceDetail.setRoadName(tacticsTrajectoryInfoBO.getRoadName());
            deviceDetail.setCaptureTime(tacticsTrajectoryInfoBO.getCaptureTime());
            deviceDetailVOList.add(deviceDetail);
        }
        return deviceDetailVOList;
    }

    /**
     * 进行ES查询，这个查询语句供2个方法使用
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return 查询结果
     */
    private List<TacticsTrajectoryInfoBO> doSearch(TacticsTrajectoryAnalyseQO trajectoryAnalyseQO) {
        String[] includes = new String[]{EsConstant.VID, EsConstant.CAPTURE_TIME, EsConstant.DEVICE_ID, EsConstant.DEVICE_NAME,
                EsConstant.CITY_NAME, EsConstant.AREA_NAME, EsConstant.ROAD_NAME, EsConstant.PHOTO_FASTDFS_URL, EsConstant.VEHICLE_TYPE,
                EsConstant.PLATE_NUMBER, EsConstant.PHOTO_ORI_FASTDFS_URL, };
        // 进行查询
        SearchResponse searchResponse = transportClient
                .prepareSearch(ORBIT_PASS_VEHICLE_RECORD)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(generateQueryBuilder(trajectoryAnalyseQO))
                .setFetchSource(includes, null)
                .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)    // 默认抓拍时间升序查询
                .execute()
                .actionGet();

        // 结果分析
        SearchHits searchHits = searchResponse.getHits();
        List<TacticsTrajectoryInfoBO> trajectoryInfoList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            TacticsTrajectoryInfoBO tacticsTrajectoryInfoBO = JsonUtils.toBean(searchHit.getSourceAsString(), TacticsTrajectoryInfoBO.class);
            tacticsTrajectoryInfoBO.setPhotoFastdfsUrl(tacticsTrajectoryInfoBO.getPhotoOriFastdfsUrl());
            trajectoryInfoList.add(tacticsTrajectoryInfoBO);
        }
        return trajectoryInfoList;
    }

    /**
     * 构建ES查询语句
     *
     * @param trajectoryAnalyseQO 查询条件
     * @return ES查询语句
     */
    private BoolQueryBuilder generateQueryBuilder(TacticsTrajectoryAnalyseQO trajectoryAnalyseQO) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 查询时间范围
        String str1 = DateUtils.format(DateUtils.getDate(trajectoryAnalyseQO.getStartTime()));
        String str2 = DateUtils.format(DateUtils.getDate(trajectoryAnalyseQO.getEndTime()));
        RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery(EsConstant.CAPTURE_TIME).from(str1).to(str2);
        queryBuilder.must(timeRangeQuery);

        // 车牌号
        TermQueryBuilder plateNumTermQuery = QueryBuilders.termQuery(EsConstant.PLATE_NUMBER, trajectoryAnalyseQO.getPlateNumber());
        queryBuilder.must(plateNumTermQuery);

        // 车牌颜色
        if (StringUtils.isNotBlank(trajectoryAnalyseQO.getPlateColor())) {
            TermQueryBuilder plateColorTermQuery = QueryBuilders.termQuery(EsConstant.PLATE_COLOR, trajectoryAnalyseQO.getPlateColor());
            queryBuilder.must(plateColorTermQuery);
        }

        // 设备编号
        if (!CollectionUtils.isEmpty(trajectoryAnalyseQO.getDeviceIdList())) {
            TermsQueryBuilder roadTermsQuery = QueryBuilders.termsQuery(EsConstant.DEVICE_ID, trajectoryAnalyseQO.getDeviceIdList());
            queryBuilder.must(roadTermsQuery);
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }
}
package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.NearbyPassVehicleRecordBO;
import com.car.orbit.orbitservice.bo.VehicleDetailBO;
import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.enums.VehicleSearchTypeEnum;
import com.car.orbit.orbitservice.qo.PassVehicleRecordQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.IPassVehicleRecordService;
import com.car.orbit.orbitservice.service.IUserService;
import com.car.orbit.orbitservice.util.PassVehicleIndexUtil;
import com.car.orbit.orbitservice.util.VehicleTrajectoryUtil;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;
import com.car.orbit.orbitservice.vo.VehicleTrajectoryVO;
import com.car.orbit.orbitutil.es.ESUtils;
import com.car.orbit.orbitutil.tools.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
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
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @Title: PassVehicleRecordServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 综合搜车服务实现类
 * @Author: monkjavaer
 * @Date: 2019/03/16 09:44
 * @Version: V1.0
 */
@Service
public class PassVehicleRecordServiceImpl implements IPassVehicleRecordService {

    @Autowired
    private TransportClient transportClient;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IDeviceService iDeviceService;

    public static final String ORBIT_PASS_WHITE_VEHICLE_RECORD = "orbit_pass_white_vehicle_record";

    /**
     * 综合搜车，未开启一车一档
     *
     * @param passVehicleRecordQO
     * @return
     */
    @Override
    public VehicleSearchBO<OrbitPassVehicleRecord> queryPassVehicleRecordList(PassVehicleRecordQO passVehicleRecordQO) {
        int start = (passVehicleRecordQO.getPageNo() - 1) * passVehicleRecordQO.getPageSize();
        String key = passVehicleRecordQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            BoolQueryBuilder queryBuilder = createQueryBuilder(passVehicleRecordQO);

            // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(passVehicleRecordQO.getStartTime(), passVehicleRecordQO.getEndTime());

            SearchHits hits = transportClient
                    .prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addSort(EsConstant.CAPTURE_TIME, SortOrder.DESC)
                    .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .execute()
                    .actionGet()
                    .getHits();

            // 结果解析
            List<OrbitPassVehicleRecord> resultList = new ArrayList<>();
            for (SearchHit searchHit : hits.getHits()) {
                String jsonStr = searchHit.getSourceAsString();
                resultList.add(JsonUtils.toBean(jsonStr, OrbitPassVehicleRecord.class));
            }

            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            List<OrbitPassVehicleRecord> pageList = redisClient.getListWithPage(searchKey, start, passVehicleRecordQO.getPageSize(), OrbitPassVehicleRecord.class);
            for (OrbitPassVehicleRecord record : pageList) {
                record.setBrandName(BrandRedis.getBrandNameByCode(record.getVehicleBrand()));
                record.setChildBrandName(BrandRedis.getChildBrandNameByCode(record.getVehicleBrandChild()));
            }
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<OrbitPassVehicleRecord> pageList = redisClient.getListWithPage(key, start, passVehicleRecordQO.getPageSize(), OrbitPassVehicleRecord.class);
            for (OrbitPassVehicleRecord record : pageList) {
                record.setBrandName(BrandRedis.getBrandNameByCode(record.getVehicleBrand()));
                record.setChildBrandName(BrandRedis.getChildBrandNameByCode(record.getVehicleBrandChild()));
            }
            Integer size = redisClient.getTotalPageSize(key);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(key, size, pageList);
        }
    }

    /**
     * 综合搜车，开启一车一档
     *
     * @param passVehicleRecordQO
     * @return
     */
    @Override
    public VehicleSearchBO<OneVehicleFileVO> queryPassVehicleRecordByCar(PassVehicleRecordQO passVehicleRecordQO) {
        int start = (passVehicleRecordQO.getPageNo() - 1) * passVehicleRecordQO.getPageSize();
        String key = passVehicleRecordQO.getSearchKey();
        if (StringUtils.isBlank(key) || !redisClient.checkKeyExist(key)) {
            // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(passVehicleRecordQO.getStartTime(), passVehicleRecordQO.getEndTime());

            BoolQueryBuilder queryBuilder = createQueryBuilder(passVehicleRecordQO);

            SearchResponse response = transportClient
                    .prepareSearch(indexArray)
                    .setSize(0)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(AggregationBuilders.terms("group_by_vid")
                            .field(EsConstant.VID)
                            .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE))))
                    .execute()
                    .actionGet();

            Terms terms = response.getAggregations().get("group_by_vid");

            List<OneVehicleFileVO> resultList = new ArrayList<>();

            for (int i = 0; i < terms.getBuckets().size(); i++) {
                OneVehicleFileVO item = new OneVehicleFileVO();
                // vid
                String vid = terms.getBuckets().get(i).getKey().toString();
                item.setVid(vid);
                // 过车记录数
                Long count = terms.getBuckets().get(i).getDocCount();
                item.setCount(count);
                resultList.add(item);
            }

            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            List<OneVehicleFileVO> pageList = redisClient.getListWithPage(searchKey, start, passVehicleRecordQO.getPageSize(), OneVehicleFileVO.class);
            queryVehicleInfoByVid(pageList);
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<OneVehicleFileVO> pageList = redisClient.getListWithPage(key, start, passVehicleRecordQO.getPageSize(), OneVehicleFileVO.class);
            queryVehicleInfoByVid(pageList);
            Integer size = redisClient.getTotalPageSize(key);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(key, size, pageList);
        }
    }

    /**
     * 综合搜车，根据设备ID聚合
     *
     * @param passVehicleRecordQO
     * @return
     */
    @Override
    public VehicleSearchBO<VehicleSearchVO> queryQueryPageByDevice(PassVehicleRecordQO passVehicleRecordQO) {
        int start = (passVehicleRecordQO.getPageNo() - 1) * passVehicleRecordQO.getPageSize();
        String key = passVehicleRecordQO.getSearchKey();
        if (StringUtils.isBlank(key) || !redisClient.checkKeyExist(key)) {
            // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(passVehicleRecordQO.getStartTime(), passVehicleRecordQO.getEndTime());

            // 查询条件
            BoolQueryBuilder queryBuilder = createQueryBuilder(passVehicleRecordQO);

            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("term_device")
                    .field(EsConstant.DEVICE_ID)
                    .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)));

            // 开启一车一档
//            if (passVehicleRecordQO.isGroupByVid()) {
//                aggregationBuilder.subAggregation(AggregationBuilders.terms("term_vid")
//                        .field(EsConstant.VID)
//                        .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_AGGREGATION_CACHE_PAGESIZE))));
//            }

            SearchResponse response = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(aggregationBuilder)
                    .setSize(0)
                    .get();

            // 获取聚合结果
            Terms aggregation = response.getAggregations().get("term_device");
            List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
            List<VehicleSearchVO> list = new ArrayList<>();
            for (Terms.Bucket bucket : buckets) {
                // 循环封装显示信息
                VehicleSearchVO vehicleSearchVO = new VehicleSearchVO();
                String deviceId = bucket.getKeyAsString();
                // 从Redis中查询设备信息
                OrbitResDevice device = DevicePointRedis.getDevicePointByCode(deviceId);

//                if (passVehicleRecordQO.isGroupByVid()) {
//                    StringTerms termVid = (StringTerms) bucket.getAggregations().asMap().get("term_vid");
//                    vehicleSearchVO.setCount((long) termVid.getBuckets().size());
//                } else {
//                    vehicleSearchVO.setCount(bucket.getDocCount());
//                }
                vehicleSearchVO.setCount(bucket.getDocCount());
                vehicleSearchVO.setPointId(deviceId);
                if (device != null) {
                    vehicleSearchVO.setPointName(device.getName());
                }
                list.add(vehicleSearchVO);
            }
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPage(searchKey, list);
            redisClient.expire(searchKey, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            List<VehicleSearchVO> pageList = redisClient.getListWithPage(searchKey, start, passVehicleRecordQO.getPageSize(), VehicleSearchVO.class);
            return new VehicleSearchBO<>(searchKey, list.size(), pageList);
        } else {
            List<VehicleSearchVO> pageList = redisClient.getListWithPage(key, start, passVehicleRecordQO.getPageSize(), VehicleSearchVO.class);
            Integer size = redisClient.getTotalPageSize(key);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(key, size, pageList);
        }
    }

    /**
     * 综合搜车，根据车辆子品牌聚合
     *
     * @param passVehicleRecordQO
     * @return
     */
    @Override
    public VehicleSearchBO<VehicleSearchVO> queryPageByBrandChild(PassVehicleRecordQO passVehicleRecordQO) {
        int start = (passVehicleRecordQO.getPageNo() - 1) * passVehicleRecordQO.getPageSize();
        String key = passVehicleRecordQO.getSearchKey();
        if (StringUtils.isBlank(key) || !redisClient.checkKeyExist(key)) {
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(passVehicleRecordQO.getStartTime(), passVehicleRecordQO.getEndTime());
            BoolQueryBuilder queryBuilder = createQueryBuilder(passVehicleRecordQO);

            TermsAggregationBuilder eggBrand = AggregationBuilders.terms("term_brand")
                    .field(EsConstant.VEHICLE_BRAND_CHILD)
                    .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)));
            // 判断是否开启一车一档
//            if (passVehicleRecordQO.isGroupByVid()) {
//                eggBrand.subAggregation(AggregationBuilders.terms("term_vid")
//                        .field(EsConstant.VID)
//                        .size(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_AGGREGATION_CACHE_PAGESIZE))));
//            }

            SearchResponse response = transportClient.prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addAggregation(eggBrand)
                    .setSize(0)
                    .get();

            //查询还是取hits中的真正条数
            long total = response.getHits().getTotalHits();

            Terms termBrand = response.getAggregations().get("term_brand");

            // 结果解析
            List<VehicleSearchVO> resultList = new ArrayList<>();
            int bound = termBrand.getBuckets().size();
            for (int i = 0; i < bound; i++) {
                VehicleSearchVO item = new VehicleSearchVO();
                Terms.Bucket bucket = termBrand.getBuckets().get(i);

                String brandChildId = bucket.getKey().toString();
                item.setBrand(brandChildId.substring(0, 4));
                item.setBrandChild(brandChildId);

                //todo 这里要优化一下性能，需要在循环外面去处理，外面一次性查询出Map化的数据，在这里直接取用即可，不能再循环中连续查询.在redis中增加Map话返回结果用流的形式过滤为Map
                item.setBrandInfo(BrandRedis.getFullBrandNameByCode(brandChildId));

//                if (passVehicleRecordQO.isGroupByVid()) {
//                    StringTerms termVid = (StringTerms) bucket.getAggregations().asMap().get("term_vid");
//                    item.setCount((long) termVid.getBuckets().size());
//                } else {
//                    item.setCount(bucket.getDocCount());
//                }
                item.setCount(bucket.getDocCount());

                resultList.add(item);
            }

            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            List<VehicleSearchVO> pageList = redisClient.getListWithPage(searchKey, start, passVehicleRecordQO.getPageSize(), VehicleSearchVO.class);
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<VehicleSearchVO> pageList = redisClient.getListWithPage(key, start, passVehicleRecordQO.getPageSize(), VehicleSearchVO.class);
            Integer size = redisClient.getTotalPageSize(key);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return new VehicleSearchBO<>(key, size, pageList);
        }
    }

    /**
     * 根据过车记录ID查询记录详情
     *
     * @param id
     * @param captureTime 辅助定位数据表
     * @return
     */
    @Override
    public OrbitPassVehicleRecord queryPassVehicleRecordById(String id, Date captureTime) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery(EsConstant.ID, id));

        int year = DateUtils.getYearOfDate(captureTime);
        int week = DateUtils.getWeekInYear(captureTime);
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
            OrbitPassVehicleRecord record = JsonUtils.toBean(hits.getHits()[0].getSourceAsString(), OrbitPassVehicleRecord.class);
            // 从Redis获取车辆品牌相关信息
            String brandName = BrandRedis.getBrandNameByCode(record.getVehicleBrand());
            record.setBrandName(brandName);
            String brandChildName = BrandRedis.getChildBrandNameByCode(record.getVehicleBrandChild());
            record.setChildBrandName(brandChildName);
            record.setFullBrand(BrandRedis.getFullBrandNameByCode(record.getVehicleBrandChild()));
            // 从Redis获取设备经纬度
            OrbitResDevice device = DevicePointRedis.getDevicePointByCode(record.getDeviceId());
            if (device != null) {
                record.setLatitude(device.getLatitude());
                record.setLongitude(device.getLongitude());
            }
            record.setPhotoFastdfsUrl(record.getPhotoOriFastdfsUrl());

            return record;
        }

        return null;
    }

    /**
     * （分页）开启一车一档后，点击分页车辆详情
     *
     * @param passVehicleRecordQO
     * @return
     */
    @Override
    public VehicleDetailBO queryDetailPage(PassVehicleRecordQO passVehicleRecordQO) {
        VehicleDetailBO vehicleDetailBO = new VehicleDetailBO();
        int start = (passVehicleRecordQO.getPageNo() - 1) * passVehicleRecordQO.getPageSize();
        String key = passVehicleRecordQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            //构造查询条件
            BoolQueryBuilder queryBuilder = createQueryBuilder(passVehicleRecordQO);

            // 计算索引，一般有多个 orbit_pass_vehicle_record_[year]_[week]
            String[] indexArray = PassVehicleIndexUtil.calculateIndexCollection(passVehicleRecordQO.getStartTime(), passVehicleRecordQO.getEndTime());

            // 输出DSL查询语言
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(queryBuilder);
            System.out.println(sourceBuilder.toString());

            //开始查询
            SearchHits hits = transportClient
                    .prepareSearch(indexArray)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addSort(EsConstant.CAPTURE_TIME, SortOrder.DESC)
                    .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .execute()
                    .actionGet()
                    .getHits();

            // 结果解析
            OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
            Map<String, String> brands = BrandRedis.getAllBrand();
            Map<String, String> childBrands = BrandRedis.getAllChildBrand();
            List<OrbitPassVehicleRecord> resultList = new ArrayList<>();
            for (SearchHit searchHit : hits.getHits()) {
                String jsonStr = searchHit.getSourceAsString();
                //翻译品牌名字
                OrbitPassVehicleRecord record = JsonUtils.toBean(jsonStr, OrbitPassVehicleRecord.class);
                record.setBrandName(BrandRedis.getBrandNameByCodeAndUserInfoAndBRAND(record.getVehicleBrand(), user, brands));
                record.setChildBrandName(BrandRedis.getChildBrandNameByCodeAndUserInfoAndChildBrands(record.getVehicleBrandChild(), user, childBrands));
                record.setFullBrand(BrandRedis.concatFullBrandName(record.getBrandName(), record.getChildBrandName()));
                // 从Redis获取设备经纬度
                OrbitResDevice device = DevicePointRedis.getDevicePointByCode(record.getDeviceId());
                if (device != null) {
                    record.setLatitude(device.getLatitude());
                    record.setLongitude(device.getLongitude());
                }
                record.setPhotoOriFastdfsUrl(record.getPhotoFastdfsUrl());

                resultList.add(record);
            }
            //第一次查询结果放入redis,分页从redis取数据
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPage(searchKey, resultList);
            redisClient.expire(searchKey, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            //分页数据
            List<OrbitPassVehicleRecord> pageList = redisClient.getListWithPage(searchKey, start, passVehicleRecordQO.getPageSize(), OrbitPassVehicleRecord.class);

            //构造返回数据
            vehicleDetailBO.setVehicleRecordPage(new VehicleSearchBO<>(searchKey, resultList.size(), pageList));

            //轨迹集合
            List<VehicleTrajectoryVO> trajectoryList = VehicleTrajectoryUtil.getTrajectory(resultList);
            vehicleDetailBO.setTrajectoryList(trajectoryList);
            return vehicleDetailBO;
        } else {
            //第二次开始不返回轨迹
            List<OrbitPassVehicleRecord> pageList = redisClient.getListWithPage(key, start, passVehicleRecordQO.getPageSize(), OrbitPassVehicleRecord.class);
            Integer size = redisClient.getTotalPageSize(key);
            vehicleDetailBO.setVehicleRecordPage(new VehicleSearchBO<>(key, size, pageList));
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            return vehicleDetailBO;
        }
    }

    /**
     * 查询白名单车辆过车记录
     *
     * @param passVehicleRecordQO
     * @return
     */
    @Override
    public VehicleSearchBO<OrbitPassVehicleRecord> querySpecialPassVehicleRecordList(PassVehicleRecordQO passVehicleRecordQO) {
        int start = (passVehicleRecordQO.getPageNo() - 1) * passVehicleRecordQO.getPageSize();
        String key = passVehicleRecordQO.getSearchKey();
        if (StringUtils.isEmpty(key) || !redisClient.checkKeyExist(key)) {
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, passVehicleRecordQO.getVid()));

            SearchHits hits = transportClient
                    .prepareSearch(ORBIT_PASS_WHITE_VEHICLE_RECORD)
                    .setTypes(EsConstant.TYPE_INFO)
                    .setQuery(queryBuilder)
                    .addSort(EsConstant.CAPTURE_TIME, SortOrder.DESC)
                    .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                    .execute()
                    .actionGet()
                    .getHits();

            // 结果解析
            List<OrbitPassVehicleRecord> resultList = new ArrayList<>();
            for (SearchHit searchHit : hits.getHits()) {
                String jsonStr = searchHit.getSourceAsString();
                resultList.add(JsonUtils.toBean(jsonStr, OrbitPassVehicleRecord.class));
            }

            // Redis缓存记录
            String searchKey = UUIDUtils.generate();
            redisClient.saveListWithPageEx(searchKey, resultList, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));
            List<OrbitPassVehicleRecord> pageList = redisClient.getListWithPage(searchKey, start, passVehicleRecordQO.getPageSize(), OrbitPassVehicleRecord.class);
            for (OrbitPassVehicleRecord record : pageList) {
                record.setBrandName(BrandRedis.getBrandNameByCode(record.getVehicleBrand()));
                record.setChildBrandName(BrandRedis.getChildBrandNameByCode(record.getVehicleBrandChild()));
            }
            return new VehicleSearchBO<>(searchKey, resultList.size(), pageList);
        } else {
            List<OrbitPassVehicleRecord> pageList = redisClient.getListWithPage(key, start, passVehicleRecordQO.getPageSize(), OrbitPassVehicleRecord.class);
            for (OrbitPassVehicleRecord record : pageList) {
                record.setBrandName(BrandRedis.getBrandNameByCode(record.getVehicleBrand()));
                record.setChildBrandName(BrandRedis.getChildBrandNameByCode(record.getVehicleBrandChild()));
            }
            Integer size = redisClient.getTotalPageSize(key);
            redisClient.expire(key, Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_EXPIRE)));

            return new VehicleSearchBO<>(key, size, pageList);
        }
    }

    /**
     * 查询前、后一条过车记录
     *
     * @param id
     * @param captureTime
     * @return
     */
    @Override
    public NearbyPassVehicleRecordBO queryNearbyRecord(String id, String captureTime) {
        NearbyPassVehicleRecordBO nearbyPassVehicleRecordBO = new NearbyPassVehicleRecordBO();

        /** 中间一条过车记录 **/
        OrbitPassVehicleRecord record = queryPassVehicleRecordById(id, DateUtils.getDate(captureTime));
        OrbitResDevice device = DevicePointRedis.getDevicePointByCode(record.getDeviceId());
        if (device != null) {
            record.setLatitude(device.getLatitude());
            record.setLongitude(device.getLongitude());
            record.setDevicePosition(DevicePointRedis.getFullPositionName(device.getId()));
        }
        nearbyPassVehicleRecordBO.setCurrentRecord(record);

        String vid = record.getVid();

        /**
         * 检索前一条过车记录
         */
        String[] earlierIndex = PassVehicleIndexUtil.calculateEarlierIndexCollection(captureTime);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.mustNot(QueryBuilders.termsQuery(EsConstant.ID, id));
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, vid));
        queryBuilder.must(QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .lte(captureTime));
        SearchHit[] hits = transportClient
                .prepareSearch(earlierIndex)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.DESC)
                .setSize(1)
                .execute()
                .actionGet()
                .getHits().getHits();
        if (hits.length > 0) {
            OrbitPassVehicleRecord earlierRecord = JsonUtils.toBean(hits[0].getSourceAsString(), OrbitPassVehicleRecord.class);
            device = DevicePointRedis.getDevicePointByCode(earlierRecord.getDeviceId());
            if (device != null) {
                earlierRecord.setLatitude(device.getLatitude());
                earlierRecord.setLongitude(device.getLongitude());
                earlierRecord.setDevicePosition(DevicePointRedis.getFullPositionName(device.getId()));
            }
            nearbyPassVehicleRecordBO.setEarlierRecord(earlierRecord);
        }

        /**
         * 检索后一条过车记录
         */
        String[] laterIndex = PassVehicleIndexUtil.calculateLaterIndexCollection(captureTime);
        queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.mustNot(QueryBuilders.termsQuery(EsConstant.ID, id));
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, vid));
        queryBuilder.must(QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .gte(captureTime));
        hits = transportClient
                .prepareSearch(laterIndex)
                .setTypes(EsConstant.TYPE_INFO)
                .setQuery(queryBuilder)
                .addSort(EsConstant.CAPTURE_TIME, SortOrder.ASC)
                .setSize(1)
                .execute()
                .actionGet()
                .getHits().getHits();
        if (hits.length > 0) {
            OrbitPassVehicleRecord laterRecord = JsonUtils.toBean(hits[0].getSourceAsString(), OrbitPassVehicleRecord.class);
            device = DevicePointRedis.getDevicePointByCode(laterRecord.getDeviceId());
            if (device != null) {
                laterRecord.setLatitude(device.getLatitude());
                laterRecord.setLongitude(device.getLongitude());
                laterRecord.setDevicePosition(DevicePointRedis.getFullPositionName(device.getId()));
            }
            nearbyPassVehicleRecordBO.setLaterRecord(laterRecord);
        }

        return nearbyPassVehicleRecordBO;
    }

    /**
     * 构建ES查询条件
     *
     * @param passVehicleRecordQO
     * @return
     */
    private BoolQueryBuilder createQueryBuilder(PassVehicleRecordQO passVehicleRecordQO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery(EsConstant.CAPTURE_TIME)
                .from(passVehicleRecordQO.getStartTime())
                .to(passVehicleRecordQO.getEndTime());
        queryBuilder.must(rangequerybuilder);

//        if (passVehicleRecordQO.isMissingPlateNumber()) {
//            queryBuilder.mustNot(QueryBuilders.existsQuery(EsConstant.PLATE_NUMBER));
//        } else if (!StringUtils.isEmpty(passVehicleRecordQO.getPlateNumber())) {
//            queryBuilder.must(QueryBuilders.wildcardQuery(EsConstant.PLATE_NUMBER, "*" + passVehicleRecordQO.getPlateNumber() + "*"));
//        }

        if (passVehicleRecordQO.getFilterType() != null &&
                passVehicleRecordQO.getFilterType() == VehicleSearchTypeEnum.MISSING_PLATE_NUMBER.getValue()) {
            queryBuilder.mustNot(QueryBuilders.existsQuery(EsConstant.PLATE_NUMBER));
        } else if (passVehicleRecordQO.getFilterType() != null &&
                passVehicleRecordQO.getFilterType() == VehicleSearchTypeEnum.HAVE_PLATE_NUMBER.getValue()) {
            queryBuilder.must(QueryBuilders.existsQuery(EsConstant.PLATE_NUMBER));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getPlateNumber())) {
            queryBuilder.must(QueryBuilders.wildcardQuery(EsConstant.PLATE_NUMBER, "*" + passVehicleRecordQO.getPlateNumber() + "*"));
        }

        if (!StringUtils.isEmpty(passVehicleRecordQO.getCityId())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.CITY_ID, passVehicleRecordQO.getCityId()));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getAreaId())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.AREA_ID, passVehicleRecordQO.getAreaId()));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getRoadCrossPointId())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.ROAD_CROSS_POINT_ID, passVehicleRecordQO.getRoadCrossPointId()));
        }
        if (passVehicleRecordQO.getRoadwayNo() != null) {
            queryBuilder.filter(QueryBuilders.termQuery(EsConstant.ROADWAY_NO, passVehicleRecordQO.getRoadwayNo()));
        }
        if (passVehicleRecordQO.getVehicleTypeList() != null && passVehicleRecordQO.getVehicleTypeList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_TYPE, passVehicleRecordQO.getVehicleTypeList()));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getVehicleColor())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_COLOR, passVehicleRecordQO.getVehicleColor()));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getPlateColor())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.PLATE_COLOR, passVehicleRecordQO.getPlateColor()));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND, passVehicleRecordQO.getBrand()));
        }
        if (!StringUtils.isEmpty(passVehicleRecordQO.getChildBrand())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VEHICLE_BRAND_CHILD, passVehicleRecordQO.getChildBrand()));
        }

        if (passVehicleRecordQO.getDeviceIdList() != null && passVehicleRecordQO.getDeviceIdList().size() > 0) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, passVehicleRecordQO.getDeviceIdList()));
        } else {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.DEVICE_ID, iDeviceService.queryAllDeviceIdList()));
        }

        if (!StringUtils.isEmpty(passVehicleRecordQO.getVid())) {
            queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, passVehicleRecordQO.getVid()));
        }

        //0：全天，不增加该条件； 1：代表白天； 2：代表黑夜
        if (passVehicleRecordQO.getDayTimeFlag() == 1) {
            queryBuilder.must(QueryBuilders.rangeQuery(EsConstant.CAPTURE_HOUR).from(passVehicleRecordQO.getDayTimeStart()).to(passVehicleRecordQO.getDayTimeEnd()).includeUpper(false));
        } else if (passVehicleRecordQO.getDayTimeFlag() == 2) {
            queryBuilder.mustNot(QueryBuilders.rangeQuery(EsConstant.CAPTURE_HOUR).from(passVehicleRecordQO.getDayTimeStart()).to(passVehicleRecordQO.getDayTimeEnd()).includeUpper(false));
        }

        //是否套牌车，true为套牌车，反之为false
        if (passVehicleRecordQO.isStatusException()) {
            queryBuilder.must(QueryBuilders.existsQuery(EsConstant.EXCEPTION));
        }

        // 车辆特征
        List<String> featureIdList = new ArrayList<>();
        List<String> excludeFeatureIdList = new ArrayList<>();
        if (passVehicleRecordQO.getFeature() != null && passVehicleRecordQO.getFeature().size() > 0) {
            for (String item : passVehicleRecordQO.getFeature()) {
                String[] array = item.split(",");
                if ("0".equals(array[1])) { // 无
                    excludeFeatureIdList.add(array[0]);
                } else if ("1".equals(array[1])) { // 有
                    featureIdList.add(array[0]);
                }
            }

            featureIdList.stream().forEach(s -> queryBuilder.must(QueryBuilders.nestedQuery("VEHICLE_PROPERTIES",
                    QueryBuilders.termsQuery("VEHICLE_PROPERTIES.feature_id", s), ScoreMode.None)));
            excludeFeatureIdList.stream().forEach(s -> queryBuilder.mustNot(QueryBuilders.nestedQuery("VEHICLE_PROPERTIES",
                    QueryBuilders.termsQuery("VEHICLE_PROPERTIES.feature_id", s), ScoreMode.None)));
        }

        //增加按照用户权限进行过滤
        queryBuilder.must(iUserService.getUserAuthorityQeuryBuilder());

        return queryBuilder;
    }

    /**
     * 根据Vid获取车牌号、图片等信息，从ES数据库一次性读取
     *
     * @param vehicleFileList
     */
    private void queryVehicleInfoByVid(List<OneVehicleFileVO> vehicleFileList) {
        List<String> vehicleIds = new ArrayList<>();
        vehicleFileList.stream().forEach(vehicleFileVO -> vehicleIds.add(vehicleFileVO.getVid()));

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termsQuery(EsConstant.VID, vehicleIds));

        SearchHit[] searchHits = transportClient
                .prepareSearch(EsConstant.ORBIT_VEHICLE_FILE)
                .setTypes(EsConstant.TYPE_FILE)
                .setQuery(queryBuilder)
                .setSize(Integer.parseInt(PropertyReaderUtils.getProValue(EsConstant.ES_CACHE_PAGESIZE)))
                .execute()
                .actionGet()
                .getHits()
                .getHits();

        Map<String, SearchHit> map = new HashMap<>();
        for (SearchHit hit : searchHits) {
            String vid = ESUtils.getField(hit, EsConstant.VID, String.class);
            map.put(vid, hit);
        }

        vehicleFileList.stream().forEach(vehicleFile -> {
            SearchHit hit = map.get(vehicleFile.getVid());
            if (hit != null) {
                // 车牌
                String plateNumber = ESUtils.getField(hit, EsConstant.PLATE_NUMBER, String.class);
                vehicleFile.setPlateNumber(plateNumber);
                // 抓拍图片
                String photoUrl = ESUtils.getField(hit, EsConstant.PHOTO_FASTDFS_URL, String.class);
                vehicleFile.setPhotoUrl(photoUrl);
            }
        });
    }
}

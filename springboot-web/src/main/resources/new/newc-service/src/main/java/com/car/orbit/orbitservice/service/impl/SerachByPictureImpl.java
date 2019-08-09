package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.bo.VehicleSearchBO;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrand;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrandChild;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.StructureException;
import com.car.orbit.orbitservice.qo.RequestPrameterQo;
import com.car.orbit.orbitservice.qo.SearchByPictureQO;
import com.car.orbit.orbitservice.service.IDeviceService;
import com.car.orbit.orbitservice.service.ISearchByPictureService;
import com.car.orbit.orbitservice.service.IVehicleTraitService;
import com.car.orbit.orbitservice.util.StructuralServerUtil;
import com.car.orbit.orbitservice.util.redis.BrandRedis;
import com.car.orbit.orbitservice.util.redis.SearchByPictureRedis;
import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehiclePositionVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Title: SerachByPictureImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description:
 * @Author: @author zks
 * @Date: 2019/03/18 16:02
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class SerachByPictureImpl implements ISearchByPictureService {

    @Autowired
    private IVehicleTraitService vehicleTraitService;

    @Autowired
    private SearchByPictureRedis searchByPictureRedis;

    @Autowired
    private IDeviceService deviceService;

    /**
     * 以图搜图===获取车辆位置信息
     *
     * @param pathUrl
     * @return List<VehiclePositionVO>
     */
    @Override
    public List<VehiclePositionVO> obtainVehiclePosition(String pathUrl) throws IllegalParamException, IOException, StructureException {
        if (StringUtils.isBlank(pathUrl)) {
            throw new IllegalParamException("picture url is empty");
        }
        List<VehiclePositionVO> list = StructuralServerUtil.getVehiclePosition(pathUrl);
        if (CollectionUtils.isEmpty(list)) {
            throw new StructureException("结构化服务器返回结果为空");
        }
        list.stream().filter(Objects::nonNull).map(VehiclePositionVO::getVehicleInfo).filter(Objects::nonNull).forEach((v) -> {
            OrbitVehicleBrand objBrand = BrandRedis.getBrandByName(v.getBrand());
            if (!Objects.isNull(objBrand)) {
                v.setBrandCode(objBrand.getCode());
                v.setBrand(BrandRedis.getBrandNameByCode(objBrand.getCode()));
            } else {
                v.setBrandCode(BrandRedis.UNKNOW_BRAND);
                v.setBrand(BrandRedis.UNKNOW_BRAND_NAME);
            }

            OrbitVehicleBrandChild objSubBrand = BrandRedis.getChildBrandByName(v.getBrand() + "-" + v.getSubBrand());
            if (StringUtils.isBlank(v.getSubBrand())) {
                //如果是null或空的子编码，需要去匹配父编码，看究竟是哪一个父的未知
                String parentCode = v.getBrandCode();
                v.setSubBrandCode(parentCode + BrandRedis.UNKNOW_BRAND);
                v.setSubBrand(BrandRedis.UNKNOW_BRAND_NAME);
            } else if(!Objects.isNull(objSubBrand)){
                v.setSubBrandCode(objSubBrand.getCode());
                v.setSubBrand(BrandRedis.getChildBrandNameByCode(objSubBrand.getCode()));
            } else{
                //如果是null或空的子编码，需要去匹配父编码，看究竟是哪一个父的未知
                String parentCode = v.getBrandCode();
                v.setSubBrandCode(parentCode + BrandRedis.UNKNOW_BRAND);
                v.setSubBrand(BrandRedis.UNKNOW_BRAND_NAME);
            }
        });
        // if (list != null && list.size() > 0) {
        //     for (int i = 0; i < list.size(); i++) {
        //         VehiclePositionVO vehiclePositionVO = list.get(i);
        //         StructuralVehicleInfoVO vehicleInfo = vehiclePositionVO.getVehicleInfo();
        //         //处理品牌和子品牌名称转编码的逻辑
        //         if (list.get(i) != null && vehicleInfo != null) {
        //             OrbitVehicleBrand obj = BrandRedis.getBrandByName(vehicleInfo.getBrand());
        //             vehicleInfo.setBrandCode(obj.getCode());
        //             vehicleInfo.setBrand(BrandRedis.getBrandNameByCode(obj.getCode()));
        //         }
        //         if (list.get(i) != null && vehicleInfo != null) {
        //             OrbitVehicleBrandChild obj = BrandRedis.getChildBrandByName(vehicleInfo.getSubBrand());
        //             if (vehicleInfo.getSubBrand() == null) {
        //                 //如果是null或空的子编码，需要去匹配父编码，看究竟是哪一个父的未知
        //                 String parentCode = vehicleInfo.getBrandCode();
        //                 vehicleInfo.setSubBrandCode(parentCode + BrandRedis.UNKNOW_BRAND);
        //                 vehicleInfo.setSubBrand(BrandRedis.UNKNOW_BRAND_NAME);
        //             } else {
        //                 vehicleInfo.setSubBrandCode(obj.getCode());
        //                 vehicleInfo.setSubBrand(BrandRedis.getChildBrandNameByCode(obj.getCode()));
        //             }
        //
        //         }
        //         vehiclePositionVO.setVehicleInfo(vehicleInfo);
        //     }
        // }
        return list;
    }

    /**
     * 以图搜图===按照条件查询以图搜图的记录信息【最底层的过车信息】
     * 根据参数判断是否缓存命中；【redis】
     *
     * @param condition
     * @return
     */
    @Override
    public VehicleSearchBO<VehicleTraitVo> imageSearch(SearchByPictureQO condition) {
        //todo step1：根据参数判断是否缓存命中；【redis】
        int size = 0;
        if (StringUtil.isNullOrEmpty(condition.getSearchKey()) || !searchByPictureRedis.checkKeyExist(condition.getSearchKey())) {

            //建立缓存记录,如果searchKey是空的，需要设置condition中的searchKey
            List<VehicleTraitVo> list = createImageSearchCache(condition);

            if (list == null || list.size() == 0) {
                return new VehicleSearchBO<>("", 0, null);
            }
        }
        String searchKey = condition.getSearchKey();
        //延长超时时间
        searchByPictureRedis.expireAll(searchKey);

        List<VehicleTraitVo> list = new ArrayList<>();
        if (condition.getGroupType() == SearchByPictureQO.all) {
            if (condition.isOneFile()) {
                //获取一车一档
                if (StringUtils.isNotEmpty(condition.getOpenVid())) {
                    list = searchByPictureRedis.getAllFileRecordWithPage(searchKey, condition.getOpenVid(), condition.getPageNo() - 1, condition.getPageSize());
                    size = searchByPictureRedis.getAllFileRecordSize(searchKey, condition.getOpenVid());
                }
            } else {
                list = searchByPictureRedis.getAllRecordWithPage(searchKey, condition.getPageNo() - 1, condition.getPageSize());
                size = searchByPictureRedis.getAllRecordSize(searchKey);
            }
        } else if (condition.getGroupType() == SearchByPictureQO.brand) {
            if (condition.isOneFile()) {
                //获取一车一档
                if (StringUtils.isNotEmpty(condition.getOpenBrandChildCode()) && StringUtils.isNotEmpty(condition.getOpenVid())) {
                    list = searchByPictureRedis.getBrandFileRecordWithPage(searchKey, condition.getOpenBrandChildCode(), condition.getOpenVid(), condition.getPageNo() - 1, condition.getPageSize());
                    size = searchByPictureRedis.getBrandFileRecordSize(searchKey, condition.getOpenBrandChildCode(), condition.getOpenVid());
                }
            } else {
                list = searchByPictureRedis.getBrandRecordWithPage(searchKey, condition.getOpenBrandChildCode(), condition.getPageNo() - 1, condition.getPageSize());
                size = searchByPictureRedis.getBrandRecordSize(searchKey, condition.getOpenBrandChildCode());
            }
        } else if (condition.getGroupType() == SearchByPictureQO.point) {
            if (condition.isOneFile()) {
                //获取一车一档
                if (StringUtils.isNotEmpty(condition.getOpenPointId()) && StringUtils.isNotEmpty(condition.getOpenVid())) {
                    list = searchByPictureRedis.getPointFileRecordWithPage(searchKey, condition.getOpenPointId(), condition.getOpenVid(), condition.getPageNo() - 1, condition.getPageSize());
                    size = searchByPictureRedis.getPointFileRecordSize(searchKey, condition.getOpenPointId(), condition.getOpenVid());
                }
            } else {
                list = searchByPictureRedis.getPointRecordWithPage(searchKey, condition.getOpenPointId(), condition.getPageNo() - 1, condition.getPageSize());
                size = searchByPictureRedis.getPointRecordSize(searchKey, condition.getOpenPointId());
            }
        }
        return new VehicleSearchBO<>(searchKey, size, list);
    }

    /**
     * 建立以图搜车记录缓存
     * 根据参数调用外部接口，获得车辆位置信息，生成以图搜车条件；【王浩】
     * 调用外部接口，获得按车辆聚合的过车ID和相似度；【老邹】
     * 根据过车ID+匹配次数，在数据库中查询过车记录；【ES】
     *
     * @param condition
     */
    private List<VehicleTraitVo> createImageSearchCache(SearchByPictureQO condition) {
        List<VehicleTraitVo> records = null;
        //todo step2：根据参数调用外部接口，获得车辆位置信息，生成以图搜车条件；
//        List<Double> featureList = StructuralServerUtil.getVehicleFeature(condition.getPicUrl());
        float[] featureList = condition.getFeatureList();
        if (featureList != null && featureList.length == 512) {
            //todo step3：调用外部接口，获得按车辆聚合的过车ID和相似度；
            RequestPrameterQo param = new RequestPrameterQo();
            param.setBegintime(condition.getStartTime());
            param.setEndtime(condition.getEndTime());
            if (condition.getDeviceId() == null || condition.getDeviceId().size() == 0) {
                param.setDeviceId(deviceService.queryAllDeviceIdList());
            } else {
                param.setDeviceId(condition.getDeviceId());
            }
            param.setImg(featureList);
            if (StringUtils.isNotEmpty(condition.getBrandCode()) || StringUtils.isNotEmpty(condition.getChildBrandCode())) {
                param.setBrand(condition.getBrandCode());
                param.setChildBrand(condition.getChildBrandCode());
            }
            //获取以图搜车的前一百条信息
            records = vehicleTraitService.getTraitWithNameByCondition(param);
            if (records != null && records.size() > 0) {
                String searchKey = UUIDUtils.generate();
                condition.setSearchKey(searchKey);
                searchByPictureRedis.saveAndGroupSearchResult(searchKey, records);
            }
        }
        return records;
    }

    /**
     * 以图搜图===按照条件返回各种情况下的一车一档数据信息
     *
     * @param condition
     * @return
     */
    @Override
    public VehicleSearchBO<OneVehicleFileVO> imageSearchByOneFile(SearchByPictureQO condition) {
        int size = 0;
        if (StringUtil.isNullOrEmpty(condition.getSearchKey()) || !searchByPictureRedis.checkKeyExist(condition.getSearchKey())) {
            //建立缓存记录,如果searchKey是空的，需要设置condition中的searchKey
            List<VehicleTraitVo> list = createImageSearchCache(condition);
            if (list == null || list.size() == 0) {
                return new VehicleSearchBO<>("", 0, null);
            }
        }
        String searchKey = condition.getSearchKey();
        List<OneVehicleFileVO> list = new ArrayList<>();
        if (condition.getGroupType() == SearchByPictureQO.all) {
            if (condition.isOneFile()) {
                list = searchByPictureRedis.getAllFileWithPage(searchKey, condition.getPageNo() - 1, condition.getPageSize());
                size = searchByPictureRedis.getAllFileSize(searchKey);
            }
        } else if (condition.getGroupType() == SearchByPictureQO.brand) {
            if (condition.isOneFile() && StringUtils.isNotEmpty(condition.getOpenBrandChildCode())) {
                list = searchByPictureRedis.getBrandFileWithPage(searchKey, condition.getOpenBrandChildCode(), condition.getPageNo() - 1, condition.getPageSize());
                size = searchByPictureRedis.getBrandFileSize(searchKey, condition.getOpenBrandChildCode());
            }
        } else if (condition.getGroupType() == SearchByPictureQO.point) {
            if (condition.isOneFile() && StringUtils.isNotEmpty(condition.getOpenPointId())) {
                list = searchByPictureRedis.getPointFileWithPage(searchKey, condition.getOpenPointId(), condition.getPageNo() - 1, condition.getPageSize());
                size = searchByPictureRedis.getPointFileSize(searchKey, condition.getOpenPointId());
            }
        }
        return new VehicleSearchBO<>(searchKey, size, list);
    }

    /**
     * 以图搜图===按照条件返回各种情况下的聚合数据信息
     *
     * @param condition
     * @return
     */
    @Override
    public VehicleSearchBO<VehicleSearchVO> imageSearchByGroup(SearchByPictureQO condition) {
        int size = 0;
        if (StringUtil.isNullOrEmpty(condition.getSearchKey()) || !searchByPictureRedis.checkKeyExist(condition.getSearchKey())) {
            //建立缓存记录,如果searchKey是空的，需要设置condition中的searchKey
            List<VehicleTraitVo> list = createImageSearchCache(condition);
            if (list == null || list.size() == 0) {
                return new VehicleSearchBO<>("", 0, null);
            }
        }
        String searchKey = condition.getSearchKey();
        List<VehicleSearchVO> list = new ArrayList<>();
        if (condition.getGroupType() == SearchByPictureQO.brand) {
            list = searchByPictureRedis.getBrandWithPage(searchKey, condition.getPageNo() - 1, condition.getPageSize());
            size = searchByPictureRedis.getBrandSize(searchKey);
        } else if (condition.getGroupType() == SearchByPictureQO.point) {
            list = searchByPictureRedis.getPointWithPage(searchKey, condition.getPageNo() - 1, condition.getPageSize());
            size = searchByPictureRedis.getPointSize(searchKey);
        }
        return new VehicleSearchBO<>(searchKey, size, list);
    }
}

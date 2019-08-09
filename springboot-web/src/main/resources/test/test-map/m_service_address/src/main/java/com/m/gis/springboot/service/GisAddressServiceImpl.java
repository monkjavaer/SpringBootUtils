package com.m.gis.springboot.service;

import com.github.pagehelper.PageHelper;
import com.m.gis.springboot.common.GisAddressConstants;
import com.m.gis.springboot.common.GisPage;
import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import com.m.gis.springboot.district.common.GisDistrict;
import com.m.gis.springboot.district.common.GisDistrictType;
import com.m.gis.springboot.enums.GisAddressNameLonLatStatusEnums;
import com.m.gis.springboot.enums.GisAddressTypeEnums;
import com.m.gis.springboot.exception.GisAddressServiceException;
import com.m.gis.springboot.exception.GisDistrictServiceThrowableException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.geo.base.utils.GisGeodesyUtil;
import com.m.gis.springboot.mapper.GisAddressTMapper;
import com.m.gis.springboot.po.GisAddressT;
import com.m.gis.springboot.qo.*;
import com.m.gis.springboot.utils.PostgisUtil;
import com.m.gis.springboot.vo.GisAddressNameInfoVO;
import com.m.gis.springboot.vo.GisAddressNameLonLatVO;
import com.m.gis.springboot.vo.GisAddressNameVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: GisAddressService
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/19
 * @Version: V1.0
 */
@Service
public class GisAddressServiceImpl implements GisAddressService{

    @Autowired
    private GisAddressTMapper gisAddressTMapper;

    @Autowired
    private GisDistrictService gisDistrictService;

    @Autowired
    private GisAbstractDistrictTypeUtil gisDistrictTypeUtil;


    private GisAddressT createGisAddressNameT(String name, GisCoordinate gisCoordinate, GisAddressTypeEnums type, String recorder){
        GisAddressT addressPo =new GisAddressT();
        addressPo.setName(name);
        addressPo.setDistrictCode(gisDistrictService.getDistrictCode(gisCoordinate));
        addressPo.setType(type.getValue());
        Date time = new Date();
        addressPo.setCreateTime(time);
        addressPo.setUpdateTime(time);
        addressPo.setRecorder(recorder);
        addressPo.setGeom(PostgisUtil.createPoint(new GisCoordinate(gisCoordinate.getLongitude(),gisCoordinate.getLatitude())));
        //无外键关联
        addressPo.setRelateId(-1);
        return addressPo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer insertAddressNameList(List<GisAddressT> list){
        return gisAddressTMapper.insertList(list);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer updateAddressNameList(List<GisAddressT> list){
        int count = 0;
        for(GisAddressT item:list) {
            count += gisAddressTMapper.updateByKey(item);
        }
        return count;
    }

    /**
     * @author monkjavaer
     * @description 获取模糊匹配的地名查询分页结果
     * @date 15:21 2019/7/8
     * @param: [qo]
     * @return com.m.gis.springboot.common.GisPage<com.m.gis.springboot.vo.GisAddressNameVO>
     **/
    @Override
    public GisPage<GisAddressNameVO> getAddressNamePages(GisAddressNameQO qo) {
        PageHelper.startPage(qo.getPageIndex(), qo.getPageSize());
        String districtCode = qo.getDistrictCode();
        if (StringUtils.isNotBlank(districtCode)){
            //
            Integer level = gisDistrictTypeUtil.getDistrictLevels();
            GisDistrictType districtType = gisDistrictTypeUtil.getDistrictTypeByDistrictCode(districtCode);
            //如果传入的不是最低级行政区划，去掉结尾的“0”模糊匹配
            if (level > Integer.parseInt(districtType.getType())) {
                //非最低级行政区划结尾都有0
                String codeEnd = "0";
                while (districtCode.endsWith(codeEnd)) {
                    districtCode = districtCode.substring(0,districtCode.length() - 1);
                }
                qo.setDistrictCode(districtCode);
            }
        }
        //1、先通过前缀模糊匹配查询
        List<GisAddressNameVO> list = gisAddressTMapper.selectLikeAddressName(qo);

        //2、模糊匹配未满，则通过全文检索分词搜索补偿
        if (list.size() < qo.getPageSize() && StringUtils.isNotBlank(qo.getName())) {
            //通过查询名字构造分词参数
            String queryName = qo.getName().replaceAll("\\s+", "&")+":*";
            qo.setSearchParam(queryName);
            List<GisAddressNameVO> fullTextList = gisAddressTMapper.selectFullTextAddressName(qo);
            //查询结果数据处理，去重
            distinctGisAddress(list,fullTextList,qo.getPageSize());
        }

        //3、如果还未满，则通过相似查询补偿
        if (list.size() < qo.getPageSize()) {
            List<GisAddressNameVO> similarityList = gisAddressTMapper.selectSimilarityAddressName(qo);
            //查询结果数据处理，去重
            distinctGisAddress(list,similarityList,qo.getPageSize());
        }
        //循环设置行政区划全名
        for (GisAddressNameVO gisAddressNameVO : list) {
            String code = gisAddressNameVO.getDistrictCode();
            if (StringUtils.isBlank(code)) {
                //地址表行政区划空，用经纬度查询
                code = gisDistrictService.getDistrictCode(new GisCoordinate(gisAddressNameVO.getLongitude(),gisAddressNameVO.getLatitude()));
            }
            String address = gisDistrictService.getFullNameByDistrictCode(code);
            gisAddressNameVO.setDistrict(address);
        }
        return new GisPage<>(list);
    }

    /**
     * @author monkjavaer
     * @description 查询结果数据处理，去重
     * @date 17:15 2019/7/8
     * @param: [list, queryAddressList, pagesize]
     * @return void
     */
    private void distinctGisAddress(List<GisAddressNameVO> list,List<GisAddressNameVO> queryAddressList,Integer pagesize){
        for (GisAddressNameVO gisAddressNameVO : queryAddressList) {
            if (list.size() >= pagesize) {
                break;
            }
            //通过主键gid去重
            List<GisAddressNameVO> containAddress = list.stream()
                    .filter(address -> address.getGid().equals(gisAddressNameVO.getGid())).collect(Collectors.toList());
            if (containAddress.size() > 0){
                continue;
            }
            list.add(gisAddressNameVO);
        }
    }

    /**
     * 根据经纬度获取地名
     * @param qo
     * @return
     */
    @Override
    public GisAddressNameLonLatVO getAddressNameByLonLat(GisAddressNameLonLatQO qo) {
        GisDistrict district = null;
        GisCoordinate gisCoordinate;
        try {
            //获取相应的行政区域
            district = gisDistrictService.getDistrict(qo.getRestrictionCode());
            //如果该行政区域不包含该坐标点，则超出范围，返回status为超出范围
            gisCoordinate = new GisCoordinate(qo.toGisCoordinate());
            if(!district.contains(gisCoordinate)){
                return new GisAddressNameLonLatVO(GisAddressNameLonLatStatusEnums.OUT_OF_BOUNDS.getStatus());
            }
        } catch (GisDistrictServiceThrowableException e) {
            throw new GisAddressServiceException(e.getMessage());
        }

        //设置容差半径，如果qo没有，则使用默认的设置；
        double distanceInDegree = qo.getTolerance()==null? GisAddressConstants.DEFAULT_ADDRESS_NAME_TOLERANCE:GisGeodesyUtil.moveInDirection(qo.toGisCoordinate(),0,qo.getTolerance()).distance(qo.toGisCoordinate());
        qo.setTolerance(distanceInDegree);

        //根据经纬度获取行政区域编码
        String districtCode = gisDistrictService.getDistrictCode(gisCoordinate);

        GisAddressNameLonLatVO addressNameLonLatVO = null;
        //1、策略一，根据经纬度先查询地址表gis_address_name_t
        addressNameLonLatVO = gisAddressTMapper.selectAddressNameByLonLatDistance(qo);
        if(addressNameLonLatVO == null){
            //2、如果没有结果，策略二，根据经纬度获取最近的道路名gis_road_l
            addressNameLonLatVO  = gisAddressTMapper.selectRoadNameByLonLatDistance(qo);
            if (addressNameLonLatVO != null) {
                addressNameLonLatVO.setDistrictCode(districtCode);
                addressNameLonLatVO.setStatus(GisAddressNameLonLatStatusEnums.VALID.getStatus());
                //道路地名类型
                addressNameLonLatVO.setType(GisAddressTypeEnums.ROAD.getValue());
            }else {
                //如果没有结果，则返回status为无地址
                addressNameLonLatVO = new GisAddressNameLonLatVO(GisAddressNameLonLatStatusEnums.INVALID.getStatus());
                addressNameLonLatVO.setDistrictCode(districtCode);
            }
        } else {
            addressNameLonLatVO.setStatus(GisAddressNameLonLatStatusEnums.VALID.getStatus());
        }
        //设置行政区划全名
        String address = gisDistrictService.getFullNameByDistrictCode(districtCode);
        addressNameLonLatVO.setDistrict(address);
        return addressNameLonLatVO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveAddressNameList(GisAddressNameSaveQO qo) {
        List<GisAddressT> gisAddressNameList = new ArrayList<>();
        for(GisAddressNameElementQO item:qo.getAddressList()){
            GisAddressT gisAddressT = createGisAddressNameT(item.getName(),item.toGisCoordinate(),GisAddressTypeEnums.getGisAddressType(item.getType()),qo.getRecorder());
            gisAddressNameList.add(gisAddressT);
        }
        return gisAddressTMapper.insertList(gisAddressNameList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer saveOrUpdateAddressNameList(GisAddressNameSaveQO qo){
        List<GisAddressT> insertAddressNameList = new ArrayList<>();
        List<GisAddressT> updateAddressNameList = new ArrayList<>();

        for(GisAddressNameElementQO item:qo.getAddressList()){
            //如果type相等，且在经纬度容差范围内，则认为该地名是已存在的
            List<GisAddressT> itemResult = gisAddressTMapper.selectAddressByNameTypeLonLatTolerance(null,item.getType(),item.toGisCoordinate(),GisAddressConstants.DEFAULT_ADDRESS_NAME_TOLERANCE);
            //如果不重复，则insert item
            if(CollectionUtils.isEmpty(itemResult)){
                GisAddressT gisAddressT = createGisAddressNameT(item.getName(),item.toGisCoordinate(),GisAddressTypeEnums.getGisAddressType(item.getType()),qo.getRecorder());
                insertAddressNameList.add(gisAddressT);
            }
            else{
                //否则批量更新
                for(GisAddressT resultItem:itemResult){
                    resultItem.setName(item.getName());
                    resultItem.setType(item.getType());
                    resultItem.setGeom(PostgisUtil.createPoint(item.toGisCoordinate()));
                    resultItem.setDistrictCode(gisDistrictService.getDistrictCode(item.toGisCoordinate()));
                    resultItem.setUpdateTime(new Date());
                    resultItem.setRecorder(qo.getRecorder());
                    updateAddressNameList.add(resultItem);
                }
            }
        }
        int insertCount = 0;
        int updateCount = 0;
        //批量插入地名
        if(CollectionUtils.isEmpty(insertAddressNameList)==false)
            insertCount = insertAddressNameList(insertAddressNameList);
        //批量更新地名
        if(CollectionUtils.isEmpty(updateAddressNameList)==false)
            updateCount = updateAddressNameList(updateAddressNameList);
        return insertCount+updateCount;
    }


    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true,isolation = Isolation.REPEATABLE_READ)
    public Boolean getAddressNameIfDuplicatedByNameAndLonLat(GisAddressNameElementQO qo) {
        List<GisAddressT> result = gisAddressTMapper.selectAddressByNameTypeLonLatTolerance(qo.getName(),qo.getType(),qo.toGisCoordinate(),GisAddressConstants.DEFAULT_ADDRESS_NAME_TOLERANCE);
        //如果没有匹配结果，则为不重复，返回false
        return !CollectionUtils.isEmpty(result);
    }


    @Deprecated
    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true,isolation = Isolation.REPEATABLE_READ)
    public Boolean getAddressNameIfDuplicated(GisCoordinateQO qo) {
        GisAddressNameLonLatQO lonlatQO = new GisAddressNameLonLatQO();
        lonlatQO.setLongitude(qo.getLongitude());
        lonlatQO.setLatitude(qo.getLatitude());

        //根据经纬度获取地名库中最近的用户定义的地名
        GisAddressNameLonLatVO vo = gisAddressTMapper.selectAddressNameByLonLatDistance(lonlatQO);

        //如果该经纬度无地名，则肯定不重合
        if(vo==null)
            return false;

        //判断地名库的点是否与入参位置重合，如果距离小于默认的容差值，则认为与地名库中某个地名重复
        GisCoordinate pos = new GisCoordinate(qo.getLongitude(),qo.getLatitude());
        return pos.distance(new GisCoordinate(vo.getLongitude(),vo.getLatitude())) < GisAddressConstants.DEFAULT_ADDRESS_NAME_TOLERANCE;
    }

    @Override
    public GisAddressNameInfoVO getAddressNameInformation(Integer gid, String type){
        GisPreconditions.checkArgument(gid>0,String.format("getAddressNameInformation param gid = {%s} must be positive.",gid));
        GisPreconditions.checkArgument(GisAddressTypeEnums.isValid(type),String.format("getAddressNameInformation param type = {%s} must be valid address name type.",type));

        GisAddressNameInfoVO vo = new GisAddressNameInfoVO();

        GisAddressTypeEnums gisAddressTypeEnums = GisAddressTypeEnums.getGisAddressType(type);
        if(gisAddressTypeEnums.isDistrictAddressNameType()){
            //如果是行政区域地名类型
            GisDistrict district = gisDistrictService.getDistrictById(gisAddressTypeEnums.getDistrictTypeCode(),gid);
            vo.setGeom(district.getWkt());
            vo.setEnvelope(district.getGeom().getEnvelope().toString());
        }
        else if(gisAddressTypeEnums.equals(GisAddressTypeEnums.ROAD)){
            //如果是道路地名类型
            //TODO
        }
        else if(gisAddressTypeEnums.equals(GisAddressTypeEnums.BUILDING)){
            //如果是建筑物地名类型

        }
        else if(gisAddressTypeEnums.equals(GisAddressTypeEnums.ADDRESS)){
            //如果是地址地名类型

        }
        else{
            //如果是用户编辑地名类型

        }
        return vo;
    }


}

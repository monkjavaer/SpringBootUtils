package com.m.gis.springboot.service;

import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import com.m.gis.springboot.district.common.GisDistrict;
import com.m.gis.springboot.district.common.GisDistrictType;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.vo.GisDistrictDictVO;
import com.m.gis.springboot.vo.GisDistrictVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisBolDistrictServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/29
 * @Version: V1.0
 */
@Service
public class GisBolDistrictServiceImpl implements GisBolDistrictService{
    private static final Logger logger = LoggerFactory.getLogger(GisBolDistrictServiceImpl.class);

    @Autowired
    private GisDistrictService gisDistrictService;

    @Autowired
    private GisAbstractDistrictTypeUtil gisDistrictTypeUtil;

    private final String DEFAULT_PARENT_CODE = "-1";

    @Override
    @Cacheable(cacheNames = "dict")
    public List<GisDistrictDictVO> getDistrictDict() {
        List<GisDistrictDictVO> result = new ArrayList<>();
        for(GisDistrictType districtType:gisDistrictTypeUtil.getGisDistrictTypeCollection().getGisDistrictTypes()){
            //最高级国家级的不返回
            if(gisDistrictTypeUtil.isHighestDistrictType(districtType))
                continue;

            List<GisDistrict> districts = gisDistrictService.getDistrict(districtType);
            for(GisDistrict item:districts){
                //parent
                String parentCode = gisDistrictTypeUtil.getParentDistrictCode(item.getDistrictCode());
                if(StringUtils.isEmpty(parentCode))
                    parentCode = DEFAULT_PARENT_CODE;

                result.add(new GisDistrictDictVO(item.getDistrictCode(), parentCode, "", item.getName()));
            }
        }
        return result;
    }

    @Override
    public List<GisDistrictVO> getDistrictAll(){
        List<GisDistrictVO> vos = new ArrayList<>();

        for(GisDistrictType districtType:gisDistrictTypeUtil.getGisDistrictTypeCollection().getGisDistrictTypes()){
            List<GisDistrict> districts = gisDistrictService.getDistrict(districtType);
            for(GisDistrict item:districts){
                vos.add(new GisDistrictVO(districtType.getType(),item.getDistrictCode(), item.getName(),item.getWkt()));
            }
        }
        return vos;
    }

    @Override
    public List<GisDistrictVO> getDistrictByType(String districtTypeCode,String parentCode){
        GisPreconditions.checkArgument(gisDistrictTypeUtil.isValid(districtTypeCode),String.format("getDistrictByType errors, districtType = {%s} is not a valid district type. ",districtTypeCode));

        //获取行政区域类型编码
        GisDistrictType districtType = gisDistrictTypeUtil.getDistrictType(districtTypeCode);

        List<GisDistrictVO> vos = new ArrayList<>();
        //如果是国家级行政区域，那parentCode可以忽略
        List<GisDistrict> districts = null;
        if(gisDistrictTypeUtil.isHighestDistrictType(districtType)){
            districts = gisDistrictService.getDistrict(districtType);
            for(GisDistrict item:districts){
                vos.add(new GisDistrictVO(districtType.getType(),item.getDistrictCode(), item.getName(),item.getWkt()));
            }
        }
        else{
            if(StringUtils.isBlank(parentCode)){
                //如果parentCode没有指定，那么默认查询所有districtTypeCode类型的行政区域
                districts = gisDistrictService.getDistrict(districtType);
            }
            else{
                districts = gisDistrictService.getDistrict(districtType,parentCode);
            }
            for(GisDistrict item:districts){
                vos.add(new GisDistrictVO(districtType.getType(),item.getDistrictCode(), item.getName(),item.getWkt()));
            }
        }
        return vos;
    }

}

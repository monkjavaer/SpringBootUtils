package com.m.gis.springboot.service;

import com.m.gis.springboot.district.common.GisDistrict;
import com.m.gis.springboot.district.common.GisDistrictType;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.mapper.GisDistrictMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GisDistrictCacheServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
@Service
public class GisDistrictCacheServiceImpl implements GisDistrictCacheService {
    private static final Logger logger = LoggerFactory.getLogger(GisDistrictCacheServiceImpl.class);

    @Autowired
    private GisDistrictMapper mapper;

    private Map<GisDistrictType,List<GisDistrict>> cacheDistricts = new HashMap<>();

    @Override
    public List<GisDistrict> getGisDistrictByType(GisDistrictType type) {
        if(cacheDistricts.containsKey(type)){
            return cacheDistricts.get(type);
        }
        //这里GisDistrictType中的type为gis_administrative_p表中的level
        List<GisDistrict> list = mapper.selectAll(type.getTableName(), Integer.valueOf(type.getType()));

        //检查数据库中行政区域编码的合法性
        for(GisDistrict item:list){
            GisPreconditions.checkArgument(
                    type.isValidDistrictCode(item.getDistrictCode()),
                    String.format("GisDistrictCacheServiceImpl getGisDistrictByType errors, district code is not valid with regex {%s}, please check the source data in db. table={%s},id={%d},district_code={%s}",type.getRegex(),type.getTableName(),item.getId(),item.getDistrictCode()));
        }
        cacheDistricts.put(type,list);
        return list;
    }

}

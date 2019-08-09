package com.m.gis.springboot.service;

import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import com.m.gis.springboot.district.common.GisDistrictType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Title: GisAsyncDistrictServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
@Service
public class GisAsyncDistrictServiceImpl implements GisAsyncDistrictService{
    private static final Logger logger = LoggerFactory.getLogger(GisAsyncDistrictServiceImpl.class);

    @Autowired
    private GisAbstractDistrictTypeUtil gisDistrictTypeUtil;

    @Autowired
    private GisDistrictCacheService gisDistrictCacheService;

    @Override
    @Async
    public void initDistrict() {
        Long start = System.currentTimeMillis();
        logger.info("begin to init district cache.");

        //预热缓存
        for(GisDistrictType item: gisDistrictTypeUtil.getGisDistrictTypeCollection().getGisDistrictTypes()){
            gisDistrictCacheService.getGisDistrictByType(item);
        }

        logger.info(String.format("end to init district cache. cost {%d} ms", System.currentTimeMillis()-start));
    }
}

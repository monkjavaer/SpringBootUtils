package com.m.gis.springboot.service;

import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import com.m.gis.springboot.district.common.GisDistrictType;
import com.m.gis.springboot.utils.LocaleUtil;
import com.m.gis.springboot.vo.DictVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisDistrictTypeServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/27
 * @Version: V1.0
 */
@Service
public class GisDistrictTypeServiceImpl implements GisDistrictTypeService {
    private static final Logger logger = LoggerFactory.getLogger(GisDistrictServiceImpl.class);

    @Autowired
    private GisAbstractDistrictTypeUtil gisDistrictTypeUtil;

    @Override
    public List<DictVO> getDictVO() {
        List<GisDistrictType> districtTypes = gisDistrictTypeUtil.getGisDistrictTypeCollection().getGisDistrictTypes();

        List<DictVO> dictVOList = new ArrayList<DictVO>();
        for(GisDistrictType item:districtTypes){
            dictVOList.add(new DictVO(item.getType(),LocaleUtil.getMessage(item.getDictCode(),null,item.getName())));
        }
        return dictVOList;
    }
}

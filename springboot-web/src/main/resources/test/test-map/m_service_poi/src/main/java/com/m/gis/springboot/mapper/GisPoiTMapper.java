package com.m.gis.springboot.mapper;

import com.m.gis.springboot.po.GisPoiT;
import com.m.gis.springboot.qo.GisPoiZoneQO;
import com.m.gis.springboot.vo.PoiVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GisPoiTMapper extends Mapper<GisPoiT> {
    /**
     *
     * @name selectByZone
     * @description  根据区域查询区域内的兴趣点
     * @param qo
     * @return
     * @author monkjavaer
     * @date 2018年3月30日
     */
    List<PoiVO> selectByZone(GisPoiZoneQO qo);

}
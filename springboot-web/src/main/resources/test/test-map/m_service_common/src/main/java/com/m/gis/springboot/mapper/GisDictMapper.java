package com.m.gis.springboot.mapper;

import com.m.gis.springboot.po.GisDictItem;
import com.m.gis.springboot.vo.DictVO;

import java.util.List;
import java.util.Map;

public interface GisDictMapper {

	List<GisDictItem> selectAll(Map<String, String> paramMaps);

	/**
	 * @author monkjavaer
	 * @description 根据表名查询字典
	 * @date 10:12 2019/6/17
	 * @param: [paramMaps]
	 * @return java.util.List<com.m.gis.springboot.vo.DictVO>
	 **/
	List<DictVO> selectDictVO(Map<String, String> paramMaps);

	/**
	 * 根据表名查询字典(兴趣点类型查询)
	 * @param paramMaps
	 * @return
	 */
	List<GisDictItem> selectAllPoi(Map<String,Object> paramMaps);
}
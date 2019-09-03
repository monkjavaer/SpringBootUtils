package com.m.gis.springboot.service;

import com.m.gis.springboot.mapper.GisDictMapper;
import com.m.gis.springboot.po.GisDictItem;
import com.m.gis.springboot.vo.DictVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GisDictServiceImpl.java
 * @Package com.m.gis.springboot.service.impls
 * @Description: 获取数据字典类型的实现
 * @author monkjavaer
 * @date 2017年11月24日 下午6:02:39
 * @version V1.0
 */
@Service
public class GisDictServiceImpl implements GisDictService{
	@Autowired
	private GisDictMapper gisDictMapper;

	@Override
	@Cacheable(cacheNames = "dict")
	public List<GisDictItem> getDict(String table){
		Map<String,String> maps = new HashMap<String,String>();
		maps.put("tablename", table);
		return gisDictMapper.selectAll(maps);
	}

	@Override
	@Cacheable(cacheNames = "dict")
	public List<DictVO> getDictVO(String table){
		List<DictVO> dictVOList = new ArrayList<DictVO>();

		List<GisDictItem> gisDictItems =  getDict(table);
		for(GisDictItem item:gisDictItems){
			dictVOList.add(new DictVO(item.getCode(),item.getNameLocale()));
		}
		return dictVOList;
	}

	/**
	 * 获取用于前端展示的字典数据
	 *
	 * @param table 表名
	 * @param queryEnable 启用查询 ：传入t表示true，查询字段query_enable = t 的信息。
	 * @return
	 */
	@Override
	@Cacheable(cacheNames = "dict")
	public List<DictVO> getDictVO(String table, Boolean queryEnable) {
		List<DictVO> dictVOList = new ArrayList<DictVO>();

		List<GisDictItem> gisDictItems =  getDict(table,queryEnable);
		for(GisDictItem item:gisDictItems){
			dictVOList.add(new DictVO(item.getCode(),item.getNameLocale()));
		}
		return dictVOList;
	}

	@Override
	@Cacheable(cacheNames = "dict")
	public List<GisDictItem> getDict(String table, Boolean queryEnable){
		Map<String,Object> maps = new HashMap<>();
		maps.put("tablename", table);
		maps.put("queryEnable", queryEnable);
		return gisDictMapper.selectAllPoi(maps);
	}
}


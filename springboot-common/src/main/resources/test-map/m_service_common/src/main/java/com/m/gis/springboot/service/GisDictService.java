package com.m.gis.springboot.service;

import com.m.gis.springboot.po.GisDictItem;
import com.m.gis.springboot.vo.DictVO;

import java.util.List;

/**
 * @Title: GisDictService.java
 * @Package com.m.gis.springboot.service.interfaces
 * @Description: 获取数据字典服务
 * @author monkjavaer
 * @date 2017年11月24日 下午5:59:51
 * @version V1.0
 */
public interface GisDictService {

	/**
	 * 
	 * @name getDict
	 * @description 获取用于前端展示的字典数据
	 * @param table 表名
	 * @return 
	 * @author monkjavaer
	 * @date 2018年3月29日
	 */
	public List<DictVO> getDictVO(String table);


	/**
	 * 获取用于前端展示的字典数据(兴趣点查询定制)
	 *
	 * @param table 表名
	 * @param queryEnable 启用查询 ：传入t表示true，查询字段query_enable = t 的信息。
	 * @return
	 */
	public List<DictVO> getDictVO(String table , Boolean queryEnable);

	/***
	 * @Description: 获取字典数据项
	 * @Author: monkjavaer
	 * @Data: 19:06 2018/6/26
	 * @Param: [table]
	 * @Throws
	 * @Return java.util.List<com.m.gis.springboot.po.GisDictItem>
	 */
	public List<GisDictItem> getDict(String table);

	/**
	 * 获取字典数据项(兴趣点查询定制)
	 * @param table
	 * @param queryEnable 启用查询 ：传入t表示true，查询字段query_enable = t 的信息。
	 * @return
	 */
	public List<GisDictItem> getDict(String table, Boolean queryEnable);

}


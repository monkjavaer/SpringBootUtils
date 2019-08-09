package com.m.gis.springboot.mapper;

import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.po.GisAddressT;
import com.m.gis.springboot.qo.GisAddressNameLonLatQO;
import com.m.gis.springboot.qo.GisAddressNameQO;
import com.m.gis.springboot.vo.GisAddressNameLonLatVO;
import com.m.gis.springboot.vo.GisAddressNameVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GisAddressTMapper extends Mapper<GisAddressT> {

    /***
     * @Description: 模糊查询地名的匹配列表
     * @Author: monkjavaer
     * @Data: 15:15 2018/6/19
     * @Param: [qo]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.vo.GisAddressNameVO>
     */
    List<GisAddressNameVO> selectLikeAddressName(GisAddressNameQO qo);


    /**
     * @author monkjavaer
     * @description 相似地名查询
     * @date 14:30 2019/7/8
     * @param: [qo]
     * @return java.util.List<com.m.gis.springboot.vo.GisAddressNameVO>
     */
    List<GisAddressNameVO> selectSimilarityAddressName(GisAddressNameQO qo);

    /**
     * @author monkjavaer
     * @description 通过全文检索分词搜索地名
     * @date 16:55 2019/7/8
     * @param: [qo]
     * @return java.util.List<com.m.gis.springboot.vo.GisAddressNameVO>
     */
    List<GisAddressNameVO> selectFullTextAddressName(GisAddressNameQO qo);

    /***
     * @Description: 根据经纬度获取最邻近地名
     * @Author: monkjavaer
     * @Data: 10:28 2018/6/21
     * @Param: []
     * @Throws
     * @Return com.m.gis.springboot.vo.GisAddressNameLonLatVO
     */
    GisAddressNameLonLatVO selectAddressNameByLonLatDistance(GisAddressNameLonLatQO qo);

    /**
     * 根据经纬度获取最近的道路名
     * @param qo
     * @return
     */
    GisAddressNameLonLatVO selectRoadNameByLonLatDistance(GisAddressNameLonLatQO qo);


    /***
     * @Description: 根据名字、类型和经纬度容差半径查找地名
     * @Author: monkjavaer
     * @Data: 15:47 2018/8/14
     * @Param: [name, coordinate, tolerance]
     * @Throws
     * @Return java.util.List<com.m.gis.springboot.po.GisAddressT>
     */
    List<GisAddressT> selectAddressByNameTypeLonLatTolerance(@Param("name") String name,
                                                             @Param("type") String type,
                                                             @Param("coordinate") GisCoordinate coordinate,
                                                             @Param("tolerance") Double tolerance);


    /***
     * @Description: 批量插入地名
     * @Author: monkjavaer
     * @Data: 17:33 2018/6/21
     * @Param: [list]
     * @Throws
     * @Return java.lang.Integer
     */
    Integer insertList(List<GisAddressT> list);

    /***
     * @Description: 更新地名
     * @Author: monkjavaer
     * @Data: 14:47 2018/8/16
     * @Param: [item]
     * @Throws
     * @Return java.lang.Integer
     */
    Integer updateByKey(GisAddressT item);

}
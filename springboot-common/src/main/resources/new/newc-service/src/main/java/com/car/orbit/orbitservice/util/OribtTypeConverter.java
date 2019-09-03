package com.car.orbit.orbitservice.util;

import com.car.orbit.orbitservice.constant.EsConstant;
import com.car.orbit.orbitservice.entity.OrbitVehicleFile;
import com.car.orbit.orbitservice.util.redis.BlacklistRedis;
import com.car.orbit.orbitservice.util.redis.BrandRedis;
import com.car.orbit.orbitservice.vo.TacticsVehicleBaseInfo;
import com.car.orbit.orbitutil.tools.JsonUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreateDate: 2019-3-27 <br/>
 * Author: monkjavaer <br/>
 * Description:
 * Version: 1.0
 **/
public class OribtTypeConverter {

    /**
     * @description 补全List<HiddenVehiclesVO>中缺失的车辆信息
     * @date: 2019-3-27 17:44
     * @author: monkjavaer
     * @param hiddenVehiclesVOList
     * @return
     */
    public static List<? extends TacticsVehicleBaseInfo> completeList(TransportClient transportClient, List<? extends TacticsVehicleBaseInfo> hiddenVehiclesVOList){
        List<String> vids = new ArrayList<>();
        for (TacticsVehicleBaseInfo hiddenVehiclesVO: hiddenVehiclesVOList){
            vids.add(hiddenVehiclesVO.getVid());
        }
        /** 构建检索条件*/
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termsQuery(EsConstant.VID, vids));
        /** 获取查询结果*/
        SearchResponse response = transportClient.prepareSearch(EsConstant.ORBIT_VEHICLE_FILE)
                .setTypes(EsConstant.TYPE_FILE)
                .setQuery(queryBuilder)
                .setSize(1000)
                .execute()
                .actionGet();
        /** 解析查询结果*/
        Map<String, OrbitVehicleFile> mapFile = new HashMap<>();
        for(SearchHit searchHit: response.getHits()){
            String jsonStr = searchHit.getSourceAsString();
            OrbitVehicleFile orbitVehicleFile = JsonUtils.toBean(jsonStr, OrbitVehicleFile.class);
            mapFile.put(orbitVehicleFile.getVid(), orbitVehicleFile);
        }

        List<TacticsVehicleBaseInfo> result = new ArrayList<>();
        for (TacticsVehicleBaseInfo hiddenVehiclesVO: hiddenVehiclesVOList){
            if(!mapFile.containsKey(hiddenVehiclesVO.getVid())){
                continue;
            }
            //更新车牌号
            hiddenVehiclesVO.setPlateNumber(mapFile.get(hiddenVehiclesVO.getVid()).getPlateNumber());
            //车牌颜色
            hiddenVehiclesVO.setPlateColor(mapFile.get(hiddenVehiclesVO.getVid()).getPlateColor());
            //车辆类型
            hiddenVehiclesVO.setVehicleType(mapFile.get(hiddenVehiclesVO.getVid()).getVehicleType());
            //车辆颜色
            hiddenVehiclesVO.setVehicleColor(mapFile.get(hiddenVehiclesVO.getVid()).getVehicleColor());
            //品牌相关
            String brandCode = mapFile.get(hiddenVehiclesVO.getVid()).getVehicleBrand();
            String brandChildCode = mapFile.get(hiddenVehiclesVO.getVid()).getVehicleBrandChild();
            hiddenVehiclesVO.setBrandName(BrandRedis.getBrandNameByCode(brandCode));
            hiddenVehiclesVO.setChildBrandName(BrandRedis.getChildBrandNameByCode(brandChildCode));
            hiddenVehiclesVO.setFullBrand(BrandRedis.getFullBrandNameByCode(brandCode, brandChildCode));
            //是否在黑名单中
            hiddenVehiclesVO.setBlackList(false);
            if (BlacklistRedis.isInBlacklist(hiddenVehiclesVO.getVid())){
                hiddenVehiclesVO.setBlackList(true);
            }
            //放入结果列表
            result.add(hiddenVehiclesVO);
        }
        //返回结果
        return result;
    }
}

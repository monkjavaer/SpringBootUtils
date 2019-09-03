package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.entity.OrbitVehicleBrand;
import com.car.orbit.orbitservice.entity.OrbitVehicleBrandChild;
import com.car.orbit.orbitservice.mapper.OrbitVehicleBrandChildMapper;
import com.car.orbit.orbitservice.mapper.OrbitVehicleBrandMapper;
import com.car.orbit.orbitservice.service.IVehicleBrandService;
import com.car.orbit.orbitservice.util.redis.BaseBusinessRedis;
import com.car.orbit.orbitutil.tools.JsonUtils;
import com.car.orbit.orbitutil.tools.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Title: VehicleBrandServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 车辆品牌，子品牌接口
 * @Author: monkjavaer
 * @Data: 2019/3/22 11:25
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class VehicleBrandServiceImpl implements IVehicleBrandService {

    @Autowired
    private OrbitVehicleBrandMapper brandMapper;

    @Autowired
    private OrbitVehicleBrandChildMapper brandChildMapper;

    /**
     * redis
     */
    @Autowired
    RedisClient redisClient;

    /**
     * 从redis获取全部品牌
     *
     * @return
     */
    @Override
    public List<OrbitVehicleBrand> getBrandFromRedis() {
        List<OrbitVehicleBrand> list = new ArrayList<>();
        Map<String, String> map = redisClient.get(BaseBusinessRedis.BRAND_KEY);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (it.hasNext()) {
            entry = it.next();
            OrbitVehicleBrand orbitVehicleBrand = JsonUtils.toBean(entry.getValue(), OrbitVehicleBrand.class);
            list.add(orbitVehicleBrand);
        }
        return list;
    }

    /**
     * 从redis获取全部子品牌
     *
     * @return
     */
    @Override
    public List<OrbitVehicleBrandChild> getChildBrandFromRedis() {
        List<OrbitVehicleBrandChild> list = new ArrayList<>();
        Map<String, String> map = redisClient.get(BaseBusinessRedis.CHILD_BRAND_KEY);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (it.hasNext()) {
            entry = it.next();
            OrbitVehicleBrandChild child = JsonUtils.toBean(entry.getValue(), OrbitVehicleBrandChild.class);
            list.add(child);
        }
        return list;
    }

    /**
     * 从mysql获取全部品牌
     *
     * @return
     */
    @Override
    public List<OrbitVehicleBrand> getBrand() {
        List<OrbitVehicleBrand> list = brandMapper.selectAll();
        return list;
    }

    /**
     * 从mysql获取全部子品牌
     *
     * @return
     */
    @Override
    public List<OrbitVehicleBrandChild> getChildBrand() {
        return brandChildMapper.selectAll();
    }

    /**
     * 从mysql根据品牌id获取子品牌
     *
     * @param brandId
     * @return
     */
    @Override
    public List<OrbitVehicleBrandChild> getChildBrand(String brandId) {
        Example example = new Example(OrbitVehicleBrandChild.class);
        example.createCriteria()
                .andEqualTo("parentCode", brandId);
        List<OrbitVehicleBrandChild> list = brandChildMapper.selectByExample(example);
        return list;
    }

    /**
     * 根据品牌code从Redis获取品牌信息
     *
     * @param code 品牌code
     * @return OrbitVehicleBrand
     */
    @Override
    public OrbitVehicleBrand getBrandByCode(String code) {
        OrbitVehicleBrand orbitVehicleBrand = null;
        Map<String, String> map = redisClient.get(BaseBusinessRedis.BRAND_KEY);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (it.hasNext()) {
            entry = it.next();
            if (code.equals(entry.getKey())) {
                orbitVehicleBrand = JsonUtils.toBean(entry.getValue(), OrbitVehicleBrand.class);
            }
        }
        return orbitVehicleBrand;
    }

    /**
     * 根据子品牌code从Redis获取子品牌信息
     *
     * @param code 子品牌code
     * @return OrbitVehicleBrandChild
     */
    @Override
    public OrbitVehicleBrandChild getChildBrandByCode(String code) {
        OrbitVehicleBrandChild child = null;
        Map<String, String> map = redisClient.get(BaseBusinessRedis.CHILD_BRAND_KEY);
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (it.hasNext()) {
            entry = it.next();
            if (code.equals(entry.getKey())) {
                child = JsonUtils.toBean(entry.getValue(), OrbitVehicleBrandChild.class);
            }
        }
        return child;
    }

}

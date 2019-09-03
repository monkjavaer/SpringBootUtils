package com.car.orbit.orbitservice.service.impl;


import com.car.orbit.orbitservice.mapper.clickhouse.OrbitVehicleTraitMapper;
import com.car.orbit.orbitservice.qo.RequestPrameterQo;
import com.car.orbit.orbitservice.service.IVehicleTraitService;
import com.car.orbit.orbitservice.util.redis.BrandRedis;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitservice.util.redis.RoadcrossPointRedis;
import com.car.orbit.orbitservice.util.redis.VehicleTypeRedis;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("vehicleTraitService")
////@Transactional(rollbackFor = Exception.class)
public class VehicleTraitServiceImpl implements IVehicleTraitService {

    @Resource
    private OrbitVehicleTraitMapper vehicleTraiDao;


    @Override
    public List<VehicleTraitVo> getTraitByImgAll() {
        return  vehicleTraiDao.getTraitByImgAll( );
    }


    @Override
    public List<VehicleTraitVo> getTraitByCondition(RequestPrameterQo requestPrameter) {
        //当传入图片信息时，计算图片的模
        if(requestPrameter.getImg() != null && requestPrameter.getImg().length > 0){
            float[] imgDouble = requestPrameter.getImg();
            Double data = 0.00 ;
            for (double math  :imgDouble) {
                data += Math.pow(math, 2);
            }
            requestPrameter.setRet(Math.sqrt(data));
        }
        return vehicleTraiDao.getTraitByCondition(requestPrameter);
    }

    /**
     * 组装好ID和对应Name的映射关系返回
     * @param requestPrameter
     * @return
     */
    @Override
    public List<VehicleTraitVo> getTraitWithNameByCondition(RequestPrameterQo requestPrameter) {
        List<VehicleTraitVo> records = getTraitByCondition(requestPrameter);
        for(VehicleTraitVo vo : records){
            vo.setRoadName(RoadcrossPointRedis.getNameByCode(vo.getRoadCrossPointId()));
            vo.setDeviceName(DevicePointRedis.getNameByCode(vo.getDeviceId()));
            vo.setVehicleTypeName(VehicleTypeRedis.getNameByCode(vo.getVehicleType()));
            vo.setVehicleBrandName(BrandRedis.getBrandNameByCode(vo.getVehicleBrand()));
            vo.setVehicleBrandChildName(BrandRedis.getChildBrandNameByCode(vo.getVehicleBrandChild()));
            if(vo.getRet()>1.00){
                vo.setRet((float) 1.00);
            }
        }
        return records;
    }
}

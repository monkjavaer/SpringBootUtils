package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;

/**
 * @Title: AlarmDetailBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 预警对比详细信息
 * @Author: monkjavaer
 * @Data: 2019/4/2 17:35
 * @Version: V1.0
 */
public class AlarmDetailBO {

    /**
     * 抓拍记录
     */
    private OrbitPassVehicleRecord orbitPassVehicleRecord;
    /**
     * 黑名单记录
     */
    private OrbitControlBlacklist orbitControlBlacklist;

    public OrbitPassVehicleRecord getOrbitPassVehicleRecord() {
        return orbitPassVehicleRecord;
    }

    public void setOrbitPassVehicleRecord(OrbitPassVehicleRecord orbitPassVehicleRecord) {
        this.orbitPassVehicleRecord = orbitPassVehicleRecord;
    }

    public OrbitControlBlacklist getOrbitControlBlacklist() {
        return orbitControlBlacklist;
    }

    public void setOrbitControlBlacklist(OrbitControlBlacklist orbitControlBlacklist) {
        this.orbitControlBlacklist = orbitControlBlacklist;
    }
}

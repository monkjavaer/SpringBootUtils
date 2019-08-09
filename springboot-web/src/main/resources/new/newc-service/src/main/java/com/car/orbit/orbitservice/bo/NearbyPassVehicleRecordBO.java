package com.car.orbit.orbitservice.bo;

import com.car.orbit.orbitservice.entity.OrbitPassVehicleRecord;

/**
 * @Title: NearbyPassVehicleRecordBO
 * @Package: com.car.orbit.orbitservice.bo
 * @Description: 前后一条过车记录
 * @Author: monkjavaer
 * @Date: 2019/04/16 14:57
 * @Version: V1.0
 */
public class NearbyPassVehicleRecordBO {

    /**
     * 中间一条过车记录
     */
    private OrbitPassVehicleRecord currentRecord;
    /**
     * 前一条过车记录
     */
    private OrbitPassVehicleRecord earlierRecord;
    /**
     * 后一条过车记录
     */
    private OrbitPassVehicleRecord laterRecord;

    public OrbitPassVehicleRecord getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(OrbitPassVehicleRecord currentRecord) {
        this.currentRecord = currentRecord;
    }

    public OrbitPassVehicleRecord getEarlierRecord() {
        return earlierRecord;
    }

    public void setEarlierRecord(OrbitPassVehicleRecord earlierRecord) {
        this.earlierRecord = earlierRecord;
    }

    public OrbitPassVehicleRecord getLaterRecord() {
        return laterRecord;
    }

    public void setLaterRecord(OrbitPassVehicleRecord laterRecord) {
        this.laterRecord = laterRecord;
    }
}

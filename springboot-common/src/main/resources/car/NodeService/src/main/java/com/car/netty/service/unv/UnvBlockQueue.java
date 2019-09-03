package com.base.springboot.car.NodeService.src.main.java.com.car.netty.service.unv;

import com.car.netty.enums.UnvEnum;
import com.car.netty.struct.base.VehicleInfo;
import com.car.netty.struct.unv.bean.UnvProtocol;
import com.car.netty.struct.unv.bean.UnvVehicle;
import com.car.netty.struct.unv.bean.UnvVehicleAlarm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * 每个队列建议做成单例，每种业务自己创建一个单例队列, 目前的做法内存消耗过大
 */
@Component
public class UnvBlockQueue {

    /**
     * 过车数据集合写入队列
     */
    private BlockingQueue<List<UnvVehicle>> oVehicleListQueue = new LinkedBlockingQueue<List<UnvVehicle>>(UnvEnum.UNV_QUEUE);
    /**
     * 违法数据集合写入队列
    */
    private BlockingQueue<List<UnvVehicleAlarm>> oAlarmListQueue = new LinkedBlockingQueue<List<UnvVehicleAlarm>>(UnvEnum.UNV_QUEUE);
    /**
     * 宇视收包队列
     */
    private BlockingQueue<UnvProtocol> oBlockQueue = new LinkedBlockingQueue<UnvProtocol>(UnvEnum.UNV_QUEUE);
    /**
     * 过车数据写入队列
     */
    private BlockingQueue<UnvVehicle> oVehicleQueue = new LinkedBlockingQueue<UnvVehicle>(UnvEnum.UNV_QUEUE);

    /**
     * 违法数据写入队列
     */
    private BlockingQueue<UnvVehicleAlarm> oAlarmQueue = new LinkedBlockingQueue<UnvVehicleAlarm>(UnvEnum.UNV_QUEUE);

    public BlockingQueue<UnvProtocol> getoBlockQueue() {
        return oBlockQueue;
    }
    public BlockingQueue<UnvVehicle> getoVehicleQueue() {
        return oVehicleQueue;
    }
    public BlockingQueue<UnvVehicleAlarm> getoAlarmQueue() {
        return oAlarmQueue;
    }
    public BlockingQueue<List<UnvVehicle>> getoVehicleListQueue() {
        return oVehicleListQueue;
    }
    public BlockingQueue<List<UnvVehicleAlarm>> getoAlarmListQueue() {
        return oAlarmListQueue;
    }
    /**
     * 读取本地文件存放路径的队列
     */
    public BlockingQueue<String> getoFilePathQueue() {
        return  new LinkedBlockingQueue<String>(UnvEnum.UNV_QUEUE);
    }
    /**
     * 发给TMS的队列(过车)
     */
    public BlockingQueue<VehicleInfo> getoVehicleInfoQueue() {
        return new LinkedBlockingQueue<VehicleInfo>(UnvEnum.UNV_QUEUE * 5);
    }

}

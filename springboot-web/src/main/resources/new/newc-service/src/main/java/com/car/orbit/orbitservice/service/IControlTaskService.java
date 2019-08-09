package com.car.orbit.orbitservice.service;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.bo.ControlTaskBO;
import com.car.orbit.orbitservice.exception.OverControlTaskException;
import com.car.orbit.orbitservice.qo.ApproverTaskQO;
import com.car.orbit.orbitservice.qo.ControlTaskQO;
import com.car.orbit.orbitservice.vo.ControlTaskVO;
import com.car.orbit.orbitutil.exception.OrbitException;
import com.car.orbit.orbitutil.page.PageUtil;

import java.util.List;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.service
 * @ClassName: IControlTaskService
 * @Description: 布控任务接口
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 15:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 15:45
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IControlTaskService {

    /**
     * @return com.car.orbit.orbitservice.bo.ControlTaskBO
     * @method
     * @description 获取所有用于启动加载到redis缓存中的业务对象
     * @date: 2019/4/2 0002 19:35
     * @author: zks
     * @param: []
     */
    List<ControlTaskBO> getInitAllOnControlTask();

    /**
     * @return com.car.orbit.orbitservice.bo.VehicleSearchBO<com.car.orbit.orbitservice.vo.ControlTaskVO>
     * @method
     * @description 查询
     * @date: 2019/4/2 0002 15:45
     * @author: zks
     * @param: [controlTaskQO]
     */
    PageUtil<ControlTaskVO> search(ControlTaskQO controlTaskQO);

    /**
     * @return com.car.orbit.orbitservice.bo.ControlTaskBO
     * @method
     * @description 获取详情
     * @date: 2019/4/2 0002 19:35
     * @author: zks
     * @param: [taskId]
     */
    ControlTaskBO getTask(String taskId, boolean isEdit);

    /**
     * @return boolean
     * @method
     * @description 添加任务
     * @date: 2019/4/2 0002 17:33
     * @author: zks
     * @param: [bo]
     */
    void addTask(ControlTaskBO bo);

    /**
     * 校验设备是否有效
     *
     * @param deviceIdStr 需要检验的设备id列表
     * @return 是否有效
     */
    boolean validateDevice(String deviceIdStr);

    /**
     * 校验接收用户是否有效
     *
     * @param receiverIdStr 需要检验的用户id列表
     * @return 是否有效
     */
    boolean validateReceiver(String receiverIdStr);

    /**
     * @return boolean
     * @method
     * @description 修改布控任务
     * @date: 2019/4/2 0002 19:35
     * @author: zks
     * @param: [bo]
     */
    void editTask(ControlTaskBO bo);

    /**
     * @return boolean
     * @method
     * @description 添加任务前判断是否允许添加
     * @date: 2019/4/3 0003 9:49
     * @author: zks
     * @param: []
     */
    boolean check();

    /**
     * @return boolean
     * @method
     * @description 审批布控任务~
     * @date: 2019/4/3 0003 9:56
     * @author: zks
     * @param: [taskId, status, note]
     */
    boolean approvalTask(ApproverTaskQO qo) throws OrbitException;

    /**
     * @return boolean
     * @method
     * @description 结束布控任务~
     * @date: 2019/4/3 0003 9:56
     * @author: zks
     * @param: [taskId]
     */
    void overTask(JSONObject taskIdObject) throws OverControlTaskException;

    /**
     * @return boolean
     * @method
     * @description 删除布控任务~
     * @date: 2019/4/3 0003 9:56
     * @author: zks
     * @param: [taskId]
     */
    void deleteTask(JSONObject taskIdObject);

    /**
     * @return boolean
     * @method
     * @description 检查任务名称是否存在~
     * @date: 2019/4/15 0015 17:58
     * @author: zks
     * @param: [newName, taskId]
     */
    boolean checkName(String newName, String taskId);
}

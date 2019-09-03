package com.car.orbit.orbitservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.bo.ControlTaskBO;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitControlTask;
import com.car.orbit.orbitservice.entity.OrbitControlTaskDetail;
import com.car.orbit.orbitservice.entity.OrbitResDevice;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.enums.ControlTaskStatusEnum;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.exception.OverControlTaskException;
import com.car.orbit.orbitservice.mapper.OrbitControlTaskDetailMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlTaskMapper;
import com.car.orbit.orbitservice.mapper.OrbitResDeviceMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.ApproverTaskQO;
import com.car.orbit.orbitservice.qo.ControlTaskQO;
import com.car.orbit.orbitservice.service.IControlTaskService;
import com.car.orbit.orbitservice.util.redis.BrandRedis;
import com.car.orbit.orbitservice.util.redis.DevicePointRedis;
import com.car.orbit.orbitservice.util.redis.OnControlRedis;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.ControlTaskVO;
import com.car.orbit.orbitutil.exception.OrbitException;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.github.pagehelper.PageHelper;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ProjectName: orbit-parent
 * @Package: com.car.orbit.orbitservice.service.impl
 * @ClassName: ControlTaskServiceImpl
 * @Description: 布控任务实现类
 * @Author: zks
 * @CreateDate: 2019/4/2 0002 17:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/2 0002 17:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class ControlTaskServiceImpl implements IControlTaskService {

    @Autowired
    OrbitControlTaskMapper taskMapper;

    @Autowired
    OrbitControlTaskDetailMapper taskDetailMapper;

    @Autowired
    OrbitResDeviceMapper deviceMapper;

    @Autowired
    OrbitSysUserMapper userMapper;

    /**
     * @return com.car.orbit.orbitservice.bo.VehicleSearchBO<com.car.orbit.orbitservice.vo.ControlTaskVO>
     * @method
     * @description 查询
     * @date: 2019/4/2 0002 15:45
     * @author: zks
     * @param: [qo]
     */
    @Override
    public PageUtil<ControlTaskVO> search(ControlTaskQO qo) {
        PageHelper.startPage(qo.getPageNo(), qo.getPageSize());
        Example example = new Example(OrbitControlTask.class);
        Example.Criteria criteria = example.createCriteria();
        // 创建时间
        if (!StringUtils.isEmpty(qo.getStartTime()) && !StringUtils.isEmpty(qo.getEndTime())) {
            criteria.andBetween("createTime", DateUtils.getDate(qo.getStartTime()),
                    DateUtils.getDate(qo.getEndTime()));
        }
        // 任务名称--模糊
        if (!StringUtils.isEmpty(qo.getTaskName())) {
            criteria.andLike("taskName", "%" + qo.getTaskName() + "%");
        }
        // 布控任务状态
        if (qo.getTaskStatus() != null) {
            criteria.andEqualTo("status", qo.getTaskStatus());
        }
        // 申请者名称--模糊
        if (!StringUtils.isEmpty(qo.getCreateName())) {
            criteria.andLike("creator", "%" + qo.getCreateName() + "%");
        }
        // 创建者ID，布控查询时只能查询当前用户自己创建的任务
        if (qo.getSearchType() == 1) {
            OrbitSysUser currentLoginUser = OrbitSysUserRedis.getCurrentLoginUser();
            String creatorId = "";
            if (currentLoginUser != null) {
                creatorId = currentLoginUser.getId();
            }
            criteria.andEqualTo("creatorId", creatorId);
        }
        // 审批者名称--模糊
        if (!StringUtils.isEmpty(qo.getApproverName())) {
            criteria.andLike("approver", "%" + qo.getApproverName() + "%");
        }
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        example.setOrderByClause("CREATE_TIME DESC");

        List<OrbitControlTask> list = taskMapper.selectByExample(example);
        PageUtil<OrbitControlTask> pageUtil = new PageUtil<>(list);

        List<ControlTaskVO> listVo = new ArrayList<>(list.size());
        for (OrbitControlTask task : list) {
            ControlTaskVO vo = getControlTaskVO(task, false);
            listVo.add(vo);
        }
        return new PageUtil<>(pageUtil.getPageIndex(), pageUtil.getPageSize(),
                (int) pageUtil.getItemCount(), pageUtil.getPageCount(), listVo);
    }

    private ControlTaskVO getControlTaskVO(OrbitControlTask task, boolean isEdit) {
        ControlTaskVO vo = new ControlTaskVO();
        BeanUtils.copyProperties(task, vo);
        OrbitSysUser creator = userMapper.selectByPrimaryKey(task.getCreatorId());
        if (creator != null) {
            vo.setCreatorPicUrl(creator.getPhotoUrl());
            vo.setCreatorTel(creator.getPhone());
        }
        OrbitSysUser approver = userMapper.selectByPrimaryKey(task.getApproverId());
        if (approver != null) {
            vo.setApproverPicUrl(approver.getPhotoUrl());
        }
        if (isEdit) {
            /** 从Redis读取，已删除的用户不会显示 **/
            if (StringUtils.isNotEmpty(task.getReceivorId())) {
                vo.setReceivorList(OrbitSysUserRedis.getUsersByIds(task.getReceivorId()));
            }
        } else {
            /** 从MYSQL查询,忽略deleted条件 **/
            if (StringUtils.isNotBlank(task.getReceivorId())) {
                Example example = new Example(OrbitSysUser.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("id", Arrays.asList(task.getReceivorId().split(",")));
                vo.setReceivorList(userMapper.selectByExample(example));
            } else {
                vo.setReceivorList(new ArrayList<>());
            }
        }
        if (StringUtils.isNotEmpty(task.getDevices())) {
            vo.setDeviceList(DevicePointRedis.getDevicesByIds(task.getDevices()));
        }
        coverInfoToVO(task.getMessageType(), vo);
        return vo;
    }

    /**
     * @return com.car.orbit.orbitservice.bo.ControlTaskBO
     * @method
     * @description 获取详情
     * @date: 2019/4/2 0002 19:35
     * @author: zks
     * @param: [taskId]
     */
    @Override
    public ControlTaskBO getTask(String taskId, boolean isEdit) {
        OrbitControlTask task = taskMapper.selectByPrimaryKey(taskId);
        Example example = new Example(OrbitControlTaskDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", taskId);
        List<OrbitControlTaskDetail> taskDetails = taskDetailMapper.selectByExample(example);
        for (OrbitControlTaskDetail detail : taskDetails) {
            detail.setVehicleBrandName(BrandRedis.getBrandNameByCode(detail.getVehicleBrand()));
            detail.setVehicleBrandChildName(BrandRedis.getChildBrandNameByCode(detail.getVehicleBrandChild()));
        }
        ControlTaskVO vo = getControlTaskVO(task, isEdit);
        return new ControlTaskBO(vo, taskDetails);
    }

    /**
     * @return void
     * @method
     * @description 转化消息类型，将前端的boolean转化组合后的int
     * @date: 2019/4/12 0012 18:07
     * @author: zks
     * @param: []
     */
    private void coverInfoFromVO(ControlTaskVO vo) {
        int messageType = 0;
        if (vo.isVoz()) {
            messageType += 0b0010;
        }
        if (vo.isSms()) {
            messageType += 0b0001;
        }
        vo.setMessageType(messageType);
    }

    /**
     * @return void
     * @method
     * @description 转化消息类型，将组合后的int转化为前端的boolean
     * @date: 2019/4/12 0012 18:07
     * @author: zks
     * @param: []
     */
    private static void coverInfoToVO(int messageType, ControlTaskVO vo) {
        String messageTypeBinary = Integer.toBinaryString(messageType);
        char[] array = messageTypeBinary.toCharArray();
        if (array.length >= 1) {
            vo.setSms(String.valueOf(array[array.length - 1]).equalsIgnoreCase("1"));
        }
        if (array.length >= 2) {
            vo.setVoz(String.valueOf(array[array.length - 2]).equalsIgnoreCase("1"));
        }
    }

    /**
     * @return boolean
     * @method
     * @description 添加任务
     * @date: 2019/4/2 0002 17:33
     * @author: zks
     * @param: [bo]
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CONTROL, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addTask(ControlTaskBO bo) {
        OrbitControlTask task = new OrbitControlTask();
        ControlTaskVO vo = bo.getTask();
        coverInfoFromVO(vo);
        BeanUtils.copyProperties(vo, task);
        task.setId(UUIDUtils.generate());
        task.setStatus(ControlTaskStatusEnum.PENDING.getValue());
        OrbitSysUser currentLoginUser = OrbitSysUserRedis.getCurrentLoginUser();
        task.setCreator(currentLoginUser.getName());
        task.setCreatorId(currentLoginUser.getId());
        task.setCreateTime(new Date());
        task.setDeleted(HasDeleteEnum.NO.getValue());

        taskMapper.insert(task);
        insertTaskDetail(bo.getTaskDetails(), task.getId());

        /** 如果是管理员，默认审核通过 **/
        if (OrbitServiceConstant.SUPER_USERNAME.equals(currentLoginUser.getUsername())) {
            ApproverTaskQO approverTaskQO = new ApproverTaskQO();
            approverTaskQO.setTaskId(task.getId());
            approverTaskQO.setStatus(ControlTaskStatusEnum.ON_CONTROL.getValue());
            approverTaskQO.setNote("");
            approvalTask(approverTaskQO);
        }
    }

    /**
     * 校验设备是否有效
     *
     * @param deviceIdStr 需要检验的设备id列表
     * @return 是否有效
     */
    @Override
    public boolean validateDevice(String deviceIdStr) {
        String[] deviceIdArray = deviceIdStr.split(",");
        List<String> deviceIdList = new ArrayList<>(Arrays.asList(deviceIdArray));

        Example example = new Example(OrbitResDevice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", deviceIdList);
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());

        return deviceMapper.selectCountByExample(example) == deviceIdArray.length;
    }

    /**
     * 校验接收用户是否有效
     *
     * @param receiverIdStr 需要检验的用户id列表
     * @return 是否有效
     */
    @Override
    public boolean validateReceiver(String receiverIdStr) {
        String[] receiverIdArray = receiverIdStr.split(",");
        List<String> receiverIdList = new ArrayList<>(Arrays.asList(receiverIdArray));

        Example example = new Example(OrbitSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", receiverIdList);
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());

        return userMapper.selectCountByExample(example) == receiverIdArray.length;
    }

    private void insertTaskDetail(List<OrbitControlTaskDetail> details, String taskId) {
        if (details != null && details.size() > 0) {
            for (OrbitControlTaskDetail detail : details) {
                detail.setId(UUIDUtils.generate());
                detail.setTaskId(taskId);
                taskDetailMapper.insert(detail);
            }
        }
    }

    /**
     * @return boolean
     * @method
     * @description 修改布控任务
     * @date: 2019/4/2 0002 19:35
     * @author: zks
     * @param: [bo]
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CONTROL, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void editTask(ControlTaskBO bo) {
        //更新任务信息【限制只能更新如下信息】
        ControlTaskVO taskVo = bo.getTask();
        coverInfoFromVO(taskVo);
        OrbitControlTask task = taskMapper.selectByPrimaryKey(taskVo.getId());
        task.setTaskName(taskVo.getTaskName());
        task.setTaskDescription(taskVo.getTaskDescription());
        task.setTaskLevel(taskVo.getTaskLevel());
        task.setDevices(taskVo.getDevices());
        task.setMessageType(taskVo.getMessageType());
        task.setReceivorId(taskVo.getReceivorId());
        if (task.getStatus() == ControlTaskStatusEnum.NOT_PASS.getValue()) {
            task.setStatus(ControlTaskStatusEnum.PENDING.getValue());
        }
        taskMapper.updateByPrimaryKeySelective(task);

        //删除关联的details并插入本次新的详情信息
        Example example = new Example(OrbitControlTaskDetail.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", task.getId());
        taskDetailMapper.deleteByExample(example);
        insertTaskDetail(bo.getTaskDetails(), task.getId());
    }

    /**
     * @return boolean
     * @method
     * @description 添加任务前判断是否允许添加
     * @date: 2019/4/3 0003 9:49
     * @author: zks
     * @param: []
     */
    @Override
    public boolean check() {
        OrbitSysUser currentLoginUser = OrbitSysUserRedis.getCurrentLoginUser();
        Example example = new Example(OrbitControlTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("creator", currentLoginUser.getId());
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        int count = taskMapper.selectCountByExample(example);
        String tmp = PropertyReaderUtils.getProValue("controlTask.perUser.max");
        int max = 100;
        if (!StringUtil.isNullOrEmpty(tmp)) {
            max = Integer.valueOf(tmp);
        }
        if (count >= 100) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return boolean
     * @method
     * @description 审批布控任务~
     * @date: 2019/4/3 0003 9:56
     * @author: zks
     * @param: [taskId, status, note]
     */
    @Override
    public boolean approvalTask(ApproverTaskQO qo) throws OrbitException {
        OrbitControlTask task = taskMapper.selectByPrimaryKey(qo.getTaskId());
        if (task.getStatus() != ControlTaskStatusEnum.PENDING.getValue()) {
            throw new OrbitException("状态已变更，请刷新重试！");
        }
        task.setApproveNote(qo.getNote());
        task.setApproveTime(new Date());
        task.setStatus(qo.getStatus());
        OrbitSysUser currentLoginUser = OrbitSysUserRedis.getCurrentLoginUser();
        task.setApproverId(currentLoginUser.getId());
        task.setApprover(currentLoginUser.getName());
        taskMapper.updateByPrimaryKeySelective(task);
        if (qo.getStatus() == ControlTaskStatusEnum.ON_CONTROL.getValue()) {
            Example example = new Example(OrbitControlTaskDetail.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("taskId", task.getId());
            List<OrbitControlTaskDetail> list = taskDetailMapper.selectByExample(example);
            ControlTaskVO vo = new ControlTaskVO();
            BeanUtils.copyProperties(task, vo);
            ControlTaskBO bo = new ControlTaskBO(vo, list);
            //审批通过
            OnControlRedis.save(bo);
        }
        return true;
    }

    /**
     * @return boolean
     * @method
     * @description 结束布控任务~
     * @date: 2019/4/3 0003 9:56
     * @author: zks
     * @param: [taskId, status, note]
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CONTROL, actionType = OrbitServiceConstant.ACTION_OVER)
    @Override
    public void overTask(JSONObject taskIdObject) throws OverControlTaskException {
        String taskId = taskIdObject.getString("id");
        OrbitControlTask task = taskMapper.selectByPrimaryKey(taskId);
        task.setStatus(ControlTaskStatusEnum.OVER.getValue());
        taskMapper.updateByPrimaryKeySelective(task);
        if (!OnControlRedis.removeOnControlTask(taskId)) {
            throw new OverControlTaskException("Over control task error");
        }
    }

    /**
     * @return boolean
     * @method
     * @description 结束布控任务~  2删除，1未删除
     * @date: 2019/4/3 0003 9:56
     * @author: zks
     * @param: [taskId, status, note]
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_CONTROL, actionType = OrbitServiceConstant.ACTION_DELETE)
    @Override
    public void deleteTask(JSONObject taskIdObject) {
        String taskId = taskIdObject.getString("id");
        OrbitControlTask task = taskMapper.selectByPrimaryKey(taskId);
        task.setDeleted(HasDeleteEnum.YES.getValue());
        taskMapper.updateByPrimaryKeySelective(task);
    }

    /**
     * @return com.car.orbit.orbitservice.bo.ControlTaskBO
     * @method
     * @description 获取所有用于启动加载到redis缓存中的业务对象
     * @date: 2019/4/2 0002 19:35
     * @author: zks
     * @param: []
     */
    @Override
    public List<ControlTaskBO> getInitAllOnControlTask() {
        Example example = new Example(OrbitControlTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", ControlTaskStatusEnum.ON_CONTROL.getValue());
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitControlTask> tasks = taskMapper.selectByExample(example);
        if (tasks == null || tasks.size() <= 0) {
            return null;
        }
        //todo 查询出所有详情列表，MAP化
        List<OrbitControlTaskDetail> details = taskDetailMapper.getInitAllDetails();
        Map<String, List<OrbitControlTaskDetail>> detailMap = details.stream().collect(Collectors.groupingBy(OrbitControlTaskDetail::getTaskId));
        List<ControlTaskBO> list = new ArrayList<>();
        for (OrbitControlTask task : tasks) {
            ControlTaskVO vo = new ControlTaskVO();
            BeanUtils.copyProperties(task, vo);
            list.add(new ControlTaskBO(vo, detailMap.get(task.getId())));
        }
        return list;
    }

    /**
     * @return boolean true 代表存在，false代表不存在对应的名称
     * @method
     * @description 检查任务名称是否存在~
     * @date: 2019/4/15 0015 17:58
     * @author: zks
     * @param: [newName, taskId]
     */
    @Override
    public boolean checkName(String newName, String taskId) {
        Example example = new Example(OrbitControlTask.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskName", newName);
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (StringUtils.isNotEmpty(taskId)) {
            criteria.andNotEqualTo("id", taskId);
        }
        List<OrbitControlTask> tasks = taskMapper.selectByExample(example);
        if (tasks != null && tasks.size() > 0) {
            return true;
        }
        return false;
    }
}

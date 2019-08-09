package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitControlAlarmVoice;
import com.car.orbit.orbitservice.entity.OrbitSysVariable;
import com.car.orbit.orbitservice.mapper.OrbitControlAlarmVoiceMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysVariableMapper;
import com.car.orbit.orbitservice.service.ISysVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Title: SysVariableServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 系统管理接口
 * @Author: monkjavaer
 * @Data: 2019/3/8 9:06
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class SysVariableServiceImpl implements ISysVariableService {

    /**
     * 系统变量mapper
     */
    @Autowired
    private OrbitSysVariableMapper orbitSysVariableMapper;

    /**
     * 铃声mapper
     */
    @Autowired
    private OrbitControlAlarmVoiceMapper voiceMapper;

    /**
     * 系统参数保存时间获取
     *
     * @return
     */
    @Override
    public List<OrbitSysVariable> queryVariableList() {
        return orbitSysVariableMapper.selectAll();
    }

    /**
     * 更新系统保存时间
     *
     * @param orbitSysVariable 根据主键修改
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_SYSVARIABLE, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateSysVariable(OrbitSysVariable orbitSysVariable) {
        orbitSysVariableMapper.updateByPrimaryKeySelective(orbitSysVariable);
    }

    /**
     * 更新报警铃声
     *
     * @param voice 铃声实体
     */
    @LogAnnotation(actionType = OrbitServiceConstant.ACTION_UPDATE, dataType = OrbitServiceConstant.DATA_ALARM_VOICE)
    @Override
    public void updateVoice(OrbitControlAlarmVoice voice) {
        voiceMapper.updateByPrimaryKeySelective(voice);
    }

    /**
     * 查询报警铃声列表
     *
     * @return 警情1-4级对应铃声
     */
    @Override
    public List<OrbitControlAlarmVoice> queryVoiceList() {
        return voiceMapper.selectAll();
    }

    /**
     * 根据等级查询铃声
     *
     * @param level
     * @return
     */
    @Override
    public OrbitControlAlarmVoice queryVoiceByLevel(Integer level) {
        Example example = new Example(OrbitControlAlarmVoice.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("level", level);
        List<OrbitControlAlarmVoice> list = voiceMapper.selectByExample(example);
        if (list.size() <= 0) {
            return new OrbitControlAlarmVoice();
        }
        return list.get(0);
    }


}

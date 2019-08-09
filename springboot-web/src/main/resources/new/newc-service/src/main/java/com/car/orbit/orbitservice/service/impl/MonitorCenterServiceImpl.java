package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitResArea;
import com.car.orbit.orbitservice.entity.OrbitResCity;
import com.car.orbit.orbitservice.entity.OrbitSysMonitorCenter;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.mapper.OrbitResAreaMapper;
import com.car.orbit.orbitservice.mapper.OrbitResCityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysMonitorCenterMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.qo.MonitorCenterQO;
import com.car.orbit.orbitservice.service.IMonitorCenterService;
import com.car.orbit.orbitservice.vo.MonitorCenterVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Title: MonitorCenterServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 机构服务实现
 * @Author: monkjavaer
 * @Data: 2019/3/7 18:17
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class MonitorCenterServiceImpl implements IMonitorCenterService {

    /**
     * 机构mapper
     */
    @Autowired
    private OrbitSysMonitorCenterMapper monitorCenterMapper;

    @Autowired
    private OrbitResCityMapper cityMapper;

    @Autowired
    private OrbitResAreaMapper areaMapper;

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;


    /**
     * 分页查询机构
     *
     * @param monitorCenterQO
     * @return
     */
    @Override
    public PageUtil<OrbitSysMonitorCenter> queryPageList(MonitorCenterQO monitorCenterQO) {
        PageHelper.startPage(monitorCenterQO.getPageNo(), monitorCenterQO.getPageSize());
        List<OrbitSysMonitorCenter> list = monitorCenterMapper.queryListByPage(monitorCenterQO);
        return new PageUtil<>(list);
    }

    /**
     * 查询机构
     *
     * @param monitorCenterId
     * @return
     */
    @Override
    public OrbitSysMonitorCenter queryById(String monitorCenterId) {
        return monitorCenterMapper.selectByPrimaryKey(monitorCenterId);
    }

    /**
     * 机构名查重
     *
     * @param name 机构名
     * @return
     */
    @Override
    public Boolean hasMonitorCenterName(String name) {
        Example example = new Example(OrbitSysMonitorCenter.class);
        example.createCriteria().andEqualTo("name", name);
        int count = monitorCenterMapper.selectCountByExample(example);
        return count > 0;
    }

    /**
     * 查询所有机构信息
     *
     * @return
     */
    @Override
    public List<MonitorCenterVO> queryAllList() {
        return monitorCenterMapper.queryAllList();
    }

    /**
     * 添加机构
     *
     * @param orbitSysMonitorCenter 需要添加的机构信息
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_MONITOR_CENTER, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addMonitorCenter(OrbitSysMonitorCenter orbitSysMonitorCenter) {
        orbitSysMonitorCenter.setId(UUIDUtils.generate());
        orbitSysMonitorCenter.setDeleted(HasDeleteEnum.NO.getValue());
        orbitSysMonitorCenter = proccessData(orbitSysMonitorCenter);
        monitorCenterMapper.insert(orbitSysMonitorCenter);
    }

    /**
     * 更新机构
     *
     * @param orbitSysMonitorCenter 需要更新的机构信息
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_MONITOR_CENTER, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateMonitorCenter(OrbitSysMonitorCenter orbitSysMonitorCenter) {
        OrbitSysMonitorCenter oldMonitorCenter = monitorCenterMapper.selectByPrimaryKey(orbitSysMonitorCenter.getId());
        oldMonitorCenter.setLevelName(null);
        oldMonitorCenter.setCityId(null);
        oldMonitorCenter.setCityName(null);
        oldMonitorCenter.setAreaId(null);
        oldMonitorCenter.setAreaName(null);
        oldMonitorCenter.setRoadId(null);
        oldMonitorCenter.setRoadName(null);
        BeanUtils.copyProperties(orbitSysMonitorCenter, oldMonitorCenter);
        oldMonitorCenter = proccessData(oldMonitorCenter);
        monitorCenterMapper.updateByPrimaryKeySelective(oldMonitorCenter);
    }

    /**
     * 数据处理
     *
     * @param orbitSysMonitorCenter
     * @return
     */
    private OrbitSysMonitorCenter proccessData(OrbitSysMonitorCenter orbitSysMonitorCenter) {
        String levelId = orbitSysMonitorCenter.getLevelId();
        if (StringUtils.isNotBlank(levelId)) {
            //前段级别字段传入以英文逗号隔开的数据
            String[] ids = levelId.split(",");
            //如果只有一个id表示选择城市
            if (ids.length == 1) {
                OrbitResCity city = new OrbitResCity();
                city.setId(ids[0]);
                city = cityMapper.selectByPrimaryKey(city);
                //存入level级别名
                orbitSysMonitorCenter.setLevelName(city.getName());
            }
            //选择两个id表示城市和区域
            if (ids.length == 2) {
                OrbitResCity city = new OrbitResCity();
                city.setId(ids[0]);
                city = cityMapper.selectByPrimaryKey(city);
                OrbitResArea area = new OrbitResArea();
                area.setId(ids[1]);
                area = areaMapper.selectByPrimaryKey(area);
                //存入level级别名
                orbitSysMonitorCenter.setLevelName(city.getName() + "," + area.getName());
            }
        }
        return orbitSysMonitorCenter;
    }


    /**
     * 删除机构（逻辑删除，delete字段改为2）
     *
     * @param orbitSysMonitorCenter
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_MONITOR_CENTER, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitSysMonitorCenterMapper.class)
    @Override
    public void deleteMonitorCenter(OrbitSysMonitorCenter orbitSysMonitorCenter) {
        orbitSysMonitorCenter.setDeleted(HasDeleteEnum.YES.getValue());
        monitorCenterMapper.updateByPrimaryKeySelective(orbitSysMonitorCenter);
    }

    /**
     * 检测机构名是否已存在
     *
     * @param monitorCenterId
     * @param name
     * @return
     */
    public boolean checkNameDuplicate(String monitorCenterId, String name) {
        Example example = new Example(OrbitSysMonitorCenter.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name)
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (StringUtils.isNotEmpty(monitorCenterId)) {
            criteria.andNotEqualTo("id", monitorCenterId);
        }
        return monitorCenterMapper.selectCountByExample(example) > 0;
    }

    /**
     * 检测某机构下是否含有用户
     *
     * @param monitorCenterId 机构id
     * @return true-含有用户，false-不含有
     */
    public boolean hasUsers(String monitorCenterId) {
        Example example = new Example(OrbitSysUser.class);
        example.createCriteria()
                .andEqualTo("monitorCenterId", monitorCenterId)
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitSysUser> users = orbitSysUserMapper.selectByExample(example);
        return !CollectionUtils.isEmpty(users);
    }
}

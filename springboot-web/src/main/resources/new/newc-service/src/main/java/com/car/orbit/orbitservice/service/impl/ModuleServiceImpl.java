package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.entity.OrbitSysAuthority;
import com.car.orbit.orbitservice.entity.OrbitSysConfig;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.mapper.OrbitSysAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysConfigMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysRoleAuthorityMapper;
import com.car.orbit.orbitservice.mapper.OrbitSysUserMapper;
import com.car.orbit.orbitservice.service.IModuleService;
import com.car.orbit.orbitservice.vo.ModuleVO;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Title: ModuleServiceImpl
 * @Package: com.car.orbit.orbitservice.service
 * @Description: 首页模块服务实现
 * @Author: monkjavaer
 * @Date: 2019/03/09 09:54
 * @Version: V1.0
 */
@Service
////@Transactional(rollbackFor = Exception.class)
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    private OrbitSysUserMapper orbitSysUserMapper;

    @Autowired
    private OrbitSysConfigMapper orbitSysConfigMapper;

    @Autowired
    private OrbitSysRoleAuthorityMapper orbitSysRoleAuthorityMapper;

    @Autowired
    private OrbitSysAuthorityMapper orbitSysAuthorityMapper;

    /**
     * 查询首页模块设置
     *
     * @param userId
     * @return
     */
    @Override
    public List<ModuleVO> queryModuleList(String userId) {
        Set<String> authorityIdSet = new HashSet<>();
        Set<String> configIdSet = new HashSet<>();

        /**
         * 查询用户所在角色id
         */
        OrbitSysUser orbitSysUser = orbitSysUserMapper.selectByPrimaryKey(userId);
        String roleId = orbitSysUser.getRoleId();

        /**
         * 查询用户拥有权限的模块
         */
        RoleServiceImpl.getRoleAuthorityIdSet(roleId, authorityIdSet, orbitSysRoleAuthorityMapper);

        /**
         * 查询用户配置的模块
         */
        Example example2 = new Example(OrbitSysConfig.class);
        example2.createCriteria()
                .andEqualTo("userId", userId);
        OrbitSysConfig sysConfig = orbitSysConfigMapper.selectOneByExample(example2);
        if (sysConfig != null) {
            String[] idArray = sysConfig.getAuthorityIdstr().split(",");
            for (int i = 0; i < idArray.length; i++) {
                configIdSet.add(idArray[i]);
            }
        }

        List<OrbitSysAuthority> authorities = orbitSysAuthorityMapper.selectAll();
        List<ModuleVO> moduleVOList = new ArrayList<>();
        for (OrbitSysAuthority item:
             authorities) {
            ModuleVO moduleVO = new ModuleVO();
            moduleVO.setId(item.getId());
            moduleVO.setName(item.getAuthorityName());
            moduleVO.setZhName(item.getZhName());
            moduleVO.setParentNode(Integer.parseInt(item.getParentNode()));
            moduleVO.setFindPermission(authorityIdSet.contains(item.getId()));
            moduleVO.setConfig(configIdSet.contains(item.getId()));
            moduleVOList.add(moduleVO);
        }

        return moduleVOList;
    }

    /**
     * 编辑首页模块设置
     *
     * @param sysConfig
     */
    @Override
    public void editModule(OrbitSysConfig sysConfig) {
        Example example = new Example(OrbitSysConfig.class);
        example.createCriteria()
                .andEqualTo("userId", sysConfig.getUserId());
        OrbitSysConfig orbitSysConfig = orbitSysConfigMapper.selectOneByExample(example);

        if (orbitSysConfig == null) {
            sysConfig.setId(UUIDUtils.generate());
            orbitSysConfigMapper.insert(sysConfig);
        } else {
            sysConfig.setId(orbitSysConfig.getId());
            orbitSysConfigMapper.updateByPrimaryKeySelective(sysConfig);
        }
    }
}

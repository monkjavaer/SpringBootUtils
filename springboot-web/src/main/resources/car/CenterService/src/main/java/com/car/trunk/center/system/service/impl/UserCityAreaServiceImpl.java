package com.car.trunk.center.system.service.impl;

import com.car.base.common.constant.EnvConstant;
import com.car.base.common.exception.ValidateException;
import com.car.base.common.utilities.SnowflakeIdWorkerUtil;
import com.car.trunk.center.device.service.AreaService;
import com.car.trunk.center.device.service.CityService;
import com.car.trunk.center.system.service.IUserCityAreaService;
import com.car.trunk.center.system.service.UserService;
import com.car.trunk.center.vo.userCityArea.request.UserCityAreaDataVO;
import com.car.trunk.center.vo.userCityArea.request.UserCityAreaUpdateVO;
import com.car.trunk.center.vo.userCityArea.result.UserCityAreaResultVO;
import com.car.trunk.dal.device.bo.CityBO;
import com.car.trunk.dal.model.AreaEntity;
import com.car.trunk.dal.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CreateDate：2018/7/11
 * Author：monkjavaer
 * Description: 该类主要用于提供用户城市区域绑定关系服务接口
 */
@Service("UserCityAreaServiceImpl")
public class UserCityAreaServiceImpl implements IUserCityAreaService{

    /** 城市服务接口 */
    @Autowired
    private CityService cityService;
    /** 区域服务接口 */
    @Autowired
    private AreaService areaService;

    /** 系统用户最新显示关联DAO接口 */
    @Autowired
    private UserCityAreaDAO userCityAreaDAO;

    /**
     * 用户接口服务
     */
    @Autowired
    private UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(UserCityAreaServiceImpl.class);
    /**
     * 获取用户对应的城市区域关联信息
     *
     * @param userId         被操作的用户ID
     * @param userName       被操作者用户名
     * @return UserCityAreaResultVO 获取到的关联信息
     */
    @Override
    public UserCityAreaResultVO queryInfoByUserId(BigInteger userId,String userName) throws ValidateException {
        UserCityAreaResultVO userCityAreaResultVO = new UserCityAreaResultVO();
        List<UserCityAreaEntity> userCityAreaEntityList =  userCityAreaDAO.queryByUserId(userId);
        Set<BigInteger> cityChecked = new  HashSet<BigInteger>();
        Set<BigInteger> areaChecked = new  HashSet<BigInteger>();
        if(null != userCityAreaEntityList && userCityAreaEntityList.size()>0){
            for (UserCityAreaEntity userCityAreaEntity:userCityAreaEntityList) {
                cityChecked.add(userCityAreaEntity.getCityId());
                areaChecked.add(userCityAreaEntity.getAreaId());
            }
        }
        UserEntity user = userService.get(userId);
        //获取所有城市信息
        List<CityBO> cityBOList;
        //超级管理员能看所有城市数据
        if (EnvConstant.SUPERADMIN.equals(userName)){
            cityBOList  =  cityService.queryList(null);
        }else {
            cityBOList =  cityService.queryList(user.getMonitorCenterId());
        }
        //获取所有区域信息
        List<AreaEntity> areaEntityList = areaService.queryList(null);
        //组装树结构
        if(null != cityBOList && cityBOList.size()>0){
            List<UserCityAreaResultVO.Data> dataList = new ArrayList<UserCityAreaResultVO.Data>();
            for (CityBO cityBO:cityBOList) {
                UserCityAreaResultVO.Data data = userCityAreaResultVO.new Data();
                BigInteger cityIdTmp = cityBO.getId();
                data.setId(cityIdTmp);
                data.setCityCode(cityBO.getAdminRegionCode());
                data.setCityName(cityBO.getName());
                data.setMonitorCenterName(cityBO.getMonitorCenterName());
                data.setChecked(cityChecked.contains(cityIdTmp)== true?1:2);
                if(null != areaEntityList && areaEntityList.size()>0) {
                    List<UserCityAreaResultVO.Area> areaList = new ArrayList<UserCityAreaResultVO.Area>();
                    boolean hasArea = false;
                    for (AreaEntity areaEntity : areaEntityList) {
                           if(cityIdTmp.equals(areaEntity.getCityId())){
                               hasArea = true;
                               UserCityAreaResultVO.Area area =  userCityAreaResultVO.new Area();
                               area.setId(areaEntity.getId());
                               area.setAreaCode(areaEntity.getAdminRegionCode());
                               area.setAreaName(areaEntity.getName());
                               area.setChecked(areaChecked.contains(areaEntity.getId())== true?1:2);
                               areaList.add(area);
                           }
                    }
                    if(hasArea){
                        data.setAreaList(areaList);
                    }
                }
                dataList.add(data) ;
            }
            userCityAreaResultVO.setData(dataList);
         }
        return  userCityAreaResultVO;
    }

    /**
     * 获取用户对应的城市信息
     *
     * @param userId         被操作的用户ID
     * @return Set<BigInteger> 获取到的关联信息
     */
    @Override
    public Set<BigInteger> queryCheckedCityByUserId(BigInteger userId){
        return  userCityAreaDAO.queryCheckedCityByUserId(userId);
    }

    /**
     * 获取用户对应的区域信息
     *
     * @param userId         被操作的用户ID
     * @param cityId         被选择的城市ID
     * @return Set<BigInteger> 获取到的关联信息
     */
    @Override
    public Set<BigInteger> queryCheckedAreaByUserIdCityId(BigInteger userId,BigInteger cityId){
        return userCityAreaDAO.queryCheckedAreaByUserIdCityId(userId,cityId);
    }

    /**
     * 修改用户对应的城市区域关联信息
     *
     * @param userCityAreaUpdateVO 待修改的信息
     * @return boolean 修改结果
     */
    @Override
    public boolean updateBatch(UserCityAreaUpdateVO userCityAreaUpdateVO){
        List<UserCityAreaDataVO> dataList = userCityAreaUpdateVO.getData(); //帖子id和素材名称列表
        List< UserCityAreaEntity > userCityAreaEntityList = new ArrayList<UserCityAreaEntity>();
        if(null != dataList && dataList.size()>0){
            for (UserCityAreaDataVO dataTmp:dataList) {
                UserCityAreaEntity userCityAreaEntityTmp = new UserCityAreaEntity();
                userCityAreaEntityTmp.setId(SnowflakeIdWorkerUtil.generateId());
                userCityAreaEntityTmp.setUserId(userCityAreaUpdateVO.getUserId());
                userCityAreaEntityTmp.setCityId(dataTmp.getCityId());
                userCityAreaEntityTmp.setAreaId(dataTmp.getAreaId());
                userCityAreaEntityList.add(userCityAreaEntityTmp);
            }
        }
        boolean updateFlag = userCityAreaDAO.updateBatch(userCityAreaEntityList);
        return updateFlag;
    }

    /**
     * 删除用户对应的城市区域关联信息
     *
     * @param userId 用户ID
     * @return boolean 修改结果
     */
    @Override
    public void deleteByUserId(BigInteger userId){
        userCityAreaDAO.deleteByUserId(userId);
    }

    /**
     * 通过城市区域信息查询有权限用户列表
     * @param cityId 城市主键
     * @param areaId 区域主键
     * @return
     */
    @Override
    public List<String> getUserNamesByCityIdAndAreaId(BigInteger cityId, BigInteger areaId) {
        return userCityAreaDAO.getUserNamesByCityIdAndAreaId(cityId,areaId);
    }

}




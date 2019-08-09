package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklistType;
import com.car.orbit.orbitservice.entity.OrbitControlWhitelist;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.enums.StatusEnum;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.ExistInOtherModuleException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.mapper.OrbitControlBlacklistMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlBlacklistTypeMapper;
import com.car.orbit.orbitservice.qo.BlackListQO;
import com.car.orbit.orbitservice.service.IBlackListService;
import com.car.orbit.orbitservice.util.redis.*;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.tools.DateUtils;
import com.car.orbit.orbitutil.tools.MD5Util;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CreateDate：2019/3/28 <br/>
 * Author：monkjavaer <br/>
 * Description: 黑名单管理service实现类
 **/
@Service
public class BlackListServiceImpl implements IBlackListService {

    public static final int IMPORT_MAX_COLUMNS = 7;
    /** 黑名单管理mapper */
    @Autowired
    private OrbitControlBlacklistMapper blacklistMapper;

    @Autowired
    private OrbitControlBlacklistTypeMapper blacklistTypeMapper;

    /**
     * 判断车牌号是否在黑名单里面
     *
     * @param plateNumber 车牌号
     * @param plateColor 车牌颜色id
     * @return true-在 false-不在
     */
    @Override
    public boolean isInBlackList(String plateNumber, String plateColor) {
        String vid = MD5Util.encode(plateNumber + plateColor);
        return isInBlackList(vid);
    }

    /**
     * 判断车牌号是否在黑名单里面
     *
     * @param vid 车辆id
     * @return true-在 false-不在
     */
    @Override
    public boolean isInBlackList(String vid) {
        return BlacklistRedis.isInBlacklist(vid);
    }

    /**
     * 查询黑名单
     *
     * @param blackListQO
     * @return
     */
    @Override
    public PageUtil<OrbitControlBlacklist> queryPageList(BlackListQO blackListQO) {
        PageHelper.startPage(blackListQO.getPageNo(), blackListQO.getPageSize());

        Example example = new Example(OrbitControlWhitelist.class);
        example.setOrderByClause("CREATE_TIME DESC");
        Example.Criteria criteria = example.createCriteria();

        // 创建时间
        if (!StringUtils.isEmpty(blackListQO.getStartTime()) && !StringUtils.isEmpty(blackListQO.getEndTime())) {
            criteria.andBetween("createTime", DateUtils.getDate(blackListQO.getStartTime()),
                    DateUtils.getDate(blackListQO.getEndTime()));
        }
        // 车牌
        if (!StringUtils.isEmpty(blackListQO.getPlateNumber())) {
            criteria.andLike("plateNumber", "%" + blackListQO.getPlateNumber() + "%");
        }
        // 车辆类别
        if (blackListQO.getVehicleTypeList() != null && blackListQO.getVehicleTypeList().size() > 0) {
            criteria.andIn("vehicleType", blackListQO.getVehicleTypeList());
        }
        // 黑名单类型
        if (!StringUtils.isEmpty(blackListQO.getType())) {
            criteria.andEqualTo("type", blackListQO.getType());
        }
        // 是否开启
        if (blackListQO.getStatus() != null) {
            criteria.andEqualTo("status", blackListQO.getStatus());
        }
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());

        List<OrbitControlBlacklist> list = blacklistMapper.selectByExample(example);
        list.stream().forEach(blacklist -> {
            blacklist.setFullBrand(BrandRedis.getFullBrandNameByCode(blacklist.getVehicleBrand(), blacklist.getVehicleBrandChild()));
            OrbitControlBlacklistType type = blacklistTypeMapper.selectByPrimaryKey(blacklist.getType());
            if (type != null) {
                blacklist.setTypeName(type.getName());
            }
        });

        return new PageUtil<>(list);
    }

    /**
     * 添加黑名单
     *
     * @param blackList
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_BLACKLIST, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addBlackList(OrbitControlBlacklist blackList) throws IllegalParamException, ExistInOtherModuleException, DuplicateDataException {
        String plateNumber = blackList.getPlateNumber();
        String plateColor = blackList.getPlateColor();
        // 车牌号和车牌颜色不能为空
        if (StringUtils.isBlank(plateNumber) || StringUtils.isBlank(plateColor)) {
            throw new IllegalParamException("Plate number and plate color can not be empty");
        }

        // 不能存在于白名单中
        String vid = MD5Util.encode(plateNumber + plateColor);
        if (WhitelistRedis.isInWhitelist(vid)) {
            throw new ExistInOtherModuleException("Already exist in White List");
        }

        // 是否已经添加过黑名单
        if (checkDuplicate(null, blackList.getPlateNumber(), blackList.getPlateColor())) {
            throw new DuplicateDataException("Already exist in Black List");
        }

        /** 如果存在逻辑删除的记录,需要先删除,否则相同的vid主键无法写入 **/
        blacklistMapper.deleteByPrimaryKey(vid);

        blackList.setVid(vid);
        blackList.setCreateTime(new Date());
        blackList.setUpdateTime(new Date());
        blackList.setDeleted(HasDeleteEnum.NO.getValue());
        blackList.setStatus(StatusEnum.OPEN.getValue());
        blacklistMapper.insert(blackList);
        BlacklistRedis.saveVo(blackList);
    }

    /**
     * 更新黑名单
     *
     * @param blackList
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_BLACKLIST, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateBlackList(OrbitControlBlacklist blackList) {
        blackList.setUpdateTime(new Date());
        blacklistMapper.updateByPrimaryKeySelective(blackList);

        // 单独更新车辆品牌、子品牌
        String vid = blackList.getVid();
        OrbitControlBlacklist newBlackList = blacklistMapper.selectByPrimaryKey(vid);
        BlacklistRedis.saveVo(newBlackList);
    }

    /**
     * 删除黑名单
     *
     * @param blacklist 需要删除的黑名单
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_BLACKLIST, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitControlBlacklistMapper.class)
    @Override
    public void deleteBlackList(OrbitControlBlacklist blacklist) {
        OrbitControlBlacklist blacklistUpdate = new OrbitControlBlacklist();
        blacklistUpdate.setVid(blacklist.getVid());
        blacklistUpdate.setDeleted(HasDeleteEnum.YES.getValue());
        blacklistMapper.updateByPrimaryKeySelective(blacklistUpdate);
        BlacklistRedis.remove(blacklist.getVid());
    }

    /**
     * 导入黑名单
     *
     * @param sourceData
     * @return
     */
    @Override
    public Map<String, Integer> importBlackList(List sourceData) {
        Map<String, Integer> resultMap = new HashMap<>();
        Integer success = 0, fail = 0;

        for (Object item : sourceData) {
            String[] row = (String[]) item;

            // 列数有误
            if (row.length != IMPORT_MAX_COLUMNS) {
                fail = sourceData.size();
                break;
            }

            // 车牌号、车牌颜色、黑名单类型不能为空
            if (StringUtils.isEmpty(row[0]) || StringUtils.isEmpty(row[1]) || StringUtils.isEmpty(row[2])) {
                fail++;
                continue;
            }

            OrbitControlBlacklist blacklist = new OrbitControlBlacklist();

            blacklist.setPlateNumber(row[0]);
            blacklist.setPlateColor(row[1]);
            blacklist.setType(row[2]);
            blacklist.setVehicleType(row[3]);
            blacklist.setVehicleColor(row[4]);
            blacklist.setVehicleBrand(row[5]);
            blacklist.setVehicleBrandChild(row[6]);

            try {
                addBlackList(blacklist);
                success++;
            } catch (Exception e) {
                fail++;
            }
        }

        resultMap.put("success", success);
        resultMap.put("fail", fail);

        return resultMap;
    }

    /**
     * 获取黑名单类型列表
     *
     * @return
     */
    @Override
    public List<OrbitControlBlacklistType> getAllBlacklistType() {
        Example example = new Example(OrbitControlBlacklistType.class);
        example.createCriteria().andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitControlBlacklistType> blacklistTypes = blacklistTypeMapper.selectByExample(example);
        blacklistTypes.forEach(blacklistType -> blacklistType.setDeletable(checkTypeDeletable(blacklistType.getId())));
        return blacklistTypes;
    }

    /**
     * 添加黑名单类型
     *
     * @param blacklistType
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_BLACKLIST_TYPE, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addBlacklistType(OrbitControlBlacklistType blacklistType) throws DuplicateDataException {
        Example example = new Example(OrbitControlBlacklistType.class);
        example.createCriteria().andEqualTo("name", blacklistType.getName())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (blacklistTypeMapper.selectCountByExample(example) > 0) {
            throw new DuplicateDataException("Already exist same type");
        }
        blacklistType.setId(UUIDUtils.generate());
        blacklistType.setBell(1);
        blacklistType.setLevel(1);
        blacklistType.setDeleted(HasDeleteEnum.NO.getValue());
        blacklistTypeMapper.insert(blacklistType);
    }

    /**
     * 编辑黑名单类型
     *
     * @param blacklistType
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_BLACKLIST_TYPE, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void editBlacklistType(OrbitControlBlacklistType blacklistType) throws DuplicateDataException {
        Example example = new Example(OrbitControlBlacklistType.class);
        example.createCriteria().andEqualTo("name", blacklistType.getName())
                .andNotEqualTo("id", blacklistType.getId());
        if (blacklistTypeMapper.selectCountByExample(example) > 0) {
            throw new DuplicateDataException("Already exist same type");
        }
        blacklistTypeMapper.updateByPrimaryKeySelective(blacklistType);
    }

    /**
     * 删除黑名单类型
     *
     * @param blacklistType 需要删除的黑名单类型
     * @return 操作结果
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_BLACKLIST_TYPE, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitControlBlacklistTypeMapper.class)
    @Override
    public void deleteBlacklistType(OrbitControlBlacklistType blacklistType) throws RelationshipException {
        if (!checkTypeDeletable(blacklistType.getId())) {
            throw new RelationshipException("There are vehicles in this black list type");
        }
        blacklistType.setDeleted(HasDeleteEnum.YES.getValue());
        blacklistTypeMapper.updateByPrimaryKeySelective(blacklistType);
    }

    /**
     * 检测该类型是否可删除
     *
     * @param id
     * @return
     */
    private boolean checkTypeDeletable(String id) {
        Example example = new Example(OrbitControlBlacklist.class);
        example.createCriteria().andEqualTo("type", id);
        if (blacklistMapper.selectCountByExample(example) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 检查是否有重复的记录
     *
     * @param vid
     * @param plateNumber
     * @return
     */
    @Override
    public boolean checkDuplicate(String vid, String plateNumber, String plateColor) {
        Example example = new Example(OrbitControlBlacklist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("BINARY PLATE_NUMBER = ", plateNumber);
        criteria.andEqualTo("plateColor", plateColor);
        if (!StringUtils.isEmpty(vid)) {
            criteria.andNotEqualTo("vid", vid);
        }
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        return blacklistMapper.selectCountByExample(example) > 0;
    }
}
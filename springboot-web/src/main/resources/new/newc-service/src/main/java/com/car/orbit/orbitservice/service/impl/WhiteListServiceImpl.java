package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.annotation.LogAnnotation;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklistType;
import com.car.orbit.orbitservice.entity.OrbitControlWhitelist;
import com.car.orbit.orbitservice.entity.OrbitControlWhitelistType;
import com.car.orbit.orbitservice.enums.HasDeleteEnum;
import com.car.orbit.orbitservice.enums.StatusEnum;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.ExistInOtherModuleException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.mapper.OrbitControlWhitelistMapper;
import com.car.orbit.orbitservice.mapper.OrbitControlWhitelistTypeMapper;
import com.car.orbit.orbitservice.qo.WhiteListQO;
import com.car.orbit.orbitservice.service.IWhiteListService;
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
 * @Title: WhiteListServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 白名单管理服务实现类
 * @Author: monkjavaer
 * @Date: 2019/03/30 14:12
 * @Version: V1.0
 */
@Service
public class WhiteListServiceImpl implements IWhiteListService {

    public static final int IMPORT_MAX_COLUMNS = 7;

    @Autowired
    private OrbitControlWhitelistMapper whitelistMapper;

    @Autowired
    private OrbitControlWhitelistTypeMapper whitelistTypeMapper;

    /**
     * 判断是否存在于白名单中
     *
     * @param plateNumber
     * @param plateColor
     * @return
     */
    @Override
    public boolean isInWhiteList(String plateNumber, String plateColor) {
        String vid = MD5Util.encode(plateNumber + plateColor);
        return WhitelistRedis.isInWhitelist(vid);
    }

    /**
     * 查询白名单
     *
     * @param whiteListQO
     * @return
     */
    @Override
    public PageUtil<OrbitControlWhitelist> queryPageList(WhiteListQO whiteListQO) {
        PageHelper.startPage(whiteListQO.getPageNo(), whiteListQO.getPageSize());

        Example example = new Example(OrbitControlWhitelist.class);
        example.setOrderByClause("CREATE_TIME DESC");
        Example.Criteria criteria = example.createCriteria();

        // 创建时间
        if (!StringUtils.isEmpty(whiteListQO.getStartTime()) && !StringUtils.isEmpty(whiteListQO.getEndTime())) {
            criteria.andBetween("createTime", DateUtils.getDate(whiteListQO.getStartTime()),
                    DateUtils.getDate(whiteListQO.getEndTime()));
        }
        // 车牌
        if (!StringUtils.isEmpty(whiteListQO.getPlateNumber())) {
            criteria.andLike("plateNumber", "%" + whiteListQO.getPlateNumber() + "%");
        }
        // 车辆类别
        if (whiteListQO.getVehicleTypeList() != null && whiteListQO.getVehicleTypeList().size() > 0) {
            criteria.andIn("vehicleType", whiteListQO.getVehicleTypeList());
        }
        // 白名单类型
        if (!StringUtils.isEmpty(whiteListQO.getType())) {
            criteria.andEqualTo("type", whiteListQO.getType());
        }
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());

        List<OrbitControlWhitelist> list = whitelistMapper.selectByExample(example);
        list.stream().forEach(whitelist -> {
            whitelist.setFullBrand(BrandRedis.getFullBrandNameByCode(whitelist.getVehicleBrand(), whitelist.getVehicleBrandChild()));
            OrbitControlWhitelistType type = whitelistTypeMapper.selectByPrimaryKey(whitelist.getType());
            if (type != null) {
                whitelist.setTypeName(type.getName());
            }
        });

        return new PageUtil<>(list);
    }

    /**
     * 添加白名单
     *
     * @param whiteList
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_WHITELIST, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addWhiteList(OrbitControlWhitelist whiteList) throws ExistInOtherModuleException, IllegalParamException, DuplicateDataException {
        // 车牌号和车牌颜色不能为空
        String plateNumber = whiteList.getPlateNumber();
        String plateColor = whiteList.getPlateColor();
        if (StringUtils.isBlank(plateNumber) || StringUtils.isBlank(plateColor)) {
            throw new IllegalParamException("Plate number and plate color can not be empty");
        }

        // 不能存在于黑名单中
        String vid = MD5Util.encode(plateNumber + plateColor);
        if (BlacklistRedis.isInBlacklist(vid)) {
            throw new ExistInOtherModuleException("Already exist in Black List");
        }

        // 是否已经添加过白名单
        if (checkDuplicate(null, plateNumber, plateColor)) {
            throw new DuplicateDataException("Already exist in White List");
        }

        // 如果存在逻辑删除的记录,需要先删除,否则相同的vid主键无法写入
        whitelistMapper.deleteByPrimaryKey(vid);

        whiteList.setVid(vid);
        whiteList.setCreateTime(new Date());
        whiteList.setUpdateTime(new Date());
        whiteList.setDeleted(HasDeleteEnum.NO.getValue());
        whiteList.setStatus(StatusEnum.OPEN.getValue());
        whitelistMapper.insert(whiteList);
        WhitelistRedis.saveVo(whiteList);
    }

    /**
     * 更新白名单
     *
     * @param whiteList
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_WHITELIST, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void updateWhiteList(OrbitControlWhitelist whiteList) {
        whiteList.setUpdateTime(new Date());
        whitelistMapper.updateByPrimaryKeySelective(whiteList);
        OrbitControlWhitelist newWhitelist = whitelistMapper.selectByPrimaryKey(whiteList.getVid());
        WhitelistRedis.saveVo(newWhitelist);
    }

    /**
     * 删除白名单
     *
     * @param whitelist
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_WHITELIST, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitControlWhitelistMapper.class)
    @Override
    public void deleteWhiteList(OrbitControlWhitelist whitelist) {
        OrbitControlWhitelist whitelistUpdate = new OrbitControlWhitelist();
        whitelistUpdate.setVid(whitelist.getVid());
        whitelistUpdate.setDeleted(HasDeleteEnum.YES.getValue());
        whitelistMapper.updateByPrimaryKeySelective(whitelistUpdate);
        WhitelistRedis.remove(whitelist.getVid());
    }

    /**
     * 白名单导入
     *
     * @param sourceData
     * @return
     */
    @Override
    public Map<String, Integer> importWhiteList(List sourceData) {
        Map<String, Integer> resultMap = new HashMap<>();
        Integer success = 0, fail = 0;

        for (Object item : sourceData) {
            String[] row = (String[]) item;

            // 列数有误
            if (row.length != IMPORT_MAX_COLUMNS) {
                fail = sourceData.size();
                break;
            }

            // 车牌号、车牌颜色、白名单类型不能为空
            if (StringUtils.isEmpty(row[0]) || StringUtils.isEmpty(row[1]) || StringUtils.isEmpty(row[2])) {
                fail++;
                continue;
            }

            OrbitControlWhitelist whitelist = new OrbitControlWhitelist();

            whitelist.setPlateNumber(row[0]);
            whitelist.setPlateColor(row[1]);
            whitelist.setType(row[2]);
            whitelist.setVehicleType(row[3]);
            whitelist.setVehicleColor(row[4]);
            whitelist.setVehicleBrand(row[5]);
            whitelist.setVehicleBrandChild(row[6]);

            try {
                addWhiteList(whitelist);
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
     * 获取白名单类型列表
     *
     * @return
     */
    @Override
    public List<OrbitControlWhitelistType> getAllWhitelistType() {
        Example example = new Example(OrbitControlWhitelistType.class);
        example.createCriteria().andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        List<OrbitControlWhitelistType> whitelistTypes = whitelistTypeMapper.selectByExample(example);
        whitelistTypes.forEach(whitelistType -> whitelistType.setDeletable(checkTypeDeletable(whitelistType.getId())));
        return whitelistTypes;
    }

    /**
     * 添加白名单类型
     *
     * @param whitelistType
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_WHITELIST_TYPE, actionType = OrbitServiceConstant.ACTION_ADD)
    @Override
    public void addWhitelistType(OrbitControlWhitelistType whitelistType) throws DuplicateDataException {
        Example example = new Example(OrbitControlBlacklistType.class);
        example.createCriteria().andEqualTo("name", whitelistType.getName())
                .andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        if (whitelistTypeMapper.selectCountByExample(example) > 0) {
            throw new DuplicateDataException("Already exist this type");
        }
        whitelistType.setId(UUIDUtils.generate());
        whitelistType.setType("");
        whitelistType.setDeleted(HasDeleteEnum.NO.getValue());
        whitelistTypeMapper.insert(whitelistType);
    }

    /**
     * 编辑白名单类型
     *
     * @param whitelistType
     * @return
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_WHITELIST_TYPE, actionType = OrbitServiceConstant.ACTION_UPDATE)
    @Override
    public void editWhitelistType(OrbitControlWhitelistType whitelistType) throws DuplicateDataException {
        Example example = new Example(OrbitControlWhitelistType.class);
        example.createCriteria().andEqualTo("name", whitelistType.getName())
                .andNotEqualTo("id", whitelistType.getId());
        if (whitelistTypeMapper.selectCountByExample(example) > 0) {
            throw new DuplicateDataException("Already exist this type");
        }
        whitelistTypeMapper.updateByPrimaryKeySelective(whitelistType);
    }

    /**
     * 删除白名单类型
     *
     * @param whitelistType 需要删除的白名单类型
     * @return 是否成功
     */
    @LogAnnotation(dataType = OrbitServiceConstant.DATA_WHITELIST_TYPE, actionType = OrbitServiceConstant.ACTION_DELETE, mapperType = OrbitControlWhitelistTypeMapper.class)
    @Override
    public void deleteWhitelistType(OrbitControlWhitelistType whitelistType) throws RelationshipException {
        if (!checkTypeDeletable(whitelistType.getId())) {
            throw new RelationshipException("There are vehicles in this white list type");
        }
        whitelistType.setDeleted(HasDeleteEnum.YES.getValue());
        whitelistTypeMapper.updateByPrimaryKeySelective(whitelistType);
    }

    /**
     * 检查是否有重复的记录
     *
     * @param vid
     * @param plateNumber
     * @return
     */
    private boolean checkDuplicate(String vid, String plateNumber, String plateColor) {
        Example example = new Example(OrbitControlWhitelist.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("BINARY PLATE_NUMBER = ", plateNumber);
        criteria.andEqualTo("plateColor", plateColor);
        if (!StringUtils.isEmpty(vid)) {
            criteria.andNotEqualTo("vid", vid);
        }
        criteria.andEqualTo("deleted", HasDeleteEnum.NO.getValue());
        return whitelistMapper.selectCountByExample(example) > 0;
    }

    /**
     * 检测该类型是否可删除
     *
     * @param id
     * @return
     */
    private boolean checkTypeDeletable(String id) {
        Example example = new Example(OrbitControlWhitelist.class);
        example.createCriteria().andEqualTo("type", id);
        if (whitelistMapper.selectCountByExample(example) > 0) {
            return false;
        }
        return true;
    }
}

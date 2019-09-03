package com.m.gis.springboot.service;

import com.m.gis.springboot.common.GisServiceConstants;
import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import com.m.gis.springboot.district.common.GisDistrict;
import com.m.gis.springboot.district.common.GisDistrictType;
import com.m.gis.springboot.exception.GisDistrictServiceThrowableException;
import com.m.gis.springboot.geo.base.common.GisCoordinate;
import com.m.gis.springboot.geo.base.common.GisPreconditions;
import com.m.gis.springboot.vo.GisDistrictNameVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: GisDistrictServiceImpl
 * @Package: com.m.gis.springboot.service
 * @Description: TODO（添加描述）
 * @Author: monkjavaer
 * @Data: 2018/6/13
 * @Version: V1.0
 */
@Service
public class GisDistrictServiceImpl implements GisDistrictService{
    private static final Logger logger = LoggerFactory.getLogger(GisDistrictServiceImpl.class);

    @Autowired
    private GisAbstractDistrictTypeUtil gisDistrictTypeUtil;

    /**
     * 缓存服务
     */
    @Autowired
    private GisDistrictCacheService gisDistrictCacheService;

    /**
     * 异步服务，用于异步加载数据库中的行政信息
     */
    @Autowired
    private GisAsyncDistrictService gisAsyncDistrictService;

    /**
     * 启动时，异步加载各级行政区域的缓冲信息
     */
    @PostConstruct
    public void init(){
        gisAsyncDistrictService.initDistrict();
    }

    @Override
    public GisDistrict getDistrictById(String districtTypeCode, Integer gid) {
        GisDistrictType type = gisDistrictTypeUtil.getDistrictType(districtTypeCode);
        return getDistrictById(type,gid);
    }

    @Override
    public GisDistrict getDistrictById(GisDistrictType type, Integer gid) {
        List<GisDistrict> districts = getDistrict(type);
        for(GisDistrict item:districts){
            if(item.getId() == gid)
                return item;
        }
        return null;
    }

    @Override
    public List<GisDistrict> getDistrict(GisDistrictType type) {
        return  gisDistrictCacheService.getGisDistrictByType(type);
    }

    @Override
    public List<GisDistrict> getDistrict(GisDistrictType type, String parent) {
        GisPreconditions.checkArgument(!(gisDistrictTypeUtil.isHighestDistrictType(type)),"getDistrict function errors, param {type} can not be highest district type code.");

        //如果是null或者空字符串的国家行政区域编码，统一转换为空字符串，以便筛选国家下一级的行政区域
        parent = StringUtils.isEmpty(parent)?"":parent;

        List<GisDistrict> districts = getDistrict(type);

        List<GisDistrict> list = new ArrayList<>();
        for(GisDistrict item:districts){
            if(gisDistrictTypeUtil.getParentDistrictCode(item.getDistrictCode()).equals(parent)){
                list.add(item);
            }
        }
        return list;
    }

    @Override
    public List<GisDistrictNameVO> getDistrictItem(String type, String parent) {
        GisDistrictType districtType = gisDistrictTypeUtil.getDistrictType(type);

        List<GisDistrict> districts = null;
        List<GisDistrictNameVO> districtVos = new ArrayList<>();

        //获取符合条件的行政区域列表
        //如果为最高级行政区域，或者parent为空
        if(gisDistrictTypeUtil.isHighestDistrictType(districtType) || StringUtils.isEmpty(parent))
            districts = getDistrict(districtType);
        else
            districts = getDistrict(districtType,parent);

        for(GisDistrict item:districts){
            districtVos.add(new GisDistrictNameVO(item.getDistrictCode(),item.getName()));
        }

        return districtVos;
    }

    @Override
    public GisDistrict getDistrict(String districtCode) throws GisDistrictServiceThrowableException {
        if (StringUtils.isBlank(districtCode)){
            districtCode = GisServiceConstants.NATION_THREE;
        }
        GisDistrictType districtType = gisDistrictTypeUtil.getDistrictTypeByDistrictCode(districtCode);

        List<GisDistrict> districts = gisDistrictCacheService.getGisDistrictByType(districtType);

        //如果为空或者null，返回国家级行政区域
        if(gisDistrictTypeUtil.isHighestDistrictType(districtType))
            return districts.get(0);

        //如果为其它行政级别，则遍历查询等于districtCode的行政区域
        for(GisDistrict item:districts){
            if(item.getDistrictCode().equals(districtCode))
                return item;
        }

        //如果没有包含的，则抛出异常给调用方处理
        throw new GisDistrictServiceThrowableException(String.format("no district is found with DistrictCode = {%s}", districtCode));
    }

    @Override
    public GisDistrict getDistrict(GisCoordinate coordinate,GisDistrictType type) throws GisDistrictServiceThrowableException {
        GisPreconditions.checkNotNull(coordinate, "getDistrictCode function errors, param {coordinate} must not be null.");

        //获取指定行政级别的所有行政区域，用于遍历获取该位置所在的行政编码
        List<GisDistrict> districts = gisDistrictCacheService.getGisDistrictByType(type);

        for(GisDistrict item:districts){
            if(item.contains(coordinate))
                return item;
        }
        //如果没有包含的，则抛出异常给调用方处理
//        throw new GisDistrictServiceThrowableException(String.format("no district is found with GisCoordinate = {%s} and GisDistrictType = {%s}", coordinate.toString(),type.toString()));
        return null;
    }


    /**
     * 根据经纬度获取该点对应的行政区域编码(从最低级依次查找,谁先找到就用那一级的编码)
     * @param coordinate
     * @return
     */
    @Override
    public String getDistrictCode(GisCoordinate coordinate){
        //最低级别的行政区域编码是最全的，
        GisDistrict district = null;
        try {
            //调整策略：从最低级依次查找,谁先找到就用那一级的编码
            for (int i = 1;i <= gisDistrictTypeUtil.getDistrictLevels();i++){
                district = getDistrict(coordinate, gisDistrictTypeUtil.getLowerDistrictType(i));
                if (district != null) {
                    break;
                }
            }
            return district != null ? district.getDistrictCode() : "";
        } catch (GisDistrictServiceThrowableException e) {
            //如果点不在行政范围内，则返回空值
            return "";
        }
    }

    /**
     * 根据行政区划代码获取行政区划全名
     *
     *
     * @param districtCode 乡镇区划编码
     * @return 国,省,市,区...
     */
    @Override
    public String getFullNameByDistrictCode(String districtCode) {

        //新版本行政区划数据编码规则：一级行政区划编码（3位）二级行政区划编码（3位）三级行政区划编码（3位）四级行政区划编码（3位）
        //实例：中国(001)四川省(008)成都市(001)双流区(002)=001008001002
        int digit = 3;

        StringBuilder addressBuilder = new StringBuilder();
        try {
            //districtType 在 district.xml配置文件
            GisDistrictType districtType = gisDistrictTypeUtil.getDistrictTypeByDistrictCode(districtCode);
            //传入code的级别：1-城市，2-省。。。。。
            int level = Integer.parseInt(districtType.getType());

            //不循环自己，例如：中国(001)四川省(008)成都市(001)双流区(002)=001008001002只循环中国(001)四川省(008)成都市(001)
            for (int i = 1; i <= level - 1; i++) {
                StringBuilder code = new StringBuilder(districtCode.substring(0, i * digit));
                //动态计算拼接多少个0
                for (int j = 0; j < districtCode.length() - (i * digit); j++) {
                    code.append("0");
                }
                //查询name并以“,”间隔
                GisDistrict gisDistrict = getDistrict(code.toString());
                addressBuilder.append(gisDistrict.getName()).append(",");
            }
            //查询传入code自己的name
            GisDistrict subGisDistrict = getDistrict(districtCode);
            addressBuilder.append(subGisDistrict.getName());
        } catch (GisDistrictServiceThrowableException e) {
            e.printStackTrace();
        }
        return addressBuilder.toString();
    }

}

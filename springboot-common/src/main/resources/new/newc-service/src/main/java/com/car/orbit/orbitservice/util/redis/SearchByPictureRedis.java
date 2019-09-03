package com.car.orbit.orbitservice.util.redis;


import com.car.orbit.orbitservice.vo.OneVehicleFileVO;
import com.car.orbit.orbitservice.vo.VehicleSearchVO;
import com.car.orbit.orbitservice.vo.VehicleTraitVo;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 以图搜车缓存的top100条数据
 * 并支持对应的分类计算并缓存
 * 这里的【列表】代表聚合上层集合或一车一档的档案集合，不是最底层的过车记录
 * 这里的【记录】代表最底层的过车记录
 *
 * @author zks
 */
@Component
public class SearchByPictureRedis extends BaseBusinessRedis {

    private static String prefixKey = "SearchByImg:";
    private static String keyList = "KeyList";
    private static String all = "all";
    private static String point = "point";
    private static String brand = "brand";
    private static String oneFile = "oneFile";
    private static int expireSeconds = 300;

    /**
     * 获取非聚合的过车记录key
     */
    private static String getAllRecordKey(String key) {
        return prefixKey + key + SPLIT + all;
    }

    /**
     * 获取非聚合条件下开启一车一档的列表key
     */
    private static String getAllFileKey(String key) {
        return prefixKey + key + SPLIT + all + SPLIT + oneFile;
    }

    /**
     * 获取非聚合条件下某个具体车辆一车一档的过车记录key
     */
    private static String getAllFileRecordKey(String key, String vid) {
        return prefixKey + key + SPLIT + all + SPLIT + oneFile + SPLIT + vid;
    }

    /**
     * 获取按点位聚合的列表key
     */
    private static String getPointKey(String key) {
        return prefixKey + key + SPLIT + point;
    }

    /**
     * 获取按车辆类型聚合的列表key
     */
    private static String getBrandKey(String key) {
        return prefixKey + key + SPLIT + brand;
    }

    /**
     * 获取按照点位聚合，未开启一车一档的记录key
     */
    private static String getPointRecordKey(String key, String pointId) {
        return prefixKey + key + SPLIT + point + SPLIT + pointId;
    }

    /**
     * 获取按照车辆类型聚合，未开启一车一档的记录key
     */
    private static String getBrandRecordKey(String key, String brandId) {
        return prefixKey + key + SPLIT + brand + brandId;
    }

    /**
     * 获取按照点位聚合，并开启一车一档列表key
     */
    private static String getPointFileKey(String key, String pointId) {
        return prefixKey + key + SPLIT + point + SPLIT + pointId + SPLIT + oneFile;
    }

    /**
     * 获取按照车辆类型聚合，并开启一车一档列表key
     */
    private static String getBrandFileKey(String key, String brandId) {
        return prefixKey + key + SPLIT + brand + brandId + SPLIT + oneFile;
    }

    /**
     * 获取某个点位聚合后某个具体车辆一车一档的记录key
     */
    private static String getPointFileRecordKey(String key, String pointId, String vid) {
        return prefixKey + key + SPLIT + point + SPLIT + pointId + SPLIT + oneFile + SPLIT + vid;
    }

    /**
     * 获取某个车辆类型聚合后某个具体车辆一车一档的记录key
     */
    private static String getBrandFileRecordKey(String key, String brandId, String vid) {
        return prefixKey + key + SPLIT + brand + brandId + SPLIT + vid;
    }

    /**
     * 添加key对应的所有缓存的分类键值
     */
    private void addKeyList(String key, String addkey) {
        List<String> cacheKeys = getKeyList(key);
        if (!StringUtil.isNullOrEmpty(key)) {
            if (cacheKeys == null) {
                cacheKeys = new ArrayList<>();
            }
            cacheKeys.add(addkey);
            redisClient.setObjectEx(prefixKey + key + SPLIT + keyList, cacheKeys, expireSeconds);
        }
    }

    /**
     * 获取查询key对应的各个reids分类缓存Key
     */
    private List<String> getKeyList(String key) {
        List<String> cacheKeys = new ArrayList<>();
        return (List<String>) redisClient.getObject(prefixKey + key + SPLIT + keyList, cacheKeys);
    }

    // ========================================下面是对11种情况分别进行保存===============================
    private void saveAllRecordWithPage(String key, List<VehicleTraitVo> records) {
        redisClient.saveListWithPageEx(getAllRecordKey(key), records, expireSeconds);
        addKeyList(key, getAllRecordKey(key));
    }

    private void saveAllFileWithPage(String key, List<OneVehicleFileVO> records) {
        redisClient.saveListWithPageEx(getAllFileKey(key), records, expireSeconds);
        addKeyList(key, getAllFileKey(key));
    }

    private void saveAllFileRecordWithPage(String key, String vid, List<VehicleTraitVo> records) {
        redisClient.saveListWithPageEx(getAllFileRecordKey(key, vid), records, expireSeconds);
        addKeyList(key, getAllFileRecordKey(key, vid));
    }

    private void savePointWithPage(String key, List<VehicleSearchVO> records) {
        redisClient.saveListWithPageEx(getPointKey(key), records, expireSeconds);
        addKeyList(key, getPointKey(key));
    }

    private void saveBrandWithPage(String key, List<VehicleSearchVO> records) {
        redisClient.saveListWithPageEx(getBrandKey(key), records, expireSeconds);
        addKeyList(key, getBrandKey(key));
    }

    private void savePointRecordWithPage(String key, String pointId, List<VehicleTraitVo> records) {
        redisClient.saveListWithPageEx(getPointRecordKey(key, pointId), records, expireSeconds);
        addKeyList(key, getPointRecordKey(key, pointId));
    }

    private void saveBrandRecordWithPage(String key, String brandId, List<VehicleTraitVo> records) {
        redisClient.saveListWithPageEx(getBrandRecordKey(key, brandId), records, expireSeconds);
        addKeyList(key, getBrandRecordKey(key, brandId));
    }

    private void savePointFileWithPage(String key, String pointId, List<OneVehicleFileVO> records) {
        redisClient.saveListWithPageEx(getPointFileKey(key, pointId), records, expireSeconds);
        addKeyList(key, getPointFileKey(key, pointId));
    }

    private void saveBrandFileWithPage(String key, String brandId, List<OneVehicleFileVO> records) {
        redisClient.saveListWithPageEx(getBrandFileKey(key, brandId), records, expireSeconds);
        addKeyList(key, getBrandFileKey(key, brandId));
    }

    private void savePointFileRecordWithPage(String key, String pointId, String vid, List<VehicleTraitVo> records) {
        redisClient.saveListWithPageEx(getPointFileRecordKey(key, pointId, vid), records, expireSeconds);
        addKeyList(key, getPointFileRecordKey(key, pointId, vid));
    }

    private void saveBrandFileRecordWithPage(String key, String brandId, String vid, List<VehicleTraitVo> records) {
        redisClient.saveListWithPageEx(getBrandFileRecordKey(key, brandId, vid), records, expireSeconds);
        addKeyList(key, getBrandFileRecordKey(key, brandId, vid));
    }

    // ========================================下面是对11种情况获取分页方法===============================
    public List<VehicleTraitVo> getAllRecordWithPage(String key, int start, int size) {
        return redisClient.getListWithPage(getAllRecordKey(key), start * size, size, VehicleTraitVo.class);
    }

    public int getAllRecordSize(String key) {
        return redisClient.getTotalPageSize(getAllRecordKey(key));
    }

    public List<OneVehicleFileVO> getAllFileWithPage(String key, int start, int size) {
        return redisClient.getListWithPage(getAllFileKey(key), start * size, size, OneVehicleFileVO.class);
    }

    public int getAllFileSize(String key) {
        return redisClient.getTotalPageSize(getAllFileKey(key));
    }

    public List<VehicleTraitVo> getAllFileRecordWithPage(String key, String vid, int start, int size) {
        return redisClient.getListWithPage(getAllFileRecordKey(key, vid), start * size, size, VehicleTraitVo.class);
    }

    public int getAllFileRecordSize(String key, String vid) {
        return redisClient.getTotalPageSize(getAllFileRecordKey(key, vid));
    }

    public List<VehicleSearchVO> getPointWithPage(String key, int start, int size) {
        return redisClient.getListWithPage(getPointKey(key), start * size, size, VehicleSearchVO.class);
    }

    public int getPointSize(String key) {
        return redisClient.getTotalPageSize(getPointKey(key));
    }

    public List<VehicleSearchVO> getBrandWithPage(String key, int start, int size) {
        return redisClient.getListWithPage(getBrandKey(key), start * size, size, VehicleSearchVO.class);
    }

    public int getBrandSize(String key) {
        return redisClient.getTotalPageSize(getBrandKey(key));
    }

    public List<VehicleTraitVo> getPointRecordWithPage(String key, String pointId, int start, int size) {
        return redisClient.getListWithPage(getPointRecordKey(key, pointId), start * size, size, VehicleTraitVo.class);
    }

    public int getPointRecordSize(String key, String pointId) {
        return redisClient.getTotalPageSize(getPointRecordKey(key, pointId));
    }

    public List<VehicleTraitVo> getBrandRecordWithPage(String key, String brandId, int start, int size) {
        return redisClient.getListWithPage(getBrandRecordKey(key, brandId), start * size, size, VehicleTraitVo.class);
    }

    public int getBrandRecordSize(String key, String brandId) {
        return redisClient.getTotalPageSize(getBrandRecordKey(key, brandId));
    }

    public List<OneVehicleFileVO> getPointFileWithPage(String key, String pointId, int start, int size) {
        return redisClient.getListWithPage(getPointFileKey(key, pointId), start * size, size, OneVehicleFileVO.class);
    }

    public int getPointFileSize(String key, String pointId) {
        return redisClient.getTotalPageSize(getPointFileKey(key, pointId));
    }

    public List<OneVehicleFileVO> getBrandFileWithPage(String key, String brandId, int start, int size) {
        return redisClient.getListWithPage(getBrandFileKey(key, brandId), start * size, size, OneVehicleFileVO.class);
    }

    public int getBrandFileSize(String key, String brandId) {
        return redisClient.getTotalPageSize(getBrandFileKey(key, brandId));
    }

    public List<VehicleTraitVo> getPointFileRecordWithPage(String key, String pointId, String vid, int start, int size) {
        return redisClient.getListWithPage(getPointFileRecordKey(key, pointId, vid), start * size, size, VehicleTraitVo.class);
    }

    public int getPointFileRecordSize(String key, String pointId, String vid) {
        return redisClient.getTotalPageSize(getPointFileRecordKey(key, pointId, vid));
    }

    public List<VehicleTraitVo> getBrandFileRecordWithPage(String key, String brandId, String vid, int start, int size) {
        return redisClient.getListWithPage(getBrandFileRecordKey(key, brandId, vid), start * size, size, VehicleTraitVo.class);
    }

    public int getBrandFileRecordSize(String key, String brandId, String vid) {
        return redisClient.getTotalPageSize(getBrandFileRecordKey(key, brandId, vid));
    }

    /**
     * check查询的KEY值是否在redis中有缓存
     * 以全量数据是否存在标识是否存在KEY
     */
    public boolean checkKeyExist(String searchKey) {
        return redisClient.checkKeyExist(getAllRecordKey(searchKey));
    }

    /**
     * 通过查询key获取缓存中分类的各个cacheKeys值，并更新所有cacheKeys的超时时间
     *
     * @param key
     */
    public void expireAll(String key) {
        List<String> cacheKeys = getKeyList(key);
        if (cacheKeys != null && cacheKeys.size() > 0) {
            for (String cache : cacheKeys) {
                redisClient.expire(cache, expireSeconds);
            }
        }
    }

    /**
     * 保存以图搜图相关的各个redis数据
     *
     * @param key
     * @param records
     * @return
     */
    public boolean saveAndGroupSearchResult(String key, List<VehicleTraitVo> records) {
        //非聚合状态下没有选择一车一档
        saveAllRecordWithPage(key, records);

        //非聚合状态选择了一车一档
        Map<String, List<VehicleTraitVo>> groupByVid = records.stream().collect(Collectors.groupingBy(VehicleTraitVo::getVid));
        List<OneVehicleFileVO> saveAllFileList = new ArrayList<>();
        for (String vid : groupByVid.keySet()) {
            if (StringUtils.isEmpty(vid)) {
                continue;
            }
            List<VehicleTraitVo> listVo = groupByVid.get(vid);
            if (listVo != null && listVo.size() > 0) {
                VehicleTraitVo vo = listVo.stream().collect(Collectors.maxBy(Comparator.comparing(VehicleTraitVo::getRet))).orElse(listVo.get(0));
                OneVehicleFileVO fileVo = new OneVehicleFileVO(vid, vo.getPlateNumber(), Long.valueOf(listVo.size()), vo.getPhotoUrl(), vo.getRet());
                saveAllFileList.add(fileVo);
                //保存每一个档案下面对应的过车记录
                saveAllFileRecordWithPage(key, vid, listVo);
            }
        }
        //保存一车一档列表
        List<OneVehicleFileVO> sortSaveAllFileList = saveAllFileList.stream().sorted(Comparator.comparing(OneVehicleFileVO::getMaxRet).reversed()).collect(Collectors.toList());
        saveAllFileWithPage(key, sortSaveAllFileList);

        //按照点位聚合
        Map<String, List<VehicleTraitVo>> groupByPoint = records.stream().collect(Collectors.groupingBy(VehicleTraitVo::getDeviceId));
        List<VehicleSearchVO> savePointList = new ArrayList<>();
        for (String pointId : groupByPoint.keySet()) {
            List<VehicleTraitVo> savePointRecordList = groupByPoint.get(pointId);
            if (savePointRecordList != null && savePointRecordList.size() > 0) {
                List<OneVehicleFileVO> savePointFileList = new ArrayList<>();
                //在点位基础上进行一车一档的分组
                Map<String, List<VehicleTraitVo>> groupByCar = savePointRecordList.stream().collect(Collectors.groupingBy(VehicleTraitVo::getVid));
                for (String vid : groupByCar.keySet()) {
                    if (StringUtils.isEmpty(vid)) {
                        continue;
                    }
                    List<VehicleTraitVo> listVo = groupByCar.get(vid);
                    if (listVo != null && listVo.size() > 0) {
                        VehicleTraitVo vo = listVo.stream().collect(Collectors.maxBy(Comparator.comparing(VehicleTraitVo::getRet))).orElse(listVo.get(0));
                        OneVehicleFileVO fileVo = new OneVehicleFileVO(vid, vo.getPlateNumber(), Long.valueOf(listVo.size()), vo.getPhotoUrl(), vo.getRet());
                        savePointFileList.add(fileVo);
                        //保存每一个档案下面对应的过车记录
                        savePointFileRecordWithPage(key, pointId, vid, listVo);
                    }
                }
                //保存点位聚合选择了一车一档列表
                List<OneVehicleFileVO> sortSavePointFileList = savePointFileList.stream().sorted(Comparator.comparing(OneVehicleFileVO::getMaxRet).reversed()).collect(Collectors.toList());
                savePointFileWithPage(key, pointId, sortSavePointFileList);
            }
            //保存点位聚合没有选择一车一档时的过车记录
            savePointRecordWithPage(key, pointId, savePointRecordList);

            VehicleSearchVO pointVo = new VehicleSearchVO(pointId, DevicePointRedis.getNameByCode(pointId), Long.valueOf(savePointRecordList.size()));
            savePointList.add(pointVo);
        }
        //保存顶层列表【按照点位聚合的数据】
        savePointWithPage(key, savePointList);

        //按照车辆型号类型聚合
        Map<String, List<VehicleTraitVo>> groupByBrand = records.stream().collect(Collectors.groupingBy(VehicleTraitVo::getVehicleBrandChild));
        List<VehicleSearchVO> saveBrandList = new ArrayList<>();
        for (String childBrandId : groupByBrand.keySet()) {
            List<VehicleTraitVo> saveBrandRecordList = groupByBrand.get(childBrandId);
            if (saveBrandRecordList != null && saveBrandRecordList.size() > 0) {
                List<OneVehicleFileVO> saveBrandFileList = new ArrayList<>();
                //在点位基础上进行一车一档的分组
                Map<String, List<VehicleTraitVo>> groupByCar = saveBrandRecordList.stream().collect(Collectors.groupingBy(VehicleTraitVo::getVid));
                for (String vid : groupByCar.keySet()) {
                    if (StringUtils.isEmpty(vid)) {
                        continue;
                    }
                    List<VehicleTraitVo> listVo = groupByCar.get(vid);
                    if (listVo != null && listVo.size() > 0) {
                        VehicleTraitVo vo = listVo.stream().collect(Collectors.maxBy(Comparator.comparing(VehicleTraitVo::getRet))).orElse(listVo.get(0));
                        OneVehicleFileVO fileVo = new OneVehicleFileVO(vid, vo.getPlateNumber(), Long.valueOf(listVo.size()), vo.getPhotoUrl(), vo.getRet());
                        saveBrandFileList.add(fileVo);
                        //保存每一个档案下面对应的过车记录
                        saveBrandFileRecordWithPage(key, childBrandId, vid, listVo);
                    }
                }
                //保存点位聚合选择了一车一档列表
                List<OneVehicleFileVO> sortSaveBrandFileList = saveBrandFileList.stream().sorted(Comparator.comparing(OneVehicleFileVO::getMaxRet).reversed()).collect(Collectors.toList());
                saveBrandFileWithPage(key, childBrandId, sortSaveBrandFileList);
            }
            //保存点位聚合没有选择一车一档时的过车记录
            saveBrandRecordWithPage(key, childBrandId, saveBrandRecordList);

            VehicleSearchVO brandVo = new VehicleSearchVO(childBrandId, Long.valueOf(saveBrandRecordList.size()));
            saveBrandList.add(brandVo);
        }
        //保存顶层列表【按照点位聚合的数据】
        saveBrandWithPage(key, saveBrandList);

        //保存
        return true;
    }

}

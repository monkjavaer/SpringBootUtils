package com.car.orbit.orbitservice.service.impl;

import com.car.orbit.orbitservice.entity.OrbitVideoFileFolder;
import com.car.orbit.orbitservice.mapper.OrbitVideoFilefolderMapper;
import com.car.orbit.orbitservice.service.IVideoFileFolderService;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Title: VideoFileFolderServiceImpl
 * @Package: com.car.orbit.orbitservice.service.impl
 * @Description: 视频文件夹服务实现类
 * @Author: monkjavaer
 * @Date: 2019/07/02 15:18
 * @Version: V1.0
 */
@Service
public class VideoFileFolderServiceImpl implements IVideoFileFolderService {

    @Autowired
    OrbitVideoFilefolderMapper folderMapper;

    /**
     * 查询文件夹列表
     *
     * @return
     */
    @Override
    public List<OrbitVideoFileFolder> queryAllList() {
        return folderMapper.selectAll();
    }

    /**
     * 添加文件夹
     *
     * @param fileFolder
     * @return
     */
    @Override
    public boolean addFolder(OrbitVideoFileFolder fileFolder) {
        if (checkFolderNameExist(fileFolder.getName(), fileFolder.getId())) {
            return false;
        }
        fileFolder.setId(UUIDUtils.generate());
        folderMapper.insert(fileFolder);
        return true;
    }

    /**
     * 编辑文件夹
     *
     * @param fileFolder
     * @return
     */
    @Override
    public boolean updateFolder(OrbitVideoFileFolder fileFolder) {
        if (checkFolderNameExist(fileFolder.getName(), fileFolder.getId())) {
            return false;
        }
        folderMapper.updateByPrimaryKey(fileFolder);
        return true;
    }

    /**
     * 删除文件夹
     *
     * @param fileFolder
     */
    @Override
    public void deleteFolder(OrbitVideoFileFolder fileFolder) {

    }

    /**
     * 检查文件夹名字是否存在
     *
     * @param folderName
     * @param folderId
     * @return
     */
    private boolean checkFolderNameExist(String folderName, String folderId) {
        Example example = new Example(OrbitVideoFileFolder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", folderName);
        if (folderId != null) {
            criteria.andNotEqualTo("id", folderId);
        }
        return folderMapper.selectCountByExample(example) > 0;
    }
}

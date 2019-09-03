package com.car.orbit.orbitservice.service;

import com.car.orbit.orbitservice.entity.OrbitVideoFileFolder;

import java.util.List;

/**
 * 视频文件夹服务接口
 */
public interface IVideoFileFolderService {

    /**
     * 查询文件夹列表
     *
     * @return
     */
    List<OrbitVideoFileFolder> queryAllList();

    /**
     * 添加文件夹
     *
     * @param fileFolder
     * @return
     */
    boolean addFolder(OrbitVideoFileFolder fileFolder);

    /**
     * 编辑文件夹
     *
     * @param fileFolder
     * @return
     */
    boolean updateFolder(OrbitVideoFileFolder fileFolder);

    /**
     * 删除文件夹
     *
     * @param fileFolder
     */
    void deleteFolder(OrbitVideoFileFolder fileFolder);
}

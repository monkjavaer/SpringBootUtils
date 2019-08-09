package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.entity.OrbitVideoFileFolder;
import com.car.orbit.orbitservice.service.IVideoFileFolderService;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: VideoFileFolderController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 历史视频文件夹Controller
 * @Author: monkjavaer
 * @Date: 2019/07/02 15:21
 * @Version: V1.0
 */
@RestController
@RequestMapping("/videoFolder")
public class VideoFileFolderController {

    @Autowired
    IVideoFileFolderService fileFolderService;

    /**
     * 查询文件夹列表
     *
     * @return
     */
    @RequestMapping(value = "/queryFolders", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList() {
        List<OrbitVideoFileFolder> list = fileFolderService.queryAllList();
        return ResultUtil.success(list);
    }

    /**
     * 创建文件夹
     *
     * @param folder
     * @return
     */
    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult createFolder(@RequestBody OrbitVideoFileFolder folder) {
        boolean res = fileFolderService.addFolder(folder);
        if (res) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(1001, "文件夹重名");
        }
    }

    /**
     * 编辑文件夹
     *
     * @param folder
     * @return
     */
    @RequestMapping(value = "/editFolder", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult editFolder(@RequestBody OrbitVideoFileFolder folder) {
        boolean res = fileFolderService.updateFolder(folder);
        if (res) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(1001, "文件夹重名");
        }
    }

    /**
     * 删除文件夹
     *
     * @param folder
     * @return
     */
    @RequestMapping(value = "/deleteFolder", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteFolder(@RequestBody OrbitVideoFileFolder folder) {
        fileFolderService.deleteFolder(folder);
        return ResultUtil.success();
    }
}

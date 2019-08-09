package com.car.orbit.orbitservice.controller;

import com.car.orbit.orbitservice.constant.ExportConstant;
import com.car.orbit.orbitservice.constant.OrbitServiceConstant;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import com.car.orbit.orbitservice.entity.OrbitControlBlacklistType;
import com.car.orbit.orbitservice.entity.OrbitSysUser;
import com.car.orbit.orbitservice.exception.DuplicateDataException;
import com.car.orbit.orbitservice.exception.IllegalParamException;
import com.car.orbit.orbitservice.exception.ExistInOtherModuleException;
import com.car.orbit.orbitservice.exception.RelationshipException;
import com.car.orbit.orbitservice.qo.BlackListQO;
import com.car.orbit.orbitservice.qo.ExportQO;
import com.car.orbit.orbitservice.service.IBlackListService;
import com.car.orbit.orbitservice.util.ExportExcelUtil;
import com.car.orbit.orbitservice.util.ReadExcel;
import com.car.orbit.orbitservice.util.redis.ColorRedis;
import com.car.orbit.orbitservice.util.redis.ExportHeaderRedis;
import com.car.orbit.orbitservice.util.redis.OrbitSysUserRedis;
import com.car.orbit.orbitservice.vo.FileVO;
import com.car.orbit.orbitutil.page.PageUtil;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitutil.response.ResponseType;
import com.car.orbit.orbitutil.response.ResultUtil;
import com.car.orbit.orbitutil.tools.FastDFSClient;
import com.car.orbit.orbitutil.tools.UUIDUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @Title: BlackListController
 * @Package: com.car.orbit.orbitservice.controller
 * @Description: 黑名单Controller
 * @Author: monkjavaer
 * @Date: 2019/04/03 10:44
 * @Version: V1.0
 */
@RestController
@RequestMapping("/blackList")
public class BlackListController {

    /** 车辆已存在于黑名单 返回码 */
    private final int duplicateParamCode = 1001;

    /** 车辆在白名单里面存在 返回码 */
    private final int existInWhiteListCode = 1002;

    /** 参数位空 返回码 */
    private final int emptyParamCode = 1003;

    /** 黑名单导出文件名 */
    private final String blacklist_xls = "blacklist.xls";

    @Autowired
    private IBlackListService blackListService;

    /**
     * 查询黑名单
     *
     * @param blackListQO
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult queryPageList(@RequestBody BlackListQO blackListQO) {
        if (blackListQO.getStatus() != null && blackListQO.getStatus() == 2) {
            blackListQO.setStatus(null);
        }
        PageUtil<OrbitControlBlacklist> list = blackListService.queryPageList(blackListQO);
        return ResultUtil.success(list);
    }

    /**
     * 添加黑名单
     *
     * @param blacklist
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addBlackList(@RequestBody OrbitControlBlacklist blacklist) {
        try {
            blackListService.addBlackList(blacklist);
        } catch (ExistInOtherModuleException e) {
            return ResultUtil.error(existInWhiteListCode, e.getMessage());
        } catch (IllegalParamException e) {
            return ResultUtil.error(emptyParamCode, e.getMessage());
        } catch (DuplicateDataException e) {
            return ResultUtil.error(duplicateParamCode, e.getMessage());
        }
        return ResultUtil.success();
    }

    /**
     * 更新黑名单
     *
     * @param blacklist
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult updateBlackList(@RequestBody OrbitControlBlacklist blacklist) {
        blackListService.updateBlackList(blacklist);
        return ResultUtil.success();
    }

    /**
     * 删除黑名单
     *
     * @param blacklist 需要删除的黑名单
     * @return 操作结果
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteBlackList(@RequestBody OrbitControlBlacklist blacklist) {
        blackListService.deleteBlackList(blacklist);
        return ResultUtil.success();
    }

    /**
     * 黑名单导出
     *
     * @param exportQO
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult exportBlackList(@RequestBody ExportQO<BlackListQO> exportQO) {
        OrbitSysUser user = OrbitSysUserRedis.getCurrentLoginUser();
        String language;
        try {
            language = user.getLanguage().split("_")[0].toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            language = "ZH";
        }

        BlackListQO qo = exportQO.getQueryObject();
        qo.setPageSize(exportQO.getEnd() - exportQO.getStart() + 1);
        qo.setPageNo(exportQO.getStart() / qo.getPageSize() + 1);

        PageUtil<OrbitControlBlacklist> list = blackListService.queryPageList(qo);
        List<OrbitControlBlacklist> blacklistList = list.getItems();

        for (OrbitControlBlacklist blacklist : blacklistList) {
            blacklist.setVehicleType(OrbitServiceConstant.getOrbitVehicleType(blacklist.getVehicleType()).getInternationalName(language));
            blacklist.setPlateColor(ColorRedis.getNameByCode(blacklist.getPlateColor()));
            blacklist.setVehicleColor(ColorRedis.getNameByCode(blacklist.getVehicleColor()));
        }

        String[] headers = ExportHeaderRedis.getHeaderNameByCode(ExportConstant.EXPORT_BLACK_LIST);
        String[] fields = new String[]{"plateNumber", "plateColor", "vehicleType", "vehicleColor", "fullBrand", "typeName"};

        try {
            String url = ExportExcelUtil.exportExcel(blacklist_xls, headers, fields, blacklistList);
            return ResultUtil.success(url);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResultUtil.error(ResponseType.EXPORT_ERROR.getCode(), "export error");
        }
    }

    /**
     * 黑名单导入
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult importBlackList(@RequestParam(value = "file") MultipartFile file) {
        if (file == null) {
            return ResultUtil.error(1001, "文件为空");
        }

        //判断文件内容是否为空
        String name = file.getOriginalFilename();
        long size = file.getSize();
        if (StringUtils.isEmpty(name) || size == 0) {
            return ResultUtil.error(1002, "文件内容为空");
        }

        String ext = name.substring(name.lastIndexOf(".") + 1);
        if (!ext.equals("xls") && !ext.equals("xlsx")) {
            return ResultUtil.error(1004, "文件格式异常");
        }

        // 解析Excel表
        ReadExcel readExcel = new ReadExcel();
        List sourceDate = readExcel.getExcelInfo(name, file);
        if (sourceDate == null) {
            return ResultUtil.error(1003, "文件解析异常");
        }

        Map<String, Integer> resultMap = blackListService.importBlackList(sourceDate);

        return ResultUtil.success(resultMap);
    }

    /**
     * 获取黑名单类型列表
     *
     * @return
     */
    @RequestMapping(value = "/typeList", method = RequestMethod.GET)
    @ResponseBody
    public OrbitResult getAllBlacklistType() {
        return ResultUtil.success(blackListService.getAllBlacklistType());
    }

    /**
     * 添加黑名单类型
     *
     * @return
     */
    @RequestMapping(value = "/typeAdd", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult addBlacklistType(@RequestBody OrbitControlBlacklistType type) {
        try {
            blackListService.addBlacklistType(type);
        } catch (DuplicateDataException e) {
            return ResultUtil.error(1001, "类型名已存在");
        }
        return ResultUtil.success();
    }

    /**
     * 编辑黑名单类型
     *
     * @return
     */
    @RequestMapping(value = "/typeEdit", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult editBlacklistType(@RequestBody OrbitControlBlacklistType type) {
        try {
            blackListService.editBlacklistType(type);
        } catch (DuplicateDataException e) {
            return ResultUtil.error(1001, "类型名已存在");
        }
        return ResultUtil.success();
    }

    /**
     * 删除黑名单类型
     *
     * @return
     */
    @RequestMapping(value = "/typeDelete", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult deleteBlacklistType(@RequestBody OrbitControlBlacklistType type) {
        try {
            blackListService.deleteBlacklistType(type);
        } catch (RelationshipException e) {
            return ResultUtil.error(1001, "该类型不能删除");
        }
        return ResultUtil.success();
    }

    /**
     * 下载模板文件
     *
     * @return
     */
    @RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST)
    @ResponseBody
    public OrbitResult downloadTemplate() {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            // 创建HSSFSheet对象
            XSSFSheet sheet = wb.createSheet("sheet0");
            sheet.setDefaultColumnWidth((short) 20);

            // 生成一个样式
            XSSFCellStyle style = wb.createCellStyle();
            // 前景色
            style.setFillForegroundColor(HSSFColor.WHITE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            // 边框
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 指定当单元格内容显示不下时自动换行
            style.setWrapText(true);
            // 生成一个字体
            XSSFFont font = wb.createFont();
            font.setColor(IndexedColors.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            // 把字体应用到当前的样式
            style.setFont(font);
            XSSFRow row;
            XSSFCell cell;

            List<OrbitControlBlacklistType> typeList = blackListService.getAllBlacklistType();
            StringBuilder sb = new StringBuilder();
            sb.append("TYPE(");
            for (int i = 0; i < typeList.size(); i++) {
                sb.append(typeList.get(i).getId()).append("-").append(typeList.get(i).getName());
                if (i != (typeList.size() - 1)) {
                    sb.append(",");
                }
            }
            sb.append(")");

            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("PLATE NUMBER(Required)");
            cell = row.createCell(1);
            cell.setCellValue("PLATE COLOR");
            cell = row.createCell(2);
            cell.setCellValue(sb.toString());
            cell = row.createCell(3);
            cell.setCellValue("VEHICLE TYPE");
            cell = row.createCell(4);
            cell.setCellValue("VEHICLE COLOR");
            cell = row.createCell(5);
            cell.setCellValue("BRAND");
            cell = row.createCell(6);
            cell.setCellValue("BRAND CHILD");

            /** 生成Excel文件 **/
            String path = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/temp";
            File file = new File(path);
            if (!file.exists()) file.mkdirs();
            String fileName = UUIDUtils.generate() + ".xlsx";
            FileOutputStream outputStream = new FileOutputStream(path + "/" + fileName);
            wb.write(outputStream);
            outputStream.flush();

            System.out.println(path);

            /** 上传到DFS服务器 **/
            String url = FastDFSClient.uploadFile(new File(path + "/" + fileName), fileName, true);
            return ResultUtil.success(new FileVO(url));
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.error(1001, e.getMessage());
        }
    }
}

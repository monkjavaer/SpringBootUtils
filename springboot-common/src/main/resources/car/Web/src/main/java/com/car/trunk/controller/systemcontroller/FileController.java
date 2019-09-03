//package com.car.trunk.controller.systemcontroller;
//
//import com.car.trunk.center.system.service.ISystemDicService;
//import com.car.trunk.center.vehiclecontrol.service.BlackListTypeDicService;
//import com.car.trunk.center.vehiclecontrol.service.WhiteListTypeDicService;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.xssf.usermodel.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping(value = "/api/file")
//public class FileController {
//	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
//
//	/**
//	 * 国际化服务
//	 */
//	@Autowired
//	private ISystemDicService systemDicService;
//
//	/**
//	 * 黑/白名单模板下载
//	 * @param response
//	 * @param fileName 控制导入黑名单还是白名单(前段传blacklist.xlsx代表上传黑名单,前段传whitelist.xlsx代表上传白名单)
//	 * @param language 语言
//	 */
//	@RequestMapping(value = "/downloadfile")
//	public void exportViolationRecord(HttpServletResponse response, String fileName,String language){
//
//		try {
//			//从数据库获取国际化数据
//			List<SystemDicEntity> dicEntities = systemDicService.getAllSystemDic();
//			Map<String,List<SystemDicEntity>> map = dicEntities.stream().collect(Collectors.groupingBy(t -> t.getType()));
//			List<SystemDicEntity> listTypeStr = map.get("listTypeStr");
//			List<SystemDicEntity> plateNumber = map.get("plateNumberForList");
//			List<SystemDicEntity> vcehicleTypeStr = map.get("vcehicleTypeStr");
//			List<SystemDicEntity> plateColorStr = map.get("plateColorStr");
//			List<SystemDicEntity> vehicleColorStr = map.get("vehicleColorStr");
//			List<SystemDicEntity> casetime = map.get("casetime");
//			List<SystemDicEntity> caseadress = map.get("caseadress");
//			List<SystemDicEntity> feature = map.get("feature");
//			List<SystemDicEntity> description = map.get("description");
//
//			// 创建HSSFWorkbook对象
//			XSSFWorkbook wb = new XSSFWorkbook();
//			// 创建HSSFSheet对象
//			XSSFSheet sheet = wb.createSheet("sheet0");
//			sheet.setDefaultColumnWidth((short)20);
//
//			// 生成一个样式
//			XSSFCellStyle style = wb.createCellStyle();
//			// 设置样式
//
//			// 指定当单元格内容显示不下时自动换行
//			style.setWrapText(true);
//			// 生成一个字体
//			XSSFFont font = wb.createFont();
//			font.setColor(IndexedColors.BLACK.index);
//			font.setFontHeightInPoints((short) 12);
//			// 把字体应用到当前的样式
//			style.setFont(font);
//			// 创建HSSFRow对象
//			XSSFRow row;
//			// 创建HSSFCell对象
//			XSSFCell cell;
//			String newName = null;
//
//			//前段传blacklist.xlsx代表上传黑名单
//			String blacklistModelName = "blacklist.xlsx";
//
//			if (blacklistModelName.equals(fileName)) {
//				//拼接黑名单表头
//				List<SystemDicEntity> blacklist = map.get("blacklist");
//				newName = SysDicUtils.getDicInfos(language, blacklist).get(0).getName();
//				List<BlackListTypeDicEntity> list = blackListTypeDicService.listAll();
//				StringBuffer blackListtype = new StringBuffer();
//
//				blackListtype.append(SysDicUtils.getDicInfos(language, listTypeStr).get(0).getName());
//				for (int i = 0; i < list.size(); i++) {
//					blackListtype.append(list.get(i).getType() + "-" + list.get(i).getName());
//					if (i <= list.size() - 2) {
//						blackListtype.append(",");
//					}
//				}
//				blackListtype.append(")");
//
//				row = sheet.createRow(0);
//				cell = row.createCell(0);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, plateNumber).get(0).getName());
//				cell = row.createCell(1);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, vcehicleTypeStr).get(0).getName());
//				cell = row.createCell(2);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, plateColorStr).get(0).getName());
//				cell = row.createCell(3);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, vehicleColorStr).get(0).getName());
//				cell = row.createCell(4);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, casetime).get(0).getName());
//				cell = row.createCell(5);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, caseadress).get(0).getName());
//				cell = row.createCell(6);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, feature).get(0).getName());
//				cell = row.createCell(7);
//				cell.setCellValue(blackListtype.toString());
//				cell = row.createCell(8);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, description).get(0).getName());
//			}
//
//			//前段传whitelist.xlsx代表上传白名单
//			String whitelistModelName = "whitelist.xlsx";
//
//			if (whitelistModelName.equals(fileName)){
//				//拼接白名单表头
//				List<SystemDicEntity> blacklist = map.get("whitelist");
//				newName = SysDicUtils.getDicInfos(language, blacklist).get(0).getName();
//				List<WhiteListTypeDicEntity> whiteListTypeDicEntities = whiteListTypeDicService.listAll();
//				StringBuffer whiteListtype = new StringBuffer();
//				whiteListtype.append(SysDicUtils.getDicInfos(language, listTypeStr).get(0).getName());
//				for (int i = 0; i < whiteListTypeDicEntities.size(); i++) {
//					whiteListtype.append(whiteListTypeDicEntities.get(i).getType() + "-" + whiteListTypeDicEntities.get(i).getName());
//					if (i <= whiteListTypeDicEntities.size() - 2) {
//						whiteListtype.append(",");
//					}
//				}
//				whiteListtype.append(")");
//				row = sheet.createRow(0);
//				cell = row.createCell(0);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, plateNumber).get(0).getName());
//				cell = row.createCell(1);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, vcehicleTypeStr).get(0).getName());
//				cell = row.createCell(2);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, plateColorStr).get(0).getName());
//				cell = row.createCell(3);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, vehicleColorStr).get(0).getName());
//				cell = row.createCell(4);
//				cell.setCellValue(whiteListtype.toString());
//				cell = row.createCell(5);
//				cell.setCellValue(SysDicUtils.getDicInfos(language, description).get(0).getName());
//			}
//
//			// 导出Excel文件
//			OutputStream output=response.getOutputStream();
//			response.reset();
//			// 赋予名称
//			String filemName = new String(newName.getBytes(), "ISO-8859-1");
//			response.setHeader("Content-disposition", "attachment; filename="+filemName+".xlsx");
//			response.setContentType("application/msexcel");
//			wb.write(output);
//			output.close();
//		}catch (Exception e){
//			e.printStackTrace();
//            logger.error("export error ! ! ! ");
//            logger.error(e.getMessage());
//		}
//
//	}
//}

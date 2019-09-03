package com.car.orbit.orbitservice.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.car.orbit.orbitservice.exception.StructureException;
import com.car.orbit.orbitservice.vo.VehiclePositionVO;
import com.car.orbit.orbitutil.tools.HttpUtil;
import com.car.orbit.orbitutil.tools.PropertyReaderUtils;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Title: 请求结构化服务器的工具类
 * @Package: com.car.orbit.orbitservice.util
 * @Description:
 * @Author: @author zks
 * @Date: 2019/03/19 16:51
 * @Version: V1.0
 * 该类主要封装了访问结构化服务器的HTTP请求接口
 */
public class StructuralServerUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StructuralServerUtil.class);

    private static final String IP_MODE = "ip";
    private static final String DOMAIN_MODE = "domain";

    private static String ip = PropertyReaderUtils.getProValue("structural.server.ip");
    private static String port = PropertyReaderUtils.getProValue("structural.server.port");
    private static String projectName = PropertyReaderUtils.getProValue("structural.server.projectname");
    private static String domain = StringUtil.isNullOrEmpty(PropertyReaderUtils.getProValue("structural.server.domain")) ? "" : PropertyReaderUtils.getProValue("structural.server.domain");
    private static String mode = StringUtil.isNullOrEmpty(PropertyReaderUtils.getProValue("structural.server.model")) ? IP_MODE : PropertyReaderUtils.getProValue("structural.server.model");

    private static String getRealUrl(String url) {
        if (mode.equals(IP_MODE)) {
            url = "http://" + ip + ":" + port + "/" + projectName + "/" + url;
        } else if (mode.equals(DOMAIN_MODE)) {
            url = "http://" + domain + "/" + projectName + "/" + url;
        }
        return url;
    }

    /**
     * 识别picUrl图中车辆的位置，根据实际情况，会返回1个或多个识别结果
     *
     * @return List<VehiclePositionVO>
     */
    public static List<VehiclePositionVO> getVehiclePosition(String picUrl) throws IOException, StructureException {
        String url = "analyse/info";
        url = getRealUrl(url) + "?picUrl=" + picUrl;
        String result = HttpUtil.sendGet(url);
        if (StringUtils.isBlank(result)) {
            throw new StructureException("结构化返回结果为空");
        }
        JSONObject obj = JSON.parseObject(result);  //将json字符串转换为json对象
        if (obj != null && ("true".equals(obj.get("success")) || obj.getBoolean("success"))) {
            LOGGER.info("-------- get getVehiclePosition is success, result is :" + result);
            return JSONArray.parseArray(JSON.toJSONString(obj.get("data")), VehiclePositionVO.class);
        } else {
            LOGGER.info("-------- get getVehiclePosition is failed, result is : " + result);
            throw new StructureException(obj != null ? obj.getString("data") : "结构化返回结果为空");
        }
    }

}

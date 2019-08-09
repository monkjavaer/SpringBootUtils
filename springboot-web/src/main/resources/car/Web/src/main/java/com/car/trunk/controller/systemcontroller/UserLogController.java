package com.car.trunk.controller.systemcontroller;

import com.car.base.common.utilities.ApiError;
import com.car.base.common.utilities.ApiResponseBody;
import com.car.base.common.utilities.PageList;
import com.car.base.redis.RedisHelper;
import com.car.trunk.center.system.bo.UserInfoBO;
import com.car.trunk.center.system.service.UserLogService;
import com.car.trunk.dal.system.bo.UserLogBO;
import com.car.trunk.dal.system.vo.UserLogVO;
import com.car.trunk.util.StringFormatUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *Description:系统日志
 * Created by monkjavaer on 2017/12/6 0006.
 */
@Controller
@RequestMapping("/api/userLog")
public class UserLogController {

    private static Logger logger = LoggerFactory.getLogger(UserLogController.class);
    @Autowired
    private UserLogService userLogService;

    /**
     * 系统日志分页列表
     * @param userLogVO
     * @return
     */
    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponseBody queryList(@RequestBody UserLogVO userLogVO,HttpServletRequest request) {
        // 验证登录状况
        String token = request.getHeader("authorization");
        if (null == token) {
            return new ApiResponseBody(ApiError.UNKNOWN_USER);
        }
        UserInfoBO userInfoBO = (UserInfoBO) JSONObject.toBean(
                JSONObject.fromObject(RedisHelper.get(token)), UserInfoBO.class);
        PageList<UserLogBO> list = null;
        if (StringUtils.isNotEmpty(userLogVO.getUser())){
            userLogVO.setUser(StringFormatUtil.formatRetrieveString(userLogVO.getUser()));
        }
        try {
            list = userLogService.queryList(userLogVO,userInfoBO.getUserName(),userInfoBO.getUserId());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return new ApiResponseBody(ApiError.UNKNOWN_EXCEPTION);
        }
        return new ApiResponseBody(list);
    }
}

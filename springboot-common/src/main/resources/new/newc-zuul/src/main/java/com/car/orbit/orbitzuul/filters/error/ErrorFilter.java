package com.car.orbit.orbitzuul.filters.error;

import com.alibaba.fastjson.JSON;
import com.car.orbit.orbitutil.response.IResponseType;
import com.car.orbit.orbitutil.response.OrbitResult;
import com.car.orbit.orbitzuul.enums.EErrorFilterOrder;
import com.car.orbit.orbitzuul.enums.EFilterType;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * CreateDate：2018/4/24 <br/>
 * Author：NieLixiang <br/>
 * Description: process exceptions thrown(except for post filter exception)
 **/
@Component
public class ErrorFilter extends ZuulFilter {

    /** logger */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        return EFilterType.FILTER_ERROR.getFilterType();
    }

    @Override
    public int filterOrder() {
        return EErrorFilterOrder.ORDER_ERROR.getOrderValue();
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * process exceptions found
     */
    @Override
    public Object run() {
        //init return information
        OrbitResult exceptionMsg = new OrbitResult();
        exceptionMsg.setCode(IResponseType.ZUUL_ERROR);
        exceptionMsg.setMessage("Unknown Server Error");
        exceptionMsg.setSuccess(false);

        //log exception information
        RequestContext context = RequestContext.getCurrentContext();
        Throwable exception = context.getThrowable();
        if (Objects.nonNull(exception)) {
            logger.error(exception.getMessage(), exception.getCause());
        }

        //return response directly
        HttpServletResponse response = context.getResponse();
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(exceptionMsg));
            out.flush();
        } catch (IOException e) {
            //will not happen forever
            logger.error("fatal system error", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return null;
    }
}

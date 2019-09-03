package com.car.orbit.orbitzuul.filters.pre;

import com.car.orbit.orbitutil.exception.OrbitException;
import com.car.orbit.orbitutil.security.GeneralAuInfo;
import com.car.orbit.orbitzuul.enums.EFilterType;
import com.car.orbit.orbitzuul.enums.EPreFilterOrder;
import com.car.orbit.orbitzuul.filters.config.ZuulConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * CreateDate：2019/3/8 <br/>
 * Author：monkjavaer <br/>
 * Description: zuul-access拦截类，防止有人绕过网关
 **/
@Component
public class AccessFilter extends ZuulFilter {

    /**
     * 前置拦截
     */
    @Override
    public String filterType() {
        return EFilterType.FILTER_PRE.getFilterType();
    }

    /**
     * 拦截顺序
     */
    @Override
    public int filterOrder() {
        return EPreFilterOrder.ORDER_ACCESS.getOrderValue();
    }

    /**
     * 默认拦截所有请求
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        // 获取请求
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        // 检查请求头是否包含网关认证信息
        String accessHeader = request.getHeader(ZuulConstants.ACCESS_KEY);
        if (!(ZuulConstants.ACCESS_VALUE.equals(accessHeader))) {

            // 请求头中没有，再检查请求参数中有没有，因为前台调用下载接口的时候，不方便在请求头中添加认证信息
            if (!(ZuulConstants.ACCESS_VALUE.equals(request.getParameter(ZuulConstants.ACCESS_KEY)))) {
                throw new OrbitException("illegal access from " + request.getMethod() + " request to " + request.getRequestURL().toString());
            }

        }
        // 添加zuul网关认证
        ctx.addZuulRequestHeader(GeneralAuInfo.ZUUL_PROCESS_LABEL_KEY, GeneralAuInfo.ZUUL_PROCESS_LABEL_VALUE);
        return null;
    }
}
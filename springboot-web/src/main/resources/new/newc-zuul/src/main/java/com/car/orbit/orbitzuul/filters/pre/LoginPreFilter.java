package com.car.orbit.orbitzuul.filters.pre;

import com.car.orbit.orbitzuul.enums.EFilterType;
import com.car.orbit.orbitzuul.enums.EPreFilterOrder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * CreateDate：2019/3/5 <br/>
 * Author：monkjavaer <br/>
 * Description: pre filter for login request
 **/
@Component
public class LoginPreFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return EFilterType.FILTER_PRE.getFilterType();
    }

    @Override
    public int filterOrder() {
        return EPreFilterOrder.ORDER_LOGIN_PRE.getOrderValue();
    }

    /**
     * only filter request to method "login" or "loginAndKick"
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String route = ctx.getRequest().getRequestURI();
        return route.endsWith("/login") || route.endsWith("/loginAndKick");
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("method pre login filter ... ");
        return null;
    }
}
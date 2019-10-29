package com.car.orbit.orbitzuul.filters.pre;

import com.car.orbit.orbitzuul.enums.EFilterType;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/**
 * CreateDate：2019/3/5 <br/>
 * Author：monkjavaer <br/>
 * Description:
 **/
@Component
public class TestPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return EFilterType.FILTER_PRE.getFilterType();
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURI = ctx.getRequest().getRequestURI();
        return requestURI.endsWith("/test");
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul pre filter test ...");
        return null;
    }
}
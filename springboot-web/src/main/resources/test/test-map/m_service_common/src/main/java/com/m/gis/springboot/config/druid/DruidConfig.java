package com.m.gis.springboot.config.druid;


import org.springframework.context.annotation.Configuration;

/**
 * Created by chen on 2017/5/16.
 * <p>
 * <p>
 * Describe:
 */
@Configuration
public class DruidConfig {
   /* *//**
     * 注册DruidServlet
     * http://localhost:8080/druid/datasource.html查看监控信息
     * @return
     *//*
    @Bean
    public ServletRegistrationBean druidServletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }

    *//**
     * 注册DruidFilter拦截
     *
     * @return
     *//*
    @Bean
    public FilterRegistrationBean duridFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        //设置忽略请求
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }*/
}

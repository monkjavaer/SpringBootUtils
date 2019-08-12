package com.base.springboot.mydruid.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Druid 配置 Filter
 *  1、extends FilterEventAdapter
 *  2、META-INF下druid-filter.properties配置
 * <p>
 *     https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter
 * </p>
 * @author monkjavaer
 * @version V1.0
 * @date 2019/8/11 0011 22:06
 */
public class ConnectionLogFilter extends FilterEventAdapter {


    /**
     * log
     */
    private static Logger log = LoggerFactory.getLogger(ConnectionLogFilter.class);

    /**
     * 连接前
     * @param chain
     * @param info
     */
    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        log.info("============BEFORE CONNECTION!==========");
    }

    /**
     * 连接后
     * @param connection
     */
    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        log.info("=============AFTER CONNECTION!==============");
    }
}

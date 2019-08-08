package com.base.springboot.utils;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: SolrClientUtils solr连接工具类
 * @Author: monkjavaer
 * @Date: 2019/6/22 19:57
 * @Version: V1.0
 */
public class SolrClientUtils {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SolrClientUtils.class);

    /**
     * solr 地址
     */
    private static String SOLR_URL = PropertyReaderUtils.getProValue("solr.address_url");

    /**
     * solr GIS 地址索引库
     */
    public static String SOLR_GIS_ADDRESS_INDEX = PropertyReaderUtils.getProValue("solr.address_index");

    /**
     * solr suggest size
     */
    public static Integer SOLR_SUGGEST_SIZE = Integer.valueOf(PropertyReaderUtils.getProValue("solr.suggest_size"));

    /**
     * suggest AnalyzingLookupFactory
     */
    public final static String SOLR_ANALYZINGSUGGESTER = PropertyReaderUtils.getProValue("solr.AnalyzingSuggester");

    /**
     * suggest AnalyzingInfixLookupFactory
     */
    public final static String SOLR_ANALYZINGINFIXSUGGESTER = PropertyReaderUtils.getProValue("solr.AnalyzingInfixSuggester");

    /**
     * HttpSolrClient
     */
    private static HttpSolrClient httpSolrClient;

    /**
     * default socket connection timeout in ms
     */
    private static int DEFAULT_CONNECTION_TIMEOUT = 60000;

    /**
     * @return org.apache.solr.client.solrj.impl.HttpSolrClient
     * @description 获取 HttpSolrClient
     * @date 20:47 2019/6/22
     * @param: []
     **/
    public static HttpSolrClient getHttpSolrClient() {
        httpSolrClient = initSolrClient();
        return httpSolrClient;
    }

    /**
     * @return void
     * @description 初始化solr
     * @date 20:25 2019/6/22
     * @param: []
     **/
    private static HttpSolrClient initSolrClient() {
        try {
            if (httpSolrClient == null) {
                logger.info("httpSolrClient is null , start build HttpSolrClient......");
                httpSolrClient = new HttpSolrClient.Builder(SOLR_URL + SOLR_GIS_ADDRESS_INDEX + "/").build();
                httpSolrClient.setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
                httpSolrClient.setDefaultMaxConnectionsPerHost(100);
                httpSolrClient.setMaxTotalConnections(100);
                logger.info("build HttpSolrClient finished .");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return httpSolrClient;
    }
}

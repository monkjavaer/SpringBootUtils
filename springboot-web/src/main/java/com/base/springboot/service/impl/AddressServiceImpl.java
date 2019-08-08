package com.base.springboot.service.impl;

import com.base.springboot.exception.WebException;
import com.base.springboot.qo.AddressNameQO;
import com.base.springboot.service.AddressService;
import com.base.springboot.utils.BasePage;
import com.base.springboot.utils.SolrClientUtils;
import com.base.springboot.vo.AddressNameVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SuggesterResponse;
import org.apache.solr.client.solrj.response.Suggestion;
import org.apache.solr.common.params.CommonParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Title: AddressServiceImpl
 * @Package: com.base.springboot.service.impl
 * @Description: 地址查询服务接口
 * @Author: monkjavaer
 * @Date: 2019/8/7 10:21
 * @Version: V1.0
 */
@Service
public class AddressServiceImpl implements AddressService {

    /**log*/
    private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    /**
     * 分页查询
     * @param addressNameQO
     * @return
     */
    @Override
    public BasePage<AddressNameVO> queryAddressName(AddressNameQO addressNameQO) {
        //从solr获取数据
        BasePage<AddressNameVO> pageList;
        try {
            pageList = queryAddressNameFromSolr(addressNameQO.getName(), addressNameQO.getPageIndex(), addressNameQO.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("query data from solr failed !!!");
            throw new WebException("query data from solr failed !!!");
        }
        return pageList;
    }

    /**
     * 向solr批量添加索引数据
     * @param list
     * @return
     */
    @Override
    public Object saveAddressNameList(List<AddressNameQO> list) {
        try {
            //向solr批量添加索引数据
            long startTime = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
            SolrClientUtils.getHttpSolrClient().addBeans(list);
            SolrClientUtils.getHttpSolrClient().commit();
            long endTime = TimeUnit.MILLISECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
            logger.info("commit solr data cost {} ms.", endTime - startTime);
        } catch (SolrServerException | IOException e) {
            logger.error("save address name to solr failed");
            throw new WebException("save address name to solr failed.");
        }
        return true;
    }

    /**
     * @description 从solr获取数据,返回组装PageList<GisAddressName>
     * @date 14:28 2019/6/25
     * @param: [name, pageIndex, pageSize]
     **/
    private BasePage<AddressNameVO> queryAddressNameFromSolr(String name, Integer pageIndex, Integer pageSize) throws Exception {
        SolrQuery query = new SolrQuery("*:*");
        query.set(CommonParams.QT, "/suggest");
        query.set("suggest.dictionary", SolrClientUtils.SOLR_ANALYZINGSUGGESTER, SolrClientUtils.SOLR_ANALYZINGINFIXSUGGESTER);
        query.set("suggest.q", name);
        //query.set("suggest.build", true);
        QueryRequest request = new QueryRequest(query);
        QueryResponse queryResponse = request.process(SolrClientUtils.getHttpSolrClient());
        SuggesterResponse response = queryResponse.getSuggesterResponse();
        Map<String, List<Suggestion>> suggestionsMap = response.getSuggestions();
        //solr查询返回集合
        List<AddressNameVO> solrList = new ArrayList<>();
        //首先处理AnalyzingLookupFactory获取的数据
        dealWithSuggestionData(suggestionsMap, solrList, SolrClientUtils.SOLR_ANALYZINGSUGGESTER);
        //如果未满五条建议会通过AnalyzingInfixLookupFactory查询结果补偿
        dealWithSuggestionData(suggestionsMap, solrList, SolrClientUtils.SOLR_ANALYZINGINFIXSUGGESTER);
        List<AddressNameVO> tempList = getGisAddressNameList(solrList);
        return new BasePage<>(tempList);
    }

    private List<AddressNameVO> getGisAddressNameList(List<AddressNameVO> solrList){
        List<AddressNameVO> tempList = new ArrayList<>();
        if (solrList.size() > 0) {
            for (AddressNameVO solrEntity : solrList) {
                //业务逻辑
            }
        }else {
            return tempList;
        }
        return tempList;
    }

    /**
     * @description 处理suggest建议数据,组装List<GisAddressName> tempList
     * @date 17:43 2019/6/25
     * @param: [suggestionsMap, tempList, suggester]
     * @return void
     **/
    private void dealWithSuggestionData(Map<String, List<Suggestion>> suggestionsMap, List<AddressNameVO> solrList, String suggester) throws Exception {
        //最大支持10条建议
        if (solrList.size() >= SolrClientUtils.SOLR_SUGGEST_SIZE) {
            return;
        }
        if (suggestionsMap.keySet().contains(suggester)) {
            List<Suggestion> mySuggester = suggestionsMap.get(suggester);
            for (Suggestion suggestion : mySuggester) {
                //最大支持10条建议
                if (solrList.size() >= SolrClientUtils.SOLR_SUGGEST_SIZE) {
                    break;
                }
                //payload存放的是主键gid
                String id = suggestion.getPayload();
                if (StringUtils.isNotBlank(id)) {
                    //通过主键去重
                    List<AddressNameVO> containAddress = solrList.stream()
                            .filter(address -> address.getId().equals(Integer.valueOf(id))).collect(Collectors.toList());
                    if (containAddress.size() > 0){
                        continue;
                    }
                    //根据主键获取完整数据，组装数据
                    AddressNameVO gisAddressName = getAddressName(id);
                    solrList.add(gisAddressName);
                }
            }
        }
    }



    /**
     * @description 根据主键检索solr数据
     * @date 22:25 2019/6/22
     * @param: [gid] solr地址主键
     **/
    private AddressNameVO getAddressName(String id) throws Exception {
        final SolrQuery query = new SolrQuery();
        //设置主键查询条件
        query.setQuery("id:" + id);
        //设置solr查询列
        query.addField("id");
        query.addField("name");
        query.addField("districtCode");
        final QueryResponse response = SolrClientUtils.getHttpSolrClient().query(query);
        final List<AddressNameVO> addressList = response.getBeans(AddressNameVO.class);
        if (addressList.size() > 0) {
            //根据主键查询,只有一条
            return addressList.get(0);
        }
        return null;
    }
}

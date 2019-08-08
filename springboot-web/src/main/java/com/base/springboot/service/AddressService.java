package com.base.springboot.service;

import com.base.springboot.qo.AddressNameQO;
import com.base.springboot.utils.BasePage;
import com.base.springboot.vo.AddressNameVO;

import java.util.List;

/**
 * @Title: AddressService
 * @Package: com.base.springboot.service
 * @Description: 地址查询服务接口
 * @Author: monkjavaer
 * @Date: 2019/8/7 10:09
 * @Version: V1.0
 */
public interface AddressService {

    /**
     * 分页查询
     * @param addressNameQO
     * @return
     */
    BasePage<AddressNameVO> queryAddressName(AddressNameQO addressNameQO);

    /**
     * 向solr批量添加索引数据
     * @param list
     * @return
     */
    Object saveAddressNameList(List<AddressNameQO> list);
}

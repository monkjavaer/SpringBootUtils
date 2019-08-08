package com.base.springboot.controller;

import com.base.springboot.qo.AddressNameQO;
import com.base.springboot.response.BaseResult;
import com.base.springboot.response.ResultUtil;
import com.base.springboot.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @Title: SolrController
 * @Package: com.base.springboot.controller
 * @Description: 通过solr全文检索查询名字
 * @Author: monkjavaer
 * @Date: 2019/8/7 9:28
 * @Version: V1.0
 */
@RestController
@RequestMapping(value="/web/solrAddress")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 模糊查询地名
     * @param addressNameQO
     * @return
     */
    @ApiOperation(value="模糊查询地名", notes="")
    @RequestMapping(value="/queryAddressName", method= RequestMethod.POST)
    public BaseResult queryAddressName(@RequestBody @Valid AddressNameQO addressNameQO) {
        return ResultUtil.success(addressService.queryAddressName(addressNameQO));
    }

    /**
     * 向solr批量添加索引数据
     * @param list
     * @return
     */
    @ApiOperation(value="向solr批量添加索引数据", notes="")
    @RequestMapping(value="/saveAddressNameList.do", method=RequestMethod.POST)
    public BaseResult saveAddressNameList(@RequestBody @Valid List<AddressNameQO> list) {
        return ResultUtil.success(addressService.saveAddressNameList(list));
    }
}

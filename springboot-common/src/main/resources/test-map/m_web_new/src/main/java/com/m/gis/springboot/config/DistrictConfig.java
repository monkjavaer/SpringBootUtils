package com.m.gis.springboot.config;

import com.m.gis.springboot.district.GisBolDistrictTypeUtil;
import com.m.gis.springboot.district.common.GisAbstractDistrictTypeUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: DistrictConfig
 * @Package: com.m.gis.springboot.config
 * @Description: 在gis_bol中继承GisAbstractDistrictTypeUtil类，并在此生成可用于gis_service_district的bean
 * @Author: monkjavaer
 * @Data: 2018/7/2
 * @Version: V1.0
 */
@Configuration
public class DistrictConfig {
    @Bean
    public GisAbstractDistrictTypeUtil getDistrictTypeBean(){
        return GisBolDistrictTypeUtil.getInstance();
    }
}

package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitControlWhitelist;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrbitControlWhitelistMapper extends Mapper<OrbitControlWhitelist> {

    void updateWhitelistId(@Param("oldVid") String oldVid, @Param("newVid") String newVid);
}
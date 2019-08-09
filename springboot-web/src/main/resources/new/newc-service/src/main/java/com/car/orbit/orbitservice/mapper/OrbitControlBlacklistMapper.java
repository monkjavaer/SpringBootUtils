package com.car.orbit.orbitservice.mapper;

import com.car.orbit.orbitservice.entity.OrbitControlBlacklist;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface OrbitControlBlacklistMapper extends Mapper<OrbitControlBlacklist> {

    void updateBlacklistId(@Param("oldVid") String oldVid, @Param("newVid") String newVid);
}
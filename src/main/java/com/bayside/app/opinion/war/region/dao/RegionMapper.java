package com.bayside.app.opinion.war.region.dao;

import java.util.List;

import com.bayside.app.opinion.war.region.model.Region;

public interface RegionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Region record);

    int insertSelective(Region record);

    List<Region> selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
}
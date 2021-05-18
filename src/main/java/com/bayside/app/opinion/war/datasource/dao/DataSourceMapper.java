package com.bayside.app.opinion.war.datasource.dao;

import java.util.List;

import com.bayside.app.opinion.war.datasource.model.DataSource;

public interface DataSourceMapper {
    int deleteByPrimaryKey(String id);

    int insert(DataSource record);

    int insertSelective(DataSource record);

    DataSource selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataSource record);

    int updateByPrimaryKey(DataSource record);
    List<DataSource> selectByName(DataSource record);
}
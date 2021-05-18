package com.bayside.app.opinion.war.datasource.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bayside.app.opinion.war.datasource.model.DataSource;

public interface DataSourceService {
   int insertDateSource(DataSource record);
   DataSource selectDataSourceById(String id);
   List<DataSource> selectByName(DataSource record);
   List<DataSource> analyzeWeiboBySerch(String html,HttpServletRequest request);
   List<DataSource> alistieba(String name,HttpServletRequest request);
}

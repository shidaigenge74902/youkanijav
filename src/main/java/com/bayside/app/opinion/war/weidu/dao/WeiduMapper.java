package com.bayside.app.opinion.war.weidu.dao;

import java.util.List;

import com.bayside.app.opinion.war.weidu.model.Weidu;

public interface WeiduMapper {
    int deleteByPrimaryKey(String id);

    int insert(Weidu record);

    int insertSelective(Weidu record);

    Weidu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Weidu record);

    int updateByPrimaryKey(Weidu record);
    List<Weidu> selectByTradeId(String tradeid);
    int deleteWeiDuByRTradeId(String tradeid);
}
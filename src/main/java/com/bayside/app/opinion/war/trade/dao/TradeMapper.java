package com.bayside.app.opinion.war.trade.dao;

import java.util.List;

import com.bayside.app.opinion.war.trade.bo.TradeBo;
import com.bayside.app.opinion.war.trade.model.Trade;

public interface TradeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Trade record);

    int insertSelective(TradeBo record);

    Trade selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TradeBo record);

    int updateByPrimaryKey(Trade record);
    List<Trade> selectAllTrade();
}
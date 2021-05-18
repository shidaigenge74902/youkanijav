package com.bayside.app.opinion.war.trade.service;

import java.util.List;

import com.bayside.app.opinion.war.trade.bo.TradeBo;
import com.bayside.app.opinion.war.trade.model.Trade;

public interface TradeService {
   int insertTrade(TradeBo record);
   int updateTrade(TradeBo record);
   int deleteTrade(String id);
   List<Trade> selectAllTrade();
   Trade selectTradeById(String id);
}

package com.bayside.app.opinion.war.trade.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.trade.bo.TradeBo;
import com.bayside.app.opinion.war.trade.dao.TradeMapper;
import com.bayside.app.opinion.war.trade.model.Trade;
import com.bayside.app.opinion.war.trade.service.TradeService;
@Service("")
public class TradeServiceImpl implements TradeService {
	@Autowired
    private TradeMapper tradeMapper;

	@Override
	public int insertTrade(TradeBo record) {
		// TODO Auto-generated method stub
		return tradeMapper.insertSelective(record);
	}

	@Override
	public int updateTrade(TradeBo record) {
		// TODO Auto-generated method stub
		return tradeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteTrade(String id) {
		// TODO Auto-generated method stub
		return tradeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Trade> selectAllTrade() {
		// TODO Auto-generated method stub
		return tradeMapper.selectAllTrade();
	}

	@Override
	public Trade selectTradeById(String id) {
		// TODO Auto-generated method stub
		return tradeMapper.selectByPrimaryKey(id);
	}

}

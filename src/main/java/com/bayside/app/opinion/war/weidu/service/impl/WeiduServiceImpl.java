package com.bayside.app.opinion.war.weidu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.weidu.dao.WeiduMapper;
import com.bayside.app.opinion.war.weidu.model.Weidu;
import com.bayside.app.opinion.war.weidu.service.WeiDuService;
@Service("weiduServiceImpl")
public class WeiduServiceImpl implements WeiDuService {
	@Autowired
    private WeiduMapper weiduMapper;
	

	@Override
	public int deleteWeidu(String id) {
		// TODO Auto-generated method stub
		return weiduMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertWeidu(Weidu record) {
		// TODO Auto-generated method stub
		return weiduMapper.insertSelective(record);
	}

	@Override
	public List<Weidu> selectByTradeId(String tradeid) {
		// TODO Auto-generated method stub
		return weiduMapper.selectByTradeId(tradeid);
	}

	@Override
	public int updateWeidu(Weidu record) {
		// TODO Auto-generated method stub
		return weiduMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Weidu selectByWeiduId(String id) {
		// TODO Auto-generated method stub
		return weiduMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteWeiDuByRTradeId(String tradeid) {
		// TODO Auto-generated method stub
		return weiduMapper.deleteWeiDuByRTradeId(tradeid);
	}

}

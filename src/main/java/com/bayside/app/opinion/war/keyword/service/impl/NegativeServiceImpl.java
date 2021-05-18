package com.bayside.app.opinion.war.keyword.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.keyword.dao.NegativeWordMapper;
import com.bayside.app.opinion.war.keyword.model.NegativeWord;
import com.bayside.app.opinion.war.keyword.service.NegativeWordService;
@Service("negativeServiceImpl")
public class NegativeServiceImpl implements NegativeWordService {
	@Autowired
    private NegativeWordMapper negativeWordMapper;
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return negativeWordMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertNegative(NegativeWord record) {
		// TODO Auto-generated method stub
		return negativeWordMapper.insertSelective(record);
	}

	@Override
	public int updateNegative(NegativeWord record) {
		// TODO Auto-generated method stub
		return negativeWordMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<NegativeWord> selectByweiduid(String weiduid) {
		// TODO Auto-generated method stub
		return negativeWordMapper.selectByweiduid(weiduid);
	}

	@Override
	public NegativeWord selectWordById(String id) {
		// TODO Auto-generated method stub
		return negativeWordMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteWordByWeiduId(String weiduid) {
		// TODO Auto-generated method stub
		return negativeWordMapper.deleteWordByWeiduId(weiduid);
	}

}

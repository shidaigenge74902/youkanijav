package com.bayside.app.opinion.war.baobei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.baobei.bo.BaoBeiBo;
import com.bayside.app.opinion.war.baobei.dao.BaoBeiMapper;
import com.bayside.app.opinion.war.baobei.model.BaoBei;
import com.bayside.app.opinion.war.baobei.service.BaoBeiService;
@Service("baoBeiServiceImpl")
public class BaoBeiServiceImpl implements BaoBeiService {
	@Autowired
	private BaoBeiMapper baoBeiMapper;
	@Override
	public int deleteBaoBei(String id) {
		// TODO Auto-generated method stub
		return baoBeiMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int updateBaoBei(BaoBeiBo record) {
		// TODO Auto-generated method stub
		return baoBeiMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertBaoBei(BaoBeiBo record) {
		// TODO Auto-generated method stub
		return baoBeiMapper.insertSelective(record);
	}

	@Override
	public List<BaoBei> selectByOrgname(BaoBeiBo record) {
		// TODO Auto-generated method stub
		return baoBeiMapper.selectByOrgname(record);
	}

	@Override
	public List<BaoBei> selectByTime(String orgname) {
		// TODO Auto-generated method stub
		return baoBeiMapper.selectByTime(orgname);
	}

	@Override
	public BaoBei selectBaoBeiById(String id) {
		// TODO Auto-generated method stub
		return baoBeiMapper.selectByPrimaryKey(id);
	}

}

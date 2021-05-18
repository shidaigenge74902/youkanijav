package com.bayside.app.opinion.war.wordinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.wordinfo.dao.WordInfoMapper;
import com.bayside.app.opinion.war.wordinfo.model.WordInfo;
import com.bayside.app.opinion.war.wordinfo.service.WordInfoService;
@Service("wordInfoServiceImpl")
public class WordInfoServiceImpl implements WordInfoService {
	@Autowired
    private WordInfoMapper wordInfoMapper;
	@Override
	public int deleteWordInfo(String id) {
		// TODO Auto-generated method stub
		return wordInfoMapper.deleteByPrimaryKey(id);
	}
	@Override
	public int insertWordInfo(WordInfo record) {
		// TODO Auto-generated method stub
		return wordInfoMapper.insertSelective(record);
	}
	@Override
	public WordInfo selecWordInfoById(String id) {
		// TODO Auto-generated method stub
		return wordInfoMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateWordInfo(WordInfo record) {
		// TODO Auto-generated method stub
		return wordInfoMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<WordInfo> selectAllWordInfo() {
		// TODO Auto-generated method stub
		return wordInfoMapper.selectAllWordInfo();
	}
	@Override
	public List<WordInfo> selectWordInfoByName(WordInfo record) {
		// TODO Auto-generated method stub
		return wordInfoMapper.selectWordInfoByName(record);
	}

}

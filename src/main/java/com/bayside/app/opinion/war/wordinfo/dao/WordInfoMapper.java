package com.bayside.app.opinion.war.wordinfo.dao;

import java.util.List;

import com.bayside.app.opinion.war.wordinfo.model.WordInfo;

public interface WordInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WordInfo record);

    int insertSelective(WordInfo record);

    WordInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WordInfo record);

    int updateByPrimaryKey(WordInfo record);
    List<WordInfo> selectAllWordInfo();
    
    List<WordInfo> selectWordInfoByName(WordInfo record);
}
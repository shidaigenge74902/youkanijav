package com.bayside.app.opinion.war.keyword.dao;

import java.util.List;

import com.bayside.app.opinion.war.keyword.model.NegativeWord;

public interface NegativeWordMapper {
    int deleteByPrimaryKey(String id);

    int insert(NegativeWord record);

    int insertSelective(NegativeWord record);

    NegativeWord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(NegativeWord record);

    int updateByPrimaryKey(NegativeWord record);
    List<NegativeWord> selectByweiduid(String weiduid);
    int deleteWordByWeiduId(String weiduid);
}
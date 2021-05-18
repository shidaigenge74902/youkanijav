package com.bayside.app.opinion.war.keyword.service;

import java.util.List;

import com.bayside.app.opinion.war.keyword.model.NegativeWord;

public interface NegativeWordService {
	int deleteByPrimaryKey(String id);
	 int insertNegative(NegativeWord record);
	 int updateNegative(NegativeWord record);
	 List<NegativeWord> selectByweiduid(String weiduid);
	 NegativeWord selectWordById(String id);
	 int deleteWordByWeiduId(String weiduid);
	 

}

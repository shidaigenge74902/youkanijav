package com.bayside.app.opinion.war.wordinfo.service;

import java.util.List;

import com.bayside.app.opinion.war.wordinfo.model.WordInfo;

public interface WordInfoService {
  int deleteWordInfo(String id);
  int insertWordInfo(WordInfo record);
  WordInfo selecWordInfoById(String id);
  int updateWordInfo(WordInfo record);
  List<WordInfo> selectAllWordInfo();
  List<WordInfo> selectWordInfoByName(WordInfo record);
}

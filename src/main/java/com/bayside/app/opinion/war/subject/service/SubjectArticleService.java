package com.bayside.app.opinion.war.subject.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bayside.app.opinion.war.subject.bo.MetaSearch;
import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.model.SubjectMArticle;

public interface SubjectArticleService {
  Boolean insertSubjectArticle(SubJectArticleBo record);
  int insertSubjectMArticle(SubjectMArticleBo record);
  List<Subject> selectSubjectByUserid(String userid);
  MetaSearch alisUrl(String titleLinks,String userid,String subjectid);
  List<SubJectArticleBo> filterCom(SubJectArticleBo record);
  int updateSMArticle(SubjectMArticle record);
  List<SubjectArticle> selectAllById(List<String> id);
  Map<String, Object> dowloadExcel(SubJectArticleBo record,HttpServletResponse response,HttpServletRequest request);
  Map<String, Object> alldowloadExcel(SubJectArticleBo record,HttpServletResponse response,HttpServletRequest request);
  int deleteSMArticle(String id);
  SubJectArticleBo selectArticleDetailById(String articleid);
  SubjectMArticle selectSMById(String id);
  int updateEmotion(SubjectMArticle record);
  List<SubJectArticleBo> getSimArticle(SubJectArticleBo record);
  Map<String, Object> dowloadfilterCom(SubJectArticleBo record, HttpServletResponse response, HttpServletRequest request);
  List<SubjectMArticle> selectBySubjectid(SubjectMArticle record);
  int selectTotalByUserid(String userid);
  int updateSubjectByUserid(Subject record);
}

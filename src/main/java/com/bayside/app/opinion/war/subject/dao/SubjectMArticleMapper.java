package com.bayside.app.opinion.war.subject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.subject.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.subject.model.SubjectMArticle;


/**
 * <p>Title: SubjectMArticleMapper</P>
 * <p>Description:专题与专题文章的中间表的映射类 </p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectMArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectMArticle record);

    int insertSelective(SubjectMArticleBo record);

    SubjectMArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectMArticle record);

    int updateByPrimaryKey(SubjectMArticle record);
    int updateSMArticle(SubjectMArticle record);
    List<SubjectMArticle> selectBySubjectid(SubjectMArticle record);
    SubjectMArticle selectTotalByUserid(String userid);
    SubjectMArticle selectMinPundateBigData(String userid);
    SubjectMArticle selectMinPundate(String userid);
    
	
}

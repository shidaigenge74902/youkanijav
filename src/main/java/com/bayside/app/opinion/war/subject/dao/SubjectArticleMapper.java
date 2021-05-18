package com.bayside.app.opinion.war.subject.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;

/**
 * <p>Title: SubjectArticleMapper</P>
 * <p>Description: 专题文章的映射表</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectArticle record);

    int insertSelective(SubJectArticleBo record);

    SubjectArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectArticle record);

    int updateByPrimaryKey(SubjectArticle record);
    List<SubjectArticle> selectAllById(List<String> id);
    List<Map<String,Object>> selectArticleByUserid(String userid);
    
    String selectSimhashcode(String id);
    
    List<SubjectArticle> selectSiteScreenPage(SubjectArticle subjectArticle);
   
    List<SubjectArticle> selectSiteScreen(SubjectArticle subjectArticle);

	int updateScreening(SubjectArticle subjectArticle);

}
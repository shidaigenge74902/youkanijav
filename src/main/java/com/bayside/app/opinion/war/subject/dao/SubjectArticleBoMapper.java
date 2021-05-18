package com.bayside.app.opinion.war.subject.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.bo.SubjectMArticleBo;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;



/**
 * <p>Title: SubjectArticleMapper</P>
 * <p>Description: 专题文章的映射表</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Lixp
 * @version 1.0
 * @since 2016年7月23日
 */
public interface SubjectArticleBoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SubjectArticle record);

    int insertSelective(SubjectArticle record);

    SubjectArticle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubjectArticle record);

    int updateByPrimaryKey(SubjectArticle record);
    List<SubJectArticleBo> filterCom(SubJectArticleBo record);
    List<SubJectArticleBo> dowloadfilterCom(List<String> id);
    List<SubJectArticleBo> filterComlist(List<String> list);
    
   
}


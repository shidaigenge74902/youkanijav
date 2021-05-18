package com.bayside.app.opinion.war.baobei.dao;

import java.util.List;

import com.bayside.app.opinion.war.baobei.bo.BaoBeiBo;
import com.bayside.app.opinion.war.baobei.model.BaoBei;

public interface BaoBeiMapper {
    int deleteByPrimaryKey(String id);

    int insert(BaoBei record);

    int insertSelective(BaoBeiBo record);

    BaoBei selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BaoBeiBo record);

    int updateByPrimaryKey(BaoBei record);
    List<BaoBei> selectByOrgname(BaoBeiBo record);
    List<BaoBei> selectByTime(String orgname);
}
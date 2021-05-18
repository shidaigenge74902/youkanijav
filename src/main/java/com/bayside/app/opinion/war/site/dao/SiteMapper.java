package com.bayside.app.opinion.war.site.dao;

import java.util.List;

import com.bayside.app.opinion.war.site.bo.SiteBo;
import com.bayside.app.opinion.war.site.model.Site;

public interface SiteMapper {
    int deleteByPrimaryKey(String id);

    int insert(Site record);

    int insertSelective(Site record);

    Site selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);
    List<Site> selectByTiaojian(Site record);
    
    Site selectsiteBydomain(String domain);
    List<Site> selectSiteByMain(String domain);
    List<Site> selectUpdateSiteByMain(Site record);
    int insertSelectiveBo(SiteBo record);
    int updateByPrimaryKeySelectiveBo(SiteBo record);
    List<Site> selectAllSite();
    int bathSite(Site record);
}
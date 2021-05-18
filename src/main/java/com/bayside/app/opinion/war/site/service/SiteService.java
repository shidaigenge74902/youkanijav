package com.bayside.app.opinion.war.site.service;

import java.io.IOException;
import java.util.List;

import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.site.bo.SiteBo;
import com.bayside.app.opinion.war.site.model.Site;
import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.util.SolrPage;

public interface SiteService {
	 int insertSite(SiteBo record);

	 Site selectSiteById(String id);

	 int updateSiteById(SiteBo record);
	 
	 List<Site> selectSiteByTiaojian(Site record);
	 
	 int deleteSiteById(String id);

	List<SubjectArticle> getSiteScreen(SubjectArticle subjectArticle);
	/**
	 * 
	 * <p>方法名称：updateSiteScreen</p>
	 * <p>方法描述：修改甄别网站信息</p>
	 * @param subJectArticleBo
	 * @return
	 * @author Administrator
	 * @since  2017年3月21日
	 * <p> history 2017年3月21日 Administrator  创建   <p>
	 */
	int updateSiteScreen(SubJectArticleBo subJectArticleBo);

	SolrPage<SubjectArticle> getSiteScreenPage(SubjectArticle subjectArticle);
	 List<Site> selectSiteByMain(String domain);
	 List<Site> selectUpdateSiteByMain(Site record);
	 List<Subject> selectAllSubject();
	 List<User> selectAllUser();
	 List<Site> selectAllSite();
	 List<Site> selectAllSiteInfo();
}

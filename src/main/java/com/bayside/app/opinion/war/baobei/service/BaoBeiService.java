package com.bayside.app.opinion.war.baobei.service;

import java.util.List;

import com.bayside.app.opinion.war.baobei.bo.BaoBeiBo;
import com.bayside.app.opinion.war.baobei.model.BaoBei;

public interface BaoBeiService {
   int deleteBaoBei(String id);
   int updateBaoBei(BaoBeiBo record);
   int insertBaoBei(BaoBeiBo record);
   List<BaoBei> selectByOrgname(BaoBeiBo record);
   List<BaoBei> selectByTime(String orgname);
   BaoBei selectBaoBeiById(String id);
}

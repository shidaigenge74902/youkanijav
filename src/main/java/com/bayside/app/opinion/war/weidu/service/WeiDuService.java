package com.bayside.app.opinion.war.weidu.service;

import java.util.List;

import com.bayside.app.opinion.war.weidu.model.Weidu;

public interface WeiDuService {
   int deleteWeidu(String id);
   int insertWeidu(Weidu record);
   List<Weidu> selectByTradeId(String tradeid);
   int updateWeidu(Weidu record);
   Weidu selectByWeiduId(String id);
   int deleteWeiDuByRTradeId(String tradeid);
   
}

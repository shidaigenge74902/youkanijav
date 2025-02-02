package com.bayside.app.opinion.war.mymessage.service;

import java.util.List;

import com.bayside.app.opinion.war.mymessage.bo.MessageCenterBo;
import com.bayside.app.opinion.war.mymessage.model.Messagecenter;
import com.bayside.app.opinion.war.mymessage.model.OfficalUser;

public interface MyMessageService {
	  int insert(Messagecenter record);

	  int insertSelective(Messagecenter record);

	  Messagecenter selectByPrimaryKey(String id);
      int updateInfo(Messagecenter record);
      List<Messagecenter> selectAllMessageByTag(Messagecenter record);
      List<Messagecenter> checkAllMessage(MessageCenterBo record);
      List<Messagecenter> selectAllMessageByUserid(MessageCenterBo record);
     
}

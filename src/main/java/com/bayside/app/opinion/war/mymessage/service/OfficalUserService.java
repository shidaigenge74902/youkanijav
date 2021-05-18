package com.bayside.app.opinion.war.mymessage.service;

import java.util.List;

import com.bayside.app.opinion.war.mymessage.bo.OfficalUserBo;
import com.bayside.app.opinion.war.mymessage.model.OfficalUser;

public interface OfficalUserService {
	List<OfficalUser> selectAllOffical(OfficalUserBo record);
}

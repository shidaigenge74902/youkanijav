package com.bayside.app.opinion.war.mymessage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.mymessage.bo.OfficalUserBo;
import com.bayside.app.opinion.war.mymessage.dao.OfficalUserMapper;
import com.bayside.app.opinion.war.mymessage.model.OfficalUser;
import com.bayside.app.opinion.war.mymessage.service.OfficalUserService;
@Service("officalUserServiceImpl")
public class OfficalUserServiceImpl implements OfficalUserService {
	@Autowired
    private OfficalUserMapper officalUserMapper;
	
	@Override
	public List<OfficalUser> selectAllOffical(OfficalUserBo record) {
		// TODO Auto-generated method stub
		return officalUserMapper.selectAllOffical(record);
	}

}

package com.bayside.app.opinion.war.mymessage.dao;

import java.util.List;

import com.bayside.app.opinion.war.mymessage.bo.OfficalUserBo;
import com.bayside.app.opinion.war.mymessage.model.OfficalUser;



public interface OfficalUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(OfficalUser record);

    int insertSelective(OfficalUser record);

    OfficalUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OfficalUser record);

    int updateByPrimaryKey(OfficalUser record);
    
    List<OfficalUser> selectByMobile(String mobilephone);
    List<OfficalUser> selectAllOffical(OfficalUserBo record);
} 
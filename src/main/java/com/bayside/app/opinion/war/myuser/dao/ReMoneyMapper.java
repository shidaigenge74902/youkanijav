package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.bo.ReMoneyBo;
import com.bayside.app.opinion.war.myuser.model.ReMoney;

public interface ReMoneyMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReMoney record);

    int insertSelective(ReMoneyBo record);

    ReMoney selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReMoneyBo record);

    int updateByPrimaryKey(ReMoney record);
    List<ReMoney> selectByExpirtime(ReMoney record);
    List<ReMoney> selectPayUser(ReMoney record);
    ReMoney succAddress();
}
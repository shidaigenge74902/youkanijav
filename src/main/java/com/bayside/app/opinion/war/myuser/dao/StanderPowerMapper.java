package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.model.StanderPower;

public interface StanderPowerMapper {
    int deleteByPrimaryKey(StanderPower record);

    int insert(StanderPower record);

    int insertSelective(StanderPower record);

    StanderPower selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StanderPower record);

    int updateByPrimaryKey(StanderPower record);
    
    StanderPower selectStanderPower(StanderPower record);
    StanderPower checkStanderPower(StanderPower record);
    List<StanderPower> selectnewStanderPower(StanderPower record);
    StanderPower selectOnlyStanderPower(StanderPower record);
    List<StanderPower> selectStanderByTypeid();
    int batchInsertStander(StanderPower record);
    int bathupdateStanderPower(StanderPower record);
}
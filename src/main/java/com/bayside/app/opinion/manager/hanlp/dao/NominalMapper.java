package com.bayside.app.opinion.manager.hanlp.dao;

import java.util.List;

import com.bayside.app.opinion.manager.hanlp.model.Nominal;

public interface NominalMapper {
    int deleteByPrimaryKey(String nominal);

    int insert(Nominal record);

    int insertSelective(Nominal record);

    Nominal selectByPrimaryKey(String nominal);

    int updateByPrimaryKeySelective(Nominal record);

    int updateByPrimaryKey(Nominal record);
    List<Nominal> selectAllNominal(Nominal record);
}
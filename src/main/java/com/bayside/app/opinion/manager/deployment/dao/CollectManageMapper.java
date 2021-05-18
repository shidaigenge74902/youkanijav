package com.bayside.app.opinion.manager.deployment.dao;

import com.bayside.app.opinion.manager.deployment.model.CollectManage;

public interface CollectManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollectManage record);

    int insertSelective(CollectManage record);

    CollectManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectManage record);

    int updateByPrimaryKey(CollectManage record);
    
    int selectNextId();
}
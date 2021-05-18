package com.bayside.app.opinion.manager.deployment.dao;

import com.bayside.app.opinion.manager.deployment.model.DeploymentNum;

public interface DeploymentNumMapper {
    int deleteByPrimaryKey(String id);

    int insert(DeploymentNum record);

    int insertSelective(DeploymentNum record);

    DeploymentNum selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DeploymentNum record);

    int updateByPrimaryKey(DeploymentNum record);
}
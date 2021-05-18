package com.bayside.app.opinion.manager.deployment.dao;

import java.util.List;

import com.bayside.app.opinion.manager.deployment.model.ServerManage;

public interface ServerManageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerManage record);

    int insertSelective(ServerManage record);

    ServerManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerManage record);

    int updateByPrimaryKey(ServerManage record);
    List<ServerManage> selectAll();
    List<ServerManage> selectCollect();
}
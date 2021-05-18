package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import com.bayside.app.opinion.war.myuser.bo.ManagerUserBo;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;

public interface ManagerUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(ManagerUser record);

    int insertSelective(ManagerUser record);

    ManagerUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ManagerUser record);

    int updateByPrimaryKey(ManagerUser record);
    List<ManagerUser> selectAllManager(ManagerUser record);
    List<ManagerUser> selectManager();
    ManagerUser selectManagerByPhone(ManagerUser record);
    ManagerUser selectManagerByName(ManagerUser record);
    List<ManagerUser> selectManagerUserByName(ManagerUser record);
    ManagerUser selectInfoByPa(ManagerUser record);
    int updateManagerUserPass(ManagerUser record);
    List<ManagerUser> selectManagerById(UserBo record);
    List<ManagerUser> selectByTag(ManagerUser record);
    List<ManagerUser> selectManagerByNick(String name);
    List<ManagerUser> selectByParentId();
    List<ManagerUser> selectSonManager(String parentid);
    List<ManagerUser> selectByTag();
}
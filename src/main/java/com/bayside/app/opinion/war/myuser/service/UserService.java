package com.bayside.app.opinion.war.myuser.service;

import java.util.List;

import com.bayside.app.opinion.war.myuser.bo.ReMoneyBo;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.ReMoney;
import com.bayside.app.opinion.war.myuser.model.Resources;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.model.UserLog;



public interface UserService {
  int saveUser(User user);
  User selectUser(User user);
  List<User> selectByName(User user);
  List<User> selectByTel(User user);
  List<User> selectByEmail(User user);
  User querySingleUser(String userName);
  List<User> selectAllagent(UserBo record);
  int insertagentuser(User record);
  int updateagentuser(User record);
  User selectagentById(String id);
  List<User> selectuserInfo(User record);
 
  int insertSelective(UserLogBo record);
  int insertWordSet(WordSet record);
  int updateWordSet(WordSet record);
  List<WordSet> selectAllWordSet(String userid);
  WordSet selectPowerByName(WordSet record);
  User selectByPrimaryKey(String id);
  List<WordSet> selectPowerByUserId(WordSet record);
  int insert(UserType record);
  List<UserType> selectUserType();
 // List<StanderPower> selectStanderPower(StanderPower record);
  int insertPower(StanderPower record);
  UserTypeBo selectUserTypeBo(String typeid,String name);
  int updateUserType(UserType record);
  
  int updateStanderPower(StanderPower record);
  
  public Boolean updateUS(UserTypeBo record);
  
  int deleteUserType(String id);
  int deleteStanderPower(StanderPower record);
  UserType selectByTypeName(String typename);
  List<User> selectByUserType(String usertype);
  int insertSelective(ReMoneyBo record);
  //此时间段内客户是否处于 已经回款状态
  List<ReMoney> selectByExpirtime(ReMoney record);
  int updateCaiWu(ReMoneyBo record);
  User selectUserAndPowerById(UserBo record);
  List<User> selectByReport();
  List<ManagerUser> selectManagerByNick(String name);
  List<ManagerUser> selectManager();
  int insertTradePower();
  int wordsetTrade();
  
}

package com.bayside.app.opinion.war.power.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.bayside.app.opinion.war.myuser.bo.ManagerUserBo;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.Resources;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.SubjectClassify;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;


public interface PowerUserService {
	  String uploadImg(String name,MultipartFile mulfile);
	  int saveUser(User user);
	  User selectUser(User user);
	  List<User> selectByName(User user);
	  List<User> selectByTel(User user);
	  List<User> selectByEmail(User user);
	  User querySingleUser(String userName);
	 /* int countUser(String userName,String userPassword);*/
	  
	 
	  //修改客户的销售员
	  User selectUserById(String id);
	  int updateUserById(User record);
	  
	  
	  List<User> selectAllagent(UserBo record);
	  int insertagentuser(User record);
	  int updateagentuser(User record);
	  User selectagentById(String id);
	  UserBo checkagentById(String id,User user);
	  List<User> selectuserInfo(User record);
	  int insertWordSet(WordSet record);
	  int updateWordSet(WordSet record);
	  List<WordSet> selectAllWordSet(String userid);
	  WordSet selectPowerByName(WordSet record);
	  User selectByPrimaryKey(String id);
	  List<WordSet> selectPowerByUserId(WordSet record);
	  int insert(UserType ut,UserTypeBo record);
	  List<UserType> selectUserType();
	  StanderPower selectStanderPower(StanderPower record);
	  List<StanderPower> selectnewStanderPower(StanderPower record);
	  int insertPower(StanderPower record);
	  UserTypeBo selectUserTypeBo(String typeid,String name);
	  int updateUserType(UserType record);
	  
	  int updateStanderPower(StanderPower record);
	  
	  public Boolean updateUS(UserTypeBo record);
	  
	  int deleteUserType(String id);
	  int deleteStanderPower(StanderPower record);
	  UserType selectByTypeName(String typename);
	  List<User> selectByUserType(String usertype);
	  int updatePassword(User user);
	  int updateUserStatus(User user);
	  int updateUserShen(User user);
	  int updateUserAttr(User user);
	  List<UserTypeBo> selectUserTypeBo();
	  User updateUserA(UserBo record);
	  
	  User userShen(UserBo record);
	  List<User> selectid();
	  int specialUser(User user);
	  List<User> selectByEndTime(User user);
	  int insertSubjectClassify(SubjectClassify record);
	  int insertManagerPuTong(ManagerUser record);
	  List<ManagerUser> selectAllManager(ManagerUser record);
	  int updateManageUser(ManagerUser record);
	  int deleteManagerUser(String id);
	  List<ManagerUser> selectManager();
	  ManagerUser selectManagerUserById(String id);
	  List<UserLog> selectUserLog();
	  List<UserLog> filterSelectUserLog(UserLogBo record);
	  ManagerUser selectManagerByPhone(ManagerUser record);
	  List<ManagerUser> selectManagerUserByName(ManagerUser record);
	  ManagerUser selectManagerByName(ManagerUser record);
	  ManagerUser selectInfoByPa(ManagerUser record);
	  List<User> selectUserByManagerId(User user);
	  List<UserLog> selectAllUserLogByIds(UserLogBo record);
	  int updateManagerUserPass(ManagerUser record);
	   //所有业务员下面的不同用户的总数
	  UserBo selectTotalNumber(UserBo record);
	  List<User> selectUserList(UserBo record);
	  List<UserBo> selectUserNumber(UserBo record);
	  List<UserBo> selectDetailUserList(UserBo record);
	  String exportUserPower(HttpServletRequest request, HttpServletResponse response, UserBo record);
	    
	  String exportUserList(HttpServletRequest request, HttpServletResponse response,UserBo record);
	  UserBo selectTotalUserNumber(UserBo record);
      //用户总数
	  List<User> selectUTByMId(UserBo record);
	  List<User> selectUserNameByMId(User record);
	  List<UserBo> selectUserTypeNumber(UserBo record);
	  UserBo selectTotalUserTypeNumber(UserBo record);
	  //
	  List<User> selectUserByManagerIdByM(User user);
	  List<User> selectUserListByM(UserBo record);
	  List<User> selectDetailUserListByM(UserBo record);
	  List<User> selectUserNameByMIdByM(User record);
	  List<User> selectuserInfoByM(User record);
	  List<User> selectidByM();
	  List<User> selectUserAndPowerByM(UserBo record);
	  List<UserBo> selectUserTypeNumberByM(UserBo record);
	  UserBo selectTotalUserTypeNumberByM(UserBo record);
	  List<ManagerUser> findMenuByparentId(ManagerUser record);

	  List<String> getChids(List<ManagerUser> list,String parentid);
	  UserType selectUserType(String id);
	  List<User> selectByOrgName(String orgname);
	    UserType selectUserTypeById(String id);
	    List<ManagerUser> selectByParentId();
	    List<ManagerUser> selectSonManager(String parentid);
	    List<ManagerUser> selectByTag();
	  
	 
	  List<User> selectuserInfoMaintainAdministrator(User record);
}

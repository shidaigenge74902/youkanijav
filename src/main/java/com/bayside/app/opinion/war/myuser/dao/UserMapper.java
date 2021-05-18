package com.bayside.app.opinion.war.myuser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.User;

public interface UserMapper {
	 int deleteByPrimaryKey(String id);
	 
	 //修改客户销售员
	 User selectUserById(String id);
	 int updateUserById(User record);
	 

	    int insert(User record);

	    int insertSelective(User record);

	    User selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(User record);

	    int updateByPrimaryKey(User record);
	    
	    User selectAll(User user);
	    
	    List<User> selectByName(User user);
	    
	    List<User> selectByTel(User user);
	    
	    List<User> selectByEmail(User user);
	    
	    List<User> selectByParentId(String parentid);
	    int countUser(User user);
		
	    User querySingleUser(@Param("name")String name);
	    
	    List<User> selectAllagent(User record);
	    /**
	     * 
	     * <p>方法名称：selectParentUser</p>
	     * <p>方法描述：查询所有的父级账号</p>
	     * @return
	     * @author sql
	     * @since  2016年10月2日
	     * <p> history 2016年10月2日 sql  创建   <p>
	     */
	    List<User> selectParentUser();

	    List<User> selectuserInfo(User record);
	    List<User> selectuserInfoByM(User record);
	    List<User> selectByUserType(String usertype);
	    int updatePassword(User user);
	    int updateUserStatus(User user);
	    int updateUserShen(User user);
	    int updateUserAttr(User user);
	    int updateUserInfo(User user);
	    int registerUserInfo(User user);
	    List<User> selectid();
	    List<User> selectidByM();
	    List<User> selectByEndTime(User user);
	    List<User> selectUserByManagerId(User user);
	    List<User> selectUserByManagerIdByM(User user);
	    UserBo selectTotalNumber(UserBo record);
	    List<User> selectUserList(UserBo record);
	    List<User> selectUserListByM(UserBo record);
	    UserBo selectUserType(UserBo record);
	    UserBo selectUserStatus(UserBo record);
	    UserBo selectExpirUser(UserBo record);
	    List<User> selectDetailUserList(UserBo record);
	    List<User> selectDetailUserListByM(UserBo record);
	    List<User> selectUserNameByMId(User record);
	    List<User> selectUserNameByMIdByM(User record);
	    List<User> selectUserAndPower(UserBo record);
	    List<User> selectUserAndPowerByM(UserBo record);
	    List<UserBo> selectUserTypeNumber(UserBo record);
	    List<UserBo> selectUserTypeNumberByM(UserBo record);
	    UserBo selectTotalUserTypeNumber(UserBo record);
	    UserBo selectTotalUserTypeNumberByM(UserBo record);
	    User selectUserAndPowerById(UserBo record);
	    List<User> selectByReport();
	  /*  List<ManagerUser> findMenuByparentId(String managerid);*/

		List<ManagerUser> findMenuByparentId(ManagerUser record);
		UserBo selectWordSetPowerByUserId(UserBo record);
		List<User> selectByOrgName(String orgname);
		List<User> selectAllUser();

		List<User> selectuserInfoMaintainAdministrator(User record);
}
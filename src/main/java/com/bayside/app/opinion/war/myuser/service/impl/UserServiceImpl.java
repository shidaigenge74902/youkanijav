package com.bayside.app.opinion.war.myuser.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bayside.app.opinion.war.myuser.bo.ReMoneyBo;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.dao.ManagerUserMapper;
import com.bayside.app.opinion.war.myuser.dao.ReMoneyMapper;
import com.bayside.app.opinion.war.myuser.dao.StanderPowerMapper;
import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.dao.UserTypeMapper;
import com.bayside.app.opinion.war.myuser.dao.WordSetMapper;
import com.bayside.app.opinion.war.myuser.dao.userLogMapper;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.ReMoney;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.UuidUtil;
@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapper userMapper;
	@Autowired
	private userLogMapper userLogMapper;
	@Autowired
	private WordSetMapper wordSetMapper;
	@Autowired
	private UserTypeMapper userTypeMapper;
	@Autowired
	private StanderPowerMapper standerPowerMapper;
	@Autowired
	private ReMoneyMapper reMoneyMapper;
	@Autowired
	private ManagerUserMapper managerUserMapper;
    //@Override
	public int saveUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(user);
	}

	@Override
	public User selectUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectAll(user);
	}

	@Override
	public List<User> selectByName(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByName(user);
	}

	@Override
	public List<User> selectByTel(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByTel(user);
	}
	@Override
	public User querySingleUser(String userName) {
		// TODO Auto-generated method stub
		return userMapper.querySingleUser(userName);
	}
	@Override
	public List<User> selectByEmail(User user) {
		// TODO Auto-generated method stub
		
		return userMapper.selectByEmail(user);
	}
	@Override
	public List<User> selectAllagent(UserBo record) {
		// TODO Auto-generated method stub
		User us = new User();
		BeanUtils.copyProperties(record, us);
		return userMapper.selectAllagent(us);
	}

	@Override
	public int insertagentuser(User record) {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(record);
	}

	@Override
	public int updateagentuser(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public User selectagentById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> selectuserInfo(User record) {
		// TODO Auto-generated method stub
		return userMapper.selectuserInfo(record);
	}

	

	@Override
	public int insertSelective(UserLogBo record) {
		// TODO Auto-generated method stub
		return userLogMapper.insertSelective(record);
	}

	@Override
	public int insertWordSet(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.insertSelective(record);
	}

	@Override
	public int updateWordSet(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<WordSet> selectAllWordSet(String userid) {
		// TODO Auto-generated method stub
		return wordSetMapper.selectByPrimaryKey(userid);
	}

	@Override
	public WordSet selectPowerByName(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.selectPowerByName(record);
	}

	@Override
	public User selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<WordSet> selectPowerByUserId(WordSet record) {
		// TODO Auto-generated method stub
		return wordSetMapper.selectPowerByUserId(record);
	}

	@Override
	public int insert(UserType record) {
		//int s = userTypeMapper.insertSelective(record);
		// TODO Auto-generated method stub
		/*//权限默认值
	    List<String> list = new ArrayList<String>();
	    list.add("人物个数");
	    list.add("微监测监测项个数");
	    list.add("专题个数");
	    list.add("云搜索次数");
	    list.add("预警信息设置个数");
	    list.add("子账号个数");
	    list.add("代理商个数");
		for(int i=0;i<list.size();i++){
			StanderPower sp = new StanderPower();
			sp.setId(UuidUtil.getUUID());
			sp.setSetword(0);
			if(list.get(i).equals("人物个数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			if(list.get(i).equals("微监测监测项个数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			if(list.get(i).equals("专题个数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			if(list.get(i).equals("云搜索次数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			if(list.get(i).equals("预警信息设置个数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			if(list.get(i).equals("子账号个数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			if(list.get(i).equals("代理商个数")){
				sp.setCansetword(10);
				sp.setName(list.get(i));
			}
			sp.setTypeid(record.getId());
			sp.setTypename(record.getTypename());
			int  num = standerPowerMapper.insertSelective(sp);
		}*/
		return userTypeMapper.insertSelective(record);
	}

	@Override
	public List<UserType> selectUserType() {
		// TODO Auto-generated method stub
		return userTypeMapper.selectUserType();
	}

	/*@Override
	public List<StanderPower> selectStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.selectStanderPower(record);
	}
*/
	@Override
	public int insertPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.insertSelective(record);
	}

	@Override
	public UserTypeBo selectUserTypeBo(String typeid,String name) {
		// TODO Auto-generated method stub
		StanderPower sp = new StanderPower();
		sp.setTypeid(typeid);
		List<StanderPower> listsp = standerPowerMapper.selectnewStanderPower(sp);
		UserTypeBo ub = new UserTypeBo();
		ub.setId(typeid);
		ub.setTypename(name);
		 for(int j=0;j<listsp.size();j++){
			  /*if(listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)){
				  ub.setAgentnum(listsp.get(j).getCansetword());
			  }*/
			  if(listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)){
				  ub.setCloudnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)){
				  ub.setJiancenum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)){
				  ub.setPersonnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)){
				  ub.setSonnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)){
				  ub.setSubjectnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)){
				  ub.setYujingnum(listsp.get(j).getCansetword());
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.EMPHASISNAME)){
				  ub.setEmphasisnum(listsp.get(j).getCansetword());;
			  }
		  }
		 
	    
		return ub;
	}
	@Override
	public int updateUserType(UserType record) {
		// TODO Auto-generated method stub
		return userTypeMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public int updateStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.updateByPrimaryKeySelective(record);
	}
	public Boolean updateUS(UserTypeBo record){	
		UserType ut = new UserType();
		BeanUtils.copyProperties(record, ut);
		int num = userTypeMapper.updateByPrimaryKeySelective(ut);
		StanderPower sp = new StanderPower();
		sp.setTypeid(record.getId());
		//sp.setTypename(record.getTypename());
		List<StanderPower> listsp = standerPowerMapper.selectnewStanderPower(sp);
		 for(int j=0;j<listsp.size();j++){
			   StanderPower spw = new StanderPower();
				spw.setTypeid(record.getId());
				spw.setTypename(record.getTypename());
			 /* if(listsp.get(j).getName().equals(AppConstant.standPower.AGENTNAME)){
				   spw.setCansetword(record.getAgentnum());
				   spw.setName(AppConstant.standPower.AGENTNAME);
				  standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }*/
			  if(listsp.get(j).getName().equals(AppConstant.standPower.CLOUDNAME)){
				    spw.setCansetword(record.getCloudnum());
				    spw.setName(AppConstant.standPower.CLOUDNAME);
				 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.JIANCENAME)){
				    spw.setCansetword(record.getJiancenum());
				    spw.setName(AppConstant.standPower.JIANCENAME);
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.PERSONNAME)){
				    spw.setCansetword(record.getPersonnum());
				    spw.setName(AppConstant.standPower.PERSONNAME);
					standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SONNAME)){
				    spw.setCansetword(record.getSonnum());
				    spw.setName(AppConstant.standPower.SONNAME); 
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.SUBJECTNAME)){
				    spw.setCansetword(record.getSubjectnum());
				    spw.setName(AppConstant.standPower.SUBJECTNAME);
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
			  if(listsp.get(j).getName().equals(AppConstant.standPower.YUJINGNAME)){
				    spw.setCansetword(record.getYujingnum());
				    spw.setName(AppConstant.standPower.YUJINGNAME);
					 standerPowerMapper.updateByPrimaryKeySelective(spw);
			  }
		  }
		if(num >0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public int deleteUserType(String id) {
		// TODO Auto-generated method stub
		return userTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.deleteByPrimaryKey(record);
	}

	@Override
	public UserType selectByTypeName(String typename) {
		// TODO Auto-generated method stub
		return userTypeMapper.selectByTypeName(typename);
	}

	@Override
	public List<User> selectByUserType(String usertype) {
		// TODO Auto-generated method stub
		return userMapper.selectByUserType(usertype);
	}

	@Override
	public int insertSelective(ReMoneyBo record) {
		// TODO Auto-generated method stub
		return reMoneyMapper.insertSelective(record);
	}

	@Override
	public List<ReMoney> selectByExpirtime(ReMoney record) {
		// TODO Auto-generated method stub
		return reMoneyMapper.selectByExpirtime(record);
	}

	@Override
	public int updateCaiWu(ReMoneyBo record) {
		// TODO Auto-generated method stub
		return reMoneyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public User selectUserAndPowerById(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectUserAndPowerById(record);
	}

	@Override
	public List<User> selectByReport() {
		// TODO Auto-generated method stub
		return userMapper.selectByReport();
	}

	@Override
	public List<ManagerUser> selectManagerByNick(String name) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectManagerByNick(name);
	}

	@Override
	public List<ManagerUser> selectManager() {
		// TODO Auto-generated method stub
		return managerUserMapper.selectManager();
	}

	@Override
	public int insertTradePower() {
		// TODO Auto-generated method stub
		List<StanderPower> list = standerPowerMapper.selectStanderByTypeid();
		for(int i=0;i<list.size();i++){
			StanderPower sp = new StanderPower();
			sp.setId(UuidUtil.getUUID());
			sp.setStatus(0);
			sp.setTypeid(list.get(i).getTypeid());
			sp.setTypecode(list.get(i).getTypecode());
			sp.setTypename(list.get(i).getTypename());
			sp.setName("交易开通");
			int num = standerPowerMapper.insertSelective(sp);
			
		}
		return 0;
	}

	@Override
	public int wordsetTrade() {
		// TODO Auto-generated method stub
		
		List<WordSet> list = wordSetMapper.selectAllByUserid();
		for(int i=0;i<list.size();i++){
			
				WordSet wordset = new WordSet();
				wordset.setId(UuidUtil.getUUID());
				wordset.setName("查询天数");
				wordset.setUserid(list.get(i).getUserid());
				if(null != list.get(i).getEndtime()){
					wordset.setEndtime(list.get(i).getEndtime());
				}
		
				wordset.setCansetword(365);
				int num = wordSetMapper.insertSelective(wordset);
			
		}
		return 0;
	}
}

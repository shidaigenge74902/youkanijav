package com.bayside.app.opinion.war.power.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.mortbay.log.Log;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.dao.ManagerUserMapper;
import com.bayside.app.opinion.war.myuser.dao.StanderPowerMapper;
import com.bayside.app.opinion.war.myuser.dao.SubjectClassifyMapper;
import com.bayside.app.opinion.war.myuser.dao.UserMapper;
import com.bayside.app.opinion.war.myuser.dao.UserTypeMapper;
import com.bayside.app.opinion.war.myuser.dao.WordSetMapper;
import com.bayside.app.opinion.war.myuser.dao.userLogMapper;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.SubjectClassify;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.subject.dao.SubjectMapper;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.ExportExcelUtil;
import com.bayside.app.util.HdfsUpLoadUtil;

import com.bayside.app.util.SendCode;
import com.bayside.app.util.TestDate;
import com.bayside.app.util.UuidUtil;



@Service("powerServiceImpl")
@Transactional
public class PowerServiceImpl implements PowerUserService {
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
	private SubjectClassifyMapper subjectClassifyMapper;
	@Autowired
	private ManagerUserMapper managerUserMapper;
	@Autowired
	private SubjectMapper subjectMapper;
	
	private Set<String> returnList = new HashSet<String>();
	private static Logger Log = LoggerFactory.getLogger(PowerServiceImpl.class);

	@Override
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
	public int insert(UserType ut, UserTypeBo record) {
		// 插入权限
		List<String> list = new ArrayList<String>();
		/*list.add(AppConstant.standPower.AGENTNAME);*/
		list.add(AppConstant.standPower.CLOUDNAME);
		list.add(AppConstant.standPower.JIANCENAME);
		list.add(AppConstant.standPower.PERSONNAME);
		list.add(AppConstant.standPower.SONNAME);
		list.add(AppConstant.standPower.SUBJECTNAME);
		list.add(AppConstant.standPower.YUJINGNAME);
		list.add(AppConstant.standPower.EMPHASISNAME);
		// 附加权限项
		list.add(AppConstant.standPower.MICROOPENNAME);
		list.add(AppConstant.standPower.EXPIRDATE);
		list.add(AppConstant.standPower.SUBJECTREPORTNAME);
		list.add(AppConstant.standPower.DAYREPORTNAME);
		list.add(AppConstant.standPower.WEEKREPORTNAME);
		list.add(AppConstant.standPower.MONTHREPORTNAME);
		list.add(AppConstant.standPower.MODALNAME);
		list.add(AppConstant.standPower.WORDNAME);
		list.add(AppConstant.standPower.SETREPORT);
		list.add(AppConstant.standPower.SETTRADE);
		list.add(AppConstant.standPower.LOGINNUM);
		list.add(AppConstant.standPower.CHECKDAYS);
		
		list.add(AppConstant.standPower.ISUPDATE);
		list.add(AppConstant.standPower.ISMEDIA);
		
		StanderPower sps = new StanderPower();
		sps.setId(UuidUtil.getUUID());
		sps.setTypeid(ut.getId());
		sps.setSetword(0);
		sps.setTypename(record.getTypename());
		sps.setTypecode(record.getTypecode());
		List<StanderPower> lists = new ArrayList<StanderPower>();
		for (int i = 0; i < list.size(); i++) {
			StanderPower sp = new StanderPower();
			sp.setId(UuidUtil.getUUID());
			sp.setTypeid(ut.getId());
			sp.setSetword(0);
			sp.setTypename(record.getTypename());
			sp.setTypecode(record.getTypecode());
			
			if (list.get(i).equals(AppConstant.standPower.CLOUDNAME)) {
				sp.setName(AppConstant.standPower.CLOUDNAME);
				sp.setCansetword(record.getCloudnum());
				lists.add(sp);
				//this.insertPower(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.JIANCENAME)) {
				sp.setName(AppConstant.standPower.JIANCENAME);
				sp.setCansetword(record.getJiancenum());
				lists.add(sp);
			}
			//重点关注
			if (list.get(i).equals(AppConstant.standPower.EMPHASISNAME)) {
				sp.setName(AppConstant.standPower.EMPHASISNAME);
				sp.setCansetword(record.getEmphasisnum());
				lists.add(sp);
			}
			//登录账号个数
			if (list.get(i).equals(AppConstant.standPower.LOGINNUM)) {
				sp.setName(AppConstant.standPower.LOGINNUM);
				sp.setCansetword(record.getLoginnum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.PERSONNAME)) {
				sp.setName(AppConstant.standPower.PERSONNAME);
				sp.setCansetword(record.getPersonnum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SONNAME)) {
				sp.setName(AppConstant.standPower.SONNAME);
				sp.setCansetword(record.getSonnum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SUBJECTNAME)) {
				sp.setName(AppConstant.standPower.SUBJECTNAME);
				sp.setCansetword(record.getSubjectnum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.YUJINGNAME)) {
				sp.setName(AppConstant.standPower.YUJINGNAME);
				sp.setCansetword(record.getYujingnum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.MICROOPENNAME)) {
				sp.setName(AppConstant.standPower.MICROOPENNAME);
				// sp.setCansetword(record.getMicroopen());
				sp.setStatus(record.getMicroopen());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.EXPIRDATE)) {
				sp.setName(AppConstant.standPower.EXPIRDATE);
				sp.setCansetword(record.getExpirdate());
				// sp.setSetword(record.getExpirdate());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
				sp.setName(AppConstant.standPower.SUBJECTREPORTNAME);
				sp.setCansetword(record.getSubjectReport());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.DAYREPORTNAME)) {
				sp.setName(AppConstant.standPower.DAYREPORTNAME);
				sp.setStatus(record.getDayReport());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.WEEKREPORTNAME)) {
				sp.setName(AppConstant.standPower.WEEKREPORTNAME);
				sp.setStatus(record.getWeekReport());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.MONTHREPORTNAME)) {
				sp.setName(AppConstant.standPower.MONTHREPORTNAME);
				sp.setStatus(record.getMonthReport());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.MODALNAME)) {
				sp.setName(AppConstant.standPower.MODALNAME);
				sp.setCansetword(record.getModalNum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.WORDNAME)) {
				sp.setName(AppConstant.standPower.WORDNAME);
				sp.setCansetword(record.getKeywordNum());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.CHECKDAYS)) {
				sp.setName(AppConstant.standPower.CHECKDAYS);
				sp.setCansetword(record.getCheckdays());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SETREPORT)) {
				sp.setName(AppConstant.standPower.SETREPORT);
				sp.setStatus(record.getSetReport());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.SETTRADE)) {
				sp.setName(AppConstant.standPower.SETTRADE);
				sp.setStatus(record.getSetTrade());
				lists.add(sp);
			}
			if (list.get(i).equals(AppConstant.standPower.ISMEDIA)) {
				sp.setName(AppConstant.standPower.ISMEDIA);
				sp.setStatus(record.getIsmedia());
				lists.add(sp);
			}
			if(list.get(i).equals(AppConstant.standPower.ISUPDATE)){
				sp.setName(AppConstant.standPower.ISUPDATE);
				if(null == record.getIsupdate()){
					sp.setIsupdate(null);
					lists.add(sp);
				}else{
					sp.setIsupdate(record.getIsupdate());
					lists.add(sp);
				}
				
			}
		}
		sps.setList(lists);
		standerPowerMapper.batchInsertStander(sps);
		return userTypeMapper.insertSelective(ut);
	}

	@Override
	public List<UserTypeBo> selectUserTypeBo() {
		List<UserTypeBo> utlist = userTypeMapper.selectUserTypePower();
		return utlist;
	}

	@Override
	public List<UserType> selectUserType() {
		// TODO Auto-generated method stub
		List<UserType> list = userTypeMapper.selectUserType();
		return list;
	}

	@Override
	public StanderPower selectStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.selectStanderPower(record);
	}

	@Override
	public int insertPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.insertSelective(record);
	}

	@Override
	public UserTypeBo selectUserTypeBo(String typeid, String name) {
		// TODO Auto-generated method stub
		
		UserType utb = userTypeMapper.selectByPrimaryKey(typeid);
		UserTypeBo ub = new UserTypeBo();
		if(utb!=null){
			ub.setId(typeid);
			ub.setTypename(utb.getTypename());
			ub.setTypecode(utb.getTypecode());
		}
		StanderPower sp = new StanderPower();
		sp.setTypeid(typeid);
		if(null!=utb){
			sp.setTypecode(utb.getTypecode());
		}
		StanderPower listsp = standerPowerMapper.selectStanderPower(sp);
		if(null!=listsp){
			ub.setCloudnum(listsp.getCloudnum());
			
			
			ub.setJiancenum(listsp.getJiancenum());
		
		
			ub.setPersonnum(listsp.getPersonnum());
		
		
			ub.setSonnum(listsp.getSonnum());
		
		
			ub.setSubjectnum(listsp.getSubjectnum());
		
	
			ub.setYujingnum(listsp.getYujingnum());
		
	
			ub.setMicroopen(listsp.getMicroopen());
	
	
			ub.setExpirdate(listsp.getExpirdate());
		

			ub.setSubjectReport(listsp.getSubjectReport());
		
		//
		
			ub.setDayReport(listsp.getDayReport());
		
	
			ub.setWeekReport(listsp.getWeekReport());
		
		
			ub.setMonthReport(listsp.getMonthReport());
		
		
			ub.setModalNum(listsp.getModalNum());
		
		
			ub.setKeywordNum(listsp.getKeywordNum());
			ub.setSetReport(listsp.getSetReport());
			ub.setSetTrade(listsp.getSetTrade());
			ub.setIsupdate(listsp.getIsupdate());

			ub.setEmphasisnum(listsp.getEmphasisnum());
			ub.setLoginnum(listsp.getLoginnum());
			ub.setCheckdays(listsp.getCheckdays());
			ub.setIsmedia(listsp.getIsmedia());
		
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

	public Boolean updateUS(UserTypeBo record) {
		UserType ut = new UserType();
		BeanUtils.copyProperties(record, ut);
		int num = userTypeMapper.updateByPrimaryKeySelective(ut);
		StanderPower sp = new StanderPower();
		sp.setTypeid(record.getId());
		sp.setTypecode(record.getTypecode());
		sp.setTypename(record.getTypename());
	    List<StanderPower> listsp = standerPowerMapper.selectnewStanderPower(sp);
	    Boolean flag = false;
	    Boolean focustag = false;
	    Boolean logintag = false;
	    Boolean checkdaystag = false;
	    Boolean ismediatag = false;
		for(int i=0;i<listsp.size();i++){
			listsp.get(i).setTypeid(record.getId());
			listsp.get(i).setTypecode(record.getTypecode());
			listsp.get(i).setTypename(record.getTypename());
			if(listsp.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)){
				listsp.get(i).setCansetword(record.getCloudnum());
				//listsp.get(i).setStatus(1);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.JIANCENAME)){
				listsp.get(i).setCansetword(record.getJiancenum());
				//listsp.get(i).setStatus(1);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.PERSONNAME)){
				listsp.get(i).setCansetword(record.getPersonnum());
				//listsp.get(i).setStatus(1);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.SONNAME)){
				listsp.get(i).setCansetword(record.getSonnum());
				//listsp.get(i).setStatus(1);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)){
				listsp.get(i).setCansetword(record.getSubjectnum());
				//listsp.get(i).setStatus(1);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)){
				listsp.get(i).setCansetword(record.getYujingnum());
				//listsp.get(i).setStatus(1);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.MICROOPENNAME)){
				listsp.get(i).setStatus(record.getMicroopen());
				//listsp.get(i).setCansetword(cansetword);
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.EXPIRDATE)){
				listsp.get(i).setCansetword(record.getExpirdate());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)){
				listsp.get(i).setCansetword(record.getSubjectReport());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.DAYREPORTNAME)){
				listsp.get(i).setStatus(record.getDayReport());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.WEEKREPORTNAME)){
				listsp.get(i).setStatus(record.getWeekReport());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.MONTHREPORTNAME)){
				listsp.get(i).setStatus(record.getMonthReport());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.MODALNAME)){
				listsp.get(i).setCansetword(record.getModalNum());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.WORDNAME)){
				listsp.get(i).setStatus(record.getKeywordNum());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.SETREPORT)){
				listsp.get(i).setStatus(record.getSetReport());
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.SETTRADE)){
				listsp.get(i).setStatus(record.getSetTrade());
			}else if(AppConstant.standPower.ISUPDATE.equals(listsp.get(i).getName())){
				listsp.get(i).setIsupdate(record.getIsupdate());
				flag = true;
			}else if(AppConstant.standPower.EMPHASISNAME.equals(listsp.get(i).getName())){
				listsp.get(i).setCansetword(record.getEmphasisnum());
				focustag = true;
			}else if(AppConstant.standPower.LOGINNUM.equals(listsp.get(i).getName())){
				listsp.get(i).setCansetword(record.getLoginnum());
				logintag = true;
			}else if(AppConstant.standPower.CHECKDAYS.equals(listsp.get(i).getName())){
				listsp.get(i).setCansetword(record.getCheckdays());
				checkdaystag = true;
			}else if(listsp.get(i).getName().equals(AppConstant.standPower.ISMEDIA)){
				listsp.get(i).setStatus(record.getIsmedia());
				ismediatag = true;
			}
			
		}
		
		sp.setList(listsp);
		standerPowerMapper.bathupdateStanderPower(sp);
		if(null !=record.getIsupdate()){
			if(!flag){
				StanderPower  spower = new StanderPower();
				spower.setTypeid(record.getId());
				spower.setTypecode(record.getTypecode());
				spower.setTypename(record.getTypename());
				spower.setIsupdate(record.getIsupdate());
				spower.setId(UuidUtil.getUUID());
				spower.setName(AppConstant.standPower.ISUPDATE);
				standerPowerMapper.insertSelective(spower);
			}
		}
		if(null !=record.getEmphasisnum()){
			if(!focustag){
				StanderPower  spower = new StanderPower();
				spower.setTypeid(record.getId());
				spower.setTypecode(record.getTypecode());
				spower.setTypename(record.getTypename());
				spower.setCansetword(record.getEmphasisnum());
				spower.setId(UuidUtil.getUUID());
				spower.setName(AppConstant.standPower.EMPHASISNAME);
				standerPowerMapper.insertSelective(spower);
			}else{
				System.out.println("000");
			}
		}
		if(null !=record.getLoginnum()){
			if(!logintag){
				StanderPower  spower = new StanderPower();
				spower.setTypeid(record.getId());
				spower.setTypecode(record.getTypecode());
				spower.setTypename(record.getTypename());
				spower.setCansetword(record.getLoginnum());
				spower.setId(UuidUtil.getUUID());
				spower.setName(AppConstant.standPower.LOGINNUM);
				standerPowerMapper.insertSelective(spower);
			}else{
				System.out.println("000");
			}
		}
		if(null !=record.getCheckdays()){
			if(!checkdaystag){
				StanderPower  spower = new StanderPower();
				spower.setTypeid(record.getId());
				spower.setTypecode(record.getTypecode());
				spower.setTypename(record.getTypename());
				spower.setCansetword(record.getCheckdays());
				spower.setId(UuidUtil.getUUID());
				spower.setName(AppConstant.standPower.CHECKDAYS);
				standerPowerMapper.insertSelective(spower);
			}else{
				System.out.println("000");
			}
		}
		if(null !=record.getIsmedia()){
			if(!ismediatag){
				StanderPower  spower = new StanderPower();
				spower.setTypeid(record.getId());
				spower.setTypecode(record.getTypecode());
				spower.setTypename(record.getTypename());
				spower.setStatus(record.getIsmedia());
				spower.setId(UuidUtil.getUUID());
				spower.setName(AppConstant.standPower.ISMEDIA);
				standerPowerMapper.insertSelective(spower);
			}else{
				System.out.println("000");
			}
		}
		if (num > 0) {
			return true;
		} else {
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
	public int updatePassword(User user) {
		// TODO Auto-generated method stub
		return userMapper.updatePassword(user);
	}

	@Override
	public int updateUserStatus(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserStatus(user);
	}

	@Override
	public int updateUserShen(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserShen(user);
	}

	@Override
	public int updateUserAttr(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserAttr(user);
	}

	@Override
	public UserBo checkagentById(String id, User user) {
		UserBo u = new UserBo();
	   if(3 == user.getStatus()){
		  User us = userMapper.selectByPrimaryKey(id);
		  BeanUtils.copyProperties(us, u);
	   }else{
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			u.setId(user.getId());
			u = userMapper.selectWordSetPowerByUserId(u);
			if(null!= u.getExpirytime()){
					String time = formatter.format(u.getExpirytime());
					u.setExpirydate(time);
				
				
			} 
	   }
		
		return u;
	}

	@Override
	public User updateUserA(UserBo record) {
		// TODO Auto-generated method stub
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		if (record.getExpirydate() != null && !"".equals(record.getExpirydate())) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.info(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		WordSet ws = new WordSet();
		List<WordSet> listws = wordSetMapper.selectByPrimaryKey(record.getId());
		Boolean logintag=false;
		Boolean emotion = false;
	    for(int i=0;i<listws.size();i++){
			if(AppConstant.standPower.SUBJECTNAME.equals(listws.get(i).getName())){
				try {
					Date date = sdf.parse(record.getExpirydate());
					listws.get(i).setEndtime(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					Log.error(e.getMessage(),e);
				}
				if (null ==user.getSubjecttimes()) {
					listws.get(i).setCansetword(0);
				}else {
					listws.get(i).setCansetword(user.getSubjecttimes());
				}
			}else if(AppConstant.standPower.SONNAME.equals(listws.get(i).getName())){
				if (null == user.getChildtimes()) {
					listws.get(i).setCansetword(0);
				} else {
					listws.get(i).setCansetword(user.getChildtimes());
				}
			}else if(AppConstant.standPower.YUJINGNAME.equals(listws.get(i).getName())){
				if (user.getWarntimes() == null) {
					listws.get(i).setCansetword(0);
				} else {
					listws.get(i).setCansetword(user.getWarntimes());
				}
			}else if(AppConstant.standPower.CLOUDNAME.equals(listws.get(i).getName())){
				if (user.getCloudtimes() == null) {
					listws.get(i).setCansetword(0);
				} else {
					listws.get(i).setCansetword(user.getCloudtimes());
				}
			}else if(AppConstant.standPower.JIANCENAME.equals(listws.get(i).getName())){
				if (null ==user.getMonitortimes()) {
					listws.get(i).setCansetword(0);
				} else {
					listws.get(i).setCansetword(user.getMonitortimes());
				}
			}else if(AppConstant.standPower.PERSONNAME.equals(listws.get(i).getName())){
				if (null ==user.getPersontimes()) {
					listws.get(i).setCansetword(0);
				} else {
					listws.get(i).setCansetword(user.getPersontimes());
				}
			}else if(AppConstant.standPower.MICROOPENNAME.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.MICROOPENNAME);
				listws.get(i).setStatus(record.getMicroopen());
			}
			else if(AppConstant.standPower.SUBJECTREPORTNAME.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.SUBJECTREPORTNAME);
				listws.get(i).setCansetword(record.getSubjectReport());
			}
			else if(AppConstant.standPower.WORDNAME.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.WORDNAME);
				listws.get(i).setCansetword(record.getKeywordNum());
			}
			else if(AppConstant.standPower.DAYREPORTNAME.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.DAYREPORTNAME);
				listws.get(i).setStatus(record.getDayReport());
			}
			else if(AppConstant.standPower.WEEKREPORTNAME.equals(listws.get(i).getName())){
			//	listws.get(i).setName(AppConstant.standPower.WEEKREPORTNAME);
				listws.get(i).setStatus(record.getWeekReport());
			}
			else if(AppConstant.standPower.MONTHREPORTNAME.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.MONTHREPORTNAME);
				listws.get(i).setStatus(record.getMonthReport());
			}
			else if(AppConstant.standPower.MODALNAME.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.MODALNAME);
				listws.get(i).setStatus(record.getModalNum());
			}
			else if(AppConstant.standPower.SETREPORT.equals(listws.get(i).getName())){
				listws.get(i).setName(AppConstant.standPower.SETREPORT);
				listws.get(i).setStatus(record.getSetReport());
			}
			else if(AppConstant.standPower.SETTRADE.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.SETTRADE);
				listws.get(i).setStatus(record.getSetTrade());
			}
			else if(AppConstant.standPower.EXPIRDATE.equals(listws.get(i).getName())){
				//listws.get(i).setName(AppConstant.standPower.EXPIRDATE);
				try {
					Date date = sdf.parse(record.getExpirydate());
					listws.get(i).setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			}else if(AppConstant.standPower.ISUPDATE.equals(listws.get(i).getName())){
				listws.get(i).setIsupdate(record.getIsupdate());
			}else if (AppConstant.standPower.EMPHASISNAME.equals((listws).get(i).getName())){
				listws.get(i).setCansetword(record.getEmphasisnum());
			}else if(AppConstant.standPower.CHECKDAYS.equals(listws.get(i).getName())){
				listws.get(i).setCansetword(record.getCheckdays());
			}else if(AppConstant.standPower.LOGINNUM.equals((listws).get(i).getName())){
				listws.get(i).setCansetword(record.getLoginnum());
				logintag = true;
			}else if(AppConstant.standPower.ISMEDIA.equals((listws).get(i).getName())){
				listws.get(i).setStatus(record.getIsmedia());
			}else if(AppConstant.standPower.EMOTIONNUM.equals((listws).get(i).getName())){
				listws.get(i).setEvalue(record.getEvalue());
				listws.get(i).setEmotionname(record.getEmotionname());
				emotion = true;
				
			}
		}
		ws.setList(listws);
	    int num = wordSetMapper.updatePowerOrder(ws);
	    if(num > 0){
	    	Subject subject = new Subject();
	        subject.setUserid(record.getId());	
	        subject.setEmotionstandard(Double.parseDouble(record.getEvalue()));
	        subjectMapper.updateSubjectByuserid(subject);
	    }
		
	    Boolean flag = false;
	    Boolean isf = false;
		boolean emphasis = false;
	    Boolean checkdays = false;
	    Boolean ismedia = false;
		 for(int i=0;i<listws.size();i++){
			 if(AppConstant.standPower.SETTRADE.equals(listws.get(i).getName())){
				 flag  = true;
			 }
			 if(AppConstant.standPower.ISUPDATE.equals(listws.get(i).getName())){
				 isf = true;
			 }
			 if(AppConstant.standPower.EMPHASISNAME.equals(listws.get(i).getName())){
				 emphasis = true;
			 }
			 if(AppConstant.standPower.CHECKDAYS.equals(listws.get(i).getName())){
				 checkdays = true;
			 }
			 if(AppConstant.standPower.ISMEDIA.equals(listws.get(i).getName())){
				 ismedia = true;
			 }
		 }
		 if(!flag){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.SETTRADE);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setStatus(record.getSetTrade());
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			wordSetMapper.insertSelective(wss);
		 }
		 if(!logintag){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.LOGINNUM);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setCansetword(record.getLoginnum());
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			wordSetMapper.insertSelective(wss);
		 }
		 //重点关注
		 if(!emphasis){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.EMPHASISNAME);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setCansetword(record.getEmphasisnum());
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			wordSetMapper.insertSelective(wss);
		 }
		 if(!isf){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.ISUPDATE);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setIsupdate(record.getIsupdate());
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			wordSetMapper.insertSelective(wss);
		 }
		 if(!checkdays){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.CHECKDAYS);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setCansetword(record.getCheckdays());
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			wordSetMapper.insertSelective(wss);
		 }
		 if(!ismedia){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.ISMEDIA);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setStatus(record.getIsmedia());
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			wordSetMapper.insertSelective(wss);
		 }
		 if(!emotion){
			 WordSet wss = new WordSet();
			 wss.setName(AppConstant.standPower.EMOTIONNUM);
			 try {
					Date date = sdf.parse(record.getExpirydate());
					wss.setEndtime(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			 wss.setId(UuidUtil.getUUID());
			 wss.setUserid(record.getId());
			 wss.setEmotionname(record.getEmotionname());
			 wss.setEvalue(record.getEvalue());
			wordSetMapper.insertSelective(wss);
		 }
		return user;
	}

	@Override
	public User userShen(UserBo record) {
		// TODO Auto-generated method stub
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (null!=record.getExpirydate()) {
			if(!"".equals(record.getExpirydate())){
				try {
					Date date = formatter.parse(record.getExpirydate());
					user.setExpirydate(date);
				} catch (ParseException e) {
					Log.error(e.getMessage(),e);
				}
			}
			
		}
		//查询用户类型
		UserType ut = new UserType();
		ut.setTypename("0");
		List<UserType> listUserType = userTypeMapper.selectUserTypeByName(ut);
		if(listUserType.size()>0){
			user.setUsertype("0");
			user.setTypecode(listUserType.get(0).getTypecode());
		}
	
		//user.setRegistertime(new Date());
		user.setStartdate(new Date());
		// 向用户发送初始化密码
		Random random = new Random();

		String s = "";

		for (int i = 0; i < 6; i++) {

			s += random.nextInt(10);

		}
		user.setPassword(s);
		String tel = user.getMobilephone();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("Account", "lyy"));
		formparams.add(new BasicNameValuePair("Pwd", "3355315CD86A2BC5B0A6F2114DC4"));
		formparams.add(new BasicNameValuePair("Content", user.getMobilephone() + "||" + s));
		formparams.add(new BasicNameValuePair("Mobile", user.getMobilephone()));
		formparams.add(new BasicNameValuePair("TemplateId", "30066"));

		formparams.add(new BasicNameValuePair("SignId", "30273"));
		try {
			SendCode.Post(formparams);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			Log.error(e.getMessage(),e);
		}
		// 添加分类 默认列表
		SubjectClassify sc = new SubjectClassify();
		sc.setUserid(record.getId());
		sc.setUserparentid(record.getId());
		sc.setClassifyname("默认列表");
		sc.setCreateTime(new Date());
		int order = 1;
		sc.setOrderIndex(order);
		sc.setId(UuidUtil.getUUID());
		sc.setNavigation(false);
		int a = this.insertSubjectClassify(sc);
		StanderPower sp = new StanderPower();
		if(listUserType.size()>0){
			sp.setTypename("0");
			sp.setTypeid(listUserType.get(0).getId());
			sp.setTypecode(listUserType.get(0).getTypecode());
		}
	
		StanderPower listpower = this.selectStanderPower(sp);
		Date d = new Date();
		// 日期处理
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, listpower.getExpirdate());
		 d = c.getTime();
		 user.setExpirydate(d);
		// 日期处理
	    Calendar c1 = Calendar.getInstance();
	   c1.add(Calendar.DATE, listpower.getExpirdate());
	   List<WordSet> listwd = new ArrayList<WordSet>();
	   WordSet bigset = new WordSet();
	     String[] powerlist = AppConstant.standPower.POWERLIST;
	     for(int i=0;i<powerlist.length;i++){
	    	 WordSet ws = new WordSet();
	    	 ws.setId(UuidUtil.getUUID());
			 ws.setUserid(record.getId());
			 ws.setEndtime(c1.getTime());
			 if(AppConstant.standPower.JIANCENAME.equals(powerlist[i])){
				 ws.setCansetword(listpower.getJiancenum());
			     ws.setSetword(0);
				 ws.setName(AppConstant.standPower.JIANCENAME);
				 ws.setStatus(null);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.EXPIRDATE.equals(powerlist[i])){
				 ws.setStatus(null);
			     ws.setCansetword(null);
				 ws.setSetword(null);
				 ws.setName(AppConstant.standPower.EXPIRDATE);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.PERSONNAME.equals(powerlist[i])){
				 ws.setCansetword(listpower.getPersonnum());
				 ws.setSetword(0);
				 ws.setStatus(null);
				 ws.setName(AppConstant.standPower.PERSONNAME);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.SONNAME.equals(powerlist[i])){
				 ws.setCansetword(listpower.getSonnum());
				 ws.setSetword(0);
				 ws.setStatus(null);
				 ws.setName(AppConstant.standPower.SONNAME);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.SUBJECTNAME.equals(powerlist[i])){
				 ws.setCansetword(listpower.getSubjectnum());
				 ws.setSetword(0);
				 ws.setName(AppConstant.standPower.SUBJECTNAME);
				 ws.setStatus(null);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.YUJINGNAME.equals(powerlist[i])){
					ws.setCansetword(listpower.getYujingnum());
					ws.setSetword(0);
					ws.setStatus(null);
					ws.setName(AppConstant.standPower.YUJINGNAME);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.MODALNAME.equals(powerlist[i])){
				    ws.setCansetword(listpower.getModalNum());
					ws.setSetword(0);
					ws.setName(AppConstant.standPower.MODALNAME);
					ws.setStatus(null);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.SUBJECTREPORTNAME.equals(powerlist[i])){
				    ws.setCansetword(listpower.getSubjectReport());
					ws.setSetword(0);
					ws.setStatus(null);
					ws.setName(AppConstant.standPower.SUBJECTREPORTNAME);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.MICROOPENNAME.equals(powerlist[i])){
				   ws.setCansetword(null);
					ws.setSetword(null);
					ws.setStatus(listpower.getMicroopen());
					user.setMicroopen(listpower.getMicroopen());
					ws.setIsupdate(null);
					ws.setName(AppConstant.standPower.MICROOPENNAME);
			 }else if(AppConstant.standPower.DAYREPORTNAME.equals(powerlist[i])){
				    ws.setStatus(listpower.getDayReport());
					ws.setName(AppConstant.standPower.DAYREPORTNAME);
					ws.setCansetword(null);
					ws.setSetword(null);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.WEEKREPORTNAME.equals(powerlist[i])){
				    ws.setStatus(listpower.getWeekReport());
					ws.setName(AppConstant.standPower.WEEKREPORTNAME);
					ws.setCansetword(null);
					ws.setSetword(null);
					ws.setIsupdate(null);
			 }else if(AppConstant.standPower.MONTHREPORTNAME.equals(powerlist[i])){
				    ws.setStatus(listpower.getMonthReport());
					ws.setName(AppConstant.standPower.MONTHREPORTNAME);
					ws.setCansetword(null);
					ws.setSetword(null);		
					ws.setIsupdate(null);
		   }else if(AppConstant.standPower.SETREPORT.equals(powerlist[i])){
			   ws.setStatus(listpower.getSetReport());
				ws.setName(AppConstant.standPower.SETREPORT);
				ws.setCansetword(null);
				ws.setSetword(null);
				ws.setIsupdate(null);
	        }else if(AppConstant.standPower.WORDNAME.equals(powerlist[i])){
	        	ws.setCansetword(listpower.getKeywordNum());
				ws.setSetword(0);
				ws.setStatus(null);
				ws.setName(AppConstant.standPower.WORDNAME);	
				ws.setIsupdate(null);
		    }else if(AppConstant.standPower.CLOUDNAME.equals(powerlist[i])){
		    	ws.setCansetword(listpower.getCloudnum());
				ws.setSetword(0);
				ws.setName(AppConstant.standPower.CLOUDNAME);
				ws.setStatus(null);		
				ws.setIsupdate(null);
		    }else if(AppConstant.standPower.SETTRADE.equals(powerlist[i])){
	        	ws.setCansetword(null);
				ws.setSetword(0);
				ws.setStatus(listpower.getSetTrade());
				ws.setName(AppConstant.standPower.SETTRADE);	
				ws.setIsupdate(null);
		    }else if(AppConstant.standPower.ISUPDATE.equals(powerlist[i])){
		    	ws.setCansetword(null);
				ws.setSetword(0);
				ws.setStatus(null);
				ws.setName(AppConstant.standPower.ISUPDATE);
				ws.setIsupdate(listpower.getIsupdate());
		    }else if(AppConstant.standPower.EMPHASISNAME.equals(powerlist[i])){
		    	ws.setCansetword(listpower.getEmphasisnum());
				ws.setSetword(0);
				ws.setStatus(null);
				ws.setName(AppConstant.standPower.EMPHASISNAME);
				ws.setIsupdate(null);
		    }
				listwd.add(ws);
	    }
	      bigset.setList(listwd);
	      //批量插入
		  wordSetMapper.batchInsert(bigset);
		return user;
	}

	@Override
	public List<User> selectid() {
		// TODO Auto-generated method stub
		return userMapper.selectid();
	}


	@Override
	public int specialUser(User record) {
		// TODO Auto-generated method stub
	//	record.setUsertype("0");
		record.setStartdate(new Date());
		// 添加分类 默认列表
		SubjectClassify sc = new SubjectClassify();
		sc.setUserid(record.getId());
		sc.setUserparentid(record.getId());
		sc.setClassifyname("默认列表");
		sc.setCreateTime(new Date());
		int order = 1;
		sc.setOrderIndex(order);
		sc.setId(UuidUtil.getUUID());
		sc.setNavigation(false);
		int a = this.insertSubjectClassify(sc);
		StanderPower sp = new StanderPower();
	    sp.setTypecode(record.getTypecode());
	    
	    sp.setTypename(record.getUsertype());
	//	StanderPower listpower = this.selectStanderPower(sp);
		StanderPower listpower = standerPowerMapper.checkStanderPower(sp);
		
		
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, listpower.getExpirdate());
		 d = c.getTime();
		 record.setExpirydate(d);
		 List<WordSet> listwd = new ArrayList<WordSet>();
		   WordSet bigset = new WordSet();
		     String[] powerlist = AppConstant.standPower.POWERLIST;
		     for(int i=0;i<powerlist.length;i++){
		    	 WordSet ws = new WordSet();
		    	 ws.setId(UuidUtil.getUUID());
				 ws.setUserid(record.getId());
				 ws.setEndtime(c.getTime());
				 if(AppConstant.standPower.JIANCENAME.equals(powerlist[i])){
					 ws.setCansetword(listpower.getJiancenum());
				     ws.setSetword(0);
					 ws.setName(AppConstant.standPower.JIANCENAME);
					 ws.setStatus(null);
					 ws.setIsupdate(null);
				 }else if(AppConstant.standPower.EXPIRDATE.equals(powerlist[i])){
					 ws.setStatus(null);
				     ws.setCansetword(null);
					 ws.setSetword(null);
					 ws.setName(AppConstant.standPower.EXPIRDATE);
					 ws.setIsupdate(null);
				 }else if(AppConstant.standPower.PERSONNAME.equals(powerlist[i])){
					 ws.setCansetword(listpower.getPersonnum());
					 ws.setSetword(0);
					 ws.setStatus(null);
					 ws.setName(AppConstant.standPower.PERSONNAME);
					 ws.setIsupdate(null);
				 }else if(AppConstant.standPower.SONNAME.equals(powerlist[i])){
					 ws.setCansetword(listpower.getSonnum());
					 ws.setSetword(0);
					 ws.setStatus(null);
					 ws.setName(AppConstant.standPower.SONNAME);
					 ws.setIsupdate(null);
				 }else if(AppConstant.standPower.SUBJECTNAME.equals(powerlist[i])){
					 ws.setCansetword(listpower.getSubjectnum());
					 ws.setSetword(0);
					 ws.setName(AppConstant.standPower.SUBJECTNAME);
					 ws.setStatus(null);
					 ws.setIsupdate(null);
				 }else if(AppConstant.standPower.YUJINGNAME.equals(powerlist[i])){
						ws.setCansetword(listpower.getYujingnum());
						ws.setSetword(0);
						ws.setStatus(null);
						ws.setName(AppConstant.standPower.YUJINGNAME);
						ws.setIsupdate(null);
				 }else if(AppConstant.standPower.MODALNAME.equals(powerlist[i])){
					    ws.setCansetword(listpower.getModalNum());
						ws.setSetword(0);
						ws.setName(AppConstant.standPower.MODALNAME);
						ws.setStatus(null);
						ws.setIsupdate(null);
				 }else if(AppConstant.standPower.SUBJECTREPORTNAME.equals(powerlist[i])){
					    ws.setCansetword(listpower.getSubjectReport());
						ws.setSetword(0);
						ws.setStatus(null);
						ws.setName(AppConstant.standPower.SUBJECTREPORTNAME);
						ws.setIsupdate(null);
				 }else if(AppConstant.standPower.MICROOPENNAME.equals(powerlist[i])){
					    ws.setCansetword(null);
						ws.setSetword(null);
						ws.setStatus(listpower.getMicroopen());
						record.setMicroopen(listpower.getMicroopen());
						ws.setName(AppConstant.standPower.MICROOPENNAME);
						ws.setIsupdate(null);
				 }else if(AppConstant.standPower.DAYREPORTNAME.equals(powerlist[i])){
					    ws.setStatus(listpower.getDayReport());
						ws.setName(AppConstant.standPower.DAYREPORTNAME);
						ws.setCansetword(null);
						ws.setSetword(null);
						ws.setIsupdate(null);
				 }else if(AppConstant.standPower.WEEKREPORTNAME.equals(powerlist[i])){
					    ws.setStatus(listpower.getWeekReport());
						ws.setName(AppConstant.standPower.WEEKREPORTNAME);
						ws.setCansetword(null);
						ws.setSetword(null);
						ws.setIsupdate(null);
				 }else if(AppConstant.standPower.MONTHREPORTNAME.equals(powerlist[i])){
					    ws.setStatus(listpower.getMonthReport());
						ws.setName(AppConstant.standPower.MONTHREPORTNAME);
						ws.setCansetword(null);
						ws.setSetword(null);	
						ws.setIsupdate(null);
			   }else if(AppConstant.standPower.SETREPORT.equals(powerlist[i])){
				   ws.setStatus(listpower.getSetReport());
					ws.setName(AppConstant.standPower.SETREPORT);
					ws.setCansetword(null);
					ws.setSetword(null);
					ws.setIsupdate(null);
		        }else if(AppConstant.standPower.WORDNAME.equals(powerlist[i])){
		        	ws.setCansetword(listpower.getKeywordNum());
					ws.setSetword(0);
					ws.setStatus(null);
					ws.setIsupdate(null);
					ws.setName(AppConstant.standPower.WORDNAME);		
			    }else if(AppConstant.standPower.CLOUDNAME.equals(powerlist[i])){
			    	ws.setCansetword(listpower.getCloudnum());
					ws.setSetword(0);
					ws.setName(AppConstant.standPower.CLOUDNAME);
					ws.setStatus(null);		
					ws.setIsupdate(null);
			    }else if(AppConstant.standPower.SETTRADE.equals(powerlist[i])){
		        	ws.setCansetword(null);
					ws.setSetword(0);
					ws.setStatus(listpower.getSetTrade());
					ws.setName(AppConstant.standPower.SETTRADE);	
					ws.setIsupdate(null);
			    }else if(AppConstant.standPower.ISUPDATE.equals(powerlist[i])){
			    	ws.setCansetword(null);
					ws.setSetword(0);
					ws.setStatus(0);
					ws.setName(AppConstant.standPower.ISUPDATE);
					ws.setIsupdate(listpower.getIsupdate());
			    }else if(AppConstant.standPower.EMPHASISNAME.equals(powerlist[i])){
			    	ws.setCansetword(listpower.getEmphasisnum());
					ws.setSetword(0);
					ws.setStatus(0);
					ws.setName(AppConstant.standPower.EMPHASISNAME);
					ws.setIsupdate(null);
			    }
					listwd.add(ws);
		    }
		     bigset.setList(listwd);
		     //批量插入
			 wordSetMapper.batchInsert(bigset);
		   return userMapper.insertSelective(record);
	}

	@Override
	public List<User> selectByEndTime(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectByEndTime(user);
	}

	@Override
	public int insertSubjectClassify(SubjectClassify record) {
		// TODO Auto-generated method stub
		return subjectClassifyMapper.insertSelective(record);
	}

	@Override
	public int insertManagerPuTong(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.insertSelective(record);
	}

	@Override
	public List<ManagerUser> selectAllManager(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectAllManager(record);
	}

	@Override
	public int updateManageUser(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteManagerUser(String id) {
		// TODO Auto-generated method stub
		return managerUserMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<ManagerUser> selectManager() {
		// TODO Auto-generated method stub
		return managerUserMapper.selectManager();
	}

	@Override
	public String uploadImg(String newFileName, MultipartFile mulfile) {
		// TODO Auto-generated method stub
		  try {
				HdfsUpLoadUtil.createFile("/app-opinion-webmanager/"+newFileName, mulfile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println(e.getMessage());
				Log.error(e.getMessage(),e);
			}
		      String imgurl= "/app-opinion-webmanager/"+newFileName;
		     return imgurl;
		
	}

	@Override
	public ManagerUser selectManagerUserById(String id) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserLog> selectUserLog() {
		// TODO Auto-generated method stub
		return userLogMapper.selectAllUserLog();
	}

	@Override
	public List<UserLog> filterSelectUserLog(UserLogBo record) {
		// TODO Auto-generated method stub
		return userLogMapper.filterSelectUserLog(record);
	}

	@Override
	public ManagerUser selectManagerByPhone(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectManagerByPhone(record);
	}

	@Override
	public ManagerUser selectInfoByPa(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectInfoByPa(record);
	}

	@Override
	public List<User> selectUserByManagerId(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectUserByManagerId(user);
	}

	@Override
	public List<UserLog> selectAllUserLogByIds(UserLogBo record) {
		// TODO Auto-generated method stub
		return userLogMapper.selectAllUserLogByIds(record);
	}

	@Override
	public int updateManagerUserPass(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.updateManagerUserPass(record);
	}

	@Override
	public ManagerUser selectManagerByName(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectManagerByName(record);
	}



	@Override
	public UserBo selectTotalNumber(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectTotalNumber(record);
	}

	@Override
	public List<User> selectUserList(UserBo record) {
		// TODO Auto-generated method stub
/*		record.setStatus(1);*/
		
		List<User> lists = userMapper.selectUserAndPower(record);
		
		return lists;
	}
	@Override
	public List<UserBo> selectUserTypeNumber(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectUserTypeNumber(record);
	}
	@Override
	public List<UserBo> selectUserNumber(UserBo record) {
		// TODO Auto-generated method stub
		List<UserBo> listBo = new ArrayList<UserBo>();
		listBo = userMapper.selectUserTypeNumber(record);
	
		return listBo;
	}
	@Override
	public UserBo selectTotalUserTypeNumber(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectTotalUserTypeNumber(record);
	}
	@Override
	public UserBo selectTotalUserNumber(UserBo record) {
		// TODO Auto-generated method stub
		UserBo ub = userMapper.selectTotalUserTypeNumber(record);
		return ub;
	}


	@Override
	public List<UserBo> selectDetailUserList(UserBo record) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<User> lists = userMapper.selectDetailUserList(record);
		List<UserBo> listBo = new ArrayList<UserBo>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(lists.size()>0){
			for(int j=0;j<lists.size();j++){
				UserBo ub = new UserBo();
				BeanUtils.copyProperties(lists.get(j), ub);
				if (null!=lists.get(j).getExpirydate()) {
					String time = formatter.format(lists.get(j).getExpirydate());
					ub.setExpirydate(time);
				}
				//
				WordSet wse = new WordSet();
				wse.setUserid(lists.get(j).getId());
				List<WordSet> list = this.selectPowerByUserId(wse);
				if(list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getName().equals(AppConstant.standPower.SONNAME)) {
							ub.setChildtimes(list.get(i).getCansetword());
						}
						/*if (list.get(i).getName().equals(AppConstant.standPower.AGENTNAME)) {
							ub.setUsertimes(list.get(i).getCansetword());
						}*/
						if (list.get(i).getName().equals(AppConstant.standPower.YUJINGNAME)) {
							ub.setWarntimes(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.CLOUDNAME)) {
							ub.setCloudtimes(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.PERSONNAME)) {
							ub.setPersontimes(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.JIANCENAME)) {
							ub.setMonitortimes(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTNAME)) {
							ub.setSubjecttimes(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.MICROOPENNAME)) {
							ub.setMicroopen(list.get(i).getStatus());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.DAYREPORTNAME)) {
							ub.setDayReport(list.get(i).getStatus());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.WEEKREPORTNAME)) {
							ub.setWeekReport(list.get(i).getStatus());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.MONTHREPORTNAME)) {
							ub.setMonthReport(list.get(i).getStatus());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.EXPIRDATE)) {
							ub.setExpirdate(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.SUBJECTREPORTNAME)) {
							ub.setSubjectReport(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.MODALNAME)) {
							ub.setModalNum(list.get(i).getCansetword());
						}
						if (list.get(i).getName().equals(AppConstant.standPower.WORDNAME)) {
							ub.setKeywordNum(list.get(i).getCansetword());
						}
					}
					listBo.add(ub);
				}else{
					ub.setChildtimes(0);
					ub.setUsertimes(0);
					ub.setWarntimes(0);
					ub.setCloudtimes(0);
					ub.setPersontimes(0);
					ub.setMonitortimes(0);
					ub.setSubjecttimes(0);
					ub.setMicroopen(0);
					ub.setDayReport(0);
					ub.setWeekReport(0);
					ub.setMonthReport(0);
					ub.setExpirdate(0);
					ub.setSubjectReport(0);
					ub.setModalNum(0);
					ub.setKeywordNum(0);
					listBo.add(ub);
				}
				
			}
		}
		return listBo;
	}
	public void setTableWidth(XWPFTable table, String width) {
		CTTbl ttbl = table.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		CTJc cTJc = tblPr.addNewJc();
		cTJc.setVal(STJc.Enum.forString("center"));
		tblWidth.setW(new BigInteger(width));
		tblWidth.setType(STTblWidth.DXA);
	}
	/**
	 * @Description: 设置行高
	 */
	public void setRowHeight(XWPFTableRow row, String hight,
			STHeightRule.Enum heigthEnum) {
		CTTrPr trPr = getRowCTTrPr(row);
		CTHeight trHeight;
		if (trPr.getTrHeightList() != null && trPr.getTrHeightList().size() > 0) {
			trHeight = trPr.getTrHeightList().get(0);
		} else {
			trHeight = trPr.addNewTrHeight();
		}
		trHeight.setVal(new BigInteger(hight));
		if (heigthEnum != null) {
			trHeight.setHRule(heigthEnum);
		}
	}
	/**
	 * @Description: 得到CTTrPr,不存在则新建
	 */
	public CTTrPr getRowCTTrPr(XWPFTableRow row) {
		CTRow ctRow = row.getCtRow();
		CTTrPr trPr = ctRow.isSetTrPr() ? ctRow.getTrPr() : ctRow.addNewTrPr();
		return trPr;
	}
	@Override
	public String exportUserPower(HttpServletRequest request, HttpServletResponse response, UserBo record1) {
		// TODO Auto-generated method stub
		List<UserBo> listBo = userMapper.selectUserTypeNumber(record1);
		for(int i=0;i<listBo.size();i++){
			if(listBo.get(i).getManagertag().equals("1")){
				listBo.get(i).setManagertag("普通管理员");
			}
			if(listBo.get(i).getManagertag().equals("0")){
				listBo.get(i).setManagertag("代理商");
			}
			if(listBo.get(i).getManagertag().equals("4")){
				listBo.get(i).setManagertag("总监");
			}
		}
		
		
		// 创建Excel
		         OutputStream out = null;
		          String loadurl = "";
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet();
				sheet.setColumnWidth(0, 11 * 256);
				sheet.setColumnWidth(1, 25 * 256);
				sheet.setColumnWidth(2, 15 * 256);
				sheet.setColumnWidth(3, 10 * 256);
				sheet.setColumnWidth(4, 10 * 256);
				sheet.setColumnWidth(5, 10 * 256);
				sheet.setColumnWidth(6, 10 * 256);
				sheet.setColumnWidth(7, 10 * 256);
				
			     try {
			    	 HSSFRow rowm = sheet.createRow(0); // 标题行
						String top[] = {"贝赛科技","业务员","业务类型","用户总数","试用用户数","过期用户","禁用用户","签约用户","内部用户"};
						for (int i = 0; i < top.length; i++) {
							HSSFCell cellm = rowm.createCell(i);
							cellm.setCellValue(top[i]);
							cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
						}
			    	   int rownum =1;
			    	   HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
						for(UserBo ub: listBo){
							HSSFRow row = sheet.createRow(rownum);
							HSSFCell cell = row.createCell(0);
							cell.setCellValue("贝赛科技");
							cell.setCellStyle(style);
							
							cell = row.createCell(1);
							cell.setCellValue(ub.getManagername());
							cell.setCellStyle(style);
							
							cell = row.createCell(2);
						    cell.setCellValue(ub.getManagertag());
						     cell.setCellStyle(style);
							
							cell = row.createCell(3);
							cell.setCellValue(ub.getTotalid());
							cell.setCellStyle(style);
							
							cell = row.createCell(4);
							cell.setCellValue(ub.getShiyongAcount());
							cell.setCellStyle(style);
							
							cell = row.createCell(5);
							cell.setCellValue(ub.getExpirAcount());
							cell.setCellStyle(style);
							
							cell = row.createCell(6);
							cell.setCellValue(ub.getJinzhiAcount());
							cell.setCellStyle(style);
							
							cell = row.createCell(7);
							cell.setCellValue(ub.getBiaozhunAcount());
							cell.setCellStyle(style);
							
							cell = row.createCell(8);
							cell.setCellValue(ub.getInnerAcount());
							cell.setCellStyle(style);
							rownum++;
						}
						
						UserBo ubs = userMapper.selectTotalUserTypeNumber(record1);
						
						HSSFRow row = sheet.createRow(rownum);
						HSSFCell cell = row.createCell(0);
						cell.setCellValue("贝赛科技");
						cell.setCellStyle(style);
						
						cell = row.createCell(1);
						cell.setCellValue("合计");
						cell.setCellStyle(style);
						
						cell = row.createCell(2);
							cell.setCellValue("无");
							cell.setCellStyle(style);
					
						cell = row.createCell(3);
						cell.setCellValue(ubs.getTotalid());
						cell.setCellStyle(style);
						
						cell = row.createCell(4);
						cell.setCellValue(ubs.getShiyongAcount());
						cell.setCellStyle(style);
						
						cell = row.createCell(5);
						cell.setCellValue(ubs.getExpirAcount());
						cell.setCellStyle(style);
						
						cell = row.createCell(6);
						cell.setCellValue(ubs.getJinzhiAcount());
						cell.setCellStyle(style);
						
						cell = row.createCell(7);
						cell.setCellValue(ubs.getBiaozhunAcount());
						cell.setCellStyle(style);
						
						cell = row.createCell(8);
						cell.setCellValue(ubs.getInnerAcount());
						cell.setCellStyle(style);
						
						//创建excel
						//取得有效的行数
					       int rowcount = sheet.getLastRowNum(); 
					       System.out.println(rowcount);
					       sheet.addMergedRegion(new Region(0, (short) (0), 3,   
			                       (short) (0)));   
						   HSSFCell cell01 = rowm.createCell((short) (0));   
			               cell01.setCellValue("贝赛科技"); // 跨单元格显示数据   
			               cell01.setCellStyle(style);
					       String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
							String fileName = date + ".xls";
							String headStr = "attachment; filename=" + fileName;
							response.setContentType("application/vnd.ms-excel;charset=utf-8");
							response.setHeader("Content-Disposition", headStr);
							
							String path = request.getSession().getServletContext()
									.getRealPath("/upload");
							File targetFile = new File(path, fileName);
							if (!targetFile.getParentFile().exists()) {
								targetFile.getParentFile().mkdirs();
							}
							if (!targetFile.exists()) {
								targetFile.createNewFile();
								// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
							}
							path = path + "/" + fileName;
							loadurl = "upload/"+fileName;
							out = new FileOutputStream(new File(path));
							workbook.write(out);
				} catch (Exception e) {
					Log.error(e.getMessage(),e);
				}finally {
					if (out != null) {
						try {
							out.flush();
							out.close();
						} catch (IOException e) {
							Log.error(e.getMessage(),e);
						}
					}
				}
						return loadurl;
	}

	@Override
	public String exportUserList(HttpServletRequest request, HttpServletResponse response, UserBo record) {
		// TODO Auto-generated method stub
		List<UserBo> listBo = new ArrayList<UserBo>();
		List<User> lists = userMapper.selectUserAndPower(record);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for(int j=0;j<lists.size();j++){
			UserBo ub = new UserBo();
			BeanUtils.copyProperties(lists.get(j), ub);
			if (null!=lists.get(j).getExpirydate()) {
				String time = formatter.format(lists.get(j).getExpirydate());
				ub.setExpirydate(time);
			}
			Date  end= lists.get(j).getExpirydate();
			Date  start= lists.get(j).getStartdate();
			if(start == null){
			    start = lists.get(j).getRegistertime();
				  
			}
		    if(start!=null && end!=null){
		    	Integer allExpirDay = TestDate.daysBetween(start, end);
		    	if(allExpirDay<0){
		    		ub.setAllexpirDay(0);
		    	}else{
		    		 ub.setAllexpirDay(allExpirDay);
		    	}
				  
		    }
		   Date now = new Date();
		   if(end!=null){
			   Integer resExpirDay = TestDate.daysBetween(now, end);
			   if(resExpirDay<0){
				   ub.setResexpirDay(0); 
			   }else{
				   ub.setResexpirDay(resExpirDay);
			   }
			  
		   }
		  listBo.add(ub);
		}
		
		
		// 创建Excel
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 25 * 256);
		sheet.setColumnWidth(2, 15 * 256);
		sheet.setColumnWidth(3, 10 * 256);
		sheet.setColumnWidth(4, 10 * 256);
		sheet.setColumnWidth(5, 10 * 256);
		sheet.setColumnWidth(6, 10 * 256);
		sheet.setColumnWidth(7, 10 * 256);
		sheet.setColumnWidth(8, 10 * 256);
		sheet.setColumnWidth(9, 10 * 256);
		sheet.setColumnWidth(10, 10 * 256);
		sheet.setColumnWidth(11, 10 * 256);
		sheet.setColumnWidth(12, 10 * 256);
		sheet.setColumnWidth(13, 10 * 256);
		sheet.setColumnWidth(14, 10 * 256);
		sheet.setColumnWidth(15, 10 * 256);
		sheet.setColumnWidth(16, 10 * 256);
		sheet.setColumnWidth(17, 10 * 256);
		sheet.setColumnWidth(18, 10 * 256);
		sheet.setColumnWidth(19, 10 * 256);
		sheet.setColumnWidth(20, 10 * 256);
		sheet.setColumnWidth(21, 10 * 256);
		sheet.setColumnWidth(22, 10 * 256);
	
		
		OutputStream out = null;
		String loadurl = "";
		try {
			HSSFRow rowm = sheet.createRow(0); // 标题行
			String top[] = {"序号","机构名称","用户类型", "有效天数 ", "天数 ", "日报开通", "周报开通","月报开通","模板可选择数量","  专题","个数","  人物 ","个数 ","  关键词","个数 ","  微监测","项数","  预警","个数","  专报","次数","  云搜索","次数"};
			
			for (int i = 0; i < top.length; i++) {
				HSSFCell cellm = rowm.createCell(i);
				cellm.setCellValue(top[i]);
				cellm.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook));
			}
			   sheet.addMergedRegion(new Region(0, (short) (3), 0,   
                       (short) (4)));   
			   HSSFCell cell01 = rowm.createCell((short) (3));   
               cell01.setCellValue("有效天数"); // 跨单元格显示数据   
               cell01.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式   
               sheet.addMergedRegion(new Region(0, (short) (9), 0,   
                       (short) (10)));   
			   HSSFCell cell02 = rowm.createCell((short) (9));   
               cell02.setCellValue("专题个数"); // 跨单元格显示数据   
               cell02.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式   
               sheet.addMergedRegion(new Region(0, (short) (11), 0,   
                       (short) (12)));   
			   HSSFCell cell03 = rowm.createCell((short) (11));   
               cell03.setCellValue("人物个数"); // 跨单元格显示数据   
               cell03.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式   
               
               sheet.addMergedRegion(new Region(0, (short) (13), 0,   
                       (short) (14)));   
			   HSSFCell cell04= rowm.createCell((short) (13));   
               cell04.setCellValue("关键词个数"); // 跨单元格显示数据   
               cell04.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式  
               
               sheet.addMergedRegion(new Region(0, (short) (15), 0,   
                       (short) (16)));   
			   HSSFCell cell05= rowm.createCell((short) (15));   
               cell05.setCellValue("监测项数"); // 跨单元格显示数据   
               cell05.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式
               
               sheet.addMergedRegion(new Region(0, (short) (17), 0,   
                       (short) (18)));   
			   HSSFCell cell06= rowm.createCell((short) (17));   
               cell06.setCellValue("预警个数"); // 跨单元格显示数据   
               cell06.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式
               
               sheet.addMergedRegion(new Region(0, (short) (19), 0,   
                       (short) (20)));   
			   HSSFCell cell07= rowm.createCell((short) (19));   
               cell07.setCellValue("专报次数"); // 跨单元格显示数据   
               cell07.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式
               
               sheet.addMergedRegion(new Region(0, (short) (21), 0,   
                       (short) (22)));   
			   HSSFCell cell08= rowm.createCell((short) (21));   
               cell08.setCellValue("云搜索次数"); // 跨单元格显示数据   
               cell08.setCellStyle(ExportExcelUtil.getColumnTopStyle(workbook)); // 式
			HSSFCellStyle style =ExportExcelUtil.getStyle(workbook);
			HSSFRow rowm1 = sheet.createRow(1); // 标题行
			String top1[] = {"0", "..", "..", "全部", "剩余", "..", "..","..","..","全部","剩余","全部","剩余","全部","剩余","全部","剩余","全部","剩余","全部","剩余","全部","剩余"};
			
			for (int i = 0; i < top1.length; i++) {
				HSSFCell cell = rowm1.createCell(i);
				cell.setCellValue(top1[i]);
				cell.setCellStyle(style);
			}
			int rownum = 2;
			for(UserBo ub:listBo){
				HSSFRow row = sheet.createRow(rownum);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(rownum-1);
				cell.setCellStyle(style);
				cell = row.createCell(1);
				if(!"".equals(ub.getOrgname()) && ub.getOrgname()!=null){
					cell.setCellValue(ub.getOrgname());
				}else{
					cell.setCellValue(".");
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(2);
				if(!"".equals(ub.getUsertype()) && ub.getUsertype()!=null){
					cell.setCellValue(ub.getUsertype());
				}else{
					cell.setCellValue(".");
				}
				cell.setCellStyle(style);
				cell = row.createCell(3);
				if(null!=ub.getAllexpirDay()){
					cell.setCellValue(ub.getAllexpirDay());
				}else{
					cell.setCellValue(".");
				}
			
				cell.setCellStyle(style);
				cell = row.createCell(4);
				if(null!=ub.getResexpirDay()){
					cell.setCellValue(ub.getResexpirDay());
				}else{
					cell.setCellValue(".");
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(5);
				if(null!=ub.getDayReport()){
					if(0==ub.getDayReport()){
						cell.setCellValue("未开通");
					}else{
						cell.setCellValue("开通");
					}
				}else{
					cell.setCellValue("未开通");
				}
				
				if(null == ub.getDayReport()){
					cell.setCellValue(".");
				}
				cell.setCellStyle(style);
				
				cell = row.createCell(6);
				if(null!=ub.getWeekReport()){
					if(0==ub.getWeekReport()){
						cell.setCellValue("未开通");
					}else{
						cell.setCellValue("开通");
					}
				}else{
					cell.setCellValue("未开通");
				}
				
				if(null == ub.getWeekReport()){
					cell.setCellValue(".");
				}
				cell.setCellStyle(style);
				cell = row.createCell(7);
				if(null!=ub.getMonthReport()){
					if(0==ub.getMonthReport()){
						cell.setCellValue("未开通");
					}else{
						cell.setCellValue("开通");
					}
				}else{
					cell.setCellValue("未开通");
				}
				
				if(null == ub.getMonthReport()){
					cell.setCellValue(".");
				}
				//cell.setCellValue(ub.getMonthReport());
				cell.setCellStyle(style);
				cell = row.createCell(8);
				if(null == ub.getModalNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getModalNum());
				}
			
				cell.setCellStyle(style);
				cell = row.createCell(9);
				if(null == ub.getSubjecttimes()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getSubjecttimes());
				}
				cell.setCellStyle(style);
				cell = row.createCell(10);
				if(null == ub.getRessubjectNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getRessubjectNum());
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(11);
				if(null == ub.getPersontimes()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getPersontimes());
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(12);
				if(null == ub.getResPersonNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getResPersonNum());
				}
				cell.setCellStyle(style);
				cell = row.createCell(13);
				if(null == ub.getKeywordNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getKeywordNum());
				}
				cell.setCellStyle(style);
				cell = row.createCell(14);
				if(null == ub.getReskeywordNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getReskeywordNum());
				}
				cell.setCellStyle(style);
				cell = row.createCell(15);
				if(null == ub.getMonitortimes()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getMonitortimes());
				}
				cell.setCellStyle(style);
				cell = row.createCell(16);
				if(null == ub.getResmiroNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getResmiroNum());
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(17);
				if(null == ub.getWarntimes()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getWarntimes());
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(18);
				if(null == ub.getResyujingNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getResyujingNum());
				}
				
				cell.setCellStyle(style);
				cell = row.createCell(19);
				if(null == ub.getSubjectReport()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getSubjectReport());
				}
			
				cell.setCellStyle(style);
				cell = row.createCell(20);
				if(null == ub.getResreportNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getResreportNum());
				}
			
				cell.setCellStyle(style);
				cell = row.createCell(21);
				if(null == ub.getCloudtimes()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getCloudtimes());
				}
			
				cell.setCellStyle(style);
				cell = row.createCell(22);
				if(null == ub.getRescloudNum()){
					cell.setCellValue(".");
				}else{
					cell.setCellValue(ub.getRescloudNum());
				}
				
				cell.setCellStyle(style);
				rownum++;
			}
			String date = DateFormatUtil.getCurrentTime("yyyyMMddHHmmss");
			String fileName = date + ".xls";
			String headStr = "attachment; filename=" + fileName;
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", headStr);
			
			String path = request.getSession().getServletContext()
					.getRealPath("/upload");
			File targetFile = new File(path, fileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				targetFile.createNewFile();
				// response.sendRedirect("/app-opinion-web/system/system.html?url="+imgaddress);
			}
			path = path + "/" + fileName;
			loadurl = "upload/"+fileName;
			out = new FileOutputStream(new File(path));
			workbook.write(out);
			
			
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					Log.error(e.getMessage(),e);;
				}
			}
		}
				return loadurl;
	}

	@Override
	public List<User> selectUTByMId(UserBo record) {
		// TODO Auto-generated method stub
		
		List<User> lists = userMapper.selectUserAndPower(record);
		
		
		return lists;
	}
	@Override
	public List<User> selectUserNameByMId(User record) {
		// TODO Auto-generated method stub
		return userMapper.selectUserNameByMId(record);
	}

	@Override
	public List<StanderPower> selectnewStanderPower(StanderPower record) {
		// TODO Auto-generated method stub
		return standerPowerMapper.selectnewStanderPower(record);
	}

	@Override
	public List<User> selectUserByManagerIdByM(User user) {
		// TODO Auto-generated method stub
		return userMapper.selectUserByManagerIdByM(user);
	}

	@Override
	public List<User> selectUserListByM(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectUserListByM(record);
	}

	@Override
	public List<User> selectDetailUserListByM(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectDetailUserListByM(record);
	}

	@Override
	public List<User> selectUserNameByMIdByM(User record) {
		// TODO Auto-generated method stub
		return userMapper.selectUserNameByMIdByM(record);
	}

	@Override
	public List<User> selectuserInfoByM(User record) {
		// TODO Auto-generated method stub
		return userMapper.selectuserInfoByM(record);
	}

	@Override
	public List<User> selectidByM() {
		// TODO Auto-generated method stub
		return userMapper.selectidByM();
	}

	@Override
	public List<User> selectUserAndPowerByM(UserBo record) {
		// TODO Auto-generated method stub
		return userMapper.selectUserAndPowerByM(record);
	}

	@Override
	public List<UserBo> selectUserTypeNumberByM(UserBo record) {
		// TODO Auto-generated method stub
		List<UserBo> listBo = new ArrayList<UserBo>();
		listBo = userMapper.selectUserTypeNumberByM(record);
	
		return listBo;
	}

	@Override
	public UserBo selectTotalUserTypeNumberByM(UserBo record) {
		// TODO Auto-generated method stub
		UserBo ub = userMapper.selectTotalUserTypeNumberByM(record);
		return ub;
	}

	@Override
	public List<ManagerUser> selectManagerUserByName(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectManagerUserByName(record);
	}

	@Override
	public List<ManagerUser> findMenuByparentId(ManagerUser record) {
		// TODO Auto-generated method stub
		return userMapper.findMenuByparentId(record);
	}

	@Override
	public List<ManagerUser> selectByParentId() {
		// TODO Auto-generated method stub
		return managerUserMapper.selectByParentId();
	}

	@Override
	public List<String> getChids(List<ManagerUser> list, String parentid) {
		// TODO Auto-generated method stub
	    List<String> listids = new ArrayList<String>();
	    listids.add(parentid);
		  if(list == null && parentid == null){
			  return null;
			 
		   }
		  if(null!=list){
			  for (Iterator<ManagerUser> iterator = list.iterator(); iterator.hasNext();) {
		        	ManagerUser node = (ManagerUser) iterator.next();
		            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
		            if (node.getParentid()!=null &&parentid.equals(node.getParentid())) {
		                returnList.add(node.getId());
		                //递归遍历子后子
		                getChids(list, node.getId());
		            }
		        } 
		  }
	      if(null!=returnList){
	    	  Iterator<String> it1 = returnList.iterator();
		        for (String ss : returnList) {
		        	listids.add(ss);
		        } 
	      }
	        
	        
	        return listids;
	}

	//修改客户销售员
	@Override
	public User selectUserById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectUserById(id);
	}

	@Override
	public int updateUserById(User record) {
		// TODO Auto-generated method stub
		return userMapper.updateUserById(record);
	}
    //

	@Override
	public UserType selectUserType(String id) {
		// TODO Auto-generated method stub
		return userTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> selectByOrgName(String orgname) {
		// TODO Auto-generated method stub
		return userMapper.selectByOrgName(orgname);
	}

	@Override
	public UserType selectUserTypeById(String id) {
		// TODO Auto-generated method stub
		return userTypeMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ManagerUser> selectSonManager(String parentid) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectSonManager(parentid);
	}

	@Override
	public List<ManagerUser> selectByTag() {
		// TODO Auto-generated method stub
		return managerUserMapper.selectByTag();
	}

	@Override
	public List<User> selectuserInfoMaintainAdministrator(User record) {
		// TODO Auto-generated method stub
		return userMapper.selectuserInfoMaintainAdministrator(record);
	}
	

	


}

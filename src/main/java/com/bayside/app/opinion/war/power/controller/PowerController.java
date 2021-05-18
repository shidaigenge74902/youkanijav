package com.bayside.app.opinion.war.power.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.bayside.app.opinion.war.mymessage.bo.MessageCenterBo;
import com.bayside.app.opinion.war.mymessage.model.Messagecenter;
import com.bayside.app.opinion.war.mymessage.model.OfficalUser;
import com.bayside.app.opinion.war.mymessage.service.MyMessageService;
import com.bayside.app.opinion.war.myuser.bo.ReMoneyBo;
import com.bayside.app.opinion.war.myuser.bo.StanderPowerBo;
import com.bayside.app.opinion.war.myuser.bo.UserBo;
import com.bayside.app.opinion.war.myuser.bo.UserLogBo;
import com.bayside.app.opinion.war.myuser.bo.UserTypeBo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.ReMoney;
import com.bayside.app.opinion.war.myuser.model.StanderPower;
import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.model.UserLog;
import com.bayside.app.opinion.war.myuser.model.UserType;
import com.bayside.app.opinion.war.myuser.model.WordSet;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.power.service.impl.PowerServiceImpl;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.service.SubjectArticleService;
import com.bayside.app.opinion.war.subject.service.impl.SubjectArticleServiceImpl;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.HdfsUpLoadUtil;
import com.bayside.app.util.Note;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.SendCode;
import com.bayside.app.util.TestDate;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class PowerController {
	@Autowired
	private PowerUserService powerServiceImpl;
	@Autowired
	private MyMessageService myMessageServiceImpl;
	@Autowired
	private UserService userServiceImpl;
	 @Autowired
	private SubjectArticleService subjectArticleServiceImpl;
	
	public static Random r = new Random();
	private static Logger Log = LoggerFactory.getLogger(PowerController.class);

	/**
	 * <p>
	 * 方法名称：saveUserInfo
	 * </p>
	 * <p>
	 * 方法描述：保存提交申请的用户
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/saveUserInfo", method = RequestMethod.GET)
	public Response saveUserInfo(User user, HttpServletRequest request,HttpSession session) {
		
			user.setId(UuidUtil.getUUID());
			System.out.println(user.getName() + "name11");
			// HttpSession session = ServletActionContext.getRequest().getSession();
			String code = request.getParameter("code");
			List<User> s = powerServiceImpl.selectByTel(user);
			  
				  if (null!=code&&!"".equals(code)&&code.equals(user.getCode())) {
						// user.set
					  user.setRegistertime(new Date());
						int num = powerServiceImpl.saveUser(user);
						if (num > 0) {
							// HttpSession session = request.getSession();
							// session.setAttribute("username", "yugi");
							
							return new Response(ResponseStatus.Success, num, true);
						} else {
							return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
						}
					} else {
						return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
					}
	}

	/**
	 * <p>
	 * 方法名称：sendTelCheck
	 * </p>
	 * <p>
	 * 方法描述：//发送手机验证码
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/sendTelCheck", method = RequestMethod.GET)
	public Response sendTelCheck(User user) {
		Boolean flag = true;
		long nums = Math.abs(r.nextLong() % 10000000000L);
		/*
		 * String s = String.valueOf(nums); for(int i = 0; i < 6-s.length();
		 * i++){ s = "0" + s; }
		 */
		Random random = new Random();

		String s = "";

		for (int i = 0; i < 6; i++) {

			s += random.nextInt(10);

		}
		
		System.out.println(s);
		String name = "用户";
		String tel = user.getMobilephone();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("Account", "lyy"));
		formparams.add(new BasicNameValuePair("Pwd", "3355315CD86A2BC5B0A6F2114DC4"));
		formparams.add(new BasicNameValuePair("Content", name + "||" + s));
		formparams.add(new BasicNameValuePair("Mobile", tel));
		formparams.add(new BasicNameValuePair("TemplateId", "30065"));

		formparams.add(new BasicNameValuePair("SignId", "30273"));
		try {
			if (flag) {
				SendCode.Post(formparams);
				flag = false;
			}
		} catch (Exception e) {
			Log.error(e.getMessage(),e);
		}
		return new Response(ResponseStatus.Success, s, true);

	}


	/**
	 * <p>
	 * 方法名称：selectByName
	 * </p>
	 * <p>
	 * 方法描述： 通过用户名查询登录用户是否存在
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByName", method = RequestMethod.POST)
	public Response selectByName(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		String name = request.getParameter("name");
		user.setName(name);
		List<User> newUser = new ArrayList<User>();
		if (user.getName() != null || !"".equals(user.getName())) {
			newUser = powerServiceImpl.selectByName(user);
		}
		if (newUser.size() > 0) {
			return new Response(ResponseStatus.Success, newUser, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * <p>
	 * 方法名称：selectByTel
	 * </p>
	 * <p>
	 * 方法描述：通过手机号查询用户是否存在
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByTel", method = RequestMethod.POST)
	public Response selectByTel(HttpServletRequest request, HttpServletResponse response) {
		List<User> newuser = new ArrayList<User>();
		User user = new User();
		String tel = request.getParameter("tel");
		user.setMobilephone(tel);
		if (user.getMobilephone() != null || !"".equals(user.getMobilephone())) {
			newuser = powerServiceImpl.selectByTel(user);
		}

		return new Response(ResponseStatus.Success, newuser, true);

	}
	/**
	 * 
	 * <p>方法名称：selectByMobile</p>
	 * <p>方法描述：检测手机号是否重复</p>
	 * @param request
	 * @param response
	 * @return
	 * @author liuyy
	 * @since  2017年1月4日
	 * <p> history 2017年1月4日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectByMobile", method = RequestMethod.GET)
	public Response selectByMobile(User record) {
		List<User> newuser = new ArrayList<User>();
		
		if (null!=record.getMobilephone() || !"".equals(record.getMobilephone())) {
			newuser = powerServiceImpl.selectByTel(record);
		}
        if(newuser.size()>0){
        	return new Response(ResponseStatus.Success, newuser, true);
        }else{
        	return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
        }
	}
	@RequestMapping(value = "/selectPassword", method = RequestMethod.POST)
	public Response selectPassword(HttpServletRequest request, HttpServletResponse response) {
		String id = (String) request.getSession().getAttribute("userid");
		User user = powerServiceImpl.selectagentById(id);

		if (user != null) {
			return new Response(ResponseStatus.Success, user, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectByEmail
	 * </p>
	 * <p>
	 * 方法描述：通过邮箱号查询登录用户
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectByEmail", method = RequestMethod.POST)
	public Response selectByEmail(HttpServletRequest request, HttpServletResponse response) {
		List<User> newUser = new ArrayList<User>();
		User user = new User();
		String email = request.getParameter("email");
		user.setEmail(email);
		if (null!=user.getEmail() || !"".equals(user.getEmail())) {
			newUser = powerServiceImpl.selectByEmail(user);
		}

		if (newUser.size() > 0) {
			return new Response(ResponseStatus.Success, newUser, true);
		} else {
			return new Response(ResponseStatus.Error, "", false);
		}
	}

	/**
	 * <p>
	 * 方法名称：selectAllUserInfo
	 * </p>
	 * <p>
	 * 方法描述：查询所有用户信息
	 * </p>
	 * @param user
	 * @param page
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllUserName", method = RequestMethod.GET)
	public Response selectAllUserName(HttpServletRequest request) {
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		List<User> list = new ArrayList<User>();
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.THREE)){
				list = powerServiceImpl.selectidByM();
			}else if(mu.getTag().equals(AppConstant.managertype.ONE) || mu.getTag().equals(AppConstant.managertype.ZERO) || mu.getTag().equals(AppConstant.managertype.FOUR)){
				User user = new User();
				  String managerid = (String)request.getSession().getAttribute("managerid");
				   
				    user.setManagerid(managerid);
				    
					List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
					Note nt = new Note();
					List<String> setList = nt.getChids(mlist, managerid);
					user.setList(setList);
			
				list = powerServiceImpl.selectUserNameByMId(user);
				
			}else{
				list = powerServiceImpl.selectid();
			}
		}
	 
		if (list.size()>0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}
	/**
	 * <p>
	 * 方法名称：selectAllUserInfo
	 * </p>
	 * <p>
	 * 方法描述：查询管理者所有用户信息
	 * </p>
	 * 
	 * @param user
	 * @param page
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllUNameByMId", method = RequestMethod.GET)
	public Response selectAllUNameByMId(HttpServletRequest request) {
		String managerid = "";
		User user = new User();
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(2) || mu.getTag() == 2 || mu.getTag().equals(3) || mu.getTag() == 3){
				
			}else{
				managerid = mu.getId();
				List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
				List<String> setList = powerServiceImpl.getChids(mlist, managerid);
				user.setList(setList);
			}
		}
	
		user.setManagerid(managerid);
		
		List<User> list = powerServiceImpl.selectUserNameByMId(user);
		if (list.size()>0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}
	/**
	 * <p>
	 * 方法名称：selectAllUserInfo
	 * </p>
	 * <p>
	 * 方法描述：分页查询所有登录用户信息
	 * </p>
	 * 
	 * @param user
	 * @param page
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllUserInfo", method = RequestMethod.GET)
	public Response selectAllUserInfo(User user, PageInfo page,HttpServletRequest request) {
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		PageInfo<User> info = null;
		List<User> list = new ArrayList<User>();
		
		if(mu!=null){
			/*if(mu.getTag().equals(AppConstant.managertype.FOUR)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				
				list = powerServiceImpl.selectuserInfoByM(user);
			}else*/ if(mu.getTag().equals(AppConstant.managertype.ONE)|| mu.getTag().equals(AppConstant.managertype.ZERO) || mu.getTag().equals(AppConstant.managertype.FOUR)){
				
				    String managerid = (String)request.getSession().getAttribute("managerid");
				   
				    user.setManagerid(managerid);
				    
					List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
					Note nt = new Note();
					List<String> setList = nt.getChids(mlist, managerid);
					user.setList(setList);
					 PageHelper.startPage(page.getPageNum(), page.getPageSize());
				     list = powerServiceImpl.selectUserByManagerId(user);
				     System.out.println(list+"ghgjhkoipo[");
			}else if(mu.getTag().equals(AppConstant.managertype.SEVEN)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				list = powerServiceImpl.selectuserInfoMaintainAdministrator(user);
			}else{
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				 list = powerServiceImpl.selectuserInfo(user);
			}
			for(int i=0;i<list.size();i++){
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
 				if (null!=list.get(i).getExpirydate()) {
					String time = sd.format(list.get(i).getExpirydate());
					list.get(i).setCpoyTime(time);
				}
			}
			 info = new PageInfo<User>(list);
		}
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	

	/**
	 * <p>
	 * 方法名称：updateUserInfo
	 * </p>
	 * <p>
	 * 方法描述： 修改用户
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public Response updateUserInfo(@RequestBody UserBo record) {
		User user = powerServiceImpl.updateUserA(record);
		if (user.getId() != null && !"".equals(user.getId())) {
			int num = powerServiceImpl.updateUserAttr(user);
			if (num > 0) {
				//修改专题
				  Subject sub = new Subject();
				  sub.setUserid(record.getId());
				String trade = record.getTrade();
				 if("0".equals(trade)){
					  String formats = "tv,bt,trade";
					  sub.setIstrade(formats);
					   subjectArticleServiceImpl.updateSubjectByUserid(sub);
				  }else{
					  String formats ="";
					  sub.setIstrade(formats);
					  subjectArticleServiceImpl.updateSubjectByUserid(sub);
				  }
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateUserShen
	 * </p>
	 * <p>
	 * 方法描述：审核成功
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月28日
	 *        <p>
	 *        history 2016年11月28日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserShen", method = RequestMethod.GET)
	public Response updateUserShen(UserBo record) {
		User user = powerServiceImpl.userShen(record);
		if (user.getId() != null && !"".equals(user.getId())) {
			int num = powerServiceImpl.updateUserShen(user);
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateUserStatus
	 * </p>
	 * <p>
	 * 方法描述：修改用户状态 禁止 审核失败
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月28日
	 *        <p>
	 *        history 2016年11月28日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserStatus", method = RequestMethod.GET)
	public Response updateUserStatus(UserBo record) {
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (record.getExpirydate() != null && !"".equals(record.getExpirydate())) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				Log.error(e.getMessage(),e);
			}
		}
		if (user.getId() != null && !"".equals(user.getId())) {
			int num = powerServiceImpl.updateUserStatus(user);
			if (num > 0) {
				return new Response(ResponseStatus.Success, num, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectPowerByName
	 * </p>
	 * <p>
	 * 方法描述：根据用户id 权限名称 查询相应权限
	 * </p>
	 * 
	 * @param request
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectPowerByName", method = RequestMethod.GET)
	public Response selectPowerByName(HttpServletRequest request, WordSet record) {
		String userid = (String) request.getSession().getAttribute("userid");
		record.setUserid(userid);
		WordSet ws = new WordSet();
		ws = powerServiceImpl.selectPowerByName(record);
		if (ws != null) {
			return new Response(ResponseStatus.Success, ws, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：updateUserStatusInfo
	 * </p>
	 * <p>
	 * 方法描述：审核失败
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年10月14日
	 *        <p>
	 *        history 2016年10月14日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserStatusInfo", method = RequestMethod.GET)
	public Response updateUserStatusInfo(UserBo record) {
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (record.getExpirydate() != null && !"".equals(record.getExpirydate())) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				Log.error(e.getMessage(),e);
			}

		}
		int num = powerServiceImpl.updateagentuser(user);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：stopUserStatusInfo
	 * </p>
	 * <p>
	 * 方法描述：禁用用户
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年10月14日
	 *        <p>
	 *        history 2016年10月14日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/stopUserStatusInfo", method = RequestMethod.GET)
	public Response stopUserStatusInfo(UserBo record) {
		User user = new User();
		BeanUtils.copyProperties(record, user);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (record.getExpirydate() != null) {
			try {
				Date date = formatter.parse(record.getExpirydate());
				user.setExpirydate(date);
			} catch (ParseException e) {
				Log.error(e.getMessage(),e);
			}

		}
		int num = powerServiceImpl.updateagentuser(user);
		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/selectStatus", method = RequestMethod.GET)
   public Response selectStatus(User user){
	   User us  = userServiceImpl.selectByPrimaryKey(user.getId());
			   if(us!=null){
				   return new Response(ResponseStatus.Success, us, true);
			   }else{
				   return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			   }
   }
	/**
	 * <p>
	 * 方法名称：checkUserInfo
	 * </p>
	 * <p>
	 * 方法描述： 查看用户属性
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/checkUserInfo", method = RequestMethod.GET)
	public Response checkUserInfo(User user) {
		UserBo ub = powerServiceImpl.checkagentById(user.getId(), user);
		if (ub != null) {
			return new Response(ResponseStatus.Success, ub, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * <p>
	 * 方法名称：repassword
	 * </p>
	 * <p>
	 * 方法描述：随机生成密码
	 * </p>
	 * 
	 * @param user
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/repassword", method = RequestMethod.GET)
	public Response repassword(User user) {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		user.setPassword(result);
		if (user.getId() != null && !"".equals(user.getId())) {
			int num = powerServiceImpl.updatePassword(user);
			if (num > 0) {
				return new Response(ResponseStatus.Success, user, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
			}
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

	}
    /**
     * 
     * <p>方法名称：checkLoginUser</p>
     * <p>方法描述：查询登录用户信息 在header 显示登录人姓名</p>
     * @param request
     * @return
     * @author liuyy
     * @since  2016年12月1日
     * <p> history 2016年12月1日 Administrator  创建   <p>
     */
	@RequestMapping(value = "/checkLoginUser", method = RequestMethod.GET)
	public Response checkLoginUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			return new Response(ResponseStatus.Success, user, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectUserPower
	 * </p>
	 * <p>
	 * 方法描述：查询用户权限
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectUserPower", method = RequestMethod.GET)
	public Response selectUserPower(HttpServletRequest request) {
		String userid = (String) request.getSession().getAttribute("userid");
		User user = powerServiceImpl.selectByPrimaryKey(userid);
		if (user != null) {
			return new Response(ResponseStatus.Success, user, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/*
	 * public static String getRealIp(HttpServletRequest request) { String ip =
	 * request.getHeader("X-Real-IP"); if (!StringUtils.isBlank(ip) &&
	 * !"unknown".equalsIgnoreCase(ip)) { return ip; } ip =
	 * request.getHeader("X-Forwarded-For"); if (!StringUtils.isBlank(ip) &&
	 * !"unknown".equalsIgnoreCase(ip)) {
	 * 
	 * int index = ip.indexOf(','); if (index != -1) {
	 * System.out.println(ip.substring(0, index)); return ip.substring(0,
	 * index); } else { System.out.println(ip+"rrrrr"); return ip; } } else {
	 * System.out.println(request.getRemoteAddr()); return
	 * request.getRemoteAddr(); } }
	 */
	/**
	 * 
	 * <p>
	 * 方法名称：selectUserType
	 * </p>
	 * <p>
	 * 方法描述：查询所有用户类型
	 * </p>
	 * 
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectUserType", method = RequestMethod.GET)
	public Response selectUserType() {
		List<UserType> list = powerServiceImpl.selectUserType();
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/selectTypeById", method = RequestMethod.GET)
	public Response selectTypeById(String id) {
		UserType ut = powerServiceImpl.selectUserTypeById(id);
		if (null !=ut) {
			return new Response(ResponseStatus.Success, ut, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：insertUserType
	 * </p>
	 * <p>
	 * 方法描述：添加用户类型
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/insertUserType", method = RequestMethod.GET)
	public Response insertUserType(UserTypeBo record) {
		record.setId(UuidUtil.getUUID());
		UserType ut = new UserType();
		ut.setId(UuidUtil.getUUID());
		ut.setTypename(record.getTypename());
        ut.setTypecode(record.getTypecode());
		int num = powerServiceImpl.insert(ut, record);

		if (num > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}
   /**
    * 
    * <p>方法名称：selectStanderPower</p>
    * <p>方法描述：根据用户类型查询标准权限</p>
    * @param record
    * @return
    * @author liuyy
    * @since  2016年12月1日
    * <p> history 2016年12月1日 Administrator  创建   <p>
    */
	@RequestMapping(value = "/selectStanderPower", method = RequestMethod.GET)
	public Response selectStanderPower(StanderPower record) {
		List<StanderPower> list = powerServiceImpl.selectnewStanderPower(record);
		List<StanderPowerBo> lis = new ArrayList<StanderPowerBo>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (int j = 0; j < list.size(); j++) {
			StanderPowerBo sb = new StanderPowerBo();
			BeanUtils.copyProperties(list.get(j), sb);
			if (list.get(j).getName().equals(AppConstant.standPower.EXPIRDATE)) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, list.get(j).getCansetword());
				String time = df.format(c.getTime());
				sb.setTime(time);
			}
			lis.add(sb);
		}
		if (lis.size() > 0) {
			return new Response(ResponseStatus.Success, lis, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/selectUserTypeById", method = RequestMethod.GET)
	public Response selectUserTypeById(String typeid){
		UserType ut = powerServiceImpl.selectUserType(typeid);
		if(null!=ut){
			return new Response(ResponseStatus.Success, ut, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：checkUserType
	 * </p>
	 * <p>
	 * 方法描述：分页查询用户类型
	 * </p>
	 * 
	 * @param page
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/checkUserType", method = RequestMethod.GET)
	public Response checkUserType(PageInfo page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserTypeBo> utlist = powerServiceImpl.selectUserTypeBo();
		PageInfo<UserTypeBo> info = new PageInfo<UserTypeBo>(utlist);
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}

	}

	/**
	 * 
	 * <p>
	 * 方法名称：updateUserTypeBo
	 * </p>
	 * <p>
	 * 方法描述：修改用户类型 及相应权限
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/updateUserTypeBo", method = RequestMethod.GET)
	public Response updateUserTypeBo(UserTypeBo record) {
		Boolean f = powerServiceImpl.updateUS(record);
		if (f) {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.UPDATESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

	}

	@RequestMapping(value = "/selectUserTypeBo", method = RequestMethod.GET)
	public Response selectUserTypeBo(UserTypeBo record) {
		UserTypeBo rec = powerServiceImpl.selectUserTypeBo(record.getId(), record.getTypecode());
		if (rec != null) {
			return new Response(ResponseStatus.Success, rec, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	
	
	/**
	 * 
	 * <p>
	 * 方法名称：updateUserById
	 * </p>
	 * <p>
	 * 方法描述：修改客户的销售人员
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author peigd
	 * @since 2017年03月31日
	 */
	@RequestMapping(value = "/updateUserById", method = RequestMethod.GET)
	public Response updateUserById(User record) {
		int f = powerServiceImpl.updateUserById(record);
		if (f > 0 ) {
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.UPDATESUCCESS, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.UPDATEEERRO, false);
		}

	}

	@RequestMapping(value = "/selectUserById", method = RequestMethod.GET)
	public Response selectUserById(String id ) {
		User u = powerServiceImpl.selectUserById(id);
		if (u != null) {
			return new Response(ResponseStatus.Success, u, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	
	@RequestMapping(value = "/selectManagerList", method = RequestMethod.GET)
	public Response selectManagerList(){
		List<ManagerUser> list = (List<ManagerUser>) powerServiceImpl.selectManager();
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 方法名称：deleteUserTypeBo
	 * </p>
	 * <p>
	 * 方法描述：删除用户类型
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/deleteUserTypeBo", method = RequestMethod.GET)
	public Response deleteUserTypeBo(UserTypeBo record) {
		int num = powerServiceImpl.deleteUserType(record.getId());
		StanderPower sp = new StanderPower();
		sp.setTypeid(record.getId());
		int n = powerServiceImpl.deleteStanderPower(sp);

		if (num > 0 && n > 0) {
			return new Response(ResponseStatus.Success, num, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.DELETEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：selectRepeatUserType
	 * </p>
	 * <p>
	 * 方法描述：用户类型是否重复
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月30日
	 *        <p>
	 *        history 2016年11月30日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectRepeatUserType", method = RequestMethod.GET)
	public Response selectRepeatUserType(UserTypeBo record) {
		UserType ut = powerServiceImpl.selectByTypeName(record.getTypecode());
		if (ut != null) {
			return new Response(ResponseStatus.Success, ut, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}

	/**
	 * 
	 * <p>
	 * 方法名称：
	 * </p>
	 * <p>
	 * 方法描述：查询 留言板或 导控消息
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月23日
	 *        <p>
	 *        history 2016年11月23日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllMessageByTag", method = RequestMethod.GET)
	public Response selectAllMessageByTag(MessageCenterBo record, PageInfo page,HttpServletRequest request) {
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		PageInfo<Messagecenter> info  = null;
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.THREE)){
			    List<User> listuser = powerServiceImpl.selectidByM();
			    if(listuser.size()>0){
			    	List<String> userids = new ArrayList<String>();
			    	for(int i=0;i<listuser.size();i++){
			    		userids.add(listuser.get(i).getId());
			    	}
			    	record.setUserids(userids);
			    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
					List<Messagecenter> list = myMessageServiceImpl.selectAllMessageByUserid(record);
					 info = new PageInfo<Messagecenter>(list);
			    }
			}else if(mu.getTag().equals(AppConstant.managertype.ZERO) || mu.getTag().equals(AppConstant.managertype.ONE)|| mu.getTag().equals(AppConstant.managertype.FOUR)){
				User user = new User();
			    String managerid = (String)request.getSession().getAttribute("managerid");
			    user.setManagerid(managerid);
			    List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
			    Note nt = new Note();
				List<String> setList = nt.getChids(mlist, managerid);
				user.setList(setList);
			    List<User> listuser = powerServiceImpl.selectUserByManagerId(user);
			    if(listuser.size()>0){
			    	List<String> userids = new ArrayList<String>();
			    	for(int i=0;i<listuser.size();i++){
			    		userids.add(listuser.get(i).getId());
			    	}
			    	record.setUserids(userids);
			    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
					List<Messagecenter> list = myMessageServiceImpl.selectAllMessageByUserid(record);
					 info = new PageInfo<Messagecenter>(list);
					
			    }else{
			    	return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			    }
			}else{
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				List<Messagecenter> list = myMessageServiceImpl.checkAllMessage(record);
			/*	if(olist.size()>0){
					for(int i=0;i<olist.size();i++){
						Messagecenter mc = new Messagecenter();
						mc.setId(olist.get(i).getName());
						mc.setContent("电话:"+olist.get(i).getMobilephone()+",公司:"+olist.get(i).getCompany());
						mc.setStatus("网站");
						mc.setUpdateTime(olist.get(i).getRegistertime());
						list.add(mc);
					}
					
				}*/
				info = new PageInfo<Messagecenter>(list);
			}
		}
		if (info != null) {
			return new Response(ResponseStatus.Success, info, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	/**
	 * 
	 * <p>
	 * 方法名称：selectAllMessageByTag
	 * </p>
	 * <p>
	 * 方法描述：普通管理员 /代理商 查看查询 留言板或 导控消息
	 * </p>
	 * 
	 * @param record
	 * @return
	 * @author liuyy
	 * @since 2016年11月23日
	 *        <p>
	 *        history 2016年11月23日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/selectAllMessageByUser", method = RequestMethod.GET)
	public Response selectAllMessageByUser(MessageCenterBo record, PageInfo page,HttpServletRequest request) {
		User user = new User();
	    String managerid = (String)request.getSession().getAttribute("managerid");
	    user.setManagerid(managerid);
	    List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
	    Note nt = new Note();
		List<String> setList = nt.getChids(mlist, managerid);
		user.setList(setList);
	    List<User> listuser = powerServiceImpl.selectUserByManagerId(user);
	    if(listuser.size()>0){
	    	List<String> userids = new ArrayList<String>();
	    	for(int i=0;i<listuser.size();i++){
	    		userids.add(listuser.get(i).getId());
	    	}
	    	record.setUserids(userids);
	    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<Messagecenter> list = myMessageServiceImpl.selectAllMessageByUserid(record);
			PageInfo<Messagecenter> info = new PageInfo<Messagecenter>(list);
			if (info != null) {
				return new Response(ResponseStatus.Success, info, true);
			} else {
				return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
			}
	    }else{
	    	return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
	    }
	
	}
	@RequestMapping(value = "/selectByUserType", method = RequestMethod.GET)
	public Response selectByUserType(UserType record) {
		List<User> list = powerServiceImpl.selectByUserType(record.getTypecode());
		if (list.size() > 0) {
			return new Response(ResponseStatus.Success, list, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
	}
	@RequestMapping(value = "/insertSpecialUser", method = RequestMethod.GET)
	public Response insertSpecialUser(User record){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
		String uuid = df.format(new Date());
		String uuids = dfs.format(new Date());
		
		if(null!=record){
			record.setStatus(4);
			record.setRegistertime(new Date());
			record.setStartdate(new Date());
			record.setBeizhu(uuid);
			  record.setId(uuid+UuidUtil.getUUID());
		}else{
			record = new User();
			record.setStatus(4);
			record.setRegistertime(new Date());
			record.setStartdate(new Date());
			record.setBeizhu(uuid);
			record.setId(uuid+UuidUtil.getUUID());
		}
		
		
	  //  List<User> list = powerServiceImpl.selectByEndTime(record);
	  
	    List<User> list = new ArrayList<User>();
	    if(null!=record){
	    	if(null!=record.getOrgname()){
	    		list = powerServiceImpl.selectByOrgName(record.getOrgname());
	    	}
	    }
	    if(list.size()>0){
	    	  return new Response(ResponseStatus.Error,AppConstant.responseInfo.CHECKORGNAME,false);   
	    
	    }else{
	    	int num = powerServiceImpl.specialUser(record);
			if(num >0){
				  return new Response(ResponseStatus.Success,num,true);
			}else{
				  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
			}
	    }
		
	}
	
	/**
	 * 
	 * <p>方法名称：insertPuTongUser</p>
	 * <p>方法描述：添加普通管理员</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月12日
	 * <p> history 2016年12月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/insertPuTongUser", method = RequestMethod.GET)
	public Response insertPuTongUser(ManagerUser record){
		record.setId(UuidUtil.getUUID());
		record.setRegistertime(new Date());
		int num = powerServiceImpl.insertManagerPuTong(record);
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectAllManager</p>
	 * <p>方法描述：查询所有普通管理员</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月12日
	 * <p> history 2016年12月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectAllManager", method = RequestMethod.GET)
	public Response selectAllManager(ManagerUser record){
		List<ManagerUser> list = (List<ManagerUser>) powerServiceImpl.selectAllManager(record);
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectManager", method = RequestMethod.GET)
	public Response selectManager(){
		List<ManagerUser> list = (List<ManagerUser>) powerServiceImpl.selectManager();
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectPageManager", method = RequestMethod.GET)
	public Response selectPageManager(PageInfo page){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<ManagerUser> list = (List<ManagerUser>) powerServiceImpl.selectManager();
		PageInfo<ManagerUser> info = new PageInfo<ManagerUser>(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectAllManager</p>
	 * <p>方法描述：分页查询普通管理员</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月12日
	 * <p> history 2016年12月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectAllManagerByPage", method = RequestMethod.GET)
	public Response selectAllManagerByPage(ManagerUser record,PageInfo page){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<ManagerUser> list = (List<ManagerUser>) powerServiceImpl.selectAllManager(record);
		PageInfo<ManagerUser> info = new PageInfo<ManagerUser>(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：updateManagerUser</p>
	 * <p>方法描述：修改管理员信息</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月12日
	 * <p> history 2016年12月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/updateManagerUser", method = RequestMethod.GET)
   public Response updateManagerUser(ManagerUser record){
	   int num = powerServiceImpl.updateManageUser(record);
	   if(num >0){
		   return new Response(ResponseStatus.Success,num,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	   }
   }
	@RequestMapping(value = "/selectManagerById", method = RequestMethod.GET)
	public Response selectManagerById(ManagerUser record){
		ManagerUser mu = powerServiceImpl.selectManagerUserById(record.getId());
		if(mu!=null){
			return new Response(ResponseStatus.Success,mu,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：deleteManagerUser</p>
	 * <p>方法描述：删除管理员</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月12日
	 * <p> history 2016年12月12日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/deleteManagerUser", method = RequestMethod.GET)
	public Response deleteManagerUser(ManagerUser record){
		int num = powerServiceImpl.deleteManagerUser(record.getId());
		if(num > 0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
		}
	}
	/**
	 * <p>
	 * 方法名称：ajaxUpload
	 * </p>
	 * <p>
	 * 方法描述：用户信息图片上传
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @author liuyy
	 * @since 2016年10月19日
	 *        <p>
	 *        history 2016年10月19日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "ajaxUpload", method = RequestMethod.POST)
	public String ajaxUpload(HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String newFileName = "";
		String imgurl = "";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile mulfile = multipartRequest.getFile(key);
			// 获取图片的扩展名
			String extensionName = mulfile.getOriginalFilename()
					.substring(mulfile.getOriginalFilename().lastIndexOf(".") + 1);
			// 新的图片文件名 = 获取时间戳+"."图片扩展名
			newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;

		   imgurl = powerServiceImpl.uploadImg(newFileName, mulfile);
		}
		return imgurl;
	}
	@RequestMapping(value = "imgajaxUpload", method = RequestMethod.POST)
	public String imgajaxUpload(HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String newFileName = "";
		String imgurl = "";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile mulfile = multipartRequest.getFile(key);
			// 获取图片的扩展名
			String extensionName = mulfile.getOriginalFilename()
					.substring(mulfile.getOriginalFilename().lastIndexOf(".") + 1);
			// 新的图片文件名 = 获取时间戳+"."图片扩展名
			newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;

		   imgurl = powerServiceImpl.uploadImg(newFileName, mulfile);
		}
		return imgurl;
	}
	@RequestMapping(value = "pload", method = RequestMethod.POST)
	public String  pload(HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String newFileName = null;
		String path="";
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile mulfile = multipartRequest.getFile(key);
			// 获取图片的扩展名
			String extensionName = mulfile.getOriginalFilename()
					.substring(mulfile.getOriginalFilename().lastIndexOf(".") + 1);
			// 新的图片文件名 = 获取时间戳+"."图片扩展名
			newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;
			path = request.getSession().getServletContext().getRealPath("/upload");
			File targetFile = new File(path,newFileName);
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs();
			}
			if (!targetFile.exists()) {
				try {
					targetFile.createNewFile();
				} catch (IOException e) {
					Log.error(e.getMessage(),e);
					System.out.println(e.getMessage());
				}
			}
			String filename = path+ "/" + newFileName;
			OutputStream os = null;
			try {
				os = new FileOutputStream(filename);
			} catch (FileNotFoundException e) {
				Log.error(e.getMessage(),e);
				System.out.println(e.getMessage());
			}
			try {
				if(null!=os){
					os.write(mulfile.getBytes());
					os.close();
				}
			 } catch (IOException e) {
				Log.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
		return "upload/" + newFileName;
	}
	@RequestMapping(value = "/getImg", method = RequestMethod.GET)
	public void getImg(String imgurl, HttpServletRequest request, HttpServletResponse response) {
		try {
			OutputStream os = response.getOutputStream();
			if (imgurl != null && !"".equals(imgurl)) {
				Configuration conf = new Configuration();
				conf.set("fs.defaultFS", "hdfs://60.205.106.32:9000");
				HdfsUpLoadUtil.readFile(imgurl, os, conf);
				os.close();
			}
		} catch (IOException e) { 
			e.printStackTrace();
			Log.error(e.getMessage(),e);
		}
	}
	@RequestMapping(value = "/selectAllUserLog", method = RequestMethod.GET)
	public Response selectAllUserLog(PageInfo page){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<UserLog> list = powerServiceImpl.selectUserLog();
		PageInfo<UserLog> info = new PageInfo<UserLog>(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/filterSelectUserLog", method = RequestMethod.GET) 
	public Response filterSelectUserLog(PageInfo page,UserLogBo record,HttpServletRequest request){
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		System.out.println(mu);
		PageInfo<UserLog> info  = null;
		 if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.THREE)|| mu.getTag().equals(AppConstant.managertype.FOUR)){
				   List<User> listuser = powerServiceImpl.selectidByM();
				    if(listuser.size()>0){
				    	List<String> userids = new ArrayList<String>();
				    	for(int i=0;i<listuser.size();i++){
				    		userids.add(listuser.get(i).getId());
				    	}
				    
				    	record.setUserids(userids);
				    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
						List<UserLog> list = powerServiceImpl.selectAllUserLogByIds(record);
				    
					  info = new PageInfo<UserLog>(list);
				    }
			}else if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				List<UserLog> list = powerServiceImpl.filterSelectUserLog(record);
			    info = new PageInfo<UserLog>(list);
			}else if(mu.getTag().equals(AppConstant.managertype.ZERO)||mu.getTag().equals(AppConstant.managertype.ONE)){
				User user = new User();
			    String managerid = (String)request.getSession().getAttribute("managerid");
			    user.setManagerid(managerid);
			    List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
			    Note nt = new Note();
				List<String> setList = nt.getChids(mlist, managerid);
				user.setList(setList);
			    List<User> listuser = powerServiceImpl.selectUserByManagerId(user);
			    if(listuser.size()>0){
			    	List<String> userids = new ArrayList<String>();
			    	for(int i=0;i<listuser.size();i++){
			    		userids.add(listuser.get(i).getId());
			    	}
			    	record.setUserids(userids);
			    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
					List<UserLog> list = powerServiceImpl.selectAllUserLogByIds(record);
					 info = new PageInfo<UserLog>(list);
					
			    }
			}else{
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				List<UserLog> list = powerServiceImpl.filterSelectUserLog(record);
			    info = new PageInfo<UserLog>(list);
			
			}
			}    
	 
	    if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectAllUserLogByIds", method = RequestMethod.GET) 
	public Response selectAllUserLogByIds(PageInfo page,UserLogBo record,HttpServletRequest request){
		User user = new User();
	    String managerid = (String)request.getSession().getAttribute("managerid");
	    user.setManagerid(managerid);
	    List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
	    Note nt = new Note();
		List<String> setList = nt.getChids(mlist, managerid);
		user.setList(setList);
	    List<User> listuser = powerServiceImpl.selectUserByManagerId(user);
	    if(listuser.size()>0){
	    	List<String> userids = new ArrayList<String>();
	    	for(int i=0;i<listuser.size();i++){
	    		userids.add(listuser.get(i).getId());
	    	}
	    	record.setUserids(userids);
	    	PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<UserLog> list = powerServiceImpl.selectAllUserLogByIds(record);
			PageInfo<UserLog> info = new PageInfo<UserLog>(list);
			if(info!=null){
				return new Response(ResponseStatus.Success,info,true);
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
			}
	    }else{
	    	return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	    }
		
	}
	@RequestMapping(value = "/selectManagerByPhone", method = RequestMethod.GET)
	public Response selectManagerByPhone(ManagerUser record){
		ManagerUser ma = powerServiceImpl.selectManagerByPhone(record);
		if(ma!=null){
			return new Response(ResponseStatus.Success,ma,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/*@RequestMapping(value = "/selectManagerByName", method = RequestMethod.GET)
	public Response selectManagerByName(ManagerUser record){
		ManagerUser ma = powerServiceImpl.selectManagerByName(record);
		if(ma!=null){
			return new Response(ResponseStatus.Success,ma,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}*/
	@RequestMapping(value = "/selectManagerUserByName", method = RequestMethod.GET)
	public Response selectManagerUserByName(ManagerUser record){
		List<ManagerUser> list = powerServiceImpl.selectManagerUserByName(record);
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectInfoByPa</p>
	 * <p>方法描述：管理员登录验证</p>
	 * @param record
	 * @param session
	 * @return
	 * @author liuyy
	 * @since  2016年12月16日
	 * <p> history 2016年12月16日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectInfoByPa", method = RequestMethod.POST)
	public Response selectInfoByPa(@RequestBody ManagerUser record,HttpSession session){
		ManagerUser ma = powerServiceImpl.selectInfoByPa(record);
	    if(ma!=null){
	    	session.setAttribute("managerid", ma.getId());
	    	session.setAttribute("managerUser",ma);
	    	return new Response(ResponseStatus.Success,ma,true);
	    }else{
	    	return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	    }
	}
	/**
	 * 
	 * <p>方法名称：selectInfoByManagerId</p>
	 * <p>方法描述：查询 管理员登录名</p>
	 * @param record
	 * @param session
	 * @return
	 * @author liuyy
	 * @since  2016年12月16日
	 * <p> history 2016年12月16日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectInfoByManagerId", method = RequestMethod.GET)
	public Response selectInfoByManagerId(HttpServletRequest request){
		String managerid = (String)request.getSession().getAttribute("managerid");
		ManagerUser ma =  powerServiceImpl.selectManagerUserById(managerid);
	    if(ma!=null){
	    	return new Response(ResponseStatus.Success,ma,true);
	    }else{
	    	return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	    }
	}
	/**
	 * 
	 * <p>方法名称：selectUserByManagerId</p>
	 * <p>方法描述：通过管理员id 分页查询用户</p>
	 * @param page
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年12月16日
	 * <p> history 2016年12月16日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectPageUserByManagerId", method = RequestMethod.GET)
	public Response selectUserPageByManagerId(PageInfo page,HttpServletRequest request,User user){
		 String managerid = (String)request.getSession().getAttribute("managerid");
		    user.setManagerid(managerid);
		    List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
		    Note nt = new Note();
			List<String> setList = nt.getChids(mlist, managerid);
			user.setList(setList);
	    PageHelper.startPage(page.getPageNum(), page.getPageSize());
	   
	    List<User> list = powerServiceImpl.selectUserByManagerId(user);
	    PageInfo<User> info = new PageInfo<User>(list);
	    if(info!=null){
	    	return new Response(ResponseStatus.Success,info,true);
	    }else{
	    	return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	    }
	}
	/**
	 * 
	 * <p>方法名称：selectUserByManagerId</p>
	 * <p>方法描述：通过管理员id 查询用户</p>
	 * @param page
	 * @param request
	 * @return
	 * @author liuyy
	 * @since  2016年12月16日
	 * <p> history 2016年12月16日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectUserByManagerId", method = RequestMethod.GET)
	public Response selectUserByManagerId(HttpServletRequest request){
		User user = new User();
	    String managerid = (String)request.getSession().getAttribute("managerid");
	    user.setManagerid(managerid);
	   
	    List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
	    Note nt = new Note();
		List<String> setList = nt.getChids(mlist, managerid);
		user.setList(setList);
	    List<User> list = powerServiceImpl.selectUserByManagerId(user);
	    if(list.size()>0){
	    	return new Response(ResponseStatus.Success,list,true);
	    }else{
	    	return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	    }
	}
	/**
	 * <p>
	 * 方法名称：outlogin
	 * </p>
	 * <p>
	 * 方法描述：退出登录
	 * </p>
	 * 
	 * @param request
	 * @return
	 * @author liuyy
	 * @since 2016年10月12日
	 *        <p>
	 *        history 2016年10月12日 Administrator 创建
	 *        <p>
	 */
	@RequestMapping(value = "/outlogin", method = RequestMethod.GET)
	public Response outlogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		session.removeAttribute("managerid");
		session.removeAttribute("managerUser");
		
		return new Response(ResponseStatus.Success, AppConstant.responseInfo.DELETESUCCESS, true);

	}
	@RequestMapping(value = "/updateManagerUserPassword", method = RequestMethod.POST)
	public Response updateManagerUserPassword(@RequestBody ManagerUser record){
		int num  = powerServiceImpl.updateManagerUserPass(record);
		if(num > 0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
		}
		
	}
	
	/**
	 * 
	 * <p>方法名称：selectUserList</p>
	 * <p>方法描述：查询所有用户的权限</p>
	 * @return
	 * @author liuyy
	 * @since  2016年12月19日
	 * <p> history 2016年12月19日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectUserList", method = RequestMethod.GET)
	public Response selectUserList(HttpServletRequest request,UserBo record){
		
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.THREE)|| mu.getTag().equals(AppConstant.managertype.FOUR)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				System.out.println(mu.getTag());
			}else{
				record.setManagerid(mu.getId());
			}
		}
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				record.setStartTime(TestDate.getCurrentYearFirst());
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		List<User> lists = powerServiceImpl.selectUserList(record);
		List<UserBo> listBo = new ArrayList<UserBo>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for(int j=0;j<lists.size();j++){
			UserBo ub = new UserBo();
			BeanUtils.copyProperties(lists.get(j), ub);
			if (null!=lists.get(j).getExpirydate()) {
				String timeww = formatter.format(lists.get(j).getExpirydate());
				ub.setExpirydate(timeww);
			}
			Date  end= lists.get(j).getExpirydate();
			Date  start= lists.get(j).getStartdate();
			if(start == null){
			    start = lists.get(j).getRegistertime();
				  
			}
		    if(start!=null && end!=null){
		    	Integer allExpirDay = TestDate.daysBetween(start, end);
				   ub.setAllexpirDay(allExpirDay);
		    }
		   Date now = new Date();
		   if(end!=null){
			   Integer resExpirDay = TestDate.daysBetween(now, end);
			   ub.setResexpirDay(resExpirDay);
		   }
		  listBo.add(ub);
		}
		if(listBo.size()>0){
			return new Response(ResponseStatus.Success,listBo,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectUserTotalNumber</p>
	 * <p>方法描述：合计</p>
	 * @param request
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月20日
	 * <p> history 2016年12月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectUserTotalNumber", method = RequestMethod.GET)
	public Response selectUserTotalNumber(HttpServletRequest request,UserBo record){
		UserBo list = new UserBo();
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				record.setStartTime(TestDate.getCurrentYearFirst());
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				List<ManagerUser> listm = powerServiceImpl.selectByTag();
				List<String> listmid = new ArrayList<String>();
				for(int i=0;i<listm.size();i++){
					if(null!=listm.get(i)){
						String id = listm.get(i).getId();
						listmid.add(id);
					}
					
				}
				record.setList(listmid);
				 list = powerServiceImpl.selectTotalUserNumber(record);
			}else if(mu.getTag().equals(AppConstant.managertype.ONE)|| mu.getTag().equals(AppConstant.managertype.FOUR)){
				/*List<String> listmid = new ArrayList<String>();
				List<ManagerUser> listm = powerServiceImpl.selectSonManager(mu.getId());
				for(int i=0;i<listm.size();i++){
					listmid.add(listm.get(i).getId());
				}
		         listmid.add(mu.getId());
		         record.setList(listmid);*/
				 List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
					List<String> setList = powerServiceImpl.getChids(mlist, mu.getId());
					record.setList(setList);
				list = powerServiceImpl.selectTotalUserTypeNumberByM(record);
			}else{
				record.setManagerid(mu.getId());
				List<String> listmid = new ArrayList<String>();
				listmid.add(mu.getId());
				record.setList(listmid);
				 list = powerServiceImpl.selectTotalUserNumber(record);
			}
		}
		if(list!=null){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectUserNumber</p>
	 * <p>方法描述：报表</p>
	 * @param request
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月20日
	 * <p> history 2016年12月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectUserNumber", method = RequestMethod.GET)
	public Response selectUserNumber(HttpServletRequest request,UserBo record){
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				record.setStartTime(TestDate.getCurrentYearFirst());
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		List<UserBo> list = new ArrayList<UserBo>();
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)){
				List<ManagerUser> listm = powerServiceImpl.selectByTag();
				List<String> listmid = new ArrayList<String>();
				for(int i=0;i<listm.size();i++){
					listmid.add(listm.get(i).getId());
				}
				record.setList(listmid);
				 list = powerServiceImpl.selectUserNumber(record);
			} else if(mu.getTag().equals(AppConstant.managertype.ONE)|| mu.getTag().equals(AppConstant.managertype.FOUR)){
				/*List<ManagerUser> listm = powerServiceImpl.selectSonManager(mu.getId());
				List<String> listmid = new ArrayList<String>();
				for(int i=0;i<listm.size();i++){
					listmid.add(listm.get(i).getId());
				}
		         listmid.add(mu.getId());
		         record.setList(listmid);*/
		         
		         List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
					List<String> setList = powerServiceImpl.getChids(mlist, mu.getId());
					record.setList(setList);
				 list = powerServiceImpl.selectUserTypeNumberByM(record);
			}else{
				record.setManagerid(mu.getId());
				List<String> listmid = new ArrayList<String>();
				listmid.add(mu.getId());
				record.setList(listmid);
				 list = powerServiceImpl.selectUserNumber(record);
			}
		}
		if(list.size()>0){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectDetailUserList</p>
	 * <p>方法描述：报表1</p>
	 * @param request
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月20日
	 * <p> history 2016年12月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectDetailUserList", method = RequestMethod.GET)
	public Response selectDetailUserList(HttpServletRequest request,UserBo record,PageInfo page){
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				record.setStartTime(TestDate.getCurrentYearFirst());
				
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		PageInfo<User> info = null;
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				List<User> lists = powerServiceImpl.selectUserList(record);
			    info = new PageInfo<User>(lists);
			}else if(mu.getTag().equals(AppConstant.managertype.THREE)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				List<User> lists = powerServiceImpl.selectUserAndPowerByM(record);
			    info = new PageInfo<User>(lists);
			}else{
				List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
				Note nt = new Note();
				List<String> setList = nt.getChids(mlist, mu.getId());
				
				//record.setManagerid(mu.getId());
				record.setList(setList);
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				List<User> lists = powerServiceImpl.selectUserList(record);
			    info = new PageInfo<User>(lists);
			}
		}
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/exportUserDe", method = RequestMethod.GET)
	public Response exportUserDe(UserBo record,HttpServletRequest request, HttpServletResponse response){
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.THREE)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				System.out.println(mu.getTag());
			}else{
				List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
				List<String> setList = powerServiceImpl.getChids(mlist, mu.getId());
				//record.setManagerid(mu.getId());
				record.setList(setList);
			}
		}
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				record.setStartTime(TestDate.getCurrentYearFirst());
				
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		String s = powerServiceImpl.exportUserList(request, response, record);
		return new Response(ResponseStatus.Success,s,true);
	}
	@RequestMapping(value = "/exportUserPower", method = RequestMethod.GET)
	public Response exportUserPower(UserBo record,HttpServletRequest request, HttpServletResponse response){
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.THREE)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				System.out.println(mu.getTag());
			}else{
			/*	record.setManagerid(mu.getId());*/
				List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
				Note nt = new Note();
				List<String> setList = nt.getChids(mlist, mu.getId());
				//record.setManagerid(mu.getId());
				record.setList(setList);
			}
		}
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				record.setStartTime(TestDate.getCurrentYearFirst());
				
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		String s = powerServiceImpl.exportUserPower(request, response, record);
		return new Response(ResponseStatus.Success,s,true);
	}
	/**
	 * 
	 * <p>方法名称：selectUTByMId</p>
	 * <p>方法描述：单个查询数量</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月20日
	 * <p> history 2016年12月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectUTByMId", method = RequestMethod.GET)
	public Response selectUTByMId(UserBo record,PageInfo page){
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				record.setStartTime(TestDate.getCurrentYearFirst());
				
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		 PageHelper.startPage(page.getPageNum(), page.getPageSize());
		 List<User> list = powerServiceImpl.selectUTByMId(record);
		 PageInfo<User> info = new PageInfo<User>(list);
		 if(info!=null){
			 return new Response(ResponseStatus.Success,info,true);
		 }else{
			 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		 }
	}
	/**
	 * 
	 * <p>方法名称：selectUTByMId</p>
	 * <p>方法描述：合计用户总数</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月20日
	 * <p> history 2016年12月20日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectHJUT", method = RequestMethod.GET)
	public Response selectHJUT(UserBo record,HttpServletRequest request,PageInfo page){
		String time = record.getTime();
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				record.setStartTime(TestDate.getMonthFirstDay());
				record.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
				record.setStartTime(a[0]);
				record.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.YEAR)){
				 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				record.setStartTime(TestDate.getCurrentYearFirst());
				
				record.setEndTime(TestDate.getCurrentYearEnd());
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				record.setStartTime(record.getStartTime());
				record.setEndTime(record.getEndTime());
			}
		}
		PageInfo<User> info = null;
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)|| mu.getTag().equals(AppConstant.managertype.FIVE)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				 List<User> list = powerServiceImpl.selectUTByMId(record);
			     info = new PageInfo<User>(list);
			}else if(mu.getTag().equals(AppConstant.managertype.THREE)){
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				 List<User> list = powerServiceImpl.selectUserAndPowerByM(record);
			     info = new PageInfo<User>(list);
			}else{
				//record.setManagerid(mu.getId());
				List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
				Note nt = new Note();
				List<String> setList = nt.getChids(mlist, mu.getId());
				record.setList(setList);
				PageHelper.startPage(page.getPageNum(), page.getPageSize());
				 List<User> list = powerServiceImpl.selectUTByMId(record);
			     info = new PageInfo<User>(list);
			}
		}
		 if(info!=null){
			 return new Response(ResponseStatus.Success,info,true);
		 }else{
			 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		 }
	}
	/**
	 * 
	 * <p>方法名称：insetCaiwuInfo</p>
	 * <p>方法描述：保存用户回款状态</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月26日
	 * <p> history 2016年12月26日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/insetCaiwuInfo", method = RequestMethod.GET)
	public Response insetCaiwuInfo(ReMoneyBo record,HttpServletRequest request){
			record.setId(UuidUtil.getUUID());
			record.setOperatetime(new Date());
			ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
			if(mu!=null){
				record.setOperator(mu.getName());
			}
			ReMoney rm  = new ReMoney();
			rm.setUserid(record.getUserid());
			List<ReMoney> list = userServiceImpl.selectByExpirtime(rm);
			if(list.size()>0){
				int count = userServiceImpl.updateCaiWu(record);
				if(count > 0){
					return new Response(ResponseStatus.Success,count,true);
				}else{
					return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
				}
				
			}else{
				int num = userServiceImpl.insertSelective(record);
				if(num >0){
					return new Response(ResponseStatus.Success,num,true);
				}else{
					return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
			    }
			}
			
	}
	/**
	 * 
	 * <p>方法名称：selectByExpirtime</p>
	 * <p>方法描述：查询该时间段内用户是否是回款状态</p>
	 * @param record
	 * @return
	 * @author liuyy
	 * @since  2016年12月26日
	 * <p> history 2016年12月26日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectByExpirtime", method = RequestMethod.GET)
	public Response selectByExpirtime(ReMoney record){
		List<ReMoney> list = userServiceImpl.selectByExpirtime(record);
		if(list.size()>0){
			if(list.get(0).getRemoney().equals(AppConstant.remoneytag.ZERO)){
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.PARTREMONEY,false);
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.JINZHIINSERT,false);
			}
			
		}else{
			return new Response(ResponseStatus.Success,AppConstant.responseInfo.SAVESUCCESS,true);
		}
	}
	@RequestMapping(value = "/selectUserPowerByUserid", method = RequestMethod.GET)
	public Response selectUserPowerByUserid(UserBo record){
		User user = userServiceImpl.selectUserAndPowerById(record);
		if(user!=null){
			return new Response(ResponseStatus.Success,user,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectDigui", method = RequestMethod.GET)
	public Response selectDigui(HttpServletRequest request){
		String managerid = (String)request.getSession().getAttribute("managerid");
		
		List<ManagerUser> list = powerServiceImpl.selectByParentId();
		Note nt = new Note();
		List<String> setList = nt.getChids(list, managerid);
		
		if(setList.size()>0){
			
			return new Response(ResponseStatus.Success,setList,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/insertTradePower", method = RequestMethod.GET)
	public int insertTradePower(){
		int num = userServiceImpl.insertTradePower();
		return 0;
	}
	@RequestMapping(value = "/insertwordPower", method = RequestMethod.GET)
	public int insertwordPower(){
		int num = userServiceImpl.wordsetTrade();
		return 0;
	}
	
}


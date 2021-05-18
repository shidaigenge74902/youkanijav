package com.bayside.app.opinion.war.subject.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.myuser.model.User;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.opinion.war.subject.bo.MetaSearch;
import com.bayside.app.opinion.war.subject.bo.SubJectArticleBo;
import com.bayside.app.opinion.war.subject.model.Subject;
import com.bayside.app.opinion.war.subject.model.SubjectArticle;
import com.bayside.app.opinion.war.subject.model.SubjectMArticle;
import com.bayside.app.opinion.war.subject.service.SubjectArticleService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.Solrhelper;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@RestController
public class SubjectArticleController {
  @Autowired
  private SubjectArticleService subjectArticleServiceImpl;
  @Autowired
  private UserService userServiceImpl;
  @RequestMapping(value = "/updateSubjectByUserid", method = RequestMethod.GET)
  public Response updateSubjectByUserid(String userid,String trade){
	  int num = 0;
	  if("1".equals(trade)){
		  String formats = "tv,bt,trade";
		  Subject sub = new Subject();
		  sub.setUserid(userid);
		  sub.setIstrade(formats);
		  num = subjectArticleServiceImpl.updateSubjectByUserid(sub);
	  }else{
		  String formats ="";
		 
		  Subject sub = new Subject();
		  sub.setUserid(userid);
		  sub.setIstrade(formats);
		  num = subjectArticleServiceImpl.updateSubjectByUserid(sub);
	  }
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	 
  }
  /**
   * 
   * <p>方法名称：insertArticleInfo</p>
   * <p>方法描述：添加舆情录入</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年1月17日
   * <p> history 2017年1月17日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/insertArticleInfo", method = RequestMethod.POST)
  public Response insertArticleInfo(SubJectArticleBo record){
	  Boolean num = subjectArticleServiceImpl.insertSubjectArticle(record);
	  if(num){
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.SAVESUCCESS,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	  }
  }
  /**
   * 
   * <p>方法名称：selectSubjectByUserid</p>
   * <p>方法描述：根据userid 查询专题</p>
   * @param userid
   * @return
   * @author liuyy
   * @since  2017年1月17日
   * <p> history 2017年1月17日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/selectSubjectByUserid", method = RequestMethod.GET)
  public Response selectSubjectByUserid(String userid){
	 List<Subject> list = subjectArticleServiceImpl.selectSubjectByUserid(userid);
	 if(list.size()>0){
		 return new Response(ResponseStatus.Success,list,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	 }
  }
  /**
   * 
   * <p>方法名称：alisUrl</p>
   * <p>方法描述：自动解析地址</p>
   * @param url
   * @return
   * @author liuyy
   * @since  2017年1月17日
   * <p> history 2017年1月17日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/alisUrl", method = RequestMethod.GET)
  public Response alisUrl(String url,String userid,String subjectid){
	  MetaSearch ms = subjectArticleServiceImpl.alisUrl(url,userid,subjectid);
	  if(ms!=null){
		  return new Response(ResponseStatus.Success,ms,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  /**
   * 
   * <p>方法名称：getFilterOpinion</p>
   * <p>方法描述:条件查询文章</p>
   * @param page
   * @param stage
   * @param subjectid
   * @param request
   * @param userid
   * @return
   * @throws Exception
   * @author liuyy
   * @since  2017年1月17日
   * <p> history 2017年1月17日 Administrator  创建   <p>
   */
  @RequestMapping(value = "/getFilterOpinion", method = RequestMethod.GET)
	public Response getFilterOpinion(PageInfo page, SubJectArticleBo stage,HttpServletRequest request){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
			stage.setUserid(stage.getUserid());
			if (stage.getStartTime() != null) {
				if (stage.getStartTime().equals("current")) {
					stage.setStartTime(df.format(new Date()));
				} else if (stage.getStartTime().equals("sun")) {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -7);
					String str = df.format(c.getTime());
					stage.setStartTime(str);
				} else if (stage.getStartTime().equals("month")) {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DATE, -30);
					String str = df.format(c.getTime());
					stage.setStartTime(str);
				}else if(stage.getStartTime().equals("zidingyi")){
					stage.setStartTime(stage.getSttime());
				}
			}
			List<SubJectArticleBo> list = subjectArticleServiceImpl.filterCom(stage);
			
			PageInfo<SubJectArticleBo> info = new PageInfo<SubJectArticleBo>(list);
			if(info!=null){
				return new Response(ResponseStatus.Success, info, true);
			}else{
				return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
			}
	}
  @RequestMapping(value = "/selectUserByReport", method = RequestMethod.GET)
  public Response selectUserByReport(){
	  List<User> list = userServiceImpl.selectByReport();
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value = "/updateSMArticle", method = RequestMethod.GET)
  public Response updateSMArticle(SubjectMArticle record){
	  int num  = subjectArticleServiceImpl.updateSMArticle(record);
	  if(num>0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value = "/download", method = RequestMethod.GET)
  public Response download(SubJectArticleBo stage,HttpServletRequest request,HttpServletResponse response){
	  Map<String,Object> map = subjectArticleServiceImpl.dowloadExcel(stage, response, request);
	  if((boolean) map.get("flag")){
			return new Response(ResponseStatus.Success,map.get("result"), true);	
		}else{
			return new Response(ResponseStatus.Error,map.get("result"), false);	
		}
  }
  @RequestMapping(value = "/alldownload", method = RequestMethod.GET)
  public Response alldownload(SubJectArticleBo stage,HttpServletRequest request,HttpServletResponse response){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	  if (stage.getStartTime() != null) {
			if (stage.getStartTime().equals("current")) {
				stage.setStartTime(df.format(new Date()));
			} else if (stage.getStartTime().equals("sun")) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				String str = df.format(c.getTime());
				stage.setStartTime(str);
			} else if (stage.getStartTime().equals("month")) {
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -30);
				String str = df.format(c.getTime());
				stage.setStartTime(str);
			}else if(stage.getStartTime().equals("zidingyi")){
				stage.setStartTime(stage.getSttime());
			}
		}
	  Map<String,Object> map = subjectArticleServiceImpl.alldowloadExcel(stage, response, request);
	  if((boolean) map.get("flag")){
			return new Response(ResponseStatus.Success,map.get("result"), true);	
		}else{
			return new Response(ResponseStatus.Error,map.get("result"), false);	
		}
  }
  @RequestMapping(value = "/filteralldownload", method = RequestMethod.GET)
  public Response filteralldownload(SubJectArticleBo stage,HttpServletRequest request,HttpServletResponse response){
	  Map<String,Object> map = subjectArticleServiceImpl.dowloadfilterCom(stage, response, request);
	  if((boolean) map.get("flag")){
			return new Response(ResponseStatus.Success,map.get("result"), true);	
		}else{
			return new Response(ResponseStatus.Error,map.get("result"), false);	
		}
  }
  @RequestMapping(value = "/deleteSMArticle", method = RequestMethod.GET)
  public Response deleteSMArticle(String id){
	  int num  = subjectArticleServiceImpl.deleteSMArticle(id);
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value = "/selectArticleDetailById", method = RequestMethod.GET)
	public Response selectArticleDetailById(SubJectArticleBo record) {
		SubJectArticleBo sb = subjectArticleServiceImpl.selectArticleDetailById(record.getArticleid());
		if (sb != null) {
			return new Response(ResponseStatus.Success, sb, true);
		} else {
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		}
  }
  @RequestMapping(value = "/selectArticleStatus", method = RequestMethod.GET)
  public Response selectArticleStatus(SubjectMArticle record){
	  SubjectMArticle sma = subjectArticleServiceImpl.selectSMById(record.getId());
	  if(sma!=null){
		  return new Response(ResponseStatus.Success,sma,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value = "/updateEmotion", method = RequestMethod.GET)
  public Response updateEmotion(SubjectMArticle record){
	  int num = subjectArticleServiceImpl.updateEmotion(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	  }
  }
  @RequestMapping(value = "/getSimArticle", method = RequestMethod.GET)
  public Response getSimArticle(SubJectArticleBo record){
	  List<SubJectArticleBo> list = subjectArticleServiceImpl.getSimArticle(record);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value = "/selectcheckdays", method = RequestMethod.GET)
  public Response selectcheckdays(String userid){
	  int days = subjectArticleServiceImpl.selectTotalByUserid(userid);
	  return new Response(ResponseStatus.Success,days,true);
  }
}

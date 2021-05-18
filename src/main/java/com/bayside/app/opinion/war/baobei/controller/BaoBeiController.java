package com.bayside.app.opinion.war.baobei.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.baobei.bo.BaoBeiBo;
import com.bayside.app.opinion.war.baobei.model.BaoBei;
import com.bayside.app.opinion.war.baobei.service.BaoBeiService;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class BaoBeiController {
 @Autowired
 private BaoBeiService baoBeiServiceImpl;
 @RequestMapping(value ="/insertBaoBei",method=RequestMethod.GET)
 public Response insertBaoBei(BaoBeiBo record,HttpServletRequest request){
	  String managerid = (String)request.getSession().getAttribute("managerid");
	 record.setId(UuidUtil.getUUID());
	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 record.setCreateTime(df.format(new Date()));
	 record.setManagerid(managerid);
	 int num = baoBeiServiceImpl.insertBaoBei(record);
	 if(num >0){
		 return new Response(ResponseStatus.Success,num,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	 }

 }
 @RequestMapping(value ="/updateBaoBei",method=RequestMethod.GET)
 public Response updateBaoBei(BaoBeiBo record){
	 int num = baoBeiServiceImpl.updateBaoBei(record);
	 if(num > 0){
		 return new Response(ResponseStatus.Success,num,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	 }
 }
 @RequestMapping(value ="/deleteBaoBei",method=RequestMethod.GET)
 public Response deleteBaoBei(String id){
	 int num = baoBeiServiceImpl.deleteBaoBei(id);
	 if(num > 0){
		 return new Response(ResponseStatus.Success,AppConstant.responseInfo.DELETESUCCESS,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	 }
 }
 @RequestMapping(value ="/selectPageBaoBei",method=RequestMethod.GET)
 public Response selectPageBaoBei(PageInfo page,BaoBeiBo record,HttpServletRequest request){
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.ONE)||mu.getTag().equals(AppConstant.managertype.FOUR)||mu.getTag().equals(AppConstant.managertype.ZERO)){
				record.setManagerid(mu.getId());
			}
			
		}
	 PageHelper.startPage(page.getPageNum(), page.getPageSize());
	 List<BaoBei> list = baoBeiServiceImpl.selectByOrgname(record);
	 PageInfo<BaoBei> info = new PageInfo<BaoBei>(list);
	 if(info!=null){
		 return new Response(ResponseStatus.Success,info,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	 }
 }
 /**
  * 
  * <p>方法名称：selectByTime</p>
  * <p>方法描述：机构名称是不是在有效期内重复</p>
  * @param orgname
  * @return
  * @author liuyy
  * @since  2017年2月27日
  * <p> history 2017年2月27日 Administrator  创建   <p>
  */
 @RequestMapping(value ="/selectByTime",method=RequestMethod.GET)
 public Response selectByTime(String orgname){
	 List<BaoBei> list = baoBeiServiceImpl.selectByTime(orgname);
	 return new Response(ResponseStatus.Success,list,true);
 }
 @RequestMapping(value ="/selectBaoBeiById",method=RequestMethod.GET)
 public Response selectBaoBeiById(String id){
	BaoBei bb = baoBeiServiceImpl.selectBaoBeiById(id);
	BaoBeiBo bo = new BaoBeiBo();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	BeanUtils.copyProperties(bb, bo);
	if(bb.getExpirtytime()!=null){
		bo.setExpirtytime(df.format(bb.getExpirtytime()));
	}
	if(bo !=null){
		return new Response(ResponseStatus.Success,bo,true);
	}else{
		return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	}
 }
 
}

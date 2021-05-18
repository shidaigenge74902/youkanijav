package com.bayside.app.opinion.war.customerinfo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;
import com.bayside.app.opinion.war.customerinfo.service.CustomerInfoService;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class CustomerInfoController {
	@Autowired
  private CustomerInfoService customerInfoServiceImpl;
  @RequestMapping(value ="/initselectCustomer",method=RequestMethod.GET)
  public Response initselectCustomer(CustomerInfoBo record,PageInfo page,HttpServletRequest request){
	  
	  PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  PageInfo<CustomerInfo> info = null;
	 
	  ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
	   if(mu!=null){
		   if(record.getTagstatus().equals(1)){
				  record.setManagerid(mu.getId());
				  record.setStatus(1);
			  }
		   if(mu.getTag().equals(AppConstant.managertype.ONE)){
			   if(record.getTagstatus().equals(1)){
					  record.setManagerid(mu.getId());
					  record.setStatus(1);
				  }else{
					  record.setStatus(0);
				  }
			   
			   List<CustomerInfo> list = customerInfoServiceImpl.publiccustomer(record);
			      info = new PageInfo<CustomerInfo>(list);
		   }else if(mu.getTag().equals(AppConstant.managertype.FOUR)){
			   List<CustomerInfo> list = customerInfoServiceImpl.zongjiancustomer(record);
			    info = new PageInfo<CustomerInfo>(list);
		   }
	   }
	  
	  if(info!=null){
		  return new Response(ResponseStatus.Success,info,true);
	  }else{
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  /**
   * 
   * <p>方法名称：selectAllFilter</p>
   * <p>方法描述：模糊查询</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年1月19日
   * <p> history 2017年1月19日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/selectAllFilter",method=RequestMethod.GET)
  public Response selectAllFilter(CustomerInfoBo record,PageInfo page,HttpServletRequest request){
	  ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
	   if(mu!=null){
		   if(record.getTagstatus().equals(1)){
				  record.setManagerid(mu.getId());
				  record.setStatus(1);
			  }
	   }
	  PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  List<CustomerInfo> list = customerInfoServiceImpl.selectAllFilter(record);
	  PageInfo<CustomerInfo> info = new PageInfo<CustomerInfo>(list);
	  if(info!=null){
		  return new Response(ResponseStatus.Success,info,true);
	  }else{
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  /**
   * 
   * <p>方法名称：selectAllFilter</p>
   * <p>方法描述：个人区域模糊查询</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年1月19日
   * <p> history 2017年1月19日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/selectPersonAllFilter",method=RequestMethod.GET)
  public Response selectPersonAllFilter(CustomerInfoBo record,PageInfo page,HttpServletRequest request){
	  ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
	   if(mu!=null){
		   if(!mu.getTag().equals(AppConstant.managertype.TWO)){
			   record.setManagerid(mu.getId());
		   }
	   }
	  PageHelper.startPage(page.getPageNum(), page.getPageSize());
	  List<CustomerInfo> list = customerInfoServiceImpl.selectAllFilter(record);
	  PageInfo<CustomerInfo> info = new PageInfo<CustomerInfo>(list);
	  if(info!=null){
		  return new Response(ResponseStatus.Success,info,true);
	  }else{
		  return new Response(ResponseStatus.Success,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  /**
   * 
   * <p>方法名称：insertCustomerInfo</p>
   * <p>方法描述：添加客户信息</p>
   * @param record
   * @param request
   * @return
   * @author liuyy
   * @since  2017年1月19日
   * <p> history 2017年1月19日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/insertCustomerInfo",method=RequestMethod.GET)
  public Response insertCustomerInfo(CustomerInfoBo record,HttpServletRequest request){
	  record.setId(UuidUtil.getUUID());
	  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	  ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
	  record.setOperator(mu.getName());
	  record.setOperatortime(df.format(new Date()));
	 int num = customerInfoServiceImpl.insertCustomerInfo(record);
	 if(num >0){
		 return new Response(ResponseStatus.Success,num,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	 }
  }
  
  /**
   * 
   * <p>方法名称：updateCustomerInfoB</p>
   * <p>方法描述：修改客户信息</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年1月19日
   * <p> history 2017年1月19日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/updateCustomerInfoB",method=RequestMethod.GET)
  public Response updateCustomerInfoB(CustomerInfoBo record){
	  record.setRenlingDate(new Date());
	 int num = customerInfoServiceImpl.updateCustomerInfoB(record);
	 if(num >0){
		 return new Response(ResponseStatus.Success,num,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	 }
  }
  /**
   * 
   * <p>方法名称：selectCustomerById</p>
   * <p>方法描述：通过id 查询详细客户信息</p>
   * @param id
   * @return
   * @author liuyy
   * @since  2017年1月19日
   * <p> history 2017年1月19日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/selectCustomerById",method=RequestMethod.GET)
  public Response selectCustomerById(String id){
	  CustomerInfo customer = customerInfoServiceImpl.selectCustomerById(id);
	  if(customer!=null){
		  return new Response(ResponseStatus.Success,customer,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  /**
   * 
   * <p>方法名称：selectManagerByTag</p>
   * <p>方法描述：通过角色查询管理者</p>
   * @return
   * @author liuyy
   * @since  2017年1月20日
   * <p> history 2017年1月20日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/selectManagerByTag",method=RequestMethod.GET)
  public Response selectManagerByTag(){
	  ManagerUser record = new ManagerUser();
	  record.setTag(1);
	 List<ManagerUser> list = customerInfoServiceImpl.selectManagerByTag(record);
	 if(list.size()>0){
		 return new Response(ResponseStatus.Success,list,true);
	 }else{
		 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	 }
  }
  /**
   * 
   * <p>方法名称：updateCustomerPublic</p>
   * <p>方法描述：修改客户信息为公开状态</p>
   * @param record
   * @return
   * @author liuyy
   * @since  2017年1月20日
   * <p> history 2017年1月20日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/updateCustomerPublic",method=RequestMethod.GET)
  public Response updateCustomerPublic(CustomerInfo record){
	  int num = customerInfoServiceImpl.updateCustomInfoPublic(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	  }
  }
  /**  
   * 描述：通过传统方式form表单提交方式导入excel文件  
   * @param request  
   * @throws Exception  
   */  
  @RequestMapping(value="uploadExcel",method={RequestMethod.GET,RequestMethod.POST},produces ="text/html;charset=UTF-8")  
  public String  uploadExcel(HttpServletRequest request,HttpServletResponse response){  
	  MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	  ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
	  String result = customerInfoServiceImpl.alisexcel(multipartRequest, mu);  
      return result;
     
   } 
  /**
   * \
   * <p>方法名称：selectByManagerUser</p>
   * <p>方法描述：查询用户的认领客户</p>
   * @param request
   * @return
   * @author liuyy
   * @since  2017年2月9日
   * <p> history 2017年2月9日 Administrator  创建   <p>
   */
  @RequestMapping(value ="/selectByManagerUser",method=RequestMethod.GET)
   public Response selectByManagerUser(HttpServletRequest request){
	  ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
	  String managerid = (String)request.getSession().getAttribute("managerid");
	   CustomerInfoBo cb = new CustomerInfoBo();
	  if(mu.getTag().equals(AppConstant.managertype.TWO)){
		  cb.setManagerid("");  
	  }else{
		  cb.setManagerid(mu.getId());
	  }
	   cb.setStatus(1);
	  
	   List<CustomerInfo> list = customerInfoServiceImpl.selectByManagerUser(cb);
	   return new Response(ResponseStatus.Success,list,true);
   }
  
}

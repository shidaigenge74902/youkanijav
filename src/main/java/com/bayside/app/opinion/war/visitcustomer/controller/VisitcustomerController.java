package com.bayside.app.opinion.war.visitcustomer.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;
import com.bayside.app.opinion.war.customerinfo.service.CustomerInfoService;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.model.ReMoney;
import com.bayside.app.opinion.war.power.service.PowerUserService;
import com.bayside.app.opinion.war.visitcustomer.bo.CvcustomerBo;
import com.bayside.app.opinion.war.visitcustomer.bo.VisitCustomerBo;
import com.bayside.app.opinion.war.visitcustomer.model.VisitCustomer;
import com.bayside.app.opinion.war.visitcustomer.service.VisitCustomerService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DateFormatUtil;
import com.bayside.app.util.DateTypeUtil;
import com.bayside.app.util.Note;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.TestDate;
import com.bayside.app.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class VisitcustomerController {
	@Autowired
   private VisitCustomerService visitCustomerServiceImpl;
	@Autowired
   private CustomerInfoService customerInfoServiceImpl;
	@Autowired
	private PowerUserService powerServiceImpl;
	@RequestMapping(value = "/insertVisitCustomer", method = RequestMethod.GET)
	private Response insertVisitCustomer(VisitCustomerBo record){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		record.setId(UuidUtil.getUUID());
		record.setOperatorTime(df.format(new Date()));
		int num = visitCustomerServiceImpl.insertVisitCustomer(record);
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
		}
	}
	@RequestMapping(value = "/selectAllVisitCustomer", method = RequestMethod.GET)
	public Response selectAllVisitCustomer(VisitCustomer record,PageInfo page,HttpServletRequest request){
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.TWO)){
				
			}else if(mu.getTag().equals(AppConstant.managertype.FOUR) || mu.getTag().equals(AppConstant.managertype.ONE)){
				List<ManagerUser> mlist = powerServiceImpl.selectByParentId();
				Note nt = new Note();
				List<String> setList = nt.getChids(mlist, mu.getId());
				record.setList(setList);
			}
		}
		List<VisitCustomer> list = visitCustomerServiceImpl.selectAllVisitCustomer(record);
		PageInfo<VisitCustomer> info = new PageInfo<VisitCustomer>(list);
		if(info!=null){
			return new Response(ResponseStatus.Success,info,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/updateVisitCustomer", method = RequestMethod.GET)
	public Response updateVisitCustomer(VisitCustomerBo record){
		int num = visitCustomerServiceImpl.updateVisitCustomer(record);
		if(num >0){
			return new Response(ResponseStatus.Success,num,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectVisitCustomer", method = RequestMethod.GET)
	public Response selectVisitCustomer(String id){
		VisitCustomer vc = visitCustomerServiceImpl.selectVisitCustomerById(id);
		if(vc!=null){
			return new Response(ResponseStatus.Success,vc,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	/**
	 * 
	 * <p>方法名称：selectTodayStatus</p>
	 * <p>方法描述：今日客户分析</p>
	 * @return
	 * @author liuyy
	 * @since  2017年3月1日
	 * <p> history 2017年3月1日 Administrator  创建   <p>
	 */
	@RequestMapping(value = "/selectTodayStatus", method = RequestMethod.GET)
	public Response selectTodayStatus(HttpServletRequest request){
		VisitCustomer record = new VisitCustomer();
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.ONE)){
				record.setManagerid(mu.getId());
			}
			
		}
		
		VisitCustomer vc = visitCustomerServiceImpl.selectTodayStatusNumber(record);
		VisitCustomer toc = visitCustomerServiceImpl.selectTodayJihua(record);
		if(null!=toc){
			vc.setAddNum(toc.getAddNum());
		}
		if(vc!=null){
			return new Response(ResponseStatus.Success,vc,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/selectStatusByTime", method = RequestMethod.GET)
	public Response selectStatusByTime(VisitCustomerBo record,HttpServletRequest request){
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.ONE)){
				record.setManagerid(mu.getId());
			}
			
		}
		
     List<CvcustomerBo> cvulist = new ArrayList<CvcustomerBo>();
    cvulist = visitCustomerServiceImpl.selectCvcustomer(record.getTime(), record.getStartTime(), record.getEndTime());
     if(cvulist.size()>0){
    	 return new Response(ResponseStatus.Success,cvulist,true);
     }else{
    	 return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
     }
	
	}
	@RequestMapping(value = "/alisAddress", method = RequestMethod.GET)
	public Response alisAddress(HttpServletRequest request){
		
		CustomerInfoBo cb = new CustomerInfoBo();
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.ONE)){
				cb.setManagerid(mu.getId());
			}
			
		}
		List<CustomerInfo> list = visitCustomerServiceImpl.selectAddress(cb);
		if(list!=null){
			return new Response(ResponseStatus.Success,list,true);
		}else{
			return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
	}
	@RequestMapping(value = "/customerType", method = RequestMethod.GET)
	public Response customerType(HttpServletRequest request){
		CustomerInfoBo cb = new CustomerInfoBo();
		ManagerUser mu = (ManagerUser)request.getSession().getAttribute("managerUser");
		if(mu!=null){
			if(mu.getTag().equals(AppConstant.managertype.ONE)){
				cb.setManagerid(mu.getId());
			}
			
		}
		//CustomerInfo ci = visitCustomerServiceImpl.succCustomerType(cb);
		ReMoney re = visitCustomerServiceImpl.succAddress();
		CustomerInfo info = visitCustomerServiceImpl.selectCustomerType(cb);
		
		CustomerInfoBo cub = new CustomerInfoBo();
		if(re!=null){
			cub.setSuccacount(re.getIdNum());
		}
		if(info!=null){
			cub.setPuacount(info.getPuacount());
			cub.setSpacount(info.getSpacount());
		}
	   if(cub!=null){
		   return new Response(ResponseStatus.Success,cub,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	   }
		
	}
	
}

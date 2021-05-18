package com.bayside.app.opinion.war.visitcustomer.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.dao.CustomerInfoMapper;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;
import com.bayside.app.opinion.war.customerinfo.service.CustomerInfoService;
import com.bayside.app.opinion.war.myuser.dao.ReMoneyMapper;
import com.bayside.app.opinion.war.myuser.model.ReMoney;
import com.bayside.app.opinion.war.visitcustomer.bo.CvcustomerBo;
import com.bayside.app.opinion.war.visitcustomer.bo.VisitCustomerBo;
import com.bayside.app.opinion.war.visitcustomer.dao.VisitCustomerMapper;
import com.bayside.app.opinion.war.visitcustomer.model.VisitCustomer;
import com.bayside.app.opinion.war.visitcustomer.service.VisitCustomerService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.DateTypeUtil;
import com.bayside.app.util.TestDate;
import com.bayside.app.util.UuidUtil;
@Service("visitCustomerServiceImpl")
public class VisitCustomerServiceImpl implements VisitCustomerService {
    @Autowired
	private VisitCustomerMapper visitCustomerMapper;
    @Autowired
    private CustomerInfoMapper customerInfoMapper;
    @Autowired
    private CustomerInfoService customerInfoServiceImpl;
    @Autowired
    private ReMoneyMapper reMoneyMapper;
	@Override
	public int insertVisitCustomer(VisitCustomerBo record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.insertSelective(record);
	}
	@Override
	public List<VisitCustomer> selectAllVisitCustomer(VisitCustomer record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.selectAllVisitCustomer(record);
	}

	@Override
	public int updateVisitCustomer(VisitCustomerBo record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public VisitCustomer selectVisitCustomerById(String id) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.selectByPrimaryKey(id);
	}

	@Override
	public VisitCustomer selectTodayStatusNumber(VisitCustomer record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.selectTodayStatusNumber(record);
	}
	@Override
	public List<VisitCustomer> selectVisByTime(VisitCustomerBo record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.selectVisByTime(record);
	}
	@Override
	public List<VisitCustomer> selectVisCountByTime(VisitCustomerBo record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.selectVisCountByTime(record);
	}
	@Override
	public List<CvcustomerBo> selectCvcustomer(String time, String startTime, String endTime) {
		// TODO Auto-generated method stub
		CustomerInfoBo cinfo = new CustomerInfoBo();
		VisitCustomerBo vinfo = new VisitCustomerBo();
		ReMoney rm = new ReMoney();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if(time!=null && !"".equals(time)){
			if(time.equals(AppConstant.timetype.MONTH)){
				cinfo.setStartTime(TestDate.getMonthFirstDay());
				vinfo.setStartTime(TestDate.getMonthFirstDay());
				cinfo.setEndTime(TestDate.getMonthEndDay());
				vinfo.setEndTime(TestDate.getMonthEndDay());
				rm.setStartTime(TestDate.getMonthFirstDay());
				rm.setEndTime(TestDate.getMonthEndDay());
			}else if(time.equals(AppConstant.timetype.JIDU)){
				 //获得当前时间的月份，月份从0开始所以结果要加1
		        Calendar calendar=Calendar.getInstance();
		        int month=calendar.get(Calendar.MONTH)+1;
		        String[] a = TestDate.getThisSeasonTime(month).split(";");
		        
		        cinfo.setStartTime(a[0]);
		        vinfo.setStartTime(a[0]);
		       cinfo.setEndTime(a[1]);
		       vinfo.setEndTime(a[1]);
			   rm.setStartTime(a[0]);
			   rm.setEndTime(a[1]);
			}else if(time.equals(AppConstant.timetype.WEEK)){
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -7);
				String str = df.format(c.getTime());
				cinfo.setStartTime(str);
				vinfo.setStartTime(str);
				cinfo.setEndTime(df.format(new Date()));
				vinfo.setEndTime(df.format(new Date()));
				rm.setStartTime(str);
				rm.setEndTime(df.format(new Date()));
			}else if(time.equals(AppConstant.timetype.ZIDINGYI)){
				cinfo.setStartTime(startTime);
				vinfo.setStartTime(startTime);
				cinfo.setEndTime(endTime);
				vinfo.setEndTime(endTime);
				rm.setStartTime(startTime);
				rm.setEndTime(endTime);
			}
		}
       List<CustomerInfo> clist = customerInfoServiceImpl.selectCustomerByTime(cinfo);//增加客户数量   拜访数量
       System.out.println(clist);
       List<VisitCustomer> vlist = this.selectVisByTime(vinfo);//未执行 拜访shuliang
       System.out.println(vlist);
       List<VisitCustomer> allvlist = this.selectVisCountByTime(vinfo);//计划拜访
       List<ReMoney> remlist = reMoneyMapper.selectPayUser(rm);
       
       
      List<CustomerInfoBo> cboList = new ArrayList<CustomerInfoBo>();
      List<VisitCustomerBo> vbolist = new ArrayList<VisitCustomerBo>();
      List<VisitCustomerBo> allBolist = new ArrayList<VisitCustomerBo>();
      List<ReMoney> remoneylist = new ArrayList<ReMoney>();
      List<String> dateList = DateTypeUtil.getDateList(vinfo.getStartTime(),vinfo.getEndTime(), 15);
     for (String string : dateList) {
			VisitCustomerBo sBo = new VisitCustomerBo();
			sBo.setVisitstarttime(string);
			sBo.setNoacount(0);
			sBo.setYacount(0);
			sBo.setJacount(0);
			for (VisitCustomer visitCustomer : vlist) {
				String pubdate = DateTypeUtil.dateFormatString(visitCustomer.getVisitstarttime());
				if (string.equals(pubdate)) {
						BeanUtils.copyProperties(visitCustomer, sBo);
						sBo.setVisitstarttime(pubdate);
						break;
				}
			}
			vbolist.add(sBo);
		}
     
     for (String string : dateList) {
    	    ReMoney rmo = new ReMoney();
    	    rmo.setTime(string);
    	    rmo.setYacount(0);
    	   for(ReMoney remoney : remlist){
    		   String pubdate = DateTypeUtil.dateFormatString(remoney.getOperatetime());
				if (string.equals(pubdate)) {
						BeanUtils.copyProperties(remoney, rmo);
						rmo.setTime(pubdate);
						rmo.setYacount(remoney.getYacount());
						break;
				}
    	   }
			
    	   remoneylist.add(rmo);
		}
     for (String string : dateList) {
			VisitCustomerBo sBo = new VisitCustomerBo();
			sBo.setVisitstarttime(string);
			sBo.setAddNum(0);
	
			for (VisitCustomer visitCustomer : allvlist) {
				String pubdate = DateTypeUtil.dateFormatString(visitCustomer.getVisitstarttime());
				if (string.equals(pubdate)) {
						BeanUtils.copyProperties(visitCustomer, sBo);
						sBo.setVisitstarttime(pubdate);
						break;
				}
			}
			allBolist.add(sBo);
		}
       //
     for (String string : dateList) {
    	 CustomerInfoBo sBo = new CustomerInfoBo();
			sBo.setOperatortime(string);
			sBo.setIdNum(0);
			sBo.setSuccacount(0);
			for (CustomerInfo customerInfo : clist) {
				String pubdate = DateTypeUtil.dateFormatString(customerInfo.getOperatortime());
				if (string.equals(pubdate)) {
						BeanUtils.copyProperties(customerInfo, sBo);
						sBo.setOperatortime(pubdate);
						break;
				}
			}
			cboList.add(sBo);
		}
     List<VisitCustomerBo> zhlist = new ArrayList<VisitCustomerBo>();
     for(int i=0;i<vbolist.size();i++){
    	 VisitCustomerBo vcb = new VisitCustomerBo();
    	 vcb.setNoacount(vbolist.get(i).getNoacount());
    	 vcb.setYacount(vbolist.get(i).getYacount());
    	 vcb.setJacount(vbolist.get(i).getJacount());
    	 vcb.setAddNum(allBolist.get(i).getAddNum());
    	 vcb.setVisitstarttime(vbolist.get(i).getVisitstarttime());
    	 zhlist.add(vcb);
     }
     List<CvcustomerBo> cvulist = new ArrayList<CvcustomerBo>();
     for(int i=0;i<cboList.size();i++){
    	 CvcustomerBo cvbo = new CvcustomerBo();
    	 cvbo.setId(UuidUtil.getUUID());
    	 cvbo.setAddNum(zhlist.get(i).getJacount());
    	 cvbo.setIdNum(cboList.get(i).getIdNum());
    	 cvbo.setSuccacount(remoneylist.get(i).getYacount());
    	 cvbo.setNoacount(zhlist.get(i).getNoacount());
    	 cvbo.setYacount(zhlist.get(i).getYacount());
    	 cvbo.setOperatorTime(dateList.get(i));
    	 cvulist.add(cvbo);
     }
		
		return cvulist;
	}
	@Override
	public VisitCustomer selectTodayJihua(VisitCustomer record) {
		// TODO Auto-generated method stub
		return visitCustomerMapper.selectTodayJihua(record);
	}
	@Override
	public List<CustomerInfo> selectAddress(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.selectAddress(record);
	}
	@Override
	public CustomerInfo succCustomerType(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.succCustomerType(record);
	}
	@Override
	public CustomerInfo selectCustomerType(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.selectCustomerType(record);
	}
	@Override
	public ReMoney succAddress() {
		// TODO Auto-generated method stub
		return reMoneyMapper.succAddress();
	}

	

}

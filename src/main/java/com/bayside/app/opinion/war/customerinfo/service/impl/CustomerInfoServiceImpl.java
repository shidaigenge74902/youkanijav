package com.bayside.app.opinion.war.customerinfo.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.dao.CustomerInfoMapper;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;
import com.bayside.app.opinion.war.customerinfo.service.CustomerInfoService;
import com.bayside.app.opinion.war.myuser.dao.ManagerUserMapper;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.subject.service.impl.SubjectArticleServiceImpl;
import com.bayside.app.util.ImportExcelUtil;
import com.bayside.app.util.UuidUtil;
@Service("customerInfoServiceImpl")
public class CustomerInfoServiceImpl implements CustomerInfoService {
	 @Autowired
     private CustomerInfoMapper customerInfoMapper;
	 @Autowired
	 private ManagerUserMapper managerUserMapper;
	 private static Logger log = LoggerFactory.getLogger(SubjectArticleServiceImpl.class);
	@Override
	public List<CustomerInfo> selectAllFilter(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.selectAllFilter(record);
	}
	@Override
	public int insertCustomerInfo(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.insertSelective(record);
	}
	@Override
	public CustomerInfo selectCustomerById(String id) {
		// TODO Auto-generated method stub
		return customerInfoMapper.selectByPrimaryKey(id);
	}
	@Override
	public int updateCustomerInfoB(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<ManagerUser> selectManagerByTag(ManagerUser record) {
		// TODO Auto-generated method stub
		return managerUserMapper.selectByTag(record);
	}
	@Override
	public int updateCustomInfoPublic(CustomerInfo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.updateCustomInfoPublic(record);
	}
	@Override
	public List<CustomerInfo> zongjiancustomer(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.zongjiancustomer(record);
	}
	@Override
	public List<CustomerInfo> publiccustomer(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.publiccustomer(record);
	}
	@Override
	public String alisexcel(MultipartHttpServletRequest multipartRequest,ManagerUser mu) {
		 List<List<Object>> listob = new ArrayList<List<Object>>();  
	      InputStream in =null;  
	      MultipartFile file = multipartRequest.getFile("upfile");  
	      if(file.isEmpty()){  
	          try {
				
				System.out.println("文件不存在！");
			  } catch (Exception e) {
				// TODO Auto-generated catch block
				  log.error(e.getMessage(),e);
			 }  
	      }  
	      try {
			in = file.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			log.error(e.getMessage(),e);
		}  
	      //http://blog.csdn.net/onepersontz/article/details/49891405
	     
	      try {
			listob = ImportExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);
		}  
	      try {
	    	  if(null!=in){
	    		  in.close(); 
	    	  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);
		}  
	      String result="导入成功";
	      for (int i = 0; i < listob.size(); i++) {  
	          List<Object> lo = listob.get(i);  
	          CustomerInfoBo cus = new CustomerInfoBo();
	          cus.setId(UuidUtil.getUUID());
	          int j = i+1;
	          if(lo.get(0) == null){
	        	  result = "第"+j+"条信息公司名称为空,请完善信息后导入";
	        	  break;
	          }else{
	        	  cus.setCompanyname(String.valueOf(lo.get(0)));
	          }
	         if(lo.get(1) == null){
	        	  result = "第"+j+"条信息联系人为空,请完善信息后导入";
	        	  break;
	         }else{
	        	 cus.setContactuser(String.valueOf(lo.get(1)));
	         }
	         if(lo.get(2) == null){
	        	  result = "第"+j+"条信息职务为空,请完善信息后导入";
	        	  break;
	         }else{
	        	   cus.setZhiwu(String.valueOf(lo.get(2)));
	         }
	         if(lo.get(3) == null){
	        	  result = "第"+j+"条信息办公电话为空,请完善信息后导入";
	        	  break;
	         }else{
	        	 cus.setCompanyphone(String.valueOf(lo.get(3)));
	         }
	         if(lo.get(4) == null){
	        	  result = "第"+j+"条信息公司地址为空,请完善信息后导入";
	        	  break;
	         }else{
	        	 cus.setCompanyaddress(String.valueOf(lo.get(4)));
	         }
	         
	          cus.setPersonmobilephone(String.valueOf(lo.get(5)));
	          if(lo.get(6) == null){
	        	  result = "第"+j+"条信息项目类型为空,请完善信息后导入";
	        	  break;
	         }else{
		          cus.setProjecttype(String.valueOf(lo.get(6)));
	         }

	          cus.setProvince(String.valueOf(lo.get(7)));
	          cus.setCity(String.valueOf(lo.get(8)));
	          cus.setEmail(String.valueOf(lo.get(9)));
	          cus.setFax(String.valueOf(lo.get(10)));
	     	
	   /*      if(lo.get(11)!=null){
	        
	            try {
	            	sdf.parse(String.valueOf(lo.get(11)));
	            	 cus.setVisitStartTime(String.valueOf(lo.get(11)));
				} catch (Exception e) {
					// TODO: handle exception
					 result = "第"+j+"条信息拜访开始时间不是日期格式,请完善信息后导入";
					
					 break;
				}
	          
	        	 
	          }else{
	        	  result = "第"+j+"条信息拜访开始时间为空,请完善信息后导入";
	        	  break;
	          }
	         
	          if(lo.get(12)!=null){
	        	  try {
		            	sdf.parse(String.valueOf(lo.get(12)));
		            	 cus.setVisitEndTime(String.valueOf(lo.get(12)));
					} catch (Exception e) {
						// TODO: handle exception
						 result = "第"+j+"条信息拜访结束时间不是日期格式,请完善信息后导入";
						 
						 break;
					}
		        	 
	        	 
	          }else{
	        	  result = "第"+j+"条信息拜访结束时间为空,请完善信息后导入";
	        	  break;
	          }*/
	         
	        //  cus.setVisitStartTime(String.valueOf(lo.get(11)));
	         // cus.setVisitEndTime(String.valueOf(lo.get(12)));
	          SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	          cus.setStatus(Integer.valueOf(String.valueOf(lo.get(11))));
	          cus.setIncluduser(String.valueOf(lo.get(12)));
	          cus.setIncludmobilephone(String.valueOf(lo.get(13)));
	          cus.setIncludphone(String.valueOf(lo.get(14)));
	          cus.setDesicuser(String.valueOf(lo.get(15)));
	          cus.setDesimobilephone(String.valueOf(lo.get(16)));
	          cus.setDesiphone(String.valueOf(lo.get(17)));
	          cus.setBeizhu(String.valueOf(lo.get(18)));
	          cus.setOperator(mu.getName());
	    	  cus.setOperatortime(ndf.format(new Date()));
	          int num = customerInfoMapper.insertSelective(cus);
	      }  
		return result;
	}
	@Override
	public List<CustomerInfo> selectByManagerUser(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.selectByManagerUser(record);
	}
	@Override
	public List<CustomerInfo> selectCustomerByTime(CustomerInfoBo record) {
		// TODO Auto-generated method stub
		return customerInfoMapper.selectCustomerByTime(record);
	}
	
  
	

}

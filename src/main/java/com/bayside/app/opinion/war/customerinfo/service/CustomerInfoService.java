package com.bayside.app.opinion.war.customerinfo.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;
import com.bayside.app.opinion.war.myuser.model.ManagerUser;

public interface CustomerInfoService {
	List<CustomerInfo> selectAllFilter(CustomerInfoBo record);
	int insertCustomerInfo(CustomerInfoBo record);
	CustomerInfo selectCustomerById(String id);
	int updateCustomerInfoB(CustomerInfoBo record);
	List<ManagerUser> selectManagerByTag(ManagerUser record);
	int updateCustomInfoPublic(CustomerInfo record);
	
	List<CustomerInfo> zongjiancustomer(CustomerInfoBo record);
	List<CustomerInfo> publiccustomer(CustomerInfoBo record);
	String alisexcel(MultipartHttpServletRequest multipartRequest,ManagerUser mu);
	List<CustomerInfo> selectByManagerUser(CustomerInfoBo record);
	List<CustomerInfo> selectCustomerByTime(CustomerInfoBo record);
	
}

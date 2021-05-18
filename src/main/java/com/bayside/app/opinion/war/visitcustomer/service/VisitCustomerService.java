package com.bayside.app.opinion.war.visitcustomer.service;

import java.util.List;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;
import com.bayside.app.opinion.war.myuser.model.ReMoney;
import com.bayside.app.opinion.war.visitcustomer.bo.CvcustomerBo;
import com.bayside.app.opinion.war.visitcustomer.bo.VisitCustomerBo;
import com.bayside.app.opinion.war.visitcustomer.model.VisitCustomer;

public interface VisitCustomerService {
  int insertVisitCustomer(VisitCustomerBo record);
  List<VisitCustomer> selectAllVisitCustomer(VisitCustomer record);
  int updateVisitCustomer(VisitCustomerBo record);
  VisitCustomer selectVisitCustomerById(String id);
  VisitCustomer selectTodayStatusNumber(VisitCustomer record);
  List<VisitCustomer> selectVisByTime(VisitCustomerBo record);
  List<VisitCustomer> selectVisCountByTime(VisitCustomerBo record);
  List<CvcustomerBo> selectCvcustomer(String time,String startTime,String endTime);
  VisitCustomer selectTodayJihua(VisitCustomer record);
  List<CustomerInfo> selectAddress(CustomerInfoBo record);
  CustomerInfo succCustomerType(CustomerInfoBo record);
  CustomerInfo selectCustomerType(CustomerInfoBo record);
  ReMoney succAddress();
 
}

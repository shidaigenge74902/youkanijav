package com.bayside.app.opinion.war.customerinfo.dao;

import java.util.List;

import com.bayside.app.opinion.war.customerinfo.bo.CustomerInfoBo;
import com.bayside.app.opinion.war.customerinfo.model.CustomerInfo;

public interface CustomerInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerInfo record);

    int insertSelective(CustomerInfoBo record);

    CustomerInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerInfoBo record);

    int updateByPrimaryKey(CustomerInfo record);
    //模糊查询
    List<CustomerInfo> selectAllFilter(CustomerInfoBo record);
    int updateCustomInfoPublic(CustomerInfo record);
    List<CustomerInfo> zongjiancustomer(CustomerInfoBo record);
    List<CustomerInfo> publiccustomer(CustomerInfoBo record);
    //个人领域
    List<CustomerInfo> personcustomer(String managerid);
    List<CustomerInfo> selectByManagerUser(CustomerInfoBo record);
    List<CustomerInfo> selectCustomerByTime(CustomerInfoBo record);
    List<CustomerInfo> selectAddress(CustomerInfoBo record);
    CustomerInfo succCustomerType(CustomerInfoBo record);
    CustomerInfo selectCustomerType(CustomerInfoBo record);
    
    
}
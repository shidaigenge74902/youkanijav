package com.bayside.app.opinion.war.visitcustomer.dao;

import java.util.List;

import com.bayside.app.opinion.war.visitcustomer.bo.VisitCustomerBo;
import com.bayside.app.opinion.war.visitcustomer.model.VisitCustomer;

public interface VisitCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(VisitCustomer record);

    int insertSelective(VisitCustomerBo record);

    VisitCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(VisitCustomerBo record);

    int updateByPrimaryKey(VisitCustomer record);
    List<VisitCustomer> selectAllVisitCustomer(VisitCustomer record);
    VisitCustomer selectTodayStatusNumber(VisitCustomer record);
    List<VisitCustomer> selectVisByTime(VisitCustomerBo record);
    List<VisitCustomer> selectVisCountByTime(VisitCustomerBo record);
    VisitCustomer selectTodayJihua(VisitCustomer record);
    
}
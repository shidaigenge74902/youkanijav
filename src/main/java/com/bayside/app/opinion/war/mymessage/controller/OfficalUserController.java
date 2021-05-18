package com.bayside.app.opinion.war.mymessage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.bayside.app.opinion.war.mymessage.bo.OfficalUserBo;
import com.bayside.app.opinion.war.mymessage.model.OfficalUser;
import com.bayside.app.opinion.war.mymessage.service.OfficalUserService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class OfficalUserController {
	@Autowired
    private OfficalUserService officalUserServiceImpl;
	@RequestMapping(value ="/selectAllOffical",method=RequestMethod.GET)
    public Response selectAllOffical(OfficalUserBo record,PageInfo page){
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<OfficalUser> list = officalUserServiceImpl.selectAllOffical(record);
		PageInfo<OfficalUser> info = new PageInfo<OfficalUser>(list);
		if(info!=null){
			   return new Response(ResponseStatus.Success,info,true);
		 }else{
			   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
		}
     }
	
}

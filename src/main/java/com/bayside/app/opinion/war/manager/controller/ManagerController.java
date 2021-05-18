package com.bayside.app.opinion.war.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.myuser.model.ManagerUser;
import com.bayside.app.opinion.war.myuser.service.UserService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
@RestController
public class ManagerController {
  @Autowired
  private UserService userServiceImpl;
  @RequestMapping(value ="/selectManagerByName",method=RequestMethod.GET)
  public Response selectManagerByName(String name){
	  List<ManagerUser> list = userServiceImpl.selectManagerByNick(name);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list.size(),true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
	
  }
  @RequestMapping(value ="/checkAllManager",method=RequestMethod.GET)
  public Response checkAllManager(){
	  List<ManagerUser> list = userServiceImpl.selectManager();
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
}

package com.bayside.app.opinion.war.weidu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.keyword.service.NegativeWordService;
import com.bayside.app.opinion.war.weidu.model.Weidu;
import com.bayside.app.opinion.war.weidu.service.WeiDuService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;
@RestController
public class WeiduController {
	@Autowired
   private WeiDuService weiduServiceImpl;
	@Autowired
	  private NegativeWordService negativeServiceImpl;
  @RequestMapping(value = "/insertWeidu", method = RequestMethod.GET)
   public Response insertWeidu(Weidu record){
	   record.setId(UuidUtil.getUUID());
	   int num = weiduServiceImpl.insertWeidu(record);
	   if(num >0){
		   return new Response(ResponseStatus.Success,num,true);
	   }else{
		   return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	   }
   }
  @RequestMapping(value = "/deleteWeidu", method = RequestMethod.GET)
  public Response deleteWeidu(String id){
	  int num = weiduServiceImpl.deleteWeidu(id);
	  int n = negativeServiceImpl.deleteWordByWeiduId(id);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	  }
  }
  @RequestMapping(value = "/selectAllWeidu", method = RequestMethod.GET)
  public Response selectAllWeidu(String tradeid){
	  List<Weidu> list = weiduServiceImpl.selectByTradeId(tradeid);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value = "/updateWeidu", method = RequestMethod.GET)
  public Response updateWeidu(Weidu record){
	  int num = weiduServiceImpl.updateWeidu(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,true);
	  }
  }
  @RequestMapping(value="/selectWeiduByID",method = RequestMethod.GET)
  public Response selectWeiduByID(String id){
	  Weidu weidu = weiduServiceImpl.selectByWeiduId(id);
	  if(weidu!=null){
		  return new Response(ResponseStatus.Success,weidu,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  
	  
  

}

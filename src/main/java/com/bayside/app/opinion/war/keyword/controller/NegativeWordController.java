package com.bayside.app.opinion.war.keyword.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayside.app.opinion.war.keyword.model.NegativeWord;
import com.bayside.app.opinion.war.keyword.service.NegativeWordService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;
import com.bayside.app.util.UuidUtil;

@RestController
public class NegativeWordController {
  @Autowired
  private NegativeWordService negativeServiceImpl;
  @RequestMapping(value ="/insertNegative",method=RequestMethod.GET)
  public Response insertNegative(NegativeWord record){
	  record.setId(UuidUtil.getUUID());
	  int num = negativeServiceImpl.insertNegative(record);
	  if(num >0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SAVEERRO,false);
	  }
  }
  @RequestMapping(value ="/updateNegative",method=RequestMethod.GET)
  public Response updateNegative(NegativeWord record){
	  int num  = negativeServiceImpl.updateNegative(record);
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.UPDATEEERRO,false);
	  }
  }
  @RequestMapping(value ="/deleteNegative",method=RequestMethod.GET)
  public Response deleteNegative(String id){
	  int num = negativeServiceImpl.deleteByPrimaryKey(id);
	  if(num > 0){
		  return new Response(ResponseStatus.Success,num,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.DELETEERRO,false);
	  }
  }
  @RequestMapping(value ="/selectAllNegative",method=RequestMethod.GET)
  public Response selectAllNegative(String weiduid){
	  List<NegativeWord> list = negativeServiceImpl.selectByweiduid(weiduid);
	  if(list.size()>0){
		  return new Response(ResponseStatus.Success,list,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
  @RequestMapping(value="/selectWordById",method=RequestMethod.GET)
  public Response selectWordById(String id){
	  NegativeWord word = negativeServiceImpl.selectWordById(id);
	  if(word!=null){
		  return new Response(ResponseStatus.Success,word,true);
	  }else{
		  return new Response(ResponseStatus.Error,AppConstant.responseInfo.SELECTEERRO,false);
	  }
  }
}

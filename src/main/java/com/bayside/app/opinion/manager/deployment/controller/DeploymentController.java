package com.bayside.app.opinion.manager.deployment.controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bayside.app.opinion.manager.deployment.bo.DeploymentNumBo;
import com.bayside.app.opinion.manager.deployment.bo.ServerConfigBo;
import com.bayside.app.opinion.manager.deployment.model.DeploymentNum;
import com.bayside.app.opinion.manager.deployment.model.ServerManage;
import com.bayside.app.opinion.manager.deployment.service.DeploymentService;
import com.bayside.app.util.AppConstant;
import com.bayside.app.util.Response;
import com.bayside.app.util.ResponseStatus;

@RestController
@RequestMapping("deploymentController")
public class DeploymentController {
	@Autowired
	private DeploymentService deploymentServiceImpl;
	
	@RequestMapping(value ="/uploadFile",method={RequestMethod.GET,RequestMethod.POST},produces ="text/html;charset=UTF-8")
	public Response uploadFile(HttpServletRequest request,String fileName){
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		boolean flag = deploymentServiceImpl.uploadFile(multiRequest,fileName);
		if(flag){
			return new Response(ResponseStatus.Success, "上传成功", true);
		}else{
			return new Response(ResponseStatus.Error, "上传失败", false);
		}
	}
	@RequestMapping(value ="/downloadFile",method={RequestMethod.GET,RequestMethod.POST},produces ="text/html;charset=UTF-8")
	public void downloadFile(HttpServletRequest request,HttpServletResponse response,String path){
		deploymentServiceImpl.downloadFile(request,response,path);
	}
	@RequestMapping(value ="/stopAllCollect",method=RequestMethod.GET)
	public Response stopAllCollect(){
		deploymentServiceImpl.stopAllCollect();
		return new Response(ResponseStatus.Success, "关闭成功", true);
	}
	@RequestMapping(value ="/startAllCollect",method=RequestMethod.GET)
	public Response startAllCollect(){
		deploymentServiceImpl.startAllCollect();
		return new Response(ResponseStatus.Success, "启动成功", true);
	}
	@RequestMapping(value ="/savedeploymentNum",method=RequestMethod.POST)
	public Response savedeploymentNum(HttpServletRequest request,DeploymentNumBo deploymentNumBo){
		int count = deploymentServiceImpl.savedeploymentNum(deploymentNumBo);
		if(count>0){
			return new Response(ResponseStatus.Success, AppConstant.responseInfo.SAVESUCCESS, true);
		}else{
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SAVEERRO, false);
		}
	}
	@RequestMapping(value ="/getCollect",method=RequestMethod.GET)
	public Response getCollect(){
		List<ServerManage> list = deploymentServiceImpl.getCollect();
		return new Response(ResponseStatus.Success, list, true);
	}
	@RequestMapping(value ="/getCollectInfo",method=RequestMethod.GET)
	public Response getCollectInfo(String ip){
		try {
			Map<String, Object> map = deploymentServiceImpl.getCollectInfo(ip);
			return new Response(ResponseStatus.Success, map, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Response(ResponseStatus.Error, AppConstant.responseInfo.SELECTEERRO, false);
		} 
		
	}
	@RequestMapping(value ="/recovery",method=RequestMethod.GET)
	public Response recovery(String ip,String collect){
		try {
			deploymentServiceImpl.recovery(ip, collect);
			return new Response(ResponseStatus.Success, "恢复成功", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new Response(ResponseStatus.Error,"恢复失败", false);
		} 
		
	}
}

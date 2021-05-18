package com.bayside.app.opinion.manager.deployment.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bayside.app.opinion.manager.deployment.bo.DeploymentNumBo;
import com.bayside.app.opinion.manager.deployment.dao.CollectManageMapper;
import com.bayside.app.opinion.manager.deployment.dao.ServerManageMapper;
import com.bayside.app.opinion.manager.deployment.model.CollectManage;
import com.bayside.app.opinion.manager.deployment.model.DeploymentNum;
import com.bayside.app.opinion.manager.deployment.model.ServerManage;
import com.bayside.app.opinion.manager.deployment.service.DeploymentService;
import com.bayside.filemanage.service.FileManageService;
@Service("deploymentServiceImpl")
public class DeploymentServiceImpl implements DeploymentService{
	@Autowired
	private CollectManageMapper collectManageMapper;
	@Autowired
	private ServerManageMapper serverManageMapper;
	@Override
	public boolean uploadFile(MultipartHttpServletRequest multiRequest, String fileName) {
		Iterator<String> iter = multiRequest.getFileNames();
		boolean flag = true;
		while (iter.hasNext()) {
			MultipartFile file = multiRequest.getFile(iter.next());
			String dir = file.getOriginalFilename();
			int version = collectManageMapper.selectNextId();
			String path = "C:/fileupload/collect/"+version;
			try {
				 File targetFile = new File(path, dir);
				 if(!targetFile.exists()){  
			            targetFile.mkdirs();  
			        }
				 file.transferTo(targetFile);
				CollectManage collectManage = new CollectManage();
				collectManage.setPath(path);
				collectManage.setFileName(dir);
				collectManageMapper.insertSelective(collectManage);
				List<ServerManage> list = serverManageMapper.selectAll();
				 Map<String, Object> map = new HashMap<String,Object>();
				 map.put("path", path);
				 map.put("fileName", dir);
				 FileManageService fileManageService = null;
				for (ServerManage serverManage : list) {
					try {
					 fileManageService =(FileManageService) Naming.lookup("rmi://"+serverManage.getInip()+":8888/fileManageService"); 
					 fileManageService.downLoadFile(map);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}
	@Override
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String path) {
		try {
			response.setContentType("application/force-download");
			response.setHeader("Content-disposition",
			    "attachment;filename=" + URLEncoder.encode(path.substring(path.lastIndexOf("/")), "UTF-8"));
			 OutputStream out = response.getOutputStream();
			 InputStream in = new FileInputStream(path);  
			 int b = -1;
			 while((b=in.read())!= -1)  
		        {  
		            out.write(b);  
		        }  
			 in.close();
			 out.flush();
			 out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public int savedeploymentNum(DeploymentNumBo deploymentNumBo) {
				DeploymentNum deploymentNum = new DeploymentNum();
				BeanUtils.copyProperties(deploymentNumBo, deploymentNum);
		
		return 0;
	}

	public void getTypeNum(int numPerServer,List<String> list,List gather){
		Iterator tiebait=list.iterator();    
		while(tiebait.hasNext()){
			String sttr = (String) tiebait.next();
			String[] sttrs = sttr.split(":");
			String getherOrder = sttrs[0];
			int num = Integer.parseInt(sttrs[1]);//对应的微博采集个数
			if(numPerServer>num){
					gather.add(sttr);
					numPerServer = numPerServer -num;
					tiebait.remove();
			}else if(numPerServer==num){
					gather.add(sttr);
					tiebait.remove();
				break;
			}else if(numPerServer<num){
					gather.add(getherOrder+":"+numPerServer);
					Collections.replaceAll(list, sttr, getherOrder+":"+(num-numPerServer));
				break;
			}
		}
	}
	@Override
	public void startAllCollect() {
		List<ServerManage> list = serverManageMapper.selectAll();
		 FileManageService fileManageService = null;
		for (ServerManage serverManage : list) {
			try {
			 fileManageService =(FileManageService) Naming.lookup("rmi://"+serverManage.getInip()+":8888/fileManageService"); 
			 fileManageService.startAllCollect();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	@Override
	public void stopAllCollect() {
		List<ServerManage> list = serverManageMapper.selectAll();
		 FileManageService fileManageService = null;
		for (ServerManage serverManage : list) {
			try {
			 fileManageService =(FileManageService) Naming.lookup("rmi://"+serverManage.getInip()+":8888/fileManageService"); 
			 fileManageService.stopAllCollect();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	@Override
	public List<ServerManage> getCollect(){
		 List<ServerManage> list = serverManageMapper.selectCollect();
		 for (ServerManage serverManage : list) {
			String serverCollect =  serverManage.getServerCollect();
			 if(serverCollect==null||"".equals(serverCollect)){
				 serverManage.setIsNormal(false);
				 continue;
			 }
			String serverCollects[] = serverCollect.split(",");
			Map<String,Integer> serverCollMap = new HashMap<String,Integer>();
			for (String string : serverCollects) {
				String attrs[] = string.split(":");
				serverCollMap.put(attrs[0], Integer.parseInt(attrs[1]));
			}
			String collect = serverManage.getCollect();
			String []collects = collect.split(";");
			for (String string : collects) {
				String attrs[] = string.split(":");
				if(serverCollMap.get(attrs[0])==null||serverCollMap.get(attrs[0])!=Integer.parseInt(attrs[1])){
					serverManage.setIsNormal(false);
					break;
				}
			}
		}
		 return list;
	}
	@Override
	public Map<String,Object> getCollectInfo(String ip) throws NotBoundException, JsonParseException, JsonMappingException, IOException{
		FileManageService fileManageService =(FileManageService) Naming.lookup("rmi://"+ip+":8888/fileManageService"); 
		String info = fileManageService.getCollectInfo();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(info, HashMap.class);
	}
	@Override
	public void recovery (String ip,String collect) throws MalformedURLException, RemoteException, NotBoundException{
		Map<String, Integer> map = new HashMap<String, Integer>();
		String collects[] = collect.split(";");
		for (String string : collects) {
			String param = getCollectParam(string.split(":")[0]);
			if(param!=null){
				map.put(param, Integer.parseInt(string.split(":")[1]));
			}
		}
		FileManageService fileManageService =(FileManageService) Naming.lookup("rmi://"+ip+":8888/fileManageService"); 
		fileManageService.recovery(map);
	}
	
	public String getCollectParam(String collect){
		if(collect.contains("search")) return collect+" subject";
		if("chinasite".equals(collect)||"foucesSite".equals(collect)||"abroadsite".equals(collect))return "ordinary "+collect;
		if("personmanage".equals(collect)) return "person "+collect;
		if("weiboconst".equals(collect))return "weibophoneconst "+collect;
		if(collect.contains("tieba")) return "tieba "+collect;
		if(collect.startsWith("weibo")&&collect.endsWith("queue")) return "weibo "+collect;
		return null;
		
	}
}

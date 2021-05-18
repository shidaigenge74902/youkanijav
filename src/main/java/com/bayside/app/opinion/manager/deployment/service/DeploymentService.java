package com.bayside.app.opinion.manager.deployment.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bayside.app.opinion.manager.deployment.bo.DeploymentNumBo;
import com.bayside.app.opinion.manager.deployment.bo.ServerConfigBo;
import com.bayside.app.opinion.manager.deployment.model.ServerManage;

public interface DeploymentService {
	/**
	 * <p>方法名称：uploadFile</p>
	 * <p>方法描述：上传文件</p>
	 * @param multiRequest
	 * @param fileName
	 * @return
	 * @author Administrator
	 * @since  2017年2月14日
	 * <p> history 2017年2月14日 Administrator  创建   <p>
	 */
	boolean uploadFile(MultipartHttpServletRequest multiRequest, String fileName);
	/**
	 * 
	 * <p>方法名称：downloadFile</p>
	 * <p>方法描述：下载文件</p>
	 * @param filename
	 * @return
	 * @author Administrator
	 * @since  2017年2月15日
	 * <p> history 2017年2月15日 Administrator  创建   <p>
	 */
	void downloadFile(HttpServletRequest request, HttpServletResponse response, String path);
	/**
	 * 
	 * <p>方法名称：savedeploymentNum</p>
	 * <p>方法描述：</p>
	 * @param deploymentNumBo
	 * @return
	 * @author Administrator
	 * @since  2017年2月17日
	 * <p> history 2017年2月17日 Administrator  创建   <p>
	 */
	int savedeploymentNum(DeploymentNumBo deploymentNumBo);
	/**
	 * 启动所有的采集
	 * <p>方法名称：startAllCollect</p>
	 * <p>方法描述：</p>
	 * @author Administrator
	 * @since  2017年8月4日
	 * <p> history 2017年8月4日 Administrator  创建   <p>
	 */
	void startAllCollect();
	/**
	 * 关闭所有的采集
	 * <p>方法名称：stopAllCollect</p>
	 * <p>方法描述：</p>
	 * @author Administrator
	 * @since  2017年8月4日
	 * <p> history 2017年8月4日 Administrator  创建   <p>
	 */
	void stopAllCollect();
	/**
	 * 
	 * <p>方法名称：getCollect</p>
	 * <p>方法描述：获取采集状况列表</p>
	 * @return
	 * @author Administrator
	 * @since  2017年8月23日
	 * <p> history 2017年8月23日 Administrator  创建   <p>
	 */
	List<ServerManage> getCollect();
	/**
	 * 
	 * <p>方法名称：getCollectInfo</p>
	 * <p>方法描述：获取采集的详细信息</p>
	 * @param ip
	 * @return
	 * @author Administrator
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @since  2017年8月23日
	 * <p> history 2017年8月23日 Administrator  创建   <p>
	 */
	Map<String, Object> getCollectInfo(String ip)throws MalformedURLException, RemoteException, NotBoundException, JsonParseException, JsonMappingException, IOException;
	void recovery(String ip, String collect) throws MalformedURLException, RemoteException, NotBoundException;
	

}

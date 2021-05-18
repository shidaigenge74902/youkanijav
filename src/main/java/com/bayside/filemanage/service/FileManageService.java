package com.bayside.filemanage.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface FileManageService extends Remote {
	//下载文档
	public void downLoadFile(Map<String, Object> map)throws RemoteException;
	//监测
	void monitorCollect() throws RemoteException;
	//停止所有的监测
	void stopAllCollect() throws RemoteException;
	//开始所有的监测
	void startAllCollect() throws RemoteException;
	//获取采集详细信息
	String getCollectInfo() throws RemoteException;
	//恢复采集
	void recovery(Map<String, Integer> map) throws RemoteException;
}

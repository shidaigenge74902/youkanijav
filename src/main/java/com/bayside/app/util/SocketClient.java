package com.bayside.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.bayside.app.opinion.manager.deployment.model.ServerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SocketClient {
	public void client(String ip,int port,String content){
		PrintWriter os = null;
		System.out.println(ip);
		BufferedReader is = null;
			Socket socket = null;
			try {
				socket = new Socket(ip,port);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return ;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return ;
			}
			String complete = "";
			while(!"ok".equals(complete)) {
				try {
				// 由系统标准输入设备构造BufferedReader对象
				os = new PrintWriter(socket.getOutputStream());
				// 由Socket对象得到输出流，并构造PrintWriter对象
				is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// 若从标准输入读入的字符串为 "bye"则停止循环
				os.println(content);
				os.flush();
				// 将从系统标准输入读入的字符串输出到Server
				// 刷新输出流，使Server马上收到该字符串
				System.out.println("Client:" + content);
				// 在系统标准输出上打印读入的字符串
				complete =  is.readLine();
				System.out.println("Server:" + complete);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Error" + e); // 出错，则打印出错信息
				}
			} // 继续循环
			try {
				if(os!=null){
					os.close(); // 关闭Socket输出流
				}
				if(is!=null){
					is.close();
				}
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 关闭Socket输入流
	}
	public static void maindowload(String[] args) {
		List<ServerConfig> list = new ArrayList<ServerConfig>();
		ObjectMapper mapper = new ObjectMapper();
		ServerConfig config = new ServerConfig();
		config.setIp("139.196.239.51");
		list.add(config);
		config = new ServerConfig();
		config.setIp("139.196.179.153");
		/*config = new ServerConfig();
		config.setIp("192.168.1.125");*/
		list.add(config);
		Map<String, Object> map = new HashMap<String,Object>();
		String filenames = "crawler-theme-1.0-SNAPSHOT.jar,crawler-common-1.0-SNAPSHOT.jar,crawler-metasearch-1.0-SNAPSHOT.jar,crawler-metasearch-baidu.jar";
		map.put("method", "downloadFile");
		map.put("param", filenames);
		for (ServerConfig serverConfig : list) {
			String serverip = serverConfig.getIp();
			new Thread( new Runnable() {
				@Override
				public void run() {
					SocketClient client = new SocketClient();
					try {
						client.client(serverip, 4700, mapper.writeValueAsString(map));
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			).start();
		}
	}
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("method", "startgather");
		map.put("gatherPackage", "crawler-metasearch-1.0-SNAPSHOT.jar");
		map.put("ip", "192.168.1.125");
		map.put("param", "");
		map.put("num", 2);
			String serverip = map.get("ip").toString();
			new Thread( new Runnable() {
				@Override
				public void run() {
					SocketClient client = new SocketClient();
					try {
						client.client(serverip, 4700, mapper.writeValueAsString(map));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}
			).start();
		}
}

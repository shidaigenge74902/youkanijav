package com.bayside.app.opinion.manager.ftpupload;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkServer {

	public static void main(String args[]) {
		String line = "";
		BufferedReader is = null ;
		PrintWriter os = null ;
		BufferedReader sin;
		Socket socket = null;
		ServerSocket server = null;
		try {

			try {
				server = new ServerSocket(4700);

				// 创建一个ServerSocket在端口4700监听客户请求
			} catch (Exception e) {

				System.out.println("can not listen to:" + e);

				// 出错，打印出错信息

			}
			try {
				socket = server.accept();
				// 使用accept()阻塞等待客户请求，有客户
				// 请求到来则产生一个Socket对象，并继续执行
			} catch (Exception e) {
				System.out.println("Error." + e);
				// 出错，打印出错信息
			}

		while (true) {
		
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// 由Socket对象得到输入流，并构造相应的BufferedReader对象

		//	os = new PrintWriter(socket.getOutputStream());

			// 由Socket对象得到输出流，并构造PrintWriter对象

			//sin = new BufferedReader(new InputStreamReader(System.in));

			// 由系统标准输入设备构造BufferedReader对象

			System.out.println("Client:" + is.readLine());

			// 在标准输出上打印从客户端读入的字符串

			//line = sin.readLine();

			// 从标准输入读入一字符串

				// 如果该字符串为 "bye"，则停止循环
				//os.println(line);

				// 向客户端输出该字符串

				//os.flush();

				// 刷新输出流，使Client马上收到该字符串

				//System.out.println("Server:" + line);

				// 在系统标准输出上打印读入的字符串

				System.out.println("Client:" + is.readLine());

				// 从Client读入一字符串，并打印到标准输出上

				//line = sin.readLine();

				// 从系统标准输入读入一字符串
			//socket.close();
			//os.close(); // 关闭Socket输出流
			//is.close(); // 关闭Socket输入流
		}// 继续循环
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error:" + e);
			// 出错，打印出错信息
		}
		} 
		
	}


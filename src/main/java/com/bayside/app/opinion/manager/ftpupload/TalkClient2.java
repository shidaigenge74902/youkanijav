package com.bayside.app.opinion.manager.ftpupload;

import java.io.*;

import java.net.*;
import java.util.Scanner;

public class TalkClient2 {

	public static void main(String args[]) {
		try {
			// 由Socket对象得到输入流，并构造相应的BufferedReader对象
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入命令：");
			String readline = sc.nextLine();
			Socket socket = new Socket("139.196.179.153", 4700);
			//Socket socket = new Socket("192.168.1.125", 4700);
			//readline = sin.readLine(); // 从系统标准输入读入一字符串
			while(true) {
				// 向本机的4700端口发出客户请求
				//BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

				// 由系统标准输入设备构造BufferedReader对象

				PrintWriter os = new PrintWriter(socket.getOutputStream());

				// 由Socket对象得到输出流，并构造PrintWriter对象

				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// 若从标准输入读入的字符串为 "bye"则停止循环

				os.println("1234444");

				// 将从系统标准输入读入的字符串输出到Server

				os.flush();

				// 刷新输出流，使Server马上收到该字符串

				System.out.println("Client:" + readline);

				// 在系统标准输出上打印读入的字符串

				System.out.println("Server:" + is.readLine());

				// 从Server读入一字符串，并打印到标准输出上
				System.out.println("请输入命令：");
				sc = new Scanner(System.in);
				readline = sc.nextLine();
				//readline = sin.readLine(); // 从系统标准输入读入一字符串
			} // 继续循环
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error" + e); // 出错，则打印出错信息
		}
		//os.close(); // 关闭Socket输出流

		//is.close(); // 关闭Socket输入流
	}

}
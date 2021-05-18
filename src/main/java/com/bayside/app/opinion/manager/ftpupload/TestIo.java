package com.bayside.app.opinion.manager.ftpupload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;

public class TestIo {
	public static void main1122(String[] args) throws IOException {
		File file = new File("F:/meta.log");
		try {
			InputStream in = new FileInputStream(file);
			int start = 0;
			 ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
			 byte[] data = new byte[1024];
			 int length = 0;  
			 while ((length = in.read(data)) != -1) {  
	                // 目标文件写入一部分内容  
				 outSteam.write(data, 0, length);
	            }  
			 outSteam.close();  
			 in.close();  
			System.out.println(outSteam.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);
		for (int i = 0; i < 1000; i++) {
			System.out.println(i);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

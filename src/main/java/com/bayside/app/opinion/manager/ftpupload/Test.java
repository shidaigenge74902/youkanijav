package com.bayside.app.opinion.manager.ftpupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.bayside.app.util.FtpUtil;

public class Test {
	private static String host = "106.14.73.91"; 
	private static int port = 21;
	private static String username = "ftpupload";
	private static String password = "bayside801";
	public static void main111(String[] args) {
		try {
			File file = new File("C:/crawler-metasearch-1.0-SNAPSHOT.jar");
			InputStream input = new FileInputStream(file);
			FtpUtil.uploadFile("106.14.73.91", 21, "ftpupload", "bayside801", "fileupload", "wenjian",file.getName(), input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		FtpUtil.downloadFile(host, port, username, password, "fileupload/wenjian", "hadoop.log", "f:/ftpdownload");
	}
}

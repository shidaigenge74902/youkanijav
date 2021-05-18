package com.bayside.app.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

public class CmdCommand {
	private java.lang.Process p; 
	 private InputStream is;
	 private OutputStream os;
	 private BufferedWriter bw;
	 private BufferedReader br;
	 private ProcessBuilder pb;
	 private InputStream stdErr;
	 public CmdCommand() {
	 }
	 //获取Process输入输流
	 public void setCmd(String cmd) {
	  try {
	   p = Runtime.getRuntime().exec(cmd);
	   os = p.getOutputStream();
	   is = p.getInputStream();
	   stdErr = p.getErrorStream();
	  } catch (IOException e) {
	   System.err.println(e.getMessage());
	  }
	 }
	 //向Process输命令
	 public void writeCmd(String cmd) {
	  try {
	   bw = new BufferedWriter(new OutputStreamWriter(os));
	   bw.write(cmd);
	   bw.newLine();
	   bw.flush();
	   bw.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	 //读Process执行结
	 public String readCmd() {
	  StringBuffer sb = new StringBuffer();
	  br = new BufferedReader(new InputStreamReader(is));
	  String buffer = null;
	  try {
	   while ((buffer = br.readLine()) != null) {
	    sb.append(buffer + "\n");
	   }
	   System.out.println(p.waitFor());
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return sb.toString();
	 }
	 //命令股脑塞入list
	 public LinkedList<String> doCmd(LinkedList<String> lists) {
	  LinkedList<String> list = new LinkedList<String>();
	  for (String s : lists) {
	   writeCmd(s);
	   list.add(readCmd());
	  }
	  return list;
	 }
	 public static void main(String[] args) {
	  CmdCommand cmd = new CmdCommand();
	  cmd.setCmd("cmd");
	  cmd.writeCmd("TASKKILL /PID 16360 /f");
	  // cmd.writeCmd("java -jar  C:/crawler-metasearch-1.0-SNAPSHOT.jar");
	  System.out.println(cmd.readCmd());
	  //LinkedList<String> list = new LinkedList<String>();
	  //list.add("dir/b");
	  //list = cmd.doCmd(list);
	  // for(String s:list){
	  // System.out.print(s);
	  // }
	 }
}

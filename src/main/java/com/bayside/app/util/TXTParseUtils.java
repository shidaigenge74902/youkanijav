package com.bayside.app.util;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import com.bayside.app.opinion.war.wordinfo.model.WordInfo;
import com.bayside.app.opinion.war.wordinfo.service.WordInfoService;

public class TXTParseUtils {

	private static final Logger log = Logger.getLogger(TXTParseUtils.class);
	private static String url = "jdbc:mysql://192.168.1.88:3306/baysidedevelop?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false"; // 数据库地址
	private static String username = "root"; // 数据库用户名
	private static String password = "abc123"; // 数据库密码
	
	    /**
	     * 功能：Java读取txt文件的内容
	     * 步骤：1：先获得文件句柄
	     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	     * 3：读取到输入流后，需要读取生成字节流
	     * 4：一行一行的输出。readline()。
	     * 备注：需要考虑的是异常情况
	     * 
	     * @param filePath
	     */
	    public static void readTxtFile(String filePath){
	        try {
	                String encoding="utf-8";
	           
	                File file=new File(filePath);
	               if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    		new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    List<WordInfo> list = new ArrayList<WordInfo>();
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                    	   String[] names = lineTxt.split("\\s+");
	                    	    WordInfo record = new WordInfo();
	                    	    record.setId(UuidUtil.getUUID());
	                    	    record.setWordname(names[0]);
	                    	    if(names.length>=2){
	                          	     if(!"".equals(names[1])){
	                          		    System.out.println(names[1]);
	                              	     record.setValue(names[1]);
	                              	    }
	                          	    }
	                          	   list.add(record);
	                    	    
	                    	    
	                    	   
	                           System.out.println(lineTxt);
	                    }
	                    //批量插入
	                    
	                	PreparedStatement psts = null;
	            		Connection conn = null;
	            		int count = 0;
	            		if (list == null || list.size() < 1) {
	            			return;
	            		}
	            		try {
	            			conn = DBUtil.getConnection(url, username, password);
	            			String sql ="insert into bs_wordinfo "
	            					+ "(ID,wordname,value) "
	            					+ " values (?,?,?)";
	            			psts = conn.prepareStatement(sql);
	            			conn.setAutoCommit(true);
	            			for (WordInfo opinionReport : list) {
	            				psts.setString(1, opinionReport.getId());
	            				if(opinionReport.getWordname()!=null){
	            					psts.setString(2, opinionReport.getWordname());
	            				}
	            				if(opinionReport.getValue()!=null){
	            					psts.setString(3, opinionReport.getValue());
	            				}
	            				psts.addBatch();
	            			}
	            			System.out.println(psts.toString());
	            			int result = psts.executeBatch().length;
	            			return;
	            		} catch (SQLException e) {
	            			System.out.println(e.getMessage());
	        				log.error(e.getMessage(),e);
	            		}finally{
	            			DBUtil.close(null, psts, conn);
	            		}
	                   read.close();
	        }else{
	            System.out.println("找不到指定的文件");
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
				log.error("读取文件内容出错"+e.getMessage(),e);
	        }
	     
	    }
	     
	    public static void main(String argv[]){
	    
	        String filePath = "C:/BosonNLP_sentiment_score.txt";
	  
//	      "res/";
	        readTxtFile(filePath);
	    }
	     




	

}

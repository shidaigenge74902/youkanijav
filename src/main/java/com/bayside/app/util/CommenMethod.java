package com.bayside.app.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * <p>Title: CommenMethod</P>
 * <p>Description: 通用方法类</p>
 * <p>Copyright: 山东贝赛信息科技有限公司 Copyright (c) 2016</p>
 * @author Administrator
 * @version 1.0
 * @since 2016年9月27日
 */
public class CommenMethod {
	/**
	 * 
	 * <p>方法名称：getDomainName</p>
	 * <p>方法描述：域名解析</p>
	 * @param url
	 * @return
	 * @author Administrator
	 * @since  2016年9月27日
	 * <p> history 2016年9月27日 Administrator  创建   <p>
	 */
	public static String getDomainName(String url) {
		if (url == null || "".equals(url)) {
			return "";
		}
		Pattern p = Pattern.compile("([\\w-]+\\.)+[\\w-]+(?<=/?)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		if(matcher.find()){
			return matcher.group();	
		}
		return "";
	
	}
	/**
	 * 
	 * <p>方法名称：getDateFromString</p>
	 * <p>方法描述：从字符串中匹配时间（还有待改进）</p>
	 * @param url
	 * @return
	 * @author Administrator
	 * @since  2016年9月27日
	 * <p> history 2016年9月27日 Administrator  创建   <p>
	 */
	public static String getDateFromString(String url) {
		if (url == null || "".equals(url)) {
			return "";
		}
		Pattern p = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-9]{1,2}",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		if(matcher.find()){
			return matcher.group();
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println(getDomainName("http://www.g-medon.com/(S(vkbcr3iq2qwml5cgycy0lnel))/Item.aspx?id=47982"));
	}
	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		if(null!=files){
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag)
						break;
				} // 删除子目录
				else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag)
						break;
				}
			}
		}
		
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

}

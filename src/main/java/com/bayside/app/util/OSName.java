package com.bayside.app.util;

public class OSName {
	/**
	 * 
	 * <p>
	 * 方法名称：getOSName
	 * </p>
	 * <p>
	 * 方法描述： 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等
	 * </p>
	 * 
	 * @return
	 * @author liuyy
	 * @since 2016年10月27日
	 *        <p>
	 *        history 2016年10月27日 Administrator 创建
	 *        <p>
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

}

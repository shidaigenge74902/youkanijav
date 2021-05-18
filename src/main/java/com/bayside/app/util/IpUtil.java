package com.bayside.app.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class IpUtil {
	public static final String getIpAddr(final HttpServletRequest request)
			throws Exception {
		if (request == null) {
			throw (new Exception(
					"getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString)
				|| "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}
		return ipString;
	}


}

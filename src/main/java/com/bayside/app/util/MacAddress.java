package com.bayside.app.util;

public class MacAddress {
	public static String getMacAddress() {
		String os = OSName.getOSName();
		String execStr = Other.getSystemRoot() + "/system32/ipconfig /all";
		String mac = null;
		if (os.startsWith("windows")) {
			if (os.equals("windows xp")) {// xp
				mac = Other.getWindowXPMACAddress(execStr);
			} else if (os.equals("windows 2003")) {// 2003
				mac = Other.getWindowXPMACAddress(execStr);
			} else {// win7
				mac = Other.getWindow7MACAddress();
			}
		} else if (os.startsWith("linux")) {
			mac = Other.getLinuxMACAddress();
		} else {
			mac = Other.getUnixMACAddress();
		}
		return mac;
	}


}

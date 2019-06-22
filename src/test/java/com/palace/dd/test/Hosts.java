package com.palace.dd.test;

import com.alibaba.dcm.DnsCacheManipulator;

public class Hosts {
	 
	public static void host() {
		String host = "192.168.0.77";
		DnsCacheManipulator.setDnsCache("testmysql",host);
		DnsCacheManipulator.setDnsCache("config.dafy.service","192.168.0.103");
	}
 
	
}

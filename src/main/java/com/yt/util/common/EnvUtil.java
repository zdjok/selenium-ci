package com.yt.util.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class EnvUtil {
	
	public static String getIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getOSName(){
		Properties pro = System.getProperties();
		return pro.getProperty("os.name");
	}
	
	public static String getOSVersion(){
		Properties pro = System.getProperties();
		return pro.getProperty("os.version");
	}
	
	public static String getOSArch(){
		Properties pro = System.getProperties();
		return pro.getProperty("os.arch");
	}
	
	public static String getOSUserName(){
		Properties pro = System.getProperties();
		return pro.getProperty("user.name");
	}

	public static void main(String[] args){
		System.out.println(getOSName() + " " +  getOSArch());
	}
}

package com.yt.util.common;

public class SmtpFactory {
	private static SmtpUtil smtp = null;
	
	public static SmtpUtil getSmtpUtil(String smtpHostName, String username, String password){
		if(smtp == null){
			if(smtpHostName == null)
				smtp = new SmtpUtil(username, password);
			else
				smtp = new SmtpUtil(smtpHostName, username, password);
		}
		return smtp;
	}
}

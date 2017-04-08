package com.yt.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigIO {
	private String homePage;
	private String loginUrl;
	private String userName;
	private String password;
	
	private String signUserName;
	private String signPsw;
	private String signFullName;
	private String signEmail;
	
	private String browseName;
	private String ieDriver;
	private String chromeDriver;
	
	private int timeOut;
	private int thinkTime;
	
	private String testDataFilePath;
	private String pageObjPath;
	private String pageObjPkg;
	
	private String from;
	private String recipient;
	private String mailPassword;
	private String smtpHost;
	
	private static ConfigIO config;
	
	private ConfigIO(){
		init();
	}
	
	public static ConfigIO getInstance(){
		if(null == config)
			config = new ConfigIO();
		return config;
	}
	
	private void init(){
		InputStream in = ClassLoader.getSystemResourceAsStream("Config.properties");
		Properties pro = new Properties();
		try {
			pro.load(in);
			this.homePage = pro.getProperty("jenkins.homepage");
			this.loginUrl = pro.getProperty("jenkins.loginurl");
			this.userName = pro.getProperty("jenkins.username");
			this.password = pro.getProperty("jenkins.password");
			this.signUserName = pro.getProperty("sign.username");
			this.signPsw = pro.getProperty("sign.psw");
			this.signFullName = pro.getProperty("sign.fullname");
			this.signEmail = pro.getProperty("sign.email");
			this.browseName = pro.getProperty("browseName");
			this.ieDriver = pro.getProperty("WebDriver.ie");
			this.chromeDriver = pro.getProperty("WebDriver.chrome");
			this.timeOut = Integer.valueOf(pro.getProperty("timeOut"));
			this.thinkTime = Integer.valueOf(pro.getProperty("thinkTime"));
			this.testDataFilePath = pro.getProperty("testcase.testdata.path");
			this.pageObjPath = pro.getProperty("testcase.pages.pageobjpath");
			this.pageObjPkg = pro.getProperty("testcase.pages.pageobjpcg");
			this.from = pro.getProperty("report.email.from");
			this.recipient = pro.getProperty("report.email.to");
			this.mailPassword = pro.getProperty("report.email.password");
			this.smtpHost = pro.getProperty("report.email.smtp.host");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHomePage() {
		return homePage;
	}
	
	public String getLoginUrl(){
		return loginUrl;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
	public String getSignUserName() {
		return signUserName;
	}

	public String getSignPsw() {
		return signPsw;
	}

	public String getSignFullName() {
		return signFullName;
	}

	public String getSignEmail() {
		return signEmail;
	}

	public String getBrowseName() {
		return browseName;
	}

	public String getIeDriver() {
		return ieDriver;
	}

	public String getChromeDriver() {
		return chromeDriver;
	}
	
	public int getTimeOut(){
		return timeOut;
	}
	
	public int getThinkTime(){
		return thinkTime;
	}

	public String getTestDataFilePath() {
		return testDataFilePath;
	}
	
	public String getPageObjPath() {
		return pageObjPath;
	}
	
	public String getPageObjPkg() {
		return pageObjPkg;
	}

	public String getFrom() {
		return from;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public String getSmtpHost() {
		return smtpHost;
	}
	
}

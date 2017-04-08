package com.yt.pageobject;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.yt.base.TestBase;
import com.yt.injection.PageObject;
import com.yt.io.ConfigIO;
import com.yt.util.common.ParseXml;
import com.yt.util.common.WebUtil;

@PageObject
public class HomePage {
	private String home_Url = "http://localhost:8080/jenkins/";
	static ConfigIO config = ConfigIO.getInstance();
	HashMap<String,String> dataMap = ParseXml.getNodeData(this.getClass().getSimpleName());
	
	static WebDriver driver;
	
	/**
	 * 反射创建对象时调用
	 */
	public HomePage(){
		driver = new TestBase().getWebDriver();
		//页面注入到driver
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "j_username")
	private WebElement inputBox_UserName;
	
	@FindBy(how = How.NAME, using = "j_password")
	private WebElement inputBox_Password;
	
	@FindBy(how = How.ID, using = "yui-gen1-button")
	private WebElement btn_Login;
	
	public void doLogin(){
		if(WebUtil.isElementPresent(driver, inputBox_UserName, config.getTimeOut(), true)){
//			WebUtil.sendKeys(inputBox_UserName, config.getUserName());
//			WebUtil.sendKeys(inputBox_Password, config.getPassword());
			WebUtil.sendKeys(inputBox_UserName, dataMap.get("userName"));
			WebUtil.sendKeys(inputBox_Password, dataMap.get("password"));
			btn_Login.click();
		}
	}
	
	public void doLogin(String userName, String psw){
		if(WebUtil.isElementPresent(driver, inputBox_UserName, config.getTimeOut(), true)){
			WebUtil.sendKeys(inputBox_UserName, userName);
			WebUtil.sendKeys(inputBox_Password, psw);
			btn_Login.click();
		}
	}

	public String getHome_Url() {
		return home_Url;
	}

	public void setHome_Url(String home_Url) {
		this.home_Url = home_Url;
	}
	
}

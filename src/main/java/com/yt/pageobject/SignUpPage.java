package com.yt.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.yt.base.TestBase;
import com.yt.injection.PageObject;
import com.yt.io.ConfigIO;
import com.yt.util.common.WebUtil;

@PageObject
public class SignUpPage {
	static ConfigIO config = ConfigIO.getInstance();
	WebDriver driver;
	
	private static String url_SignUp = "http://localhost:8080/jenkins/signup";
	
	
	public SignUpPage(){
		driver = new TestBase().getWebDriver();
		//页面注入到driver
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "username")
	private WebElement inputBox_UserName;
	
	@FindBy(how = How.NAME, using = "password1")
	private WebElement inputBox_Psw1;
	
	@FindBy(how = How.NAME, using = "password2")
	private WebElement inputBox_Psw2;
	
	@FindBy(how = How.NAME, using = "fullname")
	private WebElement inputBox_Fullname;
	
	@FindBy(how = How.NAME, using = "email")
	private WebElement inputBox_Email;
	
	@FindBy(how = How.ID, using = "yui-gen1-button")
	private WebElement btn_SignUp;
	
	@FindBy(how = How.XPATH, using = "//*[@id='main-panel']/div/div")
	private WebElement txt_ErrorMsg;

	
	public void signUp_EmailIsInvalid(){
		if(WebUtil.isElementPresent(driver, inputBox_UserName, config.getTimeOut(), true)){
			WebUtil.sendKeys(inputBox_UserName, config.getSignUserName());
			WebUtil.sendKeys(inputBox_Psw1, config.getSignPsw());
			WebUtil.sendKeys(inputBox_Psw2, config.getSignPsw());
			WebUtil.sendKeys(inputBox_Fullname, config.getSignFullName());
			btn_SignUp.click();
		}
	}
	
	public String getUrl_SignUp() {
		return url_SignUp;
	}
	
	public String getErrorMsg(){
		return txt_ErrorMsg.getText();
	}
	
}

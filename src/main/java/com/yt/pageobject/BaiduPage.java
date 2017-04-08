package com.yt.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.yt.base.TestBase;
import com.yt.injection.PageObject;
import com.yt.io.ConfigIO;
import com.yt.util.common.WebUtil;

@PageObject
public class BaiduPage {
	private String url = "https://www.baidu.com";
	ConfigIO config = ConfigIO.getInstance();
	
	WebDriver driver;
	
	public BaiduPage(){
		driver = new TestBase().getWebDriver();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="kw")
	private WebElement inputBox_Search;
	
	@FindBy(id="su")
	private WebElement btn_Search;
	
	public WebDriver search(String key){
		driver.get(url);
		if(WebUtil.isElementPresent(driver, inputBox_Search, config.getTimeOut(), true)){
			inputBox_Search.sendKeys(key);
			btn_Search.click();
		}
		return driver;
	}
}

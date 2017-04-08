package com.yt.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.yt.base.TestBase;
import com.yt.injection.PageObject;

@PageObject
public class NewJobPage {
	private String url_DashBoard = "http://localhost:8080/jenkins/view/All/newJob";
	private By newJobLink_Locator = By.linkText("新建");
	
	WebDriver driver;
	
	public NewJobPage(){
		driver = new TestBase().getWebDriver();
		//页面注入到driver
		PageFactory.initElements(driver, this);
	}

	public String getUrl_DashBoard() {
		return url_DashBoard;
	}

	public By getNewJobLink_Locator() {
		return newJobLink_Locator;
	}
	
}

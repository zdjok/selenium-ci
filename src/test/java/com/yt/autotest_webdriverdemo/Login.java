package com.yt.autotest_webdriverdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.io.ConfigIO;
import com.yt.util.common.WebUtil;

public class Login extends TestBase{
	private String homeUrl = "http://localhost:8080/jenkins/";
	ConfigIO config = ConfigIO.getInstance();
	
	@Test
	public void login(){
		WebUtil.get(driver, homeUrl);
		if(WebUtil.isElementPresent(driver, By.name("j_username"), config.getTimeOut(), true)){
			WebElement ele_name = driver.findElement(By.id("j_username"));
			ele_name.click();
			ele_name.clear();
			ele_name.sendKeys("admin");
			WebElement ele_psw = driver.findElement(By.name("j_password"));
			ele_psw.click();
			ele_psw.clear();
			ele_psw.sendKeys("admin");
			WebElement ele_btn = driver.findElement(By.id("yui-gen1-button"));
			ele_btn.click();
		}
	}
	
}

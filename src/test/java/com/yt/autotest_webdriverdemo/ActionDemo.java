package com.yt.autotest_webdriverdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.io.ConfigIO;
import com.yt.util.common.WebUtil;

public class ActionDemo extends TestBase{
	private String homeUrl = "http://localhost:8080/jenkins/";
	ConfigIO config = ConfigIO.getInstance();
	
	@Test
	public void login(){
		WebUtil.get(driver, homeUrl);
		if(WebUtil.isElementPresent(driver, By.name("j_username"), config.getTimeOut(), true)){
			Actions action = new Actions(driver);
			WebElement ele_name = driver.findElement(By.name("j_username"));
			//模拟鼠标点击动作
			action.click(ele_name).perform();
			//模拟键盘输入
			action.sendKeys(ele_name,"admin").perform();
			WebElement ele_psw = driver.findElement(By.name("j_password"));
			action.click(ele_psw).perform();;
			action.sendKeys(ele_psw,"admin").perform();;
			WebElement ele_btn = driver.findElement(By.id("yui-gen1-button"));
			action.click(ele_btn).perform();;
		}
	}
}

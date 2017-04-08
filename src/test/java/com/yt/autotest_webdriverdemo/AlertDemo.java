package com.yt.autotest_webdriverdemo;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.io.ConfigIO;
import com.yt.util.common.WebUtil;

public class AlertDemo extends TestBase{
	ConfigIO config = ConfigIO.getInstance();
	private String homeBaidu = "https://www.baidu.com/";
	
	@Test
	public void bdTest() throws InterruptedException{
		WebUtil.get(driver, homeBaidu);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("alert('hello world!!!')");
		Thread.sleep(2000);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		
		Thread.sleep(3000);
	}
}

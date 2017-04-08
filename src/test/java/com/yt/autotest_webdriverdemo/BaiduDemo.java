package com.yt.autotest_webdriverdemo;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.io.ConfigIO;
import com.yt.util.common.WebUtil;

public class BaiduDemo extends TestBase{
	ConfigIO config = ConfigIO.getInstance();
	private String homeBaidu = "https://www.baidu.com/";
	
	@Test
	public void bdTest() throws InterruptedException{
		Thread.sleep(3000);
		WebUtil.get(driver, homeBaidu);
		if(WebUtil.isElementPresent(driver, By.id("kw"), config.getTimeOut(), true)){
			driver.findElement(By.id("kw")).sendKeys("webdriver");
			driver.findElement(By.id("su")).click();
			Thread.sleep(1000);
			if(WebUtil.isElementPresent(driver, By.xpath("//*[@id='page']/a[10]"), config.getTimeOut(), true))
				driver.findElement(By.xpath("//*[@id='page']/a[10]")).click();
			Thread.sleep(3000);
		}
	}
}

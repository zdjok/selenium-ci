package com.yt.autotest_injection;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.injection.AutoInject;
import com.yt.io.ConfigIO;
import com.yt.pageobject.NewJobPage;
import com.yt.util.common.HttpRequest;
import com.yt.util.common.WebUtil;
import com.yt.util.listeners.CustomTestListener;
import com.yt.util.listeners.TakeScreenshotOnFailure;
import com.yt.util.listeners.TestReportListener;

@Listeners({CustomTestListener.class,TestReportListener.class})
public class NewJob extends TestBase{
	Logger logger = Logger.getLogger(NewJob.class);
	ConfigIO config = ConfigIO.getInstance();
	
	@AutoInject
	private static NewJobPage newJobPage;
	
	@Test
	@TakeScreenshotOnFailure
	public void test_NewJob() throws InterruptedException{
		List<Cookie> cookies = HttpRequest.doLogin();
		WebUtil.addCookies(driver, cookies);
//		WebUtil.get(driver, config.getHomePage());
		WebUtil.get(driver, newJobPage.getUrl_DashBoard());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		driver.findElement(NewJobPage.getNewJobLink_Locator()).click();
	}
	
	@AfterMethod
	public void clear(){
		driver.manage().deleteAllCookies();
	}
}

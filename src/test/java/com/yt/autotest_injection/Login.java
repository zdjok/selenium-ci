package com.yt.autotest_injection;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.injection.AutoInject;
import com.yt.io.ConfigIO;
import com.yt.pageobject.HomePage;
import com.yt.util.common.WebUtil;
import com.yt.util.listeners.CustomTestListener;
import com.yt.util.listeners.TakeScreenshotOnFailure;
import com.yt.util.listeners.TestReportListener;

@Listeners({CustomTestListener.class, TestReportListener.class})
public class Login extends TestBase{
	ConfigIO config = ConfigIO.getInstance();
	
	@AutoInject
	private static HomePage homePage;
	
/*	@BeforeMethod
	public void init(){
		homePage = PageFactory.initElements(driver, HomePage.class);
	}*/
	
	@Test
	@TakeScreenshotOnFailure
	public void testLogin() throws InterruptedException{
		WebUtil.get(driver, config.getHomePage());
		homePage.doLogin();
		Assert.assertEquals(driver.getTitle(), "Dashboard [Jenkins]");
	}
	
	@Test
	@TakeScreenshotOnFailure
	@DataProvider
	public void testLogin_WrongPassword(){
		WebUtil.get(driver, config.getHomePage());
		homePage.doLogin("admin", "ad");
		Assert.assertEquals(driver.getTitle(), "Login Error [Jenkins");
	}
	
	@AfterMethod
	public void clear(){
		WebUtil.waitForThink();
		driver.manage().deleteAllCookies();
	}

}

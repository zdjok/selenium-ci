package com.yt.autotest_injection;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.injection.AutoInject;
import com.yt.pageobject.SignUpPage;
import com.yt.util.common.WebUtil;
import com.yt.util.listeners.CustomTestListener;
import com.yt.util.listeners.TakeScreenshotOnFailure;
import com.yt.util.listeners.TestReportListener;

@Listeners({CustomTestListener.class, TestReportListener.class})
public class SignUp extends TestBase {
	Logger logger = Logger.getLogger(SignUp.class);
	
/*	@BeforeMethod
	public void init(){
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
	}*/
	
	@AutoInject
	private static SignUpPage signUpPage;
	
	@Test
	@TakeScreenshotOnFailure
	public void test_SignUpWithInvalidEmail(){
		WebUtil.get(driver, signUpPage.getUrl_SignUp());
		signUpPage.signUp_EmailIsInvalid();
		logger.info(signUpPage.getErrorMsg());
		Assert.assertEquals(signUpPage.getErrorMsg(), "Invalid e-mail address");
	}
	
	@AfterMethod
	public void clear(){
		driver.manage().deleteAllCookies();
	}
}

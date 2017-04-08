package com.yt.selenium;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LocalWDGridDemo {
	private WebDriver driver;
	DesiredCapabilities dc;
	
	
	@Parameters({"nodeUrl"})
	@BeforeMethod
	public void setUp(String nodeUrl) throws MalformedURLException{
		dc = DesiredCapabilities.firefox();
		driver = new RemoteWebDriver(new URL(nodeUrl), dc);
		driver.get("http://www.baidu.com");
		driver.manage().window().maximize();
	}
	
	@Test
	public void test(){
		driver.findElement(By.id("kw")).click();
		driver.findElement(By.id("kw")).sendKeys("json");
	}
	
	@AfterClass
	public void tearDown(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}
}

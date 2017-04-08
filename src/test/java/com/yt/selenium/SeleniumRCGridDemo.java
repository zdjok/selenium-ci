package com.yt.selenium;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumRCGridDemo {
	Selenium selenium;
	
	@BeforeClass
	public void setUp(){
		selenium = new DefaultSelenium("192.168.182.129", 5555, "*firefox", "https://www.baidu.com");
		selenium.start();
		selenium.windowMaximize();
	}
	
	@Test
	public void test(){
		selenium.open("");
	}
	
	public void tearDown(){
		selenium.stop();
	}
}

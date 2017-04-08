package com.yt.autotest_injection;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.yt.base.TestBase;
import com.yt.injection.AutoInject;
import com.yt.io.ConfigIO;
import com.yt.pageobject.BaiduPage;
import com.yt.util.listeners.CustomTestListener;

@Listeners(CustomTestListener.class)
public class BaiduTest extends TestBase {
	ConfigIO config = ConfigIO.getInstance();
	
	@AutoInject
	private static BaiduPage baiduPage;
	
	@Test
	public void searchTest(){
		baiduPage.search("SeleniumIDE");
	}
}

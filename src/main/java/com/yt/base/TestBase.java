package com.yt.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.yt.io.ConfigIO;

public class TestBase extends AbstractWebDriver {
	ConfigIO config = ConfigIO.getInstance();
	protected static WebDriver driver = null;
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	
	@BeforeClass
	public void setUp(){
		String browseName = config.getBrowseName();
		if(browseName.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver", config.getIeDriver());
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capabilities);
//			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		}else if(browseName.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", config.getChromeDriver());
			driver = new ChromeDriver();
		}else if(browseName.equalsIgnoreCase("firefox")){
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			//默认安装路径不需要指定，不是默认安装路径指定Firefox安装路径
//			capabilities.setCapability("webdriver.firefox.bin", firefoxPath);
			driver = new FirefoxDriver(capabilities);
//			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
/*			FirefoxProfile pf = new FirefoxProfile();
			driver = new FirefoxDriver(pf);*/
		}
	}
	
	@AfterClass
	public void tearDown(){
		driver.close();
		driver.quit();
	}

}

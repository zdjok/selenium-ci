package com.yt.autotest_webdriverdemo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Frame {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;

  @BeforeTest
  public void setUp() throws Exception {
/*	    System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
	    driver = new ChromeDriver();*/
	  	DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("webdriver.firefox.bin", "");
		driver = new FirefoxDriver(capabilities);
	    baseUrl = "http://seleniumhq.github.io/selenium/docs/api/java/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testFrame() throws Exception {
    driver.get(baseUrl);
    driver.manage().window().maximize();
    
    driver.switchTo().defaultContent();
    driver.switchTo().frame("classFrame");
    driver.findElement(By.linkText("com.thoughtworks.selenium")).click();
    driver.switchTo().defaultContent();
//    driver.switchTo().frame("packageListFrame");
    driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='packageListFrame']")));
    driver.findElement(By.linkText("com.thoughtworks.selenium")).click();
    System.out.println("switch to index 0");
    driver.switchTo().defaultContent();
    driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='packageFrame']")));
    driver.findElement(By.cssSelector("span.interfaceName")).click();
    driver.switchTo().defaultContent();
    driver.switchTo().frame("classFrame");
    driver.findElement(By.linkText("HttpCommandProcessor")).click();
  }

  @AfterTest
  public void tearDown() throws Exception {
    driver.quit();
  }

  protected boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  protected boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  protected String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

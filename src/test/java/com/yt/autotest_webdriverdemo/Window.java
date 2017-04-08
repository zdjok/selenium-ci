package com.yt.autotest_webdriverdemo;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.base.Function;


public class Window {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;

  @BeforeTest
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "https://www.baidu.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testWindow() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("kw")).clear();
    driver.findElement(By.id("kw")).sendKeys("hyddd");
    driver.findElement(By.id("su")).click();
    driver.findElement(By.linkText("hyddd - 博客园")).click();
    Thread.sleep(2000);
    String preHandler = driver.getWindowHandle();
    goToNewWindow(driver, "hyddd - 博客园");

    Thread.sleep(3000);
    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp |  | ]]
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | title=hyddd - 博客园 | ]]
    driver.findElement(By.linkText("阅读全文")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow |  | ]]
    Thread.sleep(2000);
    driver.switchTo().window(preHandler);
    Thread.sleep(1000);
    driver.findElement(By.id("kw")).click();
    driver.findElement(By.id("kw")).clear();
    driver.findElement(By.id("kw")).sendKeys("selenium");
    driver.findElement(By.id("su")).click();
    Thread.sleep(10000);
  }
  
  @Test
  public void testWindowWithJS() throws InterruptedException{
	  driver.get(baseUrl + "/");
	    driver.findElement(By.id("kw")).clear();
	    driver.findElement(By.id("kw")).sendKeys("hyddd");
	    driver.findElement(By.id("su")).click();
	    
	    WebElement ele = driver.findElement(By.linkText("hyddd - 博客园"));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].setAttribute('target','');", ele);
//	    js.executeScript("arguments[0].setAttribute('target','_self');", ele);
//	    js.executeScript("arguments[0].removeAttribute('target');", ele);
	    ele.click();
	    Thread.sleep(3000);
	    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp |  | ]]
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | title=hyddd - 博客园 | ]]
	    driver.findElement(By.linkText("阅读全文")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow |  | ]]
	    Thread.sleep(1000);
  }

  @AfterTest
  public void tearDown() throws Exception {
    driver.quit();
  }
  
  /**
 * @param dr:浏览器句柄
 * @param title
 */
private void goToNewWindow(WebDriver dr, String title){
	  Set<String>  set =  dr.getWindowHandles();
	  for(String handler : set){
		  dr.switchTo().window(handler);
		  if(dr.getTitle().equals(title))
			  return;
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
  
  /*
   * 判断页面时候加载成功
   */
  protected Function<WebDriver, Boolean> isPageLoaded() {
      return new Function<WebDriver, Boolean>() {
          public Boolean apply(WebDriver driver) {
              return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
          }
      };
  }

  public void waitForPageLoad() {
      WebDriverWait wait = new WebDriverWait(driver, 30);
      wait.until(isPageLoaded());
  }
}


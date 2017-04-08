package com.yt.util.common;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import com.yt.io.ConfigIO;

public class WebUtil {
	static ConfigIO config = ConfigIO.getInstance();
	static Logger logger = Logger.getLogger(WebUtil.class);
	
	/**
	 * 页面导航
	 * @param dr
	 * @param url：导航地址
	 */
	public static void get(WebDriver dr, String url){
		dr.get(url);
		dr.manage().window().maximize();
//		dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	/**
	 * 网页导航
	 * @param dr
	 * @param url：导航地址
	 */
	public static void navigate(WebDriver dr, String url){
		dr.navigate().to(url);
	}
	
	/**
	 * 添加cookie信息到webdriver
	 * @param driver
	 * @param cookies
	 */
    public static void addCookies(WebDriver driver, List<Cookie> cookies){
        if(cookies == null) return;
        try{
	        for (Cookie cookie : cookies) {
	        	if("Set-Cookie".equals(cookie.getName())){
	        		String session = cookie.getValue().split(";")[0].trim();
	        		String sessionid = session.substring(session.indexOf("=") + 1);
	        		driver.manage().addCookie(new Cookie("JSESSIONID", sessionid));
	        	}
	        }
        }catch(Exception e){
        	e.printStackTrace();
    	}
        return;
    }
	
    /**
     * 隐式等待
     * @param dr
     * @param locator：元素
     * @param timeOut：超时时间
     * @param isDisplay：元素display属性
     * @return
     */
	public static boolean isElementPresent(WebDriver dr, final By locator, int timeOut, boolean isDisplay){
		WebDriverWait wait = new WebDriverWait(dr, timeOut);
		try{
			if(isDisplay)
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			else
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 隐式等待
	 * @param dr
	 * @param ele
	 * @param timeOut
	 * @param isDisplay
	 * @return
	 */
	public static boolean isElementPresent(WebDriver dr, final WebElement ele, int timeOut, boolean isDisplay){
		WebDriverWait wait = new WebDriverWait(dr, timeOut);
		try{
			if(isDisplay)
				wait.until(ExpectedConditions.visibilityOf(ele));
			else
				wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(ele)));
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 窗口跳转
	 * @param driver
	 * @param seconds：
	 * @param windowTitle：目标窗口title
	 * @return
	 */
	public static boolean switchToWindow(WebDriver driver, int seconds, String windowTitle) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		boolean flag = false;
		try {
			String winHandleBefore = driver.getWindowHandle();          
			//Switch to new window opened
			for(String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
				wait.until(ExpectedConditions.titleContains(driver.getTitle()));
				if (driver.getTitle().contains(windowTitle)) {			
					flag = true;
					System.out.println("Switch to window: " + windowTitle + " successfully!");
					// Close the original window
					driver.switchTo().window(winHandleBefore);
					driver.close();
					//Switch to new window opened  
					for (String winHandle1 : driver.getWindowHandles()) {					
						driver.switchTo().window(winHandle1);
						wait.until(ExpectedConditions.titleContains(driver.getTitle()));
					} 
					break;  
				} else{
					continue; 
					}
			}
		} catch (NoSuchWindowException e) {  
			System.out.println("Window: " + windowTitle + " cound not found!"+ e.fillInStackTrace());  
			flag = false;  
		}
		return flag;
	}
	
	/**
	 * Description: 处理弹出的安全警告框
	 * @param driver：操作对应的WebDriver
	 * @param option：对警告框进行操作，true表示确认，fail表示取消
	 * @return flag：返回是否出现警告框
	 */
	public static boolean dealPotentialAlert(WebDriver driver, boolean option) {
		boolean flag = false;
		try {
			Alert alert = driver.switchTo().alert();
			if (null == alert)
				throw new NoAlertPresentException();
			try {
				if (option) {
					alert.accept();
					System.out.println("Accept the alert: " + alert.getText());
				} else {
					alert.dismiss();
					System.out.println("Dismiss the alert: " + alert.getText());
				}
				flag = true;
			} catch (WebDriverException ex) {
				if (ex.getMessage().startsWith("Could not find"))
					System.out.println("There is no alert appear1!");
				else
					throw ex;
			}
		} catch (NoAlertPresentException e) {
			System.out.println("There is no alert appear2!");
		}
		return flag;
	}
	
	/**
	 * 使用js脚本处理
	 * @param funcName
	 * @param dr
	 */
	public static void dealWithJS(String funcName, WebDriver dr){
		JavascriptExecutor jsExecutor = (JavascriptExecutor)dr;
		jsExecutor.executeScript(funcName);
	}
	
	/**
	 * 模拟用户等待
	 */
	public static void waitForThink(){
		try {
			Thread.sleep(config.getThinkTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文本框输入文字
	 * @param dr
	 * @param locator
	 * @param input_Txt
	 */
	public static void sendKeys(WebDriver dr, By locator, String input_Txt){
		dr.findElement(locator).click();
		dr.findElement(locator).clear();
		dr.findElement(locator).sendKeys(input_Txt);
		waitForThink();
	}
	
	/**
	 * 文本框输入文字
	 * @param dr
	 * @param locator
	 * @param input_Txt
	 */
	public static void sendKeys(WebElement ele, String input_Txt){
		ele.click();
		ele.clear();
		ele.sendKeys(input_Txt);
		waitForThink();
	}
	
	/**
	 * 根据路径获取文本文字
	 * @param dr
	 * @param Locator
	 * @return
	 */
	public static String getTextByLocator(WebDriver dr, By Locator){
		WebElement ele = dr.findElement(Locator);
		return ele.getText();
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

	  public void waitForPageLoad(WebDriver driver) {
	      WebDriverWait wait = new WebDriverWait(driver, 30);
	      wait.until(isPageLoaded());
	  }
}

package com.yt.util.listeners;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.yt.base.AbstractWebDriver;
import com.yt.injection.InitPageObject;

public class CustomTestListener extends TestListenerAdapter {
	 String fileseperator = File.separator;
	 Logger logger = Logger.getLogger(CustomTestListener.class);
	 
	 
	  @Override
	  public void onTestStart(ITestResult tr) {
		  super.onTestStart(tr);
		  Method m = tr.getMethod().getConstructorOrMethod().getMethod();
		  Class<?> testClsName = m.getDeclaringClass();
		  try {
			//实例化PageObject对象
			new InitPageObject(testClsName.newInstance());
//			new InitPageObject(testClsName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		  logger.info(m.getName());
		  logger.info(testClsName);
	  }
	
	 /**
	  * 覆写onTestFailure方法，用例失败时执行
	  */
	 @Override
	 public void onTestFailure(ITestResult tr) {
		 super.onTestFailure(tr);
		 Object o = tr.getInstance();
		 WebDriver dr = ((AbstractWebDriver) o).getWebDriver();
		 Method method = tr.getMethod().getConstructorOrMethod().getMethod();
		 TakeScreenshotOnFailure tsf = method.getAnnotation(TakeScreenshotOnFailure.class);
		 if(null != tsf){
			 String testClassName = getClassName(tr.getInstance().toString().trim());
			 String testMethodName = tr.getName().toString().trim();
			 String screenshotName = testMethodName + ".png";
			 if(null != dr){
				 String image = System.getProperty("user.dir") + fileseperator
						 + "Screenshot" + fileseperator + "TestResult" + 
						 fileseperator + testClassName + fileseperator +
				 snapshot(dr, testClassName, screenshotName);
				 logger.info(image);
			 }
		 }
	 }
	 
	  public int getPassedTestNum(){
		  return getPassedTests().size();
	  }
	  
	  public int getFailedTestNum(){
		  return getFailedTests().size();
	  }
	  
	  public int getSkipedTestNum(){
		  return getSkippedTests().size();
	  }
	  
	  public int getTotalTestNum(){
		  return getPassedTests().size() + getFailedTests().size() + getSkippedTests().size();
	  }
	 
	 /**
	  * 从方法对象名中获取类名（com.yt.autotest.Login@7305830a）
	  * @param testName
	  * @return
	  */
	 private String getClassName(String testName) {
		String[] testClassNames = testName.split("\\.");
		int i = testClassNames.length - 1;
		String testClassName = testClassNames[i].substring(0, testClassNames[i].indexOf("@"));
		return testClassName;
	}

	 /**
	  * 失败截图
	  * @param dr
	  * @param testClassName
	  * @param snapshotName
	  * @return
	  */
	public String snapshot(WebDriver dr, String testClassName, String snapshotName){
		 File fileDir = new File("Screenshot" + fileseperator + "TestResult" + fileseperator + testClassName);
		 if(!fileDir.exists())
			 fileDir.mkdirs();

		 TakesScreenshot snapshot = (TakesScreenshot) dr;
		 File srcFile = snapshot.getScreenshotAs(OutputType.FILE);
		 File dstFile = new File("Screenshot" + fileseperator + "TestResult" + fileseperator + testClassName, snapshotName);
		 try {
			FileUtils.copyFile(srcFile, dstFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return snapshotName;
	 }
}

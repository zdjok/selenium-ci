package com.yt.util.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.yt.io.ConfigIO;
import com.yt.util.common.EnvUtil;
import com.yt.util.common.SmtpFactory;
import com.yt.util.common.SmtpUtil;

public class TestReportListener extends TestListenerAdapter {
	Logger logger = Logger.getLogger(TestReportListener.class);
	ConfigIO config = ConfigIO.getInstance();
	BufferedWriter bw;
	File testReportDir, testReportFile;
	StringBuilder html = new StringBuilder();
	
	private static final String SUBJECT = "Web UI AutoTest Report";
	
	@Override
	public void onStart(ITestContext context) {
		logger.info("onStart....");
		testReportDir = new File("test-output/htmlreport");
		if(!testReportDir.exists()){
			testReportDir.mkdirs();
		}
		testReportFile = new File(testReportDir, "htmlreport.html");
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testReportFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		html.append("<html><title>Web UI Auto Test</title>")
			.append("<body style='font-family:'Microsoft YaHei'; margin-left:20; width:auto; height:auto'>");
		html.append("<div style=' margin-top:10px'>");
		html.append("<p style='font-weight:bold;font-size:28;'>Web UI自动化测试报告</p>")
			.append("<p>")
			.append("<span style='font-weight:bold'>Total：#TOTAL#</span>， ")
			.append("<span style='font-weight:bold; color:green'>PASS：#PASS#</span>， ")
			.append("<span style='font-weight:bold; color:red'>FAIL：#FAIL#</span>， ")
			.append("<span style='font-weight:bold; color:gray'>NT：#NT#</span>")
			.append("</p>");
		html.append("<div>")
			.append("<p style='font-weight:bold'>测试机器：</p>")
			.append("<p><span style='font-weight:bold'>IP：</span>").append(EnvUtil.getIpAddress()).append("</p>")
			.append("<p><span style='font-weight:bold'>Machine：</span>").append(EnvUtil.getOSName()).append("，")
			.append(EnvUtil.getOSUserName()).append("</p>")
			.append("</div>");
		html.append("</div>");
		html.append("<br/>");
		html.append("<p><span style='font-weight:bold; background-color:gray'>测试结果：</span></p>");
		html.append("<table border=1 cellspacing=0>")
			.append("<tr>")
			.append("<th width=150>接口模块</th>")
			.append("<th width=400>测试用例名称</th>")
			.append("<th width=150>测试结果</th>")
			.append("</tr>");
	}

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		super.onTestSuccess(result);
		html.append("<tr>").append("<td align=center>")
		.append(result.getMethod().getConstructorOrMethod().getMethod()
				.getDeclaringClass().getSimpleName()).append("</td>");
		html.append("<td>").append(result.getMethod().getConstructorOrMethod()
				.getMethod().getName()).append("</td>");
		html.append("<td align=center style='font-weight:bold ; color:green'>").append("PASS").append("</td></tr>");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		html.append("<tr>").append("<td align=center>")
		.append(result.getMethod().getConstructorOrMethod().getMethod()
				.getDeclaringClass().getSimpleName()).append("</td>");
		html.append("<td>").append(result.getMethod().getConstructorOrMethod()
				.getMethod().getName()).append("</td>");
		html.append("<td align=center style='font-weight:bold ; color:red'>").append("FAIL").append("</td></tr>");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		super.onTestSkipped(result);
		html.append("<tr>").append("<td align=center>")
		.append(result.getMethod().getConstructorOrMethod().getMethod()
				.getDeclaringClass().getSimpleName()).append("</td>");
		html.append("<td>").append(result.getMethod().getConstructorOrMethod()
				.getMethod().getName()).append("</td>");
		html.append("<td align=center style='font-weight:bold ; color:gray'>").append("NT").append("</td></tr>");
	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("onFinish....");
		html.append("</table>");
/*		html.append("<div style='font-family:Times New Roman ;position:fixed; bottom:20px'>")
			.append("<p>Any question please contact me freely.</p>")
			.append("Email：<a href='mailto:").append(config.getFrom()).append("'>"+config.getFrom()  + "</a></div>");*/
		html.append("</body>").append("</html>");
		String htmlStr = html.toString();
		htmlStr = htmlStr.replace("#TOTAL#", String.valueOf(getAllTestMethods().length))
						 .replace("#PASS#", String.valueOf(getPassedTests().size()))
						 .replace("#FAIL#", String.valueOf(getFailedTests().size()))
						 .replace("#NT#", String.valueOf(getSkippedTests().size()));
		try {
			bw.write(htmlStr);
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("Pass：" + getPassedTests().size());
		logger.info("Fail：" + getFailedTests().size());
		logger.info("Skiped: " + getSkippedTests().size());
		logger.info("Total: " + getAllTestMethods().length);
		
		SmtpUtil smtp = SmtpFactory.getSmtpUtil(config.getSmtpHost(), config.getFrom(), config.getMailPassword());
		try {
			smtp.sendEmail(config.getRecipient(), SUBJECT, htmlStr, 
					new File(System.getProperty("user.dir") + "/test-output/htmlreport/htmlreport.html"));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

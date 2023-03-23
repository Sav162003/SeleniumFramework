package com.feb16.utility;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.feb16.salesforce.BaseTest;

public class TestEventListenerUtility implements ITestListener{
	protected static ExtentReportsUtility extentreport = null;
	protected WebDriver driver;

	@Override
	public void onTestStart(ITestResult result) {
		//Before every test
		extentreport.startSingleTestReport(result.getMethod().getMethodName());
				
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		//extentreport.logTestPassed("ErrorLogin"); this is the hard coded way of getting the 
		extentreport.logTestPassed(result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		extentreport.logTestFailed(result.getMethod().getMethodName());
		BaseTest ob = new BaseTest();
		driver = ob.returnDriverInstance();
		//String path = BaseTest.getPageScreenshot(driver);
		//extentreport.logTestFailedScreenshot(path);
		extentreport.logException(result.getThrowable());
	}
 
	@Override
	public void onStart(ITestContext context) {
		//Starting the report
		extentreport = ExtentReportsUtility.getInstance();
		extentreport.startExtentReport();
	
	}

	@Override
	public void onFinish(ITestContext context) {
		extentreport.endReport();
	}
	
	}

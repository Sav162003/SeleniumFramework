package com.feb16.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
public class ExtentReportsUtility {

	public static ExtentReports report;
	public static ExtentSparkReporter spartReporter;
	public static ExtentTest testLogger;


	//Singleton class 
	private static ExtentReportsUtility extentObject;
	private ExtentReportsUtility() {

	} public static ExtentReportsUtility getInstance()
	{ if(extentObject==null) {
		extentObject=new ExtentReportsUtility(); 
		} return extentObject; }


	public void startExtentReport() {
		spartReporter = new ExtentSparkReporter(Constants.SPARKS_HTML_REPORT_PATH);
		report= new ExtentReports();
		report.attachReporter(spartReporter);

		report.setSystemInfo("Host Name", "Salesforce");
		report.setSystemInfo("Environment", "Automation");
		report.setSystemInfo("Tester", "Savitha");

		spartReporter.config().setDocumentTitle("Test Execution Report");
		spartReporter.config().setReportName("Salesforce TestScripts");
		spartReporter.config().setTheme(Theme.DARK);
	}

	public void startSingleTestReport(String testCase_Name) {
		testLogger = report.createTest(testCase_Name);
	}

	public void logTestInfo(String text) {
		testLogger.info(text);
	}
	public void logTestPassed(String testcaseName) {
		testLogger.pass(MarkupHelper.createLabel("testcaseName"+ "is completed",ExtentColor.PURPLE));

	}
	public void logTestFailed(String testcaseName) {
		testLogger.fail(MarkupHelper.createLabel(testcaseName+ "is not completed",ExtentColor.RED));

	}
	public void logTestFailedScreenshot(String path) {
		testLogger.addScreenCaptureFromPath(path);
	}   
	public void logException(Throwable e) {
		testLogger.log(Status.FAIL,e);	

	}
	public void endReport() {
		report.flush();
	}

}

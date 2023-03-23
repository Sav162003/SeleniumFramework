package com.feb16.salesforce;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hpsf.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.feb16.utility.Constants;
import com.feb16.utility.ExtentReportsUtility;
import com.feb16.utility.PropertiesUtility;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest { 

	protected static WebDriver driver= null;
	protected static Logger logger = null;
	protected static ExtentReportsUtility extentreport = ExtentReportsUtility.getInstance();

	@BeforeTest
	public void set_UpBeforeTest() {
		//extentreport.logTestInfo("beforeTest Method has Started");
		System.out.println("Inside @Before Test Method");
		logger=LogManager.getLogger(BaseTest.class.getName());

	}

	@BeforeMethod
	@Parameters("browsername")
	public void BeforeTestMethod(@Optional("chrome")String browserName,Method method) throws IOException{

		logger.info("Testcase executed : "+method.getName());
		extentreport.logTestInfo("Testcase executed : "+method.getName());

		PropertiesUtility propertiesutil = new PropertiesUtility();
		propertiesutil.loadFile("applicationData.Properties");
		String url = propertiesutil.getPropertyValue("url");
		if(url==null) {
			System.out.println("Url not found in properties file");
			return;
		}
		GetDriverInstance(browserName);
		goToUrl(url);
	}

	@AfterMethod
	public void AfterTestMethod() throws IOException {
		//logger.info("Browser closed");
		extentreport.logTestInfo("Browser closed");
		driver.close();

	}
	public static void GetDriverInstance(String browsername) {

		switch(browsername) {
		case "firefox" :
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;

		case "Edge" :
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			break;

		case "chrome" : 
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		default:
			System.out.println("Not a valid browsername");
			break;
		}
	}
	public static void goToUrl(String url) {
		driver.get(url);
		waitUntilPageLoads();

		//public static void GotoUrl(String url) {
		//logger.info("Url launched");
		//extentreport.logTestInfo("Url launched");
		
	}


	public static void closeBrowser() { 
		//logger.info("Closing browser");
		driver.close(); }


	public static void enterText(WebElement element,String text,String name) {
		if(element.isDisplayed()) {
			clear(element, name);
			element.sendKeys(text);
			logger.info("Text entered in :" +name+  " field");
			extentreport.logTestInfo("Text entered in :" +name+  " field");
		}
		else {
			logger.info("Text not entered in :" +name+  " field");
			extentreport.logTestInfo("Text not entered in :" +name+  " field");
		}
		driver.getTitle();
	}

	public WebDriver returnDriverInstance() {
		return driver;
	}
	public static void GetDriver(String browserName) {

		switch(browserName) {
		case "Edge" : WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		break;

		case "chrome" : WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		break;
		default:
			logger.info("Not a valid browsername");
			extentreport.logTestInfo("Not a valid browsername");
		}

	}
	

	public static void sendKeysElement(WebElement element,String objName) throws IOException {
		PropertiesUtility propertiesutil = new PropertiesUtility();
		propertiesutil.loadFile("applicationData.Properties");
		String emailName = propertiesutil.getPropertyValue("gmail.id");
		if(element.isDisplayed()) {
			element.sendKeys("gmail.id");	
			logger.info("Forgotuser element displayed: " +objName);
			extentreport.logTestInfo("Forgotuser element displayed: " +objName);
		}
		else {
			logger.info("Forgotuser element not displayed: " +objName);
			extentreport.logTestInfo("Forgotuser element not displayed: " +objName);
		}
	}

	public static void verifyPageTitle(WebDriver driver,String expected) {
		logger.info(" Page Title verified");
		String actual = driver.getTitle();
		logger.info("Page Title: "+actual);
		if (actual.equalsIgnoreCase(expected)) {
			logger.info("Page Title matches the expected Title :" +expected);
			extentreport.logTestInfo("Page Title matches the expected Title :" +expected);
		} else {
			logger.info("Page Title does not match the expected Title");
			extentreport.logTestInfo("Page Title does not match the expected Title");
		}
	}

	public static void waitUntilPageLoads() {
		driver.manage().timeouts().pageLoadTimeout(60,TimeUnit.SECONDS);

	}

	public static String getTextWebElement(WebElement element,String name) {
		if(element.isDisplayed()) {
			return element.getText();
		}
		else {
			logger.info("Webelement"  +name+ " is not displayed");
			//extentreport.logTestInfo("Webelement"  +name+ " is not displayed");
			return null;
		}
	}


	public static void clear(WebElement element, String name) {
		if(element.isDisplayed()) {
			element.clear();
			logger.info("Element Cleared : " +name);
			extentreport.logTestInfo("Element Cleared" +name);
		}
		else {
			logger.info("Element Not Cleared : " +name);
			extentreport.logTestInfo("Element Not Cleared" +name);
		}

	}
	public static void click(WebElement element, String name) {
		if(element.isDisplayed()) {
			element.click();
			logger.info("Element Clicked : " +name);
			extentreport.logTestInfo("Element Clicked : " +name);
		}
		else {
			logger.info("Element Not Clicked : " +name);
			extentreport.logTestInfo("Element Not Clicked : " +name);
		}
	}
		public static void WaitUntilElementVisible(WebElement element,String name) {

			if(element.isDisplayed()) {
				clear(element,name);
				click(element,name);
				logger.info("Element Visible:"+name);
			}
			else {
				logger.info("Element not visible:" +name);
			}
		}

	public static void LoginFromBase() throws IOException, InterruptedException  {
		logger.info("Inside login Base");
		//extentreport.logTestInfo("Inside login Base");
		PropertiesUtility propertiesutil = new PropertiesUtility();
		propertiesutil.loadFile("applicationData.Properties");
		String username = propertiesutil.getPropertyValue("login.valid.userid");
		String password = propertiesutil.getPropertyValue("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		enterText(userName,username,"usernameEle");

		WebElement pwd = driver.findElement(By.id("password"));
		enterText(pwd,password,"passwordEle"); 
		driver.findElement(By.id("Login")).click();

		String home = driver.getTitle();
		verifyPageTitle(driver, home);

	}
	public static void AccountPlusOpp() throws InterruptedException {
		logger.info("Inside AccountPlus page");
		extentreport.logTestInfo("Inside AccountPlus page");
		WebElement accountPlus = driver.findElement(By.xpath("//*[@id=\"AllTab_Tab\"]/a/img"));
		accountPlus.click();
		waitUntilPageLoads();

		String allTab = driver.getTitle();
		verifyPageTitle(driver, allTab);
		Thread.sleep(5000);

		WebElement opportunity = driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[3]/div[2]/table/tbody/tr[12]/td[2]/a"));
		//*[@id="bodyCell"]/div[3]/div[2]/table/tbody/tr[12]/td[2]/a
		opportunity.click();
		waitUntilPageLoads();
		NoThanks();

		String oppoPage = driver.getTitle();
		verifyPageTitle(driver, oppoPage);


	}
	public static void AccountPlusLeads() throws InterruptedException {
		logger.info("Inside AccountPlus page");
		extentreport.logTestInfo("Inside AccountPlus page");
		WebElement accountPlus = driver.findElement(By.xpath("//*[@id=\"AllTab_Tab\"]/a/img"));
		accountPlus.click();
		waitUntilPageLoads();

		String allTab = driver.getTitle();
		verifyPageTitle(driver, allTab);
		Thread.sleep(3000);

		WebElement leads = driver.findElement(By.xpath("//*[@id=\"bodyCell\"]/div[3]/div[2]/table/tbody/tr[4]/td[2]/a"));
		leads.click();

		String leadsHomePage = driver.getTitle();
		verifyPageTitle(driver, leadsHomePage);
		Thread.sleep(3000);
		NoThanks();


	}
	public static void NoThanks() {
		logger.info("No Thanks cleared");
		extentreport.logTestInfo("No Thanks cleared");
		WebElement noThanks = driver.findElement(By.xpath("//*[@id=\"lexNoThanks\"]"));
		noThanks.click();
		WebElement close = driver.findElement(By.xpath("//*[@id=\"tryLexDialogX\"]"));
		close.click();
	}


	public static void AccountTab() throws InterruptedException {
		logger.info("Inside Account page");
		extentreport.logTestInfo("Inside Account page");
		WebElement accounts = driver.findElement(By.xpath("//li[@id='Account_Tab']"));
		accounts.click();
		waitUntilPageLoads();

		String accTab = driver.getTitle();
		verifyPageTitle(driver, accTab);
		Thread.sleep(5000);	

		NoThanks();

	}
	public static void ContactTab() throws InterruptedException {
		logger.info("Inside Contact page");
		extentreport.logTestInfo("Inside Contact page");
		WebElement contact = driver.findElement(By.xpath("//a[normalize-space()='Contacts']"));
		contact.click();
		Thread.sleep(5000);
		NoThanks();

		String contactTab = driver.getTitle();
		verifyPageTitle(driver, contactTab);
	}
	/*
	 * public static String getPageScreenshot() {
	 * //logger.info("Screenshot generated");
	 * //extentreport.logTestInfo("Screenshot generated");; String date = new
	 * SimpleDateFormat("yyyy_MM_dd").format(new Date()); String curDir =
	 * System.getProperty("user.dir"); TakesScreenshot screenShot =
	 * (TakesScreenshot)driver; File image =
	 * screenShot.getScreenshotAs(OutputType.FILE); File location = new
	 * File(Constants.SCREENSHOT_DIR_PATH+date+".png"); try{ FileHandler.copy(image,
	 * location); } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return location.getAbsolutePath(); } public static
	 * String getPageScreenshot(WebDriver driver) {
	 * //logger.info("Screenshot generated");
	 * extentreport.logTestInfo("Screenshot generated");; String date = new
	 * SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date()); String curDir =
	 * System.getProperty("user.dir"); TakesScreenshot screenShot =
	 * (TakesScreenshot)driver; File image =
	 * screenShot.getScreenshotAs(OutputType.FILE); File location = new
	 * File(Constants.SCREENSHOT_DIR_PATH+date+".png"); try{ FileHandler.copy(image,
	 * location); } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return location.getAbsolutePath();
	 * 
	 * }
	 */

}
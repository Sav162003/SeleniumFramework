package com.feb16.salesforce;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.feb16.utility.PropertiesUtility;

import io.reactivex.rxjava3.functions.Action; 

@Listeners(com.feb16.utility.TestEventListenerUtility.class)
public class sfdcAutomation extends BaseTest {
	@Test
	public static void ErrorLogin() throws IOException, InterruptedException {

		//logger.info("Inside Error Login Method");
		extentreport.logTestInfo("Inside Error Login Method");
		PropertiesUtility propertiesutil = new PropertiesUtility();
		propertiesutil.loadFile("applicationData.Properties");
		String username = propertiesutil.getPropertyValue("login.invalid.userid");
		String password = propertiesutil.getPropertyValue("login.invalid.password");

		String errorMsg = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";

		waitUntilPageLoads();
		WebElement userName = driver.findElement(By.id("username"));
		WaitUntilElementVisible(userName, "usernameEle");
		enterText(userName,username,"usernameEle");
		Thread.sleep(3000);

		WebElement pwd = driver.findElement(By.id("password"));
		enterText(pwd,password,"passwordEle");

		driver.findElement(By.id("Login")).click();
		waitUntilPageLoads();

		WebElement error  = driver.findElement(By.id("error"));
		String actual = getTextWebElement(error,"error element");
		Assert.assertEquals(actual, errorMsg);

		/*
		 * if(actual.equalsIgnoreCase(expected)) {
		 * logger.info("Error login script passed");
		 * extentreport.logTestPassed("ErrorLogin"); } else {
		 * logger.info("Error login script failed");
		 * extentreport.logTestFailed("ErrorLogin"); }
		 */
	}
	@Test

	public static void LaunchPage() throws InterruptedException, IOException {
		logger.info("Inside login page");
		extentreport.logTestInfo("Inside login page");
		LoginFromBase();

	}
	@Test

	public static void RememberUn() throws IOException, InterruptedException {
		logger.info("Inside RememberUn Method");
		extentreport.logTestInfo("Inside RememberUn Method");
		PropertiesUtility propertiesutil = new PropertiesUtility();
		propertiesutil.loadFile("applicationData.Properties");
		String username = propertiesutil.getPropertyValue("login.valid.userid");
		String password = propertiesutil.getPropertyValue("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		enterText(userName,username,"usernameEle");

		WebElement pwd = driver.findElement(By.id("password"));
		enterText(pwd,password,"passwordEle"); 

		WebElement checkbox = driver.findElement(By.id("rememberUn"));
		click(checkbox, "rememberme");
		waitUntilPageLoads();
		driver.findElement(By.id("Login")).click();

		String home = driver.getTitle();
		verifyPageTitle(driver, home);

		WebElement user = driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]"));
		click(user, "uSer");
		waitUntilPageLoads();
		WebElement logout = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[5]"));
		waitUntilPageLoads();
		logout.click();

	}
	@Test

	public static void Forgotpwd() throws IOException  {
		logger.info("Inside Forgot password Method");
		extentreport.logTestInfo("Inside Forgot password Method");

		WebElement forgotpwd = driver.findElement(By.id("forgot_password_link"));
		click(forgotpwd, "forgotpassword");

		String forgotLink = driver.getTitle();
		verifyPageTitle(driver, forgotLink);

		WebElement forgotuser = driver.findElement(By.id("un"));
		sendKeysElement(forgotuser,"emailName");
		waitUntilPageLoads();
		driver.findElement(By.id("continue")).click();

		String checkEmail = driver.getTitle();
		verifyPageTitle(driver, checkEmail);

	}
	@Test

	public static void wrongUser() throws IOException {
		logger.info("Inside Wrong user Method");
		extentreport.logTestInfo("Inside Wrong user Method");

		PropertiesUtility propertiesutil = new PropertiesUtility();
		propertiesutil.loadFile("applicationData.Properties");
		String username = propertiesutil.getPropertyValue("login.invalid.userid");
		String password = propertiesutil.getPropertyValue("login.invalid.password");

		WebElement userName = driver.findElement(By.id("username"));
		enterText(userName,username,"usernameEle");

		WebElement pwd = driver.findElement(By.id("password"));
		enterText(pwd,password,"passwordEle"); 

		driver.findElement(By.id("Login")).click();

	}
	@Test

	public static void UserMenu() throws IOException, InterruptedException {
		logger.info("Inside User Menu Method");
		extentreport.logTestInfo("Inside User Menu Method");

		LoginFromBase();
		WebElement userName = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
		click(userName, "uSernAme");
		waitUntilPageLoads();

		WebElement logout =driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		click(logout, "userclick");
		//driver.close();

	}
	@Test
	public static void MyProfile() throws IOException, InterruptedException, AWTException {
		logger.info("Inside My Profile Method");
		extentreport.logTestInfo("Inside My Profile Method");

		LoginFromBase();
		WebElement userName = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
		click(userName, "usernAme");
		waitUntilPageLoads();

		WebElement myprofile = driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
		click(myprofile, "myProfile");
		waitUntilPageLoads();

		String userAcc= driver.getTitle();
		verifyPageTitle(driver, userAcc);
		Thread.sleep(3000);

		WebElement editprofile = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//img[@title='Edit Profile']"));
		click(editprofile, "editProfile");
		waitUntilPageLoads();
		//a[@class='contactInfoLaunch editLink']//img[@title='Edit Profile']

		WebElement iframe =	driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
		driver.switchTo().frame(iframe);

		WebElement about = driver.findElement(By.xpath("//a[contains(text(),'About')]"));
		about.click();

		WebElement lastname = driver.findElement(By.id("lastName"));
		click(lastname, "lastName");
		clear(lastname, "clearLastName");
		String lnameupdated = "M";
		lastname.sendKeys(lnameupdated);

		WebElement saveall = driver.findElement(By.xpath("//*[@id=\"TabPanel\"]/div/div[2]/form/div/input[1]"));
		saveall.click();

		WebElement post = driver.findElement(By.xpath("//a[@id='publisherAttachTextPost']"));
		post.click();
		waitUntilPageLoads();

		//iframe[@title='Rich Text Editor, publisherRichTextEditor']
		WebElement postFrame = driver.findElement(By.xpath("//iframe[@title='Rich Text Editor, publisherRichTextEditor']"));
		driver.switchTo().frame(postFrame);
		waitUntilPageLoads();

		WebElement postText = driver.findElement(By.xpath("/html[1]/body[1]"));
		click(postText, "textPosted");
		postText.sendKeys("Hello, Selenium Salesforce Automation");
		logger.info("Message posted");

		driver.switchTo().parentFrame();

		WebElement shareButton = driver.findElement(By.id("publishersharebutton"));
		click(shareButton, "Share");
		waitUntilPageLoads();

		WebElement file =  driver.findElement(By.xpath("//span[normalize-space()='File']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();",file);
		waitUntilPageLoads();

		WebElement uploadFile = driver.findElement(By.xpath("//a[@id='chatterUploadFileAction']"));
		uploadFile.click(); 
		WebElement chooseFile = driver.findElement(By.xpath("//input[@id='chatterFile']"));
		chooseFile.sendKeys("C:\\Users\\savi1\\OneDrive\\Desktop\\file.txt");

		WebElement shareFile = driver.findElement(By.id("publishersharebutton"));
		shareFile.click();
		waitUntilPageLoads();

		WebElement uploadPhoto = driver.findElement(By.xpath("//span[@id='displayBadge']"));
		Actions photoImage = new Actions(driver);
		photoImage.moveToElement(uploadPhoto).click().perform();
		waitUntilPageLoads();
		WebElement addPhoto = driver.findElement(By.xpath("//a[@id='uploadLink']"));
		addPhoto.click();

		WebElement form =  driver.findElement(By.xpath("///div[@id='uploadPhoto']")); 
		Actions forms = new Actions(driver);
		forms.moveToElement(form).build().perform();


		WebElement iFrame = driver.findElement(By.xpath("//iframe[@id='uploadPhotoContentId']"));
		driver.switchTo().frame(iFrame);

		//*[@id="j_id0:uploadFileForm"] 

		WebElement photo = driver.findElement(By.xpath("//*[@id=\"j_id0:uploadFileForm:uploadInputFile\"]"));
		JavascriptExecutor search = (JavascriptExecutor)driver;
		search.executeScript("arguments[0].click();",photo);
		StringSelection selection = new StringSelection("C:\\Users\\savi1\\OneDrive\\Desktop\\chipn.png");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		logger.info("Upload photo opened");
		Thread.sleep(3000);


		//*[@id="j_id0:j_id7:save"]
		//*[@id="j_id0:uploadFileForm:save"]
		WebElement savePhoto = driver.findElement(By.id("j_id0:uploadFileForm:save"));
		JavascriptExecutor executorsave = (JavascriptExecutor)driver;
		executorsave.executeScript("arguments[0].click", savePhoto);
		waitUntilPageLoads();

		WebElement saveFrame = driver.findElement(By.xpath("//iframe[@id='uploadPhotoContentId']"));
		driver.switchTo().frame(saveFrame);
		//<input id="j_id0:j_id7:save" type="submit" name="j_id0:j_id7:save" value="Save" style="" class="btn saveButton" xpath="1">
		WebElement saveButton = driver.findElement(By.xpath("//input[@class='btn']"));
		Actions action1 = new Actions(driver);
		action1.moveToElement(saveButton).click();

		logger.info("Profile pic uploaded");
		waitUntilPageLoads();

	}
	@Test
	public static void MySettings() throws IOException, InterruptedException  {
		logger.info("Inside My Settings Method");
		extentreport.logTestInfo("Inside My Settings Method");

		LoginFromBase();
		waitUntilPageLoads();
		WebElement user = driver.findElement(By.xpath("//*[@id=\"userNavLabel\"]"));
		click(user,"uSer");
		waitUntilPageLoads();

		WebElement mySettings = driver.findElement(By.xpath("//*[@id=\"userNav-menuItems\"]/a[2]"));
		click (mySettings, "mysEttings");
		waitUntilPageLoads();

		String userSettings= driver.getTitle();
		verifyPageTitle(driver, userSettings);
		Thread.sleep(3000);

		WebElement personal = driver.findElement(By.xpath("//span[@id='PersonalInfo_font']"));
		click(personal,"perSonal");
		waitUntilPageLoads();

		WebElement logHistory = driver.findElement(By.xpath("//span[@id='LoginHistory_font']"));
		click(logHistory,"loghisTory");
		String loginHistory= driver.getTitle();
		verifyPageTitle(driver, loginHistory);

		WebElement download = driver.findElement(By.xpath("//a[contains(text(),'Download login history for last six months, includ')]"));
		click(download, "downLoad");
		System.out.println("The csv file is downloaded for the login history");

		WebElement dislay = driver.findElement(By.id("DisplayAndLayout"));
		click(dislay, "disPlay");
		waitUntilPageLoads();
		WebElement custMyTabs = driver.findElement(By.xpath("//span[@id='CustomizeTabs_font']"));
		click(custMyTabs,"customTabs");
		waitUntilPageLoads();

		String custTab= driver.getTitle();
		verifyPageTitle(driver, custTab);

		WebElement custom = driver.findElement(By.id("p4"));
		Select ob = new Select(custom);
		ob.selectByVisibleText("Salesforce Chatter");
		WebElement tabs = driver.findElement(By.id("duel_select_0"));
		Select tab = new Select(tabs);
		tab.selectByVisibleText("Reports");
		WebElement add = driver.findElement(By.xpath("//*[@id=\"duel_select_0_right\"]/img"));
		add.click();

		WebElement emailButton = driver.findElement(By.id("EmailSetup_font"));
		click(emailButton,"email");

		WebElement myEmailSet = driver.findElement(By.id("EmailSettings_font"));
		myEmailSet.click();
		waitUntilPageLoads();
		String emailpg= driver.getTitle();
		verifyPageTitle(driver, emailpg);

		WebElement emailName = driver.findElement(By.xpath("//input[@id='sender_name']"));
		clear(emailName,"clearEmail");
		emailName.sendKeys("Tom Cruise");
		WebElement emailadd = driver.findElement(By.xpath("//input[@id='sender_email']"));
		clear(emailadd,"clearEmailAdd");
		sendKeysElement(emailadd, "emailId");
		waitUntilPageLoads();
		String emailpg2= driver.getTitle();
		verifyPageTitle(driver, emailpg2);

		WebElement radioBcc = driver.findElement(By.id("auto_bcc1"));
		radioBcc.click();
		WebElement save = driver.findElement(By.xpath("//td[@id='bottomButtonRow']|//input[@title = 'Save']"));
		click(save, "saveEmail");
		System.out.println("Settings successfully saved");
		waitUntilPageLoads();

		WebElement calReminder = driver.findElement(By.id("CalendarAndReminders_font"));
		click(calReminder, "reminder");
		waitUntilPageLoads();

		WebElement activityreminder = driver.findElement(By.id("Reminders_font"));
		click(activityreminder, "activityReminder");
		String reminder= driver.getTitle();
		verifyPageTitle(driver, reminder);

		WebElement reminderTest = driver.findElement(By.id("testbtn"));
		click(reminderTest, "testButton");
		logger.info("Reminders popup displayed");

		WebElement dismissAll = driver.findElement(By.className("btn"));
		logger.info("Inside Reminder Pop-up");
		Actions action = new Actions(driver);
		action.moveToElement(dismissAll);

	}
	@Test
	public static void DevConsole() throws IOException, InterruptedException {
		logger.info("Inside Developer console Method");
		extentreport.logTestInfo("Inside Developer console Method");

		LoginFromBase();
		WebElement user = driver.findElement(By.xpath("//div[@id='userNavButton']"));
		user.click();

		WebElement console = driver.findElement(By.xpath("//a[contains(text(),'Developer Console')]"));
		console.click();

		String devConsole = driver.getTitle();
		verifyPageTitle(driver, devConsole);

		Actions action = new Actions(driver);
		action.moveToElement(console);

		String mainWindow =driver.getWindowHandle();
		Set<String>allWindowHandle=driver.getWindowHandles(); 
		for(String handle:allWindowHandle) { 
			if(!mainWindow.equals(handle)) {
				driver.switchTo().window(handle); 
				break; 
			}
		}

	}
	@Test
	public static void Logout() throws IOException, InterruptedException  {
		logger.info("Inside Logout Method");
		extentreport.logTestInfo("Inside Logout Method");

		LoginFromBase();
		WebElement user = driver.findElement(By.xpath("//div[@id='userNavButton']"));
		user.click();

		WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		logout.click();


		String salesLogout = driver.getTitle();
		verifyPageTitle(driver, salesLogout);
		waitUntilPageLoads();

	}
	@Test
	public static void CreateAcc() throws  AWTException, IOException, InterruptedException {
		logger.info("Inside Create Account Method");
		extentreport.logTestInfo("Inside Create Account Method");
		LoginFromBase();
		AccountTab();

		WebElement newAccount = driver.findElement(By.xpath("//*[@id=\"hotlist\"]/table/tbody/tr/td[2]/input"));
		newAccount.click();

		String accEdit = driver.getTitle();
		verifyPageTitle(driver, accEdit);

		WebElement accName = driver.findElement(By.id("acc2"));
		accName.click();
		accName.clear();
		accName.sendKeys("Shreeya");
		waitUntilPageLoads();


		WebElement type = driver.findElement(By.id("acc6"));
		type.click();
		Robot ro = new Robot();
		ro.keyPress(KeyEvent.VK_DOWN);
		ro.keyPress(KeyEvent.VK_T);
		ro.keyPress(KeyEvent.VK_ENTER);
		ro.mouseWheel(4);
		waitUntilPageLoads();

		WebElement custPriority = driver.findElement(By.id("00NDn00000SjEIK"));
		custPriority.click();
		Robot high = new Robot();
		high.keyPress(KeyEvent.VK_DOWN);
		high.keyPress(KeyEvent.VK_H);
		high.keyPress(KeyEvent.VK_ENTER);
		waitUntilPageLoads();


		WebElement save = driver.findElement(By.xpath("//*[@id=\"topButtonRow\"]/input[1]"));
		save.click();

		String userAcc= driver.getTitle();
		verifyPageTitle(driver, userAcc);


	}
	@Test
	public static void CreateView() throws IOException, InterruptedException {
		logger.info("Inside Create View Method");
		extentreport.logTestInfo("Inside Create View Method");
		LoginFromBase();
		AccountTab();

		WebElement newView = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
		newView.click();

		String  createView= driver.getTitle();
		verifyPageTitle(driver, createView);


		WebElement viewName = driver.findElement(By.id("fname"));
		viewName.click();
		viewName.sendKeys("Savi_s27");
		WebElement uniqueName = driver.findElement(By.id("devname"));
		uniqueName.click();
		WebElement save = driver.findElement(By.xpath("//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[2]/form[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/input[1]"));
		save.click();

		waitUntilPageLoads();

		String  accView= driver.getTitle();
		verifyPageTitle(driver, accView);

	}
	@Test
	public static void EditName() throws AWTException, IOException, InterruptedException {

		logger.info("Inside Edit name Method");
		extentreport.logTestInfo("Inside Edit name Method");
		LoginFromBase();
		AccountTab();

		WebElement viewName = driver.findElement(By.id("fcf"));
		viewName.click();
		//String expected = "Savi_s26";

		WebElement editName = driver.findElement(By.xpath("//a[normalize-space()='Edit']"));
		editName.click();
		waitUntilPageLoads();
		String editView = driver.getTitle();
		verifyPageTitle(driver, editView);

		WebElement newName = driver.findElement(By.id("fname"));
		newName.click();
		newName.clear();
		newName.sendKeys("Savi_s28");
		waitUntilPageLoads();

		WebElement field = driver.findElement(By.id("fcol1"));
		field.click();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_ENTER);

		WebElement operator = driver.findElement(By.xpath("//select[@id='fop1']"));
		operator.click();
		Select ob = new Select(operator);
		ob.selectByVisibleText("contains");

		WebElement value = driver.findElement(By.id("fval1"));
		value.click();
		value.clear();
		value.sendKeys("a");
		waitUntilPageLoads();

		WebElement save = driver.findElement(By.xpath("//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[2]/form[1]/div[3]/table[1]/tbody[1]/tr[1]/td[2]/input[1]"));
		save.click();

		String accHome = driver.getTitle();
		verifyPageTitle(driver, accHome);
		waitUntilPageLoads();

	}
	@Test
	public static void MergeAcc() throws IOException, InterruptedException {
		logger.info("Inside Merge accounts Method");
		extentreport.logTestInfo("Inside Merge accounts Method");
		LoginFromBase();
		AccountTab();

		WebElement mergeAcc = driver.findElement(By.xpath("//a[contains(text(),'Merge Accounts')]"));
		mergeAcc.click();	

		String accMerge = driver.getTitle();
		verifyPageTitle(driver, accMerge);

		WebElement searchName = driver.findElement(By.id("srch"));
		searchName.click();
		searchName.sendKeys("Shreeya");

		WebElement findAcc = driver.findElement(By.xpath("//input[@value ='Find Accounts' and @name ='srchbutton']"));
		findAcc.click();

		WebElement selectAcc1 = driver.findElement(By.id("cid0"));
		selectAcc1.click();

		WebElement selectAcc2 = driver.findElement(By.id("cid1"));
		selectAcc2.click();
		WebElement selectAcc3 = driver.findElement(By.id("cid2"));
		selectAcc2.click();



		WebElement mergeNext = driver.findElement(By.xpath("//div[@class='pbBottomButtons']//input[@title='Next']"));
		mergeNext.click();
		WebElement merge = driver.findElement(By.xpath("//input[@title = 'Merge']"));
		merge.click();
		waitUntilPageLoads();
		Alert popup = driver.switchTo().alert();
		popup.accept();

	}
	@Test
	public static void AccReport() throws AWTException, IOException, InterruptedException {
		logger.info("Inside Account reports Method");
		extentreport.logTestInfo("Inside Account reports Method");
		LoginFromBase();
		AccountTab();

		WebElement accReport = driver.findElement(By.xpath("//a[contains(text(),'Accounts with last activity > 30 days')]"));
		//a[contains(text(),'Accounts with last activity > 30 days')]
		accReport.click();	

		String unsavedReport = driver.getTitle();
		verifyPageTitle(driver, unsavedReport);

		waitUntilPageLoads();
		WebElement dateField = driver.findElement(By.id("ext-gen148"));
		dateField.click();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DOWN);
		robot.keyPress(KeyEvent.VK_ENTER);

		WebElement dateFrom = driver.findElement(By.xpath("//img[@id='ext-gen152']"));
		dateFrom.click();
		waitUntilPageLoads();

		WebElement fromToday = driver.findElement(By.xpath("//button[@id='ext-gen281']"));
		fromToday.click();

		WebElement dateTo = driver.findElement(By.xpath("//img[@id='ext-gen154']"));
		dateTo.click();

		WebElement toToday = driver.findElement(By.xpath("//button[@id='ext-gen296']"));
		toToday.click();

		WebElement save= driver.findElement(By.xpath("//button[@id='ext-gen49']"));
		save.click();
		waitUntilPageLoads();

		WebElement reportName = driver.findElement(By.xpath("//input[@id='saveReportDlg_reportNameField']"));
		reportName.sendKeys("Rep2023");
		WebElement uniqueRepName = driver.findElement(By.xpath("//input[@id='saveReportDlg_DeveloperName']"));
		uniqueRepName.clear();
		uniqueRepName.sendKeys("Feb10");
		waitUntilPageLoads();
		WebElement saveDialog = driver.findElement(By.xpath("//table[@id='dlgSaveAndRun']"));
		saveDialog.click();
		waitUntilPageLoads();

		String repName = driver.getTitle();
		verifyPageTitle(driver, repName);

	}
	@Test
	public static void Opportunities() throws IOException, InterruptedException  {
		logger.info("Inside Opportunities Method");
		extentreport.logTestInfo("Inside Opportunities Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement oppDropDown = driver.findElement(By.xpath("//select[@id='fcf']"));
		oppDropDown.click();
		if(oppDropDown.isDisplayed()) {
			logger.info("Drop Down List displayed");
			//extentreport.logTestPassed("Opportunities");
		}
		else 
		{
			logger.info("Drop Down List not displayed");
			//extentreport.logTestFailed("Opportunities");
		}

	}
	@Test
	public static void NewOpportunities() throws IOException, InterruptedException  {
		logger.info("Inside New Opportunities Method");
		extentreport.logTestInfo("Inside New Opportunities Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement oppoNew = driver.findElement(By.xpath("//input[@title='New']"));
		oppoNew.click();
		String oppoEdit = driver.getTitle();
		verifyPageTitle(driver, oppoEdit);


		WebElement oppoName = driver.findElement(By.xpath("//input[@id='opp3']"));
		oppoName.click();
		oppoName.sendKeys("Finance");

		WebElement accName = driver.findElement(By.xpath("//input[@id='opp4']"));
		accName.click();
		accName.sendKeys("Shreeya");

		WebElement closeDate = driver.findElement(By.xpath("//input[@id='opp9']"));
		closeDate.click();
		WebElement date = driver.findElement(By.xpath("//td[contains(text(),'14')]"));
		date.click();

		WebElement stage = driver.findElement(By.xpath("//select[@id='opp11']"));
		stage.click();
		Select st = new Select(stage);
		st.selectByVisibleText("Qualification");

		WebElement probability = driver.findElement(By.id("opp12"));
		probability.click();
		probability.clear();
		probability.sendKeys("30");

		WebElement leadSource = driver.findElement(By.xpath("//select[@id='opp6']"));
		leadSource.click();
		Select lead = new Select(leadSource);
		lead.selectByVisibleText("Web");
		/*
		 * WebElement campaign = driver.findElement(By.xpath("//input[@id='opp17']"));
		 * campaign.click(); campaign.sendKeys("Google Ad");
		 */
		WebElement save = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@title='Save']"));
		save.click();

		String oppoSave = driver.getTitle();
		verifyPageTitle(driver, oppoSave);
		waitUntilPageLoads();

	}
	@Test
	public static void OpportunitiesPipeline() throws IOException, InterruptedException  {
		logger.info("Inside Opportunities Pipeline Method");
		extentreport.logTestInfo("Inside Opportunities Pipeline Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement pipeLine = driver.findElement(By.xpath("//a[contains(text(),'Opportunity Pipeline')]"));
		pipeLine.click();
		waitUntilPageLoads();

		String oppoText = driver.getTitle();
		verifyPageTitle(driver, oppoText);
		waitUntilPageLoads();

	}
	@Test

	public static void StuckOpportunity() throws IOException, InterruptedException {
		logger.info("Inside Stuck Opportunity Method");
		extentreport.logTestInfo("Inside Stuck Opportunity Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement stuckOpportunity = driver.findElement(By.xpath("//a[contains(text(),'Stuck Opportunities')]"));
		stuckOpportunity.click();
		waitUntilPageLoads();

		String stuckPage = driver.getTitle();
		verifyPageTitle(driver, stuckPage);
		waitUntilPageLoads();

	}
	@Test
	public static void QuarterlySummary() throws IOException, InterruptedException  {
		logger.info("Inside Quarterly Summary Method");
		extentreport.logTestInfo("Inside Quarterly Summary Method");
		LoginFromBase();
		AccountPlusOpp();

		waitUntilPageLoads();
		WebElement intervalCurrent = driver.findElement(By.xpath("//select[@id='quarter_q']"));
		intervalCurrent.click();
		Select ob = new Select(intervalCurrent);
		ob.selectByVisibleText("Current FQ");

		WebElement intervalAll = driver.findElement(By.xpath("//select[@id='open']"));
		intervalAll.click();
		Select all = new Select(intervalAll);
		all.selectByVisibleText("All Opportunities");
		waitUntilPageLoads();
		WebElement runReport = driver.findElement(By.xpath("//input[@title='Run Report']"));
		runReport.click();


		String oppoRepPage = driver.getTitle();
		verifyPageTitle(driver, oppoRepPage);

	}
	@Test
	public static void QuarterlySummaryB() throws IOException, InterruptedException  {
		logger.info("Inside Quarterly Summary B Method");
		extentreport.logTestInfo("Inside Quarterly Summary B Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement interval = driver.findElement(By.xpath("//select[@id='quarter_q']"));
		interval.click();
		Select ob = new Select(interval);
		ob.selectByVisibleText("Current FQ");

		WebElement intervalOpen = driver.findElement(By.xpath("//select[@id='open']"));
		intervalOpen.click();
		Select open = new Select(intervalOpen);
		open.selectByVisibleText("Open Opportunities");
		waitUntilPageLoads();
		WebElement runReport = driver.findElement(By.xpath("//input[@title='Run Report']"));
		runReport.click();
		String oppoRepPage = driver.getTitle();
		verifyPageTitle(driver, oppoRepPage);

	}
	@Test
	public static void QuarterlySummaryC() throws IOException, InterruptedException {
		logger.info("Inside Quarterly Summary C Method");
		extentreport.logTestInfo("Inside Quarterly Summary C Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement intervalNext = driver.findElement(By.xpath("//select[@id='quarter_q']"));
		intervalNext.click();
		Select ob=new Select(intervalNext);
		ob.selectByVisibleText("Next FQ");
		WebElement intervalAll = driver.findElement(By.xpath("//select[@id='open']"));
		intervalAll.click();

		Select open = new Select(intervalAll);
		open.selectByVisibleText("All Opportunities");
		WebElement runReport = driver.findElement(By.xpath("//input[@title='Run Report']"));
		runReport.click();
		String oppoRepPage = driver.getTitle();
		verifyPageTitle(driver, oppoRepPage);


	}
	@Test
	public static void QuarterlySummaryD() throws IOException, InterruptedException {
		logger.info("Inside Quarterly Summary D Method");
		extentreport.logTestInfo("Inside Quarterly Summary D Method");
		LoginFromBase();
		AccountPlusOpp();

		WebElement intervalNext = driver.findElement(By.xpath("//select[@id='quarter_q']"));
		intervalNext.click();
		Select ob=new Select(intervalNext);
		ob.selectByVisibleText("Next FQ");
		WebElement intervalClosed = driver.findElement(By.xpath("//select[@id='open']"));
		intervalClosed.click();
		Select open = new Select(intervalClosed);
		open.selectByVisibleText("Closed Opportunities");
		WebElement runReport = driver.findElement(By.xpath("//input[@title='Run Report']"));
		runReport.click();
		String oppoRepPage = driver.getTitle();
		verifyPageTitle(driver, oppoRepPage);

	}
	@Test
	public static void LeadTab() throws IOException, InterruptedException {
		logger.info("Inside Lead Tab Method");
		extentreport.logTestInfo("Inside Lead Tab Method");
		LoginFromBase();
		AccountPlusLeads();

	}
	@Test
	public static void LeadDropDown() throws IOException, InterruptedException {
		logger.info("Inside Lead DropDown Method");
		extentreport.logTestInfo("Inside Lead DropDown Method");
		LoginFromBase();
		AccountPlusLeads();

		WebElement leadDropDown = driver.findElement(By.id("fcf"));
		leadDropDown.click();
		Thread.sleep(3000);
		if(leadDropDown.isDisplayed()) {
			System.out.println("Drop down list visible");
		}
		else {
			System.out.println("Drop down list not visible");
		}

	}
	@Test
	public static void LeadDefaultViewA() throws IOException, InterruptedException {
		logger.info("Inside Lead DefaultView A Method");
		extentreport.logTestInfo("Inside Lead DefaultView A Method");
		LoginFromBase();
		AccountPlusLeads();

		WebElement go = driver.findElement(By.xpath("//input[@title='Go!']"));
		go.click(); 
		WebElement profile = driver.findElement(By.id("userNavLabel"));
		profile.click(); 
		WebElement logout =	driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		logout.click();


	}
	@Test
	public static void LeadDefaultView() throws IOException, InterruptedException {
		logger.info("Inside Lead DefaultView Method");
		extentreport.logTestInfo("Inside Lead DefaultView Method");
		LoginFromBase();
		AccountPlusLeads();

		WebElement leadDropDown = driver.findElement(By.id("fcf"));
		leadDropDown.click();
		waitUntilPageLoads();
		Select ob = new Select(leadDropDown);
		ob.selectByVisibleText("My Unread Leads");

		WebElement profile = driver.findElement(By.id("userNavLabel"));
		profile.click();
		WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		logout.click();

		String salesLogin = driver.getTitle();
		verifyPageTitle(driver, salesLogin);


	}
	@Test
	public static void TodaysLead() throws IOException, InterruptedException {
		logger.info("Inside Today's Lead Method");
		extentreport.logTestInfo("Inside Today's Lead Method");
		LoginFromBase();
		AccountPlusLeads();

		WebElement leadDropDown = driver.findElement(By.id("fcf"));
		leadDropDown.click();
		waitUntilPageLoads();

		Select ob = new Select(leadDropDown);
		ob.selectByVisibleText("Today's Leads");

		WebElement go = driver.findElement(By.xpath("//input[@title='Go!']"));
		go.click();
		String leadsPage = driver.getTitle();
		verifyPageTitle(driver, leadsPage);
		waitUntilPageLoads();

		WebElement profile = driver.findElement(By.id("userNavLabel"));
		profile.click();
		WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		logout.click();

	}
	@Test
	public static void NewTodaysLead() throws IOException, InterruptedException {
		logger.info("Inside New Today's Lead  Method");
		extentreport.logTestInfo("Inside New Today's Lead  Method");
		LoginFromBase();
		AccountPlusLeads();

		WebElement newLead = driver.findElement(By.xpath("//input[@title='New']"));
		newLead.click();
		waitUntilPageLoads();

		String leadsNewPage = driver.getTitle();
		verifyPageTitle(driver, leadsNewPage);
		WebElement lastName = driver.findElement(By.id("name_lastlea2"));
		lastName.click();
		lastName.sendKeys("ABCD");
		WebElement companyName = driver.findElement(By.id("lea3"));
		companyName.click();
		companyName.sendKeys("ABCD");
		WebElement save = driver.findElement(By.xpath("//td[@id='bottomButtonRow']//input[@title='Save']"));
		save.click();
		String leadUserPage = driver.getTitle();
		verifyPageTitle(driver, leadUserPage);
		waitUntilPageLoads();

		WebElement profile = driver.findElement(By.id("userNavLabel"));
		profile.click();
		WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
		logout.click();

	}
	@Test
	public static void NewContact() throws IOException, InterruptedException {
		logger.info("Inside New Contact Method");
		extentreport.logTestInfo("Inside New Contact Method");
		LoginFromBase();
		WebElement contact = driver.findElement(By.xpath("//a[normalize-space()='Contacts']"));
		contact.click();
		Thread.sleep(3000);
		NoThanks();
		waitUntilPageLoads();
		//img[@title='Contact']

		String contactTab = driver.getTitle();
		verifyPageTitle(driver, contactTab);

		WebElement contactNew = driver.findElement(By.xpath("//input[@title='New']"));
		contactNew.click();
		String newContact = driver.getTitle();
		verifyPageTitle(driver, newContact);

		WebElement lastName = driver.findElement(By.xpath("//input[@name='name_lastcon2']"));
		lastName.click();
		lastName.sendKeys("Harsh");
		WebElement accName = driver.findElement(By.xpath("//input[@id='con4']"));
		accName.click();
		accName.sendKeys("Shreeya");
		//td[@id='topButtonRow']//input[@title='Save']
		WebElement save = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@title='Save']"));
		save.click();
		String contactName = driver.getTitle();
		verifyPageTitle(driver, contactName);
		//*[@id="contactHeaderRow"]/div[2]/h2

	}
	@Test
	public static void CreateNewViewContact() throws IOException, InterruptedException {
		logger.info("Inside Create New View Contact Method");
		extentreport.logTestInfo("Inside Create New View Contact Method");
		LoginFromBase();
		ContactTab();

		WebElement newView = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
		newView.click();

		String viewPage = driver.getTitle();
		verifyPageTitle(driver, viewPage);

		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		viewName.click();
		viewName.sendKeys("VIEw Name");
		WebElement viewUnique = driver.findElement(By.xpath("//input[@id='devname']"));
		viewUnique.click();
		viewUnique.clear();
		viewUnique.sendKeys("VIEwUniqueName");
		WebElement save = driver.findElement(By.xpath("(//input[@title='Save'])[1]"));
		save.click();

		String actualHome = driver.getTitle();
		verifyPageTitle(driver, actualHome);

	}
	@Test
	public static void CheckMyContacts() throws IOException, InterruptedException {
		logger.info("Inside Check My Contacts Method");
		extentreport.logTestInfo("Inside Check My Contacts Method");
		LoginFromBase();
		ContactTab();

		WebElement recentlyCreated = driver.findElement(By.id("hotlist_mode"));
		recentlyCreated.click();
		Select ob = new Select(recentlyCreated);
		ob.selectByVisibleText("Recently Created");

		if(recentlyCreated.isDisplayed()) {
			logger.info("Drop down list visible");
			//extentreport.logTestPassed("CheckMyContacts");
		}
		else {
			logger.info("Drop down list not visible");
			//extentreport.logTestFailed("CheckMyContacts");
		}
		waitUntilPageLoads();

	}
	@Test
	public static void CheckRecentContact() throws AWTException, IOException, InterruptedException {
		logger.info("Inside Check Recent Contact Method");
		extentreport.logTestInfo("Inside Check Recent Contact Method");
		LoginFromBase();
		ContactTab();

		WebElement viewContact = driver.findElement(By.name("fcf"));
		viewContact.click();
		Select ob = new Select(viewContact);
		ob.selectByVisibleText("My Contacts");
		Robot ro = new Robot();
		ro.keyPress(KeyEvent.VK_ENTER);
		if(viewContact.isDisplayed()) {
			logger.info("My contacts are displayed");
			//extentreport.logTestPassed("CheckRecentContact");
		}
		else {
			logger.info("My contacts are not displayed");
			//extentreport.logTestFailed("CheckRecentContact");
		}

		WebElement go = driver.findElement(By.xpath("//input[@title='Go!']"));
		go.click();

		String actualContactsHome = driver.getTitle();
		verifyPageTitle(driver, actualContactsHome);

	}
	@Test
	public static void ViewContact() throws IOException, InterruptedException {
		logger.info("Inside View Contact Method");
		extentreport.logTestInfo("Inside View Contact Method");
		LoginFromBase();
		ContactTab();
		Thread.sleep(3000);

		WebElement myContact = driver.findElement(By.id("fcf"));
		myContact.click();


		String actualContactsHome = driver.getTitle();
		verifyPageTitle(driver, actualContactsHome);
		waitUntilPageLoads();

	}
	@Test
	public static void ErrorUniqueName() throws IOException, InterruptedException {
		logger.info("Inside Error Unique Name Method");
		extentreport.logTestInfo("Inside Error Unique Name Method");
		LoginFromBase();
		ContactTab();

		WebElement createNewView = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
		createNewView.click();
		String actualCreateNewViewHome = driver.getTitle();
		verifyPageTitle(driver, actualCreateNewViewHome);

		WebElement viewUnique = driver.findElement(By.xpath("//input[@id='devname']"));
		viewUnique.click();
		viewUnique.sendKeys("EFGH");
		WebElement save = driver.findElement(By.xpath("(//input[@title='Save'])[1]"));
		save.click();

		WebElement errorMsg = driver.findElement(By.xpath("//div[@class='requiredInput']//div[@class='errorMsg']"));
		System.out.println("Error Message : "+errorMsg.getText());

	}
	@Test
	public static void CreateViewCancel() throws IOException, InterruptedException {
		logger.info("Inside Create View Cancel Method");
		extentreport.logTestInfo("Inside Create View Cancel Method");
		LoginFromBase();
		ContactTab();

		WebElement createNewView = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
		createNewView.click();

		String actualCreateNewViewHome = driver.getTitle();
		verifyPageTitle(driver, actualCreateNewViewHome);

		WebElement viewName = driver.findElement(By.id("fname"));
		viewName.click();
		viewName.sendKeys("ABCD");
		WebElement viewUnique = driver.findElement(By.xpath("//input[@id='devname']"));
		viewUnique.click();
		viewUnique.clear();
		viewUnique.sendKeys("EFGH");
		waitUntilPageLoads();

		System.out.println("View name: " +viewName.getText());
		System.out.println("View Unique name : " +viewUnique.getText());

		WebElement cancel = driver.findElement(By.xpath("//*[@id=\"editPage\"]/div[1]/table/tbody/tr/td[2]/input[2]"));
		cancel.click();

		String contactCancel = driver.getTitle();
		verifyPageTitle(driver, contactCancel);

		//*[@id="editPage"]/div[1]/table/tbody/tr/td[2]/input[2]


	}
	@Test
	public static void SaveNewContactButton() throws IOException, InterruptedException {
		logger.info("Inside Save New Contact Method");
		extentreport.logTestInfo("Inside Save New Contact Method");
		LoginFromBase();
		ContactTab();

		WebElement newAcc = driver.findElement(By.xpath("//input[@title='New']"));
		newAcc.click();

		String ContactEditHome = driver.getTitle();
		verifyPageTitle(driver, ContactEditHome);
		waitUntilPageLoads();

		WebElement lastName = driver.findElement(By.id("name_lastcon2"));
		lastName.click();
		lastName.sendKeys("Indian");
		
		WebElement accName = driver.findElement(By.xpath("(//input[@id='con4'])[1]"));
		accName.sendKeys("Global Media");

		WebElement save = driver.findElement(By.xpath("//td[@id='bottomButtonRow']|//input[@title='Save']")); 
		Actions saveAction = new Actions(driver);
		saveAction.moveToElement(save).click();
		waitUntilPageLoads();

	}
	@Test
	public static void VerifyFirstLastName() throws IOException, InterruptedException {
		logger.info("Inside Verify First Last Name Method");
		extentreport.logTestInfo("Inside Verify First Last Name Method");
		LoginFromBase();
		String actualLoginHome = driver.getTitle();
		verifyPageTitle(driver, actualLoginHome);

		WebElement user = driver.findElement(By.xpath("//a[contains(text(),'Savitha M')]"));
		user.click();
		String userHome = driver.getTitle();
		System.out.println("Page Title :"+userHome);
		verifyPageTitle(driver, userHome);

	}
	@Test
	public static void VerifyEditLastName() throws IOException, InterruptedException {
		logger.info("Inside Verify Edit Last Name Method");
		extentreport.logTestInfo("Inside Verify Edit Last Name Method");
		LoginFromBase();
		WebElement home = driver.findElement(By.xpath("//a[contains(text(),'Home')]"));
		home.click();
		waitUntilPageLoads();

		String homeTab = driver.getTitle();
		verifyPageTitle(driver, homeTab);
		Thread.sleep(4000);
		NoThanks();
		Thread.sleep(3000);

		WebElement nameLink = driver.findElement(By.xpath("//span[@class='mruText'][normalize-space()='Savitha M']"));
		nameLink.click();
		waitUntilPageLoads();

		WebElement editUser = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//img[@title='Edit Profile']"));
		editUser.click();	

		WebElement iframe = driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
		driver.switchTo().frame(iframe);

		WebElement about = driver.findElement(By.xpath("//a[contains(text(),'About')]"));
		about.click();

		WebElement lastName = driver.findElement(By.id("lastName"));
		lastName.clear();
		lastName.click();
		lastName.sendKeys("Abcd");

		WebElement saveAll = driver.findElement(By.xpath("//input[@value='Save All']"));
		saveAll.click();
		Thread.sleep(3000);

		String newUserHome = driver.getTitle();
		verifyPageTitle(driver, newUserHome);

	}
	@Test
	public static void CustomizeTabsA() throws IOException, InterruptedException {
		logger.info("Inside Customize Tabs A Method");
		extentreport.logTestInfo("Inside Customize Tabs A Method");
		LoginFromBase();

		WebElement plusSign = driver.findElement(By.xpath("//img[@title='All Tabs']"));
		plusSign.click();
		String plusTab = driver.getTitle();
		verifyPageTitle(driver, plusTab);
		Thread.sleep(3000);

		WebElement customize = driver.findElement(By.xpath("//input[@title='Customize My Tabs']"));
		customize.click();
		String customTab = driver.getTitle();
		verifyPageTitle(driver, customTab);
		waitUntilPageLoads();

		WebElement removeTab = driver.findElement(By.xpath("//option[@value='ContentSubscriptions']"));

		removeTab.click();
		WebElement removeButton = driver.findElement(By.xpath("//img[@title='Remove']"));
		removeButton.click();

		WebElement save = driver.findElement(By.xpath("//input[@title='Save']"));
		save.click();

		WebElement userMenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
		userMenu.click();
		WebElement logout = driver.findElement(By.cssSelector("a[title='Logout']"));
		logout.click();
		waitUntilPageLoads();

	}
	@Test
	public static void CustomizeTabs() throws IOException, InterruptedException {
		logger.info("Inside Customize Tabs Method");
		extentreport.logTestInfo("Inside Customize Tabs Method");
		LoginFromBase();

		WebElement plusSign = driver.findElement(By.xpath("//img[@title='All Tabs']"));
		plusSign.click();
		String plusTab = driver.getTitle();
		verifyPageTitle(driver, plusTab);
		Thread.sleep(3000);

		WebElement customize = driver.findElement(By.xpath("//input[@title='Customize My Tabs']"));
		customize.click();
		String customTab = driver.getTitle();
		verifyPageTitle(driver, customTab);
		// Find the element using XPath expression

		String expected = "Subscriptions";
		//select[@id='duel_select_0']
		WebElement removedTab = driver.findElement(By.xpath("//select[@id='duel_select_0']//option[@value='ContentSubscriptions']"));
		String actual = removedTab.getText();
		removedTab.click();
		Assert.assertEquals(actual, expected);
		waitUntilPageLoads();

	}
	@Test
	public static void BlockingEvent() throws IOException, InterruptedException {

		logger.info("Inside Blocking Event Method");
		extentreport.logTestInfo("Inside Blocking Event Method");
		LoginFromBase();
		waitUntilPageLoads();

		WebElement home = driver.findElement(By.xpath("//a[contains(text(),'Home')]"));
		home.click();
		String homeTab = driver.getTitle();
		verifyPageTitle(driver, homeTab);
		Thread.sleep(3000);
		NoThanks();
		waitUntilPageLoads();

		WebElement dateLink = driver.findElement(By.xpath("//*[@id=\"ptBody\"]/div/div[2]/span[2]/a"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();",dateLink);


		String userHome = driver.getTitle();
		verifyPageTitle(driver, userHome);
		waitUntilPageLoads();

		WebElement time = driver.findElement(By.xpath("//a[contains(text(),'8:00 PM')]"));
		time.click();
		String newEvent = driver.getTitle();
		verifyPageTitle(driver, newEvent);

		WebElement comboBox = driver.findElement(By.xpath("//img[@title='Subject Combo (New Window)']"));
		comboBox.click();
		waitUntilPageLoads();
		String popup =driver.getWindowHandle();
		driver.switchTo().window(popup);
		waitUntilPageLoads();
		/*
		 * WebElement other = driver.findElement(By.xpath("//li[@class='listItem4']"));
		 * other.click();
		 */
		WebElement endTime = driver.findElement(By.xpath("//input[@id='EndDateTime_time']"));
		endTime.click();
		waitUntilPageLoads();

		WebElement timePick = driver.findElement(By.xpath("//div[@id='timePickerItem_42']"));
		timePick.click();
		waitUntilPageLoads();

		WebElement save = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@title='Save']"));
		save.click();
		waitUntilPageLoads();

		String calendarHome = driver.getTitle();
		verifyPageTitle(driver, calendarHome);

	}
	@Test
	public static void BlockingEventWeekly() throws IOException, InterruptedException {
		logger.info("Inside Blocking Event Weekly Method");
		extentreport.logTestInfo("Inside Blocking Event Weekly Method");
		LoginFromBase();
		waitUntilPageLoads();

		WebElement home = driver.findElement(By.xpath("//a[contains(text(),'Home')]"));
		home.click();
		String homeTab = driver.getTitle();
		verifyPageTitle(driver, homeTab);
		Thread.sleep(3000);

		NoThanks();

		WebElement dateLink = driver.findElement(By.xpath("//*[@id=\"ptBody\"]/div/div[2]/span[2]/a"));
		dateLink.click();

		String userHome = driver.getTitle();
		verifyPageTitle(driver, userHome);
		waitUntilPageLoads();


		WebElement time = driver.findElement(By.xpath("//a[normalize-space()='8:00 PM']"));
		time.click();
		String newEvent = driver.getTitle();
		verifyPageTitle(driver, newEvent);

		/*
		 * WebElement iframe =
		 * driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
		 * driver.switchTo().frame(iframe);
		 */
		WebElement comboBox = driver.findElement(By.xpath("//img[@title='Subject Combo (New Window)']"));
		comboBox.click();
		waitUntilPageLoads();
		String popup =driver.getWindowHandle();
		driver.switchTo().window(popup);
		waitUntilPageLoads();
		/*
		 * WebElement other = driver.findElement(By.xpath("//li[@class='listItem4']"));
		 * other.click();
		 */
		WebElement endTime = driver.findElement(By.xpath("//input[@id='EndDateTime_time']"));
		endTime.click();
		waitUntilPageLoads();

		WebElement timePick = driver.findElement(By.xpath("//div[@id='timePickerItem_42']"));
		timePick.click();
		waitUntilPageLoads();

		WebElement save = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@title='Save']"));
		save.click();
		waitUntilPageLoads();

		String calendarHome = driver.getTitle();
		verifyPageTitle(driver, calendarHome);

	}
}
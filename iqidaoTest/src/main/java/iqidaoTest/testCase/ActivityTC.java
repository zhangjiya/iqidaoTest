package iqidaoTest.testCase;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import iqidaoTest.Utils.MyChormeDriver;
import iqidaoTest.Utils.TestProperties;
import iqidaoTest.Utils.xmlData;
import iqidaoTest.adminPageObject.ActivitysListPage;
import iqidaoTest.adminPageObject.AdminHomePage;
import iqidaoTest.adminPageObject.AdminLoginPage;
import iqidaoTest.adminPageObject.CreatNotification;
import iqidaoTest.adminPageObject.CreateActivityPage;
import iqidaoTest.adminPageObject.CreateSeasonPage;

public class ActivityTC {
	private WebDriver driver;
	// 页面URL
	String adminLoginUrl = xmlData.getParamFromXml("adminLoginUrl");
	String adminHomeUrl = xmlData.getParamFromXml("adminHomeUrl");
	String createActivityUrl = xmlData.getParamFromXml("createActivityUrl");
	String activitysListUrl = xmlData.getParamFromXml("activitysListUrl");
	// 登录
	String ChormeURL = xmlData.getParamFromXml("ChormeURL");
	String userName = xmlData.getParamFromXml("userName");
	String passWord = xmlData.getParamFromXml("passWord");
	// 创建活动
	String[] activityName = xmlData.getParamArrayFromXml("activityName");
	String teacherName = xmlData.getParamFromXml("teacherName");
	String activityPicture = xmlData.getParamFromXml("activityPicture");
	String signupCount = xmlData.getParamFromXml("signupCount");
	String lowduan = xmlData.getParamFromXml("lowduan");
	String price = xmlData.getParamFromXml("price");
	String signupStartTime = xmlData.getParamFromXml("signupStartTime");
	String signupEndTime = xmlData.getParamFromXml("signupEndTime");
	String activityStartTime = xmlData.getParamFromXml("activityStartTime");
	String activityEndTime = xmlData.getParamFromXml("activityEndTime");
	String[] activitytype = xmlData.getParamArrayFromXml("activitytype");
	// 创建赛季
	String seasonName = xmlData.getParamFromXml("seasonName");
	String seasonPrice = xmlData.getParamFromXml("seasonPrice");
	String seasonStartTime = activityStartTime;
	String seasonEndTime = activityEndTime;
	// 创建课程
	String itemName = xmlData.getParamFromXml("itemName");
	String itemStartTime = xmlData.getParamFromXml("itemStartTime");
	String courseSyllabus = xmlData.getParamFromXml("courseSyllabus");
	//创建AI
	String AItime = xmlData.getParamFromXml("AItime");
	String gamecount = xmlData.getParamFromXml("gamecount");
	String gamelevel = xmlData.getParamFromXml("gamelevel");
	String gamerul = xmlData.getParamFromXml("gamerul");
	String ReportName = xmlData.getParamFromXml("ReportName");
	String ReportStartTime = xmlData.getParamFromXml("ReportStartTime");
	String ReportEndTime= xmlData.getParamFromXml("ReportEndTime");
	String ReportSendTime=xmlData.getParamFromXml("ReportSendTime");
	@BeforeTest
	public void beforeTest() throws IOException {
		System.setProperty("webdriver.chrome.driver", ChormeURL);
		//多个案例连续跑，只打开1个浏览器时用这个
		TestProperties prop =new TestProperties();
		String  driverserver = prop.GetValueByKey("Test.Properties", "Driver");
		String  caseSession = prop.GetValueByKey("Test.Properties", "Sessionid");
		this.driver = new MyChormeDriver(driverserver,caseSession);
		/*// 单个测试案例执行时使用
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
		String expectedResult = "首页";
		AdminLoginPage adminLoginPage = new AdminLoginPage(this.driver, adminLoginUrl);
		AdminHomePage adminHomePage = adminLoginPage.adminLogin(userName, passWord, adminHomeUrl);
		String actualResult = adminHomePage.getTitleText();
		AssertJUnit.assertTrue(actualResult.contains(expectedResult));*/
	}

	// 创建活动
	@Test(priority = 0)
	public void createActivity() {
		for (int i = 0; i < 1; i++) {
			String expectedResult = activityName[i];
			CreateActivityPage createActivityPage = new CreateActivityPage(this.driver, createActivityUrl);
			ActivitysListPage activitysListPage = createActivityPage.createActivity(activityName[i], teacherName,
					activityPicture, signupCount, lowduan, price, signupStartTime, signupEndTime, activityStartTime,
					activityEndTime, activitysListUrl, activitytype[i]);
			String actualResult = activitysListPage.getFirstActivityName().getText();
			AssertJUnit.assertTrue(actualResult.contains(expectedResult));
		}
	}

	// 创建赛季和课程
	@Test(priority = 1)
	public void createSeasonAndCourse() throws InterruptedException {
		ActivitysListPage activityListPage = new ActivitysListPage(this.driver, activitysListUrl);
		activityListPage.getActivityByName(activityName[0]);
		this.driver.navigate().refresh();
		String currentUrl = this.driver.getCurrentUrl();
		CreateSeasonPage createSeasonPage = new CreateSeasonPage(this.driver, currentUrl);
		createSeasonPage = createSeasonPage.addActivitySeason(seasonName, seasonPrice, seasonStartTime, seasonEndTime);
		Thread.sleep(2000);
		createSeasonPage = createSeasonPage.addCourseItem(itemName, itemStartTime, courseSyllabus);
		Thread.sleep(2000);
		createSeasonPage = createSeasonPage.addAIItem(itemName, itemStartTime, activitysListUrl, AItime, gamecount, gamelevel, gamerul);
		Thread.sleep(2000);
		//添加通知管理信息
		activityListPage.getActivityByName(activityName[0]);
		this.driver.navigate().refresh();
		String curl=this.driver.getCurrentUrl();
		CreatNotification noticreate=new CreatNotification(this.driver, curl);
		noticreate.CreateNotificationPage( ReportName,ReportStartTime, ReportEndTime, ReportSendTime);
	}		
	// 入学测只需添加试卷条目
	@Test(groups = { "CreateSeason" }, priority = 2)
	public void createSeasonAndCoursetest() throws InterruptedException {
		ActivitysListPage activityListPage = new ActivitysListPage(this.driver, activitysListUrl);
		activityListPage.getActivityByName(activityName[2]);
		Thread.sleep(2000);
		String currentUrl = this.driver.getCurrentUrl();
		CreateSeasonPage createSeasonPage = new CreateSeasonPage(this.driver, currentUrl);
		createSeasonPage = createSeasonPage.Enableteseclose();
		Thread.sleep(2000);
		createSeasonPage = createSeasonPage.addActivitySeason(seasonName, seasonPrice, seasonStartTime, seasonEndTime);
		Thread.sleep(2000);
		createSeasonPage = createSeasonPage.addExamItem(itemName, itemStartTime, activitysListUrl);
		Thread.sleep(2000);
	}
	/*
	 * @AfterTest public void afterTest() { this.driver.close(); }
	 */
}

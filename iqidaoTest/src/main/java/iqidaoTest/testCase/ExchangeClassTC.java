package iqidaoTest.testCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import iqidaoTest.ExchangeClass.ExClassList;
import iqidaoTest.ExchangeClass.ExClassPage;
import iqidaoTest.Utils.MyChormeDriver;
import iqidaoTest.Utils.SetLog;
import iqidaoTest.Utils.TestProperties;
import iqidaoTest.Utils.xmlData;
import iqidaoTest.adminPageObject.AdminHomePage;
import iqidaoTest.adminPageObject.AdminLoginPage;

public class ExchangeClassTC {
	private WebDriver driver;
	// 定义变量值
	// 页面URL
	String adminLoginUrl = xmlData.getParamFromXml("adminLoginUrl");
	String adminHomeUrl = xmlData.getParamFromXml("adminHomeUrl");
	String ExClassUrl = xmlData.getParamFromXml("ExClassUrl");
	// 登录
	String ChormeURL = xmlData.getParamFromXml("ChormeURL");
	String userName = xmlData.getParamFromXml("userName");
	String passWord = xmlData.getParamFromXml("passWord");
	// 换班数据
	String[] ExUserName = xmlData.getParamArrayFromXml("activityUserName");
	String OriginActivityName = xmlData.getParamFromXml("activityName");
	String OriginSeason = xmlData.getParamFromXml("seasonName");
	String ToActivityName = xmlData.getParamFromXml("ToActivityName");
	String ToSeason = xmlData.getParamFromXml("ToSeason");

	@BeforeTest
	public void BeforeTest() {
		System.setProperty("webdriver.chrome.driver", ChormeURL);
		// 多个案例连续跑，只打开1个浏览器时用这个
		TestProperties prop = new TestProperties();
		String driverserver = prop.GetValueByKey("Test.Properties", "Driver");
		String caseSession = prop.GetValueByKey("Test.Properties", "Sessionid");
		this.driver = new MyChormeDriver(driverserver, caseSession);
		/*
		 * // 单个测试案例执行时使用 this.driver = new ChromeDriver();
		 * this.driver.manage().window().maximize(); String expectedResult =
		 * "首页"; AdminLoginPage adminLoginPage = new AdminLoginPage(this.driver,
		 * adminLoginUrl); AdminHomePage adminHomePage =
		 * adminLoginPage.adminLogin(userName, passWord, adminHomeUrl); String
		 * actualResult = adminHomePage.getTitleText();
		 * AssertJUnit.assertTrue(actualResult.contains(expectedResult));
		 */

	}

	@Test(groups = { "ExClass" })
	// 添加换班信息
	public void ExClass() {
		ExClassPage exclass = new ExClassPage(driver, ExClassUrl);
		exclass.AddExClass(ExUserName[2], OriginActivityName, OriginSeason, ToActivityName, ToSeason);
		// 判断是否添加成功
		ExClassList searsh = new ExClassList(driver, ExClassUrl);
		searsh.ElementExist(ExUserName[2], OriginActivityName, ToActivityName);
	}

	@AfterTest
	public void afterTest() {
		this.driver.close();
	}

}

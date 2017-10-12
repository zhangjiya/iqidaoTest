package iqidaoTest.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


//http://101.132.45.64/admin001/activities
public class ActivitysListPage extends BasePage{
	public ActivitysListPage(WebDriver driver, String url){
		super(driver);
		this.url = url;
		this.goTo();
	}
	
	By activityMenuLocator = By.xpath("html/body/div[1]/aside/section/ul/li[1]/a/span[1]");
	By activityListMenuLocator = By.xpath("html/body/div[1]/aside/section/ul/li[1]/ul/li/a/span");
	
	By createActivityLocator = By.linkText("创建活动");
	
	public WebElement getFirstActivityName(){
		return this.dr.findElement(By.xpath("#activity-list>tbody>tr>td>a"));
	}

	public BasePage getActivityByName(String activityName) {
		boolean flag = false; 
		for(int row = 1; row < 21; row++) {
			WebElement actualActivityName = this.dr.findElement(By.xpath(".//*[@id='activity-list']/tbody/tr[" + row + "]/td[3]"));
			if(actualActivityName.getText().contains(activityName)) {
				flag = true;
				actualActivityName.click();
				break;
			}
		}
		if(flag) {
			String currentUrl = this.dr.getCurrentUrl();
			return new CreateSeasonPage(this.dr, currentUrl);
		}else {
			return new ActivitysListPage(this.dr, "http://101.132.45.64/admin001/activities");
		}
		
	}
	

}

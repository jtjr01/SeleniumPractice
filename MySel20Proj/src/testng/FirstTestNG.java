package testng;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;

public class FirstTestNG {
	//Url for website to test on
	public String baseUrl = "http://www.google.com";
	//Path to the chromedriver.exe
	String driverPath = "C:\\\\Users\\\\Jose\\\\Documents\\\\chromedriver.exe";
	//Variable for the chrome driver
	public WebDriver driver;
	//Variable for explicit wait
	WebDriverWait myWaitVar;


	@Test(priority = 0)
	public void launchBrowser() throws IOException {
		System.out.println("Launching google chrome browswer and navigating to https://www.google.com");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.get(baseUrl);
		myWaitVar = new WebDriverWait(driver, 10);
		screenshot();
	}

	@Test(priority = 1)
	public void searchAutomation() throws IOException {
		System.out.println("Searching for automation on https://www.google.com");
		WebElement element = driver.findElement(By.xpath("//input[@name='q']"));
		System.out.println(element);
		element.sendKeys("automation");
		element.submit();

		myWaitVar.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("automation");
			}
		});
		screenshot();
	}
	
	@Test(priority = 2)
	public void visitSecondResultAfterSearch() throws IOException {
		driver.findElement(By.xpath("//a[@href='http://www.automation.com/']")).click();
		myWaitVar.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().startsWith("Automation.com - News & Resources for Manufacturing, Factory & Industrial Automation; Process Control; Motion Control & Instrumentation");
			}
		});
		screenshot();
	}
	
	@AfterTest
	public void terminateBrowser() {
		driver.close();
	}
	
	public void screenshot() throws IOException{
		File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File screenShotName = new File("C:\\Users\\Jose\\git\\SeleniumPractice\\MySel20Proj\\Screenshots\\" + driver.getTitle() + ".png");
		FileUtils.copyFile(screenShot, screenShotName);
		Reporter.log("<br><img src='"+ screenShotName +"' height='400' width='400'/><br>");
	}
}


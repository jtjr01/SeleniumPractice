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


	//Launch the browser, set up driver, and the implicit wait
	@Test(priority = 0)
	public void launchBrowser() throws IOException {
		System.out.println("Launching google chrome browswer and navigating to https://www.google.com");
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		//Navigate broswer to specified url
		driver.get(baseUrl);
		//Handles implicit waiting 
		myWaitVar = new WebDriverWait(driver, 10);
		//Takes a screenshot of results
		screenshot();
	}

	//Types automation and searches it using google
	@Test(priority = 1)
	public void searchAutomation() throws IOException {
		System.out.println("Searching for automation on https://www.google.com");
		//Using xpath to find the search bar
		WebElement element = driver.findElement(By.xpath("//input[@name='q']"));
		System.out.println(element);
		//Simulates typing automation
		element.sendKeys("automation");
		//Simulates the search
		element.submit();

		//Waits for 10 seconds if the title does not match desired search
		myWaitVar.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("automation");
			}
		});
		screenshot();
	}
	
	//Navigate to the second result
	@Test(priority = 2)
	public void visitSecondResultAfterSearch() throws IOException {
		//Finds the second result and clicks on it
		driver.findElement(By.xpath("//*[@id='rso']/div[3]/div/div[2]/div/div/h3")).click();
		//Waits for 10 seconds to make sure the resulting page is the one that was supposed to be clicked on
		myWaitVar.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().startsWith("Automation.com - News & Resources for Manufacturing, Factory & Industrial Automation; Process Control; Motion Control & Instrumentation");
			}
		});
		screenshot();
	}
	
	//Closes the browser when test are complete
	@AfterTest
	public void terminateBrowser() {
		driver.close();
	}
	
	//Method to handle screenshots
	public void screenshot() throws IOException{
		//Creates a blank screenshot when method is called
		File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Save the screenshot to the folder with the name of the title
		File screenShotName = new File("C:\\Users\\Jose\\git\\SeleniumPractice\\MySel20Proj\\Screenshots\\" + driver.getTitle() + ".png");
		//Copies the screenshot taken to new file created
		FileUtils.copyFile(screenShot, screenShotName);
		//Add the screenshot to the report log
		Reporter.log("<br><img src='"+ screenShotName +"' height='400' width='400'/><br>");
	}
}


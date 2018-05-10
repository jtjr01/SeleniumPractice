package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Locate the chromedriver.exe
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jose\\Documents\\chromedriver.exe");
		
		//Driver needed to interact with the browser
		WebDriver driver = new ChromeDriver();
		
		//Navigates to desired website
		driver.get("http://www.google.com");
		
		//Finds the search bar to enter text using the items name
        WebElement element = driver.findElement(By.name("q"));

        //Sample search item
        element.sendKeys("test");

        //Simulate the search button being pressed
        element.submit();

        //Should print test in the console
        System.out.println("Page title is: " + driver.getTitle());
        
        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith("test");
            }
        });

        //Console should print test if everything went correctly
        System.out.println("Page title is: " + driver.getTitle());
		
		//Close the driver after completion
		driver.quit();
	}

}

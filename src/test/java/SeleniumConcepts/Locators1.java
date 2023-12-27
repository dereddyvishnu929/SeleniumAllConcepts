package SeleniumConcepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Locators1 {

	public static void main(String[] args) throws InterruptedException {
		
	    WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		
		driver.get("http://www.automationpractice.pl/index.php");
		driver.manage().window().maximize();
		driver.findElement(By.id("search_query_top")).sendKeys("T-shirts");
		driver.findElement(By.name("submit_search")).click();
		Thread.sleep(3000);
		
		//driver.findElement(By.linkText("Blouse")).click();
		driver.findElement(By.partialLinkText("Blou")).click();
		driver.quit();
	}

}

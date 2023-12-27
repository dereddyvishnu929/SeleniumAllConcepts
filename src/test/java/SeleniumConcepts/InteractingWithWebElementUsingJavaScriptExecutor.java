package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class InteractingWithWebElementUsingJavaScriptExecutor {

	public static void main(String[] args) throws InterruptedException {
		
WebDriver driver=new ChromeDriver();
		
		driver.get("https://testautomationpractice.blogspot.com/");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//JavaScript Executor
		JavascriptExecutor js=(JavascriptExecutor) driver;
		
		//Name Text Box
		WebElement textbox=driver.findElement(By.xpath("//input[@id='name']"));
		//textbox.sendKeys("Vishnu");
		js.executeScript("arguments[0].setAttribute('value','Vishnu')", textbox);
		
		//Gender Radio button
		WebElement radiobutton=driver.findElement(By.xpath("//input[@id='male']"));
		js.executeScript("arguments[0].click();", radiobutton);
		
		
		//Scroll the page till the element present
		WebElement ele=driver.findElement(By.xpath("//div[@id='resizable']"));
		js.executeScript("arguments[0].scrollIntoView();", ele);
	     
		driver.quit();
		
		
		
		

	}

}

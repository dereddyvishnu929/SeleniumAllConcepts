package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HandleAlerts {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver=new ChromeDriver();
		
		//WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(5));
		
		driver.get("https://testautomationpractice.blogspot.com/");
		//driver.get("https://the-internet.herokuapp.com/javascript_alerts");
		driver.manage().window().maximize();
		
		//Alert popup
		
		/*driver.findElement(By.xpath("//button[text()='Confirm Box']")).click();
		Alert wait=mywait.until(ExpectedConditions.alertIsPresent());
		Thread.sleep(3000);
		System.out.println(wait.getText());
		wait.accept();
		*/
		
		//driver.switchTo().alert().accept();
		
		//Alert prompt popup
		driver.findElement(By.xpath("//button[text()='Prompt']")).click();
		//Alert wait=mywait.until(ExpectedConditions.alertIsPresent());
		Alert alert=driver.switchTo().alert();
		Thread.sleep(2000);
		alert.getText();
		alert.sendKeys("Vishnu");
		alert.accept();
		
		//Validation
		String actvalue=driver.findElement(By.xpath("//p[@id='demo']")).getText();
		String expvalue="Hello Vishnu! How are you today?";
		
		if(actvalue.equals(expvalue))
		{
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
		}
		
		
		driver.quit();
		
		
	
	}

}

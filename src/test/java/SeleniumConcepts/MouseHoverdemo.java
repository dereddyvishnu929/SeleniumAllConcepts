package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MouseHoverdemo {

	public static void main(String[] args) throws InterruptedException {
		
     WebDriver driver=new ChromeDriver();
		
		driver.get("https://testautomationpractice.blogspot.com/");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement f1=driver.findElement(By.xpath("//input[@id='field1']"));
		
		f1.clear();
		f1.sendKeys("Vishnu");
		
		WebElement button=driver.findElement(By.xpath("//button[normalize-space()='Copy Text']"));
		
		Actions act=new Actions(driver);
		
		act.doubleClick(button).build().perform();
		
	     WebElement f2=driver.findElement(By.xpath("//input[@id='field2']"));
	     String copiedtext=f2.getAttribute("Value");
	     
	     System.out.println("Text copied from text1 : " + copiedtext);
		
		if(copiedtext.equals("Vishnu"))
		{
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
		}
		
		
	}

}

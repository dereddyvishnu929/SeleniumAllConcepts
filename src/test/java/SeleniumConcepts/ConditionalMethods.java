package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
 IsDisplayed()  
 IsEnabled()
 IsSelected()
 */
public class ConditionalMethods {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://demo.nopcommerce.com/register?returnUrl=%2F");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3000));
		
		//IsDisplayed()  IsEnabled
		boolean logostatus=driver.findElement(By.xpath("//img[@alt='nopCommerce demo store']")).isDisplayed();
		System.out.println("Status of logo : " + logostatus);
		//driver.findElement(By.xpath("//input[@id='small-searchterms']")).isDisplayed();
		boolean searchstatus=driver.findElement(By.xpath("//input[@id='small-searchterms']")).isEnabled();
		System.out.println("Status of search box : " + searchstatus);
		
		WebElement malerd=driver.findElement(By.xpath("//input[@id='gender-male']"));
		WebElement femalerd=driver.findElement(By.xpath("//input[@id='gender-female']"));
		Thread.sleep(2000);
		
	    //Before selection
		System.out.println("Before selection : ");
		System.out.println(malerd.isSelected());
		System.out.println(femalerd.isSelected());
		
		
		//After selection
		malerd.click();
		System.out.println("After selection : ");
		System.out.println(malerd.isSelected());
		System.out.println(femalerd.isSelected());
		
		
		femalerd.click();
		System.out.println("After selection : ");
		System.out.println(malerd.isSelected());
		System.out.println(femalerd.isSelected());
		
		driver.quit();
		
		
		
		
	}

}

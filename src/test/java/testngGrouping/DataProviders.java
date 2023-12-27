package testngGrouping;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class DataProviders {
	
	WebDriver driver;
	
	@BeforeClass
	void login()
	{
		driver=new ChromeDriver();
		
		
	}
	
	@Test(dataProvider="dp")
	void test(String email, String pwd)
	{
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys(email);
		driver.findElement(By.xpath("//input[@id='Password']")).sendKeys(pwd);
		driver.findElement(By.xpath("//button[normalize-space()='Log in']")).click();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		String act_title="nopCommerce demo store";
		String exp_title=driver.getTitle();
		
		
		Assert.assertEquals(exp_title,act_title);
	}
	
	@AfterClass
	void logout()
	{
		driver.close();
	}
	
	@DataProvider(name="dp", indices= {0,3})
	String [][] loginData()
	{
	String data[][] =   {  
			              {"abc@gmail.com", "test@123"},
			              {"xz@gmail.com", "te123"},
			              {"visu@gmail.com", "test123"},
			              {"abc@gmail.com", "@123"}
			        
                    	};
	return data;
	}
	

}

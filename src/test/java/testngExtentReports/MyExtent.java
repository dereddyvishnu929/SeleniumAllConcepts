package testngExtentReports;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.github.dockerjava.api.model.Driver;


public class MyExtent {
	
	WebDriver driver;
	
	@BeforeClass
	void setup()
	{
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://demo.nopcommerce.com/login?returnUrl=%2F");
		driver.manage().window().maximize();
	}
	
	@Test(priority=1)
	void testlogo()
	{
		try
		{
		boolean status=driver.findElement(By.xpath("//img[@alt='nopCommerce demo store']")).isDisplayed();
		Assert.assertEquals(status, true);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	}
	
	@Test(priority=2)
	void testlogin() throws InterruptedException
	{
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("pqr@gmail.com");
		driver.findElement(By.xpath("//input[@id='Password']")).sendKeys("vishnu");
		driver.findElement(By.xpath("//button[normalize-space()='Log in']")).click();
		Thread.sleep(5000);
		
		try
		{
		boolean status=driver.findElement(By.linkText("My account")).isDisplayed();
		Assert.assertEquals(status, false);
		}
		catch(Exception e)
		{
			Assert.assertEquals(true, true);
		}
		
		
	}
	
	@Test(priority=3, dependsOnMethods= {"testlogin"})
	void logout()
	{
		driver.findElement(By.xpath("//a[normalize-space()='Log out']")).click();
		try
		{
		boolean status=driver.findElement(By.xpath("//a[normalize-space()='Register']")).isDisplayed();
		Assert.assertEquals(status, true);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
		
		driver.close();
	}

}

package testngParallerTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ParallelTest {
	
	WebDriver driver;
	
	@BeforeClass
	@Parameters({"browser","url"})
	void setup(String br, String url) throws InterruptedException
	{
		if(br.equals("Chrome"))
		{
		driver=new ChromeDriver();
		}
		else if(br.equals("Edge"))
		{
		  driver=new EdgeDriver();	
		}
		else
		{
			driver=new FirefoxDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(url);
		driver.manage().window().maximize();
		Thread.sleep(5000);
	}
	
	@Test(priority=1)
	void testlogo()
	{
		try
		{
		boolean status=driver.findElement(By.xpath("//img[@alt='company-branding']")).isDisplayed();
		Assert.assertEquals(status, true);
		}
		catch(Exception e)
		{
			Assert.fail();
		}
	}
	@Test(priority=2)
	void testtitle()
	{
		Assert.assertEquals(driver.getTitle(), "OrangeHRM");
	}
	
	@AfterClass
	void logout()
	{
		driver.close();
	}

}

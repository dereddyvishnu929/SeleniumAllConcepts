package pageobjectmodel;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest {
	
	WebDriver driver;
	//LoginPage lp=new LoginPage(driver);
	LoginPage2 lp;
	
	@BeforeClass
	void setup() throws InterruptedException
	{
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();
		Thread.sleep(5000);
		
	}
	
	@Test(priority=1)
	void testlogo()
	{
		lp=new LoginPage2(driver);
		Assert.assertEquals(lp.checklolopresence(), true);
	}
	
	@Test(priority=2)
	void testlogin()
	{
		lp.username("Admin");
		lp.password("admin123");
		lp.submitbutton();
		
		Assert.assertEquals(driver.getTitle(), "OrangeHRM");
	}
	
	void logout()
	{
		driver.quit();
	}
	

}

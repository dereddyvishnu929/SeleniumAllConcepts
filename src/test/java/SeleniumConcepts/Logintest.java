package SeleniumConcepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Logintest {

	public static void main(String[] args) throws InterruptedException {
		
		//System.setProperty("WebDriver.Chrome.driver", "C:\\VISHNU\\Eclipse\\Drivers\\chromedriver.exe");
		
		//WebDriverManager.chromedriver().setup();
		
		/*
		 Test Cases
		1.launch browser
		2.open url - https://opensource-demo.orangehrmlive.com
		3.provide username : admin
		4.provide password : admin123
		5.click on login button
		6.verify the title of dashboard page
		   expected title : OrangrHRM
		7.close browser
		*/
		
		//Headless mode
		//ChromeOptions options=new ChromeOptions();
		//options.setHeadless(true);
		
		//1.launch browser
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		//2.open url - https://opensource-demo.orangehrmlive.com
		driver.get("https://opensource-demo.orangehrmlive.com");
		Thread.sleep(5000);
		
		//3.provide username : admin
		driver.findElement(By.name("username")).sendKeys("Admin");
		
		//4.provide password : admin123
		driver.findElement(By.name("password")).sendKeys("admin123");
		
		//5.click on login button
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		/*6.verify the title of dashboard page
		   expected title : OrangrHRM*/
		String act_title=driver.getTitle();
		System.out.println(act_title);
		String Exp_title="OrangeHRM";
		if(act_title.equals(Exp_title))
		{
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
		}
         
		//7.close browser*/
		driver.quit();
		
	}

}

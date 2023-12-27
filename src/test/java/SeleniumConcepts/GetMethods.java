package SeleniumConcepts;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

  /*
  get(url)
  getTitle()
  getCurrentUrl()
  getpagesource()
  getwindowhandle()
  getwindowhandles()
*/
public class GetMethods {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://opensource-demo.orangehrmlive.com");
		driver.manage().window().maximize();
		System.out.println(driver.getTitle());
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getPageSource());
	   // System.out.println(driver.getWindowHandle());
		
		Thread.sleep(3000);
		driver.findElement(By.linkText("OrangeHRM, Inc")).click();
		Set<String> windows=driver.getWindowHandles();
		for(String wind:windows)
		{
			System.out.println(wind);
		}
		
		//driver.close();
		driver.quit();

	}

}

package SeleniumConcepts;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleBrowserWindows {

	public static void main(String[] args) {
		
        WebDriver driver =new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();
		
		driver.findElement(By.xpath("//a[text()='OrangeHRM, Inc']")).click();
		
		
		//Capture for browser windows
		Set<String> windows=driver.getWindowHandles();
		
		//Approach 1- using list collection
		/*
		List<String> windowids=new ArrayList(windows);
		
		String parentid=windowids.get(0);
		String chilid=windowids.get(1);
		
		//Switch to chiled window
		driver.switchTo().window(chilid);
		
		driver.findElement(By.xpath("(//button[text()='Contact Sales'])[2]")).click();
		
		////Switch to parent window
		driver.switchTo().window(parentid);
		
		driver.findElement(By.name("username")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		System.out.println(driver.getTitle());
		
		*/
		
		//Approch 2 - using for loop
		for(String windowsid:windows)
		{
			String title=driver.switchTo().window(windowsid).getTitle();
			if(title.equals("OrangeHRM HR Software | OrangeHRM"))
			{
				driver.findElement(By.xpath("(//button[text()='Contact Sales'])[2]")).click();
			}
		}
		
		driver.quit();
		
		
		


	}

}

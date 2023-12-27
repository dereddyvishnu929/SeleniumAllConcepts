package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Navigationalcommands {

	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		driver.navigate().to("https://www.amazon.in/");
		System.out.println(driver.getCurrentUrl());
		
		driver.navigate().to("https://www.flipkart.com/");
		System.out.println(driver.getCurrentUrl());
		
		driver.navigate().back();
		System.out.println(driver.getCurrentUrl());
		
		driver.navigate().forward();
		System.out.println(driver.getCurrentUrl());
		
		driver.navigate().refresh();
		
		driver.quit();

	}

}

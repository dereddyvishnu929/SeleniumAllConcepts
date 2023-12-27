package SeleniumConcepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class XpathLocators4 {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://demo.opencart.com/");
		driver.manage().window().maximize();
		
		String productname=driver.findElement(By.xpath("//a[text()='MacBook']")).getText();
		System.out.println(productname);
		
		driver.quit();

	}

}

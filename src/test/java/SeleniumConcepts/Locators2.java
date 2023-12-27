package SeleniumConcepts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Locators2 {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("http://www.automationpractice.pl/index.php");
		
		Thread.sleep(5000);
		driver.manage().window().maximize();
		
		//Finding number of sliders on home page
		List<WebElement> slider=driver.findElements(By.className("homeslider-container"));
		System.out.println("Total number of sliders :" + slider.size());
		
		//Find the total number of images in home page
		List<WebElement> images=driver.findElements(By.tagName("img"));
		System.out.println("Total number of images : " + images.size());
		
		//Find total number of links on home page
		List<WebElement> links=driver.findElements(By.tagName("a"));
		System.out.println("Total number of links :" + links.size());
		
		driver.quit();

	}

}

package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleAutoSuggetionslistInThroughSearechBox {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver=new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://www.bing.com/");
		driver.manage().window().maximize();
		
		WebElement search=driver.findElement(By.xpath("//textarea[@id='sb_form_q']"));
		search.click();
		Thread.sleep(5000);
		
		
		



	}

}

package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragAndDrop {

	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://testautomationpractice.blogspot.com/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		WebElement source=driver.findElement(By.xpath("//p[normalize-space()='Drag me to my target']"));
		WebElement target=driver.findElement(By.xpath("//div[@id='droppable']"));
	
		Actions act=new Actions(driver);
		 
		act.dragAndDrop(source, target).build().perform();
		
		driver.quit();
	}

}

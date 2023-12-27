
package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropBy {

	public static void main(String[] args) {
		
WebDriver driver=new ChromeDriver();
		
		driver.get("https://www.jqueryscript.net/demo/Price-Range-Slider-jQuery-UI/");
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		Actions act=new Actions(driver);
		
		//Min slider
		WebElement minslider=driver.findElement(By.xpath("//body[1]/div[2]/div[2]/span[1]"));
		System.out.println("Current location of slider : " + minslider.getLocation());
		act.dragAndDropBy(minslider, 100, 250).build().perform();
		System.out.println("Cureent location of slider after moving : " + minslider.getLocation());
		
		//Max slider
		WebElement maxslider=driver.findElement(By.xpath("//span[2]"));
		System.out.println("Current location of slider : " + maxslider.getLocation());
		act.dragAndDropBy(maxslider, -100, 250).build().perform();
		System.out.println("Cureent location of slider after moving : " + maxslider.getLocation());
				
					
		driver.quit();
	}

}

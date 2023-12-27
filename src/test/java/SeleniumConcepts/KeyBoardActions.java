package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class KeyBoardActions {

	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://text-compare.com/");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.findElement(By.xpath("//textarea[@id='inputText1']")).sendKeys("Welocme to Bangalore");
		
		Actions act=new Actions(driver);
		
		//ctrl+a
		act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
		
		//ctrl+c
		act.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
		
		//tab
		act.sendKeys(Keys.TAB).perform();
		
		//ctrl+v
		act.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();

	}

}

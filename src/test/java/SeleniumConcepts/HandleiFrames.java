package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleiFrames {

	public static void main(String[] args) {
		
		WebDriver driver =new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get("https://ui.vision/demo/webtest/frames/");
		driver.manage().window().maximize();
		
		
		//iframe1
		WebElement frm1=driver.findElement(By.xpath("//frameset[1]/frame[1]"));
		driver.switchTo().frame(frm1);
		driver.findElement(By.xpath("//input[@name='mytext1']")).sendKeys("Vishnu");
		
		driver.switchTo().defaultContent();
		
		//iframe2
		WebElement frm2=driver.findElement(By.xpath("(//frame)[2]"));
		driver.switchTo().frame(frm2);
		driver.findElement(By.xpath("//input[@name='mytext2']")).sendKeys("Vardhan");
		
		driver.switchTo().defaultContent();
       
		//iframe3
		WebElement frm3=driver.findElement(By.xpath("(//frame)[3]"));
		driver.switchTo().frame(frm3);
		driver.findElement(By.xpath("//input[@name='mytext3']")).sendKeys("Reddy");
		
		driver.switchTo().frame(0);
		
		//inside iframe
		WebElement ifrm0=driver.findElement(By.xpath("//div[@class='geS5n']"));
		driver.findElement(By.xpath("(//div[@class='AB7Lab Id5V1'])[2]")).click();
		
		
		driver.switchTo().defaultContent();
      
		//iframe4
		WebElement frm4=driver.findElement(By.xpath("//frameset//frame[3]"));
		driver.switchTo().frame(frm4);
		driver.findElement(By.xpath("//input[@name='mytext4']")).sendKeys("Dereddy");
		
		driver.quit();
		
		
		
		

	}

}

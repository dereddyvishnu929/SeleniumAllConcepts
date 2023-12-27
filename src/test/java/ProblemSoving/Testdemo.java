package ProblemSoving;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Testdemo {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver=new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("https://rahulshettyacademy.com/locatorspractice/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		WebElement ele1=driver.findElement(By.xpath("//input[@id='inputUsername']"));
		WebElement ele2=driver.findElement(By.xpath("//input[@name='inputPassword']"));
		WebElement ele3=driver.findElement(By.xpath("//button[@type='submit']"));
		Thread.sleep(2000);
		
		
		ele1.sendKeys("vishnu");
		ele2.sendKeys("test123");
		ele3.click();
		
		WebElement ele4=driver.findElement(By.xpath("//p[@class='error']"));
		WebElement ele5=driver.findElement(By.xpath("//a[text()='Forgot your password?']"));
		
		System.out.println(ele4.getText());
		
		ele5.click();
		
		WebElement ele6=driver.findElement(By.xpath("(//input[@type='text'])[1]"));
		WebElement ele7=driver.findElement(By.xpath("(//input[@type='text'])[2]"));
		WebElement ele8=driver.findElement(By.xpath("(//input[@type='text'])[3]"));
		WebElement ele9=driver.findElement(By.xpath("//button[@class='reset-pwd-btn']"));
		Thread.sleep(2000);
		
		
		ele6.sendKeys("vishnu");
		ele7.sendKeys("dereddyvishhnu929@gmail.com");
		ele8.sendKeys("8008076050");
		ele9.click();
		
		WebElement ele10=driver.findElement(By.xpath("//p[@class='infoMsg']"));
		
		String str=ele10.getText();
		System.out.println(str);
		
		String str2=str.substring(31, 49);
		System.out.println(str2);
		
		WebElement ele11=driver.findElement(By.xpath("//button[@class='go-to-login-btn']"));
		ele11.click();
		Thread.sleep(2000);
		ele1.sendKeys("vishnu");
		ele2.sendKeys(str2);
		ele3.click();
		
		driver.quit();
		
		
		
		
		
		
		
		
		
		
		

	}

}

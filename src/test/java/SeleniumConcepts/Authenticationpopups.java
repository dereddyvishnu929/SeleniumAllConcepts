package SeleniumConcepts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Authenticationpopups {

	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		//https://the-internet.herokuapp.com/basic_auth
		//username : admin
		//password : admin
		//https://admin:admin@the-internet.herokuapp.com/basic_auth
		
		//driver.get("https://the-internet.herokuapp.com/basic_auth");
		driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
		driver.manage().window().maximize();
		
		String text=driver.findElement(By.xpath("//p[contains(text(),'Congratulations!')]")).getText();
		System.out.println(text);
		
		if(text.contains("Congratulations"))
		{
			System.out.println("Test case passed");
		}
		else
		{
			System.out.println("Test case failed");
		}
		
		driver.quit();



	}

}

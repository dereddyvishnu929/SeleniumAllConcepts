package SeleniumConcepts;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class Waitsdemo {

	public static void main(String[] args) {

        WebDriver driver=new ChromeDriver();
        
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        //Explicit wait declaration 
        
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        
        //Fluent wait declaration
        
        Wait<WebDriver> mywait = new FluentWait<WebDriver>(driver)
        	       .withTimeout(Duration.ofSeconds(30L))
        	       .pollingEvery(Duration.ofSeconds(5L))
        	       .ignoring(NoSuchElementException.class);
        	       
        
        //Explicit wait usage
        WebElement username=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Username']")));
        username.sendKeys("Admin");
        
        WebElement password=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Password']")));
        password.sendKeys("admin123");
        
       
        //Fluent wait usage
        WebElement username1 = mywait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
              return driver.findElement(By.xpath("//input[@placeholder='Username']"));
            }
          });
        		
         username1.sendKeys("Admin");
        
         WebElement password1 = mywait.until(new Function<WebDriver, WebElement>() {
             public WebElement apply(WebDriver driver) {
               return driver.findElement(By.xpath("//input[@placeholder='Password']"));
             }
           });
         		
          username1.sendKeys("admin123");
         
	}

}

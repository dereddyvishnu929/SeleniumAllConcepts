package SeleniumConcepts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CSSLocators3 {

	public static void main(String[] args) throws InterruptedException {

       WebDriverManager.chromedriver().setup();
       WebDriver driver=new ChromeDriver();
       
      /*CSS Selectors
       
       * tag id : #
       * tag class : .
       * tag attribute : []
       * tag class attribute : .[]
       
       */
          driver.get("https://demo.nopcommerce.com/");
          driver.manage().window().maximize();
          Thread.sleep(5000);
          
          //tag id
          driver.findElement(By.cssSelector("input#small-searchterms")).sendKeys("Macbook");
          //driver.findElement(By.cssSelector("#small-searchterms")).sendKeys("Macbook");
          
          //tag class
          //driver.findElement(By.cssSelector("input.search-box-text")).sendKeys("Macbook");
          //driver.findElement(By.cssSelector(".search-box-text")).sendKeys("Macbook");
          
          //tag attribute
          //driver.findElement(By.cssSelector("input[type='text']")).sendKeys("Macbook");
          //driver.findElement(By.cssSelector("[type='text']")).sendKeys("Macbook");
          
          //tag class attribute
          //driver.findElement(By.cssSelector("input.search-box-text[type='text']")).sendKeys("Macbook");
         // driver.findElement(By.cssSelector(".search-box-text[type='text']")).sendKeys("Macbook");
          
          driver.quit();
	}

}

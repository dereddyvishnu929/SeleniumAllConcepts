package SeleniumConcepts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleCheckBoxes {

	public static void main(String[] args) throws InterruptedException {

          WebDriver driver=new ChromeDriver();
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
          
          driver.get("https://testautomationpractice.blogspot.com/");
          driver.manage().window().maximize();
          
          //Select specific check box
          //driver.findElement(By.xpath("//input[@id='sunday']")).click();
          //System.out.println(driver.findElement(By.xpath("//input[@id='sunday']")).isSelected());
          
          //Select all checkboxes at a time
          List<WebElement> checkboxes=driver.findElements(By.xpath("//input[@class='form-check-input' and @type='checkbox']"));
         /* 
          for(int i=0;i<checkboxes.size();i++)
          {
        	  checkboxes.get(i).click();
          }
          Thread.sleep(3000);
          
          //Deselect all checkboxes
          for(int i=0;i<checkboxes.size();i++)
          {
        	  if(checkboxes.get(i).isSelected());
        	  {
        		  checkboxes.get(i).click();
        	  }
           }
        	  */
          
          //select first 2 check boxes
          /*
          for(int i=0;i<3;i++)
          {
        	  checkboxes.get(i).click();
          }
          Thread.sleep(2000);
          
          //Deselct first 2 checkboxes
          for(int i=0;i<3;i++)
          {
        	  if(checkboxes.get(i).isSelected());
        	  {
        		  checkboxes.get(i).click();
        	  }
          }
          */
          
          //Select last 2 checkboxes 
          for(int i=5;i<checkboxes.size();i++)
          {
        	  checkboxes.get(i).click();
          }
          Thread.sleep(2000);
          
          //Deselct last 2 checkboxes
          for(int i=5;i<checkboxes.size();i++)
          {
        	  if(checkboxes.get(i).isSelected());
        	  {
        		  checkboxes.get(i).click();
        	  }
          }		
          
          driver.quit();
          
          
          
          

	}

	}


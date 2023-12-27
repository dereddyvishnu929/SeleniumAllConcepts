package SeleniumConcepts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HandleDatePicker {

	public static void main(String[] args) {


		WebDriver driver=new ChromeDriver();
		
		driver.get("https://jqueryui.com/datepicker/");
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.switchTo().frame(0);
		
		//Approach 1
		//  driver.findElement(By.xpath("//input[@id='datepicker']")).sendKeys("10/26/2023");
		 
		//Approach 2
		
		
		  driver.findElement(By.xpath("//input[@id='datepicker']")).click();
		  
		  String month="March";
		  String year="2024";
		  String date="31";
		  
		  //select month and year
		  while(true)
		  {
		  String mon=driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
		  String ye=driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
		  
		  if(mon.equals(month) && ye.equals(year))
		  {
			  break;
		  }
		  
		  driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']")).click();
		  }
		  
		  //Select date
		  List<WebElement> alldates=driver.findElements(By.xpath("//table[@class='ui-datepicker-calendar']//td"));
		  
		  for(WebElement dt:alldates)
		  {
			  if(dt.getText().equals(date))
			  {
				  dt.click();
				  break;
			  }
		  }
		 
		  
		  
		  
	}

}

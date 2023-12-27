package SeleniumConcepts;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class HandleDropDownList {

	public static void main(String[] args) {
		
		WebDriver driver=new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.get("https://testautomationpractice.blogspot.com/");
		driver.manage().window().maximize();
		
		WebElement dropdownlist=driver.findElement(By.xpath("//select[@id='country']"));
		Select dropdown=new Select(dropdownlist); 
		
		//Select value using dropdown
		dropdown.selectByVisibleText("India");
		//dropdown.selectByValue("canada");
		//dropdown.selectByIndex(4);
		
		//Finding total options in dropdown
		List<WebElement> options=dropdown.getOptions();
		System.out.println("Total number of list in dropdown : " + options.size());
		
		//Print all dropdown list
		for(int i=0;i<options.size();i++)
		{
			System.out.println(options.get(i).getText());
		}
		
		
	}

}

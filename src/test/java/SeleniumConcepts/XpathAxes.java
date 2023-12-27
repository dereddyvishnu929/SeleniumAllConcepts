package SeleniumConcepts;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class XpathAxes {

	public static void main(String[] args) {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://demo.opencart.com/");
		
		//Self node
		String productname=driver.findElement(By.xpath("//a[text()='MacBook']")).getText();
		System.out.println("Self node : " + productname);
		
		//Parent node
		String parentnode=driver.findElement(By.xpath("//a[text()='MacBook']/parent::h4")).getText();
		System.out.println("Parent node : " + parentnode);
		
		//Ancestor node
		String Ancestornode=driver.findElement(By.xpath("//a[text()='MacBook']/ancestor::*")).getText();
		System.out.println(Ancestornode);
		
		//Following siblings
		Dimension fsiblings=driver.findElement(By.xpath("//div[@class='description']/following-sibling::*")).getSize();
		System.out.println(fsiblings);
		
		driver.quit();

	}

}

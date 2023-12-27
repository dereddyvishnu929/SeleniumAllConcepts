package pageobjectmodel;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPage {
	
	public WebDriver driver;
	
	//Constructor
	LoginPage(WebDriver driver)
	{
		this.driver=driver;
	}
	
	//Locators
	By logo_loc=By.xpath("//img[@alt='company-branding']");
	By text_username_loc=By.name("username");
	By text_password_loc=By.name("password");
	By btn_submitt_loc=By.xpath("//button[normalize-space()='Login']");
	
	
	//Action Methods
	public void username(String username)
	{
		driver.findElement(text_username_loc).sendKeys(username);
	}
	
	public void password(String password)
	{
		driver.findElement(text_password_loc).sendKeys(password);
	}
	
	public void submitbutton()
	{
		driver.findElement(btn_submitt_loc).click();
	}
	
	public boolean checklolopresence()
	{
		boolean Status=driver.findElement(logo_loc).isDisplayed();
		return Status;
		
	}

	}

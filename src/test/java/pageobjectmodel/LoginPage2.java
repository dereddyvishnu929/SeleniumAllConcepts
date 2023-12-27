package pageobjectmodel;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage2 {
	
	public WebDriver driver;
	
	//Constructor
	LoginPage2(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//Locators
	
	@FindBy(xpath="//img[@alt='company-branding']")
			WebElement logo;
	
	@FindBy(name="username")
	       WebElement text_username;
	
	@FindBy(name="password")
	       WebElement text_password;
	
	@FindBy(xpath="//button[normalize-space()='Login']")
	      WebElement submit_btn;
	
	
	
	//Action Methods
	public void username(String username)
	{
		text_username.sendKeys(username);
	}
	
	public void password(String password)
	{
		text_password.sendKeys(password);
	}
	
	public void submitbutton()
	{
		submit_btn.click();
	}
	
	public boolean checklolopresence()
	{
		boolean Status=logo.isDisplayed();
		return Status;
		
	}

	}

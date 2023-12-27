package testng;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AnnotationsDemo1 {
	
	@BeforeMethod
	void login()
	{
		System.out.println("login");
	}
	
	@Test(priority=1)
	void search()
	{
		System.out.println("search");
	}
	
	@Test(priority=2)
	void Advancedsearch()
	{
		System.out.println("advancedsearch");
	}
	
	@AfterMethod
	void logout()
	{
		System.out.println("logout");
	}

}

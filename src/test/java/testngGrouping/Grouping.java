package testngGrouping;

import org.testng.annotations.Test;

public class Grouping {
	
	/*
	loginByEmail - Sanity & regression
	loginByfacebook - sanity
	loginByTwitter - sanity
	
	signupByEmail -  Sanity & regression
	signupByfacebook - regression
	signupTeitter - regression
	
	paymentinRupees - sanity & regression
	paymentinDollar - sanity
	paymentreturnbyBank - regression
	*/
	
	@Test(priority=1, groups= {"Sanity", "regression"})
	void loginByEmail()
	{
		System.out.println("this is login email");
	}
	
	@Test(priority=2, groups= {"Sanity"})
	void loginByFacebook()
	{
		System.out.println("this is login facebook");
	}
	
	@Test(priority=3, groups= {"Sanity"})
	void loginByTwitter()
	{
		System.out.println("this is login twitter");
	}
	
	@Test(priority=4, groups= {"Sanity", "regression"})
	void signupByEmail()
	{
		System.out.println("this is signup email");
	}
	
	@Test(priority=5, groups= {"regression"})
	void signupByFacebook()
	{
		System.out.println("this is signup facebook");
	}
	
	@Test(priority=6, groups= {"regression"})
	void signupByTwitter()
	{
		System.out.println("this is signup twitter");
	}
	
	@Test(priority=7, groups= {"Sanity", "regression"})
	void paymanetinRupees()
	{
		System.out.println("this is payment in rupees");
	}
	
	@Test(priority=8,groups= {"Sanity"})
	void paymentinDollar()
	{
		System.out.println("this is payment in dollar");
	}
	
	@Test(priority=9, groups= {"regression"})
	void paymentReturnByBank()
	{
		System.out.println("this is payment return by bank");
	}
	
	
	
	

}
